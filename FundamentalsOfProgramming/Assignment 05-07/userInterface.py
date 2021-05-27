from operations.add import *
from operations.list import *
from operations.remove import *
from operations.search import *
from operations.statistics import *
from dateOperations import *

def listMoviesUI(movieList,clientList,rentalList,undoRedoController):
    '''
    Function will call the listMovies function to which it will transmit the first parameter
    :return:
    '''
    listMovies(movieList)

def listClientsUI(movieList,clientList,rentalList,undoRedoController):
    '''
    Function will call the listClients function to which it will transmit the second parameter
    :return:
    '''
    listClients(clientList)


def listRentalsUI(movieList,clientList,rentalList,undoRedoController):
    '''
    Function will call the listRentals function to which it will transmit the third parameter
    :return:
    '''
    listRentals(rentalList)

def addClientUI(movieList,clientList,rentalList,undoRedoController):
    '''
    Function will get the name of a new client and create an instance of it containing
    the id and name Client(clientId,clientName)
    '''
    clientName = input("Name: ")
    if len(clientList)!=0:
        clientId = clientList[-1].getClientId()+1
    else:
        clientId = 1

    clientToAdd = Client(clientId,clientName)
    addClient(clientList,clientToAdd)

    undoRedoController.insertClientAddOperation(clientToAdd)

def addRentalUI(movieList,clientList,rentalList,undoRedoController):
    try:
        clientId = int(input("Input the id of the client: "))
        movieId = int(input("Input the id of the movie: "))
        dayOfRenting = int(input("Day of rental: "))
        monthOfRenting = int(input("Month of rental: "))
        yearOfRenting = int(input("Year of rental: "))
        dateOfRental = date(yearOfRenting,monthOfRenting,dayOfRenting)
    except ValueError:
        print("Invalid input")
        return
    if checkRentalById(clientList,clientId):
        print("Client already rented something")
        return


    dateOfRental = date(yearOfRenting,monthOfRenting,dayOfRenting)

    print("When does the client want to return the movie")

    try:
        dayDue = int(input("Day: "))
        monthDue = int(input("Month: "))
        yearDue = int(input("Year: "))
    except ValueError:
        print("invalid input")

    dueDate = date(yearDue,monthDue,dayDue)

    if dueDate<dateOfRental:
        print("Due date is before date of rental")
        return
    if dateOfRental>getCurrentDate():
        print("We're not there yet")
        return
    if len(rentalList)==0:
        rentalId = 1
    else:
        rentalId = rentalList[-1].getRentalId() + 1
    rentalToAdd = Rental(rentalId,movieId,clientId,dateOfRental,dueDate)

    addRental(rentalToAdd,rentalList)
    changeRentalStatus(clientList,clientId)

    undoRedoController.insertRentalAddOperation(rentalToAdd)



def addMovieUI(movieList,clientList,rentalList,undoRedoController):
    movieTitle = input("Movie name please: ")
    movieDescription = input("Movie description please: ")
    movieGenre = input("Movie genre please: ")
    if len(movieList)!=0:
        movieId = movieList[-1].getMovieId()+1 #so we get the last movie id and add one (for convenience)
    else:
        movieId = 1

    movieToAdd = Movie(movieId,movieTitle,movieDescription,movieGenre)


    try:
        addMovie(movieToAdd,movieList)
    except Exception as e:
        print(e)
        return

    undoRedoController.insertMovieAddOperation(movieToAdd)

def completeRentalUI(movieList,clientList,rentalList,undoRedoController):
    try:
        rentalId = int(input("Input the id of the rental you would you like to complete: "))
        returnDay = int(input("Day: "))
        returnMonth = int(input("Month: "))
        returnYear = int(input("Year: "))
    except ValueError:
        print("Invalid date input")

    try:
        if searchRentalWithId(rentalList,rentalId).isReturned():
            print("Already completed")
            return
        returnDate = date(returnYear,returnMonth,returnDay)
        completeRental(rentalList,rentalId,returnDate)
    except ValueError:
        print("invalid input")

    undoRedoController.insertRentalCompletionOperation(searchRentalWithId(rentalList,rentalId),returnDate)




def modifyMovieUI(movieList,clientList,rentalList,undoRedoController):
    try:
        movieId = int(input("Input the id of the movie that must be modified"))
    except ValueError:
        print("Invalid input")
        return
    modifyOption = input("What would you like to modify? (title/description/genre)")
    value = input("What to replace with? ")
    try:
        movieIndex = getMovieIndex(movieList, movieId)
    except Exception as e:
        print(e)
        return
    if modifyOption == "title":
        undoRedoController.insertMovieModifyOperation(movieList[movieIndex],"title",
                                                      movieList[movieIndex].getTitle(),value)
        movieList[movieIndex].setMovieTitle(value)
    elif modifyOption == "description":
        undoRedoController.insertMovieModifyOperation(movieList[movieIndex], "description",
                                                      movieList[movieIndex].getDescription(),value)
        movieList[movieIndex].setMovieDescription(value)
    elif modifyOption == "genre":
        undoRedoController.insertMovieModifyOperation(movieList[movieIndex], "genre",
                                                      movieList[movieIndex].getGenre(),value)
        movieList[movieIndex].setMovieGenre(value)
    else:
        print("Invalid input")

def modifyClientUI(movieList, clientList, rentalList,undoRedoController):
    try:
        clientId = int(input("The id of the client "))
    except ValueError:
        print("Invalid client id")
        return
    try:
        client = searchClientById(clientList,clientId)
    except Exception as e:
        print(e)
        return

    name = input("What is the name of the client now? ")

    undoRedoController.insertClientModifyOperation(client,client.getName(),name)
    client.setClientName(name)



def modifyRentalUI(movieList,clientList,rentalList,undoRedoController):
    print()
    rentalId = int(input("Id of rental: "))

    rental = searchInRentalList(rentalList,rentalId)

    if rental.isReturned()==True:
        print("Already returned")
        return

    try:
        returnDay = int(input("Return day: "))
        returnMonth = int(input("Return Month: "))
        returnYear = int(input("Return year: "))
    except ValueError:
        print("Wrong input")
        return

    returnDate = date(returnYear,returnMonth,returnDay)
    completeRental(rentalList,rentalId)

def removeClientUI(movieList,clientList,rentalList,undoRedoController):
    clientId = int(input("Input the id of the client you would like to remove: "))

    try:
        removedClient = searchClientById(clientList,clientId)

    except Exception as e:
        print(e)
        return

    undoRedoController.insertClientRemovalOperation(removedClient,getClientIndex(clientList,clientId))
    removeClient(clientList,clientId)

def removeMovieUI(movieList,clientList,rentalList,undoRedoController):
    movieId = int(input("Input the id of the movie you would like to remove: "))
    try:
        removedMovie = searchMovieWithId(movieList,movieId)
    except Exception as e:
        print(e)
        return

    undoRedoController.insertMovieRemovalOperation(removedMovie,getMovieIndex(movieList,movieId))
    removeMovie(movieList,movieId)


def searchClientUI(movieList,clientList,rentalList,undoRedoController):
    criteria = input("Do you want to search by id or name?")
    if criteria == "id":
        id = int(input("Please input id to search: "))
        try:
            result = searchClientById(clientList,id)
        except Exception as e:
            print(e)
            return
        printClient(result)
        return
    elif criteria =="name":
        name = input("Please input name to search: ")
        result = searchClientsByName(name, clientList)
    else:
        print("Invalid input")
        return
    if len(result)==0:
        print("No client by that id")
    else:
        listClients(result)

def searchMovieUI(movieList,clientList,rentalList,undoRedoController):
    criteria = input("What criteria would you like to use ? ")
    string = input("What would you like to search ? ")

    possibleCriterias = {"title":Movie.getTitle,"id":Movie.getMovieId,
                         "description":Movie.getDescription,"genre":Movie.getGenre}
    if criteria in possibleCriterias:
        results = searchMovies(string,possibleCriterias[criteria],movieList)
    else:
        print("Invalid input")
        return

    listMovies(results)

def mostRentedUI(movieList,clientList,rentalList,undoRedoController):
    type = input("times/days?")
    if type == "times":
        result = moviesDescendingByRentingTimes(movieList,rentalList)
        listMovies(result)
    elif type == "days":
        result = moviesDescendingByRentingDays(movieList,rentalList)
        listMovies(result)
    else:
        print("invalid input ")

def mostActiveUI(movieList,clientList,rentalList,undoRedoController):
    result = clientsDescendingByActivity(clientList,rentalList)
    listClients(result)

def lateRentalsUI(movieList,clientList,rentalList,undoRedoController):
    result = lateRentals(movieList,rentalList)
    listMovies(result)

def undoOperationUI(movieList,clientList,rentalList,undoRedoController):
    try:
        undoRedoController.undo(movieList,clientList,rentalList)
    except Exception as e:
        print(e)

def redoOperationUI(movieList,clientList,rentalList,undoRedoController):
    try:
        undoRedoController.redo(movieList,clientList,rentalList)
    except Exception as e:
        print(e)

def parseInput(input,movieList,clientList,rentalList,undoRedoController):
    commands = {"list movies":listMoviesUI,"list clients":listClientsUI,
                "list rentals":listRentalsUI,"add movie":addMovieUI,"add client":addClientUI,
                "add rental":addRentalUI,"complete rental":completeRentalUI,
                "remove client":removeClientUI,"remove movie":removeMovieUI,
                "modify movie":modifyMovieUI,"modify client":modifyClientUI,
                "search client":searchClientUI,"search movie":searchMovieUI,
                "most rented":mostRentedUI,"most active":mostActiveUI,
                "late rentals":lateRentalsUI,"undo":undoOperationUI,"redo":redoOperationUI}

    help = "The following commands are valid:\n" \
           "-list movies to list all movies\n" \
           "-list clients to list all clients\n" \
           "-list rentals to list all rentals\n" \
           "-add movie to add a movie to list\n" \
           "-add client to add a client to list\n" \
           "-add rental to add a rental\n" \
           "-complete rental to add the return date of a rental\n"\
           "-remove client to remove a client\n" \
           "-remove movie to remove a movie\n"\
           "-modify movie to modify anything but the ID (as it corresponds to ISAN\n"\
           "-modify client to modify the name of the client\n"\
           "-search client \n"\
            "-search movie \n"\
            "-most rented \n"\
            "-most active \n"\
            "-late rentals \n"\
           "-exit to exit program\n" \
           "-help to prin the help menu \n" \
           "note that the ID of the movie functions like ISAN (International Standard Audiovisual Number. It can't be changed" #mention that id = ISAN (international standard audiovisual number)

    if input in commands:
            commands.get(input)(movieList,clientList,rentalList,undoRedoController)

    elif input == "exit":
        return True
    elif input == "help":
        print(help)
    else:
        print("Invalid input")
    return False