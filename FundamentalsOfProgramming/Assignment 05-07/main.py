from userInterface import *
from undoRedoController import *

def runMenu():
    '''
    Function will run the menu continously until the function parseInput returns True
    It will also initialize the movie, client and rental lists
    :return:
    '''

    movieList = []
    clientList = []
    rentalList = []
    undoRedoController = UndoRedoController()
    addStartingMovies(movieList)
    addStartingClients(clientList)
    addStartingRentals(rentalList,clientList)

    exits = False
    while not exits:
        userInput = input("Give command please: ")
        exits = parseInput(userInput,movieList,clientList,rentalList,undoRedoController)

runMenu()
