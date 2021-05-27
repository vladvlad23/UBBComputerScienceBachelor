//
// Created by Vlad on 12-May-19.
//
#include "P11.h"
#include <stdexcept>
void removeMin(std::list<TElem> &list, int k)
{
    if(k<=0)
        throw std::invalid_argument("No good k\n");
    if(k>=list.size())
    {
        list.clear();
    }

    Heap h;
    std::list<TElem> newList;
    std::list<TElem>::iterator it = list.begin();
    int i;
    for(i=0;i<k;i++)
    {
        h.add((*it));
        it++;
    }
    while(it!=list.end())
    {
        if(h.peek()>(*it))
        {
            newList.push_back(h.removeTop());
            //std::cout<<" The tree balanced itself and now is: ";
            //h.printHeap();
            h.add((*it));
            //std::cout<<"Adding "<<(*it)<<" and the heap is: ";
            //h.printHeap();
        }
        else
        {
            newList.push_back((*it));
        }
        it++;
    }
    //h.printHeap();
    /*
    list.sort();
    for(auto a : list)
        std::cout<<a<<' ';
    std::cout<<'\n';*/
    list = newList;
    /*
    newList.sort();
    for(auto a : newList)
        std::cout<<a<<' ';
    std::cout<<'\n';
    std::cout<<'\n';*/
}