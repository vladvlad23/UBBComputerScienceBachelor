def listClients(clientList):
    '''
    Function will print a list of clients by calling printClient(client) for each of them
    :param clientList: the list of clients
    '''
    for client in clientList:
        printClient(client)



def printClient(client):
    '''
    Function wil print a client
    :param client: the client whose data should be printed
    '''
    print("ID - ", client.getClientId())
    print("Name - ", client.getName())
    print("Has rented - ", "Yes" if client.getRentedStatus() else "no")
    print()




def listRentals(rentalList):
    '''
    Function will call the printRental function for every member of rentalList
    :param rentalList: list of rental instances
    :return:
    '''
    for i in rentalList:
        printRental(i)




def printRental(rental):
    '''
    Function will print a rental
    :param rental: the rental which should be printed
    '''
    print("Rental id: ", rental.getRentalId())
    print("Movie id: ", rental.getMovieId())
    print("Client id: ", rental.getClientId())

    print("Rented date: ", end="")
    printDate(rental.getRentedDate())

    print("Due date: ", end="")
    printDate(rental.getDueDate())

    if not (rental.isReturned()):
        print("Not returned yet")
    else:
        print("Returned date: ", end="")
        printDate(rental.getReturnDate())
    print()


def printDate(date):
    '''
    The function will print a date (might be considered redundant as the module itself is printable)
    but it's about the format
    :param date: date to be printed
    '''
    print(date.day, date.month, date.year, sep="/")

def listMovies(movieList):
    '''
    Function will list all movies in the list
    :param movieList: the list containing the movies
    :return:
    '''

    print("The movies are ")

    for movie in movieList:
        printMovie(movie)


def printMovie(movie):
    print("ID - ",movie.getMovieId())
    print("Title - ",movie.getTitle())
    print("Description - ",movie.getDescription())
    print("Genre - ",movie.getGenre())
    print()

