package org.apache.spark.gremlin.structure;

import com.tinkerpop.gremlin.structure.StructureStandardSuite;
import org.apache.spark.gremlin.structure.SparkGraph;
import org.junit.runner.RunWith;

public class SparkStructureStandardSuite {


    // Structure API tests
    @RunWith(StructureStandardSuite.class)
    @StructureStandardSuite.GraphProviderClass(provider = SparkGraphProvider.class, graph = SparkGraph.class)
    public class SparkStructureStandardTest {

    }


}