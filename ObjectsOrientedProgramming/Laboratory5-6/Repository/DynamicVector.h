//
// Created by Vlad on 24-Mar-19.
//

#ifndef LABORATORY5_6_DYNAMICVECTOR_H
#define LABORATORY5_6_DYNAMICVECTOR_H

#include "../Domain/Dog.h"
#include <stdexcept>
template <typename T>
class DynamicVector
{
private:
    int numberOfElements;
    int size;
    T *elements;

public:
    /*
     * Function will add an element to the elements array
     * param: const T & - the element to be added
     */
    void addElement(const T &);

    /*
     * Function will remove an element by index from the elements array
     * param:int - the index to be removed
     */
    void removeElement(int);


    //Function will get the number of elements in the element array
    int getNumberOfElements();

    T &operator[](int);

    DynamicVector();

    DynamicVector(int);

    ~DynamicVector();

private:
    //Function will double the memory size allocated to the array
    void doubleListSize();

};

template <typename T>
void DynamicVector<T>::doubleListSize()
{
    size *= 2;
    Dog *newElements = new Dog[size];
    for (int i = 0; i < numberOfElements; i++)
    {
        newElements[i] = elements[i];
    }
    delete[] elements;
    elements = newElements;
}

template <typename T>
void DynamicVector<T>::addElement(const T &newElement)
{
    if (numberOfElements + 1 == size)
    {
        doubleListSize();
        elements[numberOfElements] = newElement;
        numberOfElements++;
    } else
    {
        elements[numberOfElements] = newElement;
        numberOfElements++;
    }
}
template <typename T>
void DynamicVector<T>::removeElement(int index)
{
    int i;
    for(i=index;i<numberOfElements-1;i++)
    {
        elements[i] = elements[i+1];
    }
    numberOfElements--;

}
/*
template <typename T>
void DynamicVector<T>::changeDogName(int index, std::string newName)
{
    elements[index].setName(newName);
}*/

template <typename T>
int DynamicVector<T>::getNumberOfElements()
{
    return numberOfElements;
}

template <typename T>
T &DynamicVector<T>::operator[](int index)
{
    if (index < 0 || index >= getNumberOfElements())
    {
        throw std::invalid_argument("Invalid arguments when trying to \"fetch\" a dog");
    }
    return elements[index];
}
template <typename T>
DynamicVector<T>::DynamicVector()
{
    size = 5;
    numberOfElements = 0;
    elements = new Dog[5];
}
template <typename T>
DynamicVector<T>::DynamicVector(int size)
{
    size = size;
    numberOfElements = 0;
    elements = new Dog[size];
}
template <typename T>
DynamicVector<T>::~DynamicVector()
{
    delete []elements;

}



#endif //LABORATORY5_6_DYNAMICVECTOR_H
