//
// Created by Vlad on 12-May-19.
//

#include <stdexcept>
#include "Matrix.h"
#include "MatrixIterator.h"
#include <iostream>

int Matrix::hashFunction(int line, int column) const
{
    return (line+nrLines()*column)%capacity;
}

void Matrix::doubleArraySize()
{
    if(!rehash)
        rehashElements();
}

void Matrix::rehashElements()
{
    capacity*=2;
    rehash=true;
    Node **newElements = new Node*[capacity];
    Node **temp;
    int i;
    temp = elements;
    elements = newElements;
    for(i=0;i<capacity;i++)
    {
        elements[i] = new Node;
        elements[i]->line = -1;
        elements[i]->column = -1;
        elements[i]->next = nullptr;
    }
    for(i=0;i<capacity/2;i++)
    {
        if(temp[i]->line != -1) // we have a list. Iterate, add to current and delete
        {
            Node *prev= temp[i];
            Node *current = temp[i];
            while(current!=nullptr)
            {
                prev = current;
                modify(current->line, current->column, current->value);
                current = current->next;
                delete prev;
            }
        }
    }
    rehash = false;
    delete [] temp;
}

Matrix::Matrix(int nrLines, int nrCols)
{
    if(nrLines<=0 || nrCols<=0)
    {
        throw std::invalid_argument("Invalid constructor arguments for Matrix\n");
    }
    size = 0;
    capacity = 5;
    lineNumber = nrLines;
    columnNumber = nrCols;
    elements = new Node*[capacity];
    for(int i=0;i<capacity;i++)
    {
        elements[i] = new Node;
        elements[i]->line=-1;
        elements[i]->column=-1;
        elements[i]->next=nullptr;
    }
}

int Matrix::nrLines() const
{
    return lineNumber;
}

int Matrix::nrColumns() const
{
    return columnNumber;
}

//Linear in the linked list
Matrix::Node * Matrix::getNodeFromList(Node *head, int line, int column) const
{
    Node *element = nullptr;
    Node *another = head;
    if(head->line != line || head->column!= column) // this means the head of the list is not the answer
    {
        element = head->next;
    }
    else
        return head;
    while(element != nullptr)
        if(element->line == line && element->column == column)
            return element;
        else
            element = element->next;
    if(element == nullptr) //reached end of list. nothing there to fit what we need
        return nullptr;




}

//same as getting node
TElem Matrix::element(int i, int j) const
{
    if(i<0 or i>=nrLines() or j<0 or j>=nrColumns())
    {
        throw std::invalid_argument("Invalid element params\n");
    }
    int arrayIndex = hashFunction(i,j);
    if(elements[arrayIndex]->line==-1 or elements[arrayIndex]->column == -1)
    {
        return NULL_TELEM;
    }
    Node *result = getNodeFromList(elements[arrayIndex],i,j);
    if(result== nullptr)
        return NULL_TELEM;
    return result->value;
}

//same as element
TElem Matrix::modify(int i, int j, TElem e)
{
    TElem oldValue;
    if(i>=nrLines() || j>=nrColumns())
        throw std::invalid_argument("Invalid params\n");
    int arrayIndex = hashFunction(i,j);
    if(elements[arrayIndex]->line == -1) // there is no element here
    {
        elements[arrayIndex]->line = i;
        elements[arrayIndex]->column = j;
        elements[arrayIndex]->value = e;
        elements[arrayIndex]->next = nullptr;
        size++;

        if (computeLoadFactor() > maxLoadFactor)
        {
            doubleArraySize();
        }
        return NULL_TELEM;
    }

    Node *result = getNodeFromList(elements[arrayIndex],i,j);
    if (result == nullptr) //this means the element is not in the list
    {
        result = elements[arrayIndex];
        while(result->next!=nullptr)
            result=result->next;
        result->next = new Node;
        result->next->line=i;
        result->next->column=j;
        result->next->value=e;
        result->next->next=nullptr;
    }
    else // the element is in the list so just change it
    {
        oldValue = result->value;
        result->value=e;
    }
    return oldValue;
}

Matrix::~Matrix()
{
    for(int i=0;i<capacity;i++)
    {
        freeList(elements[i]);
    }
    delete [] elements;

}

void Matrix::freeList(Node *list)
{
    if(list == nullptr)
    {
        return;
    }
    Node *prevNode = list->next;
    Node *iterativeNode = list->next;
    while(iterativeNode != nullptr)
    {
        prevNode = iterativeNode;
        iterativeNode = iterativeNode->next;
        delete prevNode;
    }
    delete list;

}

int Matrix::computeLoadFactor() const
{
    return size/capacity;

}

void Matrix::printMatrix() const
{
    int i,j;
    for(i=0;i<lineNumber;i++)
    {
        for(j=0;j<columnNumber;j++)
        {
            std::cout<<element(i,j)<<" ";
        }
        std::cout<<std::endl;
    }
}

MatrixIterator Matrix::iterator(int column)
{
    if(column<0 || column>=columnNumber)
        throw std::invalid_argument("Invalid column\n");
    return MatrixIterator(*this,column);
}

