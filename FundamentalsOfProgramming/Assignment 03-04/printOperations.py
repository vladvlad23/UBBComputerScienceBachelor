from complexOperations import getImaginary,getReal,modulo,isReal

def listAllNumbers(numbers):
    '''
    Function will receive a list which will be iterated. It will call printComplexNumber for each
    element in the list
    '''
    for i in numbers:
        printComplexNumber(i)


def listReal(numbers, start, finish):
    '''
    Function will call printComplexNumber for each element of the numbers list from the
    index start to the index finish (inclusive). Data is validated
    '''
    for i in range(start, finish):
        if isReal(numbers[i]):
            printComplexNumber(numbers[i])


def printComplexNumber(number):
    '''
    Function will receive a complex number and it will print it using getImaginary(number)
    and getReal(number). It will cover the conflict cases where imaginary part is smaller than 0
    and where the imaginary part or real part is 0 as well and it will append i to the end


    '''

    if getImaginary(number) < 0:  # solving the conflicting case of a+(-b)i
        print(getReal(number), getImaginary(number), "i", sep="")
    elif getImaginary(number) == 0:  # solving the conflict case of form a+0i
        print(getReal(number))
    elif getImaginary(number) == 0:  # solving the conflict case of printing 0+ai
        print(getImaginary(number), "i", sep="")
    else:  # normal case
        print(getReal(number), "+", getImaginary(number), "i", sep="")


def listModulo(numbers, symbol, value):
    '''
    Function will validate the input of symbol and value (symbol = {<,=,>} and value should
    be an integer. It will also iterate and print all numbers of the given value.
    e.g modulo<10 with the help of the modulo(number) function
    '''

    try:  # data validation
        value = int(value)
    except ValueError:
        print("Invalid input at listModulo")
        return
    if symbol == '<':
        for i in numbers:
            if modulo(i) < value:
                printComplexNumber(i)
    elif symbol == '=':
        for i in numbers:
            if modulo(i) == value:
                printComplexNumber(i)
    elif symbol == '>':
        for i in numbers:
            if modulo(i) < value:
                printComplexNumber(i)
    else:  # if symbol isn't <,= or >
        print("invalid symbol")
