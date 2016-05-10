/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spring.taquin;

import connectionServerNode.TaquinClient;
import java.sql.Time;
import java.util.BitSet;
import java.util.GregorianCalendar;
import java.util.Random;
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
     public static void main(String[] args) {
            
            Graph g=new Graph(3);
            String id=((new GregorianCalendar()).getTimeInMillis()%100)+"";
            String username="JAVA"+id;
            ConnectionServerHTTP csHTTP=new ConnectionServerHTTP("http://spring-session-bermeom.c9users.io/");
            int nodeID=(g.getFarthest_node());
            Node node1=g.getBitSet(nodeID);
            csHTTP.creatBoard(0,node1.getI_puzzle(),node1.getJ_puzzle(),g.getN(),g.getNbits(),g.getSizeBS(),node1.getTaquinBS());
            int id_=2;
            csHTTP.creatGamer(id_,username);
            Node node2;
            while (nodeID!=g.getParent(nodeID)){
                    nodeID=g.getParent(nodeID);
                    node2=g.getBitSet(nodeID);
                    csHTTP.moveChip(id_, Utils.convertMovementsStringtoCoordinate(node1.getI_puzzle(), node1.getJ_puzzle(),node2.getI_puzzle(), node2.getJ_puzzle()));
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
