package cloudSimulation.CloudUtils.Factories;

import org.cloudbus.cloudsim.CloudletScheduler;
import org.cloudbus.cloudsim.Vm;

public class VMFactory extends ResourceFactory<Vm> {
	
	
	public Vm create(){
	
		return new Vm(
				id++, 
				(Integer)paramList.get("brokerId"), 
				(Integer)paramList.get("mips"), 
				(Integer)paramList.get("pesNumber"), 
				(Integer)paramList.get("ram"), 
				(Long)paramList.get("bw"), 
				(Long)paramList.get("size"), 
				(String)paramList.get("vmm")+id,
				(CloudletScheduler)paramList.get("scheduler")
				);
	}
}
