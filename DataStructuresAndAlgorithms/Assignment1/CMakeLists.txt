cmake_minimum_required(VERSION 3.10)
project(Assignment1)

set(CMAKE_CXX_STANDARD 14)

add_executable(Assignment1 main.cpp
        DAIterator.cpp DAIterator.h
        DynamicArray.cpp DynamicArray.h
        Tests/ShortTest.h Tests/ShortTest.cpp
        Tests/ExtendedTest.h Tests/ExtendedTest.cpp
        Tests/personalTest.cpp Tests/personalTest.h)