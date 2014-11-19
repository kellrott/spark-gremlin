package org.apache.spark.gremlin.process.computer

import org.apache.hadoop.conf.Configured
import com.tinkerpop.gremlin.process.computer.{VertexProgram, MapReduce, ComputerResult, GraphComputer}
import com.tinkerpop.gremlin.process.computer.GraphComputer.{Features, Isolation}
import java.util.concurrent.Future
import org.apache.spark.gremlin.structure.SparkGraph

class SparkGraphComputer(var graph:SparkGraph) extends Configured with GraphComputer {
  def isolation(isolation: Isolation): GraphComputer = {
    return this
  }

  def program(vertexProgram: VertexProgram[_]): GraphComputer = {
    throw new RuntimeException
  }

  def mapReduce(mapReduce: MapReduce[_, _, _, _, _]): GraphComputer = {
    throw new RuntimeException
  }

  def submit(): Future[ComputerResult] = {
    throw new RuntimeException
  }

  override def features(): Features = super.features()
}