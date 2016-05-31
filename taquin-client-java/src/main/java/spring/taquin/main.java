/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spring.taquin;

import connectionServerNode.TaquinClient;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Time;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;
import org.json.JSONException;
import spring.taquin.algorithm.IDA_STAR;
import spring.taquin.connectionServerSpring.ConnectionServerHTTP;
import spring.taquin.solve.Graph;
import spring.taquin.solve.Node;
import spring.taquin.solve.Taquin;
import spring.taquin.solve.Utils;
import spring.taquin.solve.heuristics.Heuristics;

/**
 *
 * @author berme_000
 */



public class main {

    public static void solveTaquin(ConnectionServerHTTP csHTTP,List<Graph> gs,BufferedReader  bf,String username){
         int n;
         int id_=2;
         try {
            System.out.println("ID borad: ");
            id_=Integer.parseInt(bf.readLine());
            csHTTP.creatGamer(id_,username);
            BitSet bs=csHTTP.getBoardBitSet();
            n=csHTTP.getN();
            Integer aux=gs.get(n - 2).getId(bs);
            if(aux == null){
                System.out.println("El tablero no existe en el grafo");
                return ;
            }
            int nodeID;
            Node node1;
            nodeID=aux;
            node1=gs.get(n-2).getBitSet(nodeID);
            Node node2;
            boolean replay;
            while (nodeID!=gs.get(n-2).getParent(nodeID)){
 
                    node2=gs.get(n-2).getBitSet(gs.get(n-2).getParent(nodeID));
                    replay=csHTTP.moveChip(id_, Utils.convertMovementsStringtoCoordinate(node1.getI_puzzle(), node1.getJ_puzzle(),node2.getI_puzzle(), node2.getJ_puzzle()));
                    System.out.println(replay+" "+Utils.convertMovementsStringtoCoordinate(node1.getI_puzzle(), node1.getJ_puzzle(),node2.getI_puzzle(), node2.getJ_puzzle()));
                    nodeID=gs.get(n-2).getParent(nodeID); 
                    Thread.sleep(500);
                    node1=node2;
            } 
         
         
            } catch (Exception e) {
                System.out.println("ERROR MAIN: "+e);
            }
        
    } 
    
    public static void addBoarad(ConnectionServerHTTP csHTTP,List<Graph> gs,BufferedReader  bf){
         int size=3;
         try {
            System.out.println("Size's borad: ");
            size=Integer.parseInt(bf.readLine());
            int nodeID=(gs.get(size-2).getFarthest_node());
            Node node1=gs.get(size-2).getBitSet(nodeID);
            csHTTP.creatBoard(0,node1.getI_puzzle(),node1.getJ_puzzle(),gs.get(size-2).getN(),gs.get(size-2).getNbits(),gs.get(size-2).getSizeBS(),node1.getTaquinBS());
        } catch (Exception e) {
                    System.out.println("ERROR MAIN: "+e);
       }
            
    
    }
    
    
    public static void menu(){
            BufferedReader  bf = new BufferedReader ( new InputStreamReader(System.in));
            List<Graph> gs=new ArrayList<>();
            gs.add(new Graph(2));
            gs.add(new Graph(3));
            String id=((new GregorianCalendar()).getTimeInMillis()%100)+"";
            String username="JAVA"+id;
            ConnectionServerHTTP csHTTP=new ConnectionServerHTTP("http://spring-session-bermeom.c9users.io/");
            //ConnectionServerHTTP csHTTP=new ConnectionServerHTTP("https://spark-davidcalle94301.c9users.io/");
            char opc='1';
            try {
                do{
                    System.out.println("TAQUIN");
                    System.out.println("1. ADD Board");
                    System.out.println("2. SOLVE Board");
                    opc=(bf.readLine()).charAt(0);
                    switch(opc){
                        case('1'):addBoarad(csHTTP, gs, bf);break;
                        case('2'):solveTaquin(csHTTP, gs, bf, username);break;
                    };
                }while(opc!='0');
            } catch (Exception e) {
                System.out.println("ERROR MAIN: "+e);
            }
            
        
    
    }
    
    
    public static void main(String[] args) throws InterruptedException, JSONException {
            //menu();
            List<List<List< List<Integer> > > > allDistanceManhattan=new ArrayList<>();
            for(int i=2;i<=10;i++){
                allDistanceManhattan.add(Utils.createSquareDistanceManhattan(i));
            }
            /*
            System.out.println("4");
            Utils.printMatrix(allDistanceManhattan.get(1).get(4));
            System.out.println("5");
            Utils.printMatrix(allDistanceManhattan.get(1).get(5));
            System.out.println("6");
            Utils.printMatrix(allDistanceManhattan.get(1).get(6));
            System.out.println("7");
            Utils.printMatrix(allDistanceManhattan.get(1).get(7));
            System.out.println("");
            //*/
            //String taquins="1 0 2 3 4 5 6 7 8";
            String taquins="20 11 3 4 17 14 1 8 13 16 12 18 21 2 23 5 6 24 19 9 15 10 7 22 0";//5
            //String taquins="0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24";//5
            //String taquins="1 2 9 10 13 15 7 6 3 5 8 11 4 14 12 0";//4
            //String taquins="1 2 9 10 13 15 7 6 3 5 8 11 4 14 12 0";//4X
            //String taquins="8 7 13 3 10 11 15 4 9 2 1 6 12 5 14 0";//4
            //taquins="0 7 10 1 15 6 4 3 12 8 13 11 2 9 5 14";
            //String taquins="8 7 6 0 4 1 2 5 3";//3
            int n=5;
            Graph g=new Graph(3);
            Taquin t=new Taquin(n);
            Taquin t2=new Taquin(n);
            BitSet mbs=Utils.convertToStringtToBitset(t.getN(), t.getNbits(), t.getSizeBS(),taquins);
            //System.out.println(" =>>>>> "+g.getId(mbs));
            //List<List<Integer> > mm=Utils.convertToStringMatriz(n,taquins);
            //Utils.printMatrix(mm);
            Node node=Utils.convertToStringtToNode(t.getN(), t.getNbits(), t.getSizeBS(),taquins);
            int nodeID=(g.getFarthest_node());
            //node=g.getBitSet(nodeID);
            //System.out.println(Heuristics.hDistanceManhattan(mm, allDistanceManhattan.get(n-2)));
            System.out.println("H= "+Heuristics.hDistanceManhattan(new Node(t.getTaquinBS(), t.getI_puzzle(), t.getJ_puzzle()), allDistanceManhattan.get(n-2),t.getN(), t.getNbits(), t.getSizeBS()));
            //System.out.println("H= "+Heuristics.hDistanceManhattan(node, allDistanceManhattan.get(n-2),t.getN(), t.getNbits(), t.getSizeBS()));
            
            //System.out.println(Utils.bitSetToStringln(t.getN(), t.getNbits(), t.getSizeBS(), t.getTaquinBS()));
            IDA_STAR ida=new IDA_STAR();
            //System.out.println();
            ida.ida_star(node, t.getN(), t.getNbits(), t.getSizeBS(), t.getTaquinBS());
            
            /*
            
            csHTTP.creatBoard(0,node1.getI_puzzle(),node1.getJ_puzzle(),g.getN(),g.getNbits(),g.getSizeBS(),node1.getTaquinBS());
            id=((new GregorianCalendar()).getTimeInMillis()%100)+"";
            username="JAVA"+id;
            csHTTP.creatGamer(id_+1,username);
            
            */
            
     }
}
