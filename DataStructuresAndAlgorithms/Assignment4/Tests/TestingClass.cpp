//
// Created by Vlad on 14-Apr-19.
//

#include "TestingClass.h"

void TestingClass::testDoubleLinkedListOnArray()
{
    DoubleLinkedListArray list;
    list.insertAtEnd(10);
    assert(list.search(10));
    list.insertAtEnd(20);
    assert(list.search(20));
    list.insertAtEnd(30);
    assert(list.search(30));
    list.insertAtEnd(40);
    assert(list.search(40));
    list.insertPosition(15,1);
    assert(list.search(15));
    list.insertPosition(25,3);
    assert(list.search(25));
    list.insertPosition(35,5);
    assert(list.search(35));
    list.insertPosition(5,0);
    assert(list.search(5));
    assert(list.search(40));
    assert(list.getSize() == 8);

    //########## Test removal ##########

    list.removeByNodePosition(2);
    assert(list.getSize() == 7);
    assert(!list.search(30));
    assert(list.search(40));

    list.removeByNodePosition(0);
    assert(list.getSize() == 6);
    assert(!list.search(10));

    list.insertAtEnd(30);
    assert(list.search(30));



}

void TestingClass::testInLabAssignment()
{
    Set firstSet;
    firstSet.add(10);
    firstSet.add(15);
    firstSet.add(20);
    firstSet.add(25);
    firstSet.add(30);
    firstSet.add(35);
    firstSet.add(40);
    firstSet.add(45);

    Set secondSet;
    secondSet.add(42);
    secondSet.add(50);
    secondSet.add(60);
    secondSet.add(15);
    secondSet.add(25);
    secondSet.add(35);
    secondSet.add(45);

    assert(firstSet.difference(secondSet)==4);
    assert(!firstSet.search(15));
    assert(!firstSet.search(25));
    assert(!firstSet.search(35));
    assert(!firstSet.search(45));

}