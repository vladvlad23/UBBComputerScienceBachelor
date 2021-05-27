from dateOperations import *

class Rental:

    def __init__(self,rentalId,movieId,clientId,rentedDate,dueDate,returnedDate=None):
        self.__rentalId = int(rentalId)
        self.__movieId = int(movieId)
        self.__clientId = int(clientId)
        self.__rentedDate = rentedDate
        self.__dueDate = dueDate
        self.__returnedDate = returnedDate

    def getRentalId(self):
        return self.__rentalId

    def getMovieId(self):
        return self.__movieId

    def getClientId(self):
        return self.__clientId

    def getRentedDate(self):
        return self.__rentedDate

    def getDueDate(self):
        return self.__dueDate

    def getReturnDate(self):
        if self.__returnedDate is None:
            raise Exception("Not returned yet")
        else:
            return self.__returnedDate

    def setReturnDate(self,returnDate):
        self.__returnedDate = returnDate

    def isReturned(self):
        return self.__returnedDate is not None


def searchInRentalList(rentalList,rentalId):
    '''
    Function will search in a list of rentals a given rental id
    If the rental isn't present, it returns an exception
    :param rentalList: the list where the rental should be searched
    :param rentalId: the id of the rental to be searched
    :return: the rental
    '''
    for i in range(0,len(rentalList)):
        if rentalList[i].getRentalId()==rentalId:
            return rentalList[i]
    raise Exception("Rental not here")

def completeRental(rentalList,rentalId,returnDate):
    '''
    The function will complete rental by adding the date where the client returned the movie
    :param rentalList: the list of rentals
    :param rentalId: the id of the rental which has been returned
    :param returnDate: the date when the rental has been returned
    :return:
    '''
    for rentals in range(0,len(rentalList)):
        if rentalList[rentals].getRentalId() == rentalId:
            rentalList[rentals].setReturnDate(returnDate)

def searchRentalWithId(rentalList, id):
    '''
    The function will receive an id and a list of rentals and it will return the

    :param rentalList: the list of rentals
    :param id: the id of the rental which has to be returned
    :return: the rental which fits the given id
    '''
    for rental in range(0,len(rentalList)):
        if rentalList[rental].getRentalId()==id:
            return rentalList[rental]

    raise Exception("Rental not found")





