package ro.ubbcluj.Tasks;

import org.apache.commons.lang3.tuple.Pair;

import java.util.Map;

public class FilteringSubTask implements Runnable {

    private final int x;
    private final int imageHeight;
    private Map<Pair<Integer,Integer>, Double> gradientResult;
    private Map<Pair<Integer,Integer>, Double> gradientDirection;

    public FilteringSubTask(int x, int imageHeight, Map<Pair<Integer, Integer>, Double> gradientResult, Map<Pair<Integer, Integer>, Double> gradientDirection) {
        this.x = x;
        this.imageHeight = imageHeight;
        this.gradientResult = gradientResult;
        this.gradientDirection = gradientDirection;
    }

    @Override
    public void run(){
        for (int y = 2; y < imageHeight - 2;  y++) {

            Pair<Integer, Integer> coordinates = Pair.of(x, y);
            double coordinatesDirectionValue = gradientDirection.get(coordinates);
            double angle = coordinatesDirectionValue >= 0 ? coordinatesDirectionValue : coordinatesDirectionValue + Math.PI;
            double roundAngle = Math.round(angle / (Math.PI / 4));

            double coordinatesGradientValue = gradientResult.get(coordinates);
            if ((roundAngle == 0 || roundAngle == 4) &&
                    gradientResult.get(Pair.of(x - 1, y)) > coordinatesGradientValue ||
                    gradientResult.get(Pair.of(x + 1, y)) > coordinatesGradientValue ||
                    (roundAngle == 1 &&
                            gradientResult.get(Pair.of(x - 1, y - 1)) > coordinatesGradientValue ||
                            gradientResult.get(Pair.of(x + 1, y + 1)) > coordinatesGradientValue) ||
                    (roundAngle == 2 &&
                            gradientResult.get(Pair.of(x, y - 1)) > coordinatesGradientValue ||
                            gradientResult.get(Pair.of(x, y + 1)) > coordinatesGradientValue) ||
                    (roundAngle == 3 &&
                            gradientResult.get(Pair.of(x + 1, y - 1)) > coordinatesGradientValue ||
                            gradientResult.get(Pair.of(x - 1, y + 1)) > coordinatesGradientValue)
            ) {
                gradientResult.put(coordinates, 0.0);
            }

        }
    }
}
