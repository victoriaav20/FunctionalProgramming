package graph

trait Graph[A] {
  def vertices: Set[A]
  def edges: Set[(A, A)]
  def neighbors(vertex: A): Set[A]
  def addEdge(from: A, to: A): Graph[A]
  def removeEdge(from: A, to: A): Graph[A]
}

case class DirectedGraph[A](vertices: Set[A], edges: Set[(A, A)]) extends Graph[A] {
  def neighbors(vertex: A): Set[A] = edges.collect { case (`vertex`, v2) => v2 }
  def addEdge(from: A, to: A): DirectedGraph[A] = copy(edges = edges + ((from, to)))
  def removeEdge(from: A, to: A): DirectedGraph[A] = copy(edges = edges - ((from, to)))
}

case class WeightedGraph[A](vertices: Set[A], edgeWeights: Map[(A, A), Double]) extends Graph[A] {
  def neighbors(vertex: A): Set[A] = edgeWeights.collect {
    case ((`vertex`, v2), _) => v2
    case ((v2, `vertex`), _) => v2
  }.toSet
  
  def addEdge(from: A, to: A): WeightedGraph[A] = addEdge(from, to, 1.0) 
  
  def addEdge(from: A, to: A, weight: Double): WeightedGraph[A] =
    copy(edgeWeights = edgeWeights + (((from, to) -> weight)))
  
  def removeEdge(from: A, to: A): WeightedGraph[A] = copy(edgeWeights = edgeWeights - ((from, to)))
  
  override def edges: Set[(A, A)] = edgeWeights.keySet
}


case class UndirectedGraph[A](vertices: Set[A], edges: Set[(A, A)]) extends Graph[A] {
  def neighbors(vertex: A): Set[A] = edges.collect {
    case (`vertex`, v2) => v2
    case (v2, `vertex`) => v2
  }
  
  def addEdge(from: A, to: A): UndirectedGraph[A] =
    copy(edges = edges + ((from, to)) + ((to, from)))
  
  def removeEdge(from: A, to: A): UndirectedGraph[A] =
    copy(edges = edges - ((from, to)) - ((to, from)))
}

