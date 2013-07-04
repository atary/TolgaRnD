package cloudSimulation.GML2BRITE;

public class Data{
	private String key;
	private String data;
	
	   public void setData( String data ) { 
		   this.data=data; 
	   }
	   
		
	   public void setKey( String key ) { 
		   this.key=key; 
	   }
	   
	   
	   

	public synchronized String getKey() {
		return key;
	}


	public synchronized String getData() {
		return data;
	}


		public String toString(){
			return "{"+key+":"+data+"}";
		}


}
