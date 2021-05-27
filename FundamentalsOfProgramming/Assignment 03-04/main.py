from userInterface import *

def populateList(numbers):
    '''
    Function will populate the given list of numbers with 10 values of form i + (i+1)j
    where i will be in [0,10)
    '''

    fakeUndoList = []

    for i in range(0,10):
        complexNumber = complex(i,i+1)
        addPerform(fakeUndoList,numbers,complexNumber)

def runMenu():
    '''
    Function will continuously run the menu of the program. It will also initially call the
    populateList function for a number list.
    The menu of the program will continuously get a string from getInput() which will be passed parseInput(a string)
    '''
    numbers = []
    undoList = createUndoList()

    populateList(numbers)
    exit = False
    while not exit:
        userInput = getInput()
        exit = parseInput(undoList,userInput ,numbers)

runMenu()