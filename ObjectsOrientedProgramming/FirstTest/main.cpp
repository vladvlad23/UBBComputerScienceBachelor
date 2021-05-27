#include <iostream>
#include "Repository.h"
#include "Controller.h"
#include "UserInterface.h"
#include "Tests.h"
int main()
{
    Tests testing;
    testing.testAll();
    Repository repo;
    Controller controller(repo);
    controller.addElement("E_coli",100,false);
    controller.addElement("Mycobacterium_tuberculosis",150,true);
    controller.addElement("Streptococus pneumonia",200,false);
    controller.addElement("Chiulangitus Cronicus",500,true);
    controller.addElement("Excessus Somnolens",350,false);
    UserInterface interface(controller);

    interface.startConsole();

}