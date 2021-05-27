cmake_minimum_required(VERSION 3.10)
project(Assignment4)

set(CMAKE_CXX_STANDARD 14)
SET(GCC_COVERAGE_COMPILE_FLAGS "-fprofile-arcs -ftest-coverage")
SET(GCC_COVERAGE_LINK_FLAGS    "-lgcov")

SET(CMAKE_CXX_FLAGS  "${CMAKE_CXX_FLAGS} ${GCC_COVERAGE_COMPILE_FLAGS}")
SET(CMAKE_EXE_LINKER_FLAGS  "${CMAKE_EXE_LINKER_FLAGS} ${GCC_COVERAGE_LINK_FLAGS}")
add_executable(Assignment4 main.cpp
        Set/Set.cpp Set/Set.h
        Tests/ExtendedTest.cpp Tests/ExtendedTest.h
        Tests/ShortTest.h Tests/ShortTest.cpp Set/SetIterator.cpp Set/SetIterator.h DoubleLinkedListArray/DoubleLinkedListArray.cpp DoubleLinkedListArray/DoubleLinkedListArray.h Tests/TestingClass.cpp Tests/TestingClass.h DoubleLinkedListArray/DoubleLinkedListArrayIterator.cpp DoubleLinkedListArray/DoubleLinkedListArrayIterator.h)