//
// Created by Vlad on 21-Mar-19.
//

#include "Graph.h"

void Graph::addVertex(int vertex) {
    if(existsVertex(vertex))
    {
        throw invalid_argument("Duplicate vertex");
    }
    vector<int> vector1;
    vector<int> vector2;
    mapIn.insert({vertex,vector1});
    mapOut.insert({vertex,vector2});
}

void Graph::removeVertex(int vertex) {
    if(existsVertex(vertex) == false)
        throw std::invalid_argument("Vertex does not exist");
    map<int,vector<int>>::iterator it = mapOut.find(vertex);
    mapOut.erase(it); //erase from out map
    it = mapIn.find(vertex);
    mapIn.erase(it);
    for(it = mapIn.begin();it!=mapIn.end();it++)
    {
        vector<int>::iterator vectorIterator;
        for (int i = 0;i<it->second.size();i++)
        {
            if(it->second[i] == vertex) {
                it->second.erase(it->second.begin() + i);
                i--;
            }
        }
    }


    for(it = mapOut.begin();it!=mapOut.end();it++)
    {
        vector<int>::iterator vectorIterator;
        for (int i = 0;i<it->second.size();i++)
        {
            if(it->second[i] == vertex) {
                it->second.erase(it->second.begin() + i);
                i--;
            }
        }
    }
    map<pair<int,int>,int>::iterator itEdges;
    for (itEdges = mapEdges.begin();itEdges != mapEdges.end();)
    {
        if(itEdges->first.first == vertex || itEdges->first.second == vertex)
        {
            itEdges = mapEdges.erase(itEdges);
        }
        else
            ++itEdges;
    }
}

void Graph::addEdge(int firstVertex, int secondVertex,int cost) {
    if(isEdge(firstVertex,secondVertex)) {
        throw std::invalid_argument("There already is an edge there. Don't be edgy");
    }
    mapEdges.insert({make_pair(firstVertex, secondVertex), cost});
    mapOut[firstVertex].push_back(secondVertex);
    mapIn[secondVertex].push_back(firstVertex);

}

void Graph::populateGraphVertices(int vertex) {
    vector<int> vector1;
    vector<int> vector2;
    mapIn.insert({vertex,vector1});
    mapOut.insert({vertex,vector2});
}

void Graph::populateGraphEdges(int firstVertex,int secondVertex,int cost){
    mapEdges.insert({make_pair(firstVertex, secondVertex), cost});
    mapOut[firstVertex].push_back(secondVertex);
    mapIn[secondVertex].push_back(firstVertex);

}

void Graph::modifyEdge(int firstVertex, int secondVertex,int newCost) {
    if(!isEdge(firstVertex,secondVertex))
    {
        throw std::invalid_argument("No edge");
    }
    mapEdges.erase(make_pair(firstVertex,secondVertex));
    mapEdges.insert({make_pair(firstVertex, secondVertex), newCost});
}

void Graph::removeEdge(int firstVertex,int secondVertex)
{
    if(isEdge(firstVertex,secondVertex) == false)
    {
        throw std::invalid_argument("No edges between these 2 vertices");
    }
    map<pair<int,int>,int>::iterator iter = mapEdges.find(make_pair(firstVertex,secondVertex));
    mapEdges.erase(iter);

    for(int i=0;i<mapIn[secondVertex].size();i++)
    {
        if(mapIn[secondVertex][i] == firstVertex)
        {
            for(int j=i;j<mapIn[secondVertex].size();j++)
            {
                mapIn[secondVertex][j] = mapIn[secondVertex][j+1];
            }
            mapIn[secondVertex].erase(mapIn[secondVertex].begin()+mapIn[secondVertex].size()-1);
        }
    }

    for(int i=0;i<mapOut[firstVertex].size();i++)
    {
        if(mapOut[firstVertex][i] == secondVertex)
        {
            for(int j=i;j<mapOut[firstVertex].size();j++)
            {
                mapOut[firstVertex][j] = mapOut[firstVertex][j+1];
            }
            mapOut[firstVertex].erase(mapOut[firstVertex].begin()+mapOut[firstVertex].size()-1);
        }
    }

}

bool Graph::existsVertex(int vertex) {
    map<int,vector<int>>::iterator it;
    for (auto const &element:mapIn)
    {
        if (element.first == vertex)
            return true;
    }
    return false;
}

int Graph::numberOfVertices() {
    return mapIn.size();
}

int Graph::numberOfEdges() {
    return mapEdges.size();
}

map<pair<int, int>, int> Graph::getEdges() {
    return mapEdges;
}

int Graph::getCost(int firstVertex, int secondVertex) {
    if(existsVertex(firstVertex) != true || existsVertex(secondVertex) != true) {
        throw std::invalid_argument("One of the vertices does not exist");
    }
    if(isEdge(firstVertex,secondVertex) != true)
        throw std::invalid_argument("There is no edge between the vertices");
    return mapEdges[make_pair(firstVertex,secondVertex)];
}

bool Graph::isEdge(int firstVertex, int secondVertex) {
    if(existsVertex(firstVertex) != true || existsVertex(secondVertex) != true) {
        throw std::invalid_argument("One of the vertices does not exist");
    }
    for (auto const&edge:mapEdges)
    {
        if(edge.first.first == firstVertex && edge.first.second == secondVertex)
        {
            return true;
        }

    }
    return false;
}

int Graph::getInDegree(int vertex) {
    if(existsVertex(vertex) == false)
        throw std::invalid_argument("Vertex does not exist");
    return mapIn[vertex].size();
}

int Graph::getOutDegree(int vertex) {
    if(existsVertex(vertex) == false)
        throw std::invalid_argument("Vertex does not exist");
    return mapOut[vertex].size();

}

vector<int> Graph::getInboundEdges(int vertex) {
    if(existsVertex(vertex) == false)
        throw std::invalid_argument("Vertex does not exist");
    return mapIn[vertex];
}

vector<int> Graph::getOutboundEdges(int vertex) {
    if(existsVertex(vertex) == false)
        throw std::invalid_argument("Vertex does not exist");
    return mapOut[vertex];
}

Graph::~Graph() {

}

Graph::Graph(int size) {
    int i;
    for(i=0;i<size;i++)
    {
        populateGraphVertices(i);
    }
}

Graph::Graph(){}

vector<int> Graph::getIsolatedVertices() {
    vector<int> returnValue;
    for(auto const &vertex:mapIn)
    {
        if(getInDegree(vertex.first)==0 && getOutDegree(vertex.first)==0)
            returnValue.push_back(vertex.first);
    }
    return returnValue;
}

Graph::Graph(const Graph &initial) {
    mapIn = initial.mapIn;
    mapOut = initial.mapOut;
    mapEdges = initial.mapEdges;

}
