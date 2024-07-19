package graph

import graph.Graph

object GraphViz {
  implicit class GraphVizOps[A](graph: Graph[A]) {
    def toGraphViz: String = {
      val edges = graph.edges.foldLeft("") { (acc, edge) =>
        acc + s"  ${edge._1} -> ${edge._2};\n"
      }
      s"digraph G {\n$edges}"
    }
  }
}
