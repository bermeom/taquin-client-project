/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spring.taquin.solve.heuristics;

import java.util.ArrayList;
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
                distance+=2;
                break;
            }
        }
        return distance+squareDM.get(taquin.getValueBS(i,j,n,nbits,sizeBS)).get(i).get(j);
    }
    
    public static long hDistanceManhattan( Node taquin,List<List< List<Integer> > > squareDM,int n,int nbits,int sizeBS){
        long distance=0,cost;
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                if (taquin.getValueBS(i,j,n,nbits,sizeBS)!=0){
                    //cost=1;
                    distance+=squareDM.get(taquin.getValueBS(i,j,n,nbits,sizeBS)).get(i).get(j);
                    for (int k=j-1;k>=0;k--){
                        if(taquin.getValueBS(i,j,n,nbits,sizeBS)<taquin.getValueBS(i,k,n,nbits,sizeBS)){
                            distance+=2;//Linear Conflict
                            //break;
                        }
                    }
                }
                //System.out.println(i+" "+j+" "+taquin.getValueBS(i,j,n,nbits,sizeBS)+"   "+squareDM.get(taquin.getValueBS(i,j,n,nbits,sizeBS)).get(i).get(j));
            }
        }
        
        return distance;
    }
 
    public static long hDistanceManhattanPatternDatabases( Node taquin,List<List< List<Integer> > > squareDM,int n,int nbits,int sizeBS){
        long distance=0;
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                if (taquin.getValueBS(i,j,n,nbits,sizeBS)!=0){
                    distance+=squareDM.get(taquin.getValueBS(i,j,n,nbits,sizeBS)).get(i).get(j);
                    for (int k=j-1;k>=0;k--){
                        if(taquin.getValueBS(i,j,n,nbits,sizeBS)<taquin.getValueBS(i,k,n,nbits,sizeBS)){
                            distance+=2*squareDM.get(taquin.getValueBS(i,j,n,nbits,sizeBS)).get(i).get(j);//Linear Conflict
                            break;
                        }
                    }
                }
                //System.out.println(i+" "+j+" "+taquin.getValueBS(i,j,n,nbits,sizeBS)+"   "+squareDM.get(taquin.getValueBS(i,j,n,nbits,sizeBS)).get(i).get(j));
            }
        }
        
        return distance;
    }
    
    public static long misplacedPatternDatabases( Node taquin,int n,int nbits,int sizeBS,List<Integer> cost){
        long count=0;
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                //System.out.println(taquin.getValueBS(i,j,n,nbits,sizeBS)+" == "+(Utils.getPosition(i, j, n)+1));
                if (!(taquin.getValueBS(i,j,n,nbits,sizeBS)==0||(i==n-1&&j==n-1))){ 
                    if(Utils.getPosition(i, j, n)+1!=taquin.getValueBS(i,j,n,nbits,sizeBS)){
                        //System.out.println((Utils.getPosition(i, j, n)+1)+" "+cost.size());
                        //count+=cost.get(Utils.getPosition(i, j, n)+1);
                        count+=cost.get(Utils.getPosition(i, j, n)+1);
                        //count++;
                    }
                }
            }
        }
        return count;
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
    
    public static List<List< List<Integer> > > createSquareDistanceManhattan(int n){
        List<List< List<Integer> > > square=new ArrayList< List< List<Integer> > >();
        int x=0,y=0,c=0;
        for(int k=0;k<n*n;k++){
             square.add(new ArrayList<List<Integer> >());
             if(k==0){
                x=n-1;
                y=n-1;
             }else if(k==1){
                  x=0;
                  y=0;
               }else if (x==n-1){
                        y++;
                        x=0;
                    }else{
                        x++;
                    } 
             //System.out.println(x+" "+y);
             for(int i=0;i<n;i++){
                square.get(k).add(new ArrayList<Integer>());
                for(int j=0;j<n;j++){
                    square.get(k).get(i).add(Math.abs(j-x)+Math.abs(i-y));
                } 
             }
        }
        return square;
    
    }
    
    
    public static DataHeuristics createSquareDistanceManhattanPatternDatabases(int n){
        List<List< List<Integer> > > square=new ArrayList< List< List<Integer> > >();
        List<Integer> cost=new ArrayList<Integer>();
        int x=0,y=0,c=0;
        for(int k=0;k<n*n;k++){
             square.add(new ArrayList<List<Integer> >());
             if(k==0){
                x=n-1;
                y=n-1;
             }else if(k==1){
                  x=0;
                  y=0;
               }else if (x==n-1){
                        y++;
                        x=0;
                    }else{
                        x++;
                    } 
             
            c=1;
                    
            for(int i=0;i<n;i++){
                if((y==i||x==i)){
                    c=(int) (((double)((n-i+1))*100*Math.exp(-(double)(i))));
                    cost.add(c);
                    break;
                }
            }
            for(int i=0;i<n;i++){
                square.get(k).add(new ArrayList<Integer>());
                for(int j=0;j<n;j++){
                    square.get(k).get(i).add((Math.abs(j-x)+Math.abs(i-y))*c);
                    
                } 
             }
        }
        return new DataHeuristics(square, cost);
    
    }
    
    public static List<List< List<Integer> > > createSquareDistanceManhattan1(int n){
        List<List< List<Integer> > > square=new ArrayList< List< List<Integer> > >();
        int x=0,y=0,c=0;
        for(int k=0;k<n*n;k++){
             square.add(new ArrayList<List<Integer> >());
             if(k==0){
                x=0;
                y=0;
             }else{
                if(x==3){
                    y++;
                    x=0;
                } 
             }
             //System.out.println(x+" "+y);
             for(int i=0;i<n;i++){
                square.get(k).add(new ArrayList<Integer>());
                for(int j=0;j<n;j++){
                    square.get(k).get(i).add(Math.abs(j-x)+Math.abs(i-y));
                } 
             }
             x++;
        }
        return square;
    
    }
    
    
}
