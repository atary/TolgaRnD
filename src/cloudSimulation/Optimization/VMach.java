/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cloudSimulation.Optimization;

import java.util.ArrayList;

/**
 *
 * @author ovatman
 */
public class VMach {

    private String name;
    private int cpuCap;
    private int cpuLoad;
    private int ramCap;
    private int ramLoad;
    private int storageCap;
    private int storageLoad;
    private int bwCap;
    private int bwLoad;
    private ArrayList<App> apps;

    public VMach(String name) {
        this.name = name;
        apps = new ArrayList<App>();
    }

    public VMach(String name, int cpuCap, int ramCap, int storageCap, int bwCap) {
        this.name = name;
        this.cpuCap = cpuCap;
        this.ramCap = ramCap;
        this.storageCap = storageCap;
        this.bwCap = bwCap;
        apps = new ArrayList<App>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public double getUtil(){
        return (double)(getCpuUtil() + getRamUtil() + getStorageUtil() + getBwUtil()) / 4; 
    }
    
    public double getUtilDeviation(){
        return Math.sqrt((Math.pow((getCpuUtil() - getUtil()), 2)
                + Math.pow((getRamUtil() - getUtil()), 2)
                + Math.pow((getStorageUtil() - getUtil()), 2)
                + Math.pow((getBwUtil() - getUtil()), 2)) / 4);
    }
    
    public double getSkewness(){
        if(getUtil() == 0) return 0;
        return Math.sqrt(Math.pow((getCpuUtil()/getUtil())-1, 2)
                + Math.pow((getRamUtil()/getUtil())-1, 2)
                + Math.pow((getStorageUtil()/getUtil())-1, 2)
                + Math.pow((getBwUtil()/getUtil())-1, 2));
    }
    
    public double getSpan(){
        double max = Math.max(Math.max(getCpuUtil(),getRamUtil()),Math.max(getStorageUtil(),getBwUtil()));
        double min = Math.min(Math.min(getCpuUtil(),getRamUtil()),Math.min(getStorageUtil(),getBwUtil())); 
        return max - min;
    }
    
    public double getCumDiff(){
        double cpuDiff = Math.abs(getCpuUtil()-getRamUtil()) + Math.abs(getCpuUtil()-getStorageUtil()) + Math.abs(getCpuUtil()-getBwUtil());
        double ramDiff = Math.abs(getRamUtil()-getStorageUtil()) + Math.abs(getRamUtil()-getBwUtil());
        double storageDiff = Math.abs(getStorageUtil()-getBwUtil());
        return (cpuDiff + ramDiff + storageDiff);
    }

    public int getCpuCap() {
        return cpuCap;
    }

    public void setCpuCap(int cpuCap) {
        this.cpuCap = cpuCap;
    }

    public int getCpuLoad() {
        return cpuLoad;
    }

    public void setCpuLoad(int cpuLoad) {
        this.cpuLoad = cpuLoad;
    }
    
    public double getCpuUtil(){
        return (double)cpuLoad / cpuCap;
    }

    public int getRamCap() {
        return ramCap;
    }

    public void setRamCap(int ramCap) {
        this.ramCap = ramCap;
    }

    public int getRamLoad() {
        return ramLoad;
    }

    public void setRamLoad(int ramLoad) {
        this.ramLoad = ramLoad;
    }
    
    public double getRamUtil(){
        return (double)ramLoad / ramCap;
    }

    public int getStorageCap() {
        return storageCap;
    }

    public void setStorageCap(int storageCap) {
        this.storageCap = storageCap;
    }

    public int getStorageLoad() {
        return storageLoad;
    }

    public void setStorageLoad(int storageLoad) {
        this.storageLoad = storageLoad;
    }
    
    public double getStorageUtil(){
        return (double)storageLoad / storageCap;
    }

    public int getBwCap() {
        return bwCap;
    }

    public void setBwCap(int bwCap) {
        this.bwCap = bwCap;
    }

    public int getBwLoad() {
        return bwLoad;
    }

    public void setBwLoad(int bwLoad) {
        this.bwLoad = bwLoad;
    }
    
    public double getBwUtil(){
        return (double)bwLoad / bwCap;
    }

    public ArrayList<App> getApps() {
        return apps;
    }
    
    public int getnumOfApps() {
        return apps.size();
    }
    @Override
    public String toString() {
        return "VMach{" + "name=" + name + ", cpu=" + cpuLoad + "/" + cpuCap + ", ram=" + ramLoad + "/" + ramCap + ", bw=" + bwLoad + "/" + bwCap + ", storage=" + storageLoad + "/" + storageCap + '}';
    }

    public boolean containsApp(App a){
        if(apps.contains(a))
            return true;
        else return false;
    }
    
    public App get(String name) {
        for (App a : apps) {
            if (a.getName().equals(name)) {
                return a;
            }
        }
        return null;
    }

    public boolean add(App e) {
        if( cpuLoad + e.getCpuCon() > cpuCap ||
            ramLoad + e.getRamCon() > ramCap ||
            storageLoad + e.getStorageCon() > storageCap ||
            bwLoad + e.getBwCon() > bwCap     
        )
            return false;
        
        cpuLoad += e.getCpuCon();
        ramLoad += e.getRamCon();
        storageLoad += e.getStorageCon();
        bwLoad += e.getBwCon();

        return apps.add(e);
    }

    public boolean remove(App e) {
        if(!apps.contains(e))
            return false;
        
        cpuLoad -= e.getCpuCon();
        ramLoad -= e.getRamCon();
        storageLoad -= e.getStorageCon();
        bwLoad -= e.getBwCon();

        return apps.remove(e);
    }

    void clearApps() {
        apps.clear();
        cpuLoad = 0;
        ramLoad = 0;
        storageLoad = 0;
        bwLoad = 0;
        
    }
}
