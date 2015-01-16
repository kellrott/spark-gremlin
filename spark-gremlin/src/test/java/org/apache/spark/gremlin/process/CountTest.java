package org.apache.spark.gremlin.process;

import com.tinkerpop.gremlin.LoadGraphWith;
import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.gremlin.structure.SparkGraph;
import org.apache.spark.gremlin.structure.SparkGraphProvider;
import org.junit.Test;

public class CountTest {

  @Test
  public void testCount() {
    SparkGraphProvider provider = new SparkGraphProvider();
    SparkConf conf = new SparkConf().setAppName("local").
            setMaster("local").
            set("spark.serializer", "org.apache.spark.serializer.KryoSerializer").
            set("spark.kryo.registrator", "org.apache.spark.gremlin.util.SparkGremlinKyroRegistrator");
    SparkContext sc = new SparkContext(conf);
    SparkGraph g = new SparkGraph(sc);
    provider.loadGraphData(g, LoadGraphWith.GraphData.GRATEFUL);
    g.V().count().submit(g.compute());
  }

}
