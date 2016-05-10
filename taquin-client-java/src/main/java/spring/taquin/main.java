/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spring.taquin;

import connectionServerNode.TaquinClient;
import java.sql.Time;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;
import org.json.JSONException;
import spring.taquin.connectionServerSpring.ConnectionServerHTTP;
import spring.taquin.solve.Graph;
import spring.taquin.solve.Node;
import spring.taquin.solve.Taquin;
import spring.taquin.solve.Utils;

/**
 *
 * @author berme_000
 */
public class main {
     public static void main(String[] args) throws InterruptedException, JSONException {
            List<Graph> gs=new ArrayList<>();
            gs.add(new Graph(2));
            gs.add(new Graph(3));
            int size=2,n;
            String id=((new GregorianCalendar()).getTimeInMillis()%100)+"";
            String username="JAVA"+id;
            ConnectionServerHTTP csHTTP=new ConnectionServerHTTP("http://spring-session-bermeom.c9users.io/");
            int nodeID=(gs.get(size-2).getFarthest_node());
            Node node1=gs.get(size-2).getBitSet(nodeID);
            csHTTP.creatBoard(0,node1.getI_puzzle(),node1.getJ_puzzle(),gs.get(size-2).getN(),gs.get(size-2).getNbits(),gs.get(size-2).getSizeBS(),node1.getTaquinBS());
            
            int id_=1;
            csHTTP.creatGamer(id_,username);
            BitSet bs=csHTTP.getBoardBitSet();
            n=csHTTP.getN();
            Integer aux=gs.get(n - 2).getId(bs);
            if(aux == null){
                System.out.println("El tablero no existe en el grafo");
                return ;
            }
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
            /*
            
            csHTTP.creatBoard(0,node1.getI_puzzle(),node1.getJ_puzzle(),g.getN(),g.getNbits(),g.getSizeBS(),node1.getTaquinBS());
            id=((new GregorianCalendar()).getTimeInMillis()%100)+"";
            username="JAVA"+id;
            csHTTP.creatGamer(id_+1,username);
            
            */
            
     }
}
