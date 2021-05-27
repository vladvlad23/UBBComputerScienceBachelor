//
// Created by Vlad on 29-May-19.
//
/*
#include <iostream>
#include "BinarySearchTree.h"

template <class TElem>
BinarySearchTree<TElem>::BinarySearchTree()
{
    root = new Node();
    root->parent = nullptr;
    root->left = nullptr;
    root->right = nullptr;

}
template <class TElem>
BinarySearchTree<TElem>::BinarySearchTree(TElem e)
{
    empty = false;
    root = new Node();
    root->value = e->value;
    root->left = nullptr;
    root->right = nullptr;

}
template <class TElem>
BinarySearchTree<TElem>::~BinarySearchTree()
{
    //to free the BST, the best way i think is to free in the order of the postorder tranversal (this guarantees we first free the children
    //and then the parents. I am going to use a "manual" stack as
    Node *currentParrent = root;
    Node *currentLeftChild = root->left;
    Node *currentRightChild = root->right;
    while(root!=nullptr)
    {
        if (currentLeftChild != nullptr) //this means we can still go left
        {
            currentParrent = currentLeftChild;
            currentLeftChild = currentParrent->left;
            currentRightChild = currentParrent->right;
        }
        else if(currentRightChild != nullptr) //this means we can't go left, but we can go right
        {
            currentParrent = currentRightChild;
            currentLeftChild = currentParrent->left;
            currentRightChild = currentParrent->right;
        }
        else //we can't go left or right, free the root
        {
            std::cout<<"Deleting node with value "<<currentParrent->value->value;
            Node *temp = currentParrent;
            currentParrent = currentParrent->parent;
            delete temp;
        }
    }
}
template <class TElem>
void BinarySearchTree<TElem>::insertNode(TElem e)
{
    Node *node = root;
    while(true)
    {
        if(e->value < node->value->value)
        {
            if(node->left != nullptr)
                node = node->left;
            else
            {
                Node *newNode = new Node;
                newNode->parent = node;
                newNode->left = nullptr;
                newNode->right = nullptr;
                newNode->value = e;
                break;
            }
        }
        else
        {
            if(node->right != nullptr)
                node = node->right;
            else
            {
                Node *newNode = new Node;
                newNode->parent = node;
                newNode->left = nullptr;
                newNode->right = nullptr;
                newNode->value = e;
                break;
            }
        }
    }

    if(empty == true)
        empty = false;
}
template <class TElem>
void BinarySearchTree<TElem>::removeNode(Node *node)
{
    Node *currentNode = node;
    while(true)
    {
        if (currentNode->left == nullptr && currentNode->right == nullptr)
        {
            Node *parentNode = currentNode->parent;
            if (currentNode == parentNode->left)
                parentNode->left = nullptr;
            else
                parentNode->right = nullptr;
            delete currentNode;
        } else if (currentNode->left != nullptr && currentNode->right == nullptr)
        {
            Node *parentNode = currentNode->parent;
            if (currentNode == parentNode->left)
                parentNode->left = currentNode->left;
            else
                parentNode->right = currentNode->left;
            delete currentNode;
        } else if (currentNode->left == nullptr && currentNode->right != nullptr)
        {
            Node *parentNode = currentNode->parent;
            if (currentNode == parentNode->left)
                parentNode->left = currentNode->right;
            else
                parentNode->right = currentNode->right;
            delete currentNode;
        }
        else //find min of left subtree
        {
            Node *dummyNode = currentNode->left;
            while(dummyNode->right != nullptr)
            {
                dummyNode = dummyNode->right;
            }
            currentNode->value = dummyNode->value;
            delete dummyNode;
        }
    }

}
template <class TElem>
TElem BinarySearchTree<TElem>::getRoot()
{
    return root->value;
}

template <class TElem>
bool BinarySearchTree<TElem>::isEmpty()
{
    return empty;
}
*/