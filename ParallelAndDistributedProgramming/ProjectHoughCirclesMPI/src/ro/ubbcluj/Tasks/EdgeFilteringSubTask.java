package ro.ubbcluj.Tasks;

import org.apache.commons.lang3.tuple.Pair;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class EdgeFilteringSubTask implements Serializable {
    private final int xStart;
    private final int xEnd;
    private final double high;
    private final int imageHeight;
    private Map<Pair<Integer, Integer>, Double> gradientResult;

    public EdgeFilteringSubTask(int xStart, int xEnd, double high, int imageHeight, Map<Pair<Integer, Integer>, Double> gradientResult) {
        this.xStart = xStart;
        this.xEnd = xEnd;
        this.high = high;
        this.imageHeight = imageHeight;
        this.gradientResult = gradientResult;
    }

    public Object[] run() {
        Set<Pair<Integer, Integer>> edgesToBeKept = new HashSet<>();
        Object[] returnObject = new Object[1];
        for (int x = xStart; x < xEnd; x++) {
            for (int y = 2; y < imageHeight - 2; y++) {
                Pair<Integer, Integer> coordinates = Pair.of(x, y);
                if (gradientResult.containsKey(coordinates)) {
                    if (gradientResult.get(coordinates) > high) {
                        edgesToBeKept.add(coordinates);
                    }
                }
            }
        }
        returnObject[0] = edgesToBeKept;
        return returnObject;
    }
}
