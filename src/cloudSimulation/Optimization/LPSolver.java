/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cloudSimulation.Optimization;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import net.sf.javailp.Linear;
import net.sf.javailp.OptType;
import net.sf.javailp.Problem;
import net.sf.javailp.Result;
import net.sf.javailp.Solver;
import net.sf.javailp.SolverFactory;
import net.sf.javailp.SolverFactoryLpSolve;

/**
 *
 * @author ovatman
 */

public class LPSolver {

    private SolverFactory factory;
    private int numOfTasks;
    private int numOfVms;
    private int[][] wls;
    private int[][] capacities;
    
    public LPSolver() {
        factory = new SolverFactoryLpSolve(); // use lp_solve
        factory.setParameter(Solver.VERBOSE, 0);
        factory.setParameter(Solver.TIMEOUT, 10); // set timeout to 100 seconds
        numOfTasks=9;
        numOfVms=3;
        wls=new int[SourceType.values().length][numOfTasks];
        capacities=new int[numOfVms][SourceType.values().length];
        
        //wls[SourceType.CPU.ordinal()]       = new int[]{30,10,10,10,30,10,10,10,30,10};
        //wls[SourceType.RAM.ordinal()]       = new int[]{10,30,10,10,10,30,10,10,10,30};
        //wls[SourceType.STORAGE.ordinal()]   = new int[]{10,10,30,10,10,10,30,10,10,10};
        //wls[SourceType.BANDWIDTH.ordinal()] = new int[]{10,10,10,30,10,10,10,30,10,10};
    }

    public int getNumOfTasks() {
        return numOfTasks;
    }

    public int getNumOfVms() {
        return numOfVms;
    }

    public void setNumOfVms(int numOfVms) {
        this.numOfVms = numOfVms;
        capacities=new int[numOfVms][SourceType.values().length];
    }

    public void setCapacities(ArrayList<VMach> VMs){
        int i=0;
        for(VMach vm:VMs){
            capacities[i][SourceType.CPU.ordinal()]=vm.getCpuCap();
            capacities[i][SourceType.RAM.ordinal()]=vm.getRamCap();
            capacities[i][SourceType.STORAGE.ordinal()]=vm.getStorageCap();
            capacities[i][SourceType.BANDWIDTH.ordinal()]=vm.getBwCap();
            i++;
        }
    }
    
    public void setNumOfTasks(int numOfTasks) {
        this.numOfTasks = numOfTasks;
        wls=new int[SourceType.values().length][numOfTasks];
    }

    public void setCPUWls(int[] wls) {
        for(int i=0;i<numOfTasks;i++)
            this.wls[SourceType.CPU.ordinal()][i]=wls[i];
    }
    
    public void setRAMWls(int[] wls) {
        for(int i=0;i<numOfTasks;i++)
            this.wls[SourceType.RAM.ordinal()][i]=wls[i];
    }
    
    public void setSTORAGEWls(int[] wls) {
        for(int i=0;i<numOfTasks;i++)
            this.wls[SourceType.STORAGE.ordinal()][i]=wls[i];
    }
        
    public void setBWIWls(int[] wls) {
        for(int i=0;i<numOfTasks;i++)
            this.wls[SourceType.BANDWIDTH.ordinal()][i]=wls[i];
    }
    
    public HashMap<Integer,Character> solve(){
        
        Problem problem = new Problem();

        Linear linear = new Linear();
        
        //Set objective: Server A-B-C cpu wlds summed should be max
        int indCount=0;
        char ind;
        for(indCount=0,ind='A';indCount<numOfVms;indCount++,ind++)
            for(SourceType t:SourceType.values())
                linear.add(1, t.getName()+ind);   
        
        problem.setObjective(linear, OptType.MAX);

        //Each task should be assigned to at most one server
        for(int i=0;i<=numOfTasks;i++){ 
            linear = new Linear();
            for(indCount=0,ind='A';indCount<numOfVms;indCount++,ind++)
                linear.add(1, ""+ind+i);
            problem.add(linear, "<=", 1);                   
        }

        //for each server
        for(indCount=0,ind='A';indCount<numOfVms;indCount++,ind++)
            for(SourceType t:SourceType.values()){
                //Sum up all the assigned tasks... should be equal to cpu wld of the server
                linear = new Linear();
                for(int i=0;i<numOfTasks;i++)    
                    linear.add(wls[t.ordinal()][i], ""+ind+i);  
                linear.add(-1, t.getName()+ind);
                problem.add(linear, "=", 0);
            }

        for(indCount=0,ind='A';indCount<numOfVms;indCount++,ind++)
            for(SourceType t:SourceType.values())
                problem.setVarType(t.getName()+ind, Integer.class);

        for(indCount=0,ind='A';indCount<numOfVms;indCount++,ind++)
                for(int i=0;i<=numOfTasks;i++)    
                    problem.setVarType(""+ind+i, Boolean.class);
        
        for(indCount=0,ind='A';indCount<numOfVms;indCount++,ind++)
            for(SourceType t:SourceType.values())
                problem.setVarUpperBound(t.getName()+ind, capacities[indCount][t.ordinal()]);
        
        Solver solver = factory.get();
        Result result = solver.solve(problem);
        
        if(result==null)
            return null;
        
        HashMap<Integer,Character> mapping= new HashMap<Integer,Character>();
        
        String res=result.toString();
        res=res.substring(res.indexOf('{')+1, res.lastIndexOf('}'));
        String[] s=res.split(", ");
        Arrays.sort(s);
        for(String sub:s){
            if(sub.contains("cpu") || sub.contains("ram") || sub.contains("sto") || sub.contains("bwi") )
                System.out.println(sub);
            else if(sub.charAt(sub.length()-1)!='0'){
                System.out.println(sub);
                String tmp=sub.substring(0, sub.indexOf('='));
                int key=Integer.parseInt(tmp.substring(1));
                char val=tmp.charAt(0);
                mapping.put(key, val);
            }
        }   
       return mapping; 
    }
    
    public int[] getRandomArray(int size){
        int[] ret=new int[size];
        
        Random r=new Random();
        
        for(int i=0;i<size;i++)
            ret[i]=r.nextInt(20)+10;
            
        return ret;
    }
}
