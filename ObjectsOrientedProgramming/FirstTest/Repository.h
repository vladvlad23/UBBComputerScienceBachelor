//
// Created by Vlad on 10-Apr-19.
//

#ifndef FIRSTTEST_REPOSITORY_H
#define FIRSTTEST_REPOSITORY_H
#include <vector>
#include "Population.h"
#include "algorithm"
class Repository
{
private:
    std::vector<Population> elements;
public:
    /*
     * Function adds a new population. It will get an element of type population and will add it to the dynamic vector
     */
    void addElement(Population);
    Population getElement(int position);

    //function will double all the populations by iterating through the dynamic array and changing them
    void doublePopulation();

    //Function will half all the populations which are not immune
    //It will iterate through the dynamic vector and change things accordingly
    void attackWithAntibiotics();

    void increaseType(int position,int numberOfOrganisms);


    std::vector<Population> getAllOfCertainTypeSorted();

};


#endif //FIRSTTEST_REPOSITORY_H
