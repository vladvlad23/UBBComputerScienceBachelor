from Assignment_05_07_refactored.domain.Movie import Movie
from Assignment_05_07_refactored.repository.RandomGenerator import RandomMovieGenerator
from Assignment_05_07_refactored.repository.MovieRepository import MovieRepository
from Assignment_05_07_refactored.domain.Exceptions import NotFoundException,FileRepositoryException
import pickle

class MoviePickleRepository(MovieRepository):

    def __init__(self,fileName="movies.pickle"):
        MovieRepository.__init__(self)
        self.__fileName = fileName
        self.__loadFromFile()

    def add(self,movie):
        MovieRepository.add(self,movie)
        self.storeToFile()

    def addMovieAtIndex(self, movie, index):
        '''
        Function will add a movie in the list at a specific index
        :param movie: the movie which should be inserted
        :param index: the index where it should be inserted
        '''
        MovieRepository.insert(self,movie,index)
        self.storeToFile()

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

    def addStartingMovies(self):
        MovieRepository.addStartingMovies(self)
        self.storeToFile()


    def __loadFromFile(self):
        file = open(self.__fileName, "rb")
        try:
            self._movieList = pickle.load(file)
        except EOFError:
            self._movieList = []
        except IOError:
            raise FileRepositoryException("Movie File error")
        finally:
            file.close()

    def storeToFile(self):
        file = open(self.__fileName,"wb")
        pickle.dump(self._movieList,file)
        file.close()



