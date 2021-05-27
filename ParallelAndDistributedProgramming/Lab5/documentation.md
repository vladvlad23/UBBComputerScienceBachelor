# Parallelizing techniques

## Requirement
    The goal of this lab is to implement a simple but non-trivial parallel algorithm.
    Perform the multiplication of 2 polynomials. Use both the regular O(n2) algorithm and the Karatsuba algorithm, and each in both the sequencial 
    form and a parallelized form. Compare the 4 variants.

## Computer Specification

    * CPU: Intel Core i7
    * RAM: 16 GB
    * System type: 64-bit

### Regular polynomial multiplication

* Complexity: O(n^2)
* Step  1:  Multiply each element of the first polynomial with the second.

### Karatsuba algorithm

* Complexity:  O(n^log3)
* Use divide and conquer analogous to the algorithm presented for the multiplication of 2 numbers

## Performed tests


Used for 100,700,1000


regular - 19,48,100


karatsuba - 54,94,270


regular paralel = 15,60,114


karatsuba paralel = 1,29,63




## Conclusion
Karatsuba is superior when using divide et impera. Using parallel algorithms to profit of the fact we have threads is paramount for performance in critical programs.