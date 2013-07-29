/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cloudSimulation.Optimization;

/**
 *
 * @author ovatman
 */
public class App {

    private String name;
    private int cpuCon;
    private int ramCon;
    private int storageCon;
    private int bwCon;

    public App(String name, int cpuCon, int ramCon, int storageCon, int bwCon) {
        this.name = name;
        this.cpuCon = cpuCon;
        this.ramCon = ramCon;
        this.storageCon = storageCon;
        this.bwCon = bwCon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCpuCon() {
        return cpuCon;
    }

    public void setCpuCon(int cpuCon) {
        this.cpuCon = cpuCon;
    }

    public int getRamCon() {
        return ramCon;
    }

    public void setRamCon(int ramCon) {
        this.ramCon = ramCon;
    }

    public int getStorageCon() {
        return storageCon;
    }

    public void setStorageCon(int storageCon) {
        this.storageCon = storageCon;
    }

    public int getBwCon() {
        return bwCon;
    }

    public void setBwCon(int bwCon) {
        this.bwCon = bwCon;
    }

    @Override
    public String toString() {
        return "App{" + "name=" + name + ", cpuCon=" + cpuCon + ", ramCon=" + ramCon + ", storageCon=" + storageCon + ", bwCon=" + bwCon + '}';
    }

    public int getCon(SourceType t) {
        switch(t){
            case CPU:
                return cpuCon;
            case RAM:
                return ramCon;
            case STORAGE:
                return storageCon;
            case BANDWIDTH:
                return bwCon;    
        }
        return 0;
    }
}
