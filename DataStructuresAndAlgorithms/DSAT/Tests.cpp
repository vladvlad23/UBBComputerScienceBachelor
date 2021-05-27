//
// Created by Vlad on 26-Apr-19.
//

#include <iostream>
#include <algorithm>
#include "Tests.h"
#include "Heap.h"
#include "P11.h"

void Tests::testAll()
{
    testCreationAndAdditionToHeap();
    testTopRemoval();
    testAssignmentFunction();
}

void Tests::testCreationAndAdditionToHeap()
{
    Heap heapInstance;
    heapInstance.add(10);
    heapInstance.add(20);
    heapInstance.add(30);
    heapInstance.add(15);
    heapInstance.add(25);
    assert(heapInstance.elements[0]>heapInstance.elements[1] && heapInstance.elements[0] > heapInstance.elements[2]);
    assert(heapInstance.elements[1]>heapInstance.elements[2] && heapInstance.elements[1] > heapInstance.elements[3]);
    assert(heapInstance.elements[2]>heapInstance.elements[4] && heapInstance.elements[2] > heapInstance.elements[5]);

}

void Tests::testTopRemoval()
{
    Heap heapInstance;
    heapInstance.add(10);
    heapInstance.add(20);
    heapInstance.add(30);
    heapInstance.add(15);
    heapInstance.add(25);
    assert(heapInstance.removeTop() == 30);
    assert(heapInstance.removeTop() == 25);
    assert(heapInstance.removeTop() == 20);
    assert(heapInstance.removeTop() == 15);
    assert(heapInstance.removeTop() == 10);
    assert(heapInstance.size == 0);
}

void Tests::testAssignmentFunction()
{
    std::list<TElem> testList;
    testList.push_back(20);
    testList.push_back(25);
    testList.push_back(30);
    testList.push_back(10);
    testList.push_back(15);

    removeMin(testList,3);
    assert(testList.size()==2);
    assert((*testList.begin())==30);
    testList.pop_front();
    assert((*testList.begin())==25);
}

