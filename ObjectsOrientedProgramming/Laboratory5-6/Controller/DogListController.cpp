//
// Created by Vlad on 24-Mar-19.
//

#include <stdexcept>
#include "DogListController.h"

void DogListController::addDogController(std::string name, std::string breed, std::string photo, int age)
{
    /*
     * Function will validate the input and add the dog to the repository through its function
     * param:name - string - name of dog
     * param:breed - string - name of breed
     * param:photo - string - link to dog photo
     * param:age - int - age of dog
     */
    validator.validateDog(name,breed,photo,age);
    repository->add(name,breed,photo,age);

}

void DogListController::removeDogController(int index)
{
    /*
     * Function will remove a dog from the repository after validating the index
     * param:index - int which will be validated and sent to the remove element to be removed
     */
    validator.validateIndex(index);
    repository->remove(index);
}

void DogListController::changeDogNameController(std::string oldName, std::string newName)
{
    /*
     * Function will change the name of a dog. First it will validate if the dog is there and then will add a new dog to the list
     * with the same characteristics except the name
     * param:oldName - string - old dog name
     * param:newName - string - new dog name
     */
    int index = getIndexByName(oldName);
    if(index==-1)
        throw std::invalid_argument("That dog isn't in the list");
    Dog dogToBeModified = repository->getDogByIndex(index);
    repository->remove(index);
    dogToBeModified.setName(newName);
    repository->add(dogToBeModified);
}




int DogListController::getNumberOfDogsController()
{
    // Function will return the number of dogs in the list
    return repository->numberOfDogs();
}



Dog DogListController::operator[](int index) const
{
    if (index < 0 || index >= repository->numberOfDogs())
    {
        throw std::invalid_argument("Invalid arguments when trying to \"fetch\" a dog");
    }
    return repository->getDogByIndex(index);
}

void DogListController::changeDogAgeController(std::string name, int newAge)
{
    /*
 * Function will change the age of a dog. First it will validate if the dog is there and then will add a new dog to the list
 * with the same characteristics except the age
 * param:oldName - string - old dog name
 * param:newAge - int - new dog age
 */
    int index = getIndexByName(name);
    if(index==-1)
    {
        throw std::invalid_argument("There is no dog by that name");
    }
    Dog dogToBeModified = repository->getDogByIndex(index);
    repository->remove(index);
    dogToBeModified.setAge(newAge);
    repository->add(dogToBeModified);

}

void DogListController::changeDogBreedController(std::string name, std::string newBreed)
{
    /*
 * Function will change the breed of a dog. First it will validate if the dog is there and then will add a new dog to the list
 * with the same characteristics except the breed
 * param:oldName - string - old dog name
 * param:newBreed - string - new dog breed
 */
    int index = getIndexByName(name);
    if(index==-1)
    {
        throw std::invalid_argument("There is no dog by that name");
    }
    Dog dogToBeModified = repository->getDogByIndex(index);
    repository->remove(index);
    dogToBeModified.setBreed(newBreed);
    repository->add(dogToBeModified);

}

void DogListController::changeDogPhotoController(std::string name, std::string newPhoto)
{
    /*
 * Function will change the photo of a dog. First it will validate if the dog is there and then will add a new dog to the list
 * with the same characteristics except the photo
 * param:oldName - string - old dog name
 * param:newPhoto - string - new dog photo
 */
    int index = getIndexByName(name);
    if(index==-1)
    {
        throw std::invalid_argument("There is no dog by that name");
    }
    Dog dogToBeModified = repository->getDogByIndex(index);
    repository->remove(index);
    dogToBeModified.setPhoto(newPhoto);
    repository->add(dogToBeModified);

}


DogListController DogListController::operator=(const DogListController &oldList)
{
    repository = oldList.repository;
    validator = DogListValidator(repository);
    return *this;
}

DogListController operator+(DogListController &list,const Dog &newDog)
{
    list.repository->add(newDog);
    return list;
}

DogListController operator+(const Dog &newDog, DogListController &list)
{
    list.repository->add(newDog);
    return list;
}



DogListController::~DogListController()
{

}

int DogListController::getIndexByName(std::string name)
{
    int i;
    for(i=0;i<repository->numberOfDogs();i++) //can't use other loop cause i need the index
    {
        if(repository->getDogByIndex(i).getName() == name)
            return i;
    }
    return -1;
}

int DogListController::getIndexByPhoto(std::string photo)
{
    /*
     * Function will return the index of a dog in the list by the photo. It will iterate through the repository and if it
     * finds a matching one, it will return the index
     */
    int i;
    if(photo == "Dead link")
        return -2;
    for(i=0;i<repository->numberOfDogs();i++) //can't use other loop cause i need the index
    {
        if((*repository)[i].getPhoto() == photo)
            return i;
    }
    return -1;
}

void DogListController::addStartingDogs()
{
    repository->add(Dog("Mr. Woofenstein", "Bichon",
                   "https://upload.wikimedia.org/wikipedia/commons/thumb/f/fa/AG_Cody.jpg/250px-AG_Cody.jpg", 1));
    repository->add(Dog("Miss CutiePaws", "Collie",
                   "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRO7c9xGedXWe7IRLCTNLmUYXl_7MHWAC50MyYKpGcDR0gk0YEd",
                   5));
    repository->add(Dog("Artoo Dogtoo", "Golden Retriever",
                   "https://cdn2-www.dogtime.com/assets/uploads/gallery/golden-retriever-dogs-and-puppies/golden-retriever-dogs-puppies-10.jpg",
                   2));
    repository->add(Dog("Winnie The Poodle", "Poodle",
                   "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS6eMRuYURWy1_aVqis8cpBmZ5Bt8Cveg1F9IyWTyANRJdJxmDf",
                   2));
    repository->add(Dog("Ozzy Pawsborne", "Mastiff",
                   "https://d17fnq9dkz9hgj.cloudfront.net/breed-uploads/2018/08/mastiff-detail.jpg?bust=1535566122&width=355",
                   3));
    repository->add(Dog("Mr.Fluffers", "Bichon",
                               "https://secure.img2-fg.wfcdn.com/im/68971445/resize-h600-w600%5Ecompr-r85/3584/35846408/Sitting+Bichon+Frise+Puppy+Statue.jpg", 1));


    repository->add(Dog("Massivo", "Mastiff",
                               "https://vetstreet.brightspotcdn.com/dims4/default/355383e/2147483647/thumbnail/645x380/quality/90/?url=https%3A%2F%2Fvetstreet-brightspot.s3.amazonaws.com%2F0d%2Fe08b40a7df11e0a0d50050568d634f%2Ffile%2FMastiff-5-645mk062111.jpg",
                               3));

    repository->add(Dog("Golden Boy", "Golden Retriever",
                               "https://www.thesprucepets.com/thmb/skYeQH255WxPaNzYWREDRfLgqFQ=/450x0/filters:no_upscale():max_bytes(150000):strip_icc()/golden-retriever-sitting-down-in-a-farm-837898820-5c7854ff46e0fb00011bf29a.jpg",
                               4));


}

void DogListController::sortVectorBy(Comparator<Dog> *comparator)
{
    repository->sortVectorBy(comparator);
}

void DogListController::updateFile()
{
    repository->printToFile();
}

void DogListController::readFromFile()
{
    repository->readFromFile();
}

bool DogListController::isHtml()
{
    return repository->isHtml();
}

void DogListController::addDogController(const Dog &dog)
{
    validator.validateDog(dog.getName(),dog.getBreed(),dog.getPhoto(),dog.getAge());
    repository->add(dog);

}

bool DogListController::existsBreed(std::string breed)
{
    for(int i=0;i<repository->numberOfDogs();i++) //can't use other loop cause i need the index
    {
        if((*repository)[i].getBreed() == breed)
            return true;
    }
    return false;
}
