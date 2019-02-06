/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soundstream.controller;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URL;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

/**
 *
 * @author pepy
 */
public class StreamController extends Thread {
    
    BufferedInputStream bis;
    public Player player;
    
    
        public void Stop(){
        if (player != null){
            player.close();
            

        }
    }
    
    public void Play (String link) {
        
        
        try {
             URL url=new URL(link);
             Socket socket=new Socket(url.getHost(), url.getPort());
             OutputStream os=socket.getOutputStream();
             String user_agent = "RadioMaster/0.5";
             String req="GET / HTTP/1.0\r\nuser-agent: "+user_agent+"\r\nIcy-MetaData: 1\r\nConnection: keep-alive\r\n\r\n";
             os.write(req.getBytes());
             InputStream is=socket.getInputStream();
             bis = new BufferedInputStream(is);
             player = new Player(bis);
             //player.play();

             
        } 
        catch (Exception e){
             e.printStackTrace();
             System.out.println("Nije moguce pokrenuti stream");
        }
        
        
        new Thread(){
            @Override
            public void run(){
                
                try {
                    player.play();
                    
               
                } catch (JavaLayerException ex) {
                    
                }
                
            }
        }.start();
    }
}
