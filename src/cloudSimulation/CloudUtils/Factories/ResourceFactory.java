package cloudSimulation.CloudUtils.Factories;

import java.util.HashMap;

public abstract class ResourceFactory<T> {
	protected HashMap<String,Object> paramList;
	protected int id;
	
	public ResourceFactory(){
		paramList = new HashMap<String,Object>();
		id=0;
	}
		
	public void addParam(String name,Object value){
		paramList.put(name, value);
	}
	
	
	public boolean containsParam(String name){
		return paramList.containsKey(name);
	}
	
	public abstract T create();
}
