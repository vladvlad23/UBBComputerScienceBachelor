//
// Created by Vlad on 29-Mar-19.
//

#include <stdexcept>
#include "TElemLinkedList.h"
#include "LinkedListIterator.h"

//Theta(1)
Node *TElemLinkedList::getHead() const
{
    return head;
}

//Theta(1)
void TElemLinkedList::setHead(Node *head)
{
    if(!TElemLinkedList::head)
    {
        TElemLinkedList::head = head;
        TElemLinkedList::numberOfElements = 1;
        return;
    }
    head->next = TElemLinkedList::head->next;
    delete (TElemLinkedList::head);
    TElemLinkedList::head = head;
}

//O(n)
Node * TElemLinkedList::search(TElem elementToBeSearched) const
{
    Node *node = head;
    while(node != nullptr)
    {
        if(node->information == elementToBeSearched)
            return node;
        node = node->next;
    }
    return nullptr;
}

//add to the end
//O(n)
void TElemLinkedList::add(TElem elementToBeAdded)
{
    Node *node = head;
    if(node == nullptr)
    {
        head = new Node(elementToBeAdded);
        numberOfElements++;
        return;
    }
    while(node->next)
    {
        node = node->next;
    }
    node->next = new Node(elementToBeAdded);
    numberOfElements++;
}

//Theta(1)
void TElemLinkedList::addAfter(Node *currentNode, TElem elementToBeAdded)
{
    if(!head)
    {
        head = new Node(elementToBeAdded);
        numberOfElements++;
        return;
    }
    Node *node = new Node(elementToBeAdded);
    node->next = currentNode->next;
    currentNode->next = node;
    numberOfElements++;

}

//O(n)
void TElemLinkedList::remove(TElem elementToBeRemoved)
{
    Node *node = head;
    if(!head)
        throw std::invalid_argument("There are no elements\n");
    if(head->information == elementToBeRemoved)
    {
        head = head->next;
        free(node);
        numberOfElements--;
        return;
    }

    while( node->next != nullptr && node->next->information!= elementToBeRemoved)
    {
        node = node->next;
    }
    if(node->next)
    {
        Node *saveForFreeing = node->next;
        node->next = node->next->next;
        delete saveForFreeing;
    }
    else
    {
        delete node->next;
        node->next = nullptr;
    }
    numberOfElements--;
}

//O(n)
TElem TElemLinkedList::getElement(int position) const
{
    int i;
    if(!head)
        throw std::invalid_argument("There is no such position\n");
    Node *node = head;
    for(i=0;i<position;i++)
    {
        if(!(node->next))
        {
            throw std::invalid_argument("There is no such position\n");
        }
        node = node->next;
    }
    return node->information;

}

LinkedListIterator TElemLinkedList::iterator() const
{
    return LinkedListIterator(*this);
}


TElemLinkedList::TElemLinkedList(TElem firstElement)
{
    head = new Node(firstElement);
    numberOfElements = 0;
}

//destroy linked list
//O(n)
TElemLinkedList::~TElemLinkedList()
{
    while(head)
    {
        Node *forDelete = head;
        head = head->next;
        delete forDelete;
    }
}


TElemLinkedList::TElemLinkedList()
{
    head = nullptr;
    numberOfElements = 0;

}

//Theta(1)
int TElemLinkedList::size() const
{
    return numberOfElements;
}

//Theta(1)
bool TElemLinkedList::isEmpty() const
{
    return head == nullptr;
}

//O(n)
Node *TElemLinkedList::getNodeByPosition(int position) const
{
    int i;
    Node *node = head;
    for(i=0;i<position;i++)
    {
        if(!(node->next))
        {
            throw std::invalid_argument("There is no such position");
        }
        node = node->next;
    }
    return node;
};


