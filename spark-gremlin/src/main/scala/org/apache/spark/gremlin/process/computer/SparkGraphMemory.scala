package org.apache.spark.gremlin.process.computer

import java.util

import com.tinkerpop.gremlin.process.computer.Memory
import com.tinkerpop.gremlin.structure.util.StringFactory

class SparkGraphMemory extends Memory {
  private var runtime: Long = 0l
  private var iteration: Int = -1
  private var memoryKeys: java.util.Set[String] = null

  override def keys(): util.Set[String] = memoryKeys

  override def set(s: String, o: scala.Any): Unit = ???

  override def get[R](s: String): R = ???

  def getIteration: Int = {
    return this.iteration
  }

  override def getRuntime: Long = runtime

  def incr(key: String, delta: Long): Long = {
    throw Memory.Exceptions.memoryCompleteAndImmutable
  }

  def and(key: String, bool: Boolean): Boolean = {
    throw Memory.Exceptions.memoryCompleteAndImmutable
  }

  def or(key: String, bool: Boolean): Boolean = {
    throw Memory.Exceptions.memoryCompleteAndImmutable
  }

  override def toString: String = {
    return StringFactory.memoryString(this)
  }

}
