//
// Created by Vlad on 10-Apr-19.
//

#ifndef FIRSTTEST_DOMAIN_H
#define FIRSTTEST_DOMAIN_H


#include <string>

class Population
{
public:
    Population(std::string type,int numberOfOrganisms,bool isImmune);
    const std::string &getType() const;

    void setType(const std::string &type);

    int getNumberOfOrganisms() const;

    void setNumberOfOrganisms(int numberOfOrganisms);

    bool isImmune() const;

    void setIsImmune(bool isImmune);

private:

    std::string type;
    int numberOfOrganisms;
    bool immunity;

};


#endif //FIRSTTEST_DOMAIN_H
