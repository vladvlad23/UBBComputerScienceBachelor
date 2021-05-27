//
// Created by Vlad on 16-Mar-19.
//

#include <exception>
#include <stdexcept>
#include "DynamicArray.h"
#include "DAIterator.h"
DynamicArray::DynamicArray(int initialSize)
{
    /*
     * description = creates new empty Dynamic Array with initial capacity = initialSize
     * pre: initialSize belongs to N*
     * post: a new empty Dynamic array with capacity initialSize
     * throws: exception if initialSize is negative or zero
     */
    if(initialSize<=0)
        throw std::invalid_argument("Invalid argument for initial size!");
    elements = new TElem[initialSize];
    numberOfElements = 0;
    capacity = initialSize;
}
DynamicArray::~DynamicArray()
{
    /*
     * description = destroys a DynamicArray(destructor)
     * pre: a Dynamic Array
     * post: memory from the given Dynamic Array is free
     */
    delete [] elements;
}
int DynamicArray::size () const
{
    /*
     * description: returns the size (number of elements) of the DynamicArray
     * pre:
     * post: returns the number of elements in the array
     */
    return numberOfElements;
}
TElem DynamicArray::getElement(int position) const
{
    /*
     * description: returns the element from a position from the DynamicArray
     * pre: 0 <= position < number of elements
     * post: the element from the position "position"
     * throws: an exception if "position" is not a valid position
     */
    if (position>=numberOfElements || position < 0)
        throw std::invalid_argument("Invalid argument for getting element from a given position!");

    return elements[position];
}
TElem DynamicArray::setElement(int position,TElem newElement)
{
    /*
     * description: changes the element from a position to another value
     * pre: 0<= position < number of elements, newElement a valid TElem
     * post: the element from position "position" gets replaced with newElement. The function will also return the old value from that position
     * throws: exception if "position" is not a valid position
     */

    if (position>=numberOfElements || position < 0)
        throw std::invalid_argument("Invalid position argument!");
    TElem temporary = elements[position];
    elements[position] = newElement;
    return temporary;
}
void DynamicArray::addToEnd(TElem newElement)
{
    /*
     * adds an element to the end of a DynamicArray. If array is full, increase capacity
     * pre: newElement is valid
     * post: newElement is on the last position of the DynamicArray which might or might not be increased in size according to the requirements
     */
    if (numberOfElements+1 == capacity)
    {
        doubleCapacity();
    }
    elements[numberOfElements++] = newElement;

}

void DynamicArray::doubleCapacity()
{
    /*
     * description : double storing capacity of the container
     * pre: a working container
     * post: identical container just with double capacity
     */
    TElem *elementsCopy = new TElem[capacity*2];
    int i;
    for(i=0;i<numberOfElements;i++)
    {
        elementsCopy[i] = elements[i];
    }
    delete [] elements;
    elements = elementsCopy;
    capacity*=2;
};


void DynamicArray::addToPosition(int pos,TElem newElem)
{
    /*
     * description: method adds "newElem" to the position "pos" by shifting the others to the left
     * pre: valid position, valid TElem
     * post: same array shifted as needed to the right and if needed with double the previous capacity
     */
    if (numberOfElements+1 == capacity)
    {
        doubleCapacity();
    }
    int i;
    for(i=numberOfElements+1;i>pos;i--)
        elements[i] = elements[i-1];
    elements[pos] = newElem;
    numberOfElements++;

}
//O(n)
TElem DynamicArray::remove(int position)
{
    /*
     * description: removes an element from position pos
     * pre : pos is a valid position
     * post : the element from position pos is removed
     * throws: exception if pos is invalid
    */
    if (position>=numberOfElements || position < 0)
        throw std::invalid_argument("Invalid argument for position when removing an element!");
    int i;
    TElem temporary = elements[position];
    for(i=position;i<numberOfElements;i++)
        elements[i] = elements[i+1];
    numberOfElements--;
    return temporary;
}

DAIterator DynamicArray::iterator() const
{
    //i must mention otherwise i will forget.
    // this returns pointer to current object and *this will give the dereferenced object which will be passed by & to function
    return DAIterator(*this);

}

//Complexity is worst O(n^2) theta(1)
bool DynamicArray::areUnique() const
{
    int i,j;
    for(i=0;i<numberOfElements-1;i++)
    {
        for(j=i+1;j<numberOfElements;j++)
        {
            if(elements[i] == elements[j])
            {
                return false;
            }
        }
    }
    return true;
}

/*pseudocode
 * subalgorithm areUnique(DynamicArray) is:
 *      for i<-0,DynamicArray.numberOfElements execute
 *          for j<-i+1,DynamicArray.numberOfElements execute
 *             if DynamicArray.elements[i] == DynamicArray.elements[j] then
 *                  areUnique<-false
 *           end-for
 *      end-for
 *      areUnique<-true
 * end-subalgorithm
 **/
