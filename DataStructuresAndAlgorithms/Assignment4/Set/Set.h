//
// Created by Vlad on 13-Apr-19.
//

#ifndef ASSIGNMENT4_SET_H
#define ASSIGNMENT4_SET_H

#include "../DoubleLinkedListArray/DoubleLinkedListArray.h"

typedef int TElem;

class SetIterator;

class Set {

    friend class SetIterator;

private:

    /* representation of Set*/
    DoubleLinkedListArray elements;

public:

    //implicit constructor

    Set();



    //adds an element to the  set

    //if the element was added, the operation returns true, otherwise (if the element was already in the set)

    //it returns false

    bool add(TElem e);



    //removes an element from the set

    //if the element was removed, it returns true, otherwise false

    bool remove(TElem e);



    //checks if an element is in the  set

    bool search(TElem elem) const;



    //returns the number of elements;

    int size() const;



    //checks if the set is empty

    bool isEmpty() const;



    //returns an iterator for the set

    SetIterator iterator() const;

    int difference(const Set &s);

    //destructor

    ~Set();



};












#endif //ASSIGNMENT4_SET_H
