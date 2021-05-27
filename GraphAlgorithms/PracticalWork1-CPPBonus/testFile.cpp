//
// Created by Vlad on 21-Mar-19.
//
#include "testFile.h"
void testIfGraphWorks()
{
    Graph graph;
    graph.addVertex(0);
    graph.addVertex(1);
    graph.addVertex(2);

    assert(graph.numberOfVertices() == 3);
    assert(graph.existsVertex(0)== true);
    assert(graph.existsVertex(1) == true);
    assert(graph.existsVertex(2)== true);

    try {
        graph.addVertex(0);
        assert(false);
    }
    catch(std::invalid_argument e){}

    graph.addEdge(0,1,2);
    graph.addEdge(0,2,3);
    graph.addEdge(1,0,4);
    graph.addEdge(1,1,5);
    graph.addEdge(1,2,6);

    assert(graph.isEdge(0,1));
    assert(graph.isEdge(0,2));
    assert(graph.isEdge(1,0));
    assert(graph.isEdge(1,1));
    assert(graph.isEdge(1,2));

    graph.removeVertex(1);
    assert(graph.numberOfVertices() == 2);
    assert(graph.existsVertex(1)==false);
    graph.removeVertex(0);
    assert(graph.numberOfVertices() == 1);
    assert(graph.existsVertex(0)==false);
    assert(graph.existsVertex(2)==true);

    assert(graph.numberOfEdges()==0);

    graph.addVertex(1);
    graph.addVertex(0);
    graph.addEdge(0,1,2);
    graph.addEdge(0,2,3);
    graph.addEdge(1,0,4);
    graph.addEdge(1,1,5);
    graph.addEdge(1,2,6);

    assert(graph.isEdge(0,1));
    assert(graph.isEdge(0,2));
    assert(graph.isEdge(1,0));
    assert(graph.isEdge(1,1));
    assert(graph.isEdge(1,2));

    int x = graph.getInDegree(0);
    assert(graph.getInDegree(0)==1);
    assert(graph.getOutDegree(0)==2);
    assert(graph.getInDegree(1)==2);
    assert(graph.getOutDegree(1)==3);
    assert(graph.getInDegree(2)==2);
    assert(graph.getOutDegree(2)==0);

    graph.removeEdge(0,1);
    assert(graph.isEdge(0,1) == false);
    graph.removeEdge(0,2);
    assert(graph.isEdge(0,2) == false);
    graph.removeEdge(1,0);
    assert(graph.isEdge(1,0) == false);
    graph.removeEdge(1,1);
    assert(graph.isEdge(1,1)== false);
    assert(graph.numberOfEdges() == 1);

    graph.removeEdge(1,2);
    assert(graph.numberOfEdges() == 0);

    graph.addEdge(0,1,2);
    graph.addEdge(0,2,3);
    graph.addEdge(1,0,4);
    graph.addEdge(1,1,5);
    graph.addEdge(1,2,6);

    assert(graph.getCost(0,1) == 2);
    assert(graph.getCost(0,2) == 3);
    assert(graph.getCost(1,0) == 4);
    assert(graph.getCost(1,1) == 5);
    assert(graph.getCost(1,2) == 6);

    vector<int> inVector = graph.getInboundEdges(2);
    assert(inVector[0]==0);
    assert(inVector[1]==1);

    vector<int> outVector = graph.getOutboundEdges(1);
    assert(outVector[0] == 0);
    assert(outVector[1] == 1);
    assert(outVector[2] == 2);

    graph.modifyEdge(0,1,10);
    graph.modifyEdge(0,2,11);
    graph.modifyEdge(1,0,12);
    graph.modifyEdge(1,1,13);
    graph.modifyEdge(1,2,14);


    assert(graph.getCost(0,1) == 10);
    assert(graph.getCost(0,2) == 11);
    assert(graph.getCost(1,0) == 12);
    assert(graph.getCost(1,1) == 13);
    assert(graph.getCost(1,2) == 14);
}


