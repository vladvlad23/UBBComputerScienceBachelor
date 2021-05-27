class Console:

    def __init__(self,MovieController,ClientController,RentalController,UndoRedoController):
        self.__movieController = MovieController
        self.__clientController = ClientController
        self.__rentalController = RentalController
        self.__undoRedoController = UndoRedoController


    def __listMovies(self):

        list = self.__movieController.getAllMovies()
        for movie in list:
            self.__printMovie(movie)

    def __printMovie(self,movie):
        print("ID - ", movie.getMovieId())
        print("Title - ", movie.getMovieTitle())
        print("Description - ", movie.getMovieDescription())
        print("Genre - ", movie.getMovieGenre())
        print()

    def __listClients(self):

        list = self.__clientController.getAllClients()
        for client in list:
            self.__printClient(client)

    def __printClient(self,client):
        '''
        Function wil print a client
        :param client: the client whose data should be printed
        '''
        print("ID - ", client.getClientId())
        print("Name - ", client.getClientName())
        print("Has rented - ", "Yes" if client.getRentedStatus() else "no")
        print()

    def __listRentals(self):
        list = self.__rentalController.getAllRentals()

        for rental in list:
            self.__printRental(rental)

    def __printRental(self,rental):
        '''
        Function will print a rental
        :param rental: the rental which should be printed
        '''
        print("Rental id: ", rental.getRentalId())
        print("Movie id: ", rental.getMovieId())
        print("Client id: ", rental.getClientId())

        print("Rented date: ", end="")
        self.__printDate(rental.getRentedDate())

        print("Due date: ", end="")
        self.__printDate(rental.getDueDate())

        if not (rental.isReturned()):
            print("Not returned yet")
        else:
            print("Returned date: ", end="")
            self.__printDate(rental.getReturnDate())
        print()

    def __printDate(self,date):
        '''
        The function will print a date (might be considered redundant as the module itself is printable)
        but it's about the format
        :param date: date to be printed
        '''
        print(date.day, date.month, date.year, sep="/")

    def __addMovie(self):
        try:
            movieId = int(input("Movie id please: "))
        except ValueError:
            print("id must be integer")
            return
        movieTitle = input("Movie name please: ")
        movieDescription = input("Movie description please: ")
        movieGenre = input("Movie genre please: ")



        self.__movieController.create(movieId, movieTitle,movieDescription,movieGenre)

        movie = self.__movieController.findMovieById(movieId)

        self.__undoRedoController.insertMovieAddOperation(movie)


    def __addClient(self):
        clientId = input("Client id please: ")
        clientName = input("Client name please: ")


        try:
            id = int(clientId)
        except ValueError:
            print("Client id must be integer")
            return


        self.__clientController.create(id,clientName)
        client = self.__clientController.findClientById(id)
        self.__undoRedoController.insertClientAddOperation(client)

    def __addRental(self):

        try:
            rentalId = int(input("Input the id of the rental: "))
            clientId = int(input("Input the id of the client: "))
            movieId = int(input("Input the id of the movie: "))
            dayOfRenting = int(input("Day of rental: "))
            monthOfRenting = int(input("Month of rental: "))
            yearOfRenting = int(input("Year of rental: "))

            print("When does the client want to return the movie")

            dayDue = int(input("Day: "))
            monthDue = int(input("Month: "))
            yearDue = int(input("Year: "))
        except ValueError:
            print("Ids and dates must be integers")
            return

        self.__rentalController.create(rentalId,clientId,movieId,dayOfRenting,monthOfRenting,yearOfRenting,
                                       dayDue,monthDue,yearDue)

        self.__clientController.changeRentalStatus(clientId)

        rental = self.__rentalController.findRentalById(rentalId)
        self.__undoRedoController.insertRentalAddOperation(rental)


    def __completeRental(self):
        try:
            rentalId = int(input("Input the id of the rental you would you like to complete: "))
            returnDay = int(input("Day: "))
            returnMonth = int(input("Month: "))
            returnYear = int(input("Year: "))
        except ValueError:
            print("Invalid date")
            return

        self.__rentalController.complete(rentalId,returnYear,returnMonth,returnDay)

        rental = self.__rentalController.findRentalById(rentalId)
        clientId = rental.getClientId()
        self.__clientController.changeRentalStatus(clientId)

        date = self.__rentalController.findRentalById(rentalId).getReturnDate()
        self.__undoRedoController.insertRentalCompletionOperation(rental,date)

    def __removeClient(self):
        try:
            clientId = int(input("What client would you like to remove "))
        except ValueError:
            print("Client id must be integer")

        client = self.__clientController.findClientById(clientId)
        self.__clientController.remove(clientId)
        self.__undoRedoController.insertClientRemovalOperation(client)

    def __removeMovie(self):
        try:
            movieId = int(input("What movie would you like to remove "))
        except ValueError:
            print("Movie Id must be integer")
            return

        movie = self.__movieController.findMovieById(movieId)

        self.__movieController.remove(movieId)

        self.__undoRedoController.insertMovieRemovalOperation(movie)

    def __modifyMovie(self):
        try:
            movieId = int(input("Id of the movie to be modified "))
        except ValueError:
            print("Movie Id must be integer")
            return

        criteria = input("What criteria to use? title/genre/description ")
        newValue = input("New Value: ")

        if criteria=="title":
            oldValue = self.__movieController.findMovieById(movieId).getMovieTitle()
            self.__movieController.modifyMovieTitle(movieId,newValue)

        elif criteria=="description":
            oldValue = self.__movieController.findMovieById(movieId()).getMovieDescription()
            self.__movieController.modifyMovieDescription(movieId,newValue)
        elif criteria=="genre":
            oldValue = self.__movieController.findMovieById(movieId()).getMovieGenre()
            self.__movieController.modifyMovieGenre(movieId,newValue)

        else:
            print("Error at criteria")

        modifiedMovie = self.__movieController.findMovieById(movieId)

        self.__undoRedoController.insertMovieModifyOperation(modifiedMovie,criteria,oldValue,newValue)

    def __modifyClient(self):
        try:
            clientId = int(input("What is the id of the client you would like to modify? "))
        except ValueError:
            print("Id must be integer")

        newValue = input("What is the name of the client now? ")
        oldValue = self.__clientController.findClientById(clientId).getClientName()
        client = self.__clientController.findClientById(clientId)

        self.__clientController.modify(clientId,newValue)

        self.__undoRedoController.insertClientModifyOperation(client,oldValue,newValue)



    def __searchClient(self):
        name = input("What name would you like to loo for ? ")
        results = self.__clientController.searchClient(name)
        for client in results:
            self.__printClient(client)


    def __searchMovie(self):
        criteria = input("What criteria would you like to use? title/description/genre")
        text = input("what would you like to search for")
        results = self.__movieController.searchMovies(text,criteria)
        for movie in results:
            self.__printMovie(movie)


    def __mostRented(self):
        criteria = input("times/days?")
        if criteria == "times":
            rentalList = self.__rentalController.getAllRentals()
            result = self.__movieController.mostRentedByTimes(rentalList)

        elif criteria == "days":
            rentalList = self.__rentalController.getAllRentals()
            result = self.__movieController.mostRentedByDays(rentalList)

        else:
            print("Invalid criteria")
            return
        for movie in result:
            self.__printMovie(movie)

    def __mostActive(self):
        rentalList = self.__rentalController.getAllRentals()
        result = self.__clientController.clientsDescendingByActivity(rentalList)
        for client in result:
            self.__printClient(client)

    def __lateRentals(self):
        result = self.__rentalController.lateRentals()
        for rental in result:
            self.__printMovie(self.__movieController.findMovieById(rental.getMovieId()))

    def __undo(self):
        self.__undoRedoController.undo()

    def __redo(self):
        self.__undoRedoController.redo()

    def __readUserCommand(self):
        help = "The following commands are valid:\n" \
               "1. list all movies\n" \
               "2. list all clients\n" \
               "3. list all rentals\n" \
               "4. add a movie to list\n" \
               "5. add a client to list\n" \
               "6. add a rental\n" \
               "7. add the return date of a rental\n" \
               "8. remove a client\n" \
               "9. remove a movie\n" \
               "10. modify anything in movie but the ID (as it corresponds to ISAN\n" \
               "11. modify the name of the client\n" \
               "12. search client \n" \
               "13. search movie \n" \
               "14. most rented \n" \
               "15. most active \n" \
               "16. late rentals \n" \
               "17. undo \n"\
                "18.redo \n"\
               "000. exit program\n" \
               "note that the ID of the movie functions like ISAN (International Standard Audiovisual Number. It can't be changed"  # mention that id = ISAN (international standard audiovisual number)
        print(help)



    def startConsole(self):


        commands = {"1":self.__listMovies,"2":self.__listClients,"3":self.__listRentals,
                    "4":self.__addMovie,"5":self.__addClient,"6":self.__addRental,
                    "7":self.__completeRental,"8":self.__removeClient,"9":self.__removeMovie,
                    "10":self.__modifyMovie,"11":self.__modifyClient,
                    "12":self.__searchClient,"13":self.__searchMovie,
                    "14":self.__mostRented,"15":self.__mostActive,
                    "16":self.__lateRentals,"17":self.__undo,"18":self.__redo,
                    "0":self.__readUserCommand}

        while(True):

            userInput = input("Give input, please. 0 for menu ").strip()

            if userInput=="000":
                print("Bye bye")
                return
            elif userInput in commands:
                    commands[userInput]()
            else:
                print("Not a valid command")


