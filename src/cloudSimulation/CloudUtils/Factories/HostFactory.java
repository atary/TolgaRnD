package cloudSimulation.CloudUtils.Factories;

import java.util.List;

import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.Pe;
import org.cloudbus.cloudsim.VmScheduler;
import org.cloudbus.cloudsim.provisioners.BwProvisioner;
import org.cloudbus.cloudsim.provisioners.RamProvisioner;

public class HostFactory extends ResourceFactory<Host> {

	@Override
	public Host create() {
		return new Host(
				id++, 
				(RamProvisioner)paramList.get("ramProvisioner"),
				(BwProvisioner)paramList.get("bwProvisioner"),
				(Long)paramList.get("storage"), 
				(List<Pe>)paramList.get("peList"),
				(VmScheduler)paramList.get("scheduler")
				);

	}

}
