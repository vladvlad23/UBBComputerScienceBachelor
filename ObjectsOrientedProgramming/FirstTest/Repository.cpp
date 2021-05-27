//
// Created by Vlad on 10-Apr-19.
//

#include "Repository.h"

bool comparePopulations(Population one,Population two) { return one.getType() < two.getType();}

void Repository::addElement(Population newPopulation)
{
    elements.push_back(newPopulation);
}

Population Repository::getElement(int position)
{
    return elements[position];
}

void Repository::doublePopulation()
{
    int i;
    for(i=0;i<elements.size();i++)
    {
        elements[i].setNumberOfOrganisms(elements[i].getNumberOfOrganisms()*2);
    }
}

void Repository::attackWithAntibiotics()
{
    int i;
    for(i=0;i<elements.size();i++)
    {
        if(!elements[i].isImmune())
        {
            elements[i].setNumberOfOrganisms(elements[i].getNumberOfOrganisms()/2);
        }
    }
}

std::vector<Population> Repository::getAllOfCertainTypeSorted()
{
    std::vector<Population> result = elements;
    std::sort(result.begin(),result.end(),comparePopulations);
    return result;
}

void Repository::increaseType(int position, int numberOfOrganisms)
{
    elements[position].setNumberOfOrganisms(elements[position].getNumberOfOrganisms()+numberOfOrganisms);
}


