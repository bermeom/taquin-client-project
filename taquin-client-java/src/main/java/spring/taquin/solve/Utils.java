package spring.taquin.solve;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

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
      public static String bitSetToStringln(int n,int nbits,int sizeBS, BitSet taquinBS) {
        int k=0;
        String row="",m="";
        for(int i=0;i<n;i++){
            row="";
            for(int j=0;j<n;j++){
                row+=" "+Utils.getValueBS(k,nbits,sizeBS,taquinBS);
                k++;
            }
            m+=row+"\n";
        }
        return m.trim();
    }
     
    public static BitSet convertToStringtToBitset(int n,int nbits,int sizeBS, String taquin) {
            BitSet bs=new BitSet(nbits);
            String []s=taquin.split(" ");
            int i=0;
            for (String c: s){
                bs=Utils.setValueBS(i, nbits, sizeBS, bs, Integer.parseInt(c));
                i++;
            }
            return bs;
    }
    
    public static Node convertToStringtToNode(int n,int nbits,int sizeBS, String taquin) {
            BitSet bs=new BitSet(nbits);
            String []s=taquin.split(" ");
            int i=0,x=0,y=0;
            Node node=new Node(bs,0, 0);
            for (String c: s){
                bs=Utils.setValueBS(i, nbits, sizeBS, bs, Integer.parseInt(c));
                if (c.equals("0")){
                    System.out.println("---------------------------");
                    System.out.println(y+" "+x);
                   node.setI_puzzle(y);
                   node.setJ_puzzle(x);
                }
                i++;
                x++;
                if(x>=n){
                    x=0;
                    y++;
                }
            }
            node.setTaquinBS(bs);
            return node;
    }
   
    public static List<List<Integer> > convertToStringMatriz(int n,String taquin) {
            List<List<Integer> > ma=new ArrayList<>();
            String []s=taquin.split(" ");
            int i=0,j=0;
            ma.add(new ArrayList<>());
            for (String c: s){
                if(j>=n){
                    i++;
                    j=0;
                    ma.add(new ArrayList<>());
                }
       
                ma.get(i).add(Integer.parseInt(c));
                j++;
            }
            System.out.println(ma.size()+" "+ma.get(0).size());
            return ma;
    }
    
    public static JSONArray convertArryJSONToBitset(int n,int nbits,int sizeBS, BitSet taquin) {
            JSONArray taquinJSON=new JSONArray();
            JSONArray rowJSON;//=new JSONArray();
            int number;
            for(int i=0;i<n;i++){
                rowJSON=new JSONArray();
                for(int j=0;j<n;j++){
                    number=Utils.getValueBS(i, j, n, nbits, sizeBS, taquin);
                    rowJSON.put(number);//==0?"B":(number+""));
                }
                taquinJSON.put(rowJSON);
            } return taquinJSON;
    }
    
    public static String convertMovementsStringtoCoordinate(int curruntI,int curruntJ,int nextI,int nextJ){
        String r="";
          
        if(nextI-curruntI==0&&nextJ-curruntJ==1){
            r="right";
        }else if(nextI-curruntI==0&&nextJ-curruntJ==-1){
                r="left";
        }else if(nextI-curruntI==1&&nextJ-curruntJ==0){
                r="down";
        }else if(nextI-curruntI==-1&&nextJ-curruntJ==0){
                r="up";
        }
        
        return r;
    }
    
    public static Pair nBits(int n){
        int num=1;
        int nbits=0;
        int maskBits=(char)((num<<nbits)-1);
        while(maskBits<(n*n-1)){
            nbits++;
            maskBits=(char)((num<<nbits)-1);
        }
        int sizeBS=nbits*n*n;
        return new Pair(nbits, sizeBS);
    }
    
    public static void readFile(String nameFile) throws IOException{
        BufferedReader br = new BufferedReader(new FileReader(nameFile));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            String everything = sb.toString();
        } finally {
            br.close();
        }
    
    }
    
    public static List<List< List<Integer> > > createSquareDistanceManhattan(int n){
        List<List< List<Integer> > > square=new ArrayList< List< List<Integer> > >();
        int x=0,y=0,c=0;
        for(int k=0;k<n*n;k++){
             square.add(new ArrayList<List<Integer> >());
             if(k==0){
                x=n-1;
                y=n-1;
             }else if(k==1){
                  x=0;
                  y=0;
               }else if (x==n-1){
                        y++;
                        x=0;
                    }else{
                        x++;
                    } 
             
             //System.out.println(x+" "+y);
             for(int i=0;i<n;i++){
                square.get(k).add(new ArrayList<Integer>());
                for(int j=0;j<n;j++){
                    square.get(k).get(i).add(Math.abs(j-x)+Math.abs(i-y));
                } 
             }
        }
        return square;
    
    }
    
    public static List<List< List<Integer> > > createSquareDistanceManhattan1(int n){
        List<List< List<Integer> > > square=new ArrayList< List< List<Integer> > >();
        int x=0,y=0,c=0;
        for(int k=0;k<n*n;k++){
             square.add(new ArrayList<List<Integer> >());
             if(k==0){
                x=0;
                y=0;
             }else{
                if(x==3){
                    y++;
                    x=0;
                } 
             }
             //System.out.println(x+" "+y);
             for(int i=0;i<n;i++){
                square.get(k).add(new ArrayList<Integer>());
                for(int j=0;j<n;j++){
                    square.get(k).get(i).add(Math.abs(j-x)+Math.abs(i-y));
                } 
             }
             x++;
        }
        return square;
    
    }
    
    public static void printMatrix(List< List<Integer> > matrix){
    
        for (int i=0;i<matrix.size();i++){
            for (int j=0;j<matrix.size();j++){
                System.out.print(" "+matrix.get(i).get(j));
            }
            System.out.println("");
        }
    
    
    }
    
    
    
}
