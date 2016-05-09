/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spring.taquin.connectionServerSpring;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author berme_000
 */
public class ConnectionServerHTTP {
    
    private final String USER_AGENT = "Mozilla/5.0";
    protected String username;
    protected String hostnambe;
    protected String id;

    public ConnectionServerHTTP( String hostnambe,String username, String id) {
        this.username = username;
        this.hostnambe = hostnambe;
        this.id = id;
    }
    
    private boolean sendRequestHTTPPost(String url,JSONObject data) throws MalformedURLException, ProtocolException, IOException, JSONException{
        
        URL object=new URL(hostnambe+url);

        HttpURLConnection con = (HttpURLConnection) object.openConnection();
        con.setDoOutput(true);
        con.setDoInput(true);
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");
        con.setRequestMethod("POST");

        OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
        wr.write(data.toString());
        wr.flush();

        //display what returns the POST request

        StringBuilder sb = new StringBuilder();  
        int HttpResult = con.getResponseCode(); 
        if (HttpResult == HttpURLConnection.HTTP_OK) {
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "utf-8"));
            String line = null;  
            while ((line = br.readLine()) != null) {  
                sb.append(line + "\n");  
            }
            br.close();
            System.out.println("" + sb.toString()); 
            return true;
        } else {
            System.out.println(con.getResponseMessage());  
            return false;
        }  

       
    }
    
    public void creatGamer(){
        
        
        try {
            /*
            JSONObject cred   = new JSONObject();
            JSONObject auth   = new JSONObject();
            JSONObject parent = new JSONObject();

            cred.put("username","adm");
            cred.put("password", "pwd");

            auth.put("tenantName", "adm");
            auth.put("passwordCredentials", cred.toString());

            parent.put("auth", data.toString());
            */
            System.out.println(sendRequestHTTPPost("api/player/"+this.id+"/new/"+this.username+"/",new JSONObject()));
        } catch (Exception ex) {
            System.out.println("ERROR : "+ex);
        }
    }
     
    void creatBoard(){
    
        try {
            JSONObject data   = new JSONObject();
            JSONObject rowColum   = new JSONObject();
            data.put("currentState","adm");
            rowColum.put("row", "1");
            rowColum.put("column", "1");
            data.put("blank", rowColum.toString());
            data.put("movements", "0");
            
            System.out.println(sendRequestHTTPPost("api/player/"+this.id+"/new/"+this.username+"/",new JSONObject()));
        } catch (Exception ex) {
            System.out.println("ERROR : "+ex);
        }
            
           // parent.put("auth", data.toString());
    }
}
