from Assignment_05_07_refactored.domain.Rental import Rental
from Assignment_05_07_refactored.repository.RentalRepository import RentalRepository
from Assignment_05_07_refactored.domain.Exceptions import FileRepositoryException
from datetime import *

class RentalCSVRepository(RentalRepository):

    def __init__(self,fileName="rentals.txt"):
        RentalRepository.__init__(self)
        self.__fileName = fileName
        self.__loadFromFile()

    def add(self,rental):
        RentalRepository.add(self,rental)
        self.storeToFile()

    def addRentalAtIndex(self, rental, index):
        RentalRepository.addRentalAtIndex(self,rental,index)
        self.storeToFile()

    def exists(self,rentalId):
        return RentalRepository.exists(self,rentalId)

    def findRentalById(self,rentalId):
        return RentalRepository.findRentalById(self,rentalId)

    def size(self):
        return RentalRepository.size(self)

    def remove(self,rentalId):
        return RentalRepository.remove(self,rentalId)
        self.storeToFile()

    def complete(self,rentalId,date):
        RentalRepository.complete(self,rentalId,date)
        self.storeToFile()

    def uncomplete(self,rentalId):
        RentalRepository.uncomplete(self,rentalId)
        self.storeToFile()

    def getBiggestRentalId(self):
        return RentalRepository.getBiggestRentalId(self)

    def getAllRentals(self):
        return RentalRepository.getAllRentals(self)

    def lateRentals(self):

        return RentalRepository.lateRentals(self)

    def addStartingRentals(self,clientList):
        RentalRepository.addStartingRentals(self,clientList)
        self.storeToFile()


    def __loadFromFile(self):
        try:
            file = open(self.__fileName,'r')
            line = file.readline().strip()
            while line != "":
                attributes = line.split(";")

                rentedDateString = attributes[3].split("/")
                rentedDate = date(int(rentedDateString[0]),int(rentedDateString[1]),int(rentedDateString[2]))

                dueDateString = attributes[4].split("/")
                dueDate = date(int(dueDateString[0]),int(dueDateString[1]),int(dueDateString[2]))

                returnedDateString = attributes[5].split("/")
                if returnedDateString[0] !="None":
                    returnedDate = date(int(returnedDateString[0]),int(returnedDateString[1]),int(returnedDateString[2]))
                else:
                    returnedDate = None
                rental = Rental(int(attributes[0]),int(attributes[1]),int(attributes[2]),rentedDate,
                                dueDate,returnedDate)

                RentalRepository.add(self,rental)

                line = file.readline().strip()
        except IOError:
            raise FileRepositoryException("Rental File Error")
        finally:
            file.close()

    def storeToFile(self):
        try:
            file = open(self.__fileName,'w')
            rentalList = RentalRepository.getAllRentals(self)
            for rental in rentalList:
                stringToStore = str(rental.getRentalId()) + ";" + str(rental.getMovieId()) + ";" + \
                                str(rental.getClientId()) + ";"

                rentedDateString = str(rental.getRentedDate().year) + "/" + \
                                   str(rental.getRentedDate().month) + "/" + \
                                   str(rental.getRentedDate().day)

                dueDateString = str(rental.getDueDate().year) + "/" + \
                                   str(rental.getDueDate().month) + "/" + \
                                   str(rental.getDueDate().day) + ""

                if rental.isReturned():
                    returnedDateString = str(rental.getReturnDate().year) + "/" + \
                                       str(rental.getReturnDate().month) + "/" + \
                                       str(rental.getReturnDate().day)
                else:
                    returnedDateString = "None"

                stringToStore = stringToStore + rentedDateString + ";" + dueDateString + ";" +\
                                returnedDateString + "\n"

                file.write(stringToStore)
        except IOError:
            raise FileRepositoryException("Rental File Error")
        finally:
            file.close()



