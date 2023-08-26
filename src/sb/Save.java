/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sb;

import static sb.DB.db;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Update config file.
 * 
 * @author KLM
 */
public class Save {
    
    public static String fp = "config.txt";
    
    //Just rewrite config file
    public static void Save() throws IOException{
        String filepath = fp;
        BufferedWriter writer = new BufferedWriter(new FileWriter(filepath));
        writer.write("<begin>");
        
        for (node a : db) {
            writer.write("\r\n<node>id=" + a.ID +";path=" + a.PATH + ";vol=" + a.VOL + ";key=" + a.KEY +";");
        }
        
        writer.write("\r\n<end>");
        writer.close();
    }
    
    //Rewrite config file and add requested item
    public static void Append(node q) throws IOException{
        String filepath = fp;
        BufferedWriter writer = new BufferedWriter(new FileWriter(filepath));
        writer.write("<begin>");
        
        for (node a : db) {
            writer.write("\r\n<node>id=" + a.ID +";path=" + a.PATH + ";vol=" + a.VOL + ";key=" + a.KEY +";");
        }
        writer.write("\r\n<node>id=" + q.ID +";path=" + q.PATH + ";vol=" + q.VOL + ";key=" + q.KEY +";");
        
        writer.write("\r\n<end>");
        writer.close();
        
    }
    
    //Remove utility - somehow working
    public static void remove(node g) throws IOException{
        String filepath = fp;
        BufferedWriter writer = new BufferedWriter(new FileWriter(filepath));
        writer.write("<begin>");
        
        for (node a : db) {
            if(a == g){
                //Ignore passed node - remove (no rewrite)
            }else{
                writer.write("\r\n<node>id=" + a.ID +";path=" + a.PATH + ";vol=" + a.VOL + ";key=" + a.KEY +";");
            }
        }
        
        writer.write("\r\n<end>");
        writer.close();
    }
}
