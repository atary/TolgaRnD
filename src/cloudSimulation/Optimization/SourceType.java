/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cloudSimulation.Optimization;

/**
 *
 * @author ovatman
 */
public enum SourceType {
    CPU("cpu"),RAM("ram"),STORAGE("sto"),BANDWIDTH("bwi");
    
    private String name;
    
    private SourceType(String s){
        name=s;
    }
    
    public String getName(){
        return name;
    }
    
}
