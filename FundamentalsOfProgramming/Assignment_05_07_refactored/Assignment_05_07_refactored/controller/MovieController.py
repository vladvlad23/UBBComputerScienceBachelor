from Assignment_05_07_refactored.domain.Movie import Movie
from Assignment_05_07_refactored.domain.Exceptions import NotFoundException,DuplicateException


class MovieController:

    def __init__(self,repository,validator):
        self.__repository = repository
        self.__validator = validator

    def create(self,movieId,movieTitle,movieDescription,movieGenre):
        '''
        Function will create,validate and store a movie
        :param movieId: id of the movie (must be integer)
        :param movieTitle: title of the movie
        :param movieDescription: description of the movie
        :param movieGenre: genre of the movie
        '''
        movie = Movie(movieId,movieTitle,movieDescription,movieGenre)


        self.__validator.validate(movie)

        try:
            movie = self.findMovieById(movieId)
            raise DuplicateException("Movie Id already exists \n \
            Or in case you tried to undo/redo, you probably added in the meantime something with that id")
        except NotFoundException:
            pass
        self.__repository.add(movie)

    def remove(self, movieId):
        '''
        The function will remove the given movie from the list

        :param movieId: the id of the movie to be removed

        '''
        if self.__repository.exists(movieId):
            self.__repository.remove(movieId)
        else:
            raise NotFoundException("Movie not found")

    def modifyMovieTitle(self,movieId, newTitle):
        '''
        Function will call the repo function to modify title after validation
        :param newTitle: the new title
        '''

        self.__validator.validateTitle(newTitle)

        if self.__repository.exists(movieId):
            self.__repository.modifyTitle(movieId,newTitle)
        else:
            raise NotFoundException("Movie not found")

    def modifyMovieDescription(self, movieId, newDescription):
        '''
        Function will call the repo function to modify description after validation
        :param newDescription: the new description
        '''

        self.__validator.validateDescription(newDescription)

        if self.__repository.exists(movieId):
            self.__repository.modifyDescription(movieId,newDescription)
        else:
            raise NotFoundException("Movie not found")

    def modifyMovieGenre(self, movieId, newGenre):
        '''
        Function will call the repo function to modify genre after validation
        :param newGenre: the new genre
        '''

        self.__validator.validateGenre(newGenre)

        if self.__repository.exists(movieId):
            self.__repository.modifyGenre(movieId,newGenre)
        else:
            raise NotFoundException("Movie not found")

    def getAllMovies(self):
        return self.__repository.getAllMovies()

    def searchMovies(self,searchString, criteria):
        self.__validator.validateMovieCriteria(criteria)

        if criteria=="title":
            return self.__repository.searchMovies(searchString,Movie.getMovieTitle)
        elif criteria=="description":
            return self.__repository.searchMovies(searchString,Movie.getMovieDescription)
        elif criteria=="genre":
            return self.__repository.searchMovies(searchString,Movie.getMovieGenre)

    def mostRentedByTimes(self,rentalList):
        return self.__repository.moviesDescendingByRentingTimes(rentalList)

    def mostRentedByDays(self,rentalList):
        return self.__repository.moviesDescendingByRentingDays(rentalList)

    def findMovieById(self, movieId):
        return self.__repository.findMovieById(movieId)







