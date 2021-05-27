class Movie:

    def __init__(self,movieId,movieTitle,movieDescription,movieGenre):
        self.__movieId = movieId
        self.__movieTitle = movieTitle
        self.__movieDescription = movieDescription
        self.__movieGenre = movieGenre

    def getMovieId(self):
        return self.__movieId

    def getMovieTitle(self):
        return self.__movieTitle

    def getMovieDescription(self):
        return self.__movieDescription

    def getMovieGenre(self):
        return self.__movieGenre

    def setMovieId(self,movieId):
        self.__movieId = movieId

    def setMovieTitle(self,movieTitle):
        self.__movieTitle = movieTitle

    def setMovieDescription(self,movieDescription):
        self.__movieDescription = movieDescription

    def setMovieGenre(self,movieGenre):
        self.__movieGenre = movieGenre


