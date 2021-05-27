from Assignment_05_07_refactored.domain.ClientValidator import ClientValidator
from Assignment_05_07_refactored.repository.ClientRepository import ClientRepository
from Assignment_05_07_refactored.domain.Client import Client
from Assignment_05_07_refactored.domain.Exceptions import NotFoundException,DuplicateException

class ClientController:

    def __init__(self,repository,validator):
        self.__validator = validator
        self.__repository = repository

    def create(self,clientId,clientName):

        client = Client(clientId,clientName)

        self.__validator.validate(client)


        try:
            client = self.findClientById(clientId)
            raise DuplicateException("Client Id already exists \n \
            Or in case you tried to undo/redo, you probably added in the meantime something with that id")
        except NotFoundException:
            pass


        self.__repository.add(client)

    def remove(self,clientId):
        if not self.__repository.exists(clientId):
            raise NotFoundException("Client does not exist")
        self.__repository.remove(clientId)

    def modify(self,clientId,newValue):
        if not self.__repository.exists(clientId):
            raise NotFoundException("Client not found")

        self.__validator.validateName(newValue)
        self.__repository.modifyName(clientId,newValue)

    def getAllClients(self):
        return self.__repository.getAllClients()

    def changeRentalStatus(self,clientId):
        if not self.__repository.exists(clientId):
            raise NotFoundException("Client not found")

        self.__repository.changeRentalStatus(clientId)

    def searchClient(self,string):
        return self.__repository.searchClientsByName(string)

    def clientsDescendingByActivity(self,rentalList):
        return self.__repository.clientsDescendingByActivity(rentalList)

    def findClientById(self, clientId):
        return self.__repository.findClientById(clientId)
