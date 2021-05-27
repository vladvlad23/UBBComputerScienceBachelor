//
// Created by Vlad on 17-Apr-19.
//

#include "Comparator.h"

bool ComparatorAscendingByAge::compare(Dog firstDog, Dog secondDog)
{
    return firstDog.getAge()<secondDog.getAge();
}

bool ComparatorDescendingByBreed::compare(Dog firstDog, Dog secondDog)
{
    return firstDog.getBreed()>secondDog.getBreed();
}
