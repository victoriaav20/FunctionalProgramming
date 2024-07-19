package graph
import zio.json._

object GraphJson {
  // Encoder and decoder for Map[(A, A), Double]
  implicit def mapEncoder[A: JsonEncoder]: JsonEncoder[Map[(A, A), Double]] = {
    JsonEncoder[Map[String, Double]].contramap { map =>
      map.map { case ((k1, k2), v) =>
        (s"${k1.toJson},${k2.toJson}", v)
      }
    }
  }

  implicit def mapDecoder[A: JsonDecoder]: JsonDecoder[Map[(A, A), Double]] = {
    JsonDecoder[Map[String, Double]].mapOrFail { map =>
      map.toList.foldLeft[Either[String, Map[(A, A), Double]]](Right(Map.empty)) {
        case (Right(acc), (key, value)) =>
          key.split(",", 2) match {
            case Array(k1Str, k2Str) =>
              for {
                k1 <- JsonDecoder[A].decodeJson(k1Str)
                k2 <- JsonDecoder[A].decodeJson(k2Str)
              } yield acc + (((k1, k2), value))
            case _ => Left(s"Invalid key format: $key")
          }
        case (Left(err), _) => Left(err)
      }
    }
  }


  implicit def directedGraphEncoder[A: JsonEncoder]: JsonEncoder[DirectedGraph[A]] =
    DeriveJsonEncoder.gen[DirectedGraph[A]]
  implicit def directedGraphDecoder[A: JsonDecoder]: JsonDecoder[DirectedGraph[A]] =
    DeriveJsonDecoder.gen[DirectedGraph[A]]


  implicit def weightedGraphEncoder[A: JsonEncoder]: JsonEncoder[WeightedGraph[A]] =
    DeriveJsonEncoder.gen[WeightedGraph[A]]
  implicit def weightedGraphDecoder[A: JsonDecoder]: JsonDecoder[WeightedGraph[A]] =
    DeriveJsonDecoder.gen[WeightedGraph[A]]


  implicit def undirectedGraphEncoder[A: JsonEncoder]: JsonEncoder[UndirectedGraph[A]] =
    DeriveJsonEncoder.gen[UndirectedGraph[A]]
  implicit def undirectedGraphDecoder[A: JsonDecoder]: JsonDecoder[UndirectedGraph[A]] =
    DeriveJsonDecoder.gen[UndirectedGraph[A]]
}