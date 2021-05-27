//
// Created by Vlad on 30-Mar-19.
//

#ifndef ASSIGNMENT2_SORTEDSETITERATOR_H
#define ASSIGNMENT2_SORTEDSETITERATOR_H


#include "SortedSet.h"
#include "../LinkedList/LinkedListIterator.h"

class SortedSetIterator
{
    friend class SortedSet;

private:
    const SortedSet &container;
    LinkedListIterator iterator;
public:
    SortedSetIterator(const SortedSet &container) : container(container),iterator(container.list.iterator()){}
    void next();
    bool valid() const;
    void first();
    TElem getCurrent() const;


};


#endif //ASSIGNMENT2_SORTEDSETITERATOR_H
