//
// Created by Vlad on 10-Apr-19.
//

#ifndef FIRSTTEST_USERINTERFACE_H
#define FIRSTTEST_USERINTERFACE_H
#include "Controller.h"
#include "UserInterface.h"
#include <iostream>
class UserInterface
{
private:
    Controller controller;

    void UIaddPopulation();
    void UIListPopulations();
    void UIsimulateDay();
    void UIprintPopulation(Population);
public:
    void startConsole();
    UserInterface(const Controller &controller) : controller(controller) {}
};


#endif //FIRSTTEST_USERINTERFACE_H
