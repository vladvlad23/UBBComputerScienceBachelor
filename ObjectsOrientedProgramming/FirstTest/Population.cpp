//
// Created by Vlad on 10-Apr-19.
//

#include "Population.h"

const std::string &Population::getType() const
{
    return type;
}

void Population::setType(const std::string &type)
{
    Population::type = type;
}

int Population::getNumberOfOrganisms() const
{
    return numberOfOrganisms;
}

void Population::setNumberOfOrganisms(int numberOfOrganisms)
{
    Population::numberOfOrganisms = numberOfOrganisms;
}

bool Population::isImmune() const
{
    return immunity;
}

void Population::setIsImmune(bool isImmune)
{
    Population::immunity = isImmune;
}

Population::Population(std::string type, int numberOfOrganisms, bool isImmune)
{
    this->type = type;
    this->numberOfOrganisms = numberOfOrganisms;
    this->immunity = isImmune;
}
