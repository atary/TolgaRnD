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
    private int total = 0;
    
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
        VMs.add(new VMach("ONE", 1000, 1000, 1000, 1000));

        VMs.add(new VMach("TWO", 1000, 1000, 1000, 1000));

        VMs.add(new VMach("THREE", 1000, 1000, 1000, 1000));
        
        VMs.add(new VMach("FOUR", 1000, 1000, 1000, 1000));
        
        VMs.add(new VMach("FIVE", 1000, 1000, 1000, 1000));
        
        VMs.add(new VMach("SIX", 1000, 1000, 1000, 1000));
        
        VMs.add(new VMach("SEVEN", 1000, 1000, 1000, 1000));
        
        VMs.add(new VMach("EIGHT", 1000, 1000, 1000, 1000));
        
        VMs.add(new VMach("NINE", 1000, 1000, 1000, 1000));
        
        VMs.add(new VMach("TEN", 1000, 1000, 1000, 1000));
    }

    public boolean submitApp(int i, App app) {
        //Original round-robin
        if (!VMs.get(i).add(app)) {
            int j = i;
            do {
                j = (j + 1) % VMs.size();
                if (VMs.get(j).add(app)) {
                    //System.out.print("App added to VM " + (j + 1) + " instead of VM " + (i + 1)+"\n");
                    return true;
                }
            } while (j != i);
        } else {
            return true;
        }
        //return submitApp(app);
        total += (it-1);
        return false;
    }
    
    public boolean submitApp2(int j, App app) {
        //Xiao's lowest skewness
        double minSkewness = Double.POSITIVE_INFINITY;
        int minSkewnessVMid = -1;
        for(int i = 0; i < VMs.size(); i++){
            if(!VMs.get(i).add(app)){
                System.out.print("Skewness " + (i + 1) + ": " + "-" + ".\n");
                continue;
            }
            double skewness = VMs.get(i).getSkewness();
            System.out.print("Skewness " + (i + 1) + ": " + skewness + ".\n");
            if(skewness < minSkewness){
                minSkewness = skewness;
                minSkewnessVMid = i;
            }
            VMs.get(i).remove(app);
        }
        if(minSkewnessVMid > -1){
            VMs.get(minSkewnessVMid).add(app);
            System.out.print("App added to VM " + (minSkewnessVMid + 1) + ".\n");
            return true;
        }
        //
        return submitApp(app);
    }
    
    public boolean submitApp3(int j, App app) {
        //Xiao's most decreasing skewness
        double biggestDecrease = Double.NEGATIVE_INFINITY;
        int biggestDecreaseId = -1;
        for(int i = 0; i < VMs.size(); i++){
            double beginning = VMs.get(i).getMinDiff();
            if(!VMs.get(i).add(app)){
                //System.out.print("Skewness change " + (i + 1) + ": " + "-" + ".\n");
                continue;
            }
            double end = VMs.get(i).getMinDiff();
            //System.out.print("Skewness change " + (i + 1) + ": " + (beginning-end) + ".\n");
            if(beginning-end > biggestDecrease){
                biggestDecrease = beginning-end;
                biggestDecreaseId = i;
            }
            VMs.get(i).remove(app);
        }
        if(biggestDecreaseId > -1){
            VMs.get(biggestDecreaseId).add(app);
            //System.out.print("App added to VM " + (biggestDecreaseId + 1) + ".\n");
            return true;
        }
        //return submitApp(app);
        total += (it-1);
        return false;
    }
    
    public boolean submitApp4(int j, App app) {
        //Lowest standard deviation
        double minDeviation = Double.POSITIVE_INFINITY;
        int minDeviationVMid = -1;
        for(int i = 0; i < VMs.size(); i++){
            if(!VMs.get(i).add(app)){
                System.out.print("Standard deviation " + (i + 1) + ": " + "-" + ".\n");
                continue;
            }
            double deviation = VMs.get(i).getUtilDeviation();
            System.out.print("Standard deviaton " + (i + 1) + ": " + deviation + ".\n");
            if(deviation < minDeviation){
                minDeviation = deviation;
                minDeviationVMid = i;
            }
            VMs.get(i).remove(app);
        }
        if(minDeviationVMid > -1){
            VMs.get(minDeviationVMid).add(app);
            System.out.print("App added to VM " + (minDeviationVMid + 1) + ".\n");
            return true;
        }
        //
        return submitApp(app);
    }
    
    public boolean submitApp5(int j, App app) {
        //Lowest span (max - min)
        double minSpan = Integer.MAX_VALUE;
        int minSpanVMid = -1;
        for(int i = 0; i < VMs.size(); i++){
            if(!VMs.get(i).add(app)){
                //System.out.println("Span " + (i + 1) + ": " + "-" + ".");
                continue;
            }
            double span = VMs.get(i).getSpan();
            //System.out.println("Span " + (i + 1) + ": " + span + ".");
            if(span < minSpan){
                minSpan = span;
                minSpanVMid = i;
            }
            VMs.get(i).remove(app);
        }
        if(minSpanVMid > -1){
            VMs.get(minSpanVMid).add(app);
            //System.out.println("App added to VM " + (minSpanVMid + 1) + ".");
            return true;
        }
        //return submitApp(app);
        //System.out.println(it-1);
        total += (it-1);
        return false;
    }
    
    public boolean submitApp6(int j, App app) {
        //Most decreasing span
        double biggestDecrease = Double.NEGATIVE_INFINITY;
        int biggestDecreaseId = -1;
        for(int i = 0; i < VMs.size(); i++){
            double beginning = VMs.get(i).getSpan();
            if(!VMs.get(i).add(app)){
                System.out.print("Span change " + (i + 1) + ": " + "-" + ".\n");
                continue;
            }
            double end = VMs.get(i).getSpan();
            System.out.print("Span change " + (i + 1) + ": " + (beginning-end) + ".\n");
            if(beginning-end > biggestDecrease){
                biggestDecrease = beginning-end;
                biggestDecreaseId = i;
            }
            VMs.get(i).remove(app);
        }
        if(biggestDecreaseId > -1){
            VMs.get(biggestDecreaseId).add(app);
            System.out.println("App added to VM " + (biggestDecreaseId + 1) + ".");
            return true;
        }
        //
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
    
    public void go(){
        for(int i=0; i<1000; i++){
            boolean yield = true;
            while (yield) {
                yield = step();
            }
            it = 0;
            for (VMach vm : VMs) {
                vm.clearApps();
            }
        }
        System.out.println((double)total/1000);
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
            System.out.println("App refused!!!!!");
            return false;
        }

        int numMigrations = 0;
        for (Map.Entry<Integer, Character> e : mapping.entrySet()) {
            int vmIndex = e.getValue() - 65;
            int appIndex = e.getKey();
            App a = apps[appIndex];
            if (!VMs.get(vmIndex).containsApp(a)) {
                if (appIndex < numOfTasks) {
                    System.out.println("App " + a.getName() + " migrated to VM " + vmIndex);
                    numMigrations++;
                }
            }

        }
        System.out.println("Count: "+numMigrations);

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
        
        //status.delete(0, status.length());     
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
