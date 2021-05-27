//
// Created by Vlad on 10-Apr-19.
//

#include <assert.h>
#include "Tests.h"

void Tests::testAddition()
{
    Repository repo;
    Controller controller(repo);
    controller.addElement("E_coli",100,false);
    controller.addElement("Mycobacterium_tuberculosis",150,true);
    controller.addElement("Streptococus pneumonia",200,false);
    controller.addElement("Chiulangitus Cronicus",500,true);
    controller.addElement("Excessus Somnolens",350,false);

    assert(controller.getElement(0).getType()=="E_coli");
    assert(controller.getElement(1).getType()=="Mycobacterium_tuberculosis");
    assert(controller.getElement(2).getType()=="Streptococus pneumonia");
    assert(controller.getElement(3).getType()=="Chiulangitus Cronicus");
    assert(controller.getElement(4).getType()=="Excessus Somnolens");

}

void Tests::testSugarEnvironment()
{
    Repository repo;
    Controller controller(repo);
    controller.addElement("E_coli",100,false);
    controller.addElement("Mycobacterium_tuberculosis",150,true);
    controller.addElement("Streptococus pneumonia",200,false);
    controller.addElement("Chiulangitus Cronicus",500,true);
    controller.addElement("Excessus Somnolens",350,false);

    controller.simulateDay("sugar");
    std::vector<Population> population = controller.getPopulationsBySortedType();
    for(auto const &element:population)
    {
        if(element.getType() == "E_coli")
            assert(element.getNumberOfOrganisms() == 200);
        if(element.getType() == "Mycobacterium_tuberculosis")
            assert(element.getNumberOfOrganisms() == 300);
        if(element.getType() == "Streptococus pneumonia")
            assert(element.getNumberOfOrganisms() == 400);
        if(element.getType() == "Chiulangitus Cronicus")
            assert(element.getNumberOfOrganisms() == 1000);
        if(element.getType() == "Excessus Somnolens")
            assert(element.getNumberOfOrganisms() == 700);
    }

}

void Tests::testAntibioticEnvironment()
{
    Repository repo;
    Controller controller(repo);
    controller.addElement("E_coli",100,false);
    controller.addElement("Mycobacterium_tuberculosis",150,true);
    controller.addElement("Streptococus pneumonia",200,false);
    controller.addElement("Chiulangitus Cronicus",500,true);
    controller.addElement("Excessus Somnolens",350,false);

    controller.simulateDay("antibiotic");
    std::vector<Population> population = controller.getPopulationsBySortedType();
    for(auto const &element:population)
    {
        if(element.getType() == "E_coli")
            assert(element.getNumberOfOrganisms() == 50);
        if(element.getType() == "Mycobacterium_tuberculosis")
            assert(element.getNumberOfOrganisms() == 150);
        if(element.getType() == "Streptococus pneumonia")
            assert(element.getNumberOfOrganisms() == 100);
        if(element.getType() == "Chiulangitus Cronicus")
            assert(element.getNumberOfOrganisms() == 500);
        if(element.getType() == "Excessus Somnolens")
            assert(element.getNumberOfOrganisms() == 175);
    }

}

void Tests::testAll()
{
    testAddition();
    testAntibioticEnvironment();
    testSugarEnvironment();

}
