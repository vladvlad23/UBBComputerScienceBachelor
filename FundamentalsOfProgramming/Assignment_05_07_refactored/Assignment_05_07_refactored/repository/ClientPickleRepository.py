from Assignment_05_07_refactored.domain.Exceptions import *
from Assignment_05_07_refactored.domain.Client import Client
from Assignment_05_07_refactored.repository.ClientRepository import ClientRepository
import pickle

class ClientPickleRepository(ClientRepository):

    def __init__(self,fileName="clients.pickle"):
        ClientRepository.__init__(self)
        self.__fileName = fileName
        self.__loadFromFile()

    def add(self,client):
        ClientRepository.add(self,client)
        self.__storeToFile()

    def remove(self,clientId):
        ClientRepository.remove(self,clientId)
        self.__storeToFile()

    def modifyName(self,clientId,newName):
        ClientRepository.modifyName(self,clientId,newName)
        self.__storeToFile()

    def addStartingClients(self):
        ClientRepository.addStartingClients(self)
        self.__storeToFile()

    def __loadFromFile(self):
        file = open(self.__fileName, 'rb')
        try:
            self._clientList = pickle.load(file)
        except EOFError:
            pass
        except IOError:
            raise FileRepositoryException("Client File Error")
        finally:
            file.close()

    def __storeToFile(self):
        try:
            file = open(self.__fileName,'wb')
            clientList = ClientRepository.getAllClients(self)
            pickle.dump(clientList,file)
        except IOError:
            raise FileRepositoryException("Client File Error")
        finally:
            file.close()



