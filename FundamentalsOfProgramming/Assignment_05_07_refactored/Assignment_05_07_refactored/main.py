from Assignment_05_07_refactored.controller import ClientController,RentalController,MovieController,UndoRedoController
from Assignment_05_07_refactored.repository import MovieCSVRepository,ClientCSVRepository, \
    RentalCSVRepository,MoviePickleRepository,ClientPickleRepository,RentalPickleRepository,\
    MovieRepository,ClientRepository,RentalRepository
from Assignment_05_07_refactored.domain import ClientValidator,MovieValidator,RentalValidator
from Assignment_05_07_refactored.userInterface import Console

from Assignment_05_07_refactored.domain.Movie import Movie
from Assignment_05_07_refactored.domain.Rental import Rental
from Assignment_05_07_refactored.domain.Client import Client

from datetime import *


start = True

try:

    file = open("settings.properties","r")

    options = file.readline().strip().split(" ")

    if options[2] == "binary-files":
        options = file.readline().strip().split(" ")
        movieRepository = MoviePickleRepository.MoviePickleRepository(options[2])

        options = file.readline().strip().split(" ")
        clientRepository = ClientPickleRepository.ClientPickleRepository(options[2])

        options = file.readline().strip().split(" ")
        rentalRepository = RentalPickleRepository.RentalPickleRepository(options[2])

    elif options[2] == "text-files":
        options = file.readline().strip().split(" ")
        movieRepository = MovieCSVRepository.MovieCSVRepository(options[2])

        options = file.readline().strip().split(" ")
        clientRepository = ClientCSVRepository.ClientCSVRepository(options[2])

        options = file.readline().strip().split(" ")
        rentalRepository = RentalCSVRepository.RentalCSVRepository(options[2])

    elif options[2] == "inmemory":
        movieRepository = MovieRepository.MovieRepository()
        movieRepository.addStartingMovies()

        clientRepository = ClientRepository.ClientRepository()
        clientRepository.addStartingClients()

        rentalRepository = RentalRepository.RentalRepository()
        rentalRepository.addStartingRentals(clientRepository.getAllClients())
    else:
        raise Exception("Incorrect settings.properties")
    file.close()
except Exception as e:
    print("Error in settings.properties")
    start = False

if start == True:
    clientValidator = ClientValidator.ClientValidator(clientRepository)
    rentalValidator = RentalValidator.RentalValidator(rentalRepository)
    movieValidator = MovieValidator.MovieValidator(movieRepository)

    clientController = ClientController.ClientController(clientRepository, clientValidator)
    rentalController = RentalController.RentalController(rentalRepository, rentalValidator)
    movieController = MovieController.MovieController(movieRepository, movieValidator)

    undoRedoController = UndoRedoController.UndoRedoController(movieRepository, clientRepository, rentalRepository)

    console = Console.Console(movieController, clientController, rentalController, undoRedoController)
    console.startConsole()

