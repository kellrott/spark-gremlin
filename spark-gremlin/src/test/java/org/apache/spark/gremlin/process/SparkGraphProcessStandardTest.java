package org.apache.spark.gremlin.process;

import com.tinkerpop.gremlin.process.ProcessStandardSuite;
import org.apache.spark.gremlin.structure.SparkGraph;
import org.apache.spark.gremlin.structure.SparkGraphProvider;
import org.junit.runner.RunWith;

@RunWith(ProcessStandardSuite.class)
@ProcessStandardSuite.GraphProviderClass(provider = SparkGraphProvider.class, graph = SparkGraph.class)
public class SparkGraphProcessStandardTest {
}

