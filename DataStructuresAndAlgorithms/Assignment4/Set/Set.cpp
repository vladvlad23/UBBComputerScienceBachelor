//
// Created by Vlad on 13-Apr-19.
//

#include <stdexcept>
#include "Set.h"
#include "SetIterator.h"

Set::Set()
{

}

bool Set::add(TElem e)
{
    if(elements.search(e))
        return false;
    elements.insertAtEnd(e);
    return true;
}

bool Set::remove(TElem e)
{
    int searchResult = elements.searchPosition(e);
    if(searchResult==-1)
        return false;
    elements.removeByNodePosition(searchResult);
    return true;
}

bool Set::search(TElem elem) const
{
    return elements.search(elem);
}

int Set::size() const
{
    return elements.getSize();
}

bool Set::isEmpty() const
{
    return elements.getSize()==0;
}

SetIterator Set::iterator() const
{
    return SetIterator(*this);
}

Set::~Set()
{

}

int Set::difference(const Set &s)
{
    SetIterator it = s.iterator();
    it.first();
    int numberRemoved=0;
    while(it.valid())
    {
        TElem potentiallyRemoved = it.getCurrent();
        if(remove(potentiallyRemoved))
        {
            numberRemoved++;
        }
        it.next();
    }
    return numberRemoved;
}

/*
 * Iterating through the elements with iterator - theta(m)
 * Searching for elements each iteration and removing them if necessary - O(n)
 * Total complexity - O(n*m)
 * best case - theta(n)
 * average and worst - O(n*m)
 *
 * subalgorithm difference(firstSet,secondSet):
 *      it <- firstSet.iterator
 *      numberRemoved <- 0
 *      while it.valid execute:
 *          potentiallyRemoved <- it.getCurrent
 *          if firstSet.search(potentiallyRemoved) is true then:
 *              firstSet.remove(potentiallyRemoved)
 *              numberRemoved <- numberRemoved + 1
 *          end-if
 *          it.next
 *      end-while
 *
 *      difference <- numberRemoved
 *
 *



 */