package spring.taquin.solve;

import java.util.BitSet;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author berme_000
 */
public class Utils {
    
    public static int getPosition(int i,int j,int n){
        return (i)*n+(j);//(i%n)*n+(j%n)
    }
    
    public static int getValueBS(int pos,int nbits,int sizeBS, BitSet taquinBS){
        int value=0;
        for(int k=nbits-1,q=sizeBS-pos*nbits-1;k>=0;k--,q--){
            value|=(taquinBS.get(q)?1:0)<<k;
        }
        return value;
    }
    
    public static int getValueBS(int i,int j,int n,int nbits,int sizeBS, BitSet taquinBS){
        return Utils.getValueBS(Utils.getPosition(i, j,n),nbits,sizeBS,taquinBS);
    }
    
    public static BitSet setValueBS(int pos,int nbits,int sizeBS, BitSet taquinBS,int value){
        for(int k=nbits-1,q=sizeBS-pos*nbits-1;k>=0&&q>=0;k--,q--){
            taquinBS.set(q,(((value>>k)&1)==1));
        }
        return taquinBS;
    }
    
    public static BitSet setValueBS(int i,int j,int n,int nbits,int sizeBS, BitSet taquinBS,int value){
        return Utils.setValueBS(Utils.getPosition(i, j,n),nbits,sizeBS,taquinBS, value);
    }
    public static String toString(int n,int nbits,int sizeBS, BitSet taquinBS) {
        int k=0;
        String row="",m="";
        for(int i=0;i<n;i++){
            row="";
            for(int j=0;j<n;j++){
                row+=Utils.getValueBS(k,nbits,sizeBS,taquinBS)+" ";
                k++;
            }
            m+=row+"\n";
        }
        return m.trim();
    }
    
     public static String bitSetToString(int n,int nbits,int sizeBS, BitSet taquinBS) {
        int k=0;
        String row="",m="";
        for(int i=0;i<n;i++){
            row="";
            for(int j=0;j<n;j++){
                row+=" "+Utils.getValueBS(k,nbits,sizeBS,taquinBS);
                k++;
            }
            m+=row;
        }
        return m.trim();
    }
     
    public static BitSet convertToStringtTBitset(int n,int nbits,int sizeBS, String taquin) {
            BitSet bs=new BitSet(nbits);
            String []s=taquin.split(" ");
            int i=0;
            for (String c: s){
                bs=Utils.setValueBS(i, nbits, sizeBS, bs, Integer.parseInt(c));
                i++;
            }
            return bs;
    }
    
    
}
