package ro.ubbcluj.Tasks;

import org.apache.commons.lang3.tuple.Pair;

import java.util.Map;
import java.util.Set;

public class EdgeFilteringSubTask implements Runnable {

    private final int x;
    private final double high;
    private final int imageHeight;
    private Set<Pair<Integer,Integer>> edgesToBeKept;
    private Map<Pair<Integer,Integer>,Double> gradientResult;

    public EdgeFilteringSubTask(int x, double high, int imageHeight, Set<Pair<Integer, Integer>> edgesToBeKept, Map<Pair<Integer, Integer>, Double> gradientResult) {
        this.x = x;
        this.high = high;
        this.imageHeight = imageHeight;
        this.edgesToBeKept = edgesToBeKept;
        this.gradientResult = gradientResult;
    }

    @Override
    public void run() {
        for (int y = 2; y < imageHeight -2; y++) {
            Pair<Integer, Integer> coordinates = Pair.of(x, y);
            if (gradientResult.get(coordinates) > high) {
                edgesToBeKept.add(coordinates);
            }
        }
    }
}
