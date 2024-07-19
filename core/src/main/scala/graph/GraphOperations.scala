package graph

import scala.annotation.tailrec
import scala.collection.immutable.Queue

object GraphOperations {

  // Depth First Search (DFS)
  def dfs[A](graph: Graph[A], start: A): List[A] = {
    @tailrec
    def dfsRec(stack: List[A], visited: Set[A], result: List[A]): List[A] = {
      stack match {
        case Nil => result.reverse
        case current :: rest =>
          if (visited.contains(current)) {
            dfsRec(rest, visited, result)
          } else {
            val neighbors = graph.neighbors(current).toList
            val newStack = neighbors.filterNot(visited.contains) ++ rest
            dfsRec(newStack, visited + current, current :: result)
          }
      }
    }
    dfsRec(List(start), Set.empty, Nil)
  }

  // Breadth First Search (BFS)
  def bfs[A](graph: Graph[A], start: A): List[A] = {
    @tailrec
    def bfsRec(queue: Queue[A], visited: Set[A], result: List[A]): List[A] = {
      if (queue.isEmpty) result.reverse
      else {
        val (current, remainingQueue) = queue.dequeue
        if (visited.contains(current)) bfsRec(remainingQueue, visited, result)
        else {
          val neighbors = graph.neighbors(current).filterNot(visited.contains)
          bfsRec(remainingQueue.enqueueAll(neighbors), visited + current, current :: result)
        }
      }
    }
    bfsRec(Queue(start), Set.empty, Nil)
  }

  // Topological Sort
  def topologicalSort[A](graph: DirectedGraph[A]): Option[List[A]] = {
    def visit(node: A, visited: Set[A], tempMarked: Set[A], order: List[A]): Option[(Set[A], List[A])] = {
      if (tempMarked.contains(node)) None // Cycle detected
      else if (!visited.contains(node)) {
        val newTempMarked = tempMarked + node
        val result = graph.neighbors(node).foldLeft[Option[(Set[A], List[A])]](Some((visited, order))) {
          case (Some((v, o)), neighbor) => visit(neighbor, v, newTempMarked, o)
          case (None, _) => None
        }
        result.map { case (newVisited, newOrder) => (newVisited + node, node :: newOrder) }
      } else Some((visited, order))
    }

    graph.vertices.foldLeft[Option[(Set[A], List[A])]](Some((Set.empty, Nil))) {
      case (Some((visited, order)), node) if !visited.contains(node) => visit(node, visited, Set.empty, order)
      case (result, _) => result
    }.map(_._2.reverse)
  }

  // Cycle Detection
  def hasCycle[A](graph: DirectedGraph[A]): Boolean = {
    def visit(node: A, visited: Set[A], tempMarked: Set[A]): Boolean = {
      if (tempMarked.contains(node)) true // Cycle detected
      else if (visited.contains(node)) false
      else {
        val newTempMarked = tempMarked + node
        graph.neighbors(node).exists(neighbor => visit(neighbor, visited, newTempMarked)) || {
          visited + node
          false
        }
      }
    }

    graph.vertices.exists(node => visit(node, Set.empty, Set.empty))
  }

  // Floyd's Algorithm
  def floydWarshall[A](graph: WeightedGraph[A]): Map[A, Map[A, Double]] = {
    val vertices = graph.vertices.toList
    val dist = vertices.map { v =>
      v -> vertices.map { u =>
        u -> (if (graph.edgeWeights.contains((v, u))) graph.edgeWeights((v, u)) else Double.PositiveInfinity)
      }.toMap
    }.toMap

    // Implementing the Floyd-Warshall algorithm
    val updatedDist = vertices.foldLeft(dist) { (currentDist, k) =>
      vertices.foldLeft(currentDist) { (tempDist, i) =>
        vertices.foldLeft(tempDist) { (distMap, j) =>
          val viaK = distMap(i)(k) + distMap(k)(j)
          if (viaK < distMap(i)(j)) {
            distMap.updated(i, distMap(i).updated(j, viaK))
          } else {
            distMap
          }
        }
      }
    }

    updatedDist
  }

  // Dijkstra's Algorithm
  def dijkstra[A](graph: WeightedGraph[A], start: A): Map[A, Double] = {
    import scala.collection.mutable

    val distances = mutable.Map(graph.vertices.map(v => v -> Double.PositiveInfinity).toSeq: _*)
    distances(start) = 0

    val priorityQueue = mutable.PriorityQueue[(A, Double)]()(Ordering.by(-_._2))
    priorityQueue.enqueue((start, 0))

    while (priorityQueue.nonEmpty) {
      val (current, currentDist) = priorityQueue.dequeue()

      if (currentDist <= distances(current)) {
        graph.neighbors(current).foreach { neighbor =>
          val edgeWeight = graph.edgeWeights.get((current, neighbor)).getOrElse(0.0)
          val newDist = currentDist + edgeWeight
          if (newDist < distances(neighbor)) {
            distances(neighbor) = newDist
            priorityQueue.enqueue((neighbor, newDist))
          }
        }
      }
    }

    distances.toMap
  }
}