//
// Created by Vlad on 24-Mar-19.
//

#include <assert.h>
#include <stdexcept>
#include "tests.h"
#include "Windows.h"
#include "shellapi.h"
#include "../Repository/Repository.h"

void testAll()
{
    testDogCreation();
    testRemovalAndModification();
    testInLabAssignment();
    testCSVAndHTML();
}

void testCSVAndHTML()
{
    std::vector<Dog> dynamicVector;

    std::string lineToBeParsed;
    std::vector<std::string> resultedParameters;
    std::ifstream f;
    f.open("input.txt");
    while(std::getline(f,lineToBeParsed))
    {
        std::string tempString;
        std::istringstream stream(lineToBeParsed);
        while(std::getline(stream,tempString,','))
        {
            resultedParameters.push_back(tempString);
        }
        Dog dog(resultedParameters[0],resultedParameters[1],resultedParameters[2],stoi(resultedParameters[3]));
        resultedParameters.clear();
        dynamicVector.push_back(dog);
    }

 //   repo.printToFile("input.html");



}

void testDogCreation()
{
    Dog dog("Mr. Woofenstein","Bichon","Dead link",10);
    Dog dog2("Miss CutiePaws","Collie","Dead link",5);
    Dog dog3("Artoo Dogtoo","Golden Retriever","Dead link",2);
    assert(dog.getAge()==10);
    assert(dog.getName()=="Mr. Woofenstein");
    assert(dog.getBreed()=="Bichon");

    assert(dog2.getAge()==5);
    assert(dog2.getName()=="Miss CutiePaws");
    assert(dog2.getBreed()=="Collie");

    assert(dog3.getAge()==2);
    assert(dog3.getName()=="Artoo Dogtoo");
    assert(dog3.getBreed()=="Golden Retriever");
}

void testRemovalAndModification()
{
    Dog dog("Mr. Woofenstein","Bichon","Dead link",10);
    Dog dog2("Miss CutiePaws","Collie","Dead link",5);
    Dog dog3("Artoo Dogtoo","Golden Retriever","Dead link",2);

    std::vector<Dog> vector;
    Repository *repository = new CSVRepository(&vector,"testFile.txt");
    DogListValidator validator(repository);
    DogListController controller(repository,validator);

    controller.addDogController("Mr. Woofenstein","Bichon","Dead link",10);
    controller.addDogController("Miss CutiePaws","Collie","Dead link",5);
    controller.addDogController("Artoo Dogtoo","Golden Retriever","Dead link",2);

    assert(controller.getNumberOfDogsController() == 3);
    assert(controller[0] == dog);
    assert(controller[1] == dog2);
    assert(controller[2] == dog3);


    // ########## TESTING REMOVAL ###########
    controller.removeDogController(1);
    assert(controller.getNumberOfDogsController() == 2);
    assert(controller[0] == dog);
    assert(controller[1] == dog3);

    controller.removeDogController(0);
    assert(controller.getNumberOfDogsController() == 1);
    assert(controller[0] == dog3);

    controller.removeDogController(0);
    assert(controller.getNumberOfDogsController() == 0);
/*
    try
    {
        controller.removeDogController(0);
        assert(false);
    }
    catch(std::domain_error &exception)
    {
    }
*/
    //add dogs back
    controller.addDogController("Mr. Woofenstein","Bichon","Dead link",10);
    controller.addDogController("Miss CutiePaws","Collie","Dead link",5);
    controller.addDogController("Artoo Dogtoo","Golden Retriever","Dead link",2);

    //########## TEST DOG MODIFICATION ##########

    controller.changeDogNameController("Mr. Woofenstein","Mr FluffyPaws");
    assert(controller.getIndexByName("Mr FluffyPaws")!= -1 && controller.getIndexByName("Mr. Woofenstein") == -1);
    controller.changeDogAgeController("Miss CutiePaws",99);
    int indexOfDog = controller.getIndexByName("Miss CutiePaws");
    assert(controller[indexOfDog].getAge()==99);
    controller.changeDogBreedController("Artoo Dogtoo","Husky");
    indexOfDog = controller.getIndexByName("Artoo Dogtoo");
    assert(controller[indexOfDog].getBreed()=="Husky");
    controller.changeDogPhotoController("Artoo Dogtoo","Not dead link");
    indexOfDog = controller.getIndexByName("Artoo Dogtoo");
    assert(controller[indexOfDog].getPhoto() == "Not dead link");

    //########## TEST DOG ADDITION BY OVERLOADED OPERATOR ###########
    controller = controller + Dog("Woofie","German Shepperd","Dead link",5);
    Dog newDog = Dog("Pawsitive","Shiba Inu","Dead link",3);
    controller = newDog + controller;
    assert (controller.getIndexByName("Pawsitive") != -1);

    std::vector<Dog> vector2;
    Repository *repository2 = new CSVRepository(&vector2,"textFile.txt");
    DogListValidator validator2(repository2);
    DogListController controller2(repository2,validator2);

    controller2 = controller2 + dog;
    controller2 = dog2 + controller2;
    controller2 = controller2 + dog3;

    assert(controller2.getNumberOfDogsController() == 3);
    assert(controller2[0] == dog);
    assert(controller2[1] == dog2);
    assert(controller2[2] == dog3);

    /*
    ShellExecuteA(NULL, NULL, "chrome.exe",
            "https://d17fnq9dkz9hgj.cloudfront.net/breed-uploads/2018/08/mastiff-detail.jpg?bust=1535566122&width=355",
            NULL, SW_SHOWMAXIMIZED);
*/


}

void testInLabAssignment()
{
    Dog dog("Mr. Woofenstein","Bichon","Dead link",10);
    Dog dog2("Miss CutiePaws","Collie","Dead link",5);
    Dog dog3("Artoo Dogtoo","Golden Retriever","Dead link",2);

    Comparator<Dog> *compareAscending = new ComparatorAscendingByAge();
    Comparator<Dog> *compareDescending = new ComparatorDescendingByBreed();
    assert(compareAscending->compare(dog,dog2) == false);
    assert(compareAscending->compare(dog2,dog) == true);
    assert(compareDescending->compare(dog2,dog) == true);
    assert(compareDescending->compare(dog,dog2) == false);
    assert(compareDescending->compare(dog3,dog2) == true);
    assert(compareDescending->compare(dog3,dog2) == true);
    assert(compareDescending->compare(dog3,dog) == true);

    std::vector<Dog> vector;
    Repository *repository = new CSVRepository(&vector,"testFile.txt");
    DogListValidator validator(repository);
    DogListController controller(repository,validator);

    controller.addDogController("Mr. Woofenstein","Bichon","Dead link",2);
    controller.addDogController("Miss CutiePaws","Collie","Dead link",5);
    controller.addDogController("Artoo Dogtoo","Golden Retriever","Dead link",10);

    dog.setAge(2);
    dog2.setAge(5);
    dog3.setAge(10);

    controller.sortVectorBy(compareAscending);
    assert(controller[0] == dog);
    assert(controller[1] == dog2);
    assert(controller[2] == dog3);

    controller.sortVectorBy(compareDescending);
    assert(controller[0] == dog3);
    assert(controller[1] == dog2);
    assert(controller[2] == dog);

}
