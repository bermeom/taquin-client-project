/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spring.taquin.algorithm;

import java.util.Comparator;

/**
 *
 * @author berme_000
 */
public class NodeSearchComparator implements Comparator<NodeSearch>{

    @Override
    public int compare(NodeSearch o1, NodeSearch o2) {
            return (int) -(o2.getF()-o1.getF());
    }
    
}
