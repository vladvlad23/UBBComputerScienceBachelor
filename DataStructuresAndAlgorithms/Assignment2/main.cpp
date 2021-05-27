#include <iostream>
#include "./Tests/PersonalTests.h"
#include "./Tests/ShortTest.h"
#include "./Tests/ExtendedTest.h"
int main()
{
    testLinkedList();
    testSortedSet();
    testAll();
    testAllExtended();
    std::cout << "Tests passed. Yay\n" << std::endl;
    return 0;
}