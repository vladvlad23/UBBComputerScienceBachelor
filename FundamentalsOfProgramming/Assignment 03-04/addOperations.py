def insertPerform(undoList,numberList, value, position):
    '''
    Function performs the insert function.
    '''
    addPerform(undoList,numberList,value,position)



def addNumberToList(numbers, numberToAdd, position=None):
    '''
    Function will receive a list and a number to add with an optional parameter being the position
    of where should the number be added. It will append or insert the number accordingly
    '''

    if position is None:
        numbers.append(numberToAdd)
    else:
        numbers.insert(int(position), numberToAdd)

# optional argument in case of adding to a certain position
def addPerform(undoList, numberList, numberToAdd, position=None):
    '''
    The function will add a number at a certain position or append it at the end if
    no final position is given
    input - list of numbers, a number to add to list, (optional) position
    action - add number to list and if there is a position, add to that position

    '''



    if position is None:
        addNumberToList(numberList, numberToAdd)
    elif position>len(numberList)+1 or position<1:
        print("Invalid input at adding operation")
    else:
        addNumberToList(numberList, numberToAdd, position-1)
