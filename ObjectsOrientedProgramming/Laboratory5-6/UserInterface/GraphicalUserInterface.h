//
// Created by Vlad on 05-May-19.
//

#ifndef LABORATORY5_6_GRAPHICALUSERINTERFACE_H
#define LABORATORY5_6_GRAPHICALUSERINTERFACE_H

#include <QApplication>
#include <QtWidgets/QMainWindow>
#include <QPushButton>
#include "DogUserInterface.h"
#include "ClientGUI.h"
#include "AdminGUI.h"

class GraphicalUserInterface : public DogUserInterface
{
private:
    int argc;
    char **argv;
    bool user;
    ClientGUI *clientGui;
    AdminGUI *adminGui;
public:
    GraphicalUserInterface(DogListController *controller,DogListController *adoptionListController);
    ~GraphicalUserInterface() { delete clientGui; }
    void startPickingInterface(int argc,char **argv) override;
    void startUserInterfaceWithClientRights() override;
    void startUserInterfaceWithAdminRights() override;

};


#endif //LABORATORY5_6_GRAPHICALUSERINTERFACE_H
