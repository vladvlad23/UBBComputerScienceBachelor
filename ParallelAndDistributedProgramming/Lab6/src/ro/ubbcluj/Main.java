package ro.ubbcluj;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {

    public static void main(String[] args) {
//        stressTest();
        hamiltonianDemonstration();
//        nonHamiltonianDemonstration();
    }

    public static void hamiltonianDemonstration(){
        Graph graph = generateHamiltonianGraph(5);
        try {
            if(testHamiltonian(graph)){
                System.out.println("************All ok***********");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void nonHamiltonianDemonstration(){
        Graph graph = generateNonHamiltonianGraph(5);
        try {
            if(!testHamiltonian(graph)){
                System.out.println("************All ok***********");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void stressTest(){
        try {
            for(int i=0;i<1000;i++) {
                Graph graph = generateHamiltonianGraph(50);
                if(testHamiltonian(graph)){
                    System.out.println("************All ok***********");
                }
                else{
                    System.out.println("************NOK***********");
                    break;
                }
            }
            for(int i=0;i<1000;i++) {
                Graph graph = generateNonHamiltonianGraph(50);
                if(!testHamiltonian(graph)){
                    System.out.println("************All ok***********");
                }
                else{
                    System.out.println("************NOK***********" + i);
                    testHamiltonian(graph);
                    break;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static Graph generateHamiltonianGraph(int graphSize) {
        Graph graph = new Graph(graphSize);
        for (int i = 1; i < graphSize; i++) {
            graph.addNode(graph.nodeList.get(i - 1), graph.nodeList.get(i));
        }

        graph.addNode(graph.nodeList.get(graphSize - 1), graph.nodeList.get(0));

        Random randomEdge = new Random();
        for (int i = 1; i < graphSize / 2; i++) {
            graph.addNode(
                    graph.nodeList.get(randomEdge.nextInt(graphSize - 1)),
                    graph.nodeList.get(randomEdge.nextInt(graphSize - 1))
            );
        }

        return graph;
    }

    private static Graph generateNonHamiltonianGraph(int graphSize) {
        Graph graph = new Graph(graphSize);
        for (int i = 1; i < graphSize; i++) {
            graph.addNode(graph.nodeList.get(i - 1), graph.nodeList.get(i));
        }

        Random randomEdge = new Random();
        for (int i = 1; i < graphSize / 2; i++) {
            graph.addNode(
                    graph.nodeList.get(randomEdge.nextInt(graphSize - 1)),
                    graph.nodeList.get(randomEdge.nextInt(graphSize - 1))
            );
        }
        graph.nodeList.get(graphSize-1).setChildren(new ArrayList<>());

        return graph;
    }

    private static boolean testHamiltonian(Graph graph) throws InterruptedException {
        System.out.println("For the graph of size " + graph.graphSize + " we have started execution" );
        Instant firstTime = Instant.now();
        ExecutorService threadPool = Executors.newFixedThreadPool(8);

        Lock lock = new ReentrantLock();
        List<Node> result = new ArrayList<>();

//        threadPool.submit(new VisitTask(graph.getRoot(), graph.getRoot(), result, lock, graph.graphSize));
        graph.getRoot().getChildren().forEach(child -> threadPool.execute(new VisitTask(graph.getRoot(), child, result, lock, graph.graphSize)));
        threadPool.shutdown();
        threadPool.awaitTermination(10, TimeUnit.SECONDS);

        boolean isHamiltonian = (result.size() == graph.graphSize + 1) && containsAllNodes(graph.getAllNodes(), result, graph.getRoot());
        Instant secondTime = Instant.now();
        System.out.println("Time spent: " + Duration.between(firstTime, secondTime).toMillis() + " miliseconds ");
        System.out.println("And the graph is " + (isHamiltonian? "hamiltonian" : "not hamiltonian."));
        return isHamiltonian;
    }

    private static boolean containsAllNodes(List<Node> nodeList, List<Node> resultPath, Node root) {
        for (Node node : nodeList) {
            if (!isValidNodeInResult(resultPath, root, node)) {
                return false;
            }
        }
        return true;
    }

    private static boolean isValidNodeInResult(List<Node> resultPath, Node root, Node node) {
        return node != root &&
                resultPath.stream().filter(resultNode -> resultNode == node).count() == 1 || //if normal node and 1 appearance
                (node == root &&  // if root node and 2 appearances
                        resultPath.stream().filter(resultNode -> resultNode == node).count() == 2);
    }
}
