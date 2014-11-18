package org.apache.spark.gremlin.structure

import java.util

import com.tinkerpop.gremlin.structure.Edge.Iterators

import collection.JavaConverters._

import com.tinkerpop.gremlin.structure.{Property, Vertex, Direction, Edge}
import com.tinkerpop.gremlin.structure.util.wrapped.WrappedEdge
import com.tinkerpop.gremlin.tinkergraph.structure.{TinkerElement, TinkerEdge}

/**

 */
class SparkGraphEdge extends SparkGraphElement with Edge with Edge.Iterators with WrappedEdge[TinkerEdge] with Serializable {
  override var tinkerElement: TinkerElement = _
  override var spark_graph: SparkGraph = _

  override def iterators(): Iterators = ???

  override def hiddenPropertyIterator[V](propertyKeys: String*): util.Iterator[Property[V]] = ???

  override def propertyIterator[V](propertyKeys: String*): util.Iterator[Property[V]] = ???

  override def vertexIterator(direction: Direction): util.Iterator[Vertex] = ???

  override def getBaseEdge: TinkerEdge = ???
}
