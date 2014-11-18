package com.tinkerpop.gremlin.giraph.structure

import com.tinkerpop.gremlin.structure.Element
import com.tinkerpop.gremlin.structure.Property
import com.tinkerpop.gremlin.structure.util.ElementHelper
import com.tinkerpop.gremlin.structure.util.wrapped.WrappedProperty
import com.tinkerpop.gremlin.tinkergraph.structure.TinkerProperty
import java.io.Serializable

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
class SparkGraphProperty[V] extends Property[V] with WrappedProperty[TinkerProperty[V]] {
  private final var tinkerProperty: TinkerProperty[V] = null
  private final var sparkElement: Element = null

  def this(tinkerProperty: TinkerProperty[V], sparkElement: Element) {
    this()
    this.tinkerProperty = tinkerProperty
    this.sparkElement = sparkElement
  }

  def isPresent: Boolean = {
    return this.tinkerProperty.isPresent
  }

  def value: V = {
    return this.tinkerProperty.value
  }

  def isHidden: Boolean = {
    return this.tinkerProperty.isHidden
  }

  def getBaseProperty: TinkerProperty[V] = {
    return this.tinkerProperty
  }

  def key: String = {
    return this.tinkerProperty.key
  }

  def remove {
    this.tinkerProperty.remove
  }

  def element: Element = {
    return this.sparkElement
  }

  override def equals(other: Any): Boolean = {
    return ElementHelper.areEqual(this, other)
  }

  override def hashCode: Int = {
    return this.tinkerProperty.hashCode
  }

  override def toString: String = {
    return this.tinkerProperty.toString
  }
}
