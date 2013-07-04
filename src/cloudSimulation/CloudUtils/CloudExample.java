package cloudSimulation.CloudUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.CloudletSchedulerTimeShared;
import org.cloudbus.cloudsim.Datacenter;
import org.cloudbus.cloudsim.DatacenterBroker;
import org.cloudbus.cloudsim.NetworkTopology;
import org.cloudbus.cloudsim.UtilizationModelFull;
import org.cloudbus.cloudsim.UtilizationModelStochastic;
import org.cloudbus.cloudsim.Vm;
import org.cloudbus.cloudsim.core.CloudSim;

import cloudSimulation.CloudUtils.Factories.CloudletFactory;
import cloudSimulation.CloudUtils.Factories.DatacenterFactory;
import cloudSimulation.CloudUtils.Factories.VMFactory;
import cloudSimulation.CloudUtils.UtilizationModels.DistributionType;
import cloudSimulation.CloudUtils.UtilizationModels.UtilizationModelDistribution;

public class CloudExample {

	private static List<Cloudlet> cloudletList;

	private static List<Vm> vmlist;

	private final int numOfVms=6;
	
	private final int numOfCloudlets=12;

	public void go() {
		try {
			int num_user = 1;
			Calendar calendar = Calendar.getInstance();
			boolean trace_flag = false;

			CloudSim.init(num_user, calendar, trace_flag);

			DatacenterFactory dcFactory=new DatacenterFactory();
			setDataCenterParameters(dcFactory);
			Datacenter datacenter0 = dcFactory.create();
			Datacenter datacenter1 = dcFactory.create();
			//Datacenter datacenter2 = dcFactory.create();
			//Datacenter datacenter3 = dcFactory.create();
			//Datacenter datacenter4 = dcFactory.create();
			//Datacenter datacenter5 = dcFactory.create();
			
			// Third step: Create Broker
			DatacenterBroker broker = new DatacenterBroker("Broker");
			//int brokerId = broker.getId();

			// Fourth step: Create one virtual machine
			vmlist = new ArrayList<Vm>();

			// VM description
			VMFactory vmFactory=new VMFactory();
			setVMParameters(vmFactory);
			vmFactory.addParam("brokerId", broker.getId());
			
			for(int i=0;i<numOfVms;i++)
				vmlist.add(vmFactory.create());

			broker.submitVmList(vmlist);

			cloudletList = new ArrayList<Cloudlet>();

			CloudletFactory cloudletFactory=new CloudletFactory();
			setCloudletParameters(cloudletFactory);
			cloudletFactory.addParam("brokerId", broker.getId());
			
			for(int i=0;i<numOfCloudlets;i++){
				cloudletFactory.addParam("vmId", i%numOfVms);
				cloudletFactory.addParam("CPUutilization", new UtilizationModelFull());
				cloudletFactory.addParam("RAMutilization", new UtilizationModelFull());
				cloudletFactory.addParam("BWutilization", new UtilizationModelDistribution(DistributionType.NORMALDISTRIBUTION,1000));
				cloudletList.add(cloudletFactory.create());
			}
			
			// submit cloudlet list to the broker
			broker.submitCloudletList(cloudletList);

			//broker.bindCloudletToVm(cloudletList.get(0).getCloudletId(),vmlist.get(0).getId());
            //broker.bindCloudletToVm(cloudletList.get(1).getCloudletId(),vmlist.get(1).getId());
            //broker.bindCloudletToVm(cloudletList.get(2).getCloudletId(),vmlist.get(2).getId());
            //broker.bindCloudletToVm(cloudletList.get(3).getCloudletId(),vmlist.get(3).getId());
            //broker.bindCloudletToVm(cloudletList.get(4).getCloudletId(),vmlist.get(4).getId());
            //broker.bindCloudletToVm(cloudletList.get(5).getCloudletId(),vmlist.get(5).getId());
			
			NetworkTopology.buildNetworkTopology("io/topology.brite");
            

			NetworkTopology.mapNode(broker.getId(), 0);
			NetworkTopology.mapNode(datacenter0.getId(), 1);
			NetworkTopology.mapNode(datacenter1.getId(), 2);
			/*NetworkTopology.mapNode(datacenter2.getId(), 3);
			NetworkTopology.mapNode(datacenter3.getId(), 4);
			NetworkTopology.mapNode(datacenter4.getId(), 5);
			NetworkTopology.mapNode(datacenter5.getId(), 6);
			*/
			//System.out.println(NetworkTopology.getDelay(datacenter1.getId(), datacenter0.getId()));
            
			// Sixth step: Starts the simulation
			CloudSim.startSimulation();

			CloudSim.stopSimulation();

			// Final step: Print results when simulation is over
			List<Cloudlet> newList = broker.getCloudletReceivedList();
			printCloudletList(newList);

			System.out.println("CloudSimExample1 finished!");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Unwanted errors happen");
		}

	}

	private void setCloudletParameters(CloudletFactory cloudletFactory) {

		cloudletFactory.addParam("length", (long)400000);
		cloudletFactory.addParam("fileSize", (long)300);
		cloudletFactory.addParam("outputSize", (long)300);
		cloudletFactory.addParam("pesNumber", 1);
	}

	private void setVMParameters(VMFactory vmFactory) {
		
		vmFactory.addParam("mips", 1000);
		vmFactory.addParam("size", (long)10000);
		vmFactory.addParam("ram", 512);
		vmFactory.addParam("bw", (long)1000);
		vmFactory.addParam("mips", 1000);
		vmFactory.addParam("pesNumber", 1);
		vmFactory.addParam("vmm", "Xen");
		vmFactory.addParam("scheduler", new CloudletSchedulerTimeShared());
	}

	private void setDataCenterParameters(DatacenterFactory dcFactory) {
		dcFactory.addParam("name", "Datacenter");
		dcFactory.addParam("mips", 3000);
		dcFactory.addParam("hostId", 0);
		dcFactory.addParam("ram", 4096);
		dcFactory.addParam("storage", (long)1000000);
		dcFactory.addParam("bw", 10000);
		dcFactory.addParam("arch", "x86");
		dcFactory.addParam("os", "Linux");
		dcFactory.addParam("time_zone", 10.0);
		dcFactory.addParam("cost", 3.0);
		dcFactory.addParam("costPerMem", 0.05);
		dcFactory.addParam("costPerStorage", 0.001);
		dcFactory.addParam("costPerBw", 0.0);
		
	}



	private static void printCloudletList(List<Cloudlet> list) {
		int size = list.size();
		Cloudlet cloudlet;

		String indent = "    ";

		System.out.println("========== OUTPUT ==========");
		System.out.println("Cloudlet ID" + indent + "STATUS" + indent
				+ "Data center ID" + indent + "VM ID" + indent + "Time"
				+ indent + "Start Time" + indent + "Finish Time");

		DecimalFormat dft = new DecimalFormat("###.##");
		for (int i = 0; i < size; i++) {
			cloudlet = list.get(i);
			System.out.print(indent + cloudlet.getCloudletId() + indent
					+ indent);

			if (cloudlet.getCloudletStatus() == Cloudlet.SUCCESS) {
				System.out.print("SUCCESS");

				System.out.println(indent + indent + cloudlet.getResourceId()
						+ indent + indent + indent + cloudlet.getVmId()
						+ indent + indent
						+ dft.format(cloudlet.getActualCPUTime()) + indent
						+ indent + dft.format(cloudlet.getExecStartTime())
						+ indent + indent
						+ dft.format(cloudlet.getFinishTime())
						);
				
				for(int k=0;k<=1000;k+=100)
					System.out.print(String.format("%.2f ", cloudlet.getUtilizationOfBw(k)));
				
				System.out.println();

			}
		}
	}

}
