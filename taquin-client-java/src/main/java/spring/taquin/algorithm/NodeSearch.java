/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spring.taquin.algorithm;

import spring.taquin.solve.Node;

/**
 *
 * @author berme_000
 */
public class NodeSearch {
    
    private long f;
    private long g;
    private Node node;

    public NodeSearch(long f, long g, Node node) {
        this.f = f;
        this.g = g;
        this.node = node;
    }

    public long getF() {
        return f;
    }

    public void setF(long f) {
        this.f = f;
    }

    
    public long getG() {
        return g;
    }

    public void setG(long g) {
        this.g = g;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }
    
    
    
}
