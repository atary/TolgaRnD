package cloudSimulation.GML2BRITE;

import java.util.HashMap;
import java.util.Vector;

public class Edge {
	private int source;
	private int target;
	private Vector<Data> data;

	public Edge() {
		data = new Vector<Data>();
	}

	public void setSource(String source) {
		this.source = Integer.parseInt(source);
	}

	public void setTarget(String target) {
		this.target = Integer.parseInt(target);
	}

	public void addData(Data d) {
		data.addElement(d);
	}

	public String toString() {
		HashMap<String, String> dataMap = new HashMap<String, String>();
		for (Data d : data)
			dataMap.put(d.getKey(), d.getData());

		String s = "" + dataMap.get("e0");
		s += " " + source;
		s += " " + target;
		s += " " + dataMap.get("e1");
		s += " " + dataMap.get("e2");
		s += " " + dataMap.get("e3");
		s += " -1";
		s += " -1";
		s += " E_RT_NONE";

		s += "\n";
		return s;
	}
}
