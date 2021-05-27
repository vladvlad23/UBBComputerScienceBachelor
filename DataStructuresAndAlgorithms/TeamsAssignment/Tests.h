//
// Created by Vlad on 12-May-19.
//

#ifndef TEAMSASSIGNMENT_TESTS_H
#define TEAMSASSIGNMENT_TESTS_H

#include "Heap.h"
#include <assert.h>


class Tests
{
public:

    static void testAll();
    static void testCreationAndAdditionToHeap();
    static void testReadingFromList();
    static void testRemovalOfSmallestElements();
    static void testTopRemoval();
    static void testWriteToList();

};

#endif //TEAMSASSIGNMENT_TESTS_H
