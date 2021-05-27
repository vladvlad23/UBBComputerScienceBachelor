//
// Created by Vlad on 04-May-19.
//
#include <iostream>
#include <QtWidgets/QFormLayout>
#include <QtWidgets/QDialog>
#include <QtWidgets/QInputDialog>
#include <QtCore/QDir>
#include "ui_clientwindow.h"
#include "ClientGUI.h"

ClientGUI::ClientGUI(QWidget *parent,DogListController *controller, DogListController *adoptionController) :
    QMainWindow(parent),
    ui(new Ui::ClientGUI)
{
    currentDog = -1;
    this->controller = controller;
    this->adoptionController = adoptionController;
    ui->setupUi(this);

    model = new QStringListModel(this);
    model->setStringList(list);
    ui->listView->setModel(model);

    connect(ui->nextButton,SIGNAL(clicked()),this,SLOT(nextButtonPressed()));
    connect(ui->filterButton,SIGNAL(clicked()),this,SLOT(filterButtonPressed()));
    connect(ui->adoptButton,SIGNAL(clicked()),this,SLOT(adoptButtonPressed()));
    connect(ui->showImageButton,SIGNAL(clicked()),this,SLOT(showImageButtonPressed()));

    readAdoptionList();

}



ClientGUI::~ClientGUI()
{
    delete ui;
}

void ClientGUI::nextButtonPressed()
{
    if(filterBreed == "")
    {
        currentDog++;
        if(currentDog >= controller->getNumberOfDogsController())
            currentDog=0;
    }
    else
    {
        if(!controller->existsBreed(filterBreed))
        {
            //should do something
        }
        currentDog++;
        if(currentDog >= controller->getNumberOfDogsController())
            currentDog=0;
        while((*controller)[currentDog].getBreed() != filterBreed || (*controller)[currentDog].getAge() > filterAge)
        {
            currentDog++;
            if (currentDog >= controller->getNumberOfDogsController())
            {
                currentDog = 0;
            }
        }
    }
    ui->Name->setText(QString::fromStdString("Name: " + (*controller)[currentDog].getName()));
    ui->Breed->setText(QString::fromStdString("Breed: " +(*controller)[currentDog].getBreed()));
    ui->Age->setText(QString::fromStdString("Age: " + std::to_string((*controller)[currentDog].getAge())));
}

void ClientGUI::filterButtonPressed()
{
    currentDog = 0;
    bool ok;
    QString text = QInputDialog::getText(this,tr("Please give the breed"),tr("Breed"),QLineEdit::Normal,"",&ok);
    if(ok && !text.isEmpty())
        filterBreed = text.toStdString();
    int age = QInputDialog::getInt(this,tr("Please give the age"),tr("Age"),0,0,100,1,&ok);
    if(ok)
    {
        filterAge = age;
    }

}

void ClientGUI::adoptButtonPressed()
{
    if(currentDog!=-1)
    {

        try
        {
            adoptionController->addDogController((*controller)[currentDog]);
        }
        catch(DogListException &exception)
        {

        }
        adoptionController->updateFile();
        readAdoptionList();

    }
}

void ClientGUI::readAdoptionList()
{
    list.clear();
    for(int i=0;i<adoptionController->getNumberOfDogsController();i++)
    {
        list << ("Name:" + (*adoptionController)[i].getName() + " "
        + "Breed:" + (*adoptionController)[i].getBreed() + " "
        + "Age:" + std::to_string((*adoptionController)[i].getAge())).c_str();

    }
    model->setStringList(list);

}

void ClientGUI::showImageButtonPressed()
{
    if(currentDog!=-1)
        ShellExecuteA(NULL, NULL, "chrome.exe", (*controller)[currentDog].getPhoto().c_str(), NULL, SW_SHOWMAXIMIZED);
}
