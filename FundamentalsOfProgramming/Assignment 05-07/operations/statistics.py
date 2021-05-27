from movie import searchMovieWithId,getBiggestMovieId
from client import searchClientById
from rental import searchRentalWithId
from dateOperations import *

def moviesDescendingByRentingTimes(movieList,rentalList):
    '''
    Procedure : will create a list counting how many times each movie has been rented and then
    will form an id list containing the indexes from highest to lowest
    :param movieList = list of movies
    :param rentalList = list of rentals
    :return list of movies in descending order by renting times
    '''
    idList = []
    newList = rentalList[:]
    frequencyList = [0] * (2*getBiggestMovieId(movieList))
    for rental in newList:
        frequencyList[rental.getMovieId()]+=1

    for i in range(1,len(frequencyList)):

        maxId = frequencyList.index(max(frequencyList)) #get the index with max apparitions
        if maxId>0:
            idList.append(maxId)
        frequencyList[maxId] = -1

    newList = []
    for id in range(0,len(idList)): #go through id list
        try:
            searchMovieWithId(movieList,idList[id])
            newList.append(searchMovieWithId(movieList, idList[id]))
        except Exception: #this means that the list indexes have been surpassed
            return newList
    return newList

def moviesDescendingByRentingDays(movieList,rentalList):
    '''
    The function will receive a list of movies and a list of rentals. It will return a list of
    movie sorted descendingly by renting days
    :param movieList: the movie list
    :param rentalList: the rental list
    :return: list of movies in the given order
    '''
    # implement dictionary where the first element is the movie id and the second
    # element consist of the renting days
    movieDictList = []
    for movie in movieList:
        movieDictionary = {"movieId":movie.getMovieId(),"days":int(0)}
        for rental in rentalList:
            if rental.getMovieId() == movie.getMovieId():
                movieDictionary["days"] += getRentedDays(rental)

        movieDictList.append(movieDictionary)

    movieDictList = sorted(movieDictList,key=lambda movieDict: movieDict.get("days"),reverse=True)

    result = []
    for movie in movieDictList:
        result.append(searchMovieWithId(movieList, movie["movieId"]))

    return result


def getRentedDays(rental):
    '''

    :param rental: the rental
    :return: the number of days a movie has been rented. If not returned yet, return -1
    '''
    try:
        return rental.getReturnDate().getDay() - rental.getRentedDate().getDay()
    except Exception as e:
        return -1;

def moviesDescendingByRentingDays(movieList,rentalList):
    '''
    The function will receive a list of movies and a list of rentals. It will return a list of
    movie sorted descendingly by renting days
    :param movieList: the movie list
    :param rentalList: the rental list
    :return: list of movies in the given order
    '''
    # implement dictionary where the first element is the movie id and the second
    # element consist of the renting days
    movieDictList = []
    for movie in movieList:
        movieDictionary = {"movieId":movie.getMovieId(),"days":int(0)}
        for rental in rentalList:
            if rental.getMovieId() == movie.getMovieId():
                movieDictionary["days"] += getRentedDays(rental)

        movieDictList.append(movieDictionary)

    movieDictList = sorted(movieDictList,key=lambda movieDict: movieDict.get("days"),reverse=True)

    result = []
    for movie in movieDictList:
        result.append(searchMovieWithId(movieList, movie["movieId"]))

    return result

def clientsDescendingByActivity(clientList,rentalList):
    '''
    The function will receive a list of clients and a list of rentals. It will return a list of
    clients sorted by renting days
    :param clientList: the movie list
    :param rentalList: the rental list
    :return: list of clients in the given order
    '''
    # implement dictionary where the first element is the movie id and the second
    # element consist of the renting days
    clientDictList= []
    for client in clientList:
        clientDictionary = {"clientId":client.getClientId(),"days":int(0)}
        for rental in rentalList:
            if rental.getClientId() == client.getClientId():
                clientDictionary["days"] += getRentedDays(rental)

        clientDictList.append(clientDictionary)

    clientDictList = sorted(clientDictList,key=lambda clientDict: clientDict.get("days"),reverse=True)

    result = []
    for client in clientDictList:
        result.append(searchClientById(clientList, client["clientId"]))

    return result


def lateRentals(movieList,rentalList):
    '''
    Function will return a list of movies that are late (the current date is past the due date and
    the client hasn't returned it yet
    :param movieList: the movie list from where the movies will be added to the result
    :param rentalList: the rental list where everything is checked
    '''
    rentalDictList = []
    for rental in rentalList:
        if not rental.isReturned():
            if (getCurrentDate() - rental.getDueDate()).days > 0:
                lateRental = {"rentalId":rental.getRentalId(),"daysLate":processLateDays(rental)}
                rentalDictList.append(lateRental)

    rentalDictList = sorted(rentalDictList,key=lambda rentalDict:rentalDict.get("daysLate"),reverse=True)

    result = []
    for rental in rentalDictList:
        rentalObject = searchRentalWithId(rentalList,rental.get("rentalId"))
        result.append(searchMovieWithId(movieList,rentalObject.getMovieId()))

    return result


def processLateDays(rental):
    '''
    Function will return how many days have passed since the due date of a given rental until today
    :param rental: the rental to be processed
    '''
    return untilToday(rental.getDueDate())
