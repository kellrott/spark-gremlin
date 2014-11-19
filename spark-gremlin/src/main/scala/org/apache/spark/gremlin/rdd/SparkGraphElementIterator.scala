package org.apache.spark.gremlin.rdd

import org.apache.spark.gremlin.structure.{SparkGraph, SparkGraphElement}

trait SparkGraphElementIterator[E <: SparkGraphElement] extends Iterator[E] {
  protected var graph: SparkGraph = null


}
