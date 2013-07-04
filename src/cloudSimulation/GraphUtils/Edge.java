package cloudSimulation.GraphUtils;

public class Edge {
	private String id;
	private String label;
	private double weight;
	
	
	public Edge(String id,String label, double weight) {
		this.id = id;
		this.label = label;
		this.weight = weight;
	}


	public Edge(String id,String label) {
		this.id = id;
		this.label = label;
	}
	
	public Edge(String id,int weight) {
		this.id = id;
		this.weight = weight;
	}
	
	public Edge(String id) {
		this.id = id;
	}

	public synchronized String getId() {
		return id;
	}

	public synchronized void setId(String id) {
		this.id = id;
	}


	public synchronized String getLabel() {
		return label;
	}

	public synchronized void setLabel(String label) {
		this.label = label;
	}


	public synchronized double getWeight() {
		return weight;
	}


	public synchronized void setWeight(double weight) {
		this.weight = weight;
	}


	@Override
	public String toString() {
		return ""+ label;
	}

	
	
}
