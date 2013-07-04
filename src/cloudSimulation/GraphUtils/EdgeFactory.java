package cloudSimulation.GraphUtils;

import org.apache.commons.collections15.Factory;


public class EdgeFactory implements Factory<Edge>{

	private int n = 0;
	
    public Edge create()
    {
        return (new Edge(""+n++));
    }
    


}
