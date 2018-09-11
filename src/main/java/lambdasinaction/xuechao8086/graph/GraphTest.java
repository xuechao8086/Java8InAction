package lambdasinaction.xuechao8086.graph;

import com.google.common.collect.ImmutableList;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;
import com.google.common.graph.MutableNetwork;
import com.google.common.graph.NetworkBuilder;
import lambdasinaction.xuechao8086.model.Node;
import org.junit.Assert;
import org.junit.Test;

import java.text.MessageFormat;
import java.util.List;
import java.util.Set;

/**
 * @author gumi
 * @since 2018/08/29 14:27
 */
public class GraphTest {

    @Test
    public void testGraph() {
        MutableGraph<Node> graph = GraphBuilder.directed()
            .allowsSelfLoops(false)
            .build();

        List<Node> nodeList = buildNodeList();
        nodeList.forEach(graph::addNode);
        graph.putEdge(nodeList.get(0), nodeList.get(1));
        graph.putEdge(nodeList.get(1), nodeList.get(4));
        graph.putEdge(nodeList.get(4), nodeList.get(7));
        graph.putEdge(nodeList.get(7), nodeList.get(10));
        graph.putEdge(nodeList.get(2), nodeList.get(5));
        graph.putEdge(nodeList.get(5), nodeList.get(8));
        graph.putEdge(nodeList.get(8), nodeList.get(10));
        graph.putEdge(nodeList.get(3), nodeList.get(6));
        graph.putEdge(nodeList.get(6), nodeList.get(9));
        graph.putEdge(nodeList.get(9), nodeList.get(10));

        Set<Node> nodeSet = graph.nodes();
        nodeSet.forEach(node -> {
            String msg = MessageFormat.format("{0} predecessors: {1}, successors: {2}",
                node, graph.predecessors(node), graph.successors(node));
            System.out.println(msg);
        });


    }

    @Test
    public void testNetWork() {
        MutableNetwork<Node, Integer> mutableNetwork = NetworkBuilder.directed()
            .allowsParallelEdges(false)
            .allowsSelfLoops(false)
            .build();
        Assert.assertNotNull(mutableNetwork);

        List<Node> nodeList = buildNodeList();
        updateNetWork(nodeList, mutableNetwork);

        Set<Node> n10Predictors = mutableNetwork.predecessors(nodeList.get(10));
        Assert.assertTrue(n10Predictors.size() > 0);
    }


    private void updateNetWork(List<Node> nodeList, MutableNetwork<Node, Integer> mutableNetwork) {
        nodeList.forEach(mutableNetwork::addNode);
        mutableNetwork.addEdge(nodeList.get(0), nodeList.get(1), 0);
        mutableNetwork.addEdge(nodeList.get(1), nodeList.get(4), 1);
        mutableNetwork.addEdge(nodeList.get(2), nodeList.get(5), 2);
        mutableNetwork.addEdge(nodeList.get(3), nodeList.get(6), 3);
        mutableNetwork.addEdge(nodeList.get(4), nodeList.get(7), 4);
        mutableNetwork.addEdge(nodeList.get(5), nodeList.get(8), 5);
        mutableNetwork.addEdge(nodeList.get(6), nodeList.get(9), 6);
        mutableNetwork.addEdge(nodeList.get(7), nodeList.get(10), 7);
        mutableNetwork.addEdge(nodeList.get(8), nodeList.get(10), 8);
        mutableNetwork.addEdge(nodeList.get(9), nodeList.get(10), 9);
    }

    private List<Node> buildNodeList() {
        return ImmutableList.of(
            Node.builder().info("n0").build(),
            Node.builder().info("n1").build(),
            Node.builder().info("n2").build(),
            Node.builder().info("n3").build(),
            Node.builder().info("n4").build(),
            Node.builder().info("n5").build(),
            Node.builder().info("n6").build(),
            Node.builder().info("n7").build(),
            Node.builder().info("n8").build(),
            Node.builder().info("n9").build(),
            Node.builder().info("n10").build()
        );
    }

}
