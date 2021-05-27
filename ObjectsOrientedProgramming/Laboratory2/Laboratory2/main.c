#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>
#include <assert.h>

/*
   *** a.Generate all the prime numbers smaller than a given natural number n.
   *** b.Given a vector/array of numbers, find the longest increasing contiguous subsequence, such the sum
		of that any 2 consecutive elements is a prime number

*/

int isPrime(int n)
{
	int p = 2;
	if (n%p == 0)
		return 0;
	for (p = 3; p*p <= n; p += 2)
	{
		if (n%p == 0)
			return 0;
	}
	return 1;
}

void printPrimes(int *values, int numerOfElements)
{
	int i;
	printf("\nThe following numbers are primes smaller than %d:\n", numerOfElements);
	for (i = 2; i < numerOfElements; i++)
	{
		if (values[i] == 1)
			printf(" %d ",i);

	}
	printf("\n");
}

void erathosteneSieve(int *values, int numberOfElements)
{
	int i, j;
	if (numberOfElements == 0)
	{
		printf("no such subsequence");
		return;
	}
	for (i = 0; i < numberOfElements; i++) //Setting all numbers as prime for now
		values[i] = 1;
	for (i = 2; i*i <= numberOfElements; i++) // going to set all numbers that are not prime which follow from the prime ones
		if (values[i] == 1)
		{
			for (j = i * i; j < numberOfElements; j += i)
			{
				values[j] = 0;
			}
		}
}

void printSecondRequirement(int *values, int numberOfElements)
{
	int i;
	printf("The longest increasing contiguous subsequence in the given array is: \n");
	for (i = 0; i < numberOfElements; i++)
	{
		printf(" %d ", values[i]);
	}
	printf("\n");


}

void secondRequirement(int *results,int *numberOfResults,int *values, int numberOfElements)
{
	/*
		input:
			#values = int array with n values
			#numberOfElements = the number of elements in the previous array
		output:
			#results = int array with the longest increasing contiguos sequence satisfying the given condition
	*/
	int i, finalStartOfSequence, finalNumberOfElements=0, temporaryStartOfSequence, temporaryNumberOfElements;
	for (i = 0; i < numberOfElements-1; i++)
	{
		if (isPrime(values[i] + values[i + 1]) && values[i]<values[i+1])
		{
			temporaryStartOfSequence = i;
			temporaryNumberOfElements = 1;
			while (i + 1 < numberOfElements && isPrime(values[i] + values[i + 1]) && values[i]<values[i+1])
			{
				temporaryNumberOfElements++;
				i++;
			}
			if (temporaryNumberOfElements > finalNumberOfElements)
			{
				finalStartOfSequence = temporaryStartOfSequence;
				finalNumberOfElements = temporaryNumberOfElements;
			}
		}
	}
	*numberOfResults = finalNumberOfElements;
	for (i = 0; i < finalNumberOfElements; i++)
	{
		results[i] = values[i + finalStartOfSequence];
	}
}

int testFirstRequirement()
{
	int inputArray[] = { 2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41,43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167,173, 179, 181, 191, 193, 197, 199, 211, 223, 227, 229, 233, 239,
		241, 251, 257, 263, 269, 271, 277, 281, 283, 293, 307, 311, 313,
		317, 331, 337, 347, 349, 353, 359, 367, 373, 379, 383, 389, 397,
		401, 409, 419, 421, 431, 433, 439, 443, 449, 457, 461, 463, 467,
		479, 487, 491, 499, 503, 509, 521, 523, 541, 547, 557, 563, 569,
		571, 577, 587, 593, 599, 601, 607, 613, 617, 619, 631, 641, 643,
		647, 653, 659, 661, 673, 677, 683, 691, 701, 709, 719, 727, 733,
		739, 743, 751, 757, 761, 769, 773, 787, 797, 809, 811, 821, 823,
		827, 829, 839, 853, 857, 859, 863, 877, 881, 883, 887, 907, 911, 919, 929, 937, 941, 947, 953, 967, 971, 977, 983, 991, 997};
	
	int testArray[1000], i, j = 0;
	erathosteneSieve(testArray, 1000);
	for (i = 2; i <= 1000; i++)
	{
		if (testArray[i] == 1)
			if (inputArray[j] != i)
				return -1;
			else
			{
				j++;
			}

	}
	return 0;

}


int testSecondRequirement()
{
	int arrayToTestWith[] = { 3,4,3,4,7,11,12,13 };
	int result[100], numberOfElementsInResult;
	secondRequirement(result, &numberOfElementsInResult, arrayToTestWith, 8);
	if (numberOfElementsInResult != 3)
		return 1;
	if (result[0] != 3)
		return 1;
	if (result[1] != 4)
		return 1;
	if (result[2] != 7)
		return 1;

	int secondArrayToTestWith[] = { 3,4,3,4,7,11,12,17,24 };
	secondRequirement(result, &numberOfElementsInResult, secondArrayToTestWith, 9);
	if (numberOfElementsInResult != 4)
	{
		return 1;
	}
	if (result[0] != 11)
		return 1;
	if (result[1] != 12)
		return 1;
	if (result[2] != 17)
		return 1;
	if (result[3] != 24)
		return 1;

	return 0;
	
}

void thirdRequirement(int *primesArray, int numberOfPrimes)
{
	/*
	We are going to iterate through all the numbers until we find enough primes.
	*/
	int i = 2, primeCounter = 0;
	if (numberOfPrimes < 1)
		return;

	numberOfPrimes--;
	primesArray[0] = 2;
	i = 3;
	while (numberOfPrimes >= 0)
	{
		if (isPrime(i) == 1)
		{
			primesArray[++primeCounter] = i;
			numberOfPrimes--;
		}
		i += 2;
	}
}

int testThirdRequirement()
{
	int primesArray[1000];
	thirdRequirement(primesArray, 1);
	assert(primesArray[0] == 2);
	int secondPrimesArray[1000];
	thirdRequirement(secondPrimesArray, 5);
	assert(secondPrimesArray[0] == 2);
	assert(secondPrimesArray[1] == 3);
	assert(secondPrimesArray[2] == 5);
	assert(secondPrimesArray[3] == 7);
	assert(secondPrimesArray[4] == 11);
	assert(secondPrimesArray[5] == 13);
	return 0;

}

void printThirdRequirement(int *primesArray, int numberOfPrimes)
{
	int i;
	printf("The first %d primes are: ", numberOfPrimes);
	for (i = 0; i < numberOfPrimes; i++)
	{

		printf(" %d ", primesArray[i]);
	}
}

void userInterface()
{
	int command;
	printf("What would you like to do?\n 1.Generate all the prime numbers smaller than a given natural number n.\n");
	printf("2.Given a vector/array of numbers, find the longest increasing contiguous subsequence, such the sum of that any 2 consecutive elements is a prime number\n");
	printf("3.Generate the first n primes");
	scanf("%d", &command);
	if (command == 1)
	{
		int values[100000], numberOfElements;
		printf("Please give the upper limit: ");
		scanf("%d", &numberOfElements);
		erathosteneSieve(values, numberOfElements);
		printPrimes(values, numberOfElements);
	}
	else if (command == 2)
	{
		int numberOfElements, i, values[10000], results[10000];
		printf("Please give the number of elements in the initial array: ");
		scanf("%d", &numberOfElements);

		printf("Please give the array ");
		for (i = 0; i < numberOfElements; i++)
		{
			scanf("%d", &values[i]);
		}
		secondRequirement(results, &numberOfElements, values, numberOfElements);
		printSecondRequirement(results, numberOfElements);
	}
	else if (command == 3)
	{
		int numberOfPrimes, primesArray[1000];
		printf("Please give number of primes: ");
		scanf("%d", &numberOfPrimes);
		printf("\n");
		thirdRequirement(primesArray, numberOfPrimes);
		printThirdRequirement(primesArray, numberOfPrimes);
	}
}


int main()
{

	assert(testFirstRequirement() == 0);
	assert(testSecondRequirement() == 0);
	assert(testThirdRequirement() == 0);
	userInterface();

	return 0;

}