//
// Created by Vlad on 30-Mar-19.
//

#ifndef ASSIGNMENT2_LINKEDLISTITERATOR_H
#define ASSIGNMENT2_LINKEDLISTITERATOR_H

#include "TElemLinkedList.h"
class LinkedListIterator
{
    friend class TElemLinkedList;

private:
    const TElemLinkedList& container;
    int position;

    LinkedListIterator(const TElemLinkedList &container) : container(container){ position = 0;}
public:
    void first();
    void next();
    bool valid() const;
    TElem getCurrent() const;
    Node* getCurrentNode() const;

};


#endif //ASSIGNMENT2_LINKEDLISTITERATOR_H
