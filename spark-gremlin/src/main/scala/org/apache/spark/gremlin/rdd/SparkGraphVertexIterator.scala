package org.apache.spark.gremlin.rdd

import org.apache.spark.gremlin.structure.{SparkGraph, SparkGraphVertex}
import org.apache.spark.gremlin.util.RDDIterator
import org.apache.spark.rdd.RDD

class SparkGraphVertexIterator(g : SparkGraph) extends RDDIterator[SparkGraphVertex, SparkGraphVertex] with SparkGraphElementIterator[SparkGraphVertex]
 {
  graph = g
  override def elementClass(): Class[_] = classOf[SparkGraphVertex]

  override def getRDD(): RDD[SparkGraphVertex] = graph.graph.vertices.map(_._2)

  override def process(in: SparkGraphVertex): SparkGraphVertex = in
}