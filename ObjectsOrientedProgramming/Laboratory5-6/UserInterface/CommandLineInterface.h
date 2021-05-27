//
// Created by Vlad on 05-May-19.
//

#ifndef LABORATORY5_6_COMMANDLINEINTERFACE_H
#define LABORATORY5_6_COMMANDLINEINTERFACE_H

#include <cstring>
#include <iostream>
#include "Windows.h"
#include "shellapi.h"
#include "DogUserInterface.h"

class CommandLineInterface : public DogUserInterface
{

public:
    CommandLineInterface(DogListController *controller, DogListController *adoptionController) : DogUserInterface(controller,adoptionController) { }

private:

    void UIaddDog();
    void UIremoveDog();
    void UIupdateDog();
    void UIclientListDogs();
    int UIprintDogForAdoption(const Dog &);
    void UIprintDog(const Dog& dog);
    void UIclientFilterDogs();
    void UIseeAdoptionList();
    void UIadminListDogs();
    void startUserInterfaceWithClientRights();
    void startUserInterfaceWithAdminRights();
    void startPickingInterface(int argc, char **argv);
    void addToAdoptionList(const Dog &possibleAdopted);
};


#endif //LABORATORY5_6_COMMANDLINEINTERFACE_H
