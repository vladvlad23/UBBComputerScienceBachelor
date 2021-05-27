import unittest

class CustomIterable:

    def __init__(self):
        self.__list = []

    def __setitem__(self, key, value):
        self.__list[key] = value

    def __getitem__(self, item):
        return self.__list[item]

    def __next__(self):
        if self.__state < len(self.__list):
            self.__state +=1
            return self.__getitem__(self.__state-1)
        else:
            raise StopIteration

    def __iter__(self):
        self.__state = 0
        return self

    def __delitem__(self, key):
        del self.__list[key]

    def __len__(self):
        return len(self.__list)

    def append(self,item):
        self.__list.append(item)

    def index(self,item):
        return self.__list.index(item)

    def reverse(self):
        self.__list.reverse()


def sort(list,comparison):
    '''
    Comb sort
    :param list: the list to be sorted
    :param comparison: the comparison to be made
    :return:
    '''
    numberOfElements = len(list)
    gap = numberOfElements

    swap = True

    while gap > 1 or swap == True:
        gap = int(gap//(1.3))

        swap = False

        for i in range(0,numberOfElements - gap):
            if not comparison(list[i],list[i+gap]):
                temp = list[i]
                list[i] = list[i+gap]
                list[i+gap] = temp
                swap = True

def filter(list,property):
    index = 0
    while index<len(list):
        if not property(list[index]):
            del list[index]
        else:
            index+=1

class CustomIterableTest(unittest.TestCase):

    def test_creation(self):
        list = CustomIterable()

    def test_appending(self):
        list = CustomIterable()
        list.append(10)
        assert list[0] == 10

        list.append(20)
        assert list[1] == 20

    def test_len(self):
        list = CustomIterable()
        list.append(10)
        list.append(20)

        assert len(list) == 2

    def test_remove(self):
        list = CustomIterable()
        list.append(10)
        list.append(20)
        assert len(list) == 2

        del list[0]

        assert len(list) == 1
        assert list[0] == 20

        del list[0]

        assert len(list) == 0

    def test_sortIncreasing(self):
        list = CustomIterable()
        prop = self.increasing_property
        list.append(50)
        list.append(40)
        list.append(30)
        list.append(20)
        list.append(10)

        sort(list,prop)

        assert list[0] == 10
        assert list[1] == 20
        assert list[2] == 30
        assert list[3] == 40
        assert list[4] == 50

    def test_sortDecreasing(self):
        list = CustomIterable()
        prop = self.decreasing_property
        list.append(10)
        list.append(20)
        list.append(30)
        list.append(40)
        list.append(50)

        sort(list,prop)

        assert list[0] == 50
        assert list[1] == 40
        assert list[2] == 30
        assert list[3] == 20
        assert list[4] == 10

    def decreasing_property(self,a,b):
        return a>=b

    def increasing_property(self,a,b):
        return a<=b

    def test_filter(self):
        list = CustomIterable()
        prop = self.__smallerThan35
        list.append(10)
        list.append(20)
        list.append(30)
        list.append(40)
        list.append(50)

        filter(list,prop)

        assert len(list) == 3
        assert list[0] == 10
        assert list[1] == 20
        assert list[2] == 30

        prop = self.__biggerThan35
        filter(list,prop)

        assert len(list) == 0

    def __smallerThan35(self,number):
        return number<35
    def __biggerThan35(self,number):
        return number>35



