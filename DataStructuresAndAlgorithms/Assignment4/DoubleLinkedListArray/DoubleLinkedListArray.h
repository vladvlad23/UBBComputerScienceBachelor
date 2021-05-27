//
// Created by Vlad on 13-Apr-19.
//

#ifndef ASSIGNMENT4_DOUBLELINKEDLISTARRAY_H
#define ASSIGNMENT4_DOUBLELINKEDLISTARRAY_H

typedef int TElem;
class DoubleLinkedListArrayIterator;
class DoubleLinkedListArray
{
    friend class DoubleLinkedListArrayIterator;
    class DLLANode
    {
        friend class DoubleLinkedListArray;
        //this might boom
    public:
        TElem info;
        int next;
        int prev;
    };

private:
    DLLANode *nodes;
    int capacity;
    int size;
    int head;
    int tail;
    int firstEmpty;

    int allocateNewElement();
    void doubleCapacity();

public:
    int getSize() const{ return size; }
    DoubleLinkedListArray();
    ~DoubleLinkedListArray();
    void insertPosition(TElem element, int position);
    void remove(int position);
    void removeByNodePosition(int position);
    void insertAtEnd(TElem element);
    bool search(TElem element) const;
    int searchPosition(TElem element) const;

    DoubleLinkedListArrayIterator iterator() const;

};


#endif //ASSIGNMENT4_DOUBLELINKEDLISTARRAY_H
