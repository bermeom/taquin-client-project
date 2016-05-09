/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spring.taquin.solve;

import java.util.Comparator;

/**
 *
 * @author berme_000
 */
public class PairComparator implements Comparator<Pair>{

    @Override
    public int compare(Pair o1, Pair o2) {
        
        if(o1.getFirst()!=o2.getFirst()){
            return o1.getFirst()-o2.getFirst();
        }else{
             return o1.getSecond()-o2.getSecond();
        }
        
    }
    
}
