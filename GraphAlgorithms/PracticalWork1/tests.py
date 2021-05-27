from Graph import Graph
import unittest
class Test(unittest.TestCase):

    def testCreationOfGraph(self):
        graph = Graph()

    def testAdditionOfVertices(self):
        graph = Graph()
        graph.addVertex(0)
        graph.addVertex(1)
        graph.addVertex(2)

        assert graph.numberOfVertices() == 3

        graph.addVertex(3)
        assert graph.numberOfVertices() == 4

    def testRemovalOfVertices(self):
        graph = Graph()
        graph.addVertex(0)
        graph.addVertex(1)
        graph.addVertex(2)
        graph.addEdge(0,1,2)
        graph.addEdge(0,2,3)
        graph.addEdge(1,0,4)
        graph.addEdge(1,1,5)
        graph.addEdge(1,2,6)

        graph.removeVertex(1)
        assert graph.numberOfVertices() == 2
        graph.removeVertex(0)
        assert graph.numberOfVertices() == 1

    def testAdditionOfEdges(self):
        graph = Graph()
        graph.addVertex(0)
        graph.addVertex(1)
        graph.addVertex(2)
        graph.addEdge(0,1,2)
        graph.addEdge(0,2,3)
        graph.addEdge(1,0,4)
        graph.addEdge(1,1,5)
        graph.addEdge(1,2,6)
        assert graph.isEdge(0,1)
        assert graph.isEdge(0,2)
        assert graph.isEdge(1,0)
        assert graph.isEdge(1,1)
        assert graph.isEdge(1,2)

    def testRemovalOfEdges(self):
        graph = Graph()
        graph.addVertex(0)
        graph.addVertex(1)
        graph.addVertex(2)
        graph.addEdge(0,1,3)
        graph.addEdge(0,2,4)
        assert graph.isEdge(0,1)
        graph.removeEdge(0,1)
        assert graph.isEdge(0,1) is not True
        graph.removeEdge(0,2)
        assert graph.isEdge(0,2) is not True

    def testOutboundEdges(self):
        graph = Graph()
        graph.addVertex(0)
        graph.addVertex(1)
        graph.addVertex(2)
        graph.addEdge(0,1,2)
        graph.addEdge(0,2,3)
        graph.addEdge(1,0,4)
        graph.addEdge(1,1,5)
        graph.addEdge(1,2,6)
        assert(graph.getInboundEdges(0) == {1:4})
        assert(graph.getOutboundEdges(0) == {1:2,2:3})
        assert(graph.getInboundEdges(1) == {0:2,1:5})
        assert(graph.getOutboundEdges(1) == {0:4,1:5,2:6})
        assert(graph.getInboundEdges(2) == {0:3,1:6})
        assert(graph.getOutboundEdges(2) == {})


    def testShortestPathBackwardsBFS(self):
        graph = Graph()
        graph.addVertex(0)
        graph.addVertex(1)
        graph.addVertex(2)
        graph.addVertex(3)
        graph.addVertex(4)
        graph.addVertex(5)
        graph.addVertex(6)
        graph.addEdge(1,2,1)
        graph.addEdge(2,3,1)
        graph.addEdge(2,4,1)
        graph.addEdge(3,4,1)
        graph.addEdge(3,5,1)
        graph.addEdge(5,6,1)
        graph.addEdge(4,6,1)

        pathAndDistance = graph.getShortestBackwardsBFSPath(1,6)
        assert(pathAndDistance[0][0] == 1 and pathAndDistance[0][1] == 2 and pathAndDistance[0][2] == 4 and pathAndDistance[0][3] == 6)
        assert(pathAndDistance[1] == 3)

    def testDijkstraBackwards(self):
        graph = Graph()
        graph.addVertex(0)
        graph.addVertex(1)
        graph.addVertex(2)
        graph.addVertex(3)
        graph.addVertex(4)
        graph.addVertex(5)
        graph.addVertex(6)
        graph.addEdge(1,2,3)
        graph.addEdge(2,3,2)
        graph.addEdge(2,4,6)
        graph.addEdge(3,4,5)
        graph.addEdge(3,5,2)
        graph.addEdge(5,6,2)
        graph.addEdge(4,6,1)

        list = graph.backwardsDijkstra(1,6)
        assert (list[1] == 2)
        assert (list[2] == 3)
        assert (list[3] == 5)
        assert (list[5] == 6)

        graph3 = Graph()
        graph3.addVertex(1)
        graph3.addVertex(2)
        graph3.addVertex(3)
        graph3.addVertex(4)
        graph3.addVertex(5)
        graph3.addVertex(6)
        graph3.addVertex(7)
        graph3.addEdge(1,3,5)
        graph3.addEdge(1,4,6)
        graph3.addEdge(1,2,3)
        graph3.addEdge(2,3,2)
        graph3.addEdge(3,5,6)
        graph3.addEdge(3,6,3)
        graph3.addEdge(3,4,2)
        graph3.addEdge(3,7,7)
        graph3.addEdge(4,6,9)
        graph3.addEdge(5,7,2)
        graph3.addEdge(6,7,1)
        graph3.addEdge(6,5,5)
        list = graph3.backwardsDijkstra(1,7)
        assert(list[0][1]==3)
        assert(list[0][3]==6)
        assert(list[0][6]==7)







