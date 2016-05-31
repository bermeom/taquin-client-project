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
    
    private long h;
    private long g;
    private Node node;

    public NodeSearch(long h, long g, Node node) {
        this.h = h;
        this.g = g;
        this.node = node;
    }

    public long getH() {
        return h;
    }
    public long getF() {
        return h+g;
    }

    public void setH(long h) {
        this.h = h;
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
