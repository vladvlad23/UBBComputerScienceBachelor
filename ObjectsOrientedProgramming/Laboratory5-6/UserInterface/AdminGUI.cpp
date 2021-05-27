//
// Created by Vlad on 07-May-19.
//

#include <QtWidgets/QInputDialog>
#include "AdminGUI.h"

AdminGUI::AdminGUI(QWidget *parent, DogListController *controller, DogListController *adoptionController) :
    QMainWindow(parent),
    controller(controller),
    adoptionController(adoptionController),
    ui(new Ui::AdminGUI)
{
    ui->setupUi(this);

    model = new QStringListModel(this);
    model->setStringList(list);
    ui->listView->setModel(model);

    readDogList();

    connect(ui->addButton,SIGNAL(clicked()),this,SLOT(addButtonPressed()));
    connect(ui->removeButton,SIGNAL(clicked()),this,SLOT(removeButtonPressed()));
    connect(ui->updateButton,SIGNAL(clicked()),this,SLOT(updateButtonPressed()));


}

void AdminGUI::readDogList()
{
    list.clear();
    for(int i=0;i<controller->getNumberOfDogsController();i++)
    {
        list << ("Id: " + std::to_string(i) + " Name:" + (*controller)[i].getName() + " "
                 + "Breed:" + (*controller)[i].getBreed() + " "
                 + "Age:" + std::to_string((*controller)[i].getAge()) + " "
                 + "Photo: " + (*controller)[i].getPhoto()).c_str();


    }
    model->setStringList(list);

}

void AdminGUI::addButtonPressed()
{
    std::string newDogName = ui->nameInput->text().toStdString();
    std::string newDogBreed = ui->breedInput->text().toStdString();
    std::string newDogLink = ui->linkInput->text().toStdString();
    int newDogAge = ui->ageInput->value();

    try
    {
        controller->addDogController(newDogName, newDogBreed, newDogLink, newDogAge);
        controller->updateFile();
        readDogList();
    }
    catch (DogListException &exception)
    {

    }
    ui->nameInput->clear();
    ui->breedInput->clear();
    ui->linkInput->clear();
    ui->ageInput->clear();
}

void AdminGUI::updateButtonPressed()
{
    bool ok;
    std::string name;
    QString text = QInputDialog::getText(this,tr("Please give the name of the dog to be modified"),tr("Name"),QLineEdit::Normal,"",&ok);
    if(ok && !text.isEmpty())
        name = text.toStdString();

    QStringList items;
    items<<tr("Name")<<tr("Breed")<<tr("Link")<<tr("Age");


    QString item = QInputDialog::getItem(this,tr("What would you like to modify?"),tr("Choose"),items,0,false,&ok);
    try
    {
        if (ok && !item.isEmpty())
        {
            if (item.toStdString() != "Age")
            {
                text = QInputDialog::getText(this, tr(std::string(
                        "Please give the " + item.toStdString() + " of the dog to be modified").c_str()),
                                             tr(item.toStdString().c_str()), QLineEdit::Normal, "", &ok);
                if (ok && !text.isEmpty())
                {
                    if (item.toStdString() == "Name")
                        controller->changeDogNameController(name, text.toStdString());
                    else if (item.toStdString() == "Breed")
                        controller->changeDogBreedController(name, text.toStdString());
                    else if (item.toStdString() == "Link")
                        controller->changeDogPhotoController(name, text.toStdString());
                }
            } else
            {
                int newAge = QInputDialog::getInt(this, tr("Please give new age"),
                                                  tr("NewAge:"), 25, 0, 100, 1, &ok);
                if (ok)
                    controller->changeDogAgeController(name, newAge);
            }
        }
    }
    catch(const std::exception &ex)
    {

    }
    controller->updateFile();
    readDogList();

}

void AdminGUI::removeButtonPressed()
{
    bool ok;
    int age = QInputDialog::getInt(this,tr("Please give the id"),tr("Id"),0,0,100,1,&ok);
    if(ok)
    {
        try
        {
            controller->removeDogController(age);
            controller->updateFile();
            readDogList();
        }
        catch(DogListException &exception)
        {

        }
    }


}

AdminGUI::~AdminGUI()
{

}
