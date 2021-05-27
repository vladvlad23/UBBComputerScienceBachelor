from Assignment_05_07_refactored.repository.RentalRepository import RentalRepository
from Assignment_05_07_refactored.domain.Exceptions import FileRepositoryException
import pickle

class RentalPickleRepository(RentalRepository):

    def __init__(self,fileName="rentals.pickle"):
        RentalRepository.__init__(self)
        self.__fileName = fileName
        self.__loadFromFile()

    def add(self,rental):
        RentalRepository.add(self,rental)
        self.storeToFile()

    def addStartingRentals(self,clientList):
        RentalRepository.addStartingRentals(self,clientList)
        self.storeToFile()

    def addRentalAtIndex(self, rental, index):
        RentalRepository.addRentalAtIndex(self,rental,index)
        self.storeToFile()

    def remove(self,rentalId):
        return RentalRepository.remove(self,rentalId)
        self.storeToFile()

    def complete(self,rentalId,date):
        RentalRepository.complete(self,rentalId,date)
        self.storeToFile()

    def uncomplete(self,rentalId):
        RentalRepository.uncomplete(self,rentalId)
        self.storeToFile()

    def __loadFromFile(self):
        try:
            file = open(self.__fileName,'rb')
            self._rentalList = pickle.load(file)
        except EOFError:
            pass
        except IOError:
            raise FileRepositoryException("Rental File Error")
        finally:
            file.close()

    def storeToFile(self):
        try:
            file = open(self.__fileName,"wb")
            pickle.dump(self._rentalList,file)
        except IOError:
            raise FileRepositoryException("Error at rental file writing")
        finally:
            file.close()



