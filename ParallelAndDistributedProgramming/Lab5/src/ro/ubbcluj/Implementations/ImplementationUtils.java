package ro.ubbcluj.Implementations;

import ro.ubbcluj.Model.Polynomial;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import java.util.concurrent.Callable;


public class ImplementationUtils {

    private interface MyOperation {
        int applyOperation(int p1, int p2);
    }

    private static final MyOperation addition = Integer::sum;
    private static final MyOperation subtraction = (p1, p2) -> p1 - p2;


    public static Polynomial simpleSequentialMultiplication(Polynomial firstPolynomial, Polynomial secondPolynomial) {
        List<Integer> firstPolynomialCoefficients = firstPolynomial.getCoefficients();
        List<Integer> secondPolynomialCoefficients = secondPolynomial.getCoefficients();

        List<Integer> resultingCoefficients = new java.util.ArrayList<>(Collections.nCopies(firstPolynomial.getDegree() + secondPolynomial.getDegree() + 1, 0));
        for (int i = 0; i < firstPolynomial.getDegree(); i++) {
            for (int j = 0; j < secondPolynomial.getDegree(); j++) {
                resultingCoefficients.set
                        (i + j,
                                resultingCoefficients.get(i + j) + firstPolynomialCoefficients.get(i) * secondPolynomialCoefficients.get(j));
            }
        }
        return new Polynomial(resultingCoefficients);
    }

    public static Polynomial simpleParallelizedMultiplication(Polynomial firstPolynomial, Polynomial secondPolynomial, int threadNumber) throws InterruptedException {
        List<Integer> firstPolynomialCoefficients = firstPolynomial.getCoefficients();
        List<Integer> secondPolynomialCoefficients = secondPolynomial.getCoefficients();
        List<Integer> resultingCoefficients = new java.util.ArrayList<>(Collections.nCopies(firstPolynomial.getDegree() + secondPolynomial.getDegree() + 1, 0));

        ThreadPoolExecutor threadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(threadNumber);
        int increment = (resultingCoefficients.size()) / threadNumber;

        for (int i = 0; i < resultingCoefficients.size(); i += increment) {
            SimpleMultiplicationTask multiplication = new SimpleMultiplicationTask(i, increment, firstPolynomialCoefficients, secondPolynomialCoefficients, resultingCoefficients);
            threadPool.execute(multiplication);
        }

        threadPool.shutdown();
        threadPool.awaitTermination(10, TimeUnit.SECONDS);

        return new Polynomial(resultingCoefficients);
    }

    public static Polynomial karatsubaSequentialMultiplication(Polynomial firstPolynomial, Polynomial secondPolynomial) {
        if (firstPolynomial.getDegree() < 3 || secondPolynomial.getDegree() < 3) {
            return simpleSequentialMultiplication(firstPolynomial, secondPolynomial);
        }

        List<Integer> firstPolynomialCoefficients = firstPolynomial.getCoefficients();
        List<Integer> secondPolynomialCoefficients = secondPolynomial.getCoefficients();

        int splitPoint = Math.max(firstPolynomial.getDegree(), secondPolynomial.getDegree()) / 2;
        Polynomial lowHalfFirst = new Polynomial(firstPolynomialCoefficients.subList(0, splitPoint));
        Polynomial highHalfFirst = new Polynomial(firstPolynomialCoefficients.subList(splitPoint, firstPolynomialCoefficients.size()));

        Polynomial lowHalfSecond = new Polynomial(secondPolynomialCoefficients.subList(0, splitPoint));
        Polynomial highHalfSecond = new Polynomial(secondPolynomialCoefficients.subList(splitPoint, secondPolynomialCoefficients.size()));

        Polynomial firstResult = karatsubaSequentialMultiplication(lowHalfFirst, lowHalfSecond);
        Polynomial secondResult = karatsubaSequentialMultiplication(operationPolynomials(lowHalfFirst, highHalfFirst, addition), operationPolynomials(lowHalfSecond, highHalfSecond, addition));
        Polynomial thirdResult = karatsubaSequentialMultiplication(highHalfFirst, highHalfSecond);

        //"formula" says 2*10^n + 3-2-1 * 10^n/2 + 3. Instead of 10^n, i add 0s
        List<Integer> firstResultCoefficients = firstResult.getCoefficients();
        List<Integer> secondResultCoefficients = operationPolynomials(
                operationPolynomials(secondResult, thirdResult, subtraction),
                firstResult,
                subtraction
        ).getCoefficients();
        List<Integer> thirdResultCoefficients = thirdResult.getCoefficients();

        Collections.nCopies(splitPoint * 2, 0).stream().forEach(extra -> thirdResultCoefficients.add(0, extra));
        Collections.nCopies(splitPoint, 0).stream().forEach(extra -> secondResultCoefficients.add(0, extra));
        return operationPolynomials(
                operationPolynomials(
                        new Polynomial(firstResultCoefficients),
                        new Polynomial(secondResultCoefficients),
                        addition
                ),
                new Polynomial(thirdResultCoefficients),
                addition
        );
    }

    public static Polynomial karatsubaParallelizedMultiplication(Polynomial firstPolynomial, Polynomial secondPolynomial) throws Exception {
        if (firstPolynomial.getDegree() < 3 || secondPolynomial.getDegree() < 3) {
            return simpleSequentialMultiplication(firstPolynomial, secondPolynomial);
        }

        List<Integer> firstPolynomialCoefficients = firstPolynomial.getCoefficients();
        List<Integer> secondPolynomialCoefficients = secondPolynomial.getCoefficients();

        int splitPoint = Math.max(firstPolynomial.getDegree(), secondPolynomial.getDegree()) / 2;
        Polynomial lowHalfFirst = new Polynomial(firstPolynomialCoefficients.subList(0, splitPoint));
        Polynomial highHalfFirst = new Polynomial(firstPolynomialCoefficients.subList(splitPoint, firstPolynomialCoefficients.size()));

        Polynomial lowHalfSecond = new Polynomial(secondPolynomialCoefficients.subList(0, splitPoint));
        Polynomial highHalfSecond = new Polynomial(secondPolynomialCoefficients.subList(splitPoint, secondPolynomialCoefficients.size()));

        ThreadPoolExecutor threadPool = (ThreadPoolExecutor) Executors.newCachedThreadPool();

        Callable<Polynomial> firstResult = () -> karatsubaSequentialMultiplication(lowHalfFirst, lowHalfSecond);
        Callable<Polynomial> secondResult = () -> karatsubaSequentialMultiplication(operationPolynomials(lowHalfFirst, highHalfFirst, addition), operationPolynomials(lowHalfSecond, highHalfSecond, addition));
        Callable<Polynomial> thirdResult = () -> karatsubaSequentialMultiplication(highHalfFirst, highHalfSecond);

        threadPool.submit(firstResult);
        threadPool.submit(secondResult);
        threadPool.submit(thirdResult);

        threadPool.shutdown();
        threadPool.awaitTermination(5,TimeUnit.SECONDS);


        List<Integer> firstResultCoefficients = firstResult.call().getCoefficients();
        List<Integer> secondResultCoefficients = operationPolynomials(
                operationPolynomials(secondResult.call(), thirdResult.call(), subtraction),
                firstResult.call(),
                subtraction
        ).getCoefficients();
        List<Integer> thirdResultCoefficients = thirdResult.call().getCoefficients();

        Collections.nCopies(splitPoint * 2, 0).stream().forEach(extra -> thirdResultCoefficients.add(0, extra));
        Collections.nCopies(splitPoint, 0).stream().forEach(extra -> secondResultCoefficients.add(0, extra));
        return operationPolynomials(
                operationPolynomials(
                        new Polynomial(firstResultCoefficients),
                        new Polynomial(secondResultCoefficients),
                        addition
                ),
                new Polynomial(thirdResultCoefficients),
                addition
        );

    }

    private static Polynomial operationPolynomials(Polynomial firstPolynomial, Polynomial secondPolynomial, MyOperation operation) {
        int smallerDegree = Math.min(firstPolynomial.getDegree(), secondPolynomial.getDegree());
        int greaterDegree = Math.max(firstPolynomial.getDegree(), secondPolynomial.getDegree());

        List<Integer> resultCoefficients = new ArrayList<>((Collections.nCopies(greaterDegree, 0)));
        List<Integer> firstPolynomialCoefficients = firstPolynomial.getCoefficients();
        List<Integer> secondPolynomialCoefficients = secondPolynomial.getCoefficients();

        IntStream
                .range(0, smallerDegree)
                .forEach(index -> resultCoefficients.set(index, operation.applyOperation(firstPolynomialCoefficients.get(index), secondPolynomialCoefficients.get(index))));


        List<Integer> greaterCoefficients = (firstPolynomial.getDegree() > secondPolynomial.getDegree()) ? firstPolynomialCoefficients : secondPolynomialCoefficients;
        if (firstPolynomial.getDegree() != secondPolynomial.getDegree()) {
            IntStream
                    .range(smallerDegree, greaterDegree)
                    .forEach(index -> resultCoefficients.set(index, greaterCoefficients.get(index)));

        }

        return new Polynomial(resultCoefficients);
    }

}
