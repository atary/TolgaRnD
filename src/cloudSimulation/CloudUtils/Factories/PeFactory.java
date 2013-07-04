package cloudSimulation.CloudUtils.Factories;

import org.cloudbus.cloudsim.Pe;
import org.cloudbus.cloudsim.provisioners.PeProvisioner;

public class PeFactory extends ResourceFactory<Pe> {

	@Override
	public Pe create() {
		return new Pe(
				id++,
				(PeProvisioner)paramList.get("peProvisioner")
				);
	}

}
