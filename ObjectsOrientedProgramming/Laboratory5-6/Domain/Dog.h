//
// Created by Vlad on 24-Mar-19.
//

#ifndef LABORATORY5_6_DOG_H
#define LABORATORY5_6_DOG_H


#include <string>
#include <sstream>
#include <vector>

class Dog
{
    friend std::istream& operator>>(std::istream &is, Dog &dog);
    friend std::ostream& operator<<(std::ostream &f,Dog &dog);
public:
    const std::string &getBreed() const;

    void setBreed(const std::string &breed);

    const std::string &getName() const;

    void setName(const std::string &name);

    bool operator==(const Dog&); // NOT NECESSARY PER SE YET

    Dog operator=(const Dog& newDog);

    int getAge() const;

    void setAge(int age);

    const std::string &getPhoto() const;

    void setPhoto(const std::string &photo);

    Dog();
    Dog(const Dog& dog) { this->breed = dog.breed, this->name = dog.name; this->age = dog.age; this-> photo = dog.photo;}
    Dog(std::string,std::string,std::string,int);
    ~Dog();


private:
    std::string breed;
    std::string name;
    int age;
    std::string photo;


};

#endif //LABORATORY5_6_DOG_H
