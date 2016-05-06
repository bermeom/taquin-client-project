/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spring.taquin.solve;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

/**
 *
 * @author berme_000
 */
public class Taquin {
    
    private BitSet taquinBS;
    private char maskBits;
    private int nbits;
    private int sizeBS;
    
    private int n;
    private int i_puzzle;
    private int j_puzzle;    

    public Taquin(int n) {
        this.n = n;
        this.nBits();
        this.taquinBS=new BitSet(this.sizeBS);
        for(int i=1;i<n*n;i++){
            this.setValueBS(i-1, i);
        }
        this.i_puzzle=n-1;
        this.j_puzzle=n-1;
        
    }
    
    private void nBits(){
        int num=1;
        this.nbits=0;
        this.maskBits=(char)((num<<this.nbits)-1);
        while(this.maskBits<(this.n*this.n-1)){
            this.nbits++;
            this.maskBits=(char)((num<<this.nbits)-1);
        }
        this.sizeBS=this.nbits*this.n*this.n;
        
    }

    public Taquin(BitSet taquinBS, char maskBits, int nbits, int sizeBS, int n, int i_puzzle, int j_puzzle) {
        this.taquinBS = taquinBS;
        this.maskBits = maskBits;
        this.nbits = nbits;
        this.sizeBS = sizeBS;
        this.n = n;
        this.i_puzzle = i_puzzle;
        this.j_puzzle = j_puzzle;
    }
    
    private int getPosition(int i,int j){
        return (i)*this.n+(j);//(i%n)*n+(j%n)
    }
    
    public int getValueBS(int pos){
        int value=0;
        for(int k=this.nbits-1,q=this.sizeBS-pos*this.nbits-1;k>=0;k--,q--){
            value|=(this.taquinBS.get(q)?1:0)<<k;
        }
        return value;
    }
    
    public int getValueBS(int i,int j){
        return this.getValueBS(this.getPosition(i, j));
    }
    
    public void setValueBS(int pos,int value){
        for(int k=this.nbits-1,q=this.sizeBS-pos*this.nbits-1;k>=0;k--,q--){
            this.taquinBS.set(q,(((value>>k)&1)==1));
        }
    }
    
    public void setValueBS(int i,int j,int value){
        this.setValueBS(this.getPosition(i, j), value);
    }
    
    public void printBitset(BitSet bs,int nbits,int nbitsprint) {
        String stringBits="";
        for (int i=nbits-1;i>=0;){
            for (int j=0; j<nbitsprint && i>=0;j++,i--){
                stringBits=stringBits+(bs.get(i)?1:0);
            }
           stringBits+=" ";
        }
        System.out.println(stringBits);
    }

    public BitSet getTaquinBS() {
        return taquinBS;
    }

    public void setTaquinBS(BitSet taquinBS) {
        this.taquinBS = taquinBS;
    }

    public char getMaskBits() {
        return maskBits;
    }

    public void setMaskBits(char maskBits) {
        this.maskBits = maskBits;
    }

    public int getNbits() {
        return nbits;
    }

    public void setNbits(int nbits) {
        this.nbits = nbits;
    }

    public int getSizeBS() {
        return sizeBS;
    }

    public void setSizeBS(int sizeBS) {
        this.sizeBS = sizeBS;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
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
    
    @Override
    public String toString() {
        int k=0;
        String row="",m="";
        for(int i=0;i<n;i++){
            row="";
            for(int j=0;j<n;j++){
                row+=" "+this.getValueBS(k);
                k++;
            }
            m+=row+"\n";
        }
        return m;
    }

    
       
    
       
       
    
}
