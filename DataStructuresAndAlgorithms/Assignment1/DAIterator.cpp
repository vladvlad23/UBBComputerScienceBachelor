//
// Created by Vlad on 16-Mar-19.
//

#include <stdexcept>
#include "DAIterator.h"

DAIterator::DAIterator(const DynamicArray &c) : c(c)
{
    position = 0;
}

void DAIterator::first()
{
    position = 0;
}

bool DAIterator::valid() const
{
    if(position>=c.size())
        return false;
    return true;
}

void DAIterator::next()
{
    if(!valid())
    {
        position--;
        throw std::logic_error("Invalid position");
    }
    position++;
}

TElem DAIterator::getCurrent() const
{
    return c.getElement(position);
}
