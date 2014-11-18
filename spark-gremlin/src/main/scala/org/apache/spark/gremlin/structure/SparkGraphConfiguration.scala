package org.apache.spark.gremlin.structure

import org.apache.spark.gremlin.Constants

import collection.JavaConverters._
import org.apache.commons.configuration.{Configuration, BaseConfiguration}

class SparkGraphConfiguration extends BaseConfiguration with Serializable with java.lang.Iterable[org.javatuples.Pair[String,AnyRef]] {

  def this(configuration: Configuration) {
    this()
    this.copy(configuration)
  }

  override def iterator: java.util.Iterator[org.javatuples.Pair[String,AnyRef]] = {
    getKeys().asScala.map( x => new org.javatuples.Pair(x, getProperty(x) ) ).toIterator.asJava
  }

  def setInputLocation(inputLocation: String) {
    this.setProperty(Constants.GREMLIN_GIRAPH_INPUT_LOCATION, inputLocation)
  }
}
