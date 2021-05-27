from Assignment_05_07_refactored.domain.Movie import Movie
from Assignment_05_07_refactored.repository.RandomGenerator import RandomMovieGenerator
from Assignment_05_07_refactored.repository.MovieRepository import MovieRepository
from Assignment_05_07_refactored.domain.Exceptions import NotFoundException,FileRepositoryException


class MovieCSVRepository(MovieRepository):

    def __init__(self,fileName="movies.txt"):
        MovieRepository.__init__(self)
        self.__fileName = fileName
        self.__loadFromFile()

    def add(self,movie):
        MovieRepository.add(self,movie)
        self.storeToFile()

    def addStartingMovies(self):
        MovieRepository.addStartingMovies(self)
        self.storeToFile()

    def addMovieAtIndex(self, movie, index):
        '''
        Function will add a movie in the list at a specific index
        :param movie: the movie which should be inserted
        :param index: the index where it should be inserted
        '''
        MovieRepository.insert(self,movie,index)
        self.storeToFile()

    def exists(self,movieId):
        return MovieRepository.exists(self,movieId)

    def findMovieById(self,movieId):
        return MovieRepository.findMovieById(self,movieId)

    def size(self):
        return MovieRepository.size(self)

    def remove(self,movieId):
        MovieRepository.remove(self, movieId)
        self.storeToFile()

    def modifyTitle(self,movieId,newTitle):
        MovieRepository.modifyTitle(self,movieId,newTitle)
        self.storeToFile()

    def modifyDescription(self,movieId,newDescription):
        MovieRepository.modifyDescription(self,movieId,newDescription)
        self.storeToFile()

    def modifyGenre(self,movieId,newGenre):
        MovieRepository.modifyGenre(self,movieId,newGenre)
        self.storeToFile()

    def getBiggestMovieId(self):
        '''
        Function returns the maximum movie id in a list
        :param movieList: the given list
        :return: the maximum id
        '''
        return MovieRepository.getBiggestMovieId(self)

    def getAllMovies(self):
        return MovieRepository.getAllMovies(self)


    def searchMovies(self,searchString, criteria):
        return MovieRepository.searchMovies(self,searchString,criteria)

    def moviesDescendingByRentingTimes(self, rentalList):
        return MovieRepository.moviesDescendingByRentingDays(self,rentalList)

    def moviesDescendingByRentingDays(self, rentalList):
        return MovieRepository.moviesDescendingByRentingDays(self,rentalList)

    def __loadFromFile(self):
        file = open(self.__fileName, "r")
        try:
            line = file.readline().strip()
            while line != "":
                attributes = line.split(";")
                movie = Movie(int(attributes[0]),attributes[1],attributes[2],attributes[3])
                MovieRepository.add(self,movie)
                line = file.readline().strip()
        except IOError:
            raise FileRepositoryException("Movie File error")
        finally:
            file.close()

    def storeToFile(self):
        file = open(self.__fileName,"w")
        movieList = MovieRepository.getAllMovies(self)
        for movie in movieList:
            stringToStore = str(movie.getMovieId()) + ";" + movie.getMovieTitle() + ";" + \
            movie.getMovieDescription() + ";" + movie.getMovieGenre()

            stringToStore = stringToStore + "\n"
            file.write(stringToStore)

        file.close()




