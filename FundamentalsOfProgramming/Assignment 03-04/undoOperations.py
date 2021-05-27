from modifyOperations import *
from addOperations import addPerform

def undoAdd(undoList,numbers,undoElement):

    '''
    Functions calls the remove function in order to reverse the addition of a previous element at the given position

    :param undoList: the stack of operations
    :param numbers: list of numbers
    :param undoElement: the dictionary containing the "position" key required
    :return:
    '''
    removePerform(undoList,numbers,int(undoElement["position"]))

def undoRemove(undoList,numbers,undoElement):
    '''
    Function will undo the remove operation by inserting the elements back into the list.
    Function will cover both cases where only one element has been removed and where an interval of elements
    have been removed.

    :param undoList: the stack of operations
    :param numbers: the number list
    :param undoElement: dictionary with needed values "lastPosition", "firstPosition" if necessary. Otherwise "position"
    :return:
    '''

    try:
        final = undoElement["lastPosition"]
        start = undoElement["firstPosition"]
        removedValues = undoElement["values"]
        removedValuesIndex = 0
        for i in range(start,final+1):
            addPerform(undoList,numbers,removedValues[removedValuesIndex],i)
            removedValuesIndex+=1
        return
    except KeyError:
        addPerform(undoList,numbers,undoElement["value"],undoElement["position"])

def undoReplace(undoList,numbers,undoElement):
    '''
    Function will undo the replace operation by replacing again the "new" element with the "old" one. This info
    is found in the undoElement dictionary
    :param undoList: the stack of operations
    :param numbers: the list of numbers
    :param undoElement: the dictionary containing the "new" and "old" keys
    :return:
    '''
    for i in range(0,len(numbers)):
        if numbers[i] == undoElement["new"]:
            numbers[i] = undoElement["old"]

def undoFilter(undoList,numbers,undoElement):
    '''
    Function will undo the previous filtering of the list numbers.

    :param undoList: the stack of operations performed
    :param numbers: the list of numbers
    :param undoElement: the dictionary containing a list of dictionaries found in the "deleted" :key
    with each having a value and an index
    :return:
    '''
    deletedNumbers = undoElement["deleted"]
    while len(deletedNumbers)!=0:
        addPerform(undoList,numbers,deletedNumbers[-1]["value"],deletedNumbers[-1]["index"]+1)
        deletedNumbers.pop()


def undoLast(undoList,numbers):

    if len(undoList)==0:
        print("No more undos")
        return

    possibleOperations = {"add":undoAdd,"remove":undoRemove,"replace":undoReplace,"filter":undoFilter}


    operation = undoList[-1]["operation"]
    if operation in possibleOperations:
        possibleOperations.get(operation)(undoList,numbers,undoList[-1])

    undoList = undoList.pop()



def createUndoList():
    undoList = []
    return undoList
