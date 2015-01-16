package org.apache.spark.gremlin.structure


import java.util.Optional

import com.tinkerpop.gremlin.process.computer.GraphComputer
import com.tinkerpop.gremlin.process.graph.GraphTraversal
import com.tinkerpop.gremlin.process.graph.util.DefaultGraphTraversal
import org.apache.commons.configuration.{BaseConfiguration, Configuration}
import org.apache.spark.gremlin.process.graph.SparkGraphStep

import collection.JavaConverters._

import org.apache.spark.{SparkContext, graphx}

import com.tinkerpop.gremlin.structure.Graph.Exceptions
import com.tinkerpop.gremlin.structure.{Graph, Vertex, Transaction, Edge}
import com.tinkerpop.gremlin.process.computer.util.GraphComputerHelper
import org.apache.spark.gremlin.process.computer.SparkGraphComputer

object SparkGraph {
  def open: SparkGraph = {
    return SparkGraph.open(null)
  }

  def open(configuration: Configuration): SparkGraph = {
    return new SparkGraph(Optional.ofNullable(configuration).orElse(EMPTY_CONFIGURATION))
  }

  private val EMPTY_CONFIGURATION: Configuration = {
    new BaseConfiguration() {
      this.setProperty(Graph.GRAPH, classOf[SparkGraph].getName())
    }
  }
}

//@Graph.OptIn(Graph.OptIn.SUITE_PROCESS_STANDARD)
@Graph.OptIn(Graph.OptIn.SUITE_PROCESS_COMPUTER)
class SparkGraph(val sc : SparkContext) extends Graph {

  var graph : graphx.Graph[SparkGraphVertex,SparkGraphEdge] = null
  var graphVariables : SparkGraphGraphVariables = null
  var config : SparkGraphConfiguration = new SparkGraphConfiguration()

  private def this(configuration: Configuration) {
    this(new SparkContext("local", "SparkGraph"))
    this.config = new SparkGraphConfiguration(configuration)
    //this.configuration = new SparkConfiguration(configuration)
    //this.variables = new GiraphGraphVariables
  }

  override def addVertex(keyValues: AnyRef*): Vertex = {
    throw Exceptions.vertexAdditionsNotSupported
  }

  override def V(): GraphTraversal[Vertex, Vertex] = {
    val traversal: GraphTraversal[Vertex, Vertex] = new DefaultGraphTraversal[Vertex, Vertex](this)
    return traversal.addStep(new SparkGraphStep[Vertex](traversal, classOf[Vertex], this))
  }

  override def tx(): Transaction = {
    throw Exceptions.transactionsNotSupported
  }

  override def variables: Graph.Variables = {
    return this.graphVariables
  }


  override def configuration(): SparkGraphConfiguration = {
    return config
  }

  override def E(): GraphTraversal[Edge, Edge] = ???

  override def compute(graphComputerClass: Class[_]*): GraphComputer = {
    //GraphComputerHelper.validateComputeArguments(graphComputerClass)
    if (graphComputerClass.length == 0 || graphComputerClass(0).equals(classOf[SparkGraphComputer])) {
      return new SparkGraphComputer(this)
    } else {
      throw new RuntimeException("Graph Computer not supported") //Graph.Exceptions.graphDoesNotSupportProvidedGraphComputer(graphComputerClass(0))
    }
  }

  override def close(): Unit = {
    if (graph != null)
      graph.unpersistVertices()
    graph = null
  }

  override def features: Graph.Features = {
    return new Graph.Features {
      override def graph: Graph.Features.GraphFeatures = {
        return new Graph.Features.GraphFeatures {
          override def supportsTransactions: Boolean = {
            return false
          }
          override def supportsThreadedTransactions: Boolean = {
            return false
          }
        }
      }
      override def vertex: Graph.Features.VertexFeatures = {
        return new Graph.Features.VertexFeatures {
          override def supportsAddVertices: Boolean = {
            return false
          }
          override def supportsAddProperty: Boolean = {
            return false
          }
          override def supportsCustomIds: Boolean = {
            return false
          }
          override def properties: Graph.Features.VertexPropertyFeatures = {
            return new Graph.Features.VertexPropertyFeatures {
              override def supportsAddProperty: Boolean = {
                return false
              }
            }
          }
        }
      }
      override def edge: Graph.Features.EdgeFeatures = {
        return new Graph.Features.EdgeFeatures {
          override def supportsAddEdges: Boolean = {
            return false
          }
          override def supportsAddProperty: Boolean = {
            return false
          }
          override def supportsCustomIds: Boolean = {
            return false
          }
        }
      }
    }
  }

}
