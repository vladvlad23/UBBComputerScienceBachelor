def searchClientsByName(searchString, clientList):
    '''
    Function will search the given string in lowercase in the names of the clients in the list
    (accessed by client.getName()
    :param searchString: the string to be searched
    :param clientList: the list of clients where it should be searched
    :return: list of clients with names matching the given string
    '''
    resultList = []
    searchString = searchString.lower()
    for client in clientList:
        if searchString in client.getName().lower():
            resultList.append(client)

    return resultList


def searchMovies(searchString,criteria,movieList):
    '''
    Function will search the given string in lowercase by the criteria given in the
    criteria argument of the movies in the list
    (accessed by movie.getTitle()
    :param searchString: the string to be searched
    :param movieList: the list of movies where it should be searched
    :return: the list of movies corresponding to the criteria
    '''
    resultList = []
    str(searchString)
    searchString = searchString.lower()
    for movie in movieList:
        if type(criteria(movie)) is int:
            tempString = str(criteria(movie))
        else:
            tempString = criteria(movie)
        if searchString in tempString.lower():
            resultList.append(movie)

    return resultList