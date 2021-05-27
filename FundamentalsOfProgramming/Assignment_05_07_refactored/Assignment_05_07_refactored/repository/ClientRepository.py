from Assignment_05_07_refactored.domain.Exceptions import *
from Assignment_05_07_refactored.domain.Client import Client
from Assignment_05_07_refactored.repository.RandomGenerator import RandomClientGenerator
from Assignment_05_07_refactored.domain.CustomIterable import CustomIterable,sort,filter

class ClientRepository:

    def __init__(self):
        self._clientList = CustomIterable()

    def add(self,client):
        self._clientList.append(client)

    def exists(self,clientId):
        for client in self._clientList:
            if client.getClientId() == clientId:
                return True
        return False

    def size(self):
        return len(self._clientList)

    def addStartingClients(self):
        '''
        Function will generate a list of 100 Client entries that will be added to a list
        '''
        self.add(Client(1, "Georgescu George"))
        self.add(Client(2, "Grigorescu Grigore"))
        self.add(Client(3, "Marinescu Marian"))
        self.add(Client(4, "Ionescu Ioan"))
        self.add(Client(5, "Popescu Pop"))

        for i in range(6, 100 + 1):
            self.add(Client(i, RandomClientGenerator.randomClientNameGenerator(self)))



    def remove(self,clientId):
        for client in self._clientList:
            if client.getClientId() == clientId:
                del self._clientList[self._clientList.index(client)]

    def modifyName(self,clientId,newName):
        for client in self._clientList:
            if client.getClientId() == clientId:
                self._clientList[self._clientList.index(client)].setClientName(newName)

    def getAllClients(self):
        return self._clientList

    def changeRentalStatus(self,clientId):
        index = self._clientList.index(self.findClientById(clientId))
        self._clientList[index].setRentedStatus()

    def findClientById(self, clientId):
        for client in self._clientList:
            if client.getClientId() == clientId:
                return client
        raise NotFoundException("Client not found")

    def searchClientsByName(self,searchString):
        '''
        Function will search the given string in lowercase in the names of the clients in the list
        (accessed by client.getName()
        :param searchString: the string to be searched
        :param clientList: the list of clients where it should be searched
        :return: list of clients with names matching the given string
        '''
        resultList = []
        searchString = searchString.lower()
        for client in self._clientList:
            if searchString in client.getClientName().lower():
                resultList.append(client)

        return resultList

    def clientsDescendingByActivity(self, rentalList):
        '''
        The function will receive a list of clients and a list of rentals. It will return a list of
        clients sorted by renting days
        :param clientList: the movie list
        :param rentalList: the rental list
        :return: list of clients in the given order
        '''
        # implement dictionary where the first element is the movie id and the second
        # element consist of the renting days
        clientDictList = CustomIterable()
        for client in self._clientList:
            clientDictionary = {"clientId": client.getClientId(), "days": int(0)}
            for rental in rentalList:
                if rental.getClientId() == client.getClientId():
                    if rental.isReturned():
                        clientDictionary["days"] += rental.getRentedDays()

            clientDictList.append(clientDictionary)

        # deprecated clientDictList = sorted(clientDictList, key=lambda clientDict: clientDict.get("days"), reverse=True)
        sort(clientDictList,comparison)

        result = []
        for client in clientDictList:
            result.append(self.findClientById(client["clientId"]))

        return result

    def storeToFile(self):
        pass


def comparison(first,second):
    return first.get("days")>=second.get("days")




