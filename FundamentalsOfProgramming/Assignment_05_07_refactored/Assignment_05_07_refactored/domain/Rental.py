from datetime import *

class Rental:

    def __init__(self, rentalId, movieId, clientId, rentedDate, dueDate, returnedDate=None):
        self.__rentalId = rentalId
        self.__movieId = movieId
        self.__clientId = clientId
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

    def setReturnDate(self, returnDate):
        self.__returnedDate = returnDate

    def isReturned(self):
        return self.__returnedDate is not None

    def getRentedDays(self):
        return (self.getReturnDate()- self.getRentedDate()).days

    def processLateDays(self):
        return (datetime.now().date() - self.getDueDate()).days