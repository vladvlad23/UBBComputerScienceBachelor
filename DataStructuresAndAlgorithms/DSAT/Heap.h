//
// Created by Vlad on 17-Apr-19.
//
#ifndef DSATEAMWORK_HEAP_H
#define DSATEAMWORK_HEAP_H

#include<list>
#include<iostream>
#define getLeftChild(position) ((2*position)+1)
#define getRightChild(position) ((2*position)+2)
#define getParent(position) (((position - 1)/2))

typedef int TElem;

class Heap
{
    friend class Tests;
    /*
     * The class will implement a binary max heap. That means
     * Both children will always be smaller than the parent
     * It is implemented using a dynamic array
     */

private:
    //TElem *elements;
    int size,capacity;

    /*
     * description: Function will add an element to the heap and balance the heap
     * pre:
     *  - this = balanced heap
     *  - newElement = the element to be added
     * post:
     *  -new Element is now in heap and heap is balanced
     *
     * Complexity:
     * Best case - Theta(1) ( no switching required)
     * Worst case - O(log(size))
     * Average case - O(log(size))
     */


    void bubbleUp(TElem newElement); //todo for Vlad


    void bubbleDown(); //todo for Andu
    void doubleCapacity();

public:
    TElem *elements;
    /*
     * pre -
     * description - class constructor
     * post - Function will alocate memory for the required variables in the class.
     */
    Heap();

    /*
     * pre: the heap is oki
     * description: function will destroy the dynamically allocated stuff in the class
     * post: the dynamic elements allocated in heap are destroyed
     *
     */
    ~Heap();

    /*
     * Function only add using the bubble up function. Might be removed
     */
    void add(TElem newElement) { bubbleUp(newElement); }

    TElem peek();

    TElem removeTop(); //todo for Andu

    void printHeap();



};


#endif //DSATEAMWORK_HEAP_H

