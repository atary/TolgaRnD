package cloudSimulation.GraphUtils;

public class Node {
	private String id;
	private String label;

	public Node(String id) {
		this.id = id; 
	}

	public Node(String id,String label) {
		this.id = id;
		this.label = label;
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

	@Override
	public String toString() {
		return ""+ label ;
	}


}
