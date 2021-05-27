//
// Created by Vlad on 30-Mar-19.
//

#include <exception>
#include <stdexcept>
#include "LinkedListIterator.h"

void LinkedListIterator::first()
{
    position = 0;
}

void LinkedListIterator::next()
{
    if(!valid())
        throw std::invalid_argument("Iterator is invalid\n");
    position++;
}

bool LinkedListIterator::valid() const
{
    return position<container.size();
}

TElem LinkedListIterator::getCurrent() const
{
    if(!valid())
    {
        throw std::invalid_argument("Invalid iterator\n");
    }
    return container.getElement(position);
}

Node* LinkedListIterator::getCurrentNode() const
{
    if(!valid())
    {
        throw std::invalid_argument("Invalid iterator\n");
    }
    return container.getNodeByPosition(position);
}
