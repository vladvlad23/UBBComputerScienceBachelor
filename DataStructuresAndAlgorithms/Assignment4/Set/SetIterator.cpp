//
// Created by Vlad on 13-Apr-19.
//

#include <stdexcept>
#include "SetIterator.h"
#include "Set.h"

SetIterator::SetIterator(const Set &c) : c(c),listIterator(c.elements.iterator()) {}

void SetIterator::first()
{
    listIterator.first();
}

void SetIterator::next()
{
    listIterator.next();
}

bool SetIterator::valid() const
{
    return listIterator.valid();
}

TElem SetIterator::getCurrent() const
{
    return listIterator.getCurrent();
}