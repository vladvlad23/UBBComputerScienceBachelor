import unittest
from Repository.GameValidator import GameValidator
from Repository.GameRepository import GameRepository
from Controller.GameController import GameController
from Repository.Exceptions import *

class GameTest(unittest.TestCase):

    def test_WinHorizontally(self):
        gameRepository = GameRepository()
        gameValidator = GameValidator(gameRepository)
        gameController = GameController(gameRepository, gameValidator)

        gameController.makeMove(1)
        gameController.makeMove(1)
        gameController.makeMove(2)
        gameController.makeMove(2)
        gameController.makeMove(3)
        gameController.makeMove(3)
        gameController.makeMove(4)

        try:
            gameController.makeMove(4)
            assert False
        except Exception as e:
            s = str(e)
            assert s == "Game already over"

        assert gameController.isOver() == True
        assert gameController.getPlayer() == "Y"

    def test_WinVertically(self):
        gameRepository = GameRepository()
        gameValidator = GameValidator(gameRepository)
        gameController = GameController(gameRepository, gameValidator)

        gameController.makeMove(1)
        gameController.makeMove(2)
        gameController.makeMove(1)
        gameController.makeMove(2)
        gameController.makeMove(1)
        gameController.makeMove(2)
        gameController.makeMove(1)

        assert gameController.isOver() == True
        assert gameController.getPlayer() == "Y"

    def test_WinHorizontallyRight(self):
        gameRepository = GameRepository()
        gameValidator = GameValidator(gameRepository)
        gameController = GameController(gameRepository, gameValidator)

        gameController.makeMove(1)
        gameController.makeMove(2)
        gameController.makeMove(2)
        gameController.makeMove(1)
        gameController.makeMove(3)
        gameController.makeMove(3)
        gameController.makeMove(3)
        gameController.makeMove(4)
        gameController.makeMove(4)
        gameController.makeMove(4)
        gameController.makeMove(4)

    def test_WinHorizontallyLeft(self):
        gameRepository = GameRepository()
        gameValidator = GameValidator(gameRepository)
        gameController = GameController(gameRepository, gameValidator)

        gameController.makeMove(7)
        gameController.makeMove(6)
        gameController.makeMove(6)
        gameController.makeMove(7)
        gameController.makeMove(5)
        gameController.makeMove(5)
        gameController.makeMove(5)
        gameController.makeMove(4)
        gameController.makeMove(4)
        gameController.makeMove(4)
        gameController.makeMove(4)

        assert gameController.isOver() == True
        assert gameController.getPlayer() == "Y"

    def test_columnFull(self):
        gameRepository = GameRepository()
        gameValidator = GameValidator(gameRepository)
        gameController = GameController(gameRepository, gameValidator)

        gameController.makeMove(1)
        gameController.makeMove(1)
        gameController.makeMove(1)
        gameController.makeMove(1)
        gameController.makeMove(1)
        gameController.makeMove(1)
        try:
            gameController.makeMove(1)
        except FullColumnError:
            pass
        try:
            gameController.makeMove("batman")
        except InvalidInputError:
            pass


