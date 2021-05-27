from Assignment_05_07_refactored.domain.Exceptions import *
from Assignment_05_07_refactored.domain.Movie import Movie

class MovieValidator:

    def __init__(self,repository):
        self.__repository = repository

    def validate(self,movie):
        error = []

        try:
            movieId = int(movie.getMovieId())
        except ValueError:
            error.append("Id must be an integer")


        if movie.getMovieTitle() == "":
            error.append("Title can't be empty\n")
        if movie.getMovieDescription() == "":
            error.append("Description can't be empty\n")
        if movie.getMovieGenre() == "":
            error.append("Genre can't be empty\n")

        if error != []:
            if self.__repository.exists(movie.getMovieId()):
                error.append("Movie Already exists\n")
            raise ValidationException(error)

    def validateTitle(self,movieTitle):
        '''
        Function will check if the title isn't empty
        :param movieTitle: check if the string is empty
        '''
        if movieTitle == "":
            raise ValidationException("Empty title")

    def validateDescription(self,movieDescription):
        '''
        Function will check if the description isn't empty
        :param movieDescription: check if the string is empty
        '''
        if movieDescription == "":
            raise ValidationException("Empty description")

    def validateGenre(self,movieGenre):
        '''
        Function will check if the genre isn't empty
        :param movieGenre: check if the string is empty
        '''
        if movieGenre == "":
            raise ValidationException("Empty genre")

    def validateMovieCriteria(self,criteria):
        if criteria!="title" and criteria!="description" and criteria!="genre":
            raise ValidationException("Invalid criteria")


