//
// Created by Vlad on 05-Apr-19.
//
#include <sstream>
#include "Repository.h"

void Repository::add(std::string name, std::string breed, std::string photo, int age)
{
    dynamicVector->push_back(Dog(name,breed,photo,age));
}

void Repository::remove(int index)
{
    dynamicVector->erase(dynamicVector->begin()+index);
}

void Repository::add(const Dog &newDog)
{
    dynamicVector->push_back(newDog);
}

Dog Repository::getDogByIndex(int index) const
{
    return (*dynamicVector)[index];
}

int Repository::numberOfDogs() const
{
    return dynamicVector->size();
}

void Repository::sortVectorBy(Comparator<Dog> *comparator)
{
    std::sort(dynamicVector->begin(),dynamicVector->end(),
            [comparator] (Dog first, Dog second) { return comparator->compare(first,second); } );

}

Dog Repository::operator[](int index)
{
    return (*dynamicVector)[index];
}

bool Repository::isHtml()
{
    return fileName=="adoptionList.html" || fileName == "input.html";
}



