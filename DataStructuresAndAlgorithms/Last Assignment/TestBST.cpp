//
// Created by Vlad on 29-May-19.
//

#include <assert.h>
#include "TestBST.h"

void TestBST::testBST()
{
    BinarySearchTree<DummyNode> tree;
    DummyNode node;
    node.value = 11;
    node.line = 2;
    node.column = 2;
    tree.insertNode(node);

    node.value = 7;
    node.line = 1;
    node.column = 1;
    tree.insertNode(node);

    node.line = 1;
    node.column = 0;
    tree.insertNode(node);

    node.value = 9;
    node.line = 1;
    node.column = 2;
    tree.insertNode(node);

    node.value = 4;
    node.line = 2;
    node.column = 1;
    tree.insertNode(node);

    node.value = 20;
    node.line = 2;
    node.column = 3;
    tree.insertNode(node);

    node.value = 19;
    node.line = 0;
    node.column = 1;
    tree.insertNode(node);

    node.value = 25;
    node.line = 0;
    node.column = 0;
    tree.insertNode(node);

    node.value = 15;
    node.line = 0;
    node.column = 2;
    tree.insertNode(node);

    node.value = 13;
    node.line = 4;
    node.column = 1;
    tree.insertNode(node);

    node.value = 17;
    node.line = 4;
    node.column = 2;
    tree.insertNode(node);


    node.line = 4;
    node.column = 2;
    tree.deleteValue(node);

    node.line = 2;
    node.column = 3;
    tree.deleteValue(node);

    node.line = 1;
    node.column = 1;
    tree.deleteValue(node);



}
