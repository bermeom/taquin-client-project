/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spring.taquin;

import java.sql.Time;
import java.util.BitSet;
import java.util.GregorianCalendar;
import java.util.Random;
import spring.taquin.solve.Graph;
import spring.taquin.solve.Taquin;
import spring.taquin.solve.Utils;

/**
 *
 * @author berme_000
 */
public class main {
     public static void main(String[] args) {
         
            TaquinClient taquinClient=new TaquinClient("https://proyect-taquin-bermeom.c9users.io/", "JAVA"+((new GregorianCalendar()).getTimeInMillis()%100));
            taquinClient.startServerConnection();
            
            /*
            Graph graph=new Graph(2);
            System.out.println("FINISHED "+graph.getGraph().size());
            System.out.println(Utils.bitSetToString(graph.getN(), graph.getNbits(), graph.getSizeBS(), graph.getBitSet(graph.getFarthest_node()).getTaquinBS()));
            System.out.println(Utils.bitSetToString(graph.getN(), graph.getNbits(), graph.getSizeBS(), graph.getBitSet(graph.getParent(graph.getFarthest_node())).getTaquinBS()));
            System.out.println(graph.getBitSet(graph.getParent(graph.getFarthest_node())).getI_puzzle()+" "+graph.getBitSet(graph.getParent(graph.getFarthest_node())).getJ_puzzle() );
            System.out.println(Utils.bitSetToString(graph.getN(), graph.getNbits(), graph.getSizeBS(), Utils.convertToStringtTBitset(graph.getN(), graph.getNbits(), graph.getSizeBS(), "0 3 2 1")));
            int id =graph.getId(Utils.convertToStringtTBitset(graph.getN(), graph.getNbits(), graph.getSizeBS(), "0 3 2 1"));
            System.out.println(id);
            /*
            for(BitSet bs:graph.getReverse_ids()){
                System.out.println(Utils.toString(graph.getN(), graph.getTaquin().getNbits(), graph.getTaquin().getSizeBS(), bs));
            
            }
            //*/
            //System.out.println(taquin);
            
            
            
            
     }
}
