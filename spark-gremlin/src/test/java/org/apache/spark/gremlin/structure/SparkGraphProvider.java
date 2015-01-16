package org.apache.spark.gremlin.structure;

import com.tinkerpop.gremlin.AbstractGraphProvider;
import com.tinkerpop.gremlin.GraphProvider;
import com.tinkerpop.gremlin.LoadGraphWith;
import com.tinkerpop.gremlin.structure.Graph;
import com.tinkerpop.gremlin.structure.io.graphson.GraphSONResourceAccess;
import com.tinkerpop.gremlin.structure.io.kryo.KryoResourceAccess;
import org.apache.commons.configuration.Configuration;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SparkGraphProvider extends AbstractGraphProvider {

    public static Map<String, String> PATHS = new HashMap<>();

    static {
        try {
            final List<String> graphsonResources = Arrays.asList(
                    "tinkerpop-classic-vertices.ldjson",
                    "grateful-dead-vertices.ldjson");
            for (final String fileName : graphsonResources) {
                PATHS.put(fileName, generateTempFile(GraphSONResourceAccess.class, fileName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public Map<String, Object> getBaseConfiguration(final String graphName, final Class<?> test,
                                                    final String testMethodName) {
        return new HashMap<String, Object>() {{
            put("gremlin.graph", SparkGraph.class.getName());
            //put("sparkgremlin.vertex_input_format", KryoVertexInputFormat.class.getCanonicalName());
            //put(GiraphConstants.VERTEX_OUTPUT_FORMAT_CLASS.getKey(), KryoVertexOutputFormat.class.getCanonicalName());
        }};
    }

    @Override
    public void clear(final Graph g, final Configuration configuration) throws Exception {
        if (g != null) {
            g.close();
            ((SparkGraph)g).sc().stop();
            //Thread.sleep(1000);
        }
    }

    @Override
    public void loadGraphData(final Graph g, final LoadGraphWith loadGraphWith) {
        this.loadGraphData(g, loadGraphWith.value());
    }

    public void loadGraphData(final Graph g, final LoadGraphWith.GraphData graphData) {

        if (graphData.equals(LoadGraphWith.GraphData.GRATEFUL)) {
            ((SparkGraph) g).configuration().setInputLocation(PATHS.get("grateful-dead-vertices.ldjson"));
        } else if (graphData.equals(LoadGraphWith.GraphData.MODERN)) {
            ((SparkGraph) g).configuration().setInputLocation(PATHS.get("tinkerpop-modern-vertices.ldjson"));
        } else if (graphData.equals(LoadGraphWith.GraphData.CLASSIC)) {
            ((SparkGraph) g).configuration().setInputLocation(PATHS.get("tinkerpop-classic-vertices.ldjson"));
        } else if (graphData.equals(LoadGraphWith.GraphData.CREW)) {
            ((SparkGraph) g).configuration().setInputLocation(PATHS.get("tinkerpop-crew-vertices.ldjson"));
        } else {
            throw new RuntimeException("Could not load graph with " + graphData);
        }
    }


    public static String generateTempFile(final Class resourceClass, final String fileName) throws IOException {
        final File temp = File.createTempFile(fileName, ".tmp");
        final FileOutputStream outputStream = new FileOutputStream(temp);
        int data;
        final InputStream inputStream = resourceClass.getResourceAsStream(fileName);
        while ((data = inputStream.read()) != -1) {
            outputStream.write(data);
        }
        outputStream.close();
        inputStream.close();
        return temp.getPath();
    }

}
