version of java used: java 11.0.21


compile the project : sbt run
run the main application: sbt "app/run"
run tests: sbt run

## Usage

example of how to create a graph and run an algorithm:

```scala
import graph._
import graph.GraphOperations._

// Create a weighted graph
val graph = WeightedGraph(
Set(1, 2, 3, 4),
Map(
 (1, 2) -> 1.0,
 (2, 3) -> 1.0,
 (3, 4) -> 1.0,
 (4, 1) -> 1.0
)
)

// Run Depth-First Search
val dfsResult = dfs(graph, 1)
println(s"DFS result: $dfsResult")

// Run Dijkstra's Algorithm
val dijkstraResult = dijkstra(graph, 1)
println(s"Shortest paths from node 1: $dijkstraResult")