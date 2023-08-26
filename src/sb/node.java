/**
 * Represents one item in database.
 *
 * @author KLM
 */

package sb;

public class node {
    public int ID = 0;
    public String PATH = null;
    public float VOL = 0;
    public String KEY = null;
    
    @Override 
    public String toString() {
        return(String.format("%-10s %-30s %-10s %-5s",this.ID,this.PATH,this.VOL,this.KEY));
    }
}
