package org.apache.spark.gremlin.structure.io.graphson

import com.tinkerpop.gremlin.structure.Direction
import com.tinkerpop.gremlin.structure.Edge
import com.tinkerpop.gremlin.structure.Vertex
import com.tinkerpop.gremlin.structure.io.graphson.GraphSONReader
import com.tinkerpop.gremlin.structure.util.detached.DetachedEdge
import com.tinkerpop.gremlin.structure.util.detached.DetachedVertex
import com.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph
import com.tinkerpop.gremlin.tinkergraph.structure.TinkerVertex
import org.apache.hadoop.io.NullWritable
import org.apache.hadoop.mapreduce.InputSplit
import org.apache.hadoop.mapreduce.RecordReader
import org.apache.hadoop.mapreduce.TaskAttemptContext
import org.apache.hadoop.mapreduce.lib.input.LineRecordReader
import java.io.ByteArrayInputStream
import java.io.IOException
import java.io.InputStream
import java.util.function.Function

import org.apache.spark.gremlin.structure.SparkGraphVertex

class GraphSONRecordReader extends RecordReader[NullWritable, SparkGraphVertex] {
  private final var lineRecordReader: LineRecordReader = new LineRecordReader
  private final var graphSONReader: GraphSONReader = GraphSONReader.build.create
  private var vertex: SparkGraphVertex = null

  @throws(classOf[IOException])
  def initialize(genericSplit: InputSplit, context: TaskAttemptContext) {
    this.lineRecordReader.initialize(genericSplit, context)
  }

  //deal with the java functions in GraphSONReader
  implicit def toJavaFunction[A, B](f: Function1[A, B]) = new java.util.function.Function[A, B] {
    override def apply(a: A): B = f(a)
  }

  @throws(classOf[IOException])
  def nextKeyValue: Boolean = {
    if (!this.lineRecordReader.nextKeyValue) return false
    val g: TinkerGraph = TinkerGraph.open
    val vertexMaker: Function[DetachedVertex, Vertex] = { detachedVertex:DetachedVertex => DetachedVertex.addTo(g, detachedVertex) }
    val edgeMaker: Function[DetachedEdge, Edge] = { detachedEdge:DetachedEdge => DetachedEdge.addTo(g, detachedEdge) }
    var v: TinkerVertex = null
    try {
      val in: InputStream = new ByteArrayInputStream(this.lineRecordReader.getCurrentValue.getBytes)
      try {
        v = this.graphSONReader.readVertex(in, Direction.BOTH, vertexMaker, edgeMaker).asInstanceOf[TinkerVertex]
      } finally {
        if (in != null) in.close()
      }
    }
    this.vertex = new SparkGraphVertex(v, null)
    return true
  }

  def getCurrentKey: NullWritable = {
    return NullWritable.get
  }

  def getCurrentValue: SparkGraphVertex = {
    return this.vertex
  }

  @throws(classOf[IOException])
  def getProgress: Float = {
    return this.lineRecordReader.getProgress
  }

  @throws(classOf[IOException])
  def close {
    this.lineRecordReader.close
  }
}
