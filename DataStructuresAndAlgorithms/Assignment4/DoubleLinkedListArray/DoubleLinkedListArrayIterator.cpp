//
// Created by Vlad on 14-Apr-19.
//

#include <stdexcept>
#include "DoubleLinkedListArrayIterator.h"

void DoubleLinkedListArrayIterator::first()
{
    position = container.head;
}

void DoubleLinkedListArrayIterator::next()
{
    if(!valid())
        throw std::invalid_argument("Invalid iterator.\n");
    position = container.nodes[position].next;
}

TElem DoubleLinkedListArrayIterator::getCurrent() const
{
    return container.nodes[position].info;
}

bool DoubleLinkedListArrayIterator::valid() const
{
    return position!=-1;
}
