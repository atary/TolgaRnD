/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cloudSimulation.MathUtils;

import cloudSimulation.CloudUtils.UtilizationModels.DistributionType;
import java.util.HashMap;
import org.apache.commons.math3.distribution.*;

/**
 *
 * @author ovatman
 */
public class Distributor {
    
    private IntegerDistribution intDist;
    private RealDistribution realDist;
    private DistributionType dtype;
    
    private HashMap<String ,Double> parameters;
    
    public Distributor(){
        realDist=new NormalDistribution(50, 10);
        dtype=DistributionType.NORMALDISTRIBUTION;
        parameters=new HashMap<String,Double>();
        setDefaultParam();
        
    }
    
       private void setDefaultParam() {
       parameters.put("SysErr",0.0);
       parameters.put("RandomErr",0.0);
       parameters.put("NormalMean",50.0);
       parameters.put("NormalVar",10.0);
       parameters.put("PoiMean",4.0);
       parameters.put("PoiEps",1.0);
       parameters.put("ZipfNoE",100.0);
       parameters.put("ZipfExpo",1.0);
    }
       
    public void setParam(String name,double val){
        if(parameters.containsKey(name))
            parameters.put(name,val);
    }
    
        
    public double getParam(String name){
        if(parameters.containsKey(name))
            return parameters.get(name);
        return 0.0;
    }   
    
    public double getNextDistValue(int lastValue){
        switch(dtype){
            case NORMALDISTRIBUTION:
                return realDist.density(lastValue);
            case POISSONDISTRIBUTION:
                return intDist.probability(lastValue);
            case ZIPFDISTRIBUTION:
                return intDist.probability(lastValue);
            default:
                return 0;
        }
            
    }

    void setDistribution(DistributionType distributionType) {
       
        switch(distributionType){
            case NORMALDISTRIBUTION:
                dtype=DistributionType.NORMALDISTRIBUTION;
                realDist=new NormalDistribution(parameters.get("NormalMean"), parameters.get("NormalVar"));
            break;
            case POISSONDISTRIBUTION:
                dtype=DistributionType.POISSONDISTRIBUTION;
                intDist=new PoissonDistribution(parameters.get("PoiMean"),parameters.get("PoiEps"));
            break;
            case ZIPFDISTRIBUTION:
                dtype=DistributionType.ZIPFDISTRIBUTION;
                intDist=new ZipfDistribution(parameters.get("ZipfNoE").intValue(), parameters.get("ZipfExpo"));
            break;
            default:
                dtype=DistributionType.NORMALDISTRIBUTION;
                realDist=new NormalDistribution(parameters.get("NormalMean"), parameters.get("NormalVar"));
            break;
                
        }
    }

    public void reDist() {
        switch(dtype){
            case NORMALDISTRIBUTION:
                realDist=new NormalDistribution(parameters.get("NormalMean"), parameters.get("NormalVar"));
            break;
            case POISSONDISTRIBUTION:
                intDist=new PoissonDistribution(parameters.get("PoiMean"),parameters.get("PoiEps"));
            break;
            case ZIPFDISTRIBUTION:
                intDist=new ZipfDistribution(parameters.get("ZipfNoE").intValue(), parameters.get("ZipfExpo"));
            break;
            default:
                realDist=new NormalDistribution(parameters.get("NormalMean"), parameters.get("NormalVar"));
            break;
        }
           
    }


}
