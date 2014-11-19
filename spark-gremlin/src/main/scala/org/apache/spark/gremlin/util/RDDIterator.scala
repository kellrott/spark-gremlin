package org.apache.spark.gremlin.util

import org.apache.spark.rdd.RDD

/**
 * Created by kellrott on 2/8/14.
 */
abstract class RDDIterator[I, O] extends java.util.Iterator[O] with java.lang.Iterable[O] {
  def elementClass() : Class[_]

  def process(in: I): O

  def getRDD() : RDD[I]

  var rddCollect : Array[I] = null
  var rddCollectIndex = 0

  def hasNext: Boolean = {
    if (rddCollect == null) {
      rddCollect = getRDD().collect()
      rddCollectIndex = 0;
    }
    return rddCollectIndex < rddCollect.length
  }


  def next(): O = {
    if (!hasNext) {
      return null.asInstanceOf[O]
    }
    if (rddCollect != null) {
      val out = process( rddCollect(rddCollectIndex) )
      rddCollectIndex += 1
      out.asInstanceOf[O]
    } else {
      null.asInstanceOf[O]
    }
  }

  def iterator(): java.util.Iterator[O] = {
    rddCollect = null
    this
  }

  override def remove() = {}
}
