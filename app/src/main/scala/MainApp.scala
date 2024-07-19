package app

import zio._
import graph._
import graph.GraphOperations._
import graph.GraphViz.GraphVizOps

object MainApp extends ZIOAppDefault {
  def run = myAppLogic

  val myAppLogic = for {
    _ <- Console.printLine("Welcome to the Graph Application!")
    graph <- buildInitialGraph
    _ <- runGraphOperations(graph)
  } yield ()

  def buildInitialGraph: UIO[WeightedGraph[Int]] = ZIO.succeed {
    WeightedGraph(
      Set(1, 2, 3, 4),
      Map(
        (1, 2) -> 1.0,
        (2, 3) -> 1.0,
        (3, 4) -> 1.0,
        (4, 1) -> 1.0
      )
    )
  }

  def runGraphOperations(graph: WeightedGraph[Int]): ZIO[Any, Throwable, Unit] = for {
    _ <- Console.printLine(s"Initial graph: ${graph.toGraphViz}")

    // Depth First Search
    dfsResult <- ZIO.attempt(dfs(graph, 1)).orDie
    _ <- Console.printLine(s"DFS result: $dfsResult")

    // Breadth First Search
    bfsResult <- ZIO.attempt(bfs(graph, 1)).orDie
    _ <- Console.printLine(s"BFS result: $bfsResult")

    // Topological Sort (Note: This might not make sense for a cyclic graph)
    topSortResult <- ZIO.attempt(topologicalSort(DirectedGraph(graph.vertices, graph.edges))).orDie
    _ <- Console.printLine(s"Topological sort result: $topSortResult")

    // Cycle Detection (Note: This needs a DirectedGraph)
    cycleDetected <- ZIO.attempt(hasCycle(DirectedGraph(graph.vertices, graph.edges))).orDie
    _ <- Console.printLine(s"Cycle detected: $cycleDetected")

    // Floyd's Algorithm
    floydResult <- ZIO.attempt(floydWarshall(graph)).orDie
    _ <- Console.printLine(s"Shortest paths (Floyd's Algorithm): $floydResult")

    // Dijkstra's Algorithm
    dijkstraResult <- ZIO.attempt(dijkstra(graph, 1)).orDie
    _ <- Console.printLine(s"Shortest paths from node 1 (Dijkstra's Algorithm): $dijkstraResult")
  } yield ()
}