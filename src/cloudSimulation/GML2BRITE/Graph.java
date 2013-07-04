package cloudSimulation.GML2BRITE;

import java.util.Vector;

public class Graph {
	private Vector<Node> nodes;
	private Vector<Edge> edges;

	public Graph() {
		nodes = new Vector<Node>();
		edges = new Vector<Edge>();
	}

	public void addNode(Node aNode) {
		nodes.addElement(aNode);
	}

	public void addEdge(Edge anEdge) {
		edges.addElement(anEdge);
	}
	
	public String toString(){
		String s="";
		s+="Topology: ( "+nodes.size()+" Nodes, "+edges.size()+" Edges )\n\n\n";
		s+="Nodes: ("+nodes.size()+")\n";
		for(Node n:nodes)
			s+=n.toString();
		s+="\n";
		
		s+="Edges: ("+nodes.size()+")\n";
		for(Edge e:edges)
			s+=e.toString();
			
		return s;
		
	}
}
