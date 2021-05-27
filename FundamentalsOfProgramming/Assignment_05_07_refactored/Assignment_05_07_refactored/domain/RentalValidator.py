from Assignment_05_07_refactored.domain.Exceptions import *
from datetime import *


class RentalValidator:


    def __init__(self,repository):
        pass
        self.__repository = repository

    def validate(self, rental):
        error = []

        if(rental.getRentedDate() > rental.getDueDate()):
            error.append("Due date must be AFTER rented date")
        if(rental.getRentedDate() > datetime.now().date()):
            error.append("Rented date must not be in the future")

        if error != []:
            raise ValidationException(error)




