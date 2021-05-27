from Repository.Exceptions import FullColumnError

class ArtificialIntelligence:

    def __init__(self):
        pass

    def compute(self,boardController):
        values = []
        opponentValues = []
        for i in range(6):
            try:
                values.append(boardController.assertMoveValue(i+1,"R"))
            except FullColumnError:
                values.append(-1) #just can't make the move, don't consider it
        for i in range(6):
            try:
                opponentValues.append(boardController.assertMoveValue(i+1,"Y"))
            except FullColumnError:
                opponentValues.append(-1)
        if max(values)+1 != 100:
            return opponentValues.index(max(opponentValues))+1
        else:
            return values.index(max(values))+1