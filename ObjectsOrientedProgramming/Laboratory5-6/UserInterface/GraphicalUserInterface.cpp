//
// Created by Vlad on 05-May-19.
//

#include <QtWidgets/QLabel>
#include <QtWidgets/QPushButton>
#include <QtWidgets/QtWidgets>
#include <QtDebug>
#include "GraphicalUserInterface.h"
void GraphicalUserInterface::startUserInterfaceWithClientRights()
{
    clientGui = new ClientGUI(nullptr,controller,adoptionListController);
    clientGui->show();
}

void GraphicalUserInterface::startUserInterfaceWithAdminRights()
{
    adminGui = new AdminGUI(nullptr,controller,adoptionListController);
    adminGui->show();

}

void GraphicalUserInterface::startPickingInterface(int argc, char **argv)
{
    this->argc = argc;
    this->argv = argv;
    QApplication app(argc,argv);
    QMessageBox boxMessage;
    boxMessage.setWindowTitle("Interface pick");
    boxMessage.setText("Are you a user?");
    boxMessage.setStandardButtons(QMessageBox::Yes);
    boxMessage.addButton(QMessageBox::No);
    if(boxMessage.exec() == QMessageBox::Yes)
    {
        startUserInterfaceWithClientRights();
    }
    else
    {
        startUserInterfaceWithAdminRights();
    }
    app.exec();

    qDebug() << "Bye";
}

GraphicalUserInterface::GraphicalUserInterface(DogListController *controller, DogListController *adoptionListController)
        : DogUserInterface(controller, adoptionListController)

{

}


