class Client:

    def __init__(self,clientId,clientName):
        self.__clientId = clientId
        self.__clientName = clientName
        self.__rentedStatus = False

    def getClientId(self):
        return self.__clientId

    def getClientName(self):
        return self.__clientName

    def setClientName(self,clientName):
        self.__clientName = clientName

    def getRentedStatus(self):
        return self.__rentedStatus

    def setRentedStatus(self):
        if self.__rentedStatus == True:
            self.__rentedStatus = False
        else:
            self.__rentedStatus = True