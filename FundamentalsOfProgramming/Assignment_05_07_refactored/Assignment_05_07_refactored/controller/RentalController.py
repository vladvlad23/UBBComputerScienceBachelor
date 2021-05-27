from Assignment_05_07_refactored.domain.RentalValidator import RentalValidator
from Assignment_05_07_refactored.repository.RentalRepository import RentalRepository
from Assignment_05_07_refactored.domain.Rental import Rental
from Assignment_05_07_refactored.domain.Exceptions import NotFoundException,DuplicateException,DateException
from datetime import *

class RentalController:

    def __init__(self,repository,validator):
        '''

        :param repository: instance of the repository class
        :param validator: instance of the validation class
        '''
        self.__validator = validator
        self.__repository = repository

    def create(self,rentalId,clientId,movieId,dayOfRenting,monthOfRenting,yearOfRenting,
                                       dayDue,monthDue,yearDue):
        '''
        The function will call validate parameters through the validator and will call the super
        repository to add the new rental
        :param rentalId: the id of the new rental
        :param clientId: the id of the client
        :param movieId: the id of the movie
        :param dayOfRenting: the day of renting
        :param monthOfRenting: the month of renting
        :param yearOfRenting: the year of renting
        :param dayDue: the day due
        :param monthDue: the month due
        :param yearDue: the year due
        :return:
        '''
        try:
            rentingDate = date(int(yearOfRenting),int(monthOfRenting),int(dayOfRenting))
            dueDate = date(int(yearDue),int(monthDue),int(dayDue))
        except Exception:
            raise DateException("Invalid date input")

        try:
            rental = self.findRentalById(rentalId)
            raise DuplicateException("Rental Id already exists \n \
            You probably added in the meantime something with that id. Careful with undo/redo")
        except NotFoundException:
            pass


        rental = Rental(rentalId,movieId,clientId,rentingDate,dueDate)

        self.__validator.validate(rental)

        self.__repository.add(rental)

    def complete(self,rentalId,year,month,day):
        '''
        Function will validate the completion parameters of a rental and call the completion method
        from the parent class
        Function will complete a rental
        :param rentalId: the id of the rental to be completed
        :param year: the year of completion
        :param month: the month of completion
        :param day: the day of completion
        '''
        try:
            finalYear = int(year)
            finalMonth = int(month)
            finalDay = int(day)
            id = int(rentalId)
        except ValueError:
            print("Rental ID must be int")
            return

        if not self.__repository.exists(id):
            raise NotFoundException("Rental not exists")


        self.__repository.complete(id,date(finalYear,finalMonth,finalDay))

    def getAllRentals(self):
        return self.__repository.getAllRentals()

    def findRentalById(self,rentalId):
        return self.__repository.findRentalById(rentalId)

    def lateRentals(self):
        return self.__repository.lateRentals()

