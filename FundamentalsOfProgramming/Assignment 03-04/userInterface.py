from addOperations import *
from modifyOperations import *
from characteristicOperations import *
from printOperations import *
from filterOperations import *
from undoOperations import undoLast,createUndoList

def list(undoList, numbers, userInput):
    '''

    Function will call the required list functions for each scenario.
    1. if the userInput has only 1 word, it will call listAllNumber(numbers)
    2. if the userInput has 2 words, it will try to call the listModulo function
        or the list real function according to the string at the first position of
        userInput list

    Any errors that shall occur will be caught by a try except block and a print message will
    appear accordingly

    '''

    if len(userInput) == 1:
        listAllNumbers(numbers)
    elif userInput[1] == "modulo":  # if the second word is 'modulo'
        try:
            listModulo(numbers, userInput[2], userInput[3])  # call the function with the 3rd and 4th word
        except IndexError:
            print("Index out of range")
    elif userInput[1] == "real":  # if the second word is real
        try:

            start = int(userInput[2]) - 1  # natural language sees lists as starting at 1, so we decrement
            finish = int(userInput[4])

        except (ValueError,IndexError):
            print("Invalid input at listReal")
            return

        try:  # if start and finish are integers, try to list all real numbers in the given range
            listReal(numbers, start, finish)
        except IndexError:
            print("Index out of range")
            return


def add(undoList, numberList, userInput):
    '''
    Function will receive a list of complexNumbers and the userInput of the user
    The user input should be either of form "add <number>" or of form "insert <number> at <position>"
    The input will be validated. Function will throw a message if input is invalid
    '''
    firstWord = userInput[0]

    if len(userInput) == 2 and firstWord == 'add':  # First form
        try:
            complexNumber = turnStringToComplexNumber(userInput[1])
            undoList.append({"operation": "add", "position": len(numberList)})
            addPerform(undoList,numberList, complexNumber)
        except ValueError:
            print("invalid input at add")
            return
    else:
        print("Invalid input at add function")

def insert(undoList,numberList, userInput):
    '''
    Function will receive a numberList and an array of words inputted by user. It
    validate the input for the insert function and if no exceptions are met
    it will call the insert function

    userInput[1] should be the value
    userInput[3] should be the position
    '''

    if len(userInput) != 4:  # if the number of words given by the user doesn't match the format
        print(len(userInput))
        print("Invalid input at insert")
        return
    try:
        complexNumber = turnStringToComplexNumber(userInput[1])
        position = int(userInput[3])
        undoList.append({"operation":"add","position":position-1})
        insertPerform(undoList,numberList, complexNumber, position)
    except ValueError:
        print("Invalid input at insert")
        return

def remove(undoList,numbers ,userInput):
    '''
    Function will receive a numberList and a list of words inputted by the user which will be
    validated to the following form:
    1.remove <position> (for which the removePerform(numbers,position) will be called
    2.remove <startPosition> to <endPosition> (for which it will call the same function  but
    with the additional parameter of endPosition

    '''

    if len(userInput) == 2:
        try:
            position =int(userInput[1])
            undoList.append({"operation": "remove", "value": numbers[position], "position": position-1})
            removePerform(undoList,numbers ,position-1)
        except ValueError:
            print("Invalid input remove")
            return

    elif len(userInput)==4 and userInput[2]=='to':
        try:
            start = int(userInput[1])
            end = int(userInput[3])
            removedNumbers = numbers[start-1:end]
            undoList.append({"operation": "remove", "values": removedNumbers, "firstPosition": start,
                             "lastPosition": end})
            removePerform(undoList,numbers ,start ,end)

        except (ValueError,IndexError):
            print("Invalid input remove")
    else:
        print("Invalid input remove")



def replace(undoList, numbers ,userInput):
    '''
    Function will validate the input for the following format: replace <value> with <value>
    and it will print exception messages accordingly

    '''

    if len(userInput)!=4 or userInput[2]!='with':
        print("Invalid replace input")
        return

    if not (isComplexNumber(userInput[1]) or isComplexNumber(userInput[3])):
        print("Invalid Input at replace")
        return

    try:

        firstNumber = turnStringToComplexNumber(userInput[1])
        secondNumber = turnStringToComplexNumber(userInput[3])

        undoList.append({"operation":"replace","old":firstNumber,"new":secondNumber})

        replacePerform(numbers,turnStringToComplexNumber(userInput[1]),
                       turnStringToComplexNumber(userInput[3]))

    except ValueError:
        print("invalid replace input")


def sum(undoList, numbers, userInput):
    '''
    Function validates data and calls the sumPerform function.
    userInput should have a length of 4 and position 1 and 3 should be integers while 2 should be 'to'

    '''
    try:
        sequenceStart = int(userInput[1]) - 1
        sequenceEnd = int(userInput[3])
    except ValueError:
        print("Invalid input in sum")
        return

    if userInput[2] != 'to':
        print("Invalid input")
        return

    sumPerform(numbers, sequenceStart, sequenceEnd)


def product(undoList, numbers, userInput):
    '''

    Function validates data and calls the productPerform function.
    userInput should have a length of 4 and position 1 and 3 should be integers while 2 should be 'to'

    :param undoList:
    :param numbers:
    :param userInput:
    :return:
    '''


    try:
        sequenceStart = int(userInput[1]) - 1
        sequenceEnd = int(userInput[3])
    except ValueError:
        print("Invalid input in product")
        return

    if userInput[2] != 'to':
        print("Invalid input")
        return

    productPerform(numbers, sequenceStart, sequenceEnd)



def filter(undoList, numbers, userInput):
    '''

    Function will call the required filter operations from the filterOperations module with the following form
    if the 2nd word is real, it means filter all real numbers
    else, we check the rest of the input and if it's ok, then call the function with the operation and the value

    :param undoList:
    :param numbers:
    :param userInput:
    :return:


    '''
    parameterLength = len(userInput)

    validModuloOperations = ["<",">","="]


    if parameterLength == 2 and userInput[1]=='real':
        filterReal(undoList, numbers)
    elif parameterLength == 4 and userInput[1]=='modulo' and userInput[2] in validModuloOperations:
        try:
            filterModulo(undoList, numbers,userInput[2],int(userInput[3]))
        except ValueError:
            print("Invalid input at filter")
    else:
        print("Invalid input at filter")

def undo(undoList,numbers,userInput):
    '''

    :param undoList:
    :param numbers:
    :param userInput:
    :return: nothing

    Functions calls the undoLast function with parameters undoList and numbers

    '''

    undoLast(undoList,numbers)

def getInput():
    '''
    Function gets input from user
    '''

    userInput = input("Please give command.For help, type 'help'. ")

    return userInput


def parseInput(undoList, userInput ,numbers):

    '''
    The function will receive raw string of user data and it will split it into words
    It will also call each function according to the first word of the input with the parameters
    being the number list and the userInput as well (for them to parse the rest of the input)
    '''

    parsedUserInput = userInput.split(" ")
    inputLength = len(parsedUserInput)


    commandOptions = {"add" :add ,"insert" :insert ,"remove" :remove,
                      "replace" :replace ,"list" :list ,"sum" :sum ,"product" :product,
                      "filter":filter,"undo":undo}


    if parsedUserInput[0] in commandOptions:
        commandOptions.get(parsedUserInput[0])(undoList,numbers,parsedUserInput)
    elif parsedUserInput[0] == 'help':
        help = "Commands are: \n\
                1. add <number> = appends a number at the end of the list \n\
                2. insert<number> at <position> = inserts given number at the index <position> \n\
                3. remove<position>- removes the number found at the index <position> \n\
                4. remove<startPosition> to <endPosition> - removes ALL the numbers from the start position to end position \n\
                5. replace<oldNumber> with <newNumber> - replaces ALL occurences of the given number with the new number \n\
                6. list - lists all numbers in the list \n\
                7. list real<startPosition> to <endPosition> = lists all real numbers between the start and end \n\
                8. list modulo[<|=|>]<number> -lists all the numbers with modulo bigger/equal/smaller than <number> \n\
                9. sum <startPosition> to <endPosition> - writes the sum of all the elements in the range (startPosition,endPosition \n\
                10.product<startPosition> to <endPosition> - writes the product of all the elements in the range ( \n\
                11.exit - exits the program \n\
                12.undo - undo the previous operations "
        print(help)
    elif parsedUserInput[0]== 'exit':
        return True
    else:
        print("Invalid Input at parsing");
    return False

