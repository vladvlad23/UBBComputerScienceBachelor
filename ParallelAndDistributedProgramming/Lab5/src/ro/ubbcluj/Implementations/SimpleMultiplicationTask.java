package ro.ubbcluj.Implementations;

import java.util.List;

public class SimpleMultiplicationTask implements Runnable {

    private int start;
    private int numberOfElements;
    private List<Integer> firstPolynomialCoefficients;
    private List<Integer> secondPolynomialCoefficients;
    private List<Integer> resultPolynomialCoefficients;

    public SimpleMultiplicationTask(int start, int numberOfElements, List<Integer> firstPolynomialCoefficients, List<Integer> secondPolynomialCoefficients, List<Integer> resultPolynomialCoefficients) {
        this.start = start;
        this.numberOfElements = numberOfElements;
        this.firstPolynomialCoefficients = firstPolynomialCoefficients;
        this.secondPolynomialCoefficients = secondPolynomialCoefficients;
        this.resultPolynomialCoefficients = resultPolynomialCoefficients;
    }

    @Override
    public void run() {
        for(int i = 0; i< firstPolynomialCoefficients.size();i++){
            for(int j = 0; j < secondPolynomialCoefficients.size(); j++){
                if(i+j > start+numberOfElements){
                    return;
                }
                if(i+j >= start){
                    resultPolynomialCoefficients.set
                            (i + j,
                                    resultPolynomialCoefficients.get(i+j) + firstPolynomialCoefficients.get(i) * secondPolynomialCoefficients.get(j));
                }

            }
        }

    }
}
