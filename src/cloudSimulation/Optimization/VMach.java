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
