from modifyOperations import removePerform
from complexOperations import modulo,isReal

def filterReal(undoList,numbers):
    '''
    Function will filter all real numbers in the numbers array and also retain the operation in the undoList


    :param undoList: the "stack" of operations that have been performed so far
    :param numbers: list of complex numbers in memories
    :return:
    '''

    i=0
    deletedNumbers = []
    while i<len(numbers):
        if not isReal(numbers[i]):
            deletedNumbers.append({"value":numbers[i],"index":i})
            removePerform(undoList,numbers,i)
            i-=1
        i+=1
    undoList.append({"operation":"filter","deleted":deletedNumbers})

def filterModulo(undoList, numbers,operator,value):
    '''
    function will filter all numbers that do not have the modulo fulfilling the condition given by operator
    general form :

    filter real
    filter modulo [ < | = | > ] <number>
    :return:
    '''
    deletedNumbers = []
    if operator=='<':
        i=0
        while i<len(numbers):
            if not modulo(numbers[i])<value:
                deletedNumbers.append({"value": numbers[i], "index": i})
                removePerform(undoList,numbers,i)
            else:
                i+=1
    elif operator == '>':
        i=0
        while i<len(numbers):
            if not modulo(numbers[i])>value:
                deletedNumbers.append({"value": numbers[i], "index": i})
                removePerform(undoList,numbers,i)
            else:
                i+=1
    elif operator == '=':
        i=0
        while i<len(numbers):
            if not modulo(numbers[i])==value:
                deletedNumbers.append({"value": numbers[i], "index": i})
                removePerform(undoList,numbers,i)
            else:
                i+=1

    undoList.append({"operation":"filter","deleted":deletedNumbers})

