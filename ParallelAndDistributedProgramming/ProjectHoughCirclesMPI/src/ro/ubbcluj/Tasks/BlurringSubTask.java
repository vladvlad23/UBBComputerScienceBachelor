package ro.ubbcluj.Tasks;

import org.apache.commons.lang3.tuple.Pair;
import ro.ubbcluj.CannyEdgeDetector;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BlurringSubTask implements Serializable {

    private final int xStart;
    private final int xEnd;
    private final Map<Pair<Integer,Integer>, Double> pixels;
    private final int imageWidth;
    private final int imageHeight;
    private final CannyEdgeDetector.ShortenFactory shortenFactory;
    private final int offset;

    double[][] gaussianKernel = {
            {1.0 / 256, 4.0 / 256, 6.0 / 256, 4.0 / 256, 1.0 / 256},
            {4.0 / 256, 16.0 / 256, 24.0 / 256, 16.0 / 256, 4.0 / 256},
            {6.0 / 256, 24.0 / 256, 36.0 / 256, 24.0 / 256, 6.0 / 256},
            {4.0 / 256, 16.0 / 256, 24.0 / 256, 16.0 / 256, 4.0 / 256},
            {1.0 / 256, 4.0 / 256, 6.0 / 256, 4.0 / 256, 1.0 / 256}};


    public BlurringSubTask(int xStart, int xEnd, Map<Pair<Integer, Integer>, Double> pixels, int imageWidth, int imageHeight, CannyEdgeDetector.ShortenFactory shortenFactory) {
        this.xStart = xStart;
        this.xEnd = xEnd;
        this.pixels = pixels;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        this.shortenFactory = shortenFactory;
        this.offset = gaussianKernel.length/2;
    }

    public Map<Pair<Integer, Integer>, Integer> run(){
        int accumulator;
        int yCoordinate;
        int xCoordinate;
        Map<Pair<Integer,Integer>,Integer> blurredResult = new ConcurrentHashMap<>();
        for(int x=xStart;x<xEnd;x++) {
            for (int y = 0; y < imageHeight; y++) {
                Pair<Integer, Integer> currentCoordinates = Pair.of(x, y);
                accumulator = 0;
                for (int kernelX = 0; kernelX < gaussianKernel.length; kernelX++) {
                    for (int kernelY = 0; kernelY < gaussianKernel.length; kernelY++) {
                        xCoordinate = shortenFactory.shorten(x + kernelX - offset, 0, imageWidth - 1);
                        yCoordinate = shortenFactory.shorten(y + kernelY - offset, 0, imageHeight - 1);
                        accumulator += pixels.get(Pair.of(xCoordinate, yCoordinate)) * gaussianKernel[kernelX][kernelY];
                    }
                }
                blurredResult.put(currentCoordinates, accumulator);
            }
        }
        return blurredResult;
    }
}
