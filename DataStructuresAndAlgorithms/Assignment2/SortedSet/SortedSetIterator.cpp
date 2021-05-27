//
// Created by Vlad on 30-Mar-19.
//

#include <stdexcept>
#include "SortedSetIterator.h"

void SortedSetIterator::next()
{
    if(!valid())
        throw std::invalid_argument("Invalid iterator\n");
    iterator.next();
}

bool SortedSetIterator::valid() const
{
    return iterator.valid();
}

void SortedSetIterator::first()
{
    iterator.first();
}

TElem SortedSetIterator::getCurrent() const
{
    return iterator.getCurrent();
}
