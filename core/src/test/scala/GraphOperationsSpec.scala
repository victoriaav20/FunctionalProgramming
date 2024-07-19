package graph

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class GraphOperationsSpec extends AnyFlatSpec with Matchers {

  // Sample graphs for testing
  val directedGraph = DirectedGraph(Set(1, 2, 3), Set((1, 2), (2, 3)))
  val weightedGraph = WeightedGraph(Set(1, 2, 3), Map((1, 2) -> 1.0, (2, 3) -> 2.0))

  "DFS" should "visit all nodes in depth-first order" in {
    val result = GraphOperations.dfs(directedGraph, 1)
    result should contain theSameElementsAs List(1, 2, 3)
  }

  "BFS" should "visit all nodes in breadth-first order" in {
    val result = GraphOperations.bfs(directedGraph, 1)
    result should contain theSameElementsAs List(1, 2, 3)
  }

  "Topological Sort" should "return a valid ordering of nodes" in {
    val result = GraphOperations.topologicalSort(directedGraph)
    result.get should contain theSameElementsAs List(1, 2, 3) // Use .get because we expect Some(List)
  }

  it should "return None for a cyclic graph" in {
    val cyclicGraph = DirectedGraph(Set(1, 2), Set((1, 2), (2, 1)))
    GraphOperations.topologicalSort(cyclicGraph) shouldBe None
  }

  "Cycle Detection" should "detect a cycle in a graph" in {
    val cyclicGraph = DirectedGraph(Set(1, 2), Set((1, 2), (2, 1)))
    GraphOperations.hasCycle(cyclicGraph) shouldBe true
  }

  it should "not detect a cycle in an acyclic graph" in {
    GraphOperations.hasCycle(directedGraph) shouldBe false
  }

  "Floyd's Algorithm" should "compute shortest paths between all pairs" in {
    val result = GraphOperations.floydWarshall(weightedGraph)
    result(1)(3) shouldBe 3.0 // 1 -> 2 -> 3
  }

  "Dijkstra's Algorithm" should "find the shortest path from a source" in {
    val result = GraphOperations.dijkstra(weightedGraph, 1)
    result(3) shouldBe 3.0 // 1 -> 2 -> 3
    result(2) shouldBe 1.0 // 1 -> 2
  }
}
