package ro.ubbcluj;

import org.apache.commons.lang3.tuple.Pair;
import ro.ubbcluj.Tasks.BlurringSubTask;
import ro.ubbcluj.Tasks.EdgeFilteringSubTask;
import ro.ubbcluj.Tasks.FilteringSubTask;
import ro.ubbcluj.Tasks.GradientSubTask;

import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CannyEdgeDetector {

    public Set<Pair<Integer, Integer>> applyCannyAlgorithm(BufferedImage image) {


        Map<Pair<Integer, Integer>, Double> pixels = new ConcurrentHashMap<>();

        for( int x = 0; x < image.getWidth(); x++ )
            for( int y = 0; y < image.getHeight(); y++ )
                pixels.put(Pair.of(x,y), (double) ((image.getRGB(x, y))& 0xFF));


        Map<Pair<Integer, Integer>, Integer> blurredImage = blurred(pixels, image.getWidth(), image.getHeight());
        System.out.println(blurredImage);
        Map<Pair<Integer, Integer>, Double> gradientResult = new ConcurrentHashMap<>();
        Map<Pair<Integer, Integer>, Double> gradientDirection = new ConcurrentHashMap<>();
        computeGradient(blurredImage, image.getWidth(), image.getHeight(), gradientResult, gradientDirection);
        filterNonMaximum(gradientResult,gradientDirection, image.getWidth(),image.getHeight());
        return filterStrongEdges(gradientResult, image.getWidth(), image.getHeight(), 20,25);

    }

    private void filterNonMaximum(Map<Pair<Integer, Integer>, Double> gradientResult,
                                  Map<Pair<Integer, Integer>, Double> gradientDirection,
                                  int imageWidth,
                                  int imageHeight) {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        for (int x = 2; x < imageWidth - 2; x++) {
            executorService.submit(new FilteringSubTask(x, imageHeight,gradientResult,gradientDirection));
        }
        executorService.shutdown();
        try {
            executorService.awaitTermination(10,TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private Set<Pair<Integer,Integer>> filterStrongEdges(Map<Pair<Integer, Integer>, Double> gradientResult,
                                        int imageWidth,
                                        int imageHeight,
                                        double low,
                                        double high) {
        Set<Pair<Integer,Integer>> edgesToBeKept = new HashSet<>();
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        for (int x = 2; x < imageWidth - 2; x++) {
            executorService.submit(new EdgeFilteringSubTask(x,high,imageHeight,edgesToBeKept,gradientResult));

        }
        executorService.shutdown();
        try {
            executorService.awaitTermination(10,TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Set<Pair<Integer, Integer>> lastIteration = new HashSet<>(edgesToBeKept);
        Set<Pair<Integer, Integer>> edgesToPixel = Set.of(
                Pair.of(-1, -1),
                Pair.of(-1, 0),
                Pair.of(-1, 1),
                Pair.of(0, -1),
                Pair.of(1, -1),
                Pair.of(1, 0),
                Pair.of(1, 1));
        //this does 4 iteration for the standard image. Not worth parallelizing (Executor service costs more than the while loop)
        while (!lastIteration.isEmpty()) {
            Set<Pair<Integer, Integer>> newEdgesToBeKept = new HashSet<>();
            for (Pair<Integer, Integer> pair : lastIteration) {
                for (Pair<Integer, Integer> edges : edgesToPixel) {
                    Pair<Integer, Integer> sumOfCoordinates = Pair.of(
                            pair.getLeft() + edges.getLeft(),
                            pair.getRight() + edges.getRight());

                    if(gradientResult.containsKey(sumOfCoordinates)) {
                        if (gradientResult.get(sumOfCoordinates) > low && !edgesToBeKept.contains(sumOfCoordinates)) {
                            newEdgesToBeKept.add(sumOfCoordinates);
                        }
                    }
                }
            }
            edgesToBeKept.addAll(newEdgesToBeKept);
            lastIteration = newEdgesToBeKept;

        }

        return edgesToBeKept;
    }

    private void computeGradient(Map<Pair<Integer, Integer>, Integer> blurredImage,
                                 int imageWidth,
                                 int imageHeight,
                                 Map<Pair<Integer, Integer>, Double> gradientResult,
                                 Map<Pair<Integer, Integer>, Double> gradientDirection) {
        ExecutorService service = Executors.newFixedThreadPool(4);
        for (int x = 0; x < imageWidth; x++) {
            service.submit(new GradientSubTask(x,blurredImage,gradientResult,gradientDirection,imageHeight,imageWidth));
        }
        service.shutdown();
        try {
            service.awaitTermination(10,TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public interface ShortenFactory {
        int shorten(int x, int l, int u);
    }


    private Map<Pair<Integer, Integer>, Integer> blurred(Map<Pair<Integer, Integer>, Double> pixels, int imageWidth, int imageHeight) {
        ShortenFactory shortenFactory = (x,l,u) -> (x<l ? l : (Math.min(x, u)));
        double[][] gaussianKernel = {
                {1.0 / 256, 4.0 / 256, 6.0 / 256, 4.0 / 256, 1.0 / 256},
                {4.0 / 256, 16.0 / 256, 24.0 / 256, 16.0 / 256, 4.0 / 256},
                {6.0 / 256, 24.0 / 256, 36.0 / 256, 24.0 / 256, 6.0 / 256},
                {4.0 / 256, 16.0 / 256, 24.0 / 256, 16.0 / 256, 4.0 / 256},
                {1.0 / 256, 4.0 / 256, 6.0 / 256, 4.0 / 256, 1.0 / 256}};

        int offset = gaussianKernel.length / 2;

        Map<Pair<Integer, Integer>, Integer> blurredResult = new ConcurrentHashMap<>();
        int accumulator;
        int xCoordinate;
        int yCoordinate;

        ExecutorService executorService = Executors.newFixedThreadPool(4);
        for (int x = 0; x < imageWidth; x++) {
            executorService.submit(new BlurringSubTask(x,pixels,imageWidth,imageHeight,shortenFactory,gaussianKernel,offset,blurredResult));
        }
        executorService.shutdown();
        try {
            executorService.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return blurredResult;
    }


}
