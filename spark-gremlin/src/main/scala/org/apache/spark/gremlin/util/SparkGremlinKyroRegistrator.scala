package sparkgraph.common

import org.apache.spark.serializer.KryoRegistrator
import com.esotericsoftware.kryo.Kryo
import org.apache.spark.graphx
import org.apache.spark.gremlin.structure.{SparkGraphEdge, SparkGraphVertex}


class SparkGremlinKyroRegistrator extends KryoRegistrator {
  override def registerClasses(kryo: Kryo) {
    kryo.register(classOf[SparkGraphVertex])
    kryo.register(classOf[SparkGraphEdge])
    kryo.register(classOf[graphx.Edge[SparkGraphEdge]])
  }
}
