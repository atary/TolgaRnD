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
    private int it,numOfTasks = 0;
    private StringBuilder status;
    
    public SimEngine() {
        status=new StringBuilder();
        numOfTasks = 0;
        it=0;
        VMs = new ArrayList<VMach>();

    }

    public ArrayList<VMach> getVMs(){
        return VMs;
    }
    
    
    public void createVMs() {
        VMs.clear();
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
                    status.append("App added to VM " + (j + 1) + " instead of VM " + (i + 1)+"\n");
                    return true;
                }
            } while (j != i);
        } else {
            return true;
        }

        return submitApp(app);
    }

    public void printVMs() {

        for (VMach v : VMs) {
            System.out.println(v);
        }

    }

    public void go(int freq, int maxit) throws InterruptedException {

        it = 0;
        boolean yield = true;

        while (it < maxit) {
            yield = step();
            if (!yield) {
                break;
            }
            Thread.sleep(freq);
        }
    }

    private boolean submitApp(App app) {
        LPSolver lps = new LPSolver();
        lps.setNumOfVms(3);
        lps.setCapacities(VMs);
        int numOfTasks = 0;
        int[][] wlds;
        App[] apps;

        for (VMach vm : VMs) {
            numOfTasks += vm.getnumOfApps();
        }

        wlds = new int[SourceType.values().length][numOfTasks + 1];
        apps = new App[numOfTasks + 1];

        int i = 0;
        for (VMach vm : VMs) {
            for (App a : vm.getApps()) {
                for (SourceType t : SourceType.values()) {
                    wlds[t.ordinal()][i] = a.getCon(t);
                }
                apps[i++] = a;
            }
        }
        apps[i] = app;

        for (SourceType t : SourceType.values()) {
            wlds[t.ordinal()][i] = app.getCon(t);
        }

        lps.setNumOfTasks(numOfTasks + 1);
        lps.setCPUWls(wlds[SourceType.CPU.ordinal()]);
        lps.setRAMWls(wlds[SourceType.RAM.ordinal()]);
        lps.setSTORAGEWls(wlds[SourceType.STORAGE.ordinal()]);
        lps.setBWIWls(wlds[SourceType.BANDWIDTH.ordinal()]);

        HashMap<Integer, Character> mapping = lps.solve();

        if (mapping == null || mapping.size() < numOfTasks + 1) {
            status.append("App refused!!!!!"+"\n");
            return false;
        }


        for (Map.Entry<Integer, Character> e : mapping.entrySet()) {
            int vmIndex = e.getValue() - 65;
            int appIndex = e.getKey();
            App a = apps[appIndex];
            if (!VMs.get(vmIndex).containsApp(a)) {
                if (appIndex < numOfTasks) {
                    status.append("App " + a.getName() + " migrated to VM " + vmIndex+"\n");
                }
            }

        }

        for (VMach vm : VMs) {
            vm.clearApps();
        }

        for (Map.Entry<Integer, Character> e : mapping.entrySet()) {
            int vmIndex = e.getValue() - 65;
            int appIndex = e.getKey();
            App a = apps[appIndex];
            VMs.get(vmIndex).add(a);
        }
        return true;
    }

    public boolean step() {
        
        status.delete(0, status.length());     
        int numOfVMs = 3;
        Random r = new Random();
        boolean yield = true;
        
        if (r.nextInt(5) > -1) {
            //numOfTasks++%numOfVMs,
            yield = submitApp(numOfTasks++ % numOfVMs, new App("App" + it, r.nextInt(30), r.nextInt(30), r.nextInt(30), r.nextInt(30)));
            //yield=submitApp(new App("App" + i, r.nextInt(10) + 5, r.nextInt(10) + 5, r.nextInt(10) + 5, r.nextInt(10) + 5));
        }

        //printLog();
        it++;

        return yield;

    }

    void reset() {
        numOfTasks = 0;
        it=0;
        createVMs();
     }

    private void printLog() {
        printVMs();
        System.out.println("Iteration: " + it);
        System.out.println("-----------------------------------------------------------------------------");
    }
    
    public String printLogTo() {
        StringBuilder s=new StringBuilder();
        
        for (VMach v : VMs) {
            s.append(v+"\n");
        }
        s.append("Iteration: " + it+"\n");
        
        s.append(status.toString());
        
        s.append("--------------------------------------"+"\n");
        return s.toString();
    }
}
