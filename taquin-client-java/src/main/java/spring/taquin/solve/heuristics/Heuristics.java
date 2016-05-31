/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spring.taquin.solve.heuristics;

import java.util.List;
import spring.taquin.solve.Node;
import spring.taquin.solve.Utils;

/**
 *
 * @author berme_000
 */
public class Heuristics {
    
    public static long hDistanceManhattan( List<List<Integer> > taquin,List<List< List<Integer> > > squareDM){
        long distance=0;
        for(int i=0;i<taquin.size();i++){
            for(int j=0;j<taquin.get(i).size();j++){
                distance+=squareDM.get(taquin.get(i).get(j)).get(i).get(j);
                for (int k=j-1;k>=0;k--){
                    if(taquin.get(i).get(j)>taquin.get(i).get(k)){
                        //distance++;
                        break;
                    }
                }
                //System.out.println(i+" "+j+" "+taquin.get(i).get(j)+"   "+squareDM.get(taquin.get(i).get(j)).get(i).get(j));
            }
        }
        return distance;
    }
    
    public static long hDistanceManhattan( Node taquin,int i,int j,List<List< List<Integer> > > squareDM,int n,int nbits,int sizeBS){
        long distance=0;
        for (int k=j-1;k>=0;k--){
            if(taquin.getValueBS(i,j,n,nbits,sizeBS)<taquin.getValueBS(i,k,n,nbits,sizeBS)){
                distance+=1;
                break;
            }
        }
        return distance+squareDM.get(taquin.getValueBS(i,j,n,nbits,sizeBS)).get(i).get(j);
    }
    
    public static long hDistanceManhattan( Node taquin,List<List< List<Integer> > > squareDM,int n,int nbits,int sizeBS){
        long distance=0;
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                if (taquin.getValueBS(i,j,n,nbits,sizeBS)!=0){
                    distance+=squareDM.get(taquin.getValueBS(i,j,n,nbits,sizeBS)).get(i).get(j);
                    for (int k=j-1;k>=0;k--){
                        if(taquin.getValueBS(i,j,n,nbits,sizeBS)<taquin.getValueBS(i,k,n,nbits,sizeBS)){
                            distance+=1;
                            break;
                        }
                    }
                    /*       
                    for (int k=i-1;k>=0;k--){
                        if(taquin.getValueBS(i,j,n,nbits,sizeBS)<taquin.getValueBS(k,j,n,nbits,sizeBS)){
                            distance++;
                            break;
                        }
                    }
                    //*/
                }
                //System.out.println(i+" "+j+" "+taquin.getValueBS(i,j,n,nbits,sizeBS)+"   "+squareDM.get(taquin.getValueBS(i,j,n,nbits,sizeBS)).get(i).get(j));
            }
        }
        
        return distance;
    }
    
    public static long misplaced( Node taquin,int n,int nbits,int sizeBS){
        long count=0;
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                if (taquin.getValueBS(i,j,n,nbits,sizeBS)!=0){
                    
                    if(Utils.getPosition(i, j, n)+1!=taquin.getValueBS(i,j,n,nbits,sizeBS)){
                        count++;
                    }
                }
            }
        }
        return count;
    }
    
}
