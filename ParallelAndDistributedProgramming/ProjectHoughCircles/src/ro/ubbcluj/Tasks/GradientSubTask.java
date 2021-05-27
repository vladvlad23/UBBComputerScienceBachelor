package ro.ubbcluj.Tasks;

import org.apache.commons.lang3.tuple.Pair;

import java.util.Map;

public class GradientSubTask implements Runnable {

    private final int x;
    private Map<Pair<Integer, Integer>, Integer> blurredImage;
    private Map<Pair<Integer, Integer>, Double> gradientResult;
    private Map<Pair<Integer, Integer>, Double> gradientDirection;
    private final int imageHeight;
    private final int imageWidth;

    public GradientSubTask(int x, Map<Pair<Integer, Integer>, Integer> blurredImage, Map<Pair<Integer, Integer>, Double> gradientResult, Map<Pair<Integer, Integer>, Double> gradientDirection, int imageHeight, int imageWidth) {
        this.x = x;
        this.blurredImage = blurredImage;
        this.gradientResult = gradientResult;
        this.gradientDirection = gradientDirection;
        this.imageHeight = imageHeight;
        this.imageWidth = imageWidth;
    }

    @Override
    public void run(){
        for (int y = 0; y < imageHeight; y++) {
            if (0 < x && x < (imageWidth - 1) && 0 < y && (y < imageHeight - 1)) {
                int xGradient = blurredImage.get(Pair.of(x + 1, y)) - blurredImage.get(Pair.of(x - 1, y));
                int yGradient = blurredImage.get(Pair.of(x, y + 1)) - blurredImage.get(Pair.of(x, y - 1));
                gradientResult.put(Pair.of(x, y), Math.sqrt(Math.pow(xGradient, 2) + Math.pow(yGradient, 2)));
                gradientDirection.put(Pair.of(x, y), Math.atan2(yGradient, xGradient));
            }
        }
    }
}
