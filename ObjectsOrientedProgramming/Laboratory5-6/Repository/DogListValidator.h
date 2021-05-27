//
// Created by Vlad on 24-Mar-19.
//

#ifndef LABORATORY5_6_DOGVALIDATOR_H
#define LABORATORY5_6_DOGVALIDATOR_H


#include "DynamicVector.h"
#include "Repository.h"
#include <vector>
#include <exception>

class DogListValidator
{
private:
    Repository *repository;
public:

    /*
     * Function will check if an index is valid in the repository. If it is, it won't do anything. Otherwise, it will throw an error
        param:index - int - index to be validated
     */

    void validateIndex(int index) const;

    /*
     * Function will check if the given parameters can form a dog, if the dog already exists and if anything fails, it throws an error
     */
    void validateDog(std::string name, std::string breed, std::string photo, int age) const;
    DogListValidator(Repository *repository) : repository(repository) {}

};

class DogListException : public std::exception
{
private:
    std::string message;

public:
    DogListException(std::string message) : message(message){}

    virtual const char * what() const throw()
    {
        return message.c_str();
    }
};


#endif //LABORATORY5_6_DOGVALIDATOR_H
