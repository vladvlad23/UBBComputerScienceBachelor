class ValidationException(Exception):
    def __init__(self,message):
        self.__message = message

    def getMessage(self):
        return self.__message

class NotFoundException(Exception):
    def __init__(self,message):
        self._message = message

    def getMessage(self):
        return self.__message

class DuplicateException(Exception):
    def __init__(self,message):
        self.__message = message
    def getMessage(self):
        return self.__message

class DateException(Exception):
    def __init__(self,message):
        self.__message = message
    def getMessage(self):
        return self.__message

class FileRepositoryException(Exception):
    def __init__(self,message):
        self.__message = message

    def getMessage(self):
        return self.__message

