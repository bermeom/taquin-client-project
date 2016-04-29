/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spring.taquin;

import java.sql.Time;
import java.util.GregorianCalendar;
import java.util.Random;

/**
 *
 * @author berme_000
 */
public class main {
     public static void main(String[] args) {
         
            TaquinClient taquinClient=new TaquinClient("https://proyect-taquin-bermeom.c9users.io/", "JAVA"+((new GregorianCalendar()).getTimeInMillis()%100));
            taquinClient.startServerConnection();
    }
}
