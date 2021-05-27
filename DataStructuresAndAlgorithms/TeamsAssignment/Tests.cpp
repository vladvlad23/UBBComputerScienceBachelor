//
// Created by Vlad on 26-Apr-19.
//

#include <iostream>
#include "Tests.h"

void Tests::testAll()
{
    testCreationAndAdditionToHeap();
    testReadingFromList();
    testTopRemoval();
    testRemovalOfSmallestElements();
    testWriteToList();
}

void Tests::testCreationAndAdditionToHeap()
{
    Heap heapInstance;
    heapInstance.add(10);
    heapInstance.add(20);
    heapInstance.add(30);
    heapInstance.add(15);
    heapInstance.add(25);
    assert(heapInstance.elements[0] == 10);
    assert(heapInstance.elements[1] == 15);
    assert(heapInstance.elements[2] == 25);
    assert(heapInstance.elements[3] == 20);
    assert(heapInstance.elements[4] == 30);
}

void Tests::testReadingFromList()
{
    Heap heapInstance2;
    std::list<TElem> testList;
    testList.push_back(10);
    testList.push_back(20);
    testList.push_back(30);
    testList.push_back(15);
    testList.push_back(25);
    heapInstance2.readFromList(testList);

    assert(heapInstance2.elements[0] == 10);
    assert(heapInstance2.elements[1] == 15);
    assert(heapInstance2.elements[2] == 25);
    assert(heapInstance2.elements[3] == 20);
    assert(heapInstance2.elements[4] == 30);
}

void Tests::testRemovalOfSmallestElements()
{
    Heap heapInstance;
    heapInstance.add(10);
    heapInstance.add(20);
    heapInstance.add(30);
    heapInstance.add(15);
    heapInstance.add(25);
    heapInstance.removeSmallestElements(3);
    assert(heapInstance.removeTop() == 25);
    assert(heapInstance.removeTop() == 30);

    heapInstance.add(10);
    heapInstance.add(20);
    heapInstance.add(30);
    heapInstance.add(15);
    heapInstance.add(25);
    heapInstance.removeSmallestElements(3);
    heapInstance.removeSmallestElements(2);
    assert(heapInstance.size == 0);
}

void Tests::testTopRemoval()
{
    Heap heapInstance;
    heapInstance.add(10);
    heapInstance.add(20);
    heapInstance.add(30);
    heapInstance.add(15);
    heapInstance.add(25);
    assert(heapInstance.removeTop() == 10);
    assert(heapInstance.removeTop() == 15);
    assert(heapInstance.removeTop() == 20);
    assert(heapInstance.removeTop() == 25);
    assert(heapInstance.removeTop() == 30);
    assert(heapInstance.size == 0);
}

void Tests::testWriteToList()
{
    Heap heapInstance2;
    std::list<TElem> testList;
    heapInstance2.add(20);
    heapInstance2.add(25);
    heapInstance2.add(30);
    heapInstance2.add(10);
    heapInstance2.add(15);
    heapInstance2.writeToList(testList);

    assert(testList.front() == 10);
    testList.pop_front();
    assert(testList.front() == 15);
    testList.pop_front();
    assert(testList.front() == 20);
    testList.pop_front();
    assert(testList.front() == 25);
    testList.pop_front();
    assert(testList.front() == 30);
    testList.pop_front();
}

