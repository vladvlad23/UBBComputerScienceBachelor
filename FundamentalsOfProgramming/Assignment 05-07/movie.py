class Movie:

    def __init__(self,movieId,title,description,genre):
        '''
        Function will initialise the given movie with the parameters given

        :param movieID: integer. Contains the id of the movie in the list
        :param title: string. The title of the movie
        :param description: string. Small description of movie
        :param genre: string

        '''

        self.__movieId = int(movieId)
        self.__title = title
        self.__description = description
        self.__genre = genre

    def getMovieId(self):
        return self.__movieId

    def getTitle(self):
        return self.__title

    def getDescription(self):
        return self.__description

    def getGenre(self):
        return self.__genre

    def setMovieId(self,newMovieId):
        self.__movieId = newMovieId

    def setMovieTitle(self,newMovieTitle):
        self.__title = newMovieTitle

    def setMovieDescription(self,newMovieDescription):
        self.__description = newMovieDescription

    def setMovieGenre(self,newMovieGenre):
        self.__genre = newMovieGenre


def exists(movie,movieList):
    '''
    Function checks wheter the movie exists in the movieList
    :param movie: movie to check
    :param movieList: where to check if the movie exists
    :return:
    '''
    for i in movieList:
        if movie.getMovieId() == i.getMovieId() or movie.getTitle()==i.getTitle():
            return True
    return False

def searchMovieWithId(movieList, id):
    '''
    The function will receive an id and a list of movies and it will return the movie in the
    movie list corresponding to that id

    :param movieList: the list of movies
    :param id: the id of the movie which has to be returned
    :return: the movie which fits the given id
    '''
    for movie in range(0,len(movieList)):
        if movieList[movie].getMovieId()==id:
            return movieList[movie]

    raise Exception("Movie not found")

def getMovieIndex(movieList,movieId):
    '''
    The function will receive an id and a list of movies and it will return the index
    where the movie is found in the movie list

    :param movieList: the list of movies
    :param movieId: the id of the movie which has to be returned
    :return: the index of the movie which fits the given id
    '''
    for movie in range(0,len(movieList)):
        if movieList[movie].getMovieId()==movieId:
            return movie

    raise Exception("Movie not found")

def getBiggestMovieId(movieList):
    '''
    Function returns the maximum movie id in a list
    :param movieList: the given list
    :return: the maximum id
    '''
    maxId = int(0)
    for movie in movieList:
        if movie.getMovieId()>maxId:
            maxId = movie.getMovieId()

    return maxId