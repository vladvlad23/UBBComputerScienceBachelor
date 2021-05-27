# -*- coding: utf-8 -*-
"""
Created on Tue Feb 19 15:48:58 2019

@author: Zsu
"""


class Bag:

    # creates a new, empty Bag
    def __init__(self):
        self.__list = []

    # adds a new element to the Bag
    def add(self, e):
        # the
        for item in self.__list:
            if item[0] == e:
                  # if we find the element, increase appearances
                  #since tuples are immutable, we must replace it
                self.__list[self.__list.index(item)] = (e,item[1]+1)
                return

        self.__list.append((e,1))

    # removes one occurrence of an element from a Bag
    # returns True if an element was actually removed (the Bag contained the element e), or False if nothing was removed
    def remove(self, e):
        for item in self.__list:
            if item[0] == e:
                if item[1] > 1:
                    self.__list[self.__list.index(item)] = (e, item[1] - 1)
                elif item[1] == 1:
                    del self.__list[self.__list.index(item)]
                return True

        return False


    # searches for an element e in the Bag
    # returns True if the Bag contains the element, False otherwise
    def search(self, e):
        for item in self.__list:
            if item[0]==e:
                return True
        return False

    # counts and returns the number of times the element e appears in the bag
    def nrOccurrences(self, e):
        for item in self.__list:
            if item[0]==e:
                return item[1]
        return 0

    # returns the size of the Bag (the number of elements)
    def size(self):
        sum=0
        for item in self.__list:
            sum+=item[1]
        return sum

    # returns True if the Bag is empty, False otherwise
    def isEmpty(self):
        return self.size()==0

    # returns a BagIterator for the Bag
    def iterator(self):
        return BagIterator(self)

class BagIterator:

    #creates an iterator for the Bag b, set to the first element of the bag, or invalid if the Bag is empty
    def __init__(self, b):
        self.__bag = b
        self.__currentElement = 0
        self.__currentApparitions = 1

    # returns True if the iterator is valid
    def valid(self):
        #embrace the pythonic way. Assume something is true and work around it if it's not
        #try:
        #    x = self.__bag._Bag__list[self.__currentElement]
        #    return True
        #except IndexError:
        #    return False

        #COmplexity is Theta(1)
        return self.__currentElement < len(self.__bag._Bag__list)

    # returns the current element from the iterator.
    # throws ValueError if the iterator is not valid
    def getCurrent(self):
        #complexity is theta(1)
        if self.valid() is not True:
            raise ValueError("Iterator not valid")

        return self.__bag._Bag__list[self.__currentElement][0]


    # moves the iterator to the next element
    #throws ValueError if the iterator is not valid
    def next(self):
        #complexity is theta(1)
        if self.valid() is not True:
            raise ValueError("Iterator not valid")
        if self.__currentApparitions >= self.__bag._Bag__list[self.__currentElement][1]:
            self.__currentElement+=1
            self.__currentApparitions=1
        else:
            self.__currentApparitions+=1

    # sets the iterator to the first element from the Bag
    def first(self):
        #theta(1)
        self.__currentApparitions = 1
        self.__currentElement = 0