//
// Created by Vlad on 29-May-19.
//

#ifndef LAST_ASSIGNMENT_BINARYSEARCHTREE_H
#define LAST_ASSIGNMENT_BINARYSEARCHTREE_H

#include <iostream>

template <class TElem>
class BinarySearchTree
{
    struct Node
    {
        TElem value;
        Node *parent;
        Node *right;
        Node *left;
    };
private:
    Node* root;
    int size;
    bool empty;
public:

    BinarySearchTree();
    BinarySearchTree(TElem e);
    ~BinarySearchTree();
    void insertNode(TElem e);
    void removeNode(Node *node);
    TElem getRoot();
    bool isEmpty();

    void deleteValue(TElem elem);

    void changeValue(TElem elem);

    Node* findNode(TElem elem) const
    {
        if(root == nullptr)
            return NULL;
        Node *node = root;
        while(true)
        {
            if(elem.line == node->value.line && elem.column == node->value.column)
                return node;
            if(elem.line < node->value.line || (elem.line == node->value.line && elem.column < node->value.column))
            {
                if(node->left == nullptr)
                    return nullptr;
                else
                    node = node->left;
            }
            else
            {
                if(node->right == nullptr)
                    return nullptr;
                else
                    node = node->right;
            }
        }
    }

};

template <class TElem>
BinarySearchTree<TElem>::BinarySearchTree()
{
    root = nullptr;
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
    if(root==nullptr)
    {
        return;
    }
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
            if(currentParrent == root)
            {
                delete root;
                root = nullptr;
                return;
            }
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
    if(root==nullptr)
    {

        root = new Node;
        root->parent = node;
        root->left = nullptr;
        root->right = nullptr;
        root->value = e;
        empty = false;
        return;
    }
    while(true)
    {
        if(e.line < node->value.line || (e.line == node->value.line && e.column < node->value.column))
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
                node->left = newNode;
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
                node->right = newNode;
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
    if(node==nullptr)
        return;
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
            return;
        } else if (currentNode->left != nullptr && currentNode->right == nullptr)
        {
            Node *parentNode = currentNode->parent;
            if (currentNode == parentNode->left)
                parentNode->left = currentNode->left;
            else
                parentNode->right = currentNode->left;
            delete currentNode;
            return;
        } else if (currentNode->left == nullptr && currentNode->right != nullptr)
        {
            Node *parentNode = currentNode->parent;
            if (currentNode == parentNode->left)
                parentNode->left = currentNode->right;
            else
                parentNode->right = currentNode->right;
            delete currentNode;
            return;
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
            return;
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

template<class TElem>
void BinarySearchTree<TElem>::deleteValue(TElem elem)
{
    Node *node = findNode(elem);
    if(node==nullptr)
        return;
    removeNode(node);
}

template<class TElem>
void BinarySearchTree<TElem>::changeValue(TElem elem)
{
    Node *node = findNode(elem);
    node->value.value = elem.value;
}


#endif //LAST_ASSIGNMENT_BINARYSEARCHTREE_H
