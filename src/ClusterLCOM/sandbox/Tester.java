package ClusterLCOM.sandbox;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Tester {


	public static void main(String args[]) throws IOException{
		//String s=readFile("C:/Users/itü/Desktop/Dropbox/Dropbox/AtakanAral/COHESION DERGI/Clusters/deneme.txt");
		String s=readFile("io/jedit.txt");
		
                List<?> allClusters=getClusters(s);
                HashMap<Integer,Integer> replacedMethods=getDifference((Map<?,HashSet<Integer>>)allClusters.get(0),(Map<?,HashSet<Integer>>)allClusters.get(1));
                
                //System.out.println(replacedMethods.toString());
	         
                //System.out.println(((Map<Integer,HashSet<Integer>>)allClusters.get(2)).toString());
	
                //System.out.println(calculateMetrics((Map<Integer,HashSet<Integer>>)allClusters.get(2),replacedMethods).toString());

               System.out.println(calculateMetrics2((Map<Integer,HashSet<Integer>>)allClusters.get(1),(Map<Integer,HashSet<Integer>>)allClusters.get(2)));
               /*System.out.println(num);
               
               System.out.println(((Map<Integer,HashSet<Integer>>)allClusters.get(0)).size());
               System.out.println(((Map<Integer,HashSet<Integer>>)allClusters.get(1)).size());
               System.out.println(((Map<Integer,HashSet<Integer>>)allClusters.get(2)).size());*/
	}
	
	
	
    private static HashMap<Integer,Integer> calculateMetrics2(Map<Integer,HashSet<Integer>> LMSet,Map<Integer,HashSet<Integer>> CMSet) {
        
    	HashMap<Integer,Integer> metrics= new HashMap<Integer,Integer>();
    	
    	int num=0;
    	for(HashSet<Integer> me: LMSet.values()){
        	if(me.size()!=1){
        		for(Integer i:me){
        			for(Entry<Integer, HashSet<Integer>> e:CMSet.entrySet()){
        				if(e.getValue().contains(i)){
        					if(metrics.containsKey(i)) metrics.put(e.getKey(), (metrics.get(i))+1);
        					else metrics.put(e.getKey(), 1);
        				}
        			}
        		}
        	}
        }
    	
    	return metrics;
	}



		private static HashMap<Integer,Integer> calculateMetrics(Map<Integer,HashSet<Integer>> classes,HashMap<Integer,Integer> replacedMethods){
            HashMap<Integer,Integer> metrics= new HashMap<Integer,Integer>();
            
            for(Map.Entry<Integer,HashSet<Integer>> clss:classes.entrySet()){
                metrics.put(clss.getKey(), 0);
                for(Integer method:clss.getValue()){
                    if(replacedMethods.containsKey(method)){
                        if(metrics.containsKey(clss.getKey()))
                            metrics.put(clss.getKey(),metrics.get(clss.getKey())+replacedMethods.get(method));
                    }
                }
            }
            
            return metrics;

        }        
        
        private static HashMap<Integer,Integer> getDifference(Map<?,HashSet<Integer>> from,Map<?,HashSet<Integer>> to){
            
            int maxcommon=-1;
            Set<Integer> temp,maxcommonSet=null;
            HashMap<Integer,Integer> swapops=new HashMap<Integer,Integer>();
            
            for(HashSet<Integer> base:from.values()){
                for(HashSet<Integer> diff:to.values()){
                    temp=new HashSet<Integer>(diff);
                    temp.retainAll(base);
                    if(temp.size()>maxcommon){
                        maxcommon=temp.size();
                        maxcommonSet=new HashSet<Integer>(diff);
                    }
                }
                base.removeAll(maxcommonSet);
                for(Integer i:base){
                    if(swapops.containsKey(i)){
                        swapops.put(i,swapops.get(i)+1);
                    }
                    else{ swapops.put(i, 1);}
                }
                maxcommon=-1;
            }
            
            return swapops;
        }
	
	private static List<?> getClusters(String s) {
		String[] clusters=s.split("\\(");
		String temp;
		
		HashMap<Integer, HashSet<Integer>> clusterMap;
		ArrayList<Map<?,?>> allClusters=new ArrayList<Map<?,?>>();
		
		for(String clust:clusters)
			if(Character.isDigit(clust.charAt(0))){
				clusterMap = new HashMap<Integer, HashSet<Integer>>();
				temp=clust.substring(0, clust.indexOf(')'));
				int i=1;
				for(String num:temp.split(",")){
					Integer clustNo=Integer.parseInt(num.trim());
					if(!clusterMap.containsKey(clustNo))
						clusterMap.put(clustNo, new HashSet<Integer>());
					clusterMap.get(clustNo).add(i++);
				}
				allClusters.add(clusterMap);
					
			}
		
		return allClusters;
	}


	public static String readFile(String fileName) throws IOException{
		String s="";
		BufferedReader br = new BufferedReader(new FileReader(fileName));
	    try {
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();

	        while (line != null) {
	            sb.append(line);
	            sb.append("\n");
	            line = br.readLine();
	        }
	        s = sb.toString();
	    } finally {
	        br.close();
	    }
	    return s;
	}

}
