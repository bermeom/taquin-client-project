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
import spring.taquin.solve.heuristics.DataHeuristics;
import spring.taquin.solve.heuristics.Heuristics;

/**
 *
 * @author berme_000
 */



public class main {
    
    public static void solveTaquin_2_3(ConnectionServerHTTP csHTTP,List<Graph> gs,Node node,int id_){
            int n=csHTTP.getN();
            BitSet bs=node.getTaquinBS();
            try {
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
                            //System.out.println(replay+" "+Utils.convertMovementsStringtoCoordinate(node1.getI_puzzle(), node1.getJ_puzzle(),node2.getI_puzzle(), node2.getJ_puzzle()));
                            nodeID=gs.get(n-2).getParent(nodeID); 
                            Thread.sleep(500);
                            node1=node2;
                    } 
            } catch (Exception e) {
                System.out.println("ERROR MAIN: "+e);
            }
    }
    public static void solveTaquin_4_10(ConnectionServerHTTP csHTTP,IDA_STAR ida,Node node,int id_){
            int n=csHTTP.getN();
            try {
                    Taquin t=new Taquin(n);
                    ida.ida_star(csHTTP.getBoardNode(), t.getN(), t.getNbits(), t.getSizeBS(), t.getTaquinBS());
                    List<Node> path=ida.getPath();
                    Node node1,node2;
                    node1=node;
                    boolean replay;
                    for (int i=path.size()-1;i>=0;i--){
                        node2=path.get(i);
                        
                        replay=csHTTP.moveChip(id_, Utils.convertMovementsStringtoCoordinate(node1.getI_puzzle(), node1.getJ_puzzle(),node2.getI_puzzle(), node2.getJ_puzzle()));
                        //System.out.println(replay+" "+Utils.convertMovementsStringtoCoordinate(node1.getI_puzzle(), node1.getJ_puzzle(),node2.getI_puzzle(), node2.getJ_puzzle()));
                        node1=node2;          
                    }
            } catch (Exception e) {
                System.out.println("ERROR MAIN: "+e);
            }
    }
    

    public static void solveTaquin(ConnectionServerHTTP csHTTP,List<Graph> gs,BufferedReader  bf,String username,IDA_STAR ida){
         int n;
         int id_=2;
         try {
            System.out.println("ID borad: ");
            id_=Integer.parseInt(bf.readLine());
            csHTTP.creatGamer(id_,username);
            Node node=csHTTP.getBoardNode();
            n=csHTTP.getN();
            if(n>=2&&n<=3){
                solveTaquin_2_3(csHTTP, gs, node, id_);
            }else{
                 solveTaquin_4_10(csHTTP, ida, node, id_);
            }
           
         } catch (Exception e) {
                System.out.println("ERROR MAIN: "+e);
            }
        
    } 
    
    public static void addBoarad(ConnectionServerHTTP csHTTP,List<Graph> gs, List<List<String > >boars,BufferedReader  bf){
         int size=3;
         try {
          
            System.out.println("Size's borad: ");
            size=Integer.parseInt(bf.readLine());
             Taquin t;
            Node node1;
            if(size>=2&&size<=3){
                int nodeID=(gs.get(size-2).getFarthest_node());
                node1=gs.get(size-2).getBitSet(nodeID);
                t=gs.get(size-2).getTaquin();
            }else{
               t=new Taquin(size);
                System.out.println(boars.size());
                System.out.println(boars.get(size-4).size());
                System.out.println(boars.get(size-4).get(0));
                node1=Utils.convertToStringtToNode(t.getN(), t.getNbits(), t.getSizeBS(),boars.get(size-4).get(0));
            }
             System.out.println("Pso");
            csHTTP.creatBoard(0,node1.getI_puzzle(),node1.getJ_puzzle(),t.getN(), t.getNbits(), t.getSizeBS(),node1.getTaquinBS());
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
            IDA_STAR ida=new IDA_STAR();
            List<List<String > >boars=loadBoards();
            System.out.println(boars.size());
            try {
                do{
                    System.out.println("TAQUIN");
                    System.out.println("1. ADD Board");
                    System.out.println("2. SOLVE Board");
                    opc=(bf.readLine()).charAt(0);
                    switch(opc){
                        case('1'):addBoarad(csHTTP, gs,boars, bf);break;
                        case('2'):solveTaquin(csHTTP, gs, bf, username,ida);break;
                    };
                }while(opc!='0');
            } catch (Exception e) {
                System.out.println("ERROR MAIN: "+e);
            }
            
        
    
    }
    
    public static  List<List<String > > loadBoards(){
            List<List<String > >boars=new ArrayList<List<String>>();
            for(int i=0;i<10-4;i++){
                boars.add(new ArrayList<>());
            }
            boars.get(4-4).add("1 2 9 10 13 15 7 6 3 5 8 11 4 14 12 0");
            boars.get(4-4).add("0 12 9 13 15 11 10 14 7 8 5 6 4 3 2 1");
            boars.get(5-4).add("20 11 3 4 17 14 1 8 13 16 12 18 21 2 23 5 6 24 19 9 15 10 7 22 0");
            boars.get(5-4).add("0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24");
            boars.get(6-4).add("22 23 11 6 9 27 25 8 12 10 5 34 20 18 4 32 24 3 21 19 14 7 30 16 15 33 17 1 26 2 31 13 35 29 28 0");
            boars.get(7-4).add("16 22 2 5 6 7 44 8 27 29 10 11 23 35 17 1 20 18 3 14 48 31 25 4 36 34 19 12 9 21 33 42 46 40 41 13 32 30 37 15 26 24 45 39 43 38 28 47 0");
            return boars;
    }
    
    
    public static void main(String[] args) throws InterruptedException, JSONException {
           
            menu();
            
            /*
            List<List<List< List<Integer> > > > allDistanceManhattan=new ArrayList<>();
             List<DataHeuristics> allDMPatternDatabases=new ArrayList<>();
            for(int i=2;i<=10;i++){
                allDistanceManhattan.add(Heuristics.createSquareDistanceManhattan(i));
                allDMPatternDatabases.add(Heuristics.createSquareDistanceManhattanPatternDatabases(i));
            }
            int a;
            a=1;
            System.out.println(a);
            Utils.printMatrix(allDMPatternDatabases.get(3).getDistanceManhattan().get(a));
            a=2;
            System.out.println(a);
            Utils.printMatrix(allDMPatternDatabases.get(3).getDistanceManhattan().get(a));
            a=5;
            System.out.println(a);
            Utils.printMatrix(allDMPatternDatabases.get(3).getDistanceManhattan().get(a));
            a=7;
            System.out.println(a);
            Utils.printMatrix(allDMPatternDatabases.get(3).getDistanceManhattan().get(a));
            a=11;
            System.out.println(a);
            Utils.printMatrix(allDMPatternDatabases.get(3).getDistanceManhattan().get(a));
            //* /
            //String taquins="35 3 11 62 6 10 2 13 4 1 8 29 25 19 9 15 31 32 18 23 20 49 24 51 45 41 21 5 27 84 22 17 36 56 52 43 12 87 68 16 38 63 47 28 50 39 14 55 91 75 65 60 69 7 86 74 42 68 46 54 80 33 44 40 85 90 26 57 30 58 89 99 81 59 53 94 34 73 92 88 82 71 66 97 76 79 95 48 37 78 72 83 96 77 61 93 64 70 98 0";//10
            //String taquins="1 0 2 3 4 5 6 7 8";
            //String taquins="20 11 3 4 17 14 1 8 13 16 12 18 21 2 23 5 6 24 19 9 15 10 7 22 0";//5
            //String taquins="0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24";//5
            //String taquins="22 23 11 6 9 27 25 8 12 10 5 34 20 18 4 32 24 3 21 19 14 7 30 16 15 33 17 1 26 2 31 13 35 29 28 0";//6
            //String taquins="1 2 9 10 13 15 7 6 3 5 8 11 4 14 12 0";//4
            //String taquins="1 2 9 10 13 15 7 6 3 5 8 11 4 14 12 0";//4X
            //String taquins="0 12 9 13 15 11 10 14 7 8 5 6 4 3 2 1";//4
            String taquins="16 22 2 5 6 7 44 8 27 29 10 11 23 35 17 1 20 18 3 14 48 31 25 4 36 34 19 12 9 21 33 42 46 40 41 13 32 30 37 15 26 24 45 39 43 38 28 47 0";//7
            //String taquins="11 24 48 18 10 23 9 22 21 3 13 14 17 2 29 58 57 52 33 19 8 42 30 28 5 32 7 4 15 36 12 20 45 53 49 46 37 34 43 1 26 56 35 61 60 25 51 59 16 54 40 31 47 55 41 39 27 6 63 50 62 38 44 0";//8
            //String taquins="8 7 6 0 4 1 2 5 3";//3
            int n=7;
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
            IDA_STAR ida=new IDA_STAR();
            //System.out.println();
            //ida.ida_star(node, t.getN(), t.getNbits(), t.getSizeBS(), t.getTaquinBS());
            //ida.ida_starPatternDatabase(node, t.getN(), t.getNbits(), t.getSizeBS(), t.getTaquinBS());
            ida.ida_star(node, t.getN(), t.getNbits(), t.getSizeBS(), t.getTaquinBS());
            
            /*
            
            csHTTP.creatBoard(0,node1.getI_puzzle(),node1.getJ_puzzle(),g.getN(),g.getNbits(),g.getSizeBS(),node1.getTaquinBS());
            id=((new GregorianCalendar()).getTimeInMillis()%100)+"";
            username="JAVA"+id;
            csHTTP.creatGamer(id_+1,username);
            
            */
            
     }
}
