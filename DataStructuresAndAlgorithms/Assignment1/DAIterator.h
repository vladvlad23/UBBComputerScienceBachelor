//
// Created by Vlad on 16-Mar-19.
//

#ifndef ASSIGNMENT1_DAITERATOR_H
#define ASSIGNMENT1_DAITERATOR_H
#include "DynamicArray.h"

class DynamicArray;

class DAIterator {
    friend class DynamicArray;
private:

    //contains a reference of the container it iterates over

    const DynamicArray& c;

    //Constructor receives a reference of the container.

    //after creation the iterator will refer to the first element of the container, or it will be invalid if the container is empty

    DAIterator(const DynamicArray& c);


    /* representation specific for the iterator*/
    int position;


public:
    //sets the iterator to the first element of the container

    void first();



    //moves the iterator to the next element

    //throws exception if the iterator is not valid

    void next();



    //checks if the iterator is valid

    bool valid() const;



    //returns the value of the current element from the iterator

    // throws exception if the iterator is not valid

    TElem getCurrent() const;



};


#endif //ASSIGNMENT1_DAITERATOR_H
