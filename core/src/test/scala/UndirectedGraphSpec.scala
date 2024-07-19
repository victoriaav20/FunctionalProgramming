package graph

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class UndirectedGraphSpec extends AnyFlatSpec with Matchers {

  "An UndirectedGraph" should "correctly add edges" in {
    val graph = UndirectedGraph[Int](Set.empty, Set.empty) // Initialize with empty vertices
    val updatedGraph = graph.addEdge(1, 2)
    updatedGraph.edges should contain theSameElementsAs Set((1, 2), (2, 1)) // Expect both directions
  }

  it should "correctly remove edges" in {
    val graph = UndirectedGraph[Int](Set.empty, Set((1, 2), (2, 1))) // Initialize with existing edges
    val updatedGraph = graph.removeEdge(1, 2)
    updatedGraph.edges should not contain ((1, 2)) // After removal, should not contain (1, 2)
    updatedGraph.edges should not contain ((2, 1)) // After removal, should not contain (2, 1)
  }

  it should "return neighbors of a vertex" in {
    val graph = UndirectedGraph[Int](Set.empty, Set((1, 2), (2, 3))) // Initialize with existing edges
    graph.neighbors(1) should contain theSameElementsAs Set(2)
    graph.neighbors(2) should contain theSameElementsAs Set(1, 3)
  }
}
