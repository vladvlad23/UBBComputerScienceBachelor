from RandomGraphGenerator import RandomGraphGenerator
from copy import deepcopy
class UserInterface:

    def __init__(self,graph):
        self.__graph = graph
        self.__copy = False
        self.__graphCopy = deepcopy(graph)

    def __UIaddVertex(self):
        try:
            vertex = int(input("Please give the index of the vertex"))
        except ValueError:
            raise ValueError("Index must be integer")
        self.__graph.addVertex()

    def __UIaddEdge(self):
        try:
            firstVertex = int(input("Please give first vertex: "))
            secondVertex = int(input("Please give second vertex: "))
            cost = int(input("Please give the cost: "))
            self.__graph.addEdge(firstVertex,secondVertex,cost)
        except ValueError:
            raise Exception("Integers please")

    def __UIremoveVertex(self):
        try:
            vertex = int(input("Please give the vertex to remove: "))
        except ValueError:
            raise ValueError("No. An integer existing in the graph please")
        self.__graph.removeVertex(vertex)

    def __UIremoveEdge(self):
        try:
            firstVertex = int(input("Please give the first vertex: "))
            secondVertex = int(input("Please give the second vertex: "))
        except ValueError:
            raise Exception("Invalid values")
        self.__graph.removeEdge(firstVertex,secondVertex)

    def __UInumberOfVertices(self):
        print("The number of vertices is " + str(self.__graph.numberOfVertices()))

    def __UIisEdge(self):
        try:
            firstVertex = int(input("Please give first vertex: "))
            secondVertex = int(input("Please give second vertex: "))
        except ValueError:
            raise Exception("Invalid input :( ")

        if self.__graph.isEdge(firstVertex,secondVertex):
            print("There is an edge there with the cost of " + str(self.__graph.getCost(firstVertex,secondVertex)))
        else:
            print("There is no edge there.")




    def __UImodifyEdge(self):

        try:
            firstVertex = int(input("Please give the first vertex: "))
            secondVertex = int(input("Please give the second vertex: "))
            cost = int(input("Please give the cost: "))
        except ValueError:
            raise Exception("Integers please")
        self.__graph.modifyEdge(firstVertex,secondVertex,cost)

    def __UIprintGraph(self):
        edges = self.__graph.getEdges()
        for key in edges:
            print(str(key[0]) +  " -> " + str(key[1]) + '(' + str(edges[key]) + ')')

    def __UIgenerateRandomGraph(self):
       # This is in case i will want to add it to file in future
       #filename = input("What is the filename where you want to store the randomly created graph? ")
        try:
            nrVertices = int(input("What is the number of vertices? "))
            nrEdges = int(input("What is the number of edges? "))
        except ValueError:
            raise Exception("Both number of vertices and number of edges must be an integer")

        generator = RandomGraphGenerator(nrVertices,nrEdges)
        self.__graph = generator.generateGraph()
        self.__graphCopy = self.__graph

    def __UIgetInDegree(self):
        try:
            vertex = int(input("Get in degree "))
        except ValueError:
            raise Exception("Sorry :(. It must be an integer")


        print(str(self.__graph.inDegree(vertex)) + " is the in degree of the vertex")


    def __UIgetOutDegree(self):
        try:
            vertex = int(input("Get in degree "))
        except ValueError:
            raise Exception("Sorry :(. It must be an integer")

        print(str(self.__graph.outDegree(vertex))+ " is the out degree of the vertex")

    def __UIshiftBetweenCopyAndOriginal(self):
        temp = deepcopy(self.__graph)
        self.__graph = self.__graphCopy
        self.__graphCopy = temp
        if self.__copy is True:
            self.__copy = False
            print("You are now working with your ORIGINAL graph")
        else:
            self.__copy = True
            print("You are now working with THE COPY of your graph")



    def __UIparseInboundEdges(self):
        try:
            vertex = int(input("Please give the vertex: "))
        except ValueError:
            raise Exception("Sorry :(. It must be an integer")

        inboundDictionary = self.__graph.getInboundEdges(vertex)
        print("The inbound edges are ")
        for vertex,cost in inboundDictionary.items():
            print(str(vertex) + " -> " + str(vertex) + "(" + str(cost) + ")")

    def __UIparseOutboundEdges(self):
        try:
            vertex = int(input("Please give the vertex: "))
        except ValueError:
            raise Exception("Sorry :(. It must be an integer")

        outboundDictionary = self.__graph.getOutboundEdges(vertex)
        print("The outbound edges are ")
        for vertex,cost in outboundDictionary.items():
            print(str(vertex) + " -> " + str(vertex) + "(" + str(cost) + ")")

    def __UIputInFile(self):
        fileName = input("What would you like to be the name of the new file?")
        self.__graph.writeToFile(fileName)

    def __UIbackwardsBFS(self):
        try:
            startVertex = int(input("Please give start vertex"))
            endVertex = int(input("Please give end vertex"))
        except ValueError:
            print("Please give valid vertices")
            return
        pathAndDistance = self.__graph.getShortestBackwardsBFSPath(startVertex,endVertex)
        print("The path is " + ("-".join(str(e) for e in pathAndDistance[0])))
        print("The length is " + str(pathAndDistance[1]))

    def __UIbackwardsDijkstra(self):
        try:
            startVertex = int(input("Please give start vertex"))
            endVertex = int(input("Please give end vertex"))
        except ValueError:
            print("Please give valid vertices")
            return
        dictionaryOfPaths = self.__graph.backwardsDijkstra(startVertex,endVertex)
        path = [startVertex]
        while path[-1] != endVertex:
            path.append(dictionaryOfPaths[0][path[-1]])
        print("The path is: " + ("-".join(str(e) for e in path)))
        print("The cost is: " + str(dictionaryOfPaths[1]))

    def __UITopologicalSortPredecessorAlgorithm(self):
        sorted = self.__graph.predecessorCountingTopologicalSort()
        if sorted is not None:
            print("The topological sort is " + ("-".join(str(e) for e in sorted)))
        else:
            print("The graph is not a DAG")

    def __UIgetLongestDistanceInDAG(self):
        try:
            startVertex = int(input("Please give start vertex"))
            endVertex = int(input("Please give end vertex"))
        except ValueError:
            print("Please give valid vertices")
        distanceAndPath = self.__graph.getLongestDistanceInDAG(startVertex,endVertex)
        if distanceAndPath is not None:
            print("The max distance is " + str(distanceAndPath[0]))
            print("The path is " + ("-".join(str(e) for e in distanceAndPath[1])))
        else:
            print("No path")


    def __UIminimumCostPathHamiltonGraph(self):
        try:
            result = self.__graph.travellingSalesmanProblem(self.__graph.getListOfVertices()[0])
            print("The cost is: " + str(result[0]))
            print("The path is: " + "-".join(str(e) for e in result[1]))
            return
        except KeyError:
            print("The graph is not a Hamiltonian graph \n")



    def startConsoleWithGraph(self):
        help = "Hello, there! What operation would you like to perform? \n " + \
                "0. Exit. \n " + \
                "1. Add vertex \n" + \
                "2. Remove vertex \n" + \
                "3. Number of Vertices\n" + \
                "4. Test if something is edge\n" + \
                "5. Modify an edge\n" + \
                "6. Print the graph\n" + \
                "7. Add an edge\n" \
                "8. Remove an edge\n" + \
                "9. Generate random graph \n" + \
                "10. Get In degree of a node \n" + \
                "11. Get Out degree of a node \n" + \
                "12. Shift between original and copy of graph \n" + \
                "13. Parse inbound edges of a vertex \n" + \
                "14. Parse outbound edges of a vertex \n" + \
                "15. Put graph in file\n" + \
                "16. Find lowest length between 2 vertices using backwards breadth first search \n" + \
                "17. Find lowest cost walk between 2 vertices using backwards Dijkstra algorithm \n" + \
                "18. Topological sort using predecessor algorithm\n" + \
                "19. Get longest distance in DAG\n" + \
                "20. Minimum cost path hamilton graph\n"
        print(help)
        if self.__copy is not True:
            print("I must remind you that you are currently working with your ORIGINAL graph")
        else:
            print("I must remind you that you are currently working with THE COPY of your graph")

        commands = {1: self.__UIaddVertex, 2: self.__UIremoveVertex, 3: self.__UInumberOfVertices, 4: self.__UIisEdge,
                    5: self.__UImodifyEdge, 6: self.__UIprintGraph, 7: self.__UIaddEdge, 8: self.__UIremoveEdge,
                    9: self.__UIgenerateRandomGraph, 10:self.__UIgetInDegree, 11:self.__UIgetOutDegree,
                    12:self.__UIshiftBetweenCopyAndOriginal, 13:self.__UIparseInboundEdges, 14:self.__UIparseOutboundEdges,
                    15:self.__UIputInFile,16:self.__UIbackwardsBFS,17:self.__UIbackwardsDijkstra, 18:self.__UITopologicalSortPredecessorAlgorithm,
                    19:self.__UIgetLongestDistanceInDAG,20: self.__UIminimumCostPathHamiltonGraph}
        while(True):
            try:
                userInput = int(input(" >> "))
            except ValueError:
                print("Invalid input. Sorry. Would you repeat that, please? ")
                self.startConsoleWithGraph()
                return
            if userInput in commands:
                    #try:
                    commands[userInput]()
                    #except Exception as e:
                      #  print(e)
            elif userInput == 0:
                print("Ok. Bye bye")
                return
            else:
                print("Invalid command. Try again: ")

