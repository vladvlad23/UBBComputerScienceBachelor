//
// Created by Vlad on 10-Apr-19.
//

#ifndef FIRSTTEST_CONTROLLER_H
#define FIRSTTEST_CONTROLLER_H


#include "Repository.h"

class Controller
{
public:
    Controller(const Repository &repo);

private:
    Repository repo;
public:
    /*
     * Function will get the parameters from the user interface, will create a population and will call the repo function to add it to the
     * dynamic vector
     */
    void addElement(std::string type,int numberOfOrganisms, bool isImmune);


    std::vector<Population> getPopulationsBySortedType();


    /*
     * Function will get as parameter from the user interface the simulation type and it will call the repo function accordingly
     */
    void simulateDay(std::string typeOfDay);
    Population getElement(int position);

};


#endif //FIRSTTEST_CONTROLLER_H
