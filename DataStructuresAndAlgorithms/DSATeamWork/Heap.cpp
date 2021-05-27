//
// Created by Vlad on 17-Apr-19.
//

#include "Heap.h"
#include <algorithm>
#include <stdexcept>
#include <assert.h>

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

    while (position > 0 && elements[position] > elements[getParent(position)]) // while the elements are not in a good order, keep swapping
    {
        //switching
        std::swap(elements[position], elements[getParent(position)]);
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

void Heap::bubbleDown(){
    int currentPos = 0;

    int lChildPos = getLeftChild(currentPos);
    int rChildPos = getRightChild(currentPos);
    while(lChildPos < size || rChildPos < size)
    {
        if(lChildPos<size && rChildPos<size)
        {
            if(elements[currentPos] > elements[lChildPos] &&
            elements[currentPos] > elements[rChildPos])
            {
                break;
            }
            else
            {
                if (elements[lChildPos] > elements[rChildPos])
                {
                    std::swap(elements[lChildPos],elements[currentPos]);
                    currentPos = lChildPos;
                }
                else
                {
                    std::swap(elements[rChildPos],elements[currentPos]);
                    currentPos = rChildPos;
                }
            }
        }
        else// there is only one child
        {
            if(lChildPos < size)
            {
                if (elements[currentPos] < elements[lChildPos])
                {
                    std::swap(elements[currentPos],elements[lChildPos]);
                    currentPos = lChildPos;
                }
                else
                    break;
            }
            else
            {
                if (elements[currentPos] < elements[rChildPos])
                {
                    std::swap(elements[currentPos],elements[rChildPos]);
                    currentPos = rChildPos;
                }
                else
                    break;
            }
        }
        lChildPos = getLeftChild(currentPos);
        rChildPos = getRightChild(currentPos);
    }
}

bool Heap::checkForBalance()
{
    int i;
    while(getLeftChild(i) < size || getRightChild(i) < size)
    {
        if(getLeftChild(i) < size)
            assert(elements[i]>elements[getLeftChild(i)]);
        else if(getRightChild(i)<size)
            assert(elements[i]>elements[getRightChild(i)]);
    }
}

TElem Heap::removeTop(){
    TElem returnItem = elements[0];
    std::swap(elements[0], elements[size - 1]); //Swapping the first and last elements
    size--;
    bubbleDown();
    return returnItem;
}


TElem Heap::peek()
{
    return elements[0];
}

void Heap::printHeap()
{
    for(int i=0;i<size;i++)
    {
        std::cout << elements[i] << ",";
    }
    std::cout<<std::endl;

}

