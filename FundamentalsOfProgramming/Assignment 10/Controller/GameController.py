from Repository.Exceptions import GameOverError
from copy import deepcopy
class GameController:

    def __init__(self,gameRepository,gameValidator):
        '''
        Function will initialize the game controller.
        :param gameRepository: the game repository required to check if the column is not already full
        :param gameValidator: the game validator for the same purpose
        '''
        self.__gameRepository = gameRepository
        self.__gameValidator = gameValidator
        self.__player = 'Y'

    def makeMove(self,userInput):
        '''
        Function will validate the userinput and then will make a move
        :param userInput: the input to be validated
        '''
        if self.isOver():
            raise GameOverError("Game already over")

        self.__gameValidator.validate(userInput)
        self.__gameRepository.addToColumn(int(userInput)-1,self.__player)
        if self.__gameRepository.checkForWin():
            return
        if self.__player == "Y":
            self.__player = "R"
        else:
            self.__player ="Y"

    def getPlayer(self):
        return self.__player

    def isOver(self):
        return self.__gameRepository.isOver()

    def getBoard(self):
        return self.__gameRepository.getBoard()

    def assertMoveValue(self,move,playerAtTurn):
        temporaryRepository = deepcopy(self.__gameRepository)
        temporaryValidator = deepcopy(self.__gameValidator)
        temporaryValidator.validate(move)
        temporaryRepository.addToColumn(move-1,playerAtTurn)
        temporaryRepository.checkForWin()
        if temporaryRepository.isOver():
            return 100
        else:
            return 0