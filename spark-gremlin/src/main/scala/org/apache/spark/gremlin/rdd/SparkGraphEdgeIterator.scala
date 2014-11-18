package org.apache.spark.gremlin.rdd

import java.io.IOException

import com.tinkerpop.gremlin.process.util.FastNoSuchElementException
import org.apache.spark.gremlin.structure.{SparkGraphEdge, SparkGraph, SparkGraphVertex}
import org.apache.spark.gremlin.util.RDDIterator
import org.apache.spark.rdd.RDD

class SparkGraphEdgeIterator(g : SparkGraph) extends RDDIterator[SparkGraphEdge, SparkGraphEdge] with SparkGraphElementIterator[SparkGraphEdge]
{
  graph = g
  override def elementClass(): Class[_] = classOf[SparkGraphEdge]

  override def getRDD(): RDD[SparkGraphEdge] = graph.graph.edges.map(_.attr)

  override def process(in: SparkGraphEdge): SparkGraphEdge = in
}