package cloudSimulation.CloudUtils.Factories;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.cloudbus.cloudsim.Datacenter;
import org.cloudbus.cloudsim.DatacenterCharacteristics;
import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.Pe;
import org.cloudbus.cloudsim.Storage;
import org.cloudbus.cloudsim.VmAllocationPolicySimple;
import org.cloudbus.cloudsim.VmSchedulerTimeShared;
import org.cloudbus.cloudsim.provisioners.BwProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.PeProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.RamProvisionerSimple;

public class DatacenterFactory extends ResourceFactory<Datacenter>{

	private static int id=0;
	
	public Datacenter create(){
		

		List<Host> hostList = new ArrayList<Host>();

		HostFactory hostFactory=new HostFactory();
		setHostFactoryParameters(hostFactory);
		
		hostList.add(hostFactory.create());

		LinkedList<Storage> storageList = new LinkedList<Storage>(); // we are not adding SAN devices by now

		DatacenterCharacteristics characteristics = new DatacenterCharacteristics(
				(String)paramList.get("arch"), 
				(String)paramList.get("os"), 
				(String)paramList.get("vmm"), 
				hostList, 
				(Double)paramList.get("time_zone"), 
				(Double)paramList.get("cost"), 
				(Double)paramList.get("costPerMem"),
				(Double)paramList.get("costPerStorage"), 
				(Double)paramList.get("costPerBw"));

		Datacenter datacenter = null;
		try {
			datacenter = new Datacenter(
					(String)paramList.get("name")+"_"+id++, 
					characteristics,
					new VmAllocationPolicySimple(hostList), 
					storageList, 
					0);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return datacenter;		
	}

	private void setHostFactoryParameters(HostFactory hostFactory) {

		List<Pe> peList = new ArrayList<Pe>();
		PeFactory peFactory=new PeFactory();
		setPeFactoryParameters(peFactory);

		peList.add(peFactory.create());
		
		hostFactory.addParam("ramProvisioner", new RamProvisionerSimple((Integer)paramList.get("ram")));
		hostFactory.addParam("bwProvisioner", new BwProvisionerSimple((Integer)paramList.get("bw")));
		hostFactory.addParam("storage", (Long)paramList.get("storage"));
		hostFactory.addParam("peList", peList);
		hostFactory.addParam("scheduler", new VmSchedulerTimeShared(peList));
		
	}

	private void setPeFactoryParameters(PeFactory peFactory) {
		peFactory.addParam("peProvisioner", new PeProvisionerSimple((Integer)paramList.get("mips")));
	}
	
	
}
