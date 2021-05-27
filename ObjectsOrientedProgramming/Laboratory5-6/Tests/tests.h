//
// Created by Vlad on 24-Mar-19.
//

#ifndef LABORATORY5_6_TESTS_H
#define LABORATORY5_6_TESTS_H


#include "../Controller/DogListController.h"
#include "../Repository/DogListValidator.h"
#include "../Repository/DynamicVector.h"
#include "../Comparator.h"
void testAll();
void testDogCreation();
void testRemovalAndModification(); //all in one because i don't want to initialize the same dogs multiple times. it's futile
void testInLabAssignment();
void testCSVAndHTML();
#endif //LABORATORY5_6_TESTS_H
