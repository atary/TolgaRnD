package cloudSimulation.CloudUtils.Factories;

import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.UtilizationModel;

public class CloudletFactory extends ResourceFactory<Cloudlet> {
	
	
	public Cloudlet create(){
		
		Cloudlet cloudlet = new Cloudlet(
				id++, 
				(Long)paramList.get("length"), 
				(Integer)paramList.get("pesNumber"), 
				(Long)paramList.get("fileSize"),
				(Long)paramList.get("outputSize"), 
				(UtilizationModel)paramList.get("CPUutilization"), 
				(UtilizationModel)paramList.get("RAMutilization"), 
				(UtilizationModel)paramList.get("BWutilization")
				);
		
			cloudlet.setUserId((Integer)paramList.get("brokerId") );
			cloudlet.setVmId((Integer)paramList.get("vmId") );
			
			return cloudlet;
			
	}
	
	
}
