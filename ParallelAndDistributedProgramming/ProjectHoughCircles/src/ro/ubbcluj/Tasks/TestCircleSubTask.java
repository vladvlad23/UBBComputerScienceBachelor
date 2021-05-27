package ro.ubbcluj.Tasks;

import org.apache.commons.lang3.tuple.Triple;

import java.util.List;

public class TestCircleSubTask implements Runnable {
    private final int x;
    private final int imageWidth;
    private final int imageHeight;
    private final int minimumRadius;
    private final int maximumRadius;
    private final int stepNumber;
    private final double treshold;
    private final int[][][] accumulator;
    private final List<Triple<Integer,Integer,Integer>> circles;

    public TestCircleSubTask(int x, int imageWidth, int imageHeight, int minimumRadius, int maximumRadius, int stepNumber, double treshold, int[][][] accumulator, List<Triple<Integer, Integer, Integer>> circles) {
        this.x = x;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        this.minimumRadius = minimumRadius;
        this.maximumRadius = maximumRadius;
        this.stepNumber = stepNumber;
        this.treshold = treshold;
        this.accumulator = accumulator;
        this.circles = circles;
    }

    @Override
    public void run() {
        for(int y=0;y<imageHeight;y++){
            for(int r=minimumRadius;r<maximumRadius;r++){
                int value = accumulator[x][y][r];
                if(((float) value/stepNumber) >= treshold && validCircles(x,y, circles)){
                    System.out.println(String.format("Values: %.2f %d %d %d ",(float) value/stepNumber,x,y,r));
                    circles.add(Triple.of(x,y,r));
                }
            }
        }
    }


    private boolean validCircles(int x, int y, List<Triple<Integer, Integer, Integer>> circles) {
        for(Triple<Integer,Integer,Integer> triple: circles){
            if(Math.pow(x-triple.getLeft(),2) + Math.pow(y-triple.getMiddle(),2) < Math.pow(triple.getRight(),2)){
                return false;
            }
        }
        return true;
    }
}
