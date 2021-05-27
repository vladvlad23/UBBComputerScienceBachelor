//
// Created by Vlad on 24-Mar-19.
//

#include <stdexcept>
#include "DogListValidator.h"

void DogListValidator::validateIndex(int index) const
{
    if(repository->numberOfDogs() == 0)
    {
        throw DogListException("There is no dog in the list. Maybe they have all been adopted and are in a better place!\n");
    }
    if(index<0 || index > repository->numberOfDogs()-1)
    {
        throw DogListException("Index of dog invalid");
    }

}

void DogListValidator::validateDog(std::string name, std::string breed, std::string photo, int age) const
{
    int i;
    for(i=0;i<repository->numberOfDogs();i++) //can't use other loop cause i need the index
    {
        if(repository->getDogByIndex(i).getName() == name)
            throw DogListException("You are trying to add a dog that is already there(same name)");
    }
    for(i=0;i<repository->numberOfDogs();i++) //can't use other loop cause i need the index
    {
        if(repository->getDogByIndex(i).getPhoto() == photo && photo!="Dead link")
            throw DogListException("You are trying to add a dog that is already there(same picture)");
    }
}

