#include <iostream>
#include <fstream>
#include <sstream>
#include "testFile.h"
#include "UserInterface.h"
int main() {
    testIfGraphWorks();
    std::cout<<"Tests passed :)\n";
    ifstream file("graph1k.txt");
    if(!file) {
        printf("Error when reading graph from file");
        return 0;
    }
    int numberOfVertices,numberOfEdges,x,y,z;
    file>>numberOfVertices>>numberOfEdges;
    Graph graph(numberOfVertices);
    for(int i=0;i<numberOfEdges;i++)
    {
        if(i%10000==0)
            std::cout<<"Line number "<<i<<"\n";
        file>>x>>y>>z;
        graph.populateGraphEdges(x,y,z);
    }
    file.close();
    std::cout<<"Graph sucessfully read from file"<<endl;

    UserInterface interface(graph);
    interface.startConsole();


    return 0;
}