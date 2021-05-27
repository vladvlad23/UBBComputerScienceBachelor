from heapq import *
import itertools
class Graph:
    def __init__(self):
        # the dictionary that will define all the edges going IN and OUT of an element
        self.__dictionaryIn = dict()
        self.__dictionaryOut = dict()
        self.__dictionaryEdges = dict()


    def getListOfVertices(self):
        return list(self.__dictionaryIn.keys())

    def addVertex(self,index):
        '''
        Function will add a vertex with the given index in the dictionaries representing the graph
        :param index: the index of the vertex to be added
        :post condition: the vertex will be added in the dictionaries
        :throws - Value error if index already exists
        '''
        if self.existsVertex(index):
            raise ValueError("Index already exists")
        else:
            self.__dictionaryIn[index] = dict()
            self.__dictionaryOut[index] = dict()


    def removeVertex(self,index):
        '''
        Function will remove a vertex from the graph by removing it from the dictionaries and the edges
        :param index: the vertex to be removed
        :post condition: the vertex will be removed from the dictionaries and all edges cut off
        :throws - Value error if index already exists
        '''

        if self.existsVertex(index) is not True:
            raise Exception("There is no such vertex in the graph")

        #we create new dictionaries not containing anything regarding the vertex to be removed

        newDictionary = dict()
        for vertexStart,vertexDestination in self.__dictionaryOut.items(): # cut all edges
            if index != vertexDestination and index !=vertexStart:
                newDictionary[vertexStart] = vertexDestination
        self.__dictionaryOut = newDictionary

        newDictionary = dict()
        for edge in self.__dictionaryEdges: #remove edges from edges dictionary
            if edge[1] != index and edge[0] != index:
                newDictionary[edge] = self.__dictionaryEdges[edge]
        self.__dictionaryEdges = newDictionary

        newDictionary = dict()
        for vertexDestination,vertexStart in self.__dictionaryIn.items(): # cut all edges
            if index != vertexDestination and index !=vertexStart:
                newDictionary[vertexDestination] = vertexStart

        self.__dictionaryIn = newDictionary

    def removeEdge(self,firstVertex,secondVertex):
        '''
        Function will remove an edge from the edge dictionary
        :param firstVertex = the origin of the edge
        :param secondVertex = the destination of the edge
        :post condition = the edge dictionary won't contain the edge anymore and the dictionaries of ins and outs won't either
        :throws Exception if there is no edge (which in turn will throw exception is one of the vertices is missing)

        '''
        if self.isEdge(firstVertex,secondVertex) is not True:
            raise Exception("No edge here. Sorry")
        self.__dictionaryIn[secondVertex].pop(firstVertex)
        self.__dictionaryOut[firstVertex].pop(secondVertex)
        self.__dictionaryEdges.pop((firstVertex,secondVertex))

    def addEdge(self,firstVertex,secondVertex,cost):
        '''
        Function will add an edge with a given cost
        :param firstVertex = the origin of the edge
        :param secondVertex = the destination of the edge
        :param cost = the cost of the new edge
        :post condition = the edge dictionary will contain the new edge and the dictionaries of ins and outs will as well
        :throw Exception if one of the vertices does no exist
        '''
        if (self.existsVertex(firstVertex) and self.existsVertex(secondVertex)) is not True:
            raise Exception("One of the vertices does not exist. Sorry")
        if self.isEdge(firstVertex,secondVertex) is True:
            raise Exception("There already is an edge there. Don't be edgy")

        #This means the firstVertex key dictionary has a dictionary with key to secondVertex and the cost is
        #the value between the 2 of them
        self.__dictionaryOut[firstVertex][secondVertex] = cost
        self.__dictionaryIn[secondVertex][firstVertex] = cost
        self.__dictionaryEdges[(firstVertex,secondVertex)] = cost

    def modifyEdge(self,firstVertex,secondVertex,newCost):
        '''
        Function will add an edge with a given cost
        :param firstVertex = the origin of the edge
        :param secondVertex = the destination of the edge
        :param cost = the cost of the new edge
        :post condition = the edge dictionary will modify the edge cost
        :throw Exception is edge does not exist (which in turn will raise exception if vertices do not exist)
        '''
        if self.isEdge(firstVertex,secondVertex) is not True:
            raise Exception("There is no edge")
        self.__dictionaryEdges[(firstVertex,secondVertex)] = newCost


    def existsVertex(self,candidate):
        '''
        Function will return true if a certain vertex exists
        :param candidate: the vertex to be checked
        :return: true if the vertex with the given index is there and false otherwise
        '''
        if candidate in self.__dictionaryIn:
            return True
        return False

    def numberOfVertices(self):
        '''
        :return: the number of vertices in the graph
        '''
        return len(self.__dictionaryIn.keys())

    def numberOfEdges(self):
        '''
        Function will return the number of edges in the graph (by accessing the dictionary edges keys)
        :return: the number of edges in the graph
        '''
        return len(self.__dictionaryEdges.keys())

    def getEdges(self):
        '''
        Function will return the edges of the graph.
        :return: the dictionary edges of the graph
        '''
        return self.__dictionaryEdges

    def getCost(self,firstVertex,secondVertex):
        '''
        Function will get the cost of an edge between 2 vertices
        :param firstVertex: the origin of the edge
        :param secondVertex: the destination of the edge
        :throw exception if there is no edge( which in turn will raise exception if vertices do not exist)
        :return: the cost of the given edge in the graph
        '''
        if self.isEdge(firstVertex,secondVertex) is not True:
            raise Exception("No edge when getting cost")

        return self.__dictionaryEdges[(firstVertex,secondVertex)]


    def isEdge(self,firstVertex,secondVertex):
        '''
        Function will check if there exists an edge between 2 vertexes
        :param firstVertex: one of the vertices
        :param secondVertex: the other vertix
        :return:True if it does and False otherwise
        '''
        if self.existsVertex(firstVertex) is not True or self.existsVertex(secondVertex) is not True:
            raise Exception("One of the vertices does not exist")
        if (firstVertex,secondVertex) in self.__dictionaryEdges:
            return True
        return False

    def inDegree(self,vertex):
        '''
        Function will return the in degree (the number of edges that go "in" the graph)
        :param vertex: the vertex to compute de degree of
        :throw = function will raise exception if there is no vertex
        :return: the degree of the "in" graph
        '''
        if self.existsVertex(vertex) is not True:
            raise Exception("No such vertex")

        return len(self.__dictionaryIn[vertex])

    def outDegree(self,vertex):
        '''
        Function will return the out degree (the number of edges that go "out" of the graph)
        :param vertex: the vertex to compute de degree of
        :throw = function will raise exception if there is no vertex
        :return: the degree of the "out" graph
        '''
        if self.existsVertex(vertex) is not True:
            raise Exception("No such vertex")

        return len(self.__dictionaryOut[vertex])

    def getInboundEdges(self,vertex):
        '''
        Function will return a list of inbound edges of the given vertex
        :param vertex: the vertex whose inbound edges should be returned
        :return: a python list of inbound edges
        :throw: value error if there is no such vertex
        '''
        if self.existsVertex(vertex) is not True:
            raise ValueError("There is no such vertex")
        return self.__dictionaryIn[vertex]

    def getOutboundEdges(self,vertex):
        '''
        Function will return a list of outbound edges of the given vertex
        :param vertex: the vertex whose outbound edges should be returned
        :return: a python list of outbound edges
        :throw: value error if there is no such vertex
        '''
        if self.existsVertex(vertex) is not True:
            raise ValueError("There is no such vertex")
        return self.__dictionaryOut[vertex]

    def predecessorCountingTopologicalSort(self):
        sorted = []
        queue = []
        count = {}
        for vertex in self.__dictionaryIn.keys():
            count[vertex] = self.inDegree(vertex)
            if count[vertex] == 0: #no predecessors
                queue.append(vertex)

        while len(queue)!=0: #while the queue is not empty
            vertex = queue.pop()
            sorted.append(vertex)
            for neighbour in self.getOutboundEdges(vertex):
                count[neighbour] = count[neighbour] - 1
                if count[neighbour] == 0:
                    queue.append(neighbour)
        return None if (len(sorted)<len(self.__dictionaryIn)) else sorted #ternary operator

    def getLongestDistanceInDAG(self,start,end):
        if self.existsVertex(start) is not True or self.existsVertex(end) is not True:
            raise ValueError("One of the vertices does not exist\n")
        sorted = self.predecessorCountingTopologicalSort()
        if sorted is None:
            return None
        parent = {}
        distances = dict.fromkeys(sorted,None)
        distances[start] = 0
        for vertex in sorted:
            for neighbor in self.getOutboundEdges(vertex):
                if distances[vertex] is not None:
                    if distances[neighbor] is None or distances[neighbor] < distances[vertex] + self.getCost(vertex,neighbor):
                        parent[neighbor] = vertex
                        distances[neighbor] = distances[vertex] + self.getCost(vertex,neighbor)

        if distances[end] is None:
            return None
        path = []
        vertex = end
        while vertex!=start:
            path.append(vertex)
            vertex = parent[vertex]
        path.append(start)
        path.reverse()
        return (distances[end], path)


    def getShortestBackwardsBFSPath(self,startIndex,endIndex):
        if self.existsVertex(startIndex) is not True or self.existsVertex(endIndex) is not True:
            raise ValueError("You must give existing vertices as parameters")
        queue = [endIndex]
        next = {}
        distances = {}
        visited = []
        distances[endIndex] = 0
        while len(queue)!=0:
            currentVertex = queue.pop(0)
            for neighbor in self.__dictionaryIn[currentVertex]:
                if not (neighbor in visited):
                    queue.append(neighbor)
                    visited.append(neighbor)
                    distances[neighbor] = distances[currentVertex]+1
                    next[neighbor] = currentVertex
                    if neighbor == startIndex:
                        path = []
                        index = startIndex
                        while index != endIndex:
                            path.append(index)
                            index = next[index]
                        path.append(endIndex)
                        return (path, distances[startIndex])
        raise Exception("we can't reach that")

    def backwardsDijkstra(self,startVertex,endVertex):
        if self.existsVertex(startVertex) is not True or self.existsVertex(endVertex) is not True:
            raise ValueError("You must give existing vertices as parameters")
        priorityQueue = []
        prevList = {}
        distances = {}
        heappush(priorityQueue,(0,endVertex))
        distances[endVertex] = 0
        while len(priorityQueue)!=0:
            currentElement = heappop(priorityQueue)[1]
            for element in self.__dictionaryIn[currentElement]:
                if element not in distances or \
                    distances[currentElement] + self.getCost(element,currentElement) < distances[element]:
                    distances[element] = distances[currentElement] + self.getCost(element,currentElement)
                    heappush(priorityQueue,(distances[element],element))
                    prevList[element] = currentElement
            if currentElement == startVertex:
                return (prevList,distances[currentElement])

        raise ValueError("there is no path")

    def readFromFile(self,fileName):
        '''
        Function will read from the given file the graph infomration and will store it in the instance of this class
        :param fileName: the file with the information which should be read
        :return: True if the graph has been read sucessful or False oterwise (Also will print the exception)
        '''
        try:
            i=1
            file = open(fileName, "r")
            firstLine = file.readline().strip().split(" ")

            # firstLine is an array. first element will be number of vertices and second the number of edges
            for i in range(int(firstLine[0])):  # add vertices
                self.addVertex(i)

            for i in range(int(firstLine[1])):
                line = file.readline().strip().split(" ")
                print("Line number", i)
                i+=1
                # line is a list where the first 2 elements are the vertices and the last is the cost of the edge
                self.addEdge(int(line[0]), int(line[1]), int(line[2]))

            file.close()
        except IOError as e:
            print(e)
            return False

        return True


    def __getAllSubsets(self, listToConvert):
        '''
        Function will get all the subsets of the given list.
        :param listToConvert:
        :return:
        '''
        setFinalList = [()]
        for i in range(len(listToConvert)):
            setFinalList.extend(tuple(itertools.combinations(listToConvert, i + 1)))
        return setFinalList

    def travellingSalesmanProblem(self,startVertex):
        '''
        Function will find the minnimium cost hamilton path.
        :return:
        '''
        dist = {} # distance dict
        vertexList = list(self.__dictionaryIn.keys())
        vertexList.remove(startVertex) #list of vertices without the first one as it is not needed
        parent = {}
        setVertex = self.__getAllSubsets(vertexList) # complexity 2^n
        vertexList.append(startVertex)
        for everySet in setVertex: #Going through all sets
            for vertex in vertexList: #Computing distance to all vertices using the given set
                if len(everySet)==0: #empty set so we set the distance to startVertex - vertex edge
                    if self.isEdge(startVertex,vertex):
                        dist[(vertex,())] = self.getCost(startVertex,vertex)
                        parent[(vertex, everySet)] = startVertex
                if vertex in everySet: #if the vertex is already in the set, we do nothing
                    continue
                for element in everySet:
                    index = everySet.index(element)
                    preceedingSet = everySet[:index] + everySet[index+1:] #this removes the element from tuple
                    if self.isEdge(element,vertex): #updating distance
                        if (vertex,everySet) not in dist or \
                                dist[element,preceedingSet] + self.getCost(element,vertex) < dist[(vertex,everySet)]:
                            dist[(vertex,everySet)] = dist[(element,preceedingSet)] + self.getCost(element,vertex)
                            parent[(vertex,everySet)] = element

        path = [startVertex]
        preceedingSet = everySet
        vertex = parent[(startVertex,preceedingSet)]
        while vertex !=startVertex: #path building
            path.append(vertex)
            index = preceedingSet.index(vertex)
            preceedingSet = preceedingSet[:index] + preceedingSet[index+1:]
            vertex = parent[(vertex, preceedingSet)]

        path.append(startVertex)
        path.reverse()


        return (dist[(startVertex,everySet)],path)




    def writeToFile(self,fileName):
        '''
        Function will write to the given file the graph information for future use
        :param fileName: the file where the information should be written
        :param fileName: the file where the graph should be read
        '''
        file = open(fileName,"w")
        nrVertices = self.numberOfVertices()
        nrEdges = self.numberOfEdges()
        file.write(str(self.numberOfVertices()) + " " + str(self.numberOfEdges()) + "\n")
        for key,elem in self.__dictionaryEdges.items():
            file.write(str(key[0]) + " " + str(key[1]) + " " + str(elem) + "\n")
        file.close()



