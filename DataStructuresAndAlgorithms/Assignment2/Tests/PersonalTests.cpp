//
// Created by Vlad on 30-Mar-19.
//

#include <cassert>
#include <exception>
#include <stdexcept>
#include "PersonalTests.h"

void testLinkedList()
{
    TElemLinkedList list;
    list.add(10);
    list.add(20);
    list.add(30);
    list.add(40);

    LinkedListIterator iterator = list.iterator();
    iterator.first();
    assert (iterator.getCurrent() == 10);
    iterator.next();
    assert (iterator.getCurrent() == 20);
    iterator.next();
    assert (iterator.getCurrent() == 30);
    iterator.next();
    assert (iterator.getCurrent() == 40);
    iterator.next();
    try
    {
        iterator.next();
        assert(false);
    }
    catch (std::exception&){}

    Node *node = list.search(30);
    assert(node->information == 30);
    assert(node->next->information == 40);

    list.addAfter(list.search(30), 35);
    assert (list.search(35)->information);
    assert (list.search(35)->next->information == 40);
    assert (node->next->information == 35);
    assert (node->next->next->information == 40);

    list.remove(35);
    assert(!list.search(35));
    assert(list.getElement(0) == 10);
    assert(list.getElement(1) == 20);
    assert(list.getElement(2) == 30);
    assert(list.getElement(3) == 40);








}

void testSortedSet()
{


}
