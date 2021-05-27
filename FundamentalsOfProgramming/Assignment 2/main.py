def printAllNumbers(numbers):
    '''
    Function will print all numbers passed to it 
    by accessing the "printComplexNumber(complexNumber)"
    function to which it will send a member of the numbers list
    
    input: list
    action: for each member of the list, call the required function using it as a parameter
    output: none
    '''
    if len(numbers)==0:
        print("There are no numbers in the list")
    
    for i in numbers:
        printComplexNumber(i)
        

def printComplexNumber(number):
    '''
    The function will receive a dictionary containing 'real' and 'imaginary' keys
    The function will print the number in the following for : 'real'+'imaginary'i
    e.g for real=3 and imaginary=4 the print will be 3+4i
        for real=3 and imaginary=(-4) the print will be 3-4i
    input: dictionary with the keys 'real' and 'imaginary' containing integers
    action: print the number in the described manner
    '''
    imaginaryPart = getImaginaryPart(number)
    realPart = getRealPart(number)
    
    if imaginaryPart<0: #solving the conflicting case of a+(-b)i
        print(realPart,imaginaryPart,"i",sep="")
    elif imaginaryPart==0: #solving the conflict case of form a+0i
        print(realPart)
    elif realPart==0: #solving the conflict case of printing 0+ai
        print(imaginaryPart,"i",sep="")
    else: #normal case
        print(realPart,"+",imaginaryPart,"i",sep="")
            
            
'''
Getter functions which will receive a dictionary with key 'imaginary' or 'real'
Functions will return the value stored in the dictionary under the given key
'''
def getImaginaryPart(number):
    return number['imaginary']

def getRealPart(number):
    return number['real']


'''
Setter functions which will receive a dictionary with key 'imaginary' or 'real' and an integer
Functions will set the value under the given key to the value of the integer
'''
def setImaginaryPart(number,value):
   number["imaginary"] = value
    
def setRealPart(number,value):
    number["real"] = value
    
#Function will append a given element to a list
def appendNumberToList(numbers,complexNumber): 
    numbers.append(complexNumber)

def getNumbers(numbers):
    '''
    The function will receive a list which must be populated with the input of the user
    The user will input real and imaginary parts of complex numbers which must be stored as
    dictionaries with 'real', respectively 'imaginary' keys 
    
    input: list of numbers containing type dictionary
    action: populate the list with dictionaries containing values read from keyboard
    
    '''
    numberOfInputs = int(input("How many numbers would you like to input? "))
    for i in range(0,numberOfInputs):
        complexNumber = {'real':0,'imaginary':0} #Create empty dictionary to represent the complex number        
        
        realPart = int(input("The real part, please: ")) 
        setRealPart(complexNumber,realPart)
        
        imaginaryPart = int(input("Imaginary part, please: "))
        setImaginaryPart(complexNumber, imaginaryPart)
        
        appendNumberToList(numbers,complexNumber)
        
        
        
def printRealNumbers(numbers):
    '''
    *The function will receive a list of dictionaries containing real and imaginary parts of 
    complex numbers stored under the keys 'real' and 'imaginary'. 
    *The function will call another function to get the longest sequence of real numbers
    (function which will return a dictionary containing keys 
    'maxStart' for where the sequence will start and 'maxLength' for the numbers of elements in the sequence.
    *The function has to call the printComplexNumber function which requires an argument of
    type dictionary (elements in the numbers list with indexes between maxStart and maxStart+maxLength
    
    input: list containing type dictionary
    action: call printComplexNumber for each number in the longest sequence of real numbers (obtained from given function)
    
    '''
    maxes = longestSequenceOfRealNumbers(numbers)
    sequenceStart = maxes['maxStart']
    sequenceLength = maxes['maxLength']
    
    print("The longest subsequence of real numbers has", maxes['maxLength'], "elements and is formed of: ")
    
    for i in range(sequenceStart, sequenceStart + sequenceLength):
        printComplexNumber(numbers[i])
        
def printDistinctNumbers(numbers):
    '''
    The function will receive a list. It will get the start and the length of the 
    longest sequence of distinct numbers in the list in the form of a dictionary containing
    the keys 'maxStart' and 'maxLength' from the function "longestSequenceOfDistinctNumbers"
    
    the function must call printComplexNumber for each element having the index between maxStart and maxStart+maxLength
    
    
    input: list of elements
    action: get longest Sequence of distinct numbers and call printComplexNumber for each 
            list element having its index between 'maxStart' and 'maxStart'+'maxLength'
    '''
    if len(numbers)==0:
        print("There are no numbers in the list")
    maxes = longestSequenceOfDistinctNumbers(numbers)
    
    start = maxes['maxStart']
    length = maxes['maxLength']
    
    print("The longest subsequence of distinct numbers has ", length, "numbers. They are:" )
    for i in range(start,start+length-1):
        printComplexNumber(numbers[i])
            
def distinctComplexNumbers(a,b):
    '''
    Function will test whether 2 complex numbers a and b are distinct
    2 complex numbers a and b are considered distinct if their imaginary part or real part
    are distinct (given by the functions getRealPart(number) and getImaginaryPart(number)
    
    '''
    return (getImaginaryPart(a) != getImaginaryPart(b) or getRealPart(a)!= getRealPart(b))


def isNumberInSequence(numbers, sequenceStart, sequenceEnd, complexNumber):
    '''
    Function will receive a list of numbers, a sequence start and sequence 
    
    
    input: list, integer representing start and end of sequence and an object of the same type as the ones in the list
    action: test whether the last given object is found in the list between the indexes start and end
    output: true if in the interval start and end+1 the last given object is found
            false otherwise
            
    
    
    '''
    for i in range(sequenceStart,sequenceEnd+1):
        if numbers[i] == complexNumber:
            return True
    return False
            
def longestSequenceOfDistinctNumbers(numbers):
    '''
    The function will find the longest sequence of distinct elements in a given list
    
    
    '''
    i=0
    maxLength=1
    maxStart=0
    while i<len(numbers)-1:
        #if this is the start of a multiple elements sequence
        if distinctComplexNumbers(numbers[i], numbers[i+1]): 
            sequenceStart = i
            length = 2
            i+=1
            #while we haven't reached the end of the list
            #and the next number isn't already present in the list
            while i<len(numbers)-1 and not isNumberInSequence(numbers,sequenceStart,i,numbers[i+1]):
                length+=1 #increase length of sequence
                i+=1
            i=sequenceStart #the next check will start after the start of the previous sequence
            if length>maxLength: #if the length is maximum, update the maximum values
                maxLength = length
                maxStart = sequenceStart
        i+=1
    #return the results. If there are no multiple elements sequence, the default values will point to the first element
    result = {'maxStart':maxStart, 'maxLength':maxLength} 
    return result
        
def longestSequenceOfRealNumbers(numbers):
    i=0
    maxLength=0
    maxStart=0
    while i<len(numbers):
        #if this is the start of a sequence
        if getImaginaryPart(numbers[i])==0:
            sequenceStart = i
            length = 1
            i+=1
            #while we haven't reached the end of the list and the following numbers is still real
            while i<len(numbers) and getImaginaryPart(numbers[i])==0:
                length+=1
                i+=1
            if length>maxLength: #update the maximum values if necessary
                maxLength=length
                maxStart=sequenceStart
        i+=1
        
    #return the results and if there are none, both start and length are 0
    result={'maxStart':maxStart,'maxLength':maxLength}
    return result

def initialListValues(numbers):
    '''
    Function will populate the received list with dictionaries containing keys: 'real' and 'imaginary'
    using the appendNumberToList function
    numbers will be in range 1 and 10
    
    '''
    
    for i in range(1,11):
        complexNumber = {'real':0,'imaginary':0}
        setRealPart(complexNumber, i)
        setImaginaryPart(complexNumber, i+1)
        appendNumberToList(numbers, complexNumber)
                    
def runMenu():
    '''
    Function will run the console menu.
    It will print the menu and it will handle the input from the user
    It will also create a numbers list which will be populated by the initialListValues function
    '''
    
    
    help = "Options: \n,\
    1. 'read' to read a list of complex numbers(in z=a+bi form). \n \
    2. 'print' to print the entire list of numbers. \n \
    3. 'print-real' to print the longest subsequence of all real numbers. \n \
    4. print-distinct' to print the longest subsequence of all distinct numbers. \n"
    
    numbers = []
    
    initialListValues(numbers)
    
    
    options = { 'read':getNumbers, 'print':printAllNumbers,\
                'print-real':printRealNumbers, 'print-distinct':printDistinctNumbers}
    
    #infinite loop to get input
    while(True):
        print("Type 'help' to print list of possible commands")
        userInput = input("Please type an option: ")
        if userInput in options:
            options.get(userInput)(numbers)
        elif userInput=="exit":
            print("Okay. Bye bye")
            break
        elif userInput=="help":
            print(help)
        else:
            print("Invalid input. Try again or type 'help' for help (Without apostrophes")

runMenu()


        