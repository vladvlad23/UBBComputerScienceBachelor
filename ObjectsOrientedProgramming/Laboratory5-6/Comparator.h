//
// Created by Vlad on 17-Apr-19.
//

#ifndef LABORATORY5_6_COMPARATOR_H
#define LABORATORY5_6_COMPARATOR_H


#include "Domain/Dog.h"

template <typename T>
class Comparator
{
public:
    virtual bool compare(T,T) = 0;
};

class ComparatorAscendingByAge : public Comparator<Dog>
{
    bool compare(Dog firstDog,Dog secondDog);

};


class ComparatorDescendingByBreed : public Comparator<Dog>
{
    bool compare(Dog firstDog, Dog secondDog);
};


#endif //LABORATORY5_6_COMPARATOR_H
