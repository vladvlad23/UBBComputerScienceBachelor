from Assignment_05_07_refactored.domain.Movie import Movie
from Assignment_05_07_refactored.repository.RandomGenerator import RandomMovieGenerator
from Assignment_05_07_refactored.domain.Exceptions import NotFoundException
from Assignment_05_07_refactored.domain.CustomIterable import CustomIterable

class MovieRepository:

    def __init__(self):
        self._movieList = CustomIterable()

    def add(self,movie):
        self._movieList.append(movie)

    def addMovieAtIndex(self, movie, index):
        '''
        Function will add a movie in the list at a specific index
        :param movie: the movie which should be inserted
        :param index: the index where it should be inserted
        '''
        self._movieList.insert(index, movie)

    def exists(self,movieId):
        for movie in self._movieList:
            if movie.getMovieId() == movieId:
                return True
        return False

    def findMovieById(self,movieId):
        for movie in self._movieList:
            if movie.getMovieId() == movieId:
                return movie
        raise NotFoundException("Movie not found")

    def size(self):
        return len(self._movieList)

    def remove(self,movieId):
        if not self.exists(movieId):
            raise NotFoundException("Movie does not exist")

        for movie in self._movieList:
            if movie.getMovieId() == movieId:
                del self._movieList[self._movieList.index(movie)]

    def modifyTitle(self,movieId,newTitle):
        for movie in self._movieList:
            if movie.getMovieId() == movieId:
                self._movieList[self._movieList.index(movie)].setMovieTitle(newTitle)

    def modifyDescription(self,movieId,newDescription):
        for movie in self._movieList:
            if movie.getMovieId() == movieId:
                self._movieList[self._movieList.index(movie)].setMovieDescription(newDescription)

    def modifyGenre(self,movieId,newGenre):
        for movie in self._movieList:
            if movie.getMovieId() == movieId:
                self._movieList[self._movieList.index(movie)].setMovieGenre(newGenre)

    def addStartingMovies(self):
        '''
        Function will generate 100 Movie class entries that will be added in a list
        :param movieList: the list where the entries should be added
        '''

        generator = RandomMovieGenerator()

        self.add(Movie(1, "Pulp Fiction", "2 hitmen have a penchant for discussions", "action"))
        self.add(Movie(2, "Reservoir Dogs", "Six criminals are hired to carry a robbery", "action"))
        self.add(Movie(3, "Batman", "The classical DC hero", "action"))
        self.add(Movie(4, "12 angry men", "A jury tries to bring justice in a crime", "crime"))
        self.add(Movie(5, "Django Unchained", "A slave and a bounty hunter go on a mission", "adventure"))

        for i in range(6, 100 + 1):
            title = generator.randomTitleGenerator()
            description = generator.randomDescriptionGenerator()
            genre = generator.randomGenreGenerator()
            movie = Movie(i, title,description,genre)
            self.add(movie)

    def getBiggestMovieId(self):
        '''
        Function returns the maximum movie id in a list
        :param movieList: the given list
        :return: the maximum id
        '''
        maxId = int(0)
        for movie in self._movieList:
            if movie.getMovieId() > maxId:
                maxId = movie.getMovieId()

        return maxId

    def getAllMovies(self):
        return self._movieList


    def searchMovies(self,searchString, criteria):
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
        for movie in self._movieList:
            if type(criteria(movie)) is int:
                tempString = str(criteria(movie))
            else:
                tempString = criteria(movie)
            if searchString in tempString.lower():
                resultList.append(movie)

        return resultList

    def moviesDescendingByRentingTimes(self, rentalList):
        '''
        Procedure : will create a list counting how many times each movie has been rented and then
        will form an id list containing the indexes from highest to lowest
        :param movieList = list of movies
        :param rentalList = list of rentals
        :return list of movies in descending order by renting times
        '''
        idList = []
        newList = rentalList[:]
        frequencyList = [0] * (2 * self.getBiggestMovieId())
        for rental in newList:
            frequencyList[rental.getMovieId()] += 1

        for i in range(1, len(frequencyList)):

            maxId = frequencyList.index(max(frequencyList))  # get the index with max apparitions
            if maxId > 0:
                idList.append(maxId)
            frequencyList[maxId] = -1

        newList = []
        for id in range(0, len(idList)):  # go through id list
            try:
                movie = self.findMovieById(idList[id])
                newList.append(movie)
            except Exception:  # this means that the list indexes have been surpassed
                return newList
        return newList

    def moviesDescendingByRentingDays(self, rentalList):
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
        for movie in self._movieList:
            movieDictionary = {"movieId": movie.getMovieId(), "days": int(0)}
            for rental in rentalList:
                if rental.getMovieId() == movie.getMovieId():
                    if rental.isReturned():
                        movieDictionary["days"] += rental.getRentedDays()

            movieDictList.append(movieDictionary)

        movieDictList = sorted(movieDictList, key=lambda movieDict: movieDict.get("days"), reverse=True)

        result = []
        for movie in movieDictList:
            result.append(self.findMovieById(movie["movieId"]))

        return result

    def storeToFile(self):
        pass




