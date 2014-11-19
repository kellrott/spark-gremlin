package org.apache.spark.gremlin.process;

import com.tinkerpop.gremlin.process.ProcessComputerSuite;
import com.tinkerpop.gremlin.process.ProcessStandardSuite;
import org.apache.spark.gremlin.structure.SparkGraph;
import org.apache.spark.gremlin.structure.SparkGraphProvider;
import org.junit.runner.RunWith;

@RunWith(ProcessComputerSuite.class)
@ProcessComputerSuite.GraphProviderClass(provider = SparkGraphProvider.class, graph = SparkGraph.class)
public class SparkGraphProcessComputerTest {
}

