/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spring.taquin.solve;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 * @author berme_000
 */
public class Graph {
    
    
    private Map<BitSet,Integer> ids;
    private List<BitSet> reverse_ids;
    private List<List<Integer> > graph;
    private Taquin taquin;   
    private int n;

    
    public Graph(int n) {
        this.n = n;
        this.taquin=new Taquin(n);
        this.ids=new HashMap<>();
        this.reverse_ids=new ArrayList<>();
        this.graph=new ArrayList<>();
        this.creatGraph();
    }
 
    private boolean validationPos(int i,int lowerLimits ,int upperLimits){
        return (i>=lowerLimits)&&(i<=upperLimits);
    }
    private void creatGraph(){
        Queue<Triple> queue=new LinkedBlockingQueue<>();
      
        this.addNode(this.taquin.getTaquinBS());
        queue.add(new Triple(this.ids.get(taquin.getTaquinBS()), this.taquin.getI_puzzle(), this.taquin.getJ_puzzle()));
        //System.out.println(this.taquin.getI_puzzle()+" "+ this.taquin.getJ_puzzle());
        Triple triple;
        int []x={1,0,-1, 0};
        int []y={0,1, 0,-1};
        BitSet tem;
        int move_,i,j,id_;
        while (!queue.isEmpty()){
               triple=queue.poll();
               
               for (int k=0;k<4;k++){
                   if (this.validationPos(triple.getI_puzzle()+x[k], 0, n-1)&&this.validationPos(triple.getJ_puzzle()+y[k], 0, n-1)){
                       tem=(BitSet) this.reverse_ids.get(triple.getId()).clone();
                       i=triple.getI_puzzle()+x[k];
                       j=triple.getJ_puzzle()+y[k];
                       move_=Utils.getValueBS(i, j, this.n, this.taquin.getNbits(), this.taquin.getSizeBS(), tem);
                       //System.out.println(move_+" "+i+" "+j+" "+triple.getI_puzzle()+" "+triple.getJ_puzzle());
                       tem=Utils.setValueBS(triple.getI_puzzle(), triple.getJ_puzzle(), this.n, this.taquin.getNbits(), this.taquin.getSizeBS(), tem, move_);
                       tem=Utils.setValueBS(i, j, this.n, this.taquin.getNbits(), this.taquin.getSizeBS(), tem, 0);
                       if(!this.ids.containsKey(tem)){
                          this.addNode(tem);
                          id_=this.ids.get(tem);
                          this.addEdge(id_, triple.getId());
                          queue.add(new Triple(id_, i, j));
                       }
                   }
               }
        }
    }
    
    public boolean addNode(BitSet bs){
        if ( !(bs==null) && !ids.containsKey(bs)){
            this.ids.put(bs, this.reverse_ids.size());
            this.reverse_ids.add(bs);
            this.graph.add(new ArrayList<>());
            return true;
        }
        return false;
    }
    
    public boolean addEdge(int id1,int id2){
        this.graph.get(id1).add(id2);
        this.graph.get(id2).add(id1);
        return true;
    }
    public int getId(BitSet bs){
        return this.ids.get(bs);
    }
    
    public BitSet getBitSet(int id){
        return this.reverse_ids.get(id);
    }

    public Map<BitSet, Integer> getIds() {
        return ids;
    }

    public void setIds(Map<BitSet, Integer> ids) {
        this.ids = ids;
    }

    public List<BitSet> getReverse_ids() {
        return reverse_ids;
    }

    public void setReverse_ids(List<BitSet> reverse_ids) {
        this.reverse_ids = reverse_ids;
    }

    public List<List<Integer>> getGraph() {
        return graph;
    }

    public void setGraph(List<List<Integer>> graph) {
        this.graph = graph;
    }

    public Taquin getTaquin() {
        return taquin;
    }

    public void setTaquin(Taquin taquin) {
        this.taquin = taquin;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }
    
    
   
    
    
    
}
