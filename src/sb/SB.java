/**
 * Soundboard
 *
 * @author NiousekCZ
 * @author KLM
 * 
 * ©2023
 * 
 * Created: 23.8.2023
 * 
 * Path to config file is defined in Save.java
 */


/**
 * TODO
 * 
 * -rework removing items
 * -volume control
 * -output selector
 */

package sb;

import static sb.DB.db;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class SB {
    
    public static String kps = "";//Key pressed (becouse i am a potato)
    public static Window frame;
    public static boolean acty = false;
    
    public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException {
        frame = new Window();
        
        //INITIALIZE KEY LISTENER
        try {
            GlobalScreen.registerNativeHook();
        }
        catch (NativeHookException ex) {
            System.err.println("HOOK ERROR");
            System.exit(1);
        }
        GlobalScreen.addNativeKeyListener(new Key());
        
        //MAIN LOOP
        while(true){//Shit, ale asi to ničemu nevadí. Zavře se s oknem.
            kps = "";
            //System.out.println("INACTIVE");//Nefunguje to když to nic nedělá? - delay stačí
            delay(1);
            while(acty){
                //System.out.println("ACTIVE");
                //System.out.println(java.lang.Thread.activeCount());//Zobrazí počet aktivních vláken
                if(getOK(kps)){
                    if(getID(kps) != 0){
                        node cur = getNode(getID(kps));
                        //Create new player in thread for each sound generated.
                        Player nox = new Player(cur);
                        new Thread(nox).start();

                        //Player nox = new Player();
                        //nox.play(cur.PATH);
                        //nox.play("C://GDEV/test3.mp3");
                        kps = "";//reset key value
                    }
                }
            }
        }
    }
    
    //returns true if there is asked key registered in db
    public static boolean getOK(String k){
        for(node a : db){
            if(a.KEY.equals(k)){
                return(true);
            }
        }
        return(false);
    }
    
    //returns node id of asked key
    public static int getID(String k){
        for(node a : db){
            if(a.KEY.equals(k)){
                return(a.ID);
            }
        }
        return(0);
    }
    
    //returns whole node
    public static node getNode(int id){
        for(node a : db){
            if(a.ID == id){
                return(a);
            }
        }
        return null; 
    }
    
    //sleep utility
    public static void delay(int sec){
        try { TimeUnit.SECONDS.sleep(sec); } catch (InterruptedException ex) {}
    }
}
