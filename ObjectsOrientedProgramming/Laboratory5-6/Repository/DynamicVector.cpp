//
// Created by Vlad on 24-Mar-19.
//
/*
#include <stdexcept>
#include "DynamicVector.h"
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
template <typename T>
void DynamicVector<T>::changeDogName(int index, std::string newName)
{
    elements[index].setName(newName);
}

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
template <typename T>
int DynamicVector<T>::getIndexByName(std::string name)
{
    int i;
    for(i=0;i<numberOfElements;i++)
    {
        if(elements[i].getName() == name)
            return i;
    }
    return -1;
}
template <typename T>
int DynamicVector<T>::getIndexByPhoto(std::string photo)
{
    int i;
    if(photo == "Dead link")
        return -2;
    for(i=0;i<numberOfElements;i++)
    {
        if(elements[i].getPhoto() == photo)
            return i;
    }
    return -1;
}
template <typename T>
void DynamicVector<T>::changeDogAge(int index, int newAge)
{
    elements[index].setAge(newAge);
}
template <typename T>
void DynamicVector<T>::changeDogBreed(int index, std::string newBreed)
{
    elements[index].setBreed(newBreed);
}
template <typename T>
void DynamicVector<T>::changeDogPhoto(int index, std::string newPhoto)
{
    elements[index].setPhoto(newPhoto);

}
template <typename T>
void DynamicVector<T>::addStartingDogs()
{
    addElement(Dog("Mr. Woofenstein", "Bichon",
                   "https://upload.wikimedia.org/wikipedia/commons/thumb/f/fa/AG_Cody.jpg/250px-AG_Cody.jpg", 1));
    addElement(Dog("Miss CutiePaws", "Collie",
                   "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRO7c9xGedXWe7IRLCTNLmUYXl_7MHWAC50MyYKpGcDR0gk0YEd",
                   5));
    addElement(Dog("Artoo Dogtoo", "Golden Retriever",
                   "https://cdn2-www.dogtime.com/assets/uploads/gallery/golden-retriever-dogs-and-puppies/golden-retriever-dogs-puppies-10.jpg",
                   2));
    addElement(Dog("Winnie The Poodle", "Poodle",
                   "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS6eMRuYURWy1_aVqis8cpBmZ5Bt8Cveg1F9IyWTyANRJdJxmDf",
                   2));
    addElement(Dog("Ozzy Pawsborne", "Mastiff",
                   "https://d17fnq9dkz9hgj.cloudfront.net/breed-uploads/2018/08/mastiff-detail.jpg?bust=1535566122&width=355",
                   3));
}

*/