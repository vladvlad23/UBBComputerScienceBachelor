def removeClient(clientList,clientId):
    '''
    Function removes client from a list. If client is not there, throw an exception.

    :param clientId: client to be removed
    :param clientList: list of clients
    '''

    exists=False

    for client in clientList:
        if client.getClientId() == clientId:
            clientList.remove(client)
            exists = True
    if not exists:
        raise Exception("Client not in list")

def removeRental(rentalList,rentalId):
    '''
    Function removes rental from a list. If rental is not there, throw an exception.

    :param rentalId: rental to be removed
    :param rentalList: list of rentals
    '''

    exists=False

    for rental in rentalList:
        if rental.getRentalId() == rentalId:
            rentalList.remove(rental)
            exists = True
    if not exists:
        raise Exception("Rental not in list")



def removeMovie(movieList,movieId):
    '''
    The function will remove the given movie from the list

    :param movieId: the id of the movie to be removed
    :param movieList: the list of movies in the store
    :return:
    '''
    for movie in range(0,len(movieList)):
        if movieList[movie].getMovieId() == movieId:
            movieList.remove(movieList[movie])
            return
    raise Exception("Movie id not found")

