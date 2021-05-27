package ro.ubbcluj;

import ro.ubbcluj.Model.Polynomial;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import static ro.ubbcluj.Implementations.ImplementationUtils.*;

public class Main {

    //(x^2+5x+1)(3x^2-10x+15)

    public static void main(String[] args) {
        List<Integer> firstPolynomialCoefficients = List.of(1,5,1);
        List<Integer> secondPolynomialCoefficients = List.of(15,-10,3);
        Polynomial firstPolynomial = new Polynomial(firstPolynomialCoefficients);
        Polynomial secondPolynomial = new Polynomial(secondPolynomialCoefficients);
//        Polynomial firstPolynomial = new Polynomial(1000);
//        Polynomial secondPolynomial = new Polynomial(1000);

        Instant firstTime = Instant.now();
        Polynomial simpleSequential = simpleSequentialMultiplication(firstPolynomial, secondPolynomial);
        Instant secondTime = Instant.now();
        System.out.println("Time spent: " + Duration.between(firstTime, secondTime).toMillis());
        firstTime = Instant.now();
        Polynomial karatsubaSequential = karatsubaSequentialMultiplication(firstPolynomial, secondPolynomial);
        secondTime = Instant.now();
        System.out.println("Time spent: " + Duration.between(firstTime, secondTime).toMillis());

        System.out.println(firstPolynomial.toString());
        System.out.println(secondPolynomial.toString());


        System.out.println("Results: ");
        System.out.println(simpleSequential);
        System.out.println(karatsubaSequential);

        System.out.println("Now for the parallels: ");

        try {
            firstTime = Instant.now();
            Polynomial simpleParallel = simpleParallelizedMultiplication(firstPolynomial, secondPolynomial,4);
            secondTime = Instant.now();
            System.out.println("Time spent: " + Duration.between(firstTime, secondTime).toMillis());
            firstTime = Instant.now();
            Polynomial karatsubaParallel = karatsubaParallelizedMultiplication(firstPolynomial, secondPolynomial);
            secondTime = Instant.now();
            System.out.println("Time spent: " + Duration.between(firstTime, secondTime).toMillis());
            
            
            System.out.println(simpleParallel);
            System.out.println(karatsubaParallel);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
