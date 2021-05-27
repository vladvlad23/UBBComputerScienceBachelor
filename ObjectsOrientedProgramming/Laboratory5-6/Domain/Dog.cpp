//
// Created by Vlad on 24-Mar-19.
//

#include "Dog.h"

const std::string &Dog::getBreed() const
{
    return breed;
}

void Dog::setBreed(const std::string &breed)
{
    Dog::breed = breed;
}

const std::string &Dog::getName() const
{
    return name;
}

void Dog::setName(const std::string &name)
{
    Dog::name = name;
}

int Dog::getAge() const
{
    return age;
}

void Dog::setAge(int age)
{
    Dog::age = age;
}

const std::string &Dog::getPhoto() const
{
    return photo;
}

void Dog::setPhoto(const std::string &photo)
{
    Dog::photo = photo;
}

Dog::Dog(std::string name, std::string breed, std::string photo, int age)
{
    this->name = name;
    this->breed = breed;
    this->age = age;
    this->photo = photo;
}


Dog::~Dog()
{

}

Dog::Dog()
{

}

bool Dog::operator==(const Dog &dogToCompareWith)
{
    return (this->getBreed() == dogToCompareWith.getBreed() &&
            this->getName() == dogToCompareWith.getName() &&
            this->getAge() == dogToCompareWith.getAge() &&
            this->getPhoto() == dogToCompareWith.getPhoto());
}

Dog Dog::operator=(const Dog &newDog)
{
    this->name = newDog.name;
    this->age = newDog.age;
    this->photo = newDog.photo;
    this->breed = newDog.breed;
    return *this;

}


std::istream &operator>>(std::istream &f, Dog &dog)
{
    std::string lineToBeParsed;
    if (std::getline(f, lineToBeParsed))
    {
        std::string tempString;
        std::vector<std::string> resultedParameters;
        std::istringstream stream(lineToBeParsed);
        while (std::getline(stream, tempString, ','))
        {
            resultedParameters.push_back(tempString);
        }
        dog.setName(resultedParameters[0]);
        dog.setBreed(resultedParameters[1]);
        dog.setPhoto(resultedParameters[2]);
        dog.setAge(std::stoi(resultedParameters[3]));
        return f;
    }
    return f;
}

std::ostream &operator<<(std::ostream &out,Dog &dog)
{
    out<<dog.getName()<<","<<dog.getBreed()<<","<<dog.getPhoto()<<","<<std::to_string(dog.getAge())<<"\n";
    return out;
}
