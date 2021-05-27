//
// Created by Vlad on 10-Apr-19.
//

#include <iostream>
#include "UserInterface.h"

void UserInterface::UIaddPopulation()
{
    std::string type;
    int numberOfOrganisms;
    int isImmuneInput;
    bool isImmune;
    std::cout<<"Please give the type of bacteria: ";
    std::getline(std::cin,type);

    std::cout<<"Please give the number of organisms: ";
    std::cin>>numberOfOrganisms;

    std::cout<<"Please tell me if the bacteria is immune(1 for true and 0 for false): ";
    std::cin>>isImmuneInput;
    std::cin.get();
    if(isImmuneInput==1)
    {
        isImmune = true;
    }
    else
        isImmune = false;

    controller.addElement(type,numberOfOrganisms,isImmune);


}

void UserInterface::UIListPopulations()
{
    std::vector<Population> result = controller.getPopulationsBySortedType();
    for(auto const &element:result)
    {
        UIprintPopulation(element);
    }
}

void UserInterface::UIsimulateDay()
{
    std::string dayType;
    std::cout<<"Please give day type";
    std::getline(std::cin,dayType);
    controller.simulateDay(dayType);
}

void UserInterface::startConsole()
{
    int command;
    std::cout<<"Welcome to the bacteria simulator \n";
    std::cout<<"Commands:\n1.Add population\n2.Show populations\n3.Simulate passing of day\n";
    while(true)
    {
        std::cin >> command;
        std::cin.get();
        if (command == 1)
        {
            UIaddPopulation();
        } else if (command == 2)
        {
            UIListPopulations();
        } else if (command == 3)
        {
            UIsimulateDay();
        } else if (command == 0)
        {
            return;
        } else
            std::cout << "Invalid command. Please try again\n";

    }
}

void UserInterface::UIprintPopulation(Population populationToBePrinted)
{
    std::cout<<"The type is "<<populationToBePrinted.getType()<<"\n";
    std::cout<<"The number of organisms is "<<populationToBePrinted.getNumberOfOrganisms()<<"\n";
    if(populationToBePrinted.isImmune())
        std::cout<<"The population is immune\n";
    else
        std::cout<<"The population is not immune\n";
    std::cout<<"\n\n";


}
