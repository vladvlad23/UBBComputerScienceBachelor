//
// Created by Vlad on 17-Apr-19.
//

#include "Heap.h"
#include <algorithm>

Heap::Heap()
{
    size=0;
    capacity=10;
    elements = new TElem[capacity];
}

Heap::~Heap()
{
    delete [] elements;
}

void Heap::bubbleUp(TElem newElement)
{
    if(size+1>capacity) //we double if there is not enough size
        doubleCapacity();
    elements[size] = newElement; //we append the new element to the end
    int position = size++; // we take the position before incrementing size

    while (elements[position] < elements[getParent(position)]) // while the elements are not in a good order, keep swapping
    {
        //switching
        int temp = elements[position];
        elements[position] = elements[getParent(position) ];
        elements[getParent(position)] = temp;
        position = getParent(position); //update position
    }
}

void Heap::doubleCapacity()
{
    TElem *newElements = new TElem[2*capacity];
    for(int i=0;i<size;i++)
    {
        newElements[i] = elements[i];
    }
    capacity*=2;
    delete [] elements;
    elements = newElements;

}

void Heap::readFromList(std::list<TElem> &readingList)
{
    for(auto const &element:readingList)
    {
        add(element);
    }

}

void Heap::writeToList(std::list<TElem>& writingList)
{
    while (size > 0)
        writingList.push_back(removeTop());
}

void Heap::bubbleDown(){
    int currentPos = 0;
    while (currentPos < size){
        int lChildPos = getLeftChild(currentPos);
        int rChildPos = getRightChild(currentPos);

        if(lChildPos < size && elements[lChildPos] < elements[currentPos]) { //Left child test and value update
            std::swap(elements[currentPos], elements[lChildPos]);
            currentPos = lChildPos;
        }
        if(rChildPos < size && elements[rChildPos] < elements[currentPos]) { //Right child test and value update
            std::swap(elements[currentPos], elements[rChildPos]);
            currentPos = rChildPos;
        }
        else // if neither child is valid, we end the loop
            break;
    }
}

TElem Heap::removeTop(){
    TElem returnItem = elements[0];
    std::swap(elements[0], elements[size - 1]); //Swapping the first and last elements
    size --;

    bubbleDown();
    return returnItem;
}

void Heap::removeSmallestElements(int k){
    for (int i = 1; i <= k; ++ i)
        removeTop();
}

