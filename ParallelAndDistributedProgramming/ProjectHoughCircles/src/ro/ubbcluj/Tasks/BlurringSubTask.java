package ro.ubbcluj.Tasks;

import org.apache.commons.lang3.tuple.Pair;
import ro.ubbcluj.CannyEdgeDetector;

import java.util.Map;

public class BlurringSubTask implements Runnable {

    private final int x;
    private final Map<Pair<Integer, Integer>, Double> pixels;
    private final int imageWidth;
    private final int imageHeight;
    private final CannyEdgeDetector.ShortenFactory shortenFactory;
    private final double[][] gaussianKernel;
    private final int offset;
    private final Map<Pair<Integer, Integer>, Integer> blurredResult;

    public BlurringSubTask(int x, Map<Pair<Integer, Integer>, Double> pixels, int imageWidth, int imageHeight, CannyEdgeDetector.ShortenFactory shortenFactory, double[][] gaussianKernel, int offset, Map<Pair<Integer, Integer>, Integer> blurredResult) {
        this.x = x;
        this.pixels = pixels;
        this.imageHeight = imageHeight;
        this.imageWidth = imageWidth;
        this.shortenFactory = shortenFactory;
        this.gaussianKernel = gaussianKernel;
        this.blurredResult = blurredResult;
        this.offset = offset;
    }

    @Override
    public void run() {
        int accumulator;
        int yCoordinate;
        int xCoordinate;
        for (int y = 0; y < imageHeight; y++) {
            Pair<Integer, Integer> currentCoordinates = Pair.of(x, y);
            accumulator = 0;
            for (int kernelX = 0; kernelX < gaussianKernel.length; kernelX++) {
                for (int kernelY = 0; kernelY < gaussianKernel.length; kernelY++) {
                    xCoordinate = shortenFactory.shorten(x + kernelX - offset, 0, imageWidth - 1);
                    yCoordinate = shortenFactory.shorten(y + kernelY - offset, 0,  imageHeight - 1);
                    accumulator += pixels.get(Pair.of(xCoordinate,yCoordinate)) * gaussianKernel[kernelX][kernelY];
                }
            }
            blurredResult.put(currentCoordinates, accumulator);
        }
    }
}
