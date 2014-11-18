package org.apache.spark.gremlin.structure

import java.util

import com.tinkerpop.gremlin.structure.util.wrapped.WrappedVertex

import collection.JavaConverters._
import com.tinkerpop.gremlin.structure._
import com.tinkerpop.gremlin.tinkergraph.structure.{TinkerVertexProperty, TinkerElement, TinkerVertex}


class SparkGraphVertex(vertex:TinkerVertex, graph:SparkGraph) extends SparkGraphElement(vertex, graph)
with Vertex with Vertex.Iterators with WrappedVertex[TinkerVertex] {

  override def addEdge(label: String, inVertex: Vertex, keyValues: AnyRef*): Edge = {
    throw Vertex.Exceptions.edgeAdditionsNotSupported()
  }

  override def getBaseVertex: TinkerVertex = {
    return this.tinkerElement.asInstanceOf[TinkerVertex]
  }

  override def iterators: Vertex.Iterators = {
    return this
  }

  //override def vertexIterator(direction:Direction, labels:String*) : Iterator[Vertex] = {
  //return StreamFactory.stream(vertex.iterators().vertexIterator(direction, labels:_*)).map(v => graph.v(.id())).iterator();
  //}
  override var tinkerElement: TinkerElement = _
  override var spark_graph: SparkGraph = _

  override def property[V](key: String): VertexProperty[V] = {
    val vertexProperty: VertexProperty[V] = getBaseVertex.property[V](key)
    return if (vertexProperty.isPresent) new SparkGraphVertexProperty[V]((this.tinkerElement.asInstanceOf[Vertex]).property(key).asInstanceOf[TinkerVertexProperty[V]], this) else VertexProperty.empty[V]
  }

  override def property[V](key: String, value: V): VertexProperty[V] = {
    throw Element.Exceptions.propertyAdditionNotSupported
  }

  override def hiddenPropertyIterator[V](propertyKeys: String*): util.Iterator[VertexProperty[V]] = ???

  override def edgeIterator(direction: Direction, edgeLabels: String*): util.Iterator[Edge] = ???

  override def propertyIterator[V](propertyKeys: String*): util.Iterator[VertexProperty[V]] = ???

  override def vertexIterator(direction: Direction, edgeLabels: String*): util.Iterator[Vertex] = ???
}
