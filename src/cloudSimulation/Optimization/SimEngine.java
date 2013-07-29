/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cloudSimulation.Optimization;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 *
 * @author ovatman
 */
public class SimEngine {

    private ArrayList<VMach> VMs;

    public SimEngine() {
        VMs = new ArrayList<VMach>();

    }

    public void createVMs() {
        VMs.add(new VMach("ONE", 100, 100, 100, 100));

        VMs.add(new VMach("TWO", 100, 100, 100, 100));

        VMs.add(new VMach("THREE", 100, 100, 100, 100));
    }

    public boolean submitApp(int i, App app) {
        if (!VMs.get(i).add(app)) {
            int j = i;
            do {
                j = (j + 1) % VMs.size();
                if (VMs.get(j).add(app)) {
                    System.out.println("App added to VM " + (j + 1) + " instead of VM " + (i + 1));
                    return true;
                }
            } while (j != i);
        } else {
            return true;
        }
        System.out.println("App refused");
        return false;
    }

    public void printVMs() {

        for (VMach v : VMs) {
            System.out.println(v);
        }

    }

    public void go(int freq, int maxit) throws InterruptedException {
        int i = 0,numOfTasks=0;
        Random r = new Random();
        boolean yield=true;
        
        while (i < maxit) {
            if (r.nextInt(5) > -1) {
                yield=submitApp(numOfTasks++%3, new App("App" + i, r.nextInt(10) + 5, r.nextInt(10) + 5, r.nextInt(10) + 5, r.nextInt(10) + 5));
                //yield=submitApp(new App("App" + i, r.nextInt(10) + 5, r.nextInt(10) + 5, r.nextInt(10) + 5, r.nextInt(10) + 5));
            }

            printVMs();
            System.out.println("Iteration: " + i);
            System.out.println("-----------------------------------------------------------------------------");
            i++;
            if(!yield)
                break;
            Thread.sleep(freq);
        }
    }

    private boolean submitApp(App app) {
       LPSolver lps=new LPSolver();
       int numOfTasks=0;
       int[][] wlds;
       App[] apps;
               
       for(VMach vm:VMs)
           numOfTasks+=vm.getnumOfApps();

       wlds=new int[SourceType.values().length][numOfTasks+1];
       apps=new App[numOfTasks+1];
       
       int i=0;
       for(VMach vm:VMs){
           for(App a:vm.getApps()){
                for(SourceType t:SourceType.values()){
                    wlds[t.ordinal()][i]=a.getCon(t);
                }
                apps[i++]=a;
           }  
       }
       apps[i]=app;
       
       for(SourceType t:SourceType.values()){
            wlds[t.ordinal()][i]=app.getCon(t);
       }
                       
       lps.setNumOfTasks(numOfTasks+1);
       lps.setCPUWls(wlds[SourceType.CPU.ordinal()]);
       lps.setRAMWls(wlds[SourceType.RAM.ordinal()]);
       lps.setSTORAGEWls(wlds[SourceType.STORAGE.ordinal()]);
       lps.setBWIWls(wlds[SourceType.BANDWIDTH.ordinal()]);
       
       HashMap<Integer,Character> mapping=lps.solve();
       
       if(mapping.size()<numOfTasks+1 || mapping==null){
           System.out.println("App refused");
           return false;
       }
       
       
       for(Map.Entry<Integer,Character> e:mapping.entrySet()){
           int vmIndex=e.getValue()-65;
           int appIndex=e.getKey();
           App a=apps[appIndex];
           if(!VMs.get(vmIndex).containsApp(a)){
               if(appIndex<numOfTasks)
                    System.out.println("App "+a.getName()+" migrated to VM " + vmIndex);
           }
           
       }
       
       for(VMach vm:VMs)
           vm.clearApps();
       
       for(Map.Entry<Integer,Character> e:mapping.entrySet()){
           int vmIndex=e.getValue()-65;
           int appIndex=e.getKey();
           App a=apps[appIndex];
           VMs.get(vmIndex).add(a);
       }
       return true;
    }
}
