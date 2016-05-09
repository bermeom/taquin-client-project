/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spring.taquin.solve;

import java.util.BitSet;

/**
 *
 * @author berme_000
 */
public class Node {
    private BitSet taquinBS;
    private int i_puzzle;
    private int j_puzzle;

    public Node(BitSet taquinBS, int i_puzzle, int j_puzzle) {
        this.taquinBS = taquinBS;
        this.i_puzzle = i_puzzle;
        this.j_puzzle = j_puzzle;
    }

    public BitSet getTaquinBS() {
        return taquinBS;
    }

    public void setTaquinBS(BitSet taquinBS) {
        this.taquinBS = taquinBS;
    }

    public int getI_puzzle() {
        return i_puzzle;
    }

    public void setI_puzzle(int i_puzzle) {
        this.i_puzzle = i_puzzle;
    }

    public int getJ_puzzle() {
        return j_puzzle;
    }

    public void setJ_puzzle(int j_puzzle) {
        this.j_puzzle = j_puzzle;
    }
    
    
    
}
