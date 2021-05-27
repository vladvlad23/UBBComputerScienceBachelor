from operations.add import *
from operations.remove import *


class UndoRedoController:

    def __init__(self):
        self.__index = -1
        self.__list = []

    '''
    All the following functions insert a given operation into the list of operations
    The list will contain dictionaries having data regarding the operations added
    '''
    def insertMovieAddOperation(self,movieAdded):
        '''
        Function inserts an add movie operation in the list of operations
        :param movieAdded: the movie added
        '''
        dictionary = {"operation":"addMovie","movieAdded":movieAdded}
        self.__list.insert(self.__index+1,dictionary)
        self.__index +=1

    def insertClientAddOperation(self,clientAdded):
        '''
        Function inserts an add client operation in the list of operations
        :param clientAdded: the client added
        '''
        dictionary = {"operation":"addClient","clientAdded":clientAdded}
        self.__list.insert(self.__index+1,dictionary)
        self.__index +=1

    def insertRentalAddOperation(self,rentalToAdd):
        '''
        Function inserts an add rental operation in the list of operations
        :param rentalToAdd: the rental added
        '''
        dictionary = {"operation":"addRental","rentalAdded":rentalToAdd}
        self.__list.insert(self.__index+1,dictionary)
        self.__index +=1

    def insertRentalCompletionOperation(self,rentalCompleted,returnDate):
        dictionary = {"operation":"completeRental","rentalCompleted":rentalCompleted,
                      "returnDate":returnDate}
        self.__list.insert(self.__index+1,dictionary)
        self.__index+=1

    def insertClientRemovalOperation(self, removedClient,index):
        dictionary = {"operation":"removeClient","clientRemoved":removedClient,"index":index}
        self.__list.insert(self.__index+1,dictionary)
        self.__index+=1

    def insertMovieRemovalOperation(self, removedMovie,index):
        dictionary = {"operation":"removeMovie","movieRemoved":removedMovie,"index":index}
        self.__list.insert(self.__index+1,dictionary)
        self.__index+=1

    def insertMovieModifyOperation(self, modifiedMovie,parameterModified,oldValue,newValue):
        dictionary = {"operation":"modifyMovie","movieModified":modifiedMovie,
                      "parameterModified":parameterModified,"oldValue":oldValue,"newValue":newValue}
        self.__list.insert(self.__index+1,dictionary)
        self.__index+=1

    def insertClientModifyOperation(self,modifiedClient, oldValue, newValue):
        dictionary = {"operation":"modifyClient","clientModified":modifiedClient,
                      "oldValue":oldValue,"newValue":newValue}

        self.__list.insert(self.__index+1,dictionary)
        self.__index+=1

    def undo(self,movieList,clientList,rentalList):
        '''
        The function will check if an undo operation is possible, otherwise throw an exception
        If it's possible, it will update the index and call the method to perform the operation
        :param movieList: the list movies
        :param clientList: the list of clients
        :param rentalList: the list of rentals
        '''
        if self.__index < 0:
            raise Exception("No more undos")
            return

        self.performUndoOperation(self.__list[self.__index],movieList,clientList,rentalList)
        self.__index -=1

    def redo(self,movieList,clientList,rentalList):
        '''
        The function will check if a redo operation is possible, otherwise throw an exception
        If it's possible, it will update the index and call the method to perform the operation
        :param movieList: the list movies
        :param clientList: the list of clients
        :param rentalList: the list of rentals
        '''
        if self.__index+1 > len(self.__list)-1:
            raise Exception("No more redos")
            return
        self.__index+=1
        self.performRedoOperation(self.__list[self.__index],movieList,clientList,rentalList)

    def performUndoOperation(self, operation,movieList,clientList,rentalList):
        '''
        The method will perform an undo operation
        :param operation: the operation to be reversed
        :param movieList: the list movies
        :param clientList: the list of clients
        :param rentalList: the list of rentals
        '''
        possibleOperations = {"addMovie":undoAddMovie,"addClient":undoAddClient,
                              "addRental":undoAddRental,"completeRental":undoCompleteRental,
                              "removeClient":undoRemoveClient,"removeMovie":undoRemoveMovie,
                              "modifyMovie":undoModifyMovie,"modifyClient":undoModifyClient}

        if operation["operation"] in possibleOperations:
            possibleOperations[operation["operation"]](operation,movieList,clientList,rentalList)

    def performRedoOperation(self,operation,movieList,clientList,rentalList):
        '''
        The method will perform a redo operation
        :param operation: the operation to be performed
        :param movieList: the list movies
        :param clientList: the list of clients
        :param rentalList: the list of rentals
        :return:
        '''
        possibleOperations = {"addMovie":redoAddMovie,"addClient":redoAddClient,
                              "addRental":redoAddRental,"completeRental":redoCompleteRental,
                              "removeClient":redoRemoveClient,"removeMovie":redoRemoveMovie,
                              "modifyMovie":redoModifyMovie,"modifyClient":redoModifyClient}
        if operation["operation"] in possibleOperations:
            possibleOperations[operation["operation"]](operation,movieList,clientList,rentalList)


'''
The following methods will call the opposite functions for redoing/undoing the given operations
'''
def undoAddMovie(operation,movieList,clientList,rentalList):
    removeMovie(movieList,operation["movieAdded"].getMovieId())

def redoAddMovie(operation,movieList,clientList,rentalList):
    addMovie(operation["movieAdded"],movieList)

def undoAddClient(operation,movieList,clientList,rentalList):
    removeClient(clientList,operation["clientAdded"].getClientId())

def redoAddClient(operation,movieList,clientList,rentalList):
    addClient(clientList,operation["clientAdded"])

def undoAddRental(operation,movieList,clientList,rentalList):
    removeRental(rentalList,operation["rentalAdded"].getRentalId())
    changeRentalStatus(clientList,operation["rentalAdded"].getClientId())

def redoAddRental(operation,movieList,clientList,rentalList):
    addRental(operation["rentalAdded"],rentalList)
    changeRentalStatus(clientList,operation["rentalAdded"].getClientId())

def undoCompleteRental(operation,movieList,clientList,rentalList):
    rentalIndex = rentalList.index(searchRentalWithId(rentalList,operation["rentalCompleted"].getRentalId()))
    rentalList[rentalIndex].setReturnDate(None)

    clientIndex = clientList.index(searchClientById(clientList,operation["rentalCompleted"].getClientId()))
    clientList[clientIndex].changeRentedStatus()


def redoCompleteRental(operation,movieList,clientList,rentalList):
    rentalIndex = rentalList.index(searchRentalWithId(rentalList,operation["rentalCompleted"].getRentalId()))
    rentalList[rentalIndex].setReturnDate(operation["returnDate"])

    clientIndex = clientList.index(searchClientById(clientList,operation["rentalCompleted"].getClientId()))
    clientList[clientIndex].changeRentedStatus()

def undoRemoveClient(operation,movieList,clientList,rentalList):
    addClientAtIndex(clientList,operation["clientRemoved"],operation["index"])

def redoRemoveClient(operation,movieList,clientList,rentalList):
    removeClient(clientList,operation["clientRemoved"].getClientId())

def undoRemoveMovie(operation,movieList,clientList,rentalList):
    addMovieAtIndex(movieList,operation["movieRemoved"],operation["index"])

def redoRemoveMovie(operation,movieList,clientList,rentalList):
    removeMovie(movieList,operation["movieRemoved"].getMovieId())

def undoModifyMovie(operation,movieList,clientList,rentalList):
    movie = operation["movieModified"]
    parameterModified = operation["parameterModified"]
    oldValue = operation["oldValue"]

    index = movieList.index(movie)

    if parameterModified == "title":
        movieList[index].setMovieTitle(oldValue)
    elif parameterModified == "description":
        movieList[index].setMovieDescription(oldValue)
    elif parameterModified == "genre":
        movieList[index].setMovieGenre(oldValue)

def redoModifyMovie(operation,movieList,clientList,rentalList):
    movie = operation["movieModified"]
    parameterModified = operation["parameterModified"]
    newValue = operation["newValue"]

    index = movieList.index(movie)

    if parameterModified == "title":
        movieList[index].setMovieTitle(newValue)
    elif parameterModified == "description":
        movieList[index].setMovieDescription(newValue)
    elif parameterModified == "genre":
        movieList[index].setMovieGenre(newValue)

def undoModifyClient(operation,movieList,clientList,rentalList):
    client = operation["clientModified"]
    oldValue = operation["oldValue"]

    index = clientList.index(client)
    clientList[index].setClientName(oldValue)

def redoModifyClient(operation,movieList,clientList,rentalList):
    client = operation["clientModified"]
    newValue = operation["newValue"]

    index = clientList.index(client)
    clientList[index].setClientName(newValue)