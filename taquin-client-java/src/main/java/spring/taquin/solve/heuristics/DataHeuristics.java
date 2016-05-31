/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spring.taquin.solve.heuristics;

import java.util.List;

/**
 *
 * @author berme_000
 */
public class DataHeuristics {
    private List<List< List<Integer> > > distanceManhattan;
    private List<Integer> cost;

    public DataHeuristics(List<List<List<Integer>>> distanceManhattan, List<Integer> cost) {
        this.distanceManhattan = distanceManhattan;
        this.cost = cost;
    }

    public List<List<List<Integer>>> getDistanceManhattan() {
        return distanceManhattan;
    }

    public void setDistanceManhattan(List<List<List<Integer>>> distanceManhattan) {
        this.distanceManhattan = distanceManhattan;
    }

    public List<Integer> getCost() {
        return cost;
    }

    public void setCost(List<Integer> cost) {
        this.cost = cost;
    }
    
    
    
}
