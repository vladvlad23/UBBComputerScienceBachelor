package ro.ubbcluj;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import ro.ubbcluj.Tasks.EdgeDetectingSubTask;
import ro.ubbcluj.Tasks.TestCircleSubTask;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class HoughTransformation {

    public BufferedImage applyHoughTransformation(BufferedImage image, int minimumRadius, int maximumRadius, int stepNumber, double treshold) throws IOException {
        int accumulator[][][] = createEmptyAccumulator(image.getWidth(),image.getHeight(), maximumRadius);
        Map<Pair<Integer,Integer>, Integer> points = new ConcurrentHashMap();

        for(int r=minimumRadius;r<maximumRadius+1;r++){
            for (int step=0;step<stepNumber;step++){
                Pair<Integer,Integer> corespondent = radiusCorrespondent(r, step,stepNumber);
                points.put(corespondent,r);
            }
        }

        Set<Pair<Integer, Integer>> edgeDetector = new CannyEdgeDetector().applyCannyAlgorithm(image);
        for(Pair<Integer,Integer> edge: edgeDetector){
            for(Map.Entry<Pair<Integer,Integer>,Integer> pointCoordinate: points.entrySet()){
                int a = edge.getLeft() - pointCoordinate.getKey().getLeft();
                int b = edge.getRight() - pointCoordinate.getKey().getRight();
                if(a>0 && b>0 && a< image.getWidth() && b<image.getHeight())
                    accumulator[a][b][pointCoordinate.getValue()] += 1;
            }
        }
        List<Triple<Integer,Integer,Integer>> circles = Collections.synchronizedList(new ArrayList<>());
        ExecutorService service = Executors.newFixedThreadPool(4);
        for(int x=0;x< image.getWidth();x++){
            service.submit(new TestCircleSubTask(x,image.getWidth(),image.getHeight(),minimumRadius,maximumRadius,stepNumber,treshold,accumulator,circles));

        }
        service.shutdown();
        try {
            service.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        Graphics2D g = image.createGraphics();
        g.setColor(Color.RED);
        for(Triple<Integer,Integer,Integer> circle: circles) {
            double a = circle.getLeft() - circle.getRight() * Math.cos(0 * Math.PI / 180);
            double b = circle.getMiddle() - circle.getRight() * Math.sin(90 * Math.PI / 180);
            g.drawOval((int) a, (int) b, 2 * circle.getRight(), 2 * circle.getRight());
        }
        File outfinal = new File( "totalCircles.png");
        ImageIO.write(image, "png", outfinal);

        return null;
    }


    private int[][][] createEmptyAccumulator(int width, int height, int maximumRadius) {
        int [][][] returnValue = new int[width][height][maximumRadius+1];
        for(int [][] firstDimension : returnValue) {
            for(int [] secondDimension : firstDimension){
                Arrays.fill(secondDimension,0);
            }
        }
        return returnValue;
    }

    public static Pair<Integer,Integer> radiusCorrespondent(int radius, int step, int stepNumber){
        return Pair.of(
                ((Long)(Math.round(radius * Math.cos(2 * Math.PI * step / stepNumber)))).intValue(),
                ((Long)Math.round(radius * Math.sin(2 * Math.PI * step / stepNumber))).intValue());
    }

}
