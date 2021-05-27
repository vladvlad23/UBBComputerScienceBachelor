//
// Created by Vlad on 23-Mar-19.
//

#include "UserInterface.h"
#

UserInterface::~UserInterface() {

}

void UserInterface::UIaddVertex()
{
    int vertex;
    std::cout<<"Please give the index of the vertex: ";
    opValid = false;
    while(!opValid)
    {
        std::cin >> vertex;
        if (std::cin.fail())
        {
            std::cout << "Invalid input\n";
            std::cin.clear();
            std::cin.ignore();
        }
        else
            opValid=true;
    }
    try
    {
        repository.addVertex(vertex);
    }
    catch(std::invalid_argument &exception)
    {
        std::cerr<<exception.what()<<endl;
        return;
    }
    std::cout<<"\n Vertex added sucessfully\n";
}

void UserInterface::UIaddEdge()
{
    int firstVertex,secondVertex,cost;
    std::cout<<"Please give the first vertex: ";
    opValid = false;
    while(!opValid)
    {
        std::cin >> firstVertex;
        if (std::cin.fail())
        {
            std::cout << "Invalid input\n";
            std::cin.clear();
            std::cin.ignore();
        }
        else
            opValid=true;
    }

    std::cout<<"Please give the second vertex: ";
    opValid = false;
    while(!opValid)
    {
        std::cin >> secondVertex;
        if (std::cin.fail())
        {
            std::cout << "Invalid input\n";
            std::cin.clear();
            std::cin.ignore();
        }
        else
            opValid=true;
    }

    std::cout<<"Please give the cost: ";

    opValid = false;
    while(!opValid)
    {
        std::cin >> cost;
        if (std::cin.fail())
        {
            std::cout << "Invalid input\n";
            std::cin.clear();
            std::cin.ignore();
        }
        else
            opValid=true;
    }

    try
    {
        repository.addEdge(firstVertex, secondVertex, cost);
    }
    catch(std::invalid_argument &exception)
    {
        std::cerr<<exception.what()<<endl;
        return;
    }
    std::cout<<"\nEdge added sucessfully \n";
}

void UserInterface::UIremoveVertex()
{
    int vertex;
    std::cout<<"Please give the index of the vertex: ";

    opValid = false;
    while(!opValid)
    {
        std::cin >> vertex;
        if (std::cin.fail())
        {
            std::cout << "Invalid input\n";
            std::cin.clear();
            std::cin.ignore();
        }
        else
            opValid=true;
    }

    try
    {
        repository.removeVertex(vertex);
    }
    catch(std::invalid_argument &exception)
    {
        std::cerr<<exception.what()<<endl;
        return;
    }
    std::cout<<"\n Vertex removed sucessfully\n";
}

void UserInterface::UIremoveEdge()
{
    int firstVertex,secondVertex;
    std::cout<<"Please give the first vertex: ";

    opValid = false;
    while(!opValid)
    {
        std::cin >> firstVertex;
        if (std::cin.fail())
        {
            std::cout << "Invalid input\n";
            std::cin.clear();
            std::cin.ignore();
        }
        else
            opValid=true;
    }

    std::cout<<"Please give the second vertex: ";
    opValid = false;
    while(!opValid)
    {
        std::cin >> secondVertex;
        if (std::cin.fail())
        {
            std::cout << "Invalid input\n";
            std::cin.clear();
            std::cin.ignore();
        }
        else
            opValid=true;
    }

    try
    {
        repository.removeEdge(firstVertex,secondVertex);
    }
    catch(std::invalid_argument &exception)
    {
        std::cerr<<exception.what()<<endl;
        return;
    }
    std::cout<<"\n Edge removed sucessfully\n";
}

void UserInterface::UInumberOfVertices()
{
    std::cout<<"The number of vertices is "<<repository.numberOfVertices()<<endl;
}

void UserInterface::UIisEdge()
{
    int firstVertex,secondVertex;
    std::cout<<"Please give the first vertex: ";

    opValid = false;
    while(!opValid)
    {
        std::cin >> firstVertex;
        if (std::cin.fail())
        {
            std::cout << "Invalid input\n";
            std::cin.clear();
            std::cin.ignore();
        }
        else
            opValid=true;
    }

    std::cout<<"Please give the second vertex: ";
    opValid = false;
    while(!opValid)
    {
        std::cin >> secondVertex;
        if (std::cin.fail())
        {
            std::cout << "Invalid input\n";
            std::cin.clear();
            std::cin.ignore();
        }
        else
            opValid=true;
    }

    try
    {
        if (repository.isEdge(firstVertex, secondVertex))
        {
            std::cout << "There is an edge there with cost: " << repository.getCost(firstVertex, secondVertex) << endl;
        } else
            std::cout << "There is no edge there\n";
    }
        catch(exception &e)
        {
            std::cout<<e.what()<<"\n";
        }

}

void UserInterface::UImodifyEdge()
{
    int firstVertex,secondVertex,newCost;
    std::cout<<"Please give the first vertex: ";

    opValid = false;
    while(!opValid)
    {
        std::cin >> firstVertex;
        if (std::cin.fail())
        {
            std::cout << "Invalid input\n";
            std::cin.clear();
            std::cin.ignore();
        }
        else
            opValid=true;
    }

    std::cout<<"Please give the second vertex: ";
    opValid = false;
    while(!opValid)
    {
        std::cin >> secondVertex;
        if (std::cin.fail())
        {
            std::cout << "Invalid input\n";
            std::cin.clear();
            std::cin.ignore();
        }
        else
            opValid=true;
    }

    std::cout<<"Please give the new cost of the edge: ";
    opValid = false;
    while(!opValid)
    {
        std::cin >> newCost;
        if (std::cin.fail())
        {
            std::cout << "Invalid input\n";
            std::cin.clear();
            std::cin.ignore();
        }
        else
            opValid=true;
    }

    try
    {
        repository.modifyEdge(firstVertex,secondVertex,newCost);
    }
    catch(std::invalid_argument &exception)
    {
        std::cout<<exception.what()<<endl;
    }



}

void UserInterface::UIprintGraph() {
    std::cout<<"The edges are: \n";
    for(auto const &edge:repository.getEdges())
    {
        std::cout << edge.first.first << " - > " << edge.first.second << " (" << edge.second << ")\n";
    }
    std::cout<<"\nThe isolated vertices are: \n";
    vector<int> list = repository.getIsolatedVertices();
    for(auto const&isolatedVertex:list)
    {
        std::cout<< isolatedVertex<<" ";
    }
    std::cout<<endl;
}

void UserInterface::UIgetInDegree() {
    int vertex;
    std::cout<<"Please give the vertex: ";
    opValid = false;
    while(!opValid)
    {
        std::cin >> vertex;
        if (std::cin.fail())
        {
            std::cout << "Invalid input\n";
            std::cin.clear();
            std::cin.ignore();
        }
        else
            opValid=true;
    }

    try
    {
        std::cout<<"The grade is "<<repository.getInDegree(vertex);
    }
    catch(std::invalid_argument &exception)
    {
        std::cerr<<exception.what()<<endl;
    }
}

void UserInterface::UIgetOutDegree() {
    int vertex;
    std::cout<<"Please give the vertex: ";
    opValid = false;
    while(!opValid)
    {
        std::cin >> vertex;
        if (std::cin.fail())
        {
            std::cout << "Invalid input\n";
            std::cin.clear();
            std::cin.ignore();
        }
        else
            opValid=true;
    }

    try
    {
        std::cout<<"The grade is "<<repository.getOutDegree(vertex);
    }
    catch(std::invalid_argument &exception)
    {
        std::cerr<<exception.what()<<endl;
    }
}

void UserInterface::UIshiftBetweenCopyAndOriginal() {

    isCopy = !isCopy;
    swap(repository,copy);

}

void UserInterface::UIparseInboundEdges() {
    int vertex;
    vector<int>inboundEdges;
    std::cout<<"Please insert the vertex ";
    opValid = false;
    while(!opValid)
    {
        std::cin >> vertex;
        if (std::cin.fail())
        {
            std::cout << "Invalid input\n";
            std::cin.clear();
            std::cin.ignore();
        }
        else
            opValid=true;
    }

    try
    {
        inboundEdges = repository.getInboundEdges(vertex);
        std::cout<<"The inbound edges of the vertex are ";
        for(auto const &element:inboundEdges)
        {
            std::cout<<" "<<element<<" ";
        }
        std::cout<<endl;
    }
    catch(std::invalid_argument &exception)
    {
        std::cerr<<exception.what()<<endl;
    }
}

void UserInterface::UIparseOutboundEdges() {
    int vertex;
    vector<int>outboundEdges;
    std::cout<<"Please insert the vertex ";
    opValid = false;
    while(!opValid)
    {
        std::cin >> vertex;
        if (std::cin.fail())
        {
            std::cout << "Invalid input\n";
            std::cin.clear();
            std::cin.ignore();
        }
        else
            opValid=true;
    }

    try
    {
        outboundEdges = repository.getOutboundEdges(vertex);
        std::cout<<"The outbound edges of the vertex are ";
        for(auto const &element:outboundEdges)
        {
            std::cout<<" "<<element<<" ";
        }
        std::cout<<endl;
    }
    catch(std::invalid_argument &exception)
    {
        std::cerr<<exception.what()<<endl;
    }
}

void UserInterface::startConsole() {
    std::cout<<"Hello, there! What operation would you like to perform? \n " \
                "0. Exit. \n " \
                "1. Add vertex \n" \
                "2. Remove vertex \n" \
                "3. Number of Vertices\n" \
                "4. Test if something is edge\n" \
                "5. Modify an edge\n"  \
                "6. Print the graph\n"  \
                "7. Add an edge\n" \
                "8. Remove an edge\n"  \
                "9. Get In degree of a node \n"  \
                "10. Get Out degree of a node \n"  \
                "11. Shift between original and copy of graph \n"  \
                "12. Parse inbound edges of a vertex \n"  \
                "13. Parse outbound edges of a vertex \n";

    int command;
    while(true) {
        if(isCopy)
        {
            std::cout<<"I must remind you that you are working with A COPY of your graph\n";
        }
        else
            std::cout<<"I must remind you that you are working with your ORIGINAL graph\n";
        opValid = false;
        while(!opValid)
        {
            std::cout<<"Please issue a command: ";
            std::cin >> command;
            if (std::cin.fail())
            {
                std::cout << "Invalid input\n";
                std::cin.clear();
                std::cin.ignore();
            }
            else
                opValid=true;
        }

        switch (command) {
            case 0:
                return;
            case 1:
                UIaddVertex();
                break;
            case 2:
                UIremoveVertex();
                break;
            case 3:
                UInumberOfVertices();
                break;
            case 4:
                UIisEdge();
                break;
            case 5:
                UImodifyEdge();
                break;
            case 6:
                UIprintGraph();
                break;
            case 7:
                UIaddEdge();
                break;
            case 8:
                UIremoveEdge();
                break;
            case 9:
                UIgetInDegree();
                break;
            case 10:
                UIgetOutDegree();
                break;
            case 11:
                UIshiftBetweenCopyAndOriginal();
                break;
            case 12:
                UIparseInboundEdges();
                break;
            case 13:
                UIparseOutboundEdges();
                break;
        }
    }

}

UserInterface::UserInterface(const Graph &repository) {
    this->repository = repository;
    this->copy = repository;
}
