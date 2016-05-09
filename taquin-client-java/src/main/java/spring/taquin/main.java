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
import spring.taquin.solve.Taquin;
import spring.taquin.solve.Utils;

/**
 *
 * @author berme_000
 */
public class main {
     public static void main(String[] args) {
            String id=((new GregorianCalendar()).getTimeInMillis()%100)+"";
            String username="JAVA"+id;
            ConnectionServerHTTP csHTTP=new ConnectionServerHTTP("http://spring-session-bermeom.c9users.io/", username, id);
            csHTTP.creatGamer();
            
            
     }
}
