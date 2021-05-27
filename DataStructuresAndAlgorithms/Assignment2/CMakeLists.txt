cmake_minimum_required(VERSION 3.10)
project(Assignment2)

set(CMAKE_CXX_STANDARD 14)

add_executable(Assignment2 main.cpp SortedSet/SortedSet.cpp SortedSet/SortedSet.h LinkedList/TElemLinkedList.cpp LinkedList/TElemLinkedList.h Tests/PersonalTests.cpp Tests/PersonalTests.h LinkedList/LinkedListIterator.cpp LinkedList/LinkedListIterator.h SortedSet/SortedSetIterator.cpp SortedSet/SortedSetIterator.h Tests/ExtendedTest.h Tests/ExtendedTest.cpp Tests/ShortTest.cpp Tests/ShortTest.h)