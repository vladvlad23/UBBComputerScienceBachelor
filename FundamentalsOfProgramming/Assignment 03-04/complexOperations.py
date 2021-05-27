def getReal(complexNumber):
    return complexNumber.real

def getImaginary(complexNumber):
    return complexNumber.imag


def turnStringToComplexNumber(number):
    '''
    Function will receive a string which will be turned into a complex Number
    The String is guaranteed to be a complex number
    '''

    # new string must be formed to be able to create complex number with complex() function
    # this means copy to new string everything except the last part and append j to it if its i

    if 'i' in number:
        newStringNumber = number[0:len(number) - 1] + 'j'
    else:
        newStringNumber = number
    complexNumber = complex(newStringNumber)
    return complexNumber


def isComplexNumber(number):
    '''
    Function will test if a number is a complex Number by trying to call turnStringToComplexNumber
    it will return true if succeeded and false otherwise
    '''

    try:
        number = turnStringToComplexNumber(number)
    except ValueError:
        return False
    return True


def isReal(complexNumber):
    return getImaginary(complexNumber) == 0



def modulo(complexNumber):
    '''
    Function returns the modulo of a complexNumber
    '''
    return abs(complexNumber)


