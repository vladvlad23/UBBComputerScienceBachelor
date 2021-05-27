package ro.ubbcluj;

import mpi.MPI;
import org.apache.commons.lang3.tuple.Pair;
import ro.ubbcluj.Tasks.BlurringSubTask;
import ro.ubbcluj.Tasks.EdgeFilteringSubTask;
import ro.ubbcluj.Tasks.FilteringNonMaximumSubTask;
import ro.ubbcluj.Tasks.GradientSubTask;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class CannyEdgeDetector {

    private static final int PROCESSOR_NUMBER = 4;

    public Set<Pair<Integer, Integer>> applyCannyAlgorithm(BufferedImage image) {


        Map<Pair<Integer, Integer>, Double> pixels = new ConcurrentHashMap<>();

        for (int x = 0; x < image.getWidth(); x++)
            for (int y = 0; y < image.getHeight(); y++)
                pixels.put(Pair.of(x, y), (double) ((image.getRGB(x, y)) & 0xFF));


        Map<Pair<Integer, Integer>, Integer> blurredImage = blurred(pixels, image.getWidth(), image.getHeight());

        Map<Pair<Integer, Integer>, Double> gradientResult = new ConcurrentHashMap<>();
        Map<Pair<Integer, Integer>, Double> gradientDirection = new ConcurrentHashMap<>();
        computeGradient(blurredImage, image.getWidth(), image.getHeight(), gradientResult, gradientDirection);
        filterNonMaximum(gradientResult, gradientDirection, image.getWidth(), image.getHeight());
        return filterStrongEdges(gradientResult, image.getWidth(), image.getHeight(), 20,25);

    }

    private void filterNonMaximum(Map<Pair<Integer, Integer>, Double> gradientResult,
                                  Map<Pair<Integer, Integer>, Double> gradientDirection,
                                  int imageWidth,
                                  int imageHeight) {

        Object[] filteringNonMaximumSubTask = new FilteringNonMaximumSubTask[PROCESSOR_NUMBER];
        int step = (imageWidth-2) / (PROCESSOR_NUMBER-1);
        for (int x = 2, procNumber = 0; x + step <= imageWidth - 2; x += step, procNumber++) {
            filteringNonMaximumSubTask[procNumber] = new FilteringNonMaximumSubTask(x, x + step,imageHeight,gradientResult,gradientDirection);
        }
        for(int i=1;i<PROCESSOR_NUMBER;i++){
            MPI.COMM_WORLD.Send(filteringNonMaximumSubTask, i-1, 1, MPI.OBJECT, i, i);
        }
        Object[] receivedResults = new Object[2];
        for (int procNumber = 1; procNumber < PROCESSOR_NUMBER; procNumber++) {

            MPI.COMM_WORLD.Recv(receivedResults, 0, 2, MPI.OBJECT, procNumber, 12);
            gradientResult.putAll((Map<? extends Pair<Integer, Integer>, ? extends Double>) receivedResults[0]);
            gradientDirection.putAll((Map<? extends Pair<Integer, Integer>, ? extends Double>) receivedResults[1]);
        }
    }

    private Set<Pair<Integer,Integer>> filterStrongEdges(Map<Pair<Integer, Integer>, Double> gradientResult,
                                                         int imageWidth,
                                                         int imageHeight,
                                                         double low,
                                                         double high) {
        Set<Pair<Integer,Integer>> edgesToBeKept = new HashSet<>();
        Object[] edgeFilteringSubtask = new EdgeFilteringSubTask[PROCESSOR_NUMBER];
        int step = imageWidth / (PROCESSOR_NUMBER-1);
        for (int x = 1, procNumber = 0; x + step <= imageWidth; x += step, procNumber++) {
            edgeFilteringSubtask[procNumber] = new EdgeFilteringSubTask(x, x + step,high,imageHeight,gradientResult);
        }
        for(int i=1;i<PROCESSOR_NUMBER;i++){
            MPI.COMM_WORLD.Send(edgeFilteringSubtask, i-1, 1, MPI.OBJECT, i, i);
        }
        Object[] receivedResults = new Object[2];
        for (int procNumber = 1; procNumber < PROCESSOR_NUMBER; procNumber++) {

            MPI.COMM_WORLD.Recv(receivedResults, 0, 1, MPI.OBJECT, procNumber, 12);
            edgesToBeKept.addAll((Set<Pair<Integer,Integer>>) receivedResults[0]);
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
        //this does 4 iteration for the standard image. Not worth parallelizing (MPI costs more than the while loop)
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
        Object[] gradientSubTasks = new GradientSubTask[PROCESSOR_NUMBER];
        int step = imageWidth / (PROCESSOR_NUMBER-1);
        for (int x = 0, procNumber = 0; x + step < imageWidth; x += step, procNumber++) {
            gradientSubTasks[procNumber] = new GradientSubTask(x, x + step, blurredImage, imageHeight, imageWidth);
        }
        for(int i=1;i<PROCESSOR_NUMBER;i++){
            MPI.COMM_WORLD.Send(gradientSubTasks, i-1, 1, MPI.OBJECT, i, i);
        }
        Object[] receivedResults = new Object[2];
        for (int procNumber = 1; procNumber < PROCESSOR_NUMBER; procNumber++) {

            MPI.COMM_WORLD.Recv(receivedResults, 0, 2, MPI.OBJECT, procNumber, 12);
            gradientResult.putAll((Map<? extends Pair<Integer, Integer>, ? extends Double>) receivedResults[0]);
            gradientDirection.putAll((Map<? extends Pair<Integer, Integer>, ? extends Double>) receivedResults[1]);
        }
    }

    public interface ShortenFactory extends Serializable {
        int shorten(int x, int l, int u);
    }


    private Map<Pair<Integer, Integer>, Integer> blurred(Map<Pair<Integer, Integer>, Double> pixels, int imageWidth, int imageHeight) {
        ShortenFactory shortenFactory = (x, l, u) -> (x < l ? l : (Math.min(x, u)));

        Map<Pair<Integer, Integer>, Integer> blurredResult = new ConcurrentHashMap<>();

        Object[] blurringSubTasks = new BlurringSubTask[PROCESSOR_NUMBER];
        int step = imageWidth / (PROCESSOR_NUMBER-1);
        for (int x = 0, procNumber = 0; x < imageWidth; x += step, procNumber++) {
            blurringSubTasks[procNumber] = new BlurringSubTask(x, x + step, pixels, imageWidth, imageHeight, shortenFactory);
        }
        for (int procNumber = 1; procNumber < PROCESSOR_NUMBER; procNumber++) {
            MPI.COMM_WORLD.Send(blurringSubTasks, procNumber-1, 1, MPI.OBJECT, procNumber, procNumber);
        }
        Object[] receivedResults = new Object[PROCESSOR_NUMBER];
        for (int procNumber = 1; procNumber < PROCESSOR_NUMBER; procNumber++) {

            MPI.COMM_WORLD.Recv(receivedResults, procNumber, 1, MPI.OBJECT, procNumber, 12);
        }
        for (int i = 1; i < PROCESSOR_NUMBER; i++) {
            blurredResult.putAll((Map) receivedResults[i]);
        }

        return blurredResult;
    }


}
