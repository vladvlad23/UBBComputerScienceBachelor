#include <iostream>
#include "ShortTest.h"
#include "ExtendedTest.h"
int main()
{
    testAll();
    personalTest();
    testAllExtended();
    testLabAssignment();
    std::cout << "Hello, World!" << std::endl;
    return 0;
}