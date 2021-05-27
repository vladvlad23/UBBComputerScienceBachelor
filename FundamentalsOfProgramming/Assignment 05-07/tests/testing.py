import unittest
from operations.add import *
from operations.remove import *
from operations.search import *
from operations.statistics import *
from movie import *
from client import *
from rental import *
from dateOperations import *
from undoRedoController import *


class MyTestCase(unittest.TestCase):

    def test_addMovie(self):

        movieList = []

        movie1 = Movie(1, "Pulp Fiction", "2 hitmen have a penchant for discussions", "action")
        movie2 = Movie(2, "Reservoir Dogs", "Six criminals are hired to carry a robbery", "action")
        movie3 = Movie(3, "Batman", "The classical DC hero", "action")

        addMovie(movie1, movieList)
        addMovie(movie2, movieList)
        addMovie(movie3, movieList)


        assert movieList[0] == movie1
        assert movieList[1] == movie2
        assert movieList[2] == movie3

        try:
            assert movie1.getTitle() == movieList[1].getTitle()
            raise Exception("Should not get here")
        except AssertionError:
            pass


    def test_addClient(self):
        clientList = []

        client1 = Client(1, "Georgescu George")
        client2 = Client(2, "Grigorescu Grigore")
        client3 = Client(3, "Marinescu Marian")
        client4 = Client(4, "Ionescu Ioan")
        client5 = Client(5, "Popescu Pop")
        addClient(clientList, client1)
        addClient(clientList, client2)
        addClient(clientList, client3)
        addClient(clientList, client4)
        addClient(clientList, client5)

        assert clientList[0] == client1
        assert clientList[1] == client2
        assert clientList[2] == client3

        try:
            assert client1.getName() == clientList[1].getName()
            raise Exception("Should not get here")
        except AssertionError:
            pass

    def test_addRental(self):
        rentalList = []

        rental1 = Rental(1,1,1,date(2000,1,1),date(2000,1,15),date(2000,1,6))
        rental2 = Rental(2,2,2,date(2000,1,2),date(2000,1,16),date(2000,1,6))
        rental3 = Rental(3,3,3,date(2000,1,3),date(2000,1,17),date(2000,1,7))
        rental4 = Rental(5,5,5,date(2000,1,4),date(2000,1,18),date(2000,1,8))

        addRental(rental1,rentalList)
        addRental(rental2,rentalList)
        addRental(rental3,rentalList)
        addRental(rental4,rentalList)

        assert rentalList[0] == rental1
        assert rentalList[1] == rental2
        assert rentalList[2] != rental1

        try:
            assert rentalList[0].getRentalId() == rental2.getRentalId()
            raise Exception("Should not get here")
        except AssertionError:
            pass


    def test_removeMovie(self):

        movieList = []
        addStartingMovies(movieList)

        removeMovie(movieList,1)

        assert movieList[0].getMovieId() == 2
        removeMovie(movieList,3)

        assert movieList[1].getMovieId() == 4
        removeMovie(movieList,4)

        try:
            assert movieList[0].getMovieId() == 3
            raise Exception("Should not get here")
        except AssertionError:
            pass

    def test_removeClient(self):

        clientList = []
        addStartingTestClients(clientList)

        removeClient(clientList, 1)

        assert clientList[0].getClientId() == 2
        removeClient(clientList, 3)

        assert clientList[1].getClientId() == 4
        removeClient(clientList, 4)

        try:
            assert clientList[0].getClientId() == 3
            raise Exception("Should not get here")
        except AssertionError:
            pass

    def test_modifyMovie(self):
        movie = Movie(1, "Pulp Fiction", "2 hitmen have a penchant for discussions", "action")

        movie.setMovieTitle("Batman")
        assert movie.getTitle() == "Batman"
        movie.setMovieGenre("Gen")
        assert movie.getGenre() == "Gen"
        movie.setMovieDescription("Description")

    def test_modifyClient(self):
        client = Client(1,"Batman")
        client.setClientName("NotBatman")
        assert client.getName() == "NotBatman"

    def test_searchClient(self):
        clientList = []
        addStartingTestClients(clientList)

        resultList = [clientList[4]]

        assert resultList == searchClientsByName("Pop", clientList)
        assert resultList == searchClientsByName("pop", clientList)

        resultList = [clientList[2]]


        assert resultList == searchClientsByName("Mari", clientList)
        assert resultList == searchClientsByName("mari", clientList)

        assert clientList == searchClientsByName("escu", clientList)
        assert clientList == searchClientsByName("ESCU", clientList)

    def test_searchMovieByTitle(self):
        movieList = []
        addStartingTestMovies(movieList)
        addMovie(Movie(6, "Superman", "Classical DC hero", "action"),movieList)

        resultList = [movieList[4]]

        assert resultList == searchMovies("django unchained",Movie.getTitle,movieList)
        assert resultList == searchMovies("DJANGO UNCHAINED",Movie.getTitle,movieList)

        resultList = [movieList[2]]


        assert resultList == searchMovies("Batman",Movie.getTitle,movieList)
        assert resultList == searchMovies("batman",Movie.getTitle,movieList)

        resultList1 = [movieList[2],movieList[5]]

        assert resultList1 == searchMovies("man",Movie.getTitle,movieList)
        assert resultList1 == searchMovies("MAN",Movie.getTitle,movieList)
        
    def test_searchMovieByDescription(self):
        movieList = []
        addStartingTestMovies(movieList)
        addMovie(Movie(6, "Superman", "Classical DC hero", "action"),movieList)

        resultList = [movieList[4]]

        assert resultList == searchMovies("on a mission",Movie.getDescription,movieList)
        assert resultList == searchMovies("A SLAVE AND a bounty",Movie.getDescription,movieList)

        resultList = [movieList[2]]


        assert resultList == searchMovies("the classical",Movie.getDescription,movieList)

        resultList.append(movieList[5])
        assert resultList == searchMovies("DC HERO",Movie.getDescription,movieList)

        assert resultList == searchMovies("dc hero",Movie.getDescription,movieList)
        assert resultList == searchMovies("DC HERO",Movie.getDescription,movieList)


    def test_searchMovieById(self):
        movieList = []
        addStartingTestMovies(movieList)
        addMovie(Movie(6, "Superman", "Classical DC hero", "action"),movieList)

        resultList = [movieList[4]]

        assert resultList == searchMovies("5",Movie.getMovieId,movieList)

        resultList = [movieList[2]]


        assert resultList == searchMovies("3",Movie.getMovieId,movieList)

        resultList = [movieList[5]]
        assert resultList == searchMovies("6",Movie.getMovieId,movieList)

    def test_moviesDescendingByRentingTimes(self):

        rentalList = []
        movieList = []
        addStartingTestMovies(movieList)
        addStartingTestRentals(rentalList)

        result = moviesDescendingByRentingTimes(movieList,rentalList)


        assert movieList == result

        addRental(Rental(7,4,5,date(2018,11,20),date(2018,11,21)),rentalList)
        addRental(Rental(8,4,4,date(2018,11,21),date(2018,11,22)),rentalList)
        addRental(Rental(9,3,3,date(2018,11,22),date(2018,11,23)),rentalList)

        result = moviesDescendingByRentingTimes(movieList,rentalList)

        assert result[0] == movieList[3]
        assert result[1] == movieList[2]

    def test_moviesDescendingByRentingDates(self):
        movieList = []
        rentalList = []
        addStartingTestMovies(movieList)
        addStartingTestRentals(rentalList)

        result = movieList[:]
        result.reverse()

        resultFunct = moviesDescendingByRentingDays(movieList,rentalList)
        assert result != resultFunct


    def test_clientsDescendingByActivity(self):
        clientList = []
        rentalList = []

        addStartingTestClients(clientList)
        addStartingTestRentals(rentalList)

        result = clientList[:]
        result.reverse()

        resultFunct = clientsDescendingByActivity(clientList,rentalList)
        assert result != resultFunct


    def test_lateRentals(self):
        rentalList = []
        movieList = []
        addStartingTestMovies(movieList)
        addRental(Rental(6,1,1,date(2001,2,2),date(2001,2,3)),rentalList)
        addRental(Rental(7,2,2,date(2001,2,2),date(2001,2,4)),rentalList)
        addRental(Rental(8,2,2,date(2001,2,2),date(2001,2,5)),rentalList)

        result = rentalList[-3:].reverse()
        assert result != lateRentals(movieList,rentalList)

    def test_addStartingMovies(self):
        movieList = []
        addStartingMovies(movieList)

        assert len(movieList)==100

    def test_addStartingClients(self):
        clientList = []
        addStartingClients(clientList)

        assert len(clientList)==100


    def test_undoAddMovie(self):
        movieList = []
        clientList = []
        rentalList = []
        undoRedoController = UndoRedoController()

        movie1 = Movie(1,"test1","test1","Test1")
        addMovie(movie1,movieList)
        undoRedoController.insertMovieAddOperation(movie1)

        movie2 = Movie(2,"test2","test2","test2")
        addMovie(movie2,movieList)
        undoRedoController.insertMovieAddOperation(movie2)

        movie3 = Movie(3,"test3","test3","test3")
        addMovie(movie3,movieList)
        undoRedoController.insertMovieAddOperation(movie3)

        movie4 = Movie(4,"test4","test4","test4")
        addMovie(movie4,movieList)
        undoRedoController.insertMovieAddOperation(movie4)

        undoRedoController.undo(movieList,clientList,rentalList)
        assert len(movieList) == 3
        assert movieList[2] == movie3

        undoRedoController.undo(movieList,clientList,rentalList)
        assert len(movieList) == 2
        assert movieList[1] == movie2

        undoRedoController.undo(movieList,clientList,rentalList)
        assert len(movieList) == 1
        assert movieList[0] == movie1

        undoRedoController.undo(movieList,clientList,rentalList)
        assert len(movieList) == 0

        try:
            undoRedoController.undo(movieList,clientList,rentalList)
            assert False
        except Exception as e:
            assert str(e) == "No more undos"

    def test_redoAddMovie(self):
        movieList = []
        clientList = []
        rentalList = []
        undoRedoController = UndoRedoController()

        movie1 = Movie(1,"test1","test1","Test1")
        addMovie(movie1,movieList)
        undoRedoController.insertMovieAddOperation(movie1)

        movie2 = Movie(2,"test2","test2","test2")
        addMovie(movie2,movieList)
        undoRedoController.insertMovieAddOperation(movie2)

        movie3 = Movie(3,"test3","test3","test3")
        addMovie(movie3,movieList)
        undoRedoController.insertMovieAddOperation(movie3)

        movie4 = Movie(4,"test4","test4","test4")
        addMovie(movie4,movieList)
        undoRedoController.insertMovieAddOperation(movie4)

        undoRedoController.undo(movieList,clientList,rentalList)
        undoRedoController.undo(movieList,clientList,rentalList)
        undoRedoController.undo(movieList,clientList,rentalList)
        undoRedoController.undo(movieList,clientList,rentalList)


        assert len(movieList)==0

        undoRedoController.redo(movieList,clientList,rentalList)
        assert len(movieList)==1
        assert movieList[0] == movie1

        undoRedoController.redo(movieList,clientList,rentalList)
        assert len(movieList)==2
        assert movieList[1] == movie2
        assert movieList[0] == movie1

        undoRedoController.redo(movieList,clientList,rentalList)
        assert len(movieList)==3
        assert movieList[2] == movie3

        undoRedoController.redo(movieList,clientList,rentalList)
        assert len(movieList)==4
        assert movieList[3] == movie4

        try:
            undoRedoController.redo(movieList,clientList,rentalList)
            assert False
        except Exception as e:
            assert str(e) == "No more redos"

    def test_undoAddClient(self):
        movieList = []
        clientList = []
        rentalList = []
        undoRedoController = UndoRedoController()

        client1 = Client(1,"client1")
        clientList.append(client1)
        undoRedoController.insertClientAddOperation(client1)

        client2 = Client(2,"client2")
        clientList.append(client2)
        undoRedoController.insertClientAddOperation(client2)

        client3 = Client(3,"client3")
        clientList.append(client3)
        undoRedoController.insertClientAddOperation(client3)

        client4 = Client(4,"client4")
        clientList.append(client4)
        undoRedoController.insertClientAddOperation(client4)

        undoRedoController.undo(movieList,clientList,rentalList)
        assert len(clientList) == 3
        assert clientList[2] == client3

        undoRedoController.undo(movieList,clientList,rentalList)
        assert len(clientList) == 2
        assert clientList[1] == client2

        undoRedoController.undo(movieList,clientList,rentalList)
        assert len(clientList) == 1
        assert clientList[0] == client1

        undoRedoController.undo(movieList,clientList,rentalList)
        assert len(clientList) == 0

        try:
            undoRedoController.undo(movieList,clientList,rentalList)
            assert False
        except Exception as e:
            assert str(e) == "No more undos"

    def test_redoAddClient(self):
        movieList = []
        clientList = []
        rentalList = []
        undoRedoController = UndoRedoController()

        client1 = Client(1,"client1")
        clientList.append(client1)
        undoRedoController.insertClientAddOperation(client1)

        client2 = Client(2,"client2")
        clientList.append(client2)
        undoRedoController.insertClientAddOperation(client2)

        client3 = Client(3,"client3")
        clientList.append(client3)
        undoRedoController.insertClientAddOperation(client3)

        client4 = Client(4,"client4")
        clientList.append(client4)
        undoRedoController.insertClientAddOperation(client4)

        undoRedoController.undo(movieList,clientList,rentalList)
        undoRedoController.undo(movieList,clientList,rentalList)
        undoRedoController.undo(movieList,clientList,rentalList)
        undoRedoController.undo(movieList,clientList,rentalList)

        assert len(clientList) == 0

        undoRedoController.redo(movieList,clientList,rentalList)
        assert len(clientList) == 1
        assert clientList[0] == client1

        undoRedoController.redo(movieList,clientList,rentalList)
        assert len(clientList) == 2
        assert clientList[1] == client2

        undoRedoController.redo(movieList,clientList,rentalList)
        assert len(clientList) == 3
        assert clientList[2] == client3

        undoRedoController.redo(movieList,clientList,rentalList)
        assert len(clientList) == 4
        assert clientList[3] == client4

        try:
            undoRedoController.redo(movieList,clientList,rentalList)
            assert False
        except Exception as e:
            assert str(e) == "No more redos"

    def test_undoAddRental(self):
        movieList = []
        clientList = []
        rentalList = []

        undoRedoController = UndoRedoController()

        addStartingTestClients(clientList)
        addStartingTestRentals(rentalList)

        for client in clientList:
            changeRentalStatus(clientList,client.getClientId())

        rentalListCopy = rentalList[:]

        undoRedoController.insertRentalAddOperation(rentalList[0])
        undoRedoController.insertRentalAddOperation(rentalList[1])
        undoRedoController.insertRentalAddOperation(rentalList[2])
        undoRedoController.insertRentalAddOperation(rentalList[3])
        undoRedoController.insertRentalAddOperation(rentalList[4])

        assert len(rentalList) == 5

        undoRedoController.undo(movieList,clientList,rentalList)
        assert len(rentalList) == 4
        assert rentalList[3] == rentalListCopy[3]
        assert rentalList[2] == rentalListCopy[2]
        assert rentalList[1] == rentalListCopy[1]
        assert rentalList[0] == rentalListCopy[0]

        undoRedoController.undo(movieList,clientList,rentalList)
        assert len(rentalList) == 3
        assert rentalList[2] == rentalListCopy[2]
        assert clientList[3].getRentedStatus() == False

        undoRedoController.undo(movieList,clientList,rentalList)
        assert len(rentalList) == 2
        assert rentalList[1] == rentalListCopy[1]
        assert clientList[2].getRentedStatus() == False

        undoRedoController.undo(movieList,clientList,rentalList)
        assert len(rentalList) == 1
        assert rentalList[0] == rentalListCopy[0]
        assert clientList[1].getRentedStatus() == False

        undoRedoController.undo(movieList,clientList,rentalList)
        assert len(rentalList) == 0

        try:
            undoRedoController.undo(movieList,clientList,rentalList)
            assert False
        except Exception as e:
            assert str(e) == "No more undos"

    def test_redoAddRental(self):
        movieList = []
        clientList = []
        rentalList = []

        undoRedoController = UndoRedoController()

        addStartingTestClients(clientList)
        addStartingTestRentals(rentalList)

        for client in clientList:
            changeRentalStatus(clientList,client.getClientId())

        rentalListCopy = rentalList[:]

        undoRedoController.insertRentalAddOperation(rentalList[0])
        undoRedoController.insertRentalAddOperation(rentalList[1])
        undoRedoController.insertRentalAddOperation(rentalList[2])
        undoRedoController.insertRentalAddOperation(rentalList[3])
        undoRedoController.insertRentalAddOperation(rentalList[4])

        assert len(rentalList)==5

        undoRedoController.undo(movieList,clientList,rentalList)
        undoRedoController.undo(movieList,clientList,rentalList)
        undoRedoController.undo(movieList,clientList,rentalList)
        undoRedoController.undo(movieList,clientList,rentalList)
        undoRedoController.undo(movieList,clientList,rentalList)

        assert len(rentalList) == 0

        undoRedoController.redo(movieList,clientList,rentalList)
        assert len(rentalList) == 1
        assert rentalList[0] == rentalListCopy[0]

        undoRedoController.redo(movieList,clientList,rentalList)
        assert len(rentalList) == 2
        assert rentalList[1] == rentalListCopy[1]
        assert clientList[1].getRentedStatus() == True

        undoRedoController.redo(movieList,clientList,rentalList)
        assert len(rentalList) == 3
        assert rentalList[2] == rentalListCopy[2]
        assert clientList[2].getRentedStatus() == True

        undoRedoController.redo(movieList,clientList,rentalList)
        assert len(rentalList) == 4
        assert rentalList[3] == rentalListCopy[3]
        assert clientList[3].getRentedStatus() == True

        undoRedoController.redo(movieList,clientList,rentalList)
        assert len(rentalList) == 5
        assert rentalList[4] == rentalListCopy[4]
        assert clientList[4].getRentedStatus() == True

        try:
            undoRedoController.redo(movieList,clientList,rentalList)
            assert False
        except Exception as e:
            assert str(e) == "No more redos"

    def test_undoCompleteRental(self):
        clientList = []
        rentalList = []
        movieList = []

        addStartingTestRentals(rentalList)
        addStartingTestClients(clientList)

        undoRedoController = UndoRedoController()

        #since they are already completed and it's kinda redundant to complete them again
        #as that function is already tested, just the undoRedoController insert method will be called

        undoRedoController.insertRentalCompletionOperation(rentalList[0],
                                                           rentalList[0].getReturnDate())
        undoRedoController.insertRentalCompletionOperation(rentalList[1],
                                                           rentalList[1].getReturnDate())
        undoRedoController.insertRentalCompletionOperation(rentalList[2],
                                                           rentalList[2].getReturnDate())
        undoRedoController.insertRentalCompletionOperation(rentalList[3],
                                                           rentalList[3].getReturnDate())
        undoRedoController.insertRentalCompletionOperation(rentalList[4],
                                                           rentalList[4].getReturnDate())

        for rental in rentalList:
            assert rental.isReturned() == True

        undoRedoController.undo(movieList,clientList,rentalList)
        assert rentalList[3].isReturned() == True
        assert rentalList[4].isReturned() == False

        undoRedoController.undo(movieList,clientList,rentalList)
        assert rentalList[3].isReturned() == False

        undoRedoController.undo(movieList,clientList,rentalList)
        assert rentalList[2].isReturned() == False

        undoRedoController.undo(movieList,clientList,rentalList)
        assert rentalList[1].isReturned() == False

        undoRedoController.undo(movieList,clientList,rentalList)
        assert rentalList[0].isReturned() == False

        try:
            undoRedoController.undo(movieList,clientList,rentalList)
            assert False
        except Exception as e:
            assert str(e) == "No more undos"

    def test_redoCompleteRental(self):
        clientList = []
        rentalList = []
        movieList = []

        addStartingTestRentals(rentalList)
        addStartingTestClients(clientList)


        undoRedoController = UndoRedoController()

        undoRedoController.insertRentalCompletionOperation(rentalList[0],
                                                           rentalList[0].getReturnDate())
        undoRedoController.insertRentalCompletionOperation(rentalList[1],
                                                           rentalList[2].getReturnDate())
        undoRedoController.insertRentalCompletionOperation(rentalList[2],
                                                           rentalList[2].getReturnDate())
        undoRedoController.insertRentalCompletionOperation(rentalList[3],
                                                           rentalList[3].getReturnDate())
        undoRedoController.insertRentalCompletionOperation(rentalList[4],
                                                           rentalList[4].getReturnDate())

        undoRedoController.undo(movieList,clientList,rentalList)
        undoRedoController.undo(movieList,clientList,rentalList)
        undoRedoController.undo(movieList,clientList,rentalList)
        undoRedoController.undo(movieList,clientList,rentalList)
        undoRedoController.undo(movieList,clientList,rentalList)

        undoRedoController.redo(movieList,clientList,rentalList)
        assert rentalList[0].isReturned() == True
        for rentalIndex in range(1,len(rentalList)):
            assert rentalList[rentalIndex].isReturned() == False

        undoRedoController.redo(movieList, clientList, rentalList)
        assert rentalList[1].isReturned() == True
        assert rentalList[0].isReturned() == True

        undoRedoController.redo(movieList, clientList, rentalList)
        assert rentalList[2].isReturned() == True

        undoRedoController.redo(movieList, clientList, rentalList)
        assert rentalList[3].isReturned() == True

        undoRedoController.redo(movieList, clientList, rentalList)
        assert rentalList[4].isReturned() == True

        try:
            undoRedoController.redo(movieList, clientList, rentalList)
            assert False
        except Exception as e:
            assert str(e) == "No more redos"


    def test_undoRemoveClient(self):
        clientList = []
        movieList = []
        rentalList = []
        undoRedoController = UndoRedoController()

        addStartingTestClients(clientList)

        clientListCopy = clientList[:]

        assert len(clientList) == 5

        undoRedoController.insertClientRemovalOperation(clientList[0],0)
        removeClient(clientList,clientList[0].getClientId())

        undoRedoController.insertClientRemovalOperation(clientList[0],0)
        removeClient(clientList,clientList[0].getClientId())

        undoRedoController.insertClientRemovalOperation(clientList[0],0)
        removeClient(clientList,clientList[0].getClientId())

        undoRedoController.insertClientRemovalOperation(clientList[0],0)
        removeClient(clientList,clientList[0].getClientId())

        undoRedoController.insertClientRemovalOperation(clientList[0],0)
        removeClient(clientList,clientList[0].getClientId())

        assert len(clientList) == 0

        undoRedoController.undo(movieList,clientList,rentalList)
        assert len(clientList) == 1
        assert clientList[0] == clientListCopy[4]

        undoRedoController.undo(movieList,clientList,rentalList)
        assert len(clientList) == 2
        assert clientList[0] == clientListCopy[3]

        undoRedoController.undo(movieList,clientList,rentalList)
        assert len(clientList) == 3
        assert clientList[0] == clientListCopy[2]

        undoRedoController.undo(movieList,clientList,rentalList)
        assert len(clientList) == 4
        assert clientList[0] == clientListCopy[1]

        undoRedoController.undo(movieList,clientList,rentalList)
        assert len(clientList) == 5
        assert clientList[0] == clientListCopy[0]

        try:
            undoRedoController.undo(movieList,clientList,rentalList)
            assert False
        except Exception as e:
            assert str(e) == "No more undos"

    def test_redoRemoveClient(self):
        clientList = []
        movieList = []
        rentalList = []
        undoRedoController = UndoRedoController()

        addStartingTestClients(clientList)

        clientListCopy = clientList[:]

        assert len(clientList) == 5

        undoRedoController.insertClientRemovalOperation(clientList[0],0)
        removeClient(clientList,clientList[0].getClientId())

        undoRedoController.insertClientRemovalOperation(clientList[0],0)
        removeClient(clientList,clientList[0].getClientId())

        undoRedoController.insertClientRemovalOperation(clientList[0],0)
        removeClient(clientList,clientList[0].getClientId())

        undoRedoController.insertClientRemovalOperation(clientList[0],0)
        removeClient(clientList,clientList[0].getClientId())

        undoRedoController.insertClientRemovalOperation(clientList[0],0)
        removeClient(clientList,clientList[0].getClientId())

        assert len(clientList) == 0

        undoRedoController.undo(movieList,clientList,rentalList)
        undoRedoController.undo(movieList,clientList,rentalList)
        undoRedoController.undo(movieList,clientList,rentalList)
        undoRedoController.undo(movieList,clientList,rentalList)
        undoRedoController.undo(movieList,clientList,rentalList)

        assert len(clientList) == 5

        undoRedoController.redo(movieList,clientList,rentalList)
        assert len(clientList) == 4
        assert clientList[0] == clientListCopy[1]

        undoRedoController.redo(movieList,clientList,rentalList)
        assert len(clientList) == 3
        assert clientList[0] == clientListCopy[2]

        undoRedoController.redo(movieList,clientList,rentalList)
        assert len(clientList) == 2
        assert clientList[0] == clientListCopy[3]

        undoRedoController.redo(movieList,clientList,rentalList)
        assert len(clientList) == 1
        assert clientList[0] == clientListCopy[4]

        undoRedoController.redo(movieList,clientList,rentalList)
        assert len(clientList) == 0

        try:
            undoRedoController.redo(movieList, clientList, rentalList)
            assert False
        except Exception as e:
            assert str(e) == "No more redos"


    def test_undoRemoveMovie(self):
        movieList = []
        clientList = []
        rentalList = []
        addStartingTestMovies(movieList)
        undoRedoController = UndoRedoController()

        assert len(movieList) == 5

        movieListCopy = movieList[:]

        undoRedoController.insertMovieRemovalOperation(movieList[0],0)
        removeMovie(movieList,movieList[0].getMovieId())

        undoRedoController.insertMovieRemovalOperation(movieList[0],0)
        removeMovie(movieList,movieList[0].getMovieId())

        undoRedoController.insertMovieRemovalOperation(movieList[0],0)
        removeMovie(movieList,movieList[0].getMovieId())

        undoRedoController.insertMovieRemovalOperation(movieList[0],0)
        removeMovie(movieList,movieList[0].getMovieId())

        undoRedoController.insertMovieRemovalOperation(movieList[0],0)
        removeMovie(movieList,movieList[0].getMovieId())

        assert len(movieList) == 0

        undoRedoController.undo(movieList,clientList,rentalList)
        assert len(movieList) == 1
        assert movieList[0] == movieListCopy[4]

        undoRedoController.undo(movieList,clientList,rentalList)
        assert len(movieList) == 2
        assert movieList[0] == movieListCopy[3]

        undoRedoController.undo(movieList,clientList,rentalList)
        assert len(movieList) == 3
        assert movieList[0] == movieListCopy[2]

        undoRedoController.undo(movieList,clientList,rentalList)
        assert len(movieList) == 4
        assert movieList[0] == movieListCopy[1]

        undoRedoController.undo(movieList,clientList,rentalList)
        assert len(movieList) == 5
        assert movieList[0] == movieListCopy[0]


        try:
            undoRedoController.undo(movieList, clientList, rentalList)
            assert False
        except Exception as e:
            assert str(e) == "No more undos"

    def test_redoRemoveMovie(self):
        clientList = []
        movieList = []
        rentalList = []
        undoRedoController = UndoRedoController()

        addStartingTestMovies(movieList)

        movieListCopy = movieList[:]

        assert len(movieList) == 5

        undoRedoController.insertMovieRemovalOperation(movieList[0],0)
        removeMovie(movieList,movieList[0].getMovieId())

        undoRedoController.insertMovieRemovalOperation(movieList[0],0)
        removeMovie(movieList,movieList[0].getMovieId())

        undoRedoController.insertMovieRemovalOperation(movieList[0],0)
        removeMovie(movieList,movieList[0].getMovieId())

        undoRedoController.insertMovieRemovalOperation(movieList[0],0)
        removeMovie(movieList,movieList[0].getMovieId())

        undoRedoController.insertMovieRemovalOperation(movieList[0],0)
        removeMovie(movieList,movieList[0].getMovieId())

        assert len(movieList) == 0

        undoRedoController.undo(movieList,clientList,rentalList)
        undoRedoController.undo(movieList,clientList,rentalList)
        undoRedoController.undo(movieList,clientList,rentalList)
        undoRedoController.undo(movieList,clientList,rentalList)
        undoRedoController.undo(movieList,clientList,rentalList)

        assert len(movieList) == 5

        undoRedoController.redo(movieList,clientList,rentalList)
        assert len(movieList) == 4
        assert movieList[0] == movieListCopy[1]

        undoRedoController.redo(movieList,clientList,rentalList)
        assert len(movieList) == 3
        assert movieList[0] == movieListCopy[2]

        undoRedoController.redo(movieList,clientList,rentalList)
        assert len(movieList) == 2
        assert movieList[0] == movieListCopy[3]

        undoRedoController.redo(movieList,clientList,rentalList)
        assert len(movieList) == 1
        assert movieList[0] == movieListCopy[4]

        undoRedoController.redo(movieList,clientList,rentalList)
        assert len(movieList) == 0

        try:
            undoRedoController.redo(movieList, clientList, rentalList)
            assert False
        except Exception as e:
            assert str(e) == "No more redos"


    def test_undoModifyMovie(self):
        movieList = []
        clientList = []
        rentalList = []
        addStartingTestMovies(movieList)

        undoRedoController = UndoRedoController()

        undoRedoController.insertMovieModifyOperation(movieList[0],"title",
                                                      movieList[0].getTitle(),"test1")
        movieList[0].setMovieTitle("test1")

        undoRedoController.insertMovieModifyOperation(movieList[0],"title",
                                                      movieList[0].getTitle(),"test2")
        movieList[0].setMovieTitle("test2")

        undoRedoController.undo(movieList,clientList,rentalList)
        assert movieList[0].getTitle() == "test1"

        undoRedoController.undo(movieList,clientList,rentalList)
        assert movieList[0].getTitle() == "Pulp Fiction"

        try:
            undoRedoController.undo(movieList,clientList,rentalList)
            assert False
        except Exception as e:
            assert str(e) == "No more undos"

        #TEST DESCRIPTION CHANGE

        undoRedoController.insertMovieModifyOperation(movieList[0],"description",
                                                      movieList[0].getDescription(),"test1")
        movieList[0].setMovieDescription("test1")

        undoRedoController.insertMovieModifyOperation(movieList[0],"description",
                                                      movieList[0].getDescription(),"test2")
        movieList[0].setMovieDescription("test2")

        undoRedoController.undo(movieList,clientList,rentalList)
        assert movieList[0].getDescription() == "test1"

        undoRedoController.undo(movieList,clientList,rentalList)
        assert movieList[0].getDescription() == "2 hitmen have a penchant for discussions"

        #TEST GENRE CHANGE

        undoRedoController.insertMovieModifyOperation(movieList[0],"genre",
                                                      movieList[0].getGenre(),"test1")
        movieList[0].setMovieGenre("test1")

        undoRedoController.insertMovieModifyOperation(movieList[0],"genre",
                                                      movieList[0].getGenre(),"test2")
        movieList[0].setMovieGenre("test2")

        undoRedoController.undo(movieList,clientList,rentalList)
        assert movieList[0].getGenre() == "test1"

        undoRedoController.undo(movieList,clientList,rentalList)
        assert movieList[0].getGenre() == "action"

        try:
            undoRedoController.undo(movieList,clientList,rentalList)
            assert False
        except Exception as e:
            assert str(e) == "No more undos"

    def test_redoModifyMovie(self):
            movieList = []
            clientList = []
            rentalList = []
            addStartingTestMovies(movieList)

            undoRedoController = UndoRedoController()

            undoRedoController.insertMovieModifyOperation(movieList[0], "title",
                                                          movieList[0].getTitle(), "test1")
            movieList[0].setMovieTitle("test1")

            undoRedoController.insertMovieModifyOperation(movieList[0], "title",
                                                          movieList[0].getTitle(), "test2")
            movieList[0].setMovieTitle("test2")

            undoRedoController.undo(movieList, clientList, rentalList)
            undoRedoController.undo(movieList, clientList, rentalList)
            undoRedoController.redo(movieList,clientList,rentalList)
            assert movieList[0].getTitle() == "test1"

            undoRedoController.redo(movieList,clientList,rentalList)
            assert movieList[0].getTitle() == "test2"

            try:
                undoRedoController.redo(movieList, clientList, rentalList)
                assert False
            except Exception as e:
                assert str(e) == "No more redos"

            # TEST DESCRIPTION CHANGE

            undoRedoController.insertMovieModifyOperation(movieList[0], "description",
                                                          movieList[0].getDescription(), "test1")
            movieList[0].setMovieDescription("test1")

            undoRedoController.insertMovieModifyOperation(movieList[0], "description",
                                                          movieList[0].getDescription(), "test2")
            movieList[0].setMovieDescription("test2")

            undoRedoController.undo(movieList, clientList, rentalList)
            undoRedoController.undo(movieList, clientList, rentalList)

            undoRedoController.redo(movieList,clientList,rentalList)
            assert movieList[0].getDescription() == "test1"

            undoRedoController.redo(movieList,clientList,rentalList)
            assert movieList[0].getDescription() == "test2"

            # TEST GENRE CHANGE

            undoRedoController.insertMovieModifyOperation(movieList[0], "genre",
                                                          movieList[0].getGenre(), "test1")
            movieList[0].setMovieGenre("test1")

            undoRedoController.insertMovieModifyOperation(movieList[0], "genre",
                                                          movieList[0].getGenre(), "test2")
            movieList[0].setMovieGenre("test2")

            undoRedoController.undo(movieList, clientList, rentalList)
            undoRedoController.undo(movieList, clientList, rentalList)

            undoRedoController.redo(movieList,clientList,rentalList)
            assert movieList[0].getGenre() == "test1"

            undoRedoController.redo(movieList, clientList, rentalList)
            assert movieList[0].getGenre() == "test2"

            try:
                undoRedoController.redo(movieList, clientList, rentalList)
                assert False
            except Exception as e:
                assert str(e) == "No more redos"

    def test_undoModifyClient(self):
        movieList = []
        clientList = []
        rentalList = []
        addStartingTestClients(clientList)

        undoRedoController = UndoRedoController()

        undoRedoController.insertClientModifyOperation(clientList[0],
                                                      clientList[0].getName(),"test1")
        clientList[0].setClientName("test1")

        undoRedoController.insertClientModifyOperation(clientList[0],
                                                      clientList[0].getName(),"test2")
        clientList[0].setClientName("test2")

        undoRedoController.undo(clientList,clientList,rentalList)
        assert clientList[0].getName() == "test1"

        undoRedoController.undo(clientList,clientList,rentalList)
        assert clientList[0].getName() == "Georgescu George"

        try:
            undoRedoController.undo(movieList,clientList,rentalList)
            assert False
        except Exception as e:
            assert str(e) == "No more undos"

    def test_redoModifyClient(self):
        movieList = []
        clientList = []
        rentalList = []
        addStartingTestClients(clientList)

        undoRedoController = UndoRedoController()

        undoRedoController.insertClientModifyOperation(clientList[0],
                                                      clientList[0].getName(), "test1")
        clientList[0].setClientName("test1")

        undoRedoController.insertClientModifyOperation(clientList[0],
                                                      clientList[0].getName(), "test2")
        clientList[0].setClientName("test2")

        undoRedoController.undo(movieList, clientList, rentalList)
        undoRedoController.undo(movieList, clientList, rentalList)
        undoRedoController.redo(movieList, clientList, rentalList)
        assert clientList[0].getName() == "test1"

        undoRedoController.redo(movieList, clientList, rentalList)
        assert clientList[0].getName() == "test2"

        try:
            undoRedoController.redo(movieList, clientList, rentalList)
            assert False
        except Exception as e:
            assert str(e) == "No more redos"



def addStartingTestMovies(movieList):


    addMovie(Movie(1, "Pulp Fiction", "2 hitmen have a penchant for discussions", "action"),movieList)
    addMovie(Movie(2, "Reservoir Dogs","Six criminals are hired to carry a robbery", "action"),movieList)
    addMovie(Movie(3, "Batman", "The classical DC hero", "action"),movieList)
    addMovie(Movie(4, "12 angry men", "A jury tries to bring justice in a crime", "crime"),movieList)
    addMovie(Movie(5, "Django Unchained", "A slave and a bounty hunter go on a mission","adventure"),movieList)

def addStartingTestClients(clientList):
    addClient(clientList,Client(1,"Georgescu George"))
    addClient(clientList,Client(2,"Grigorescu Grigore"))
    addClient(clientList,Client(3,"Marinescu Marian"))
    addClient(clientList,Client(4,"Ionescu Ioan"))
    addClient(clientList,Client(5,"Popescu Pop"))


def addStartingTestRentals(rentalList):
    addRental(Rental(1,1,1,date(2000,1,1),date(2000,1,6),date(2000,1,1)),rentalList)
    addRental(Rental(2,2,2,date(2000,1,2),date(2000,1,6),date(2000,1,3)),rentalList)
    addRental(Rental(3,3,3,date(2000,1,3),date(2000,1,7),date(2000,1,5)),rentalList)
    addRental(Rental(4,4,4,date(2000,1,5),date(2000,1,10),date(2000,1,7)),rentalList)
    addRental(Rental(5,5,5,date(2000,1,4),date(2000,1,8),date(2000,1,8)),rentalList)