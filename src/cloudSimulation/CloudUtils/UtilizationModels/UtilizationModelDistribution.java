package cloudSimulation.CloudUtils.UtilizationModels;

import org.cloudbus.cloudsim.UtilizationModel;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.distribution.RealDistribution;

public class UtilizationModelDistribution implements UtilizationModel {

	
	private DistributionType d_type;
	private long timeFrequency;
	private RealDistribution p;
	
	public UtilizationModelDistribution(DistributionType d_type,long timeFrequency) {
		this.d_type=d_type;
		this.timeFrequency=timeFrequency;
		p=decideDistribution();
	}

	private RealDistribution decideDistribution() {
		if(d_type==DistributionType.NORMALDISTRIBUTION)
			return new NormalDistribution(0.5,0.2);
		return null;
	}

	@Override
	public double getUtilization(double arg0) {
		
		return p.density((arg0%timeFrequency)/timeFrequency);
	}

}
