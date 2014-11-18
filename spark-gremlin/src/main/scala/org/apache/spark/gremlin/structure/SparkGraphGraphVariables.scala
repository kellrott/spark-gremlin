package org.apache.spark.gremlin.structure

import java.util.Optional

import com.tinkerpop.gremlin.structure.Graph
import com.tinkerpop.gremlin.structure.util.{StringFactory, GraphVariableHelper}


class SparkGraphGraphVariables extends Graph.Variables {
  private final val variables: java.util.Map[String, AnyRef] = new java.util.HashMap[String, AnyRef]

  def keys: java.util.Set[String] = {
    return this.variables.keySet
  }

  def get[R](key: String): Optional[R] = {
    return Optional.ofNullable(this.variables.get(key).asInstanceOf[R])
  }

  def remove(key: String) {
    this.variables.remove(key)
  }

  def set(key: String, value: AnyRef) {
    GraphVariableHelper.validateVariable(key, value)
    this.variables.put(key, value)
  }

  override def toString: String = {
    return StringFactory.graphVariablesString(this)
  }
}
