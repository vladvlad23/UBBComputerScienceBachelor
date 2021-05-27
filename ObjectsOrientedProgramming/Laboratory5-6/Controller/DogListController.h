//
// Created by Vlad on 24-Mar-19.
//

#ifndef LABORATORY5_6_DOGLISTCONTROLLER_H
#define LABORATORY5_6_DOGLISTCONTROLLER_H

#include "../Repository/DynamicVector.h"
#include <vector>
#include "../Repository/DogListValidator.h"
#include "../Domain/Dog.h"
#include "../Comparator.h"
#include <algorithm>
#include <Repository/Repository.h>

class DogListController
{
private:
    Repository *repository;
    DogListValidator validator;
public:
    void readFromFile();

    void updateFile();

    bool isHtml();


    void addDogController(std::string name, std::string breed, std::string photo, int age);

    void addDogController(const Dog &dog);

    bool existsBreed(std::string breed);

    void removeDogController(int index);

    void changeDogNameController(std::string oldName, std::string newName);

    void changeDogAgeController(std::string name, int age);

    void changeDogBreedController(std::string name, std::string newBreed);

    void changeDogPhotoController(std::string name, std::string newPhoto);

    friend DogListController operator+(DogListController &list,const Dog &newDog);
    friend DogListController operator+(const Dog &newDog, DogListController &list);

    DogListController operator=(const DogListController &oldList);

    void addStartingDogs();

    int getIndexByName(std::string);
    int getIndexByPhoto(std::string photo);

    void sortVectorBy(Comparator<Dog> *comparator);

    int getNumberOfDogsController();

    Dog operator [](int index) const;

    DogListController(Repository *repository,const DogListValidator& validator) : repository(repository),validator(validator){}
    ~DogListController();
};





#endif //LABORATORY5_6_DOGLISTCONTROLLER_H
