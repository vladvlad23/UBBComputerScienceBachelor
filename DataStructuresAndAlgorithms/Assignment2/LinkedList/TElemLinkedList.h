//
// Created by Vlad on 29-Mar-19.
//

#ifndef ASSIGNMENT2_TELEMLINKEDLIST_H
#define ASSIGNMENT2_TELEMLINKEDLIST_H


typedef int TElem;
typedef TElem TComp;

class Node{
public:
    TElem information;
    Node *next;

    Node(TElem information): information(information){ next = nullptr;}

    bool operator==(Node compareTo) { if(this->information == compareTo.information && this->next == compareTo.next) return true; return false;}
    bool operator!=(Node compareTo) { if(this->information != compareTo.information || this->next != compareTo.next) return true; return false;}
} ;

class LinkedListIterator;

class TElemLinkedList
{

    friend class LinkedListIterator;
private:
    Node *head;
    int numberOfElements;
public:
    TElemLinkedList();
    TElemLinkedList(TElem firstElement);
    ~TElemLinkedList();

    Node *getHead() const;
    void setHead(Node *head);
    Node* search(TElem elementToBeSearched) const;
    Node* getNodeByPosition(int position) const;
    void add(TElem elementToBeAdded);
    void addAfter(Node *currentNode, TElem elementToBeAdded);
    void remove(TElem elementToBeRemoved);
    TElem getElement(int position) const;
    LinkedListIterator iterator() const;
    bool isEmpty() const;
    int size() const;
    void operator=(const TElemLinkedList &list) {this->head = list.head;}


};


#endif //ASSIGNMENT2_TELEMLINKEDLIST_H
