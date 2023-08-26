/**
 * This class reads config file and puts data into db vector.
 *
 * @author KLM
 */

package sb;

import static sb.DB.db;
import static sb.Save.fp;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;

public class Load {
    //id=0;path=C://GDEV/test.mp3;vol=1.0;key=Q;
    
    public static void Load(){
        try{
            String file = IStoSTR(fp);
            regex(file);
            
        }catch(Exception e){
            throw new Error("Piss off!", e);
        }
    }
    
    private static void regex(String in) throws Exception{
        //===[ HEAD ]===
        String part = in.substring(0,7);
        if(!part.equals("<begin>")){
            throw new Exception();
        }
        String nxt = in.replace("<begin>","");
        
        //===[ DATA ]===
        part = nxt.substring(0,6);

        //number of nodes
        int lastIndex = 0;
        int count = 0;
        while(lastIndex != -1){
            lastIndex = in.indexOf("<node>",lastIndex);
            if(lastIndex != -1){
                count++;
                lastIndex += 6;
            }
        }
        
        int p = nxt.indexOf("<node>", nxt.indexOf("<node>") + 1);
        String nwp = nxt.substring(0,p);//Node workplace

        int op = 0;
        int ed = 0;               
        String value = null;
            
        //resolve each node
        for(/*NIC*/;count > 0;count--){
            node tmp = new node();//Pro každou iteraci znovu inicializovat node (pokud ne, přenášejí se hodnoty)
            if(part.equals("<node>")){
                nwp = nwp.replace("<node>","");
                //ID
                op = nwp.indexOf("id=");
                ed = nwp.indexOf(";"); 
                value = nwp.substring(op+3, ed);
                tmp.ID = parseInt(value);
                value = nwp.substring(0,ed+1);
                nwp = nwp.replace(value,"");

                //PATH
                op = nwp.indexOf("path=");
                ed = nwp.indexOf(";"); 
                value = nwp.substring(op+5, ed);
                tmp.PATH = value;
                value = nwp.substring(0,ed+1);
                nwp = nwp.replace(value,"");

                //VOLUME
                op = nwp.indexOf("vol=");
                ed = nwp.indexOf(";"); 
                value = nwp.substring(op+4, ed);
                tmp.VOL = parseFloat(value);
                value = nwp.substring(0,ed+1);
                nwp = nwp.replace(value,"");

                //KEY
                op = nwp.indexOf("key=");
                ed = nwp.indexOf(";"); 
                value = nwp.substring(op+4, ed);
                tmp.KEY = value;
                value = nwp.substring(0,ed+1);
                nwp = nwp.replace(value,"");

                db.add(tmp);
                
                nwp = nxt.substring(0,p);
                nxt = nxt.replace(nwp,"");
                
                if(nxt.equals("<end>")){
                    return;
                }else{
                    p = nxt.indexOf("<node>", nxt.indexOf("<node>") + 1);
                    if(p == -1){
                        p = nxt.indexOf("<end>", nxt.indexOf("<node>") + 1);
                    }
                    nwp = nxt.substring(0,p);
                }
            }
        }
    }
    
    private static String IStoSTR(String filepath) throws FileNotFoundException, IOException{
        InputStream is = new FileInputStream(filepath);
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader reader = new BufferedReader(isr);
        StringBuffer sb = new StringBuffer();
        String str;
        while((str = reader.readLine())!= null){
            sb.append(str);
        }
        return sb.toString();
    }
}
