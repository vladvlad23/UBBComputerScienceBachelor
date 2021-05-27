#include <iostream>
#include "./Tests/ShortTest.h"
#include "./Tests/personalTest.h"
#include "Tests/ExtendedTest.h"

int main() {
    try {
        testDynamicArrayPersonal();
        testAll();
        testAllExtended();
    }
    catch(std::exception&){
        std::cout<<"Tests Failed";
        return 0;
    }
    std::cout<<"Tests are succesfull. Yay :)";
    return 0;
}