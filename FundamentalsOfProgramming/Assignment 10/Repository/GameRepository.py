from texttable import Texttable
class GameRepository:

    def __init__(self):
        '''
        Function will initialized the game board and the "won" state as false
        '''
        self.__gameTable = 6*[[]]
        for i in range(6):
            self.__gameTable[i] = ["0"]*7
        self.__won = False

    def getBoard(self):
        '''
        :return: the gameboard
        '''
        return self.__gameTable

    def addToColumn(self,column,player):
        '''
        Function will add to the given column a "circle" for the player
        :param column: the column to be added
        :param player: the player
        '''
        for i in range(6):
            if self.__gameTable[i][column] != "0" and i!=0:
                self.__gameTable[i-1][column] = player
                return
        self.__gameTable[i][column] = player #this means nothing is on the bottom row

    def checkForWin(self):
        '''
        Function will check if the game is over
        :return: true if the game is over and false otherwise
        '''
        for i in range(6):
            for j in range(7):
                if self.checkAllSides(i,j)==True:
                    self.__won = True
                    return True

    def checkForImmediateWin(self):
        for i in range(6):
            for j in range(7):
                if self.checkAllSides(i,j)==True:
                    return (i,j)

    def checkAllSides(self,row,column):
        '''
        :return: Function will return true if after checking all conditions of the conect four game
        one of them is fulfilled
        '''
        if row+3<6:
            if self.__gameTable[row][column]!="0" and \
                self.__gameTable[row][column] == self.__gameTable[row+1][column] and \
                self.__gameTable[row][column] == self.__gameTable[row+2][column] and \
                self.__gameTable[row][column] == self.__gameTable[row+3][column]:
                    return True
        if column+3<6:
            if self.__gameTable[row][column]!="0" and \
                self.__gameTable[row][column] == self.__gameTable[row][column+1] and \
                self.__gameTable[row][column] == self.__gameTable[row][column+2] and \
                self.__gameTable[row][column] == self.__gameTable[row][column+3]:
                    return True

        if row-3<6 and column+3<6:
            if self.__gameTable[row][column]!="0" and \
                self.__gameTable[row][column] == self.__gameTable[row-1][column+1] and \
                self.__gameTable[row][column] == self.__gameTable[row-2][column+2] and \
                self.__gameTable[row][column] == self.__gameTable[row-3][column+3]:
                    return True

        if row-3>0:
            if self.__gameTable[row][column]!="0" and \
                self.__gameTable[row][column] == self.__gameTable[row-1][column] and \
                self.__gameTable[row][column] == self.__gameTable[row-2][column] and \
                self.__gameTable[row][column] == self.__gameTable[row-3][column]:
                    return True

        if column-3>0:
            if self.__gameTable[row][column]!="0" and \
                self.__gameTable[row][column] == self.__gameTable[row][column-1] and \
                self.__gameTable[row][column] == self.__gameTable[row][column-2] and \
                self.__gameTable[row][column] == self.__gameTable[row][column-3]:
                    return True

        if row+3>0 and column-3>0:
            if self.__gameTable[row][column]!="0" and \
                self.__gameTable[row][column] == self.__gameTable[row-1][column-1] and \
                self.__gameTable[row][column] == self.__gameTable[row-2][column-2] and \
                self.__gameTable[row][column] == self.__gameTable[row-3][column-3]:
                    return True

    def __checkNeighbours(self,row,column):
        '''
        :return: Function will return the max number of neighbours of same colour
        '''
        leftRowNumber = 0
        rightRowNumber = 0
        upwardsColumnNumber = 0
        downwardsColumnNumber = 0
        leftDiagonalNumber = 0
        rightDiagonalNumber = 0
        if row+4<6:
            if self.__gameTable[row][column]!="0":
                if self.__gameTable[row][column] == self.__gameTable[row+1][column]:
                    rightRowNumber+=1
                    if self.__gameTable[row][column] == self.__gameTable[row+2][column]:
                        rightRowNumber+=1
                        if self.__gameTable[row][column] == self.__gameTable[row+3][column]:
                            rightRowNumber+=1
                            return rightRowNumber
        if column+4<6:
                if self.__gameTable[row][column] != "0" and self.__gameTable[row][column] == self.__gameTable[row][column+1]:
                    upwardsColumnNumber += 1
                    if self.__gameTable[row][column] == self.__gameTable[row][column+2]:
                        upwardsColumnNumber += 1
                        if self.__gameTable[row][column] == self.__gameTable[row][column+3]:
                            upwardsColumnNumber += 1
                            return upwardsColumnNumber

        if row+4<6 and column+4<6:
            if self.__gameTable[row][column] != "0" and self.__gameTable[row][column] == self.__gameTable[row+1][column+1]:
                leftDiagonalNumber += 1
                if self.__gameTable[row][column] == self.__gameTable[row+2][column+2]:
                    leftDiagonalNumber += 1
                    if self.__gameTable[row][column] == self.__gameTable[row+3][column+3]:
                        leftDiagonalNumber += 1
                        return leftDiagonalNumber

        if row-4>0:
            if self.__gameTable[row][column] != "0" and self.__gameTable[row][column] == self.__gameTable[row-1][column]:
                leftRowNumber += 1
                if self.__gameTable[row][column] == self.__gameTable[row-2][column]:
                    leftRowNumber += 1
                    if self.__gameTable[row][column] == self.__gameTable[row-3][column]:
                        leftRowNumber += 1
                        return leftRowNumber

        if column-4>0:
            if self.__gameTable[row][column] != "0" and self.__gameTable[row][column] == self.__gameTable[row][column-1]:
                downwardsColumnNumber+=1
                if self.__gameTable[row][column] == self.__gameTable[row][column-2]:
                    downwardsColumnNumber += 1
                    if self.__gameTable[row][column] == self.__gameTable[row][column-3]:
                        downwardsColumnNumber += 1
                        return downwardsColumnNumber

        if row-4>0 and column-4>0:
            if self.__gameTable[row][column] != "0" and self.__gameTable[row][column] == self.__gameTable[row-1][column-1]:
                rightDiagonalNumber += 1
                if self.__gameTable[row][column] == self.__gameTable[row-2][column-2]:
                    rightDiagonalNumber += 1
                    if self.__gameTable[row][column] == self.__gameTable[row-3][column-3]:
                        rightDiagonalNumber += 1
                        return rightDiagonalNumber
        return max(leftRowNumber,rightDiagonalNumber,rightRowNumber,leftDiagonalNumber,rightDiagonalNumber,upwardsColumnNumber,downwardsColumnNumber)

    def isOver(self):
        '''
        :return: true if the game is over and false otherwise
        '''
        return self.__won

    def isColumnFull(self,column):
        '''
        Checks is a column is full
        :param column: the column to be checked
        :return: true if the first row of the column is not 0, false otherwise
        '''
        return self.__gameTable[0][column] != "0"

    def setBoard(self,table):
        '''
        Sets the table to a given state
        '''
        self.__gameTable = table





