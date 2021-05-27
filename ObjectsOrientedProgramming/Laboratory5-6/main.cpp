#include <iostream>
#include <UserInterface/ClientGUI.h>
#include <QtWidgets/QApplication>
#include <UserInterface/GraphicalUserInterface.h>
#include "./UserInterface/DogUserInterface.h"
#include "./Tests/tests.h"


int main(int argc, char **argv) {
    testAll();
    std::cout<<"The tests passed :)";
    std::vector<Dog> vector; // perhaps this should be in its own class and have its own stuff?!
    std::vector<Dog> adoptionListVector;
/*
    std::string CSVrepository;
    std::cout<<"Please tell me if you want to work csv or html. (Type \"text\" or \"html\")\n";
    std::getline(std::cin,CSVrepository);
*/
    Repository *repository;
    Repository *adoptionRepository;
    DogUserInterface *userInterface;

    bool gui = true,csv=true;


    if(csv)
    {
        repository = new CSVRepository(&vector,"input.csv");
        adoptionRepository = new CSVRepository(&adoptionListVector,"adoptionList.csv");
    }
    else
    {
        repository = new HTMLRepository(&vector,"input.html");
        adoptionRepository = new HTMLRepository(&adoptionListVector,"adoptionList.html");
    }
    DogListValidator validator(repository);
    DogListController controller(repository,validator);
    DogListValidator validator2(repository);
    DogListController controller2(adoptionRepository,validator2);


    if(gui)
    {
        userInterface = new GraphicalUserInterface(&controller,&controller2);
        userInterface->startPickingInterface(argc,argv);
    }
    else
    {
        userInterface = new CommandLineInterface(&controller,&controller2);
        userInterface->startPickingInterface(argc,argv);
    }
    delete repository;
    delete adoptionRepository;
    delete userInterface;

    return 0;
}