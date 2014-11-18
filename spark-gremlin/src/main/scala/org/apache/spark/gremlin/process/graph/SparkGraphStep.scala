package org.apache.spark.gremlin.process.graph

import com.tinkerpop.gremlin.process.{Traversal, TraverserGenerator}
import com.tinkerpop.gremlin.process.graph.step.sideEffect.GraphStep
import com.tinkerpop.gremlin.process.util.TraversalMetrics
import com.tinkerpop.gremlin.structure.{Element, Vertex}
import org.apache.spark.gremlin.rdd.{SparkGraphEdgeIterator, SparkGraphVertexIterator}
import org.apache.spark.gremlin.structure.SparkGraph

class SparkGraphStep[E <: Element](traversal: Traversal[_, _], returnClass: Class[E])
  extends GraphStep[E](traversal: Traversal[_, _], returnClass: Class[E]) {

  private var graph: SparkGraph = null

  def this(traversal: Traversal[_, _], returnClass: Class[E], graph: SparkGraph) = {
    this(traversal, returnClass)
    this.graph = graph
  }

  override def generateTraversers(traverserGenerator: TraverserGenerator)
  {
    //if (PROFILING_ENABLED) TraversalMetrics.start(this)
    try {
      this.start = if (classOf[Vertex].isAssignableFrom(this.returnClass)) new SparkGraphVertexIterator(this.graph) else new SparkGraphEdgeIterator(this.graph)
      super.generateTraversers(traverserGenerator)
    }
    catch {
      case e: Exception => {
        throw new IllegalStateException(e.getMessage, e)
      }
    } finally {
      //if (PROFILING_ENABLED) TraversalMetrics.stop(this)
    }
  }
}
