//
// Created by Vlad on 07-May-19.
//

#ifndef LABORATORY5_6_ADMINGUI_H
#define LABORATORY5_6_ADMINGUI_H

#include <QtWidgets/QMainWindow>
#include <Controller/DogListController.h>
#include <QtCore/QStringListModel>
#include "ui_adminwindow.h"

namespace Ui{
    class AdminGUI;
}

class AdminGUI : public QMainWindow
{
    Q_OBJECT
public:
    explicit AdminGUI(QWidget *parent=0,DogListController *controller=nullptr,DogListController *adoptionController = nullptr);
    virtual ~AdminGUI();

private:
    DogListController *controller,*adoptionController;
    Ui::AdminGUI *ui;
    QStringList list;
    QStringListModel *model;
    void readDogList();

private slots:
    void addButtonPressed();
    void updateButtonPressed();
    void removeButtonPressed();


};


#endif //LABORATORY5_6_ADMINGUI_H
