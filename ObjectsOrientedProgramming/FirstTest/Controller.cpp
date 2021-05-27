//
// Created by Vlad on 10-Apr-19.
//

#include "Controller.h"

Controller::Controller(const Repository &repo) : repo(repo)
{}

void Controller::addElement(std::string type, int numberOfOrganisms, bool isImmune)
{
    Population newPopulation(type,numberOfOrganisms,isImmune);

    std::vector<Population> result = repo.getAllOfCertainTypeSorted();
    int i;
    for(i=0;i<result.size();i++)
    {
        if(result[i].getType() == type)
        {
            repo.increaseType(i, numberOfOrganisms);
            return;
        }
    }

    repo.addElement(newPopulation);

}

void Controller::simulateDay(std::string typeOfDay)
{
    if(typeOfDay == "sugar")
    {
        repo.doublePopulation();
    }
    else if(typeOfDay == "antibiotic")
    {
        repo.attackWithAntibiotics();
    }
}

std::vector<Population> Controller::getPopulationsBySortedType()
{
    return repo.getAllOfCertainTypeSorted();

}

Population Controller::getElement(int position)
{
    return repo.getElement(position);

}


