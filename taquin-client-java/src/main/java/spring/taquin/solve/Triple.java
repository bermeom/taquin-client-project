/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spring.taquin.solve;

/**
 *
 * @author berme_000
 */
public class Triple {
    
    private int id;
    private int i_puzzle;
    private int j_puzzle;    

    public Triple(int id, int i_puzzle, int j_puzzle) {
        this.id = id;
        this.i_puzzle = i_puzzle;
        this.j_puzzle = j_puzzle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
