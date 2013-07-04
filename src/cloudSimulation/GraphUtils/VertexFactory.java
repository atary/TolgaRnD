package cloudSimulation.GraphUtils;

import org.apache.commons.collections15.Factory;

public class VertexFactory implements Factory<Node>{
   
	private int n = 0;
	
    public Node create()
    {
        return (new Node(""+n++));
    }
    
}
