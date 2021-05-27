//
// Created by Vlad on 12-May-19.
//

#ifndef TEAMSASSIGNMENT_HEAP_H
#define TEAMSASSIGNMENT_HEAP_H


#include<list>

#define getLeftChild(position) ((2*position)+1)
#define getRightChild(position) ((2*position)+2)
#define getParent(position) ((position/2))
typedef int TElem;

class Heap
{
    friend class Tests;
    /*
     * The class will implement a binary min heap. That means
     * Both children will always be smaller than the parent
     * It is implemented using a dynamic array
     */

private:
    TElem *elements;
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

    void readFromList(std::list<TElem> &readingList);

    /* todo Andu
    * description The k smallest elements from the heap will be removed
     * pre: k>0 and k<number of elements
     * post: the k smallest elements will be removed
     * throws exception if k<0 or if k>number of elements
     */

    void removeSmallestElements(int k); //todo for Andu

    TElem removeTop(); //todo for Andu

    void writeToList(std::list<TElem> &writingList);


};
#endif //TEAMSASSIGNMENT_HEAP_H
