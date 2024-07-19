import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import graph._

class WeightedGraphSpec extends AnyFlatSpec with Matchers {

  "A WeightedGraph" should "correctly add edges with default weight" in {
    val graph = WeightedGraph(Set(1, 2, 3), Map.empty[(Int, Int), Double])
    val updatedGraph = graph.addEdge(1, 2)
    updatedGraph.edgeWeights should contain key ((1, 2))
  }

  it should "correctly add edges with custom weight" in {
    val graph = WeightedGraph(Set(1, 2, 3), Map.empty[(Int, Int), Double])
    val updatedGraph = graph.addEdge(1, 2, 2.5)
    updatedGraph.edgeWeights should contain key ((1, 2))
    updatedGraph.edgeWeights((1, 2)) shouldEqual 2.5
  }

  it should "correctly remove edges" in {
    val graph = WeightedGraph(Set(1, 2, 3), Map((1, 2) -> 1.0, (2, 3) -> 2.0))
    val updatedGraph = graph.removeEdge(1, 2)
    updatedGraph.edgeWeights should not contain key ((1, 2))
  }

  it should "return neighbors of a vertex" in {
    val graph = WeightedGraph(Set(1, 2, 3), Map((1, 2) -> 1.0, (2, 3) -> 2.0))
    graph.neighbors(1) shouldEqual Set(2)
  }
}
