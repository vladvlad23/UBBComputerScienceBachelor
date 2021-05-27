//
// Created by Vlad on 29-Mar-19.
//

#include <stdexcept>
#include "SortedSet.h"
#include "SortedSetIterator.h"

SortedSet::SortedSet(Relation r)
{
    relation = r;
}

//Complexity of O(n)
bool SortedSet::add(TComp e)
{
    if(!list.getHead())
    {
        list.setHead(new Node(e));
        return true;
    }
    Node *prevNode = list.getHead();
    if(prevNode->information == e)
        return false;

    if(!relation(prevNode->information,e)) // means we have to change head
    {
        if(prevNode->information!=e)
        {
            list.addAfter(list.getHead(),list.getHead()->information);
            list.setHead(new Node(e));
            return true;
        }
        else
            return false;
    }
    Node *currentNode = prevNode->next;
    for(int i=1;i<list.size() && relation(currentNode->information,e);i++)
    {
        prevNode = currentNode;
        if(prevNode->information == e)
            return false;
        currentNode = currentNode->next;
    }
    if(prevNode->next)
        if(prevNode->next->information == e)
            return false;
    list.addAfter(prevNode, e);
    return true;
}
//Complexity O(n)
bool SortedSet::remove(TComp e)
{
    if(!list.search(e))
        return false;
    list.remove(e);
    return true;
}

//Complexity O(n)
bool SortedSet::search(TElem elem) const
{
    return (list.search(elem) != nullptr);
}

//Complexity Theta(1)
int SortedSet::size() const
{
    return list.size();
}

//Complexity Theta(1)
bool SortedSet::isEmpty() const
{
    return list.isEmpty();
}

//Complexity Theta(1)
SortedSetIterator SortedSet::iterator() const
{
    return SortedSetIterator(*this);
}

SortedSet::~SortedSet()
{

}