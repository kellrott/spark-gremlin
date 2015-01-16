package org.apache.spark.gremlin.structure.io.graphson

import org.apache.hadoop.conf.Configurable
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.Path
import org.apache.hadoop.io.NullWritable
import org.apache.hadoop.io.compress.CompressionCodecFactory
import org.apache.hadoop.mapreduce.InputSplit
import org.apache.hadoop.mapreduce.JobContext
import org.apache.hadoop.mapreduce.RecordReader
import org.apache.hadoop.mapreduce.TaskAttemptContext
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat
import java.io.IOException

import org.apache.spark.gremlin.structure.SparkGraphVertex


class GraphSONInputFormat extends FileInputFormat[NullWritable, SparkGraphVertex] with Configurable {
  private var config: Configuration = null

  @throws(classOf[IOException])
  @throws(classOf[InterruptedException])
  def createRecordReader(split: InputSplit, context: TaskAttemptContext): RecordReader[NullWritable, SparkGraphVertex] = {
    val reader: RecordReader[NullWritable, SparkGraphVertex] = new GraphSONRecordReader()
    reader.initialize(split, context)
    return reader
  }

  protected override def isSplitable(context: JobContext, file: Path): Boolean = {
    return null == new CompressionCodecFactory(context.getConfiguration).getCodec(file)
  }

  def setConf(config: Configuration) {
    this.config = config
  }

  def getConf: Configuration = {
    return this.config
  }
}
