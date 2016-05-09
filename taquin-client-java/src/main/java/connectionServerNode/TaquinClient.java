/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connectionServerNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import spring.taquin.solve.Graph;
import spring.taquin.solve.Utils;

/**
 *
 * @author berme_000
 */
public class TaquinClient extends ConnectionServerSocketIO{
    
    
    private int step;
    private List<Graph> graphs;
    private int size;
    private int id;
    
    public TaquinClient(String hostnambe, String username) {
        super(hostnambe, username);
        this.graphs=new ArrayList<>();
        for (int i=2;i<=3;i++){
            this.graphs.add(new Graph(i));
        }
        this.step=0;
   }

    
    @Override
    public String getTaquin(int size_) {
            size_-=2;
            this.size=size_;
            return  Utils.bitSetToString(graphs.get(size_).getN(), graphs.get(size_).getNbits(), graphs.get(size_).getSizeBS(), graphs.get(size_).getBitSet(graphs.get(size_).getFarthest_node()).getTaquinBS());
    }

    @Override
    public String solveTaquin(String matriz) {
            this.step=1;
            this.id=graphs.get(this.size).getId(Utils.convertToStringtTBitset(graphs.get(this.size).getN(), graphs.get(this.size).getNbits(), graphs.get(this.size).getSizeBS(), matriz));
            this.id=graphs.get(this.size).getParent(id);
            return this.step+" "+this.graphs.get(this.size).getBitSet(id).getI_puzzle()+" "+this.graphs.get(this.size).getBitSet(id).getJ_puzzle();
    }

    @Override
    public String solveNextStep() {
            this.id=graphs.get(this.size).getParent(id);
            return (++this.step)+" "+this.graphs.get(this.size).getBitSet(id).getI_puzzle()+" "+this.graphs.get(this.size).getBitSet(id).getJ_puzzle();
    }

    @Override
    public String errorStep() {
            return (this.step)+" "+this.graphs.get(this.size).getBitSet(id).getI_puzzle()+" "+this.graphs.get(this.size).getBitSet(id).getJ_puzzle();
    }
   
  
    
}
