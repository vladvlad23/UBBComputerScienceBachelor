import numpy
import matplotlib.pyplot as plt
import re
import random


def firstRequirement():
    print("Please choose your distro")
    print("1. LogNormal")
    print("2. Normal")
    x = None
    start = None
    end = None
    userInput = int(input(">>"))

    if userInput == 1:
        start = int(input("Please give me the start of the interval"))
        end = int(input("Please give me the end of the interval"))

        mean = (start+end)//2

        x = numpy.random.lognormal(mean, size=1000)


        sigma = 1
        # plt.hist(x)

        #pdf for log distribution

        count, bins, ignored = plt.hist(x, 100, density=True, align='mid')


        x = numpy.linspace(min(bins), max(bins), 10000) # Returns 10000 evenly spaced numbers over min and max. If i don't do this, they're all over because they're not ordered. Also, the docs "approve" it so...
        pdf = numpy.exp(-(numpy.log(x) - mean) ** 2 / (2 * sigma ** 2)) / (x * sigma * numpy.sqrt(2 * numpy.pi))

        plt.plot(x, pdf, linewidth=2, color='r')
        plt.axis('tight')
        plt.show()


    elif userInput == 2:
        start = int(input("Please give me the start of the interval"))
        end = int(input("Please give me the end of the interval"))

        # chose the standard deviation as 1
        mean = (start + end) // 2
        x = numpy.random.normal(mean, size=100000)


        sigma = 1
        count, bins, ignored = plt.hist(x, 30, density=True)

        #pdf for gaussian distribution
        pdf = 1 / (sigma * numpy.sqrt(2 * numpy.pi)) *  numpy.exp(- (bins - mean) ** 2 / (2 * sigma ** 2))


        plt.plot(bins, pdf,
                 linewidth=2, color='r')
        plt.show()



    else:
        print("That doesn't work. Sorry")
        exit()


def addRandomRules(text):
    hexa = numpy.arange(0, 17, 1).tolist()
    code = {}
    setOfLetters = set()
    for sentence in text:
        for word in sentence:
            newWord = list(word)
            for letter in newWord:
                setOfLetters.add(letter)

    while len(setOfLetters) > 0 and len(hexa) > 0:
        randomHexa = random.choice(hexa)
        randomLetter = random.choice(list(setOfLetters))
        hexa.remove(randomHexa)
        setOfLetters.remove(randomLetter)
        code[randomLetter] = randomHexa

    return code

def testSentence(tempOperands, operator, result):
    if operator=="+":
        x = sum(tempOperands)
        y = result
        return sum(tempOperands) != result
    elif operator=="-":
        return tempOperands[0] - sum(tempOperands[1:]) == result #subtract from first element all the others
    elif operator=="*":
        return numpy.prod(tempOperands) == result
    elif operator =="/":
        return tempOperands[0] // (numpy.prod(tempOperands)) == result
    return False

def turnOperandsToNumber(text,code):
    resultingOperands = []
    for sentence in text:
        tempSentence = []
        for operand in sentence:
            tempWord = operand
            for i in range(len(operand)):
                tempWord = tempWord.replace(operand[i],str(code[operand[i]]))
            tempSentence.append(int(tempWord))
        resultingOperands.append(tempSentence)

    return resultingOperands

def turnResultsToNumber(results,code):
    resultingResults = []
    for result in results:
        tempWord = result
        for i in range(len(result)):
            tempWord = tempWord.replace(result[i],str(code[result[i]]))
        resultingResults.append(int(tempWord))

    return resultingResults




def testCode(code,operands,operators,results):


    numberOfOperations = len(results)
    tempOperands = turnOperandsToNumber(operands, code)
    tempResults = turnResultsToNumber(results,code)


    for index in range(numberOfOperations):
        if testSentence(tempOperands[index],operators[index],tempResults[index]) is not True:
            return False


    print(code)
    print()
    print()


    print(operands)
    print(tempOperands)

    print()
    print()


    print(results)
    print(tempResults)

    return True


def secondRequirement():
    operands = []
    operators = []
    results = []
    allWords = []


    file = open("hexaInput.txt","r")
    for line in file.read().splitlines():
        if "+" in line:
            operators.append("+")
        elif "-" in line:
            operators.append("-")
        elif "*" in line:
            operators.append("*")
        elif "/" in line:
            operators.append("/")

        splittedString = re.split('[+-/*=]',line)
        allWords.append(splittedString)
        operands.append(splittedString[:-1])
        results.append(splittedString[-1])

    tries = int(input("Hello, friend. How many times would you like to try?"))

    for every in range(tries):
        code = addRandomRules(allWords)
        if testCode(code,operands,operators,results):
            print("Damn, this random stuff actually worked")
            break

    print("Nope, no luck. Try again")

secondRequirement()
