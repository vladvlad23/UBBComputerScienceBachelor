# Parallelizing techniques 2 - The return of the Thread


# Goal

The goal of this lab is to implement a simple but non-trivial parallel algorithm.
Requirement

Solve the problem below:

Given a directed graph, find a Hamiltonean cycle, if one exists. Use multiple threads to parallelize the search.

The documentation will describe:

    the algorithms,
    the synchronization used in the parallelized variants,
    the performance measurements 
    
    
## Computer Specification

    * CPU: Intel Core i7
    * RAM: 16 GB
    * System type: 64-bit

### Hamiltonian graph

A Hamiltonian graph may be defined as 
 
If there exists a closed walk in the connected graph that
 visits every vertex of the graph exactly once. (except starting vertex) 
 without repeating the edges, then such a graph is called as a Hamiltonian graph.

### Idea

We are going to be using backtracking to generate all possible paths.

This is a good way to conclude whether parallelism is fit for the problem as we can have multiple threads for multiple nodes thus leading to parallel computations. 

### Example

Consider the following graph with nodes 1,2,3,4,5 and the edges

1 - 2

2 - 3
 
3 - 4
 
4 - 5

5 - 1

You can see we have a closed walk 1-2-3-4-5-1 thus this graph is hamiltonian

By taking the children of the first node, following the children of the 2nd etc it results eventually that we have a walk containing everything necessary.

### Generating hamiltonian graphs

The idea i have used to generate these graphs is the following:

1. Generate a number of nodes
2. Link each node with the next one 
3. Link the last one with the first one
4. Generate some random edges to not have a trivial graph


### Tests made

I have generated graphs of up to 700 nodes for 8 threads (max number according to CPU manufacturer).

 | Nodes | Time |
|-------|------|
| 100   | 11ms |
| 300   | 18ms |
| 500   | 21ms |
| 700   | 25ms |

## Conclusion

Multithreading is a useful tool in such parallel algorithm implementation.
