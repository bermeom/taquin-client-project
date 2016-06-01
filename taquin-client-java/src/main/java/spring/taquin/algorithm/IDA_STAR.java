/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spring.taquin.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Stack;
import java.util.concurrent.PriorityBlockingQueue;
import javax.crypto.Mac;
import spring.taquin.solve.Node;
import spring.taquin.solve.Utils;
import spring.taquin.solve.heuristics.DataHeuristics;
import spring.taquin.solve.heuristics.Heuristics;

/**
 *
 * @author berme_000
 */
public class IDA_STAR {
    
    
    
    private int nbits;
    private int sizeBS;
    private int n;
    private BitSet goal;    
    private boolean found;
    private int []x={1,0,-1, 0};
    private int []y={0,1, 0,-1};
    private List<List<List< List<Integer> > > > allDistanceManhattan;
    private List<DataHeuristics> allDMPatternDatabases;
    private Map<BitSet,Node> maks;
    private Node rootT;
    public IDA_STAR() {
        
        this.allDistanceManhattan=new ArrayList<>();
        this.allDMPatternDatabases=new ArrayList<>();
        for(int i=2;i<=10;i++){
            allDistanceManhattan.add(Heuristics.createSquareDistanceManhattan(i));
            allDMPatternDatabases.add(Heuristics.createSquareDistanceManhattanPatternDatabases(i));
        }
    }
    public void ida_star(Node rootT, int n,int nbits, int sizeBS, BitSet goal){
        this.nbits = nbits;
        this.sizeBS = sizeBS;
        this.n = n;
        this.goal = goal;
        this.rootT=rootT;
        long bound=Heuristics.hDistanceManhattan(rootT, this.allDistanceManhattan.get(this.n-2),this.n,this.nbits,sizeBS);
        long t=1000000000;
        this.found=false;
        this.maks=new Hashtable<>();
        t=0;
        System.out.println(Heuristics.misplaced(rootT, n, nbits, sizeBS));
        //while(found==false){
        System.out.println("\t"+Utils.bitSetToStringln(this.n, this.nbits, this.sizeBS, this.rootT.getTaquinBS()));
        
        //for(int i=0;i<10000;i++){
            bound=Heuristics.hDistanceManhattan(rootT, this.allDistanceManhattan.get(this.n-2),this.n,this.nbits,sizeBS);
            bound=Math.max(bound, Heuristics.misplaced(this.rootT,this.n,this.nbits,sizeBS));
            t=search(this.rootT, t, bound);
            System.out.println("++++++++++++++++++++++++++++++++++++++>>> "+t);
        //}
        //}
        System.out.println("Finish "+t);
    }
   
     public void ida_starPatternDatabase(Node rootT, int n,int nbits, int sizeBS, BitSet goal){
        this.nbits = nbits;
        this.sizeBS = sizeBS;
        this.n = n;
        this.goal = goal;
        this.rootT=rootT;
        long bound=Heuristics.hDistanceManhattan(rootT, this.allDistanceManhattan.get(this.n-2),this.n,this.nbits,sizeBS);
        long t=1000000000;
        this.found=false;
        System.out.println("\t"+Utils.bitSetToStringln(this.n, this.nbits, this.sizeBS, this.rootT.getTaquinBS()));
        t=0;        
        this.maks=new Hashtable<>();
        //while(found==false){
            bound=Heuristics.hDistanceManhattanPatternDatabases(this.rootT, this.allDMPatternDatabases.get(this.n-2).getDistanceManhattan(),this.n,this.nbits,sizeBS);
            bound=Math.max(bound, Heuristics.misplacedPatternDatabases(this.rootT,this.n,this.nbits,sizeBS,this.allDMPatternDatabases.get(this.n-2).getCost()));
            t=searchPatternDatabases(this.rootT, t, bound);
            /*
            bound=Heuristics.hDistanceManhattanPatternDatabases(this.rootT, this.allDMPatternDatabases.get(this.n-2).getDistanceManhattan(),this.n,this.nbits,sizeBS);
            bound=Math.max(bound, Heuristics.misplacedPatternDatabases(this.rootT,this.n,this.nbits,sizeBS,this.allDMPatternDatabases.get(this.n-2).getCost()));
            t=search(this.rootT, t, bound);
            */
        //}
        System.out.println("Finish "+t);
    }
   
    public long search(Node node,long g,long bound){
        long min=bound*bound+g,min2,f,h;   
        int i,j;
        Node aux,min_ = null;
        PriorityQueue<NodeSearch> fs=new PriorityQueue<NodeSearch>(new NodeSearchComparator());
        this.maks.put(node.getTaquinBS(), node);
        fs.add(new NodeSearch(bound, g, node));
        NodeSearch ns;
        System.out.println("-------------> Bound min: "+min);
        while(!fs.isEmpty()){
            ns=fs.poll();
            f=ns.getF();
            /*
            System.out.println(fs.size());
            System.out.println("===-======= F= "+f+" G= "+ns.getG()+" H= "+ns.getH()+" "+Heuristics.hDistanceManhattan(ns.getNode(), this.allDistanceManhattan.get(this.n-2),this.n,this.nbits,sizeBS));
            System.out.println("\t"+Utils.bitSetToStringln(this.n, this.nbits, this.sizeBS, ns.getNode().getTaquinBS()));
            //*/
            if(is_goal(ns.getNode())){
                System.out.println("====+====== F= "+f+" G= "+ns.getG()+" H= "+ns.getH()+" "+Heuristics.hDistanceManhattan(ns.getNode(), this.allDistanceManhattan.get(this.n-2),this.n,this.nbits,sizeBS));
                System.out.println("\t"+Utils.bitSetToStringln(this.n, this.nbits, this.sizeBS, ns.getNode().getTaquinBS()));
                this.found=true;
                this.rootT=ns.getNode();
                return 0;
            } 
            /*
            if (min>f&&(!this.rootT.equals(ns.getNode()))){
                System.out.println("======>>>===== F= "+f+" G= "+ns.getG()+" H= "+ns.getH());
                System.out.println("\t"+Utils.bitSetToStringln(this.n, this.nbits, this.sizeBS, ns.getNode().getTaquinBS()));
                System.out.println("++++++++++++++++++++ "+min+" "+f);
                min=f;
                this.rootT=ns.getNode();
                //return ns.getG();
            }
            //*/
            
            for(int k=0;k<4;k++){
                i=x[k]+ns.getNode().getI_puzzle();
                j=y[k]+ns.getNode().getJ_puzzle();
                if (boundaryValidation(i, 0,this.n-1)&&boundaryValidation(j, 0,this.n-1)){
                    aux=movePuzzle(ns.getNode(), i, j);
                    if (this.maks.get(aux.getTaquinBS())==null){
                        //h=ns.getH()-Heuristics.hDistanceManhattan(ns.getNode(),i,j,this.allDistanceManhattan.get(this.n-2),this.n,this.nbits,sizeBS);
                        //h+=Heuristics.hDistanceManhattan(aux,ns.getNode().getI_puzzle(),ns.getNode().getJ_puzzle(),this.allDistanceManhattan.get(this.n-2),this.n,this.nbits,sizeBS);
                        h=Heuristics.hDistanceManhattan(aux, this.allDistanceManhattan.get(this.n-2),this.n,this.nbits,sizeBS);
                        h=Math.max(h, Heuristics.misplaced(aux,this.n,this.nbits,sizeBS));
                        //fs.add(new NodeSearch((long)((double)h*Math.sqrt((double)h+ns.getG()+1)), ns.getG()+1 , aux));
                        fs.add(new NodeSearch(h*h, ns.getG()+1 , aux));
                        this.maks.put(aux.getTaquinBS(), new Node(ns.getNode().getTaquinBS(),i,j));
                        //fs.
                     }
                }
            }
            
           if (fs.size()>1000000){
               PriorityQueue<NodeSearch> fs1=new PriorityQueue<NodeSearch>(new NodeSearchComparator());
               for (i=0;i<500000;i++){
                   fs1.add(fs.poll());
               }
               fs=new PriorityQueue<NodeSearch>(fs1);
               System.gc();
           }
           //*/
     
        }
        return min;
    }

    public long searchPatternDatabases(Node node,long g,long bound){
        long min=bound*bound+g,min2,f,h;   
        int i,j;
        Node aux,min_ = null;
        //Stack<NodeSearch> fs=new Stack<>();
        PriorityQueue<NodeSearch> fs=new PriorityQueue<NodeSearch>(new NodeSearchComparator());
        //Stack<NodeSearch> fs=new Stack<>();
        this.maks.put(node.getTaquinBS(), node);
        fs.add(new NodeSearch(bound, g, node));
        NodeSearch ns;
        
        while(!fs.isEmpty()){
            ns=fs.poll();
            f=ns.getF();
            /*
            System.out.println("=========== F= "+f+" G= "+ns.getG()+" H= "+ns.getH());
            System.out.println("\t"+Utils.bitSetToStringln(this.n, this.nbits, this.sizeBS, ns.getNode().getTaquinBS()));
            //*/
            if(is_goal(ns.getNode())||is_goalPatternDatabases(ns.getNode())){
                
                System.out.println("=========== F= "+f+" G= "+ns.getG()+" H= "+ns.getH());
                System.out.println("\t"+Utils.bitSetToStringln(this.n, this.nbits, this.sizeBS, ns.getNode().getTaquinBS()));
                //*/
                this.found=true;
                this.rootT=ns.getNode();
                return ns.getG();
            }
            /*
            if (min>f&&!this.rootT.equals(ns.getNode())){
                System.out.println("=========== F= "+f+" G= "+ns.getG()+" H= "+ns.getH());
                System.out.println("\t"+Utils.bitSetToStringln(this.n, this.nbits, this.sizeBS, ns.getNode().getTaquinBS()));
                System.out.println("++++++++++++++++++++ "+min+" "+f);
                min=f;
                this.rootT=ns.getNode();
                //return ns.getG();
            }
            //*/
            for(int k=0;k<4;k++){
                i=x[k]+ns.getNode().getI_puzzle();
                j=y[k]+ns.getNode().getJ_puzzle();
                if (boundaryValidation(i, 0,this.n-1)&&boundaryValidation(j, 0,this.n-1)){
                    aux=movePuzzle(ns.getNode(), i, j);
                    if (this.maks.get(aux.getTaquinBS())==null){
                        //h=ns.getH()-Heuristics.hDistanceManhattan(ns.getNode(),i,j,this.allDistanceManhattan.get(this.n-2),this.n,this.nbits,sizeBS);
                        //h+=Heuristics.hDistanceManhattan(aux,ns.getNode().getI_puzzle(),ns.getNode().getJ_puzzle(),this.allDistanceManhattan.get(this.n-2),this.n,this.nbits,sizeBS);
                        h=Heuristics.hDistanceManhattanPatternDatabases(aux, this.allDMPatternDatabases.get(this.n-2).getDistanceManhattan(),this.n,this.nbits,sizeBS);
                        h=Math.max(h, Heuristics.misplacedPatternDatabases(aux,this.n,this.nbits,sizeBS,this.allDMPatternDatabases.get(this.n-2).getCost()));
                        //fs.add(new NodeSearch((long)((double)h*h*Math.sqrt((double)h*0)), ns.getG()+1 , aux));
                        fs.add(new NodeSearch(h*h, ns.getG()+1 , aux));
                        this.maks.put(aux.getTaquinBS(), new Node(ns.getNode().getTaquinBS(),i,j));
                    }
                }
            }
     
        }
        return min;
    }
    
    public long search1(Node node,long g,long bound){
        long min=bound,min2,f,h;   
        int i,j;
        Node aux;
        NodeSearch min_=null;
        Stack<NodeSearch> fs=new Stack<>();
        this.maks.put(node.getTaquinBS(), node);
        fs.add(new NodeSearch(bound, g, node));
        NodeSearch ns;
        List<NodeSearch> listAux;
        while(!fs.isEmpty()){
            ns=fs.pop();
            f=ns.getF();
            //System.out.println("=========== F= "+f+" G= "+ns.getG());
            //System.out.println("\t"+Utils.bitSetToStringln(this.n, this.nbits, this.sizeBS, ns.getNode().getTaquinBS()));
           if(is_goal(ns.getNode())){
                this.found=true;
                return 0;
            } 
            min2=-1;
            listAux=new ArrayList<>();
            for(int k=0;k<4;k++){
                i=x[k]+ns.getNode().getI_puzzle();
                j=y[k]+ns.getNode().getJ_puzzle();
                if (boundaryValidation(i, 0,this.n-1)&&boundaryValidation(j, 0,this.n-1)){
                    aux=movePuzzle(ns.getNode(), i, j);
                    if (this.maks.get(aux.getTaquinBS())==null){
                        //h=ns.getH()-Heuristics.hDistanceManhattan(ns.getNode(),i,j,this.allDistanceManhattan.get(this.n-2),this.n,this.nbits,sizeBS);
                        //h+=Heuristics.hDistanceManhattan(aux,ns.getNode().getI_puzzle(),ns.getNode().getJ_puzzle(),this.allDistanceManhattan.get(this.n-2),this.n,this.nbits,sizeBS);
                        h=Heuristics.hDistanceManhattan(aux, this.allDistanceManhattan.get(this.n-2),this.n,this.nbits,sizeBS);
                        h=Math.max(h, Heuristics.misplaced(aux,this.n,this.nbits,sizeBS));
                        listAux.add(new NodeSearch(h, ns.getG()+1 , aux));
                        this.maks.put(aux.getTaquinBS(), new Node(ns.getNode().getTaquinBS(),i,j));
                    }
                }
            }
            listAux.sort(new NodeSearchComparator());
            for(NodeSearch nn:listAux){
                fs.add(nn);
                //break;
            }
        }
        return min;
    }
    
    public boolean boundaryValidation(int a,int l,int r){
        return a>=l&&a<=r;
    }
    public boolean is_goal(Node node){
        return goal.equals(node.getTaquinBS());
    }
    public boolean is_goalPatternDatabases(Node node){
        int c=0;
        for(int i=0;i<this.n;i++){
            //System.out.println(" i= "+i+" j= "+0+" "+node.getValueBS(i,0,n,nbits,sizeBS)+" -> "+this.allDistanceManhattan.get(this.n-2).get(node.getValueBS(i,0,n,nbits,sizeBS)).get(i).get(0));
            if(this.allDistanceManhattan.get(this.n-2).get(node.getValueBS(i,0,n,nbits,sizeBS)).get(i).get(0)==0){
                c++;
               // System.out.println("SI");
            }
            //System.out.println(" i= "+0+" j= "+i+" "+node.getValueBS(0,i,n,nbits,sizeBS)+" -> "+this.allDistanceManhattan.get(this.n-2).get(node.getValueBS(0,i,n,nbits,sizeBS)).get(0).get(i));
            if(i!=0&&this.allDistanceManhattan.get(this.n-2).get(node.getValueBS(0,i,n,nbits,sizeBS)).get(0).get(i)==0){
                c++;
                //System.out.println("SI");
            }
        }
        //System.out.println( " ============>>>>>>>>>>>>>> "+c+"  ===== "+(2*this.n-1) );
        return c==2*this.n-1;//goal.equals(node.getTaquinBS());
    }
    
    public Node movePuzzle(Node node,int i,int j){
          Node newNode=new Node(node.getTaquinBS(), i, j);
          int value =node.getValueBS(i, j, n, nbits, sizeBS);
          newNode.setValueBS(node.getI_puzzle(), node.getJ_puzzle(), this.n, this.nbits, this.sizeBS, value);
          newNode.setValueBS(i,j, this.n, this.nbits, this.sizeBS, 0);
          //System.out.println(Utils.bitSetToStringln(this.n, this.nbits, this.sizeBS, node.getTaquinBS()));
          //System.out.println();  
          //System.out.println(Utils.bitSetToStringln(this.n, this.nbits, this.sizeBS, newNode.getTaquinBS()));
            
          return newNode;
    }
}
