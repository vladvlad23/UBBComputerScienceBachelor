#include <iostream>
#include "TestBST.h"
#include "ShortTest.h"
#include "ExtendedTest.h"
int main()
{
   // TestBST::testBST();
   testAll();
   testAllExtended();
    std::cout << "Hello, World!" << std::endl;
    return 0;
}