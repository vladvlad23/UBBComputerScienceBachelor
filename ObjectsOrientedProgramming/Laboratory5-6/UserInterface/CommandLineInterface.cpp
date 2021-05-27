//
// Created by Vlad on 05-May-19.
//

#include "CommandLineInterface.h"

int CommandLineInterface::UIprintDogForAdoption(const Dog &dog)
{
    UIprintDog(dog);

    ShellExecuteA(NULL, NULL, "chrome.exe", dog.getPhoto().c_str(), NULL, SW_SHOWMAXIMIZED);

    std::cout<<"Press 1 to add to adoption list and 2 to see next one and other number to exit\n";
    int command;
    while (true)
    {
        std::cin >> command;
        if (std::cin.fail())
        {
            std::cin.clear();
            std::cin.ignore(10000, '\n');
            std::cout << "A number please";
            std::cin >> command;
            if (!std::cin.fail())
                break;
        } else
            break;
    }
    if(command == 1 || command == 2)
        return command;
    return -1;
}
void CommandLineInterface::UIclientListDogs()
{
    int i;
    for(i=0;i<controller->getNumberOfDogsController();i++)
    {
        int userWish = UIprintDogForAdoption((*controller)[i]);
        if(userWish == 1)
            addToAdoptionList((*controller)[i]);
        else if(userWish == -1)
            break;
    }
    std::cout<<"So that's it for now\n";
}

void CommandLineInterface::UIclientFilterDogs()
{
    std::string breed;
    std::cout<<"Please give the breed: ";
    std::getline(std::cin,breed);
    if(breed == "")
        UIclientListDogs();
    int age;
    std::cout<<"Please give age: ";
    while(true)
    {
        std::cin>>age;
        if(std::cin.fail())
        {
            std::cin.clear();
            std::cin.ignore(10000, '\n');
            std::cout<<"A number please";
            std::cin>>age;
            if(!std::cin.fail())
                break;
        }
        else
            break;
    }

    for(int i=0;i<controller->getNumberOfDogsController();i++)
    {

        if((*controller)[i].getBreed() == breed && (*controller)[i].getAge()<age)
        {
            /*
             * If function is unsatisfacatory, just uncomment this and comment next
             * UIprintDog((*controller)[i]);
             */
            int userWish = UIprintDogForAdoption((*controller)[i]);
            if(userWish == 1)
                addToAdoptionList((*controller)[i]);
            else if(userWish == -1)
                break;
        }
    }
    std::cout<<"So that's it for now\n";
}



void CommandLineInterface::UIseeAdoptionList()
{
    /*
    int i;
    std::cout<<"These are the dogs in your adoptions list:\n";
    for(i=0;i<adoptionListController->getNumberOfDogsController();i++)
    {
        UIprintDog((*controller)[i]);
    }*/
    if(controller->isHtml() == true)
        ShellExecuteA(NULL, NULL, "E:\\College\\First Year\\Second Semester\\OOP-Homework\\Laboratory5-6\\cmake-build-debug\\adoptionList.html", NULL, NULL, SW_SHOWMAXIMIZED);
    else
        ShellExecuteA(NULL,NULL, "E:\\College\\First Year\\Second Semester\\OOP-Homework\\Laboratory5-6\\cmake-build-debug\\adoptionList.csv",NULL,NULL,SW_SHOWMAXIMIZED);
}

void CommandLineInterface::addToAdoptionList(const Dog &possibleAdopted)
{
    adoptionListController->addDogController(possibleAdopted.getName(),possibleAdopted.getBreed(),possibleAdopted.getPhoto(),possibleAdopted.getAge());
    adoptionListController->updateFile();
}

void CommandLineInterface::UIprintDog(const Dog &dog)
{
    std::cout << "The name is: " << dog.getName() << "\n";
    std::cout << "The breed is: " << dog.getBreed() << "\n";
    std::cout << "And it's only " << dog.getAge() << " years!\n";
    std::cout << std::endl << std::endl;
}


void CommandLineInterface:: startUserInterfaceWithClientRights()
{
    int command;
    std::cout<<"Hello, there. Here are the commands you can issue: \n0.Exit\n1.See all dogs.\n2.Filter dogs\n3.See adoption list\n";
    while(true)
    {
        while(true)
        {
            std::cin>>command;
            if(std::cin.fail())
            {
                std::cin.clear();
                std::cin.ignore(10000, '\n');
                std::cout<<"A number please";
                std::cin>>command;
                if(!std::cin.fail())
                    break;
            }
            else
                break;
        }
        std::cin.get();
        try
        {
            switch (command)
            {
                case 0:
                    std::cout << "Bye bye!\n";
                    return;
                case 1:
                    UIclientListDogs();
                    break;
                case 2:
                    UIclientFilterDogs();
                    break;
                case 3:
                    UIseeAdoptionList();
                    break;
                default:
                    std::cout << "Not a valid command :(\n";
            }
        }
        catch(std::exception &exception)
        {
            std::cerr<<exception.what()<<std::endl;

        }
    }
}

void CommandLineInterface::startUserInterfaceWithAdminRights()
{
    int command;
    std::cout<<"Hello, there. Here are the commands you can issue:\n1.List all dogs.\n2.Add a dog\n3.Remove a dog\n4.Update a dog\n";
    while(true)
    {
        while(true)
        {
            std::cin>>command;
            if(std::cin.fail())
            {
                std::cin.clear();
                std::cin.ignore(10000, '\n');
                std::cout<<"A number please";
                std::cin>>command;
                if(!std::cin.fail())
                    break;
            }
            else
                break;
        }
        std::cin.get();
        try
        {
            switch (command)
            {
                case 0:
                    std::cout << "Bye bye!\n";
                    return;
                case 1:
                    UIadminListDogs();
                    break;
                case 2:
                    UIaddDog();
                    controller->updateFile();
                    break;
                case 3:
                    UIremoveDog();
                    controller->updateFile();
                    break;
                case 4:
                    UIupdateDog();
                    controller->updateFile();
                    break;
                default:
                    std::cout << "Not a valid command :(\n";
            }
        }
        catch(std::exception &exception)
        {
            std::cerr<<exception.what()<<std::endl;

        }
    }

}

void CommandLineInterface::startPickingInterface(int argc,char **argv)
{
    int interfaceType = 0;
    while(interfaceType != 1 && interfaceType != 2)
    {
        std::cout << "Please pick an user interface. 1 for admin and 2 for client\n";
        while (true)
        {
            std::cin >> interfaceType;
            if (std::cin.fail())
            {
                std::cin.clear();
                std::cin.ignore(10000, '\n');
                std::cout << "A number please";
                std::cin >> interfaceType;
                if (!std::cin.fail())
                    break;
            } else
                break;
        }
    }
    if(interfaceType == 1)
        startUserInterfaceWithAdminRights();
    else
    {
        startUserInterfaceWithClientRights();
    }
}

void CommandLineInterface::UIaddDog()
{
    std::string name,breed,photo;
    int age;
    std::cout<<"Please give the name of a dog: ";
    std::getline(std::cin,name);

    std::cout<<"Please give the breed of the dog: ";
    std::getline(std::cin,breed);

    std::cout<<"Please give photo link of the dog: ";
    std::getline(std::cin,photo);

    std::cout<<"Please give age: ";
    while(true)
    {
        std::cin>>age;
        if(std::cin.fail())
        {
            std::cin.clear();
            std::cin.ignore(10000, '\n');
            std::cout<<"A number please";
            std::cin>>age;
            if(!std::cin.fail())
                break;
        }
        else
            break;
    }
    std::cin.get();

    controller->addDogController(name,breed,photo,age);

}

void CommandLineInterface::UIremoveDog()
{
    int index;
    std::cout<<"Please give the index of the dog: ";
    std::cin>>index;
    std::cin.get();
    controller->removeDogController(index);

}

void CommandLineInterface::UIupdateDog()
{
    std::string name;
    int modify;
    std::cout<<"Please give the name of the dog: ";
    std::getline(std::cin,name);

    std::cout<<"What would you like to modify? 1 = name, 2 = breed, 3 = photo, 4 = age: ";
    while(true)
    {
        std::cin>>modify;
        if(std::cin.fail())
        {
            std::cin.clear();
            std::cin.ignore(10000, '\n');
            std::cout<<"A number please";
            std::cin>>modify;
            if(!std::cin.fail())
                break;
        }
        else
            break;
    }
    std::cin.get();

    switch(modify)
    {
        case 1:
        {
            std::string newName;
            std::cout << "Please give new name: ";
            std::getline(std::cin, newName);
            controller->changeDogNameController(name, newName);
            break;
        }
        case 2:
        {
            std::string newBreed;
            std::cout << "Please give new breed: ";
            std::getline(std::cin, newBreed);
            controller->changeDogBreedController(name, newBreed);
            break;
        }
        case 3:
        {
            std::string newPhoto;
            std::cout << "Please give new photo: ";
            std::getline(std::cin, newPhoto);
            controller->changeDogPhotoController(name, newPhoto);
            break;
        }
        case 4:
        {
            int newAge;
            std::cout << "Please give new age: ";
            while(true)
            {
                std::cin>>newAge;
                if(std::cin.fail())
                {
                    std::cin.clear();
                    std::cin.ignore(10000, '\n');
                    std::cout<<"A number please";
                    std::cin>>newAge;
                    if(!std::cin.fail())
                        break;
                }
                else
                    break;
            }
            std::cin.get();
            controller->changeDogAgeController(name, newAge);
            break;
        }
    }

}

void CommandLineInterface::UIadminListDogs()
{
    int i;
    for(i=0;i<controller->getNumberOfDogsController();i++)
    {
        std::cout<<"The Index of the dog is: "<<i<<std::endl;
        UIprintDog((*controller)[i]);
    }
}


