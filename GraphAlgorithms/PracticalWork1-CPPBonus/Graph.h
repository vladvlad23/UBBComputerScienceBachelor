//
// Created by Vlad on 21-Mar-19.
//

#ifndef PRACTICALWORK1_CPPBONUS_GRAPH_H
#define PRACTICALWORK1_CPPBONUS_GRAPH_H
#include <map>
#include <vector>
#include <utility>
using namespace std;
class Graph {

private:
    map<int,vector<int>> mapIn;
    map<int,vector<int>> mapOut;
    map<pair<int,int>,int> mapEdges;
public:
    void addVertex(int);
    void populateGraphVertices(int);
    void removeVertex(int);
    void addEdge(int,int,int);
    void populateGraphEdges(int,int,int);
    void modifyEdge(int,int,int);
    void removeEdge(int,int);
    bool existsVertex(int);
    int numberOfVertices();
    int numberOfEdges();
    map<pair<int, int>, int> getEdges();
    vector<int> getIsolatedVertices();
    int getCost(int,int);
    bool isEdge(int, int);
    int getInDegree(int);
    int getOutDegree(int);
    vector<int> getInboundEdges(int);
    vector<int> getOutboundEdges(int);

    Graph();
    Graph(const Graph &initial);
    Graph(int);
    ~Graph();


};


#endif //PRACTICALWORK1_CPPBONUS_GRAPH_H
