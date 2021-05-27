from Assignment_05_07_refactored.domain.Exceptions import *

class ClientValidator:
    def __init__(self,repository):
        self.__repository = repository

    def validate(self, client):

        error = []


        try:
            id = int(client.getClientId())
        except ValueError:
            error.append("Id must be an integer")

        if client.getClientName() == "":
            error.append("Name can't be empty")

        if error != []:
            if self.__repository.exists(client.getClientId()):
                error.append("Client Already exists\n")
            raise ValidationException(error)

    def validateName(self,name):
        if name == "":
            raise ValidationException("Name cannot be empty")


