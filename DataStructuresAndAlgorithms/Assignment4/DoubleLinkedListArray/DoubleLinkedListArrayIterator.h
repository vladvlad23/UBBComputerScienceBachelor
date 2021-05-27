//
// Created by Vlad on 14-Apr-19.
//

#ifndef ASSIGNMENT4_DOUBLELINKEDLISTARRAYITERATOR_H
#define ASSIGNMENT4_DOUBLELINKEDLISTARRAYITERATOR_H

#include "DoubleLinkedListArray.h"

class DoubleLinkedListArrayIterator
{

    friend class DoubleLinkedListArray;



private:
    int position;
    const DoubleLinkedListArray &container;
    DoubleLinkedListArrayIterator(const DoubleLinkedListArray &container) : container(container) {first();}
public:

    void first();
    void next();
    TElem getCurrent() const;
    bool valid() const;

};


#endif //ASSIGNMENT4_DOUBLELINKEDLISTARRAYITERATOR_H
