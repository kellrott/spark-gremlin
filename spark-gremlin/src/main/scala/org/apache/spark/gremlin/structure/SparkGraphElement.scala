package org.apache.spark.gremlin.structure

import com.tinkerpop.gremlin.structure._
import com.tinkerpop.gremlin.tinkergraph.structure.TinkerElement
import com.tinkerpop.gremlin.structure.util.ElementHelper
import com.tinkerpop.gremlin.structure.Element.Iterators
import java.util


abstract class SparkGraphElement() extends Element with Serializable {
  var tinkerElement : TinkerElement
  var spark_graph : SparkGraph

  def this(tinkerElement:TinkerElement, graph:SparkGraph) = {
    this()
    this.tinkerElement = tinkerElement
    this.spark_graph = graph
  }

  override def graph() : Graph = {
    return this.graph
  }

  override def id() : java.lang.Object = {
    return this.tinkerElement.id()
  }

  override def label() : String = {
    return this.tinkerElement.label()
  }

  override def remove() = {
    if (this.tinkerElement.isInstanceOf[Vertex])
      throw Vertex.Exceptions.vertexRemovalNotSupported()
    else
      throw Edge.Exceptions.edgeRemovalNotSupported()
  }

  override def property[V](key: String) : Property[V] = {
    return this.tinkerElement.property(key)
  }

  override def property[V](key: String, value: V ): Property[V] = {
    throw Element.Exceptions.propertyAdditionNotSupported();
  }

  override def equals(obj:Any) : Boolean = {
    return ElementHelper.areEqual(this, obj)
  }

  override def hashCode() : Int = {
    return this.tinkerElement.hashCode();
  }

  override def toString() : String = {
    return this.tinkerElement.toString()
  }

  override def keys(): util.Set[String] = {
    return this.tinkerElement.keys()
  }

  override def hiddenKeys(): util.Set[String] = {
    return this.tinkerElement.hiddenKeys()
  }

  override def value[V](key: String): V = {
    return this.tinkerElement.value(key)
  }

  override def value[V](key: String, orElse: V): V = {
    return this.tinkerElement.value(key, orElse)
  }
}
