package cloudSimulation.GML2BRITE;

import java.util.HashMap;
import java.util.Vector;

public class Node{
	private int id;
	private Vector<Data> data;

	public Node() {
		data = new Vector<Data>();
	}
	
	   public void setId( String id ) { 
		   this.id=Integer.parseInt(id); 
	   }

	   public void addData( Data d ) {
	      data.addElement( d );
	   }
	   
		public String toString(){
			HashMap<String,String> dataMap=new HashMap<String,String>();
			for(Data d:data)
				dataMap.put(d.getKey(), d.getData());
			
			String s=""+id;
			s+=" "+dataMap.get("d0");
			s+=" "+dataMap.get("d1");
			s+=" "+dataMap.get("d2");
			s+=" "+dataMap.get("d3");
			s+=" -1";
			s+=" RT_NONE";
			
			s+="\n";
			return s;
		}
}
