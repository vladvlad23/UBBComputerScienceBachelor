from Assignment_05_07_refactored.domain.Rental import Rental
from Assignment_05_07_refactored.repository.RandomGenerator import RandomDateGenerator
from Assignment_05_07_refactored.domain.Exceptions import NotFoundException,DateException
from Assignment_05_07_refactored.domain.CustomIterable import CustomIterable

from datetime import *
from random import randint

class RentalRepository:

    def __init__(self):
        self._rentalList = CustomIterable()
        self.__dateGenerator = RandomDateGenerator()

    def add(self,rental):
        self._rentalList.append(rental)

    def addRentalAtIndex(self, rental, index):
        '''
        Function will add a rental in the list at a specific index
        :param rental: the rental which should be inserted
        :param index: the index where it should be inserted
        '''
        self._rentalList.insert(index, rental)

    def exists(self,rentalId):
        for rental in self._rentalList:
            if rental.getRentalId() == rentalId:
                return True
        return False

    def findRentalById(self,rentalId):
        for rental in self._rentalList:
            if rental.getRentalId() == rentalId:
                return rental
        raise NotFoundException("Rental not found")

    def size(self):
        return len(self._rentalList)

    def remove(self,rentalId):
        if not self.findRentalById(rentalId):
            raise NotFoundException("Rental does not exist")

        for rental in self._rentalList:
            if rental.getRentalId() == rentalId:
                del self._rentalList[self._rentalList.index(rental)]

    def complete(self,rentalId,date):
        index = self._rentalList.index(self.findRentalById(rentalId))

        if(self._rentalList[index].getRentedDate() > date):
            raise DateException("Return date must be after rented date")
        self._rentalList[index].setReturnDate(date)

    def uncomplete(self,rentalId):
        index = self._rentalList.index(self.findRentalById(rentalId))
        self._rentalList[index].setReturnDate(None)


    def getBiggestRentalId(self):
        '''
        Function returns the maximum rental id in the list
        :return: the maximum ida list

        '''
        maxId = int(0)
        for rental in self._rentalList:
            if rental.getRentalId() > maxId:
                maxId = rental.getRentalId()

        return maxId

    def getAllRentals(self):
        return self._rentalList

    def lateRentals(self):
        '''
        Function will return a list of movies that are late (the current date is past the due date and
        the client hasn't returned it yet
        :param movieList: the movie list from where the movies will be added to the result
        :param rentalList: the rental list where everything is checked
        '''
        rentalDictList = []
        for rental in self._rentalList:
            if not rental.isReturned():
                if rental.processLateDays() > 0:
                    lateRental = {"rentalId": rental.getRentalId(), "daysLate": rental.processLateDays()}
                    rentalDictList.append(lateRental)

        rentalDictList = sorted(rentalDictList, key=lambda rentalDict: rentalDict.get("daysLate"), reverse=True)

        result = []
        for rental in rentalDictList:
            rentalObject = self.findRentalById(rental.get("rentalId"))
            result.append(rentalObject)

        return result

    def addStartingRentals(self,clientList):
        '''
        Function will generate a list of 100 rental entries that will be added to a list
        :param rentalList: the list where the entries should be added
        :param clientList: this is to aid in creating rentals that haven't been returned
        '''
        self.add(Rental(1, 1, 1, date(2000, 1, 1), date(2000, 1, 6), date(2000, 1, 1)))
        self.add(Rental(2, 2, 2, date(2000, 1, 2), date(2000, 1, 6), date(2000, 1, 3)))
        self.add(Rental(3, 3, 3, date(2000, 1, 3), date(2000, 1, 7), date(2000, 1, 5)))
        self.add(Rental(4, 4, 4, date(2000, 1, 5), date(2000, 1, 10), date(2000, 1, 7)))
        self.add(Rental(5, 5, 5, date(2000, 1, 4), date(2000, 1, 8), date(2000, 1, 8)))

        for i in range(6, 150 + 1):
            movieId = randint(1, 100)
            clientId = randint(1, 100)
            randomRentalDate = self.__dateGenerator.generateDateOfRental()
            randomDueDate = self.__dateGenerator.generateDueDate(randomRentalDate)
            randomReturnedDate = self.__dateGenerator.generateReturnDate(randomRentalDate)

            if self.__findClientWithId(clientList, clientId).getRentedStatus() == False:
                self.add(Rental(i, movieId, clientId, randomRentalDate, randomDueDate, randomReturnedDate))
                clientList[clientList.index(self.__findClientWithId(clientList, clientId))].setRentedStatus()

    def __findClientWithId(self,list,id):
        for client in list:
            if client.getClientId() == id:
                return client

    def storeToFile(self):
        pass