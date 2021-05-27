from Assignment_05_07_refactored.domain.Exceptions import *
from Assignment_05_07_refactored.domain.Client import Client
from Assignment_05_07_refactored.repository.ClientRepository import ClientRepository

class ClientCSVRepository(ClientRepository):

    def __init__(self,fileName="clients.txt"):
        ClientRepository.__init__(self)
        self.__fileName = fileName
        self.__loadFromFile()

    def add(self,client):
        ClientRepository.add(self,client)
        self.storeToFile()

    def remove(self,clientId):
        ClientRepository.remove(self,clientId)
        self.storeToFile()

    def modifyName(self,clientId,newName):
        ClientRepository.modifyName(self,clientId,newName)
        self.storeToFile()

    def addStartingClients(self):
        ClientRepository.addStartingClients(self)
        self.storeToFile()


    def __loadFromFile(self):
        file = open(self.__fileName, 'r')
        try:

            line = file.readline().strip()
            while line != "":
                attributes = line.split(";")
                client = Client(int(attributes[0]),attributes[1])

                ClientRepository.add(self,client)

                line = file.readline().strip()
        except IOError:
            raise FileRepositoryException("Client File Error")
        finally:
            file.close()

    def storeToFile(self):
        try:
            file = open(self.__fileName,'w')
            clientList = ClientRepository.getAllClients(self)
            for client in clientList:
                stringToStore = str(client.getClientId()) + ";" + client.getClientName() + "\n"
                file.write(stringToStore)
        except IOError:
            raise FileRepositoryException("Client File Error")
        finally:
            file.close()



