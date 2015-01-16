package org.apache.spark.gremlin.process.computer

import org.apache.hadoop.conf.Configured
import com.tinkerpop.gremlin.process.computer.{VertexProgram, MapReduce, ComputerResult, GraphComputer}
import com.tinkerpop.gremlin.process.computer.GraphComputer.{Features, Isolation}
import java.util.concurrent.{TimeUnit, Future}
import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.hadoop.io.NullWritable
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat
import org.apache.spark.gremlin.Constants
import org.apache.spark.gremlin.structure.{SparkGraphVertex, SparkGraph}
import org.apache.spark.gremlin.structure.io.graphson.GraphSONInputFormat

class SparkGraphComputer(var graph:SparkGraph) extends Configured with GraphComputer {
  val configuration = graph.configuration()

  def isolation(isolation: Isolation): GraphComputer = {
    return this
  }

  def program(vertexProgram: VertexProgram[_]): GraphComputer = {
    return this
  }

  def mapReduce(mapReduce: MapReduce[_, _, _, _, _]): GraphComputer = {
    throw new RuntimeException
  }

  def submit(): Future[ComputerResult] = {
    val path = this.configuration.get(Constants.GREMLIN_SPARK_INPUT_LOCATION).asInstanceOf[String]
    val input = graph.sc.newAPIHadoopFile(path, classOf[GraphSONInputFormat], classOf[NullWritable], classOf[SparkGraphVertex] ).map(_._2)
    println(input.count)
    println(input.take(10).mkString(","))
    //if (!FileSystem.get(this.giraphConfiguration).exists(inputPath)) throw new IllegalArgumentException("The provided input path does not exist: " + inputPath)
    //FileInputFormat.setInputPaths(job.getInternalJob, inputPath)
    //FileOutputFormat.setOutputPath(job.getInternalJob, new Path(this.giraphConfiguration.get(Constants.GREMLIN_GIRAPH_OUTPUT_LOCATION) + "/" + Constants.SYSTEM_G))
    //throw new RuntimeException
    return new Future[ComputerResult] {
      var cancelled = false
      override def cancel(mayInterruptIfRunning: Boolean): Boolean = {
        cancelled = true
        return true
      }

      override def isCancelled: Boolean = cancelled

      override def get(): ComputerResult = {
        new ComputerResult(graph, new SparkGraphMemory)
      }

      override def get(timeout: Long, unit: TimeUnit): ComputerResult = {
        new ComputerResult(graph, new SparkGraphMemory)
      }

      override def isDone: Boolean = true
    }
  }

  override def features(): Features = super.features()


}