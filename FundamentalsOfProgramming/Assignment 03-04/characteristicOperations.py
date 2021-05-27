from printOperations import printComplexNumber

def productPerform(numbers, sequenceStart, sequenceEnd):
    product = 1

    for i in range(sequenceStart, sequenceEnd):
        product *= numbers[i]

    printComplexNumber(product)

def sumPerform(numbers, sequenceStart, sequenceEnd):
    sum = 0

    for i in range(sequenceStart, sequenceEnd):
        sum += numbers[i]

    printComplexNumber(sum)

