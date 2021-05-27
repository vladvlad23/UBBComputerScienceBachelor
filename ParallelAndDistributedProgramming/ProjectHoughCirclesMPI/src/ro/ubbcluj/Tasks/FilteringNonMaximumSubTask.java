package ro.ubbcluj.Tasks;

import org.apache.commons.lang3.tuple.Pair;

import java.io.Serializable;
import java.util.Map;

public class FilteringNonMaximumSubTask implements Serializable {

    private final int xStart;
    private final int xEnd;
    private final int imageHeight;
    private Map<Pair<Integer,Integer>, Double> gradientResult;
    private Map<Pair<Integer,Integer>, Double> gradientDirection;

    public FilteringNonMaximumSubTask(int xStart, int xEnd, int imageHeight, Map<Pair<Integer, Integer>, Double> gradientResult, Map<Pair<Integer, Integer>, Double> gradientDirection) {
        this.xStart = xStart;
        this.xEnd = xEnd;
        this.imageHeight = imageHeight;
        this.gradientResult = gradientResult;
        this.gradientDirection = gradientDirection;
    }

    public Object[] run() {
        for (int x = xStart; x < xEnd; x++) {
            for (int y = 2; y < imageHeight - 2; y++) {

                Pair<Integer, Integer> coordinates = Pair.of(x, y);
                double coordinatesDirectionValue = gradientDirection.get(coordinates);
                double angle = coordinatesDirectionValue >= 0 ? coordinatesDirectionValue : coordinatesDirectionValue + Math.PI;
                double roundAngle = Math.round(angle / (Math.PI / 4));

                double coordinatesGradientValue = gradientResult.get(coordinates);
                if(gradientResult.containsKey(Pair.of(x+1,y)) && gradientDirection.containsKey(Pair.of(x-1,y))) {
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
        Object [] returnArray = new Map[2];
        returnArray[0] = gradientResult;
        returnArray[1] = gradientDirection;
        return returnArray;
    }
}
