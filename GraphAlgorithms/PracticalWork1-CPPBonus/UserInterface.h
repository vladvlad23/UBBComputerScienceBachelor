//
// Created by Vlad on 23-Mar-19.
//

#ifndef PRACTICALWORK1_CPPBONUS_USERINTERFACE_H
#define PRACTICALWORK1_CPPBONUS_USERINTERFACE_H
#include "Graph.h"
#include <algorithm> //needed for swap. Well, not needed, but i am lazy
#include <iostream>

class UserInterface {

private:
    Graph repository;
    Graph copy;
    bool isCopy = false;
    bool opValid;

    void UIaddVertex();
    void UIaddEdge();
    void UIremoveVertex();
    void UIremoveEdge();
    void UInumberOfVertices();
    void UIisEdge();
    void UImodifyEdge();
    void UIprintGraph();
    void UIgetInDegree();
    void UIgetOutDegree();
    void UIshiftBetweenCopyAndOriginal();
    void UIparseInboundEdges();
    void UIparseOutboundEdges();
public:
    //constructor
    UserInterface(const Graph&repository);
    ~UserInterface();


    void startConsole();
};


#endif //PRACTICALWORK1_CPPBONUS_USERINTERFACE_H
