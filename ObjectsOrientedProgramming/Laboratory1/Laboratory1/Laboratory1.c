#include <stdio.h>

/*
   *** a.Generate all the prime numbers smaller than a given natural number n.
   *** b.Given a vector/array of numbers, find the longest increasing contiguous subsequence, such the sum
		of that any 2 consecutive elements is a prime number
*/


/*
We are going to use the sieve of Erathostene in order to solve the first requirement. 
*/
void erathosteneSieve(int *values, int n)
{
	int i, j;
	for (i = 0; i < n; i++) //Setting all numbers as prime for now
		values[i] = 1;
	for (i = 2; i*i<=n;i++)
		if (values[i] == 1)
		{
			for (j = i * i; j < n; j += i)
			{
				values[j] = 0;
			}
		}
}

void printPrimes(int *values, int n)
{
	int i;
	printf("The following numbers are primes smaller than %d", n);
	for (i = 2; i < n; i++)
	{
		if (values[i] == 1)
			printf(" ", values[i]);

	}
}


int main()
{
	int values[1000], n;
	scanf("%d", &n);
	erathosteneSieve(values, n);
	printPrimes(values, n);


}