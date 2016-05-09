/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connectionServerNode;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author berme_000
 */
public abstract class ConnectionServerSocketIO {
    
    protected Socket socket;
    protected String username;
    protected String hostnambe;
    protected String id;

    public ConnectionServerSocketIO(String hostnambe, String username) {
        this.hostnambe = hostnambe;
        this.username = username;
    }
    
    public void startServerConnection(){
        try {
            Socket socket = IO.socket(hostnambe);
            socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
                    @Override
                    public void call(Object... args) {
                        socket.emit("add user", username);
                        //socket.disconnect();
                    }

                  }).on("login", new Emitter.Listener() {

                    @Override
                    public void call(Object... args) {
                        try {
                            JSONObject obj = (JSONObject)args[0];
                            System.out.println("Welcome to TAQUIN");
                            System.out.println("Your username is "+username);
                            System.out.println("Your ID is "+obj.getString("id"));
                            id=obj.getString("id");
                        } catch (JSONException ex) {
                            Logger.getLogger(ConnectionServerSocketIO.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                  }).on("start", new Emitter.Listener() {

                    @Override
                    public void call(Object... args) {
                        socket.emit("start-request", id);
                    }

                  }).on("get-matriz", new Emitter.Listener() {

                    @Override
                    public void call(Object... args) {
                        try {
                            JSONObject obj = (JSONObject)args[0];
                            socket.emit("get-matriz-reply", getTaquin(Integer.parseInt(obj.getString("n_size"))));
                        } catch (JSONException ex) {
                            Logger.getLogger(ConnectionServerSocketIO.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                  }).on("solve", new Emitter.Listener() {

                    @Override
                    public void call(Object... args) {
                        try {
                            JSONObject obj = (JSONObject)args[0];
                            if(obj.getString("id").equals(id)){
                                socket.emit("step", solveTaquin(obj.getString("matriz")) );
                            }
                        } catch (JSONException ex) {
                            Logger.getLogger(ConnectionServerSocketIO.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                  }).on("ack-solve", new Emitter.Listener() {

                    @Override
                    public void call(Object... args) {
                        socket.emit("step", solveNextStep());
                    }

                  }).on("error-solve", new Emitter.Listener() {

                    @Override
                    public void call(Object... args) {
                        socket.emit("step", errorStep());
                    }

                  }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {

                    @Override
                    public void call(Object... args) {
                    
                    
                    }

                 });
                socket.connect();
        } catch (URISyntaxException ex) {
            Logger.getLogger(ConnectionServerSocketIO.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    } 
    
    public abstract String getTaquin(int size_);
    public abstract String solveTaquin(String matriz);
    public abstract String solveNextStep();
    public abstract String errorStep();
    
    @Override
    public void finalize() throws Throwable {
        socket.disconnect();
        super.finalize();
    }

    
}
