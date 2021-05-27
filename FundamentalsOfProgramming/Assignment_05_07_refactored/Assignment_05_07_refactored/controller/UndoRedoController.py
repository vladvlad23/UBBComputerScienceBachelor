class UndoRedoController:

    def __init__(self,MovieRepository,ClientRepository,RentalRepository):
        self.__MovieRepository = MovieRepository
        self.__ClientRepository = ClientRepository
        self.__RentalRepository = RentalRepository
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

    def insertClientRemovalOperation(self, removedClient):
        dictionary = {"operation":"removeClient","clientRemoved":removedClient}
        self.__list.insert(self.__index+1,dictionary)
        self.__index+=1

    def insertMovieRemovalOperation(self, removedMovie):
        dictionary = {"operation":"removeMovie","movieRemoved":removedMovie}
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

    def undo(self):
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

        self.performUndoOperation(self.__list[self.__index])
        self.__index -=1

    def redo(self):
        '''
        The function will check if a redo operation is possible, otherwise throw an exception
        If it's possible, it will update the index and call the method to perform the operation
        '''
        if self.__index+1 > len(self.__list)-1:
            raise Exception("No more redos")
            return
        self.__index+=1
        self.performRedoOperation(self.__list[self.__index])

    def performUndoOperation(self, operation):
        '''
        The method will perform an undo operation
        :param operation: the operation to be reversed
        :param movieList: the list movies
        :param clientList: the list of clients
        :param rentalList: the list of rentals
        '''
        possibleOperations = {"addMovie":self.undoAddMovie,"addClient":self.undoAddClient,
                              "addRental":self.undoAddRental,"completeRental":self.undoCompleteRental,
                              "removeClient":self.undoRemoveClient,"removeMovie":self.undoRemoveMovie,
                              "modifyMovie":self.undoModifyMovie,"modifyClient":self.undoModifyClient}

        if operation["operation"] in possibleOperations:
            possibleOperations[operation["operation"]](operation)

        self.__MovieRepository.storeToFile()
        self.__ClientRepository.storeToFile()
        self.__RentalRepository.storeToFile()



    def performRedoOperation(self,operation):
        '''
        The method will perform a redo operation
        :param operation: the operation to be performed
        :param movieList: the list movies
        :param clientList: the list of clients
        :param rentalList: the list of rentals
        :return:
        '''
        possibleOperations = {"addMovie":self.redoAddMovie,"addClient":self.redoAddClient,
                              "addRental":self.redoAddRental,"completeRental":self.redoCompleteRental,
                              "removeClient":self.redoRemoveClient,"removeMovie":self.redoRemoveMovie,
                              "modifyMovie":self.redoModifyMovie,"modifyClient":self.redoModifyClient}
        if operation["operation"] in possibleOperations:
            possibleOperations[operation["operation"]](operation)

        self.__MovieRepository.storeToFile()
        self.__ClientRepository.storeToFile()
        self.__RentalRepository.storeToFile()


    '''
    The following methods will call the opposite functions for redoing/undoing the given operations
    '''
    def undoAddMovie(self,operation):
        self.__MovieRepository.remove(operation["movieAdded"].getMovieId())

    def redoAddMovie(self,operation):
        self.__MovieRepository.add(operation["movieAdded"])

    def undoAddClient(self,operation):
        self.__ClientRepository.remove(operation["clientAdded"].getClientId())

    def redoAddClient(self,operation):
        self.__ClientRepository.add(operation["clientAdded"])

    def undoAddRental(self,operation):
        self.__RentalRepository.remove(operation["rentalAdded"].getRentalId())
        self.__ClientRepository.changeRentalStatus(operation["rentalAdded"].getClientId())

    def redoAddRental(self,operation):
        self.__RentalRepository.add(operation["rentalAdded"])
        self.__ClientRepository.changeRentalStatus(operation["rentalAdded"].getClientId())

    def undoCompleteRental(self,operation):
        self.__RentalRepository.uncomplete(operation["rentalCompleted"].getRentalId())
        self.__ClientRepository.changeRentalStatus(operation["rentalCompleted"].getClientId())


    def redoCompleteRental(self,operation):
        rental = operation["rentalCompleted"]
        self.__RentalRepository.complete(rental.getRentalId(),operation["returnDate"])
        self.__ClientRepository.changeRentalStatus(rental.getClientId())

    def undoRemoveClient(self,operation):
        self.__ClientRepository.add(operation["clientRemoved"])

    def redoRemoveClient(self,operation):
        self.__ClientRepository.remove(operation["clientRemoved"].getClientId())

    def undoRemoveMovie(self,operation):
        self.__MovieRepository.add(operation["movieRemoved"])

    def redoRemoveMovie(self,operation):
        self.__MovieRepository.remove(operation["movieRemoved"].getMovieId())

    def undoModifyMovie(self,operation):
        movie = operation["movieModified"]
        parameterModified = operation["parameterModified"]
        oldValue = operation["oldValue"]

        id = movie.getMovieId()

        if parameterModified == "title":
            self.__MovieRepository.modifyTitle(id,oldValue)
        elif parameterModified == "description":
            self.__MovieRepository.modifyDescription(id,oldValue)
        elif parameterModified == "genre":
            self.__MovieRepository.modifyGenre(id,oldValue)

    def redoModifyMovie(self,operation):
        movie = operation["movieModified"]
        parameterModified = operation["parameterModified"]
        newValue = operation["newValue"]
        id = movie.getMovieId()

        if parameterModified == "title":
            self.__MovieRepository.modifyTitle(id,newValue)
        elif parameterModified == "description":
            self.__MovieRepository.modifyDescription(id,newValue)
        elif parameterModified == "genre":
            self.__MovieRepository.modifyGenre(id,newValue)


    def undoModifyClient(self,operation):
        client = operation["clientModified"]
        oldValue = operation["oldValue"]

        self.__ClientRepository.modifyName(client.getClientId(),oldValue)

    def redoModifyClient(self,operation):
        client = operation["clientModified"]
        newValue = operation["newValue"]

        self.__ClientRepository.modifyName(client.getClientId(),newValue)