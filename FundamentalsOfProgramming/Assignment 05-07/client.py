class Client:
    def __init__(self,clientID,name):
        '''
        Constructor will create the new Client instance having an id and a name.

        :param clientID: integer - the id of the newly created client
        :param name: string - the name of the newly created client
        '''
        self.__clientID = clientID
        self.__name = name
        self.__hasRented = False

    def getClientId(self):
        return self.__clientID

    def getName(self):
        return self.__name

    def setClientId(self,value):
        self.__clientID = value

    def setClientName(self,value):
        self.__name = value


    def getRentedStatus(self):
        return self.__hasRented

    def changeRentedStatus(self):
        '''
        Method changes the rented status of a client from true to false or otherwise
        '''
        if self.__hasRented == True:
            self.__hasRented = False
        else:
            self.__hasRented = True

def changeRentalStatus(clientList,clientId):
    '''
    Function changes the rental status of a client with a given id in the clientList
    :param clientList: the list where the client will be searched
    :param clientId: the id of the client
    '''
    for clients in range(0,len(clientList)):
        if clientList[clients].getClientId() == clientId:
            clientList[clients].changeRentedStatus()

def checkRentalById(clientList,clientId):
    '''
    Function returns if a client with a given id has returned a movie
    :param clientList: the list where the client will be searched
    :param clientId: the id of the client
    :return: True or False according to the situation
    '''
    for client in range(0,len(clientList)):
        if clientList[client].getClientId() == clientId:
            return clientList[client].getRentedStatus()


def searchClientById(clientList,clientId):
    '''
    Function will search for a client entry in a list and return it accordingly
    If the client isn't there, it raises an exception
    :param clientList: the list where it should be searched
    :param clientId: the id of the client
    :return: the client to be returned
    '''
    for i in range(0,len(clientList)):
        if clientList[i].getClientId() == clientId:
            return clientList[i]

    raise Exception("Client not found")

def getClientIndex(clientList,clientId):
    '''
    Function gets where in the list of a client entries is a client with a certain Id found
    :param clientList: the list where the client is to be searched
    :param clientId: the id which should be searched
    :return: the index
    '''
    for i in range(0,len(clientList)):
        if clientList[i].getClientId() == clientId:
            return i

    raise Exception("Client not found")



