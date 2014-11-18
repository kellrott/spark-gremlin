package org.apache.spark.gremlin.process.graph

import com.tinkerpop.gremlin.process.graph.util.DefaultGraphTraversal
import com.tinkerpop.gremlin.structure.Element
import org.apache.spark.gremlin.structure.SparkGraph
import com.tinkerpop.gremlin.process.graph.step.sideEffect.StartStep


class SparkGraphElementTraversal[A] extends DefaultGraphTraversal[A, A] {
  def this(element: Element, graph: SparkGraph) {
    this()
    this.sideEffects.setGraph(graph)
    this.addStep(new StartStep[AnyRef](this, element))
  }
}

