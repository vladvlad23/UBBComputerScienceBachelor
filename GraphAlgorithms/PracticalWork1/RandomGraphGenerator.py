from Graph import Graph
from random import randint

class RandomGraphGenerator():

    def __init__(self,numberOfVertices,numberOfEdges):
        if numberOfVertices*(numberOfVertices-1) < numberOfEdges:
            raise Exception("not possible to generate graph. Please less edges or more vertices");

        self.__numberOfVertices = numberOfVertices
        self.__numberOfEdges = numberOfEdges
        self.__graph = Graph()

    def generateGraph(self):

        #adding the number of vertices
        for i in range(self.__numberOfVertices):
            self.__graph.addVertex(i)

        #adding the edges
        for i in range(self.__numberOfEdges):
            while self.__graph.numberOfEdges()!=self.__numberOfEdges:
                #we add edges until the number of edges is satisfactory
                #the try block is in case we try to add an edge that is already there. We just ignore and try again
                try:
                    self.__graph.addEdge(randint(0,self.__numberOfVertices-1),randint(0,self.__numberOfVertices-1),randint(0,100))
                except Exception as e:
                    pass
        return self.__graph
