from complexOperations import isComplexNumber,turnStringToComplexNumber

def removePerform(undoList,numbers ,position ,positionEnd=None):
    '''
    Function performs the removal operation of the last element if the last parameter is none
    and the removal operation of the elements with the index belonging in the interval of the
    second and third argument inclusive
    '''


    if positionEnd == None:
        del numbers[position]
    else:
        for i in range(position ,positionEnd+1):
            del numbers[position-1]


def replacePerform(numbers ,numberToBeReplaced ,numberToReplaceWith):
    '''
    Functions will perform the replace operation. It will receive a list of numbers,
    the value which must be replaced and the value to replace with. It will replace all
    occurences of the first number in the number list with the second number

    '''


    for i ,number in enumerate(numbers):
        if number == numberToBeReplaced:
            numbers[i ] = numberToReplaceWith
