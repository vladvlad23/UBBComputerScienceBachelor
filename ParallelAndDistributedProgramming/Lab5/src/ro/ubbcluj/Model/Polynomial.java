package ro.ubbcluj.Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Polynomial {

    private List<Integer> coefficients;
    private int degree;

    public Polynomial(List<Integer> coefficients) {
        this.coefficients = coefficients;
        this.degree = coefficients.size();
    }

    public Polynomial(int degree) {
        this.degree = degree;
        this.coefficients = new java.util.ArrayList<>(Collections.nCopies(degree, 0));

        Random random = new Random();
        this.coefficients = this.coefficients.stream().map(element -> random.nextInt(10)).collect(Collectors.toList());

    }

    public List<Integer> getCoefficients() {
        return coefficients;
    }

    public void setCoefficients(List<Integer> coefficients) {
        this.coefficients = coefficients;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("");
        builder.append("Polynomial: ");
        IntStream
                .range(0, coefficients.size())
                .filter(index -> coefficients.get(index) != 0)
                .forEach(index -> builder
                        .append((coefficients.get(index) > 0 && index > 0) ? "+" : "")
                        .append(coefficients.get(index))
                        .append((index == 1) ? "x" : "")
                        .append((index > 1) ? "x^" : "")
                        .append((index > 1) ? index : ""));
        return builder.toString();

    }

    public int getDegree() {
        return degree;
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }
}
