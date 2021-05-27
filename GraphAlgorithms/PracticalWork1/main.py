from Graph import Graph
from UserInterface import UserInterface


graph = Graph()
if graph.readFromFile("HamiltonGraph2.txt")==True:
    print(" ###### Graph has been read from file ######")
    console = UserInterface(graph)
    console.startConsoleWithGraph()

else:
    print(" !!!!!! Error when reading from file. Check file. !!!!!!")



