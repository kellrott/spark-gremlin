package org.apache.spark.gremlin.structure

import java.util.Iterator
import com.tinkerpop.gremlin.giraph.structure.SparkGraphProperty

import collection.JavaConverters._

import com.tinkerpop.gremlin.process.graph.GraphTraversal
import com.tinkerpop.gremlin.structure.util.wrapped.WrappedVertexProperty
import com.tinkerpop.gremlin.structure.{Property, VertexProperty, Vertex, Element}
import com.tinkerpop.gremlin.structure.util.ElementHelper
import com.tinkerpop.gremlin.tinkergraph.structure.{TinkerProperty, TinkerVertexProperty}
import org.apache.spark.gremlin.process.graph.SparkGraphElementTraversal

class SparkGraphVertexProperty[V] extends VertexProperty[V] with VertexProperty.Iterators with WrappedVertexProperty[TinkerVertexProperty[V]] {
  private var tinkerVertexProperty: TinkerVertexProperty[V] = null
  private var sparkVertex: SparkGraphVertex = null

  def this(tinkerVertexProperty: TinkerVertexProperty[V], sparkVertex: SparkGraphVertex) = {
    this()
    this.tinkerVertexProperty = tinkerVertexProperty
    this.sparkVertex = sparkVertex
  }

  override def start: GraphTraversal[VertexProperty[_], VertexProperty[_]] = {
    return new SparkGraphElementTraversal[VertexProperty[_]](this, this.sparkVertex.graph.asInstanceOf[SparkGraph])
  }

  def id: AnyRef = {
    return this.tinkerVertexProperty.id
  }

  def value: V = {
    return this.tinkerVertexProperty.value
  }

  def key: String = {
    return this.tinkerVertexProperty.key
  }

  def remove {
    this.tinkerVertexProperty.remove
  }

  def isHidden: Boolean = {
    return this.tinkerVertexProperty.isHidden
  }

  def isPresent: Boolean = {
    return this.tinkerVertexProperty.isPresent
  }

  override def property[U](key: String): Property[U] =
  {
    return this.tinkerVertexProperty.property(key)
  }

  def property[U](key: String, value: U): Property[U] = {
    throw Element.Exceptions.propertyAdditionNotSupported
  }

  override def equals(other: Any): Boolean =
  {
    return ElementHelper.areEqual(this, other)
  }

  override def hashCode: Int =
  {
    return this.tinkerVertexProperty.hashCode
  }

  override def toString: String = {
    return this.tinkerVertexProperty.toString
  }

  def element: Vertex = {
    return this.sparkVertex
  }

  def getBaseVertexProperty: TinkerVertexProperty[V] = {
    return this.tinkerVertexProperty
  }

  def iterators: VertexProperty.Iterators = {
    return this
  }

  def propertyIterator[U](propertyKeys: String*): Iterator[Property[U]] = {
    return getBaseVertexProperty.iterators().
      propertyIterator[U](propertyKeys:_*).asScala.
      map( x => new SparkGraphProperty(x.asInstanceOf[TinkerProperty[V]], this) ).
      toIterator.asInstanceOf[Iterator[Property[U]]]
  }

  def hiddenPropertyIterator[U](propertyKeys: String*): Iterator[Property[U]] = {
    return getBaseVertexProperty.iterators().
      hiddenPropertyIterator[U](propertyKeys:_*).asScala.
      map( x => new SparkGraphProperty(x.asInstanceOf[TinkerProperty[V]], this) ).
      toIterator.asInstanceOf[Iterator[Property[U]]]
  }
}
