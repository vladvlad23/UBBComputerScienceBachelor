from UserInterface.Console import Console
from UserInterface.GraphicalUserInterface import GraphicalUserInterface
from Controller.GameController import GameController
from Repository.GameRepository import GameRepository
from Repository.GameValidator import GameValidator

def startProgram():

    gameRepository = GameRepository()
    gameValidator = GameValidator(gameRepository)
    gameController = GameController(gameRepository,gameValidator)


    file = open("settings.properties","r")
    line = file.readline().strip().split("=")
    if line[0] !="interface":
        raise Exception("Setting properties error")
        return
    elif line[1] == "graphical":
        console = GraphicalUserInterface(gameController)
        console.startGraphicalUserInterface()
    elif line[1] == "console":
        console = Console(gameController)
        console.startConsole()
    else:
        raise Exception("Setting properties error")

    file.close()

startProgram()