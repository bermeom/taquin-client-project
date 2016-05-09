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
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 * @author berme_000
 */
public class Graph {
    
    
    private Map<BitSet,Integer> ids;
    private List<Node> reverse_ids;
    private List<List<Integer> > graph;
    private Taquin taquin;   
    private int n;
    /*
        For Dijkstra
    
    */
    private List<Integer> parents;
    private int farthest_node;
    
    public Graph(int n) {
        this.n = n;
        this.taquin=new Taquin(n);
        this.ids=new HashMap<>();
        this.reverse_ids=new ArrayList<>();
        this.graph=new ArrayList<>();
        this.creatGraph();
        this.dijkstra();
    }
 
    private boolean validationPos(int i,int lowerLimits ,int upperLimits){
        return (i>=lowerLimits)&&(i<=upperLimits);
    }
    private void creatGraph(){
        Queue<Triple> queue=new LinkedBlockingQueue<>();
      
        this.addNode(this.taquin.getTaquinBS(),this.taquin.getI_puzzle(),this.taquin.getJ_puzzle());
        queue.add(new Triple(this.ids.get(taquin.getTaquinBS()), this.taquin.getI_puzzle(), this.taquin.getJ_puzzle()));
        //System.out.println(this.taquin.getI_puzzle()+" "+ this.taquin.getJ_puzzle());
        Triple triple;
        int []x={1,0,-1, 0};
        int []y={0,1, 0,-1};
        BitSet tem;
        int move_,i,j,id_,currID,currI,currJ;
        while (!queue.isEmpty()){
               triple=queue.poll();
               currID=triple.getFirst();
               currI=triple.getSecond();
               currJ=triple.getThird();
               for (int k=0;k<4;k++){
                   if (this.validationPos(currI+x[k], 0, n-1)&&this.validationPos(currJ+y[k], 0, n-1)){
                       tem=(BitSet) this.reverse_ids.get(currID).getTaquinBS().clone();
                       i=currI+x[k];
                       j=currJ+y[k];
                       move_=Utils.getValueBS(i, j, this.n, this.taquin.getNbits(), this.taquin.getSizeBS(), tem);
                       //System.out.println(move_+" "+i+" "+j+" "+triple.getI_puzzle()+" "+triple.getJ_puzzle());
                       tem=Utils.setValueBS(currI, currJ, this.n, this.taquin.getNbits(), this.taquin.getSizeBS(), tem, move_);
                       tem=Utils.setValueBS(i, j, this.n, this.taquin.getNbits(), this.taquin.getSizeBS(), tem, 0);
                       if(!this.ids.containsKey(tem)){
                          this.addNode(tem,i,j);
                          id_=this.ids.get(tem);
                          this.addEdge(id_, currID);
                          queue.add(new Triple(id_, i, j));
                       }
                   }
               }
        }
    }
    
    private void dijkstra(){
        // first cost and second id node
        PriorityQueue<Triple> pq=new PriorityQueue<>(new TripleComparator());
        this.parents=new ArrayList<>();
        List<Boolean> marks=new ArrayList<>();
        for (int i=0;i<this.graph.size();i++){
            this.parents.add(i);
            marks.add(true);
        }
        pq.add(new Triple(0, 0, 0));
        Triple aux;
        int cost,id_,parent;
        int farthest_distance=0;
        this.farthest_node=0;
        while(!pq.isEmpty()){
            aux=pq.poll();
            cost=aux.getFirst();
            id_=aux.getSecond();
            parent=aux.getThird();
            if (marks.get(id_)){
                marks.set(id_, false);
                this.parents.set(id_,parent);
                if(cost>farthest_distance){
                    farthest_distance=cost;
                    this.farthest_node=id_;
                }
                for (int i=0;i<this.graph.get(id_).size();i++){
                    //if (marks.get(graph.get(id_).get(i))){
                        pq.add(new Triple(cost+1,graph.get(id_).get(i),id_));
                    //}
                    }
            
            }
        }
    }
    
    public boolean addNode(BitSet bs, int i_puzzle, int j_puzzle){
        if ( !(bs==null) && !ids.containsKey(bs)){
            this.ids.put(bs, this.reverse_ids.size());
            this.reverse_ids.add(new Node(bs, i_puzzle, j_puzzle));
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
    
    public Node getBitSet(int id){
        return this.reverse_ids.get(id);
    }

    public Map<BitSet, Integer> getIds() {
        return ids;
    }

    public void setIds(Map<BitSet, Integer> ids) {
        this.ids = ids;
    }

    public List<Node> getReverse_ids() {
        return reverse_ids;
    }

    public void setReverse_ids(List<Node> reverse_ids) {
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

    public List<Integer> getParents() {
        return parents;
    }

    public int getParent(int i) {
        return this.parents.get(i);
    }

    public void setParents(List<Integer> parents) {
        this.parents = parents;
    }

    public int getFarthest_node() {
        return farthest_node;
    }
    
    public void setFarthest_node(int farthest_node) {
        this.farthest_node = farthest_node;
    }
    
    public int getNbits() {
        return this.taquin.getNbits();
    }

    public int getSizeBS() {
        return this.taquin.getSizeBS();
    }
    
    
    
   
    
    
    
}
