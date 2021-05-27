from texttable import Texttable
from ArtificialIntelligence import ArtificialIntelligence
class Console:

    def __init__(self,gameController):
        self.__gameController = gameController

    def __UIStartGame(self):
        '''
        Function will play the game using the game controller. While the game isn't over, the
        board will be printed and input will be taken and send to makeMove() of the controller.
        If the game is over, the winner will be printed
        '''
        while not self.__gameController.isOver():
            print(self.__makeBoard(self.__gameController.getBoard()))
            print("\n\n")
            player = self.__gameController.getPlayer()
            try:
                user = input(player + " turn. Write a column please: ")
                self.__gameController.makeMove(user)
            except Exception as e:
                print()
                print(e)
                print()

        print(self.__makeBoard(self.__gameController.getBoard()))
        if player == "Y":
            print("AND THE WINNER IS: YELLOW PLAYER")
        if player == "R":
            print("AND THE WINNER IS: RED PLAYER")
        return True

    def __makeBoard(self,gameTable):
        '''
        Function will create the board using the Texttable library
        :param gameTable: the multidimensional array to be used
        '''
        game = Texttable()
        game.add_row([1,2,3,4,5,6,7])
        game.add_rows(gameTable,header=False)
        return game.draw()

    def __UIStartGameWithAI(self):
        '''
        Function will play the game using the game controller with AI. While the game isn't over, the
        board will be printed and input will be taken and send to makeMove() of the controller.
        If the game is over, the winner will be printed
        '''
        computer = ArtificialIntelligence()
        while not self.__gameController.isOver():
            print(self.__makeBoard(self.__gameController.getBoard()))
            print("\n\n")
            player = self.__gameController.getPlayer()
            if player == "Y":
                try:
                    user = input("Write a column please: ")
                    self.__gameController.makeMove(user)
                except Exception as e:
                    print()
                    print(e)
                    print()
            else:
                self.__gameController.makeMove(computer.compute(self.__gameController))
        print(self.__makeBoard(self.__gameController.getBoard()))
        if player == "R":
            print("AND THE WINNER IS: COMPUTER (and it's a really bad computer so you should feel bad)")
        if player == "Y":
            print("AND THE WINNER IS: RED PLAYER")
        return True

    def __UIPrintRules(self):
        object = "\t Object: \n" +\
                 "To win Connect Four you must be the first player to get four of your colored checkers in a row \n either horizontally, vertically or diagonally.\n"

        print(object)

    def startConsole(self):
        '''
        Function will shot the first menu with options to choose between starting the game and reading the rules
        '''
        help = " \t This is a game of Connect Four \n" + \
                      "1. Start game.\n" + \
                      "2. Read rules.\n" + \
                      "3. Start Game with AI. \n" + \
                      "0. Exit"

        commands = {"1": self.__UIStartGame,"2":self.__UIPrintRules,"3":self.__UIStartGameWithAI}

        while True:
            print(help)
            userInput = input(" >> ")
            if userInput in commands:
                if commands.get(userInput)() != None:
                    return
            elif userInput == "0":
                return
            else:
                print("A valid input, please")


