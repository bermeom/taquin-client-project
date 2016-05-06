/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spring.taquin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author berme_000
 */
public class TaquinClient extends ConnectionServerSocketIO{
    
    
    private int step;
    private Map<String,List<String> > taquinSolutions;
    private String taquin;
    
    public TaquinClient(String hostnambe, String username) {
        super(hostnambe, username);
        this.step=0;
        this.taquinSolutions=new HashMap<>();
        List<String> s1= new ArrayList<>();
        s1.add("1 0 0");
        s1.add("2 0 1");
        s1.add("3 1 1");
        taquinSolutions.put("1 0 3 2", s1);
        s1.clear();
        s1.add("1 0 1");
        s1.add("2 1 1");
        s1.add("3 1 0");
        s1.add("4 0 0");
        s1.add("5 0 1");
        s1.add("6 1 1");
        taquinSolutions.put("0 3 2 1", s1);
   }

    
    @Override
    public String getTaquin(int size_) {
            step=0;
            return "0 3 2 1";
    }

    @Override
    public String solveTaquin(String matriz) {
            this.taquin=matriz;
            return taquinSolutions.get(this.taquin).get(step++);
    }

    @Override
    public String solveNextStep() {
            return taquinSolutions.get(this.taquin).get(step++);
    }

    @Override
    public String errorStep() {
            return taquinSolutions.get(this.taquin).get(step);
    }
   
  
    
}
