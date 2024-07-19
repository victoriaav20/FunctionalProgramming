

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import graph.DirectedGraph

class DirectedGraphSpec extends AnyFlatSpec with Matchers {

  "A DirectedGraph" should "correctly add edges" in {
    val graph = DirectedGraph[Int](Set(1, 2, 3), Set.empty)
    graph.addEdge(1, 2).edges should contain theSameElementsAs Set((1, 2))
  }

  it should "correctly remove edges" in {
    val graph = DirectedGraph[Int](Set(1, 2, 3), Set((1, 2), (2, 3)))
    graph.removeEdge(1, 2).edges should contain theSameElementsAs Set((2, 3))
  }

  it should "return neighbors of a vertex" in {
    val graph = DirectedGraph[Int](Set(1, 2, 3), Set((1, 2), (2, 3)))
    graph.neighbors(1) should contain theSameElementsAs Set(2)
  }
}
