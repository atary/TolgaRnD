package cloudSimulation.GML2BRITE;

import java.io.File;
import java.io.IOException;

import org.apache.commons.digester3.Digester;
import org.xml.sax.SAXException;

public class GML2BRITE {

	private Graph g;
	
	public void readIn(String filename) throws IOException, SAXException{
        Digester digester = new Digester();
        digester.setValidating( false );

        digester.addObjectCreate( "graphml/graph", Graph.class );
     
        	digester.addObjectCreate( "graphml/graph/node", Node.class );
        		digester.addSetProperties( "graphml/graph/node", "id", "id" );
        		
                	digester.addObjectCreate( "graphml/graph/node/data", Data.class );
                		digester.addSetProperties( "graphml/graph/node/data", "key", "key" );
                		digester.addBeanPropertySetter( "graphml/graph/node/data");

                	digester.addSetNext( "graphml/graph/node/data", "addData" );
                
            digester.addSetNext( "graphml/graph/node", "addNode" );
            
        	digester.addObjectCreate( "graphml/graph/edge", Edge.class );
    			digester.addSetProperties( "graphml/graph/edge", "source", "source" );
    			digester.addSetProperties( "graphml/graph/edge", "target", "target" );
    		
            	digester.addObjectCreate( "graphml/graph/edge/data", Data.class );
            		digester.addSetProperties( "graphml/graph/edge/data", "key", "key" );
            		digester.addBeanPropertySetter( "graphml/graph/edge/data");
            		
                digester.addSetNext( "graphml/graph/edge/data", "addData" );
                    
            digester.addSetNext( "graphml/graph/edge", "addEdge" );  
 

        File input = new File( filename );
        g = (Graph)digester.parse( input );		
	}
	
	public String toString(){
		if(g!=null)
			return g.toString();
		else return "NULL";
	}
}