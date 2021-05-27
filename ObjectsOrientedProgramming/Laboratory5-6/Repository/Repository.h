//
// Created by Vlad on 05-Apr-19.
//

#ifndef LABORATORY5_6_REPOSITORY_H
#define LABORATORY5_6_REPOSITORY_H

#include <fstream>
#include <cstring>
#include <sstream>
#include <vector>
#include <Comparator.h>
#include <algorithm>
#include "../Domain/Dog.h"


class Repository
{

protected:
    std::vector<Dog> *dynamicVector;
    std::string fileName;
public:

    Repository(std::vector<Dog> *dynamicVector,std::string fileName) : dynamicVector(dynamicVector), fileName(fileName){}
    virtual ~Repository() { };

    Dog operator[](int index);

    bool isHtml();

    void add(std::string name,std::string breed,std::string photo, int age);

    void add(const Dog &newDog);

    void remove(int index);

    Dog getDogByIndex(int index) const;

    int numberOfDogs() const;

    void sortVectorBy(Comparator<Dog> *comparator);

    virtual void readFromFile() = 0;
    virtual void printToFile() = 0;
};

class CSVRepository : public Repository
{

public:


    CSVRepository(std::vector<Dog> *dynamicVector,std::string fileName) : Repository(dynamicVector,fileName) {};
    void printToFile()
    {
        std::ofstream out{fileName};
        for(auto &dog: *dynamicVector)
        {
            out<<dog;
        }

    }

    void readFromFile()
    {
        std::ifstream f;
        f.open(fileName);
        Dog dogToBeRead{};
        while(f>>dogToBeRead)
        {
            dynamicVector->push_back(dogToBeRead);
        }
        /*
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
            dynamicVector->push_back(dog);
        }*/

    }
};

class HTMLRepository : public Repository
{
public:
    HTMLRepository(std::vector<Dog> *dynamicVector,std::string fileName) : Repository(dynamicVector,fileName) {};
    void readFromFile()
    {
        std::string lineToBeParsed;
        std::vector<std::string> resultedParameters;
        std::ifstream f;
        f.open(fileName);
        if(f.fail() || f.peek() ==  std::ifstream::traits_type::eof()) // file is empty
        {
            printToFile();
        }
        if(!std::getline(f,lineToBeParsed))
            throw std::invalid_argument("Bad file\n");
        if(lineToBeParsed != "<!DOCTYPE html>")
            throw std::invalid_argument("Bad File\n");
        //idk if all 6 line should be checked, but i will not
        for(int i=0;i<12;i++) //all fields until elements
        {
            std::getline(f,lineToBeParsed);
        }
        std::getline(f,lineToBeParsed); //<tr>
        while(lineToBeParsed!="</table>")
        {
            resultedParameters.clear();
            if(lineToBeParsed != "<tr>")
                throw std::invalid_argument("Bad File\n");
            std::getline(f,lineToBeParsed); //<td>
            std::getline(f,lineToBeParsed);
            resultedParameters.push_back(lineToBeParsed);
            std::getline(f,lineToBeParsed); // </td>
            std::getline(f,lineToBeParsed); //<td>
            std::getline(f,lineToBeParsed);
            resultedParameters.push_back(lineToBeParsed);
            std::getline(f,lineToBeParsed); // </td>
            std::getline(f,lineToBeParsed); //<td>
            std::getline(f,lineToBeParsed);
            resultedParameters.push_back(lineToBeParsed);
            std::getline(f,lineToBeParsed); // </td>
            std::getline(f,lineToBeParsed); //<td>
            std::getline(f,lineToBeParsed);
            resultedParameters.push_back(lineToBeParsed);
            std::getline(f,lineToBeParsed); // </td>
            std::getline(f,lineToBeParsed); // </tr>



            Dog dog(resultedParameters[0],resultedParameters[1],resultedParameters[2],stoi(resultedParameters[3]));
            dynamicVector->push_back(dog);
            std::getline(f,lineToBeParsed); //<tr>
        }
    }

    void printToFile()
    {
        std::ofstream out;
        out.open(fileName);
        out<<"<!DOCTYPE html>\n";

        out<<"<html>\n";
        out<<"<head>\n";
        out<<"<title>Dog List</title>\n";
        out<<"</head>\n";
        out<<"<body>\n";
        out<<"<table border=\"1\">\n";
        out<<"<tr>\n";
        out<<"<td>Name</td>\n";
        out<<"<td>Breed</td>\n";
        out<<"<td>Link</td>\n";
        out<<"<td>Age</td>\n";
        out<<"</tr>\n";

        for(const auto &dog: *dynamicVector)
        {
            out<<"<tr>\n";
            out << "<td>\n" << dog.getName() <<"\n" << "</td>\n";
            out << "<td>\n" << dog.getBreed() <<"\n"<< "</td>\n";
            out << "<td><a href=\n" << dog.getPhoto()<<"\n" << ">Link</a></td>\n";
            out << "<td>\n" << std::to_string(dog.getAge())<<"\n" << "</td>\n";
            out<<"</tr>\n";
        }

        out<<"</table>\n";
        out<<"</body>\n";
        out<<"</html>\n";



    }


};
#endif //LABORATORY5_6_REPOSITORY_H
