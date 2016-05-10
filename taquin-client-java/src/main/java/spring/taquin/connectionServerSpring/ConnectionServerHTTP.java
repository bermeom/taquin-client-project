/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spring.taquin.connectionServerSpring;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.BitSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import spring.taquin.solve.Utils;

/**
 *
 * @author berme_000
 */
public class ConnectionServerHTTP {
    
    private final String USER_AGENT = "Mozilla/5.0";
    protected String hostnambe;
   
    public ConnectionServerHTTP( String hostnambe) {
        this.hostnambe = hostnambe;
    }
    
    private boolean sendRequestHTTPPost(String url,JSONObject data) throws MalformedURLException, ProtocolException, IOException, JSONException{
        
        URL object=new URL(hostnambe+url);

        HttpURLConnection con = (HttpURLConnection) object.openConnection();
        con.setDoOutput(true);
        con.setDoInput(true);
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        OutputStream  wr = (con.getOutputStream());
        //wr.write("{\"movements\":0,\"blank\":{\"row\":0,\"column\":0},\"currentState\":[[\"B\",\"2\"],[\"3\",\"1\"]]}".getBytes("UTF-8"));
        wr.write(data.toString().getBytes("UTF-8"));
        wr.flush();
        wr.close();
        //display what returns the POST request
        
        //StringBuilder sb = new StringBuilder();  
        int HttpResult = con.getResponseCode(); 
        if (HttpResult == HttpURLConnection.HTTP_OK) {
            /*
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "utf-8"));
            String line = null;  
            while ((line = br.readLine()) != null) {  
                sb.append(line + "\n");  
            }
            br.close();
            System.out.println("" + sb.toString()); 
            //*/
            return true;
        } else {
            System.out.println(con.getResponseMessage());  
            return false;
        }  

       
    }
    
    public void creatGamer(int id,String username){
        try {
            sendRequestHTTPPost("api/player/"+id+"/new/"+username+"/",new JSONObject());
        } catch (Exception ex) {
            System.out.println("ERROR : "+ex);
        }
    }
     
    public void creatBoard(int movements,int row,int column,int n,int nbits,int sizeBS, BitSet taquin){
    
        try {
            JSONObject data   = new JSONObject();
            JSONObject rowColum   = new JSONObject();
            data.put("currentState",Utils.convertArryJSONToBitset(n, nbits, sizeBS, taquin));
            rowColum.put("row",(row));
            rowColum.put("column",(column));
            data.put("blank", rowColum);
            data.put("movements",(movements));
            //System.out.println(data);
            
            sendRequestHTTPPost("api/board/new/",data);
        } catch (Exception ex) {
            System.out.println("ERROR : "+ex);
        }
    }
    
    public void moveChip(int idplayer,String direction){
    
        try {
            sendRequestHTTPPost("api/player/"+idplayer+"/board/move/"+direction+"/",new JSONObject());
        } catch (Exception ex) {
            System.out.println("ERROR : "+ex);
        }
    }
}
