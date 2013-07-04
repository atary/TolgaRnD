package cloudSimulation.GraphUtils;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;
import java.awt.Stroke;
import java.io.IOException;
import java.util.Map;

import javax.swing.JFrame;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.collections15.BidiMap;
import org.apache.commons.collections15.Transformer;
import org.xml.sax.SAXException;

import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.graph.*;
import edu.uci.ics.jung.io.GraphMLMetadata;
import edu.uci.ics.jung.io.GraphMLReader;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;
import java.awt.FlowLayout;
import java.awt.Container;

public class GraphML {

	private DirectedGraph<Node, Edge> graph;

	public GraphML() {
		graph = new DirectedSparseMultigraph<Node, Edge>();
	}

	public void drawGraph(Container cont) {
		cont.setLayout(new FlowLayout());
                Layout<Integer, String> layout = new CircleLayout(graph);
		layout.setSize(new Dimension(300, 300)); // sets the initial size of the
													// space
		// The BasicVisualizationServer<V,E> is parameterized by the edge types
		BasicVisualizationServer<Integer, String> vv = new BasicVisualizationServer<Integer, String>(
				layout);
		vv.setPreferredSize(cont.getParent().getPreferredSize()); // Sets the viewing area
														// size

		vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
		vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller());
		vv.getRenderer().getVertexLabelRenderer().setPosition(Position.CNTR);
                
		cont.add(vv);

	}

	public void outConsole() {
		for (Node n : graph.getVertices())
			System.out.println(n);

		for (Edge e : graph.getEdges())
			System.out.println(e);
	}

	public void readIn(String filename) throws ParserConfigurationException,
			SAXException, IOException {
		GraphMLReader<DirectedGraph<Node, Edge>, Node, Edge> gmlr = new GraphMLReader<DirectedGraph<Node, Edge>, Node, Edge>(
				new VertexFactory(), new EdgeFactory());

		gmlr.load(filename, graph);

		BidiMap<Node, String> vertex_ids = gmlr.getVertexIDs();
		// BidiMap<Edge,String> edge_ids = gmlr.getEdgeIDs();

		Map<String, GraphMLMetadata<Node>> vertex_meta = gmlr
				.getVertexMetadata();
		Map<String, GraphMLMetadata<Edge>> edge_meta = gmlr.getEdgeMetadata();

		for (Node n : graph.getVertices()) {
			n.setId(vertex_ids.get(n));
			n.setLabel(vertex_meta.get("d0").transformer.transform(n));
		}

		for (Edge e : graph.getEdges()) {
			e.setLabel(edge_meta.get("d2").transformer.transform(e));
			e.setWeight(Double.parseDouble(edge_meta.get("d1").transformer
					.transform(e)));
		}

	}

}
