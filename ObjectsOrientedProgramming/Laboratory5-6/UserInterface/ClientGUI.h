//
// Created by Vlad on 04-May-19.
//

#ifndef LABORATORY5_6_CLIENTGUI_H
#define LABORATORY5_6_CLIENTGUI_H

#include <QtWidgets/QMainWindow>
#include <QtCore/QStringListModel>
#include "CommandLineInterface.h"

namespace Ui{
    class ClientGUI;
}

class ClientGUI : public QMainWindow
{
    Q_OBJECT
public:
    explicit ClientGUI(QWidget *parent = nullptr,DogListController *controller=nullptr, DogListController *adoptionController=nullptr);
    virtual ~ClientGUI();

private:
    DogListController *controller,*adoptionController;
    QStringList list;
    QStringListModel *model;
    Ui::ClientGUI *ui;
    int currentDog,filterAge;
    std::string filterBreed;
    void readAdoptionList();


private slots:
    void nextButtonPressed();
    void filterButtonPressed();
    void adoptButtonPressed();
    void showImageButtonPressed();

};


#endif //LABORATORY5_6_CLIENTGUI_H
