//
// Created by Vlad on 24-Mar-19.
//

#ifndef LABORATORY5_6_DOGUSERINTERFACE_H
#define LABORATORY5_6_DOGUSERINTERFACE_H

#include "../Controller/DogListController.h"
#include "../Repository/Repository.h"

class DogUserInterface
{
protected:
    DogListController *adoptionListController;
    DogListController *controller;

public:
    DogUserInterface(
            DogListController *controller,
            DogListController *adoptionListController) :
    controller(controller),
    adoptionListController(adoptionListController)
    {
        controller->readFromFile();
        adoptionListController->readFromFile();
    }

    virtual void startUserInterfaceWithClientRights() = 0;
    virtual void startUserInterfaceWithAdminRights() = 0;
    virtual void startPickingInterface(int argc,char **argv) = 0;

};


#endif //LABORATORY5_6_DOGUSERINTERFACE_H
