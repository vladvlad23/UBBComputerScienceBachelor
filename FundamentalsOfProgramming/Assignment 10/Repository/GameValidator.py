from Repository.Exceptions import InvalidInputError,FullColumnError

class GameValidator:

    def __init__(self,gameRepository):
        self.__gameRepository = gameRepository

    def validate(self,input):
        try:
            input = int(input)
        except ValueError:
            raise InvalidInputError("Input must be int")

        if input<1 or input>7:
            raise InvalidInputError("Input must be between 1 and 7 (there are just 7 columns)")

        if self.__gameRepository.isColumnFull(input-1):
            raise FullColumnError("Column is full. Try again")

