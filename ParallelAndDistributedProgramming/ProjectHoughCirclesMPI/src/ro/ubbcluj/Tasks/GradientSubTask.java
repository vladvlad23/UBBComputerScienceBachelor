package ro.ubbcluj.Tasks;

import org.apache.commons.lang3.tuple.Pair;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GradientSubTask implements Serializable {

    private final int xStart;
    private final int xEnd;
    private Map<Pair<Integer, Integer>, Integer> blurredImage;
    private final int imageHeight;
    private final int imageWidth;

    public GradientSubTask(int x, int xEnd, Map<Pair<Integer, Integer>, Integer> blurredImage, int imageHeight, int imageWidth) {
        this.xStart = x;
        this.xEnd = xEnd;
        this.blurredImage = blurredImage;
        this.imageHeight = imageHeight;
        this.imageWidth = imageWidth;
    }

    public Object[] run(){
        Map<Pair<Integer, Integer>, Double> gradientResult = new ConcurrentHashMap<>();
        Map<Pair<Integer, Integer>, Double> gradientDirection = new ConcurrentHashMap<>();
        for(int x = xStart;x<xEnd;x++) {
            for (int y = 0; y < imageHeight; y++) {
                if (0 < x && x < (imageWidth - 1) && 0 < y && (y < imageHeight - 1)) {
                    if(blurredImage.containsKey(Pair.of(x+1,y)) && blurredImage.containsKey(Pair.of(x-1,y))) {
                        int xGradient = blurredImage.get(Pair.of(x + 1, y)) - blurredImage.get(Pair.of(x - 1, y));
                        int yGradient = blurredImage.get(Pair.of(x, y + 1)) - blurredImage.get(Pair.of(x, y - 1));
                        gradientResult.put(Pair.of(x, y), Math.sqrt(Math.pow(xGradient, 2) + Math.pow(yGradient, 2)));
                        gradientDirection.put(Pair.of(x, y), Math.atan2(yGradient, xGradient));
                    }
                }
            }
        }
        Object [] returnArray = new Map[2];
        returnArray[0] = gradientResult;
        returnArray[1] = gradientDirection;
        return returnArray;
    }
}
