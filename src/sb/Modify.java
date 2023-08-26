/**
 * GUI for modification.
 *
 * @author KLM
 */

package sb;

import static sb.DB.db;
import static sb.Window.model;
import static sb.Window.sreq;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Modify extends JFrame implements ActionListener{
    JButton b_sav;
    JButton b_ccl;
    
    JTextField path;
    JTextField vol;
    JTextField key;
    
    JLabel l_p;
    JLabel l_v;
    JLabel l_k;
            
    node a;
    
    private static boolean GonnaAdd = false;
    
    //GUI Dimensions
    private int hgt = 20;
    private int lwt = 50;
    private int twt = 150;
    
    Modify(node a, boolean pwr){
        GonnaAdd = pwr;
        this.a = a;
        
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(250,200);
        this.setLayout(new BorderLayout());
        this.setResizable(false);
        
        path = new JTextField(a.PATH);
        path.setMinimumSize(new Dimension(twt, hgt));
        path.setPreferredSize(new Dimension(twt, hgt));
        path.setMaximumSize(new Dimension(twt, hgt));
        vol = new JTextField(Float.toString(a.VOL));
        vol.setMinimumSize(new Dimension(twt, hgt));
        vol.setPreferredSize(new Dimension(twt, hgt));
        vol.setMaximumSize(new Dimension(twt, hgt));
        key = new JTextField(a.KEY);
        key.setMinimumSize(new Dimension(twt, hgt));
        key.setPreferredSize(new Dimension(twt, hgt));
        key.setMaximumSize(new Dimension(twt, hgt));
        
        l_p = new JLabel("Path:",SwingConstants.LEFT);
        l_p.setMinimumSize(new Dimension(lwt, hgt));
        l_p.setPreferredSize(new Dimension(lwt, hgt));
        l_p.setMaximumSize(new Dimension(lwt, hgt));
        //l_p.setText("Path:");
        l_v = new JLabel("Volume:",SwingConstants.LEFT);
        l_v.setMinimumSize(new Dimension(lwt, hgt));
        l_v.setPreferredSize(new Dimension(lwt, hgt));
        l_v.setMaximumSize(new Dimension(lwt, hgt));
        //l_v.setText("Volume:");
        l_k = new JLabel("Key:",SwingConstants.LEFT);
        l_k.setMinimumSize(new Dimension(lwt, hgt));
        l_k.setPreferredSize(new Dimension(lwt, hgt));
        l_k.setMaximumSize(new Dimension(lwt, hgt));
        //l_k.setText("Key:");
        
        GridLayout tab = new GridLayout(3,2);
        FlowLayout row = new FlowLayout();
        
        JPanel panel = new JPanel();
        panel.setLayout(tab);
        
        JPanel panel1 = new JPanel();
        panel1.setLayout(row);
        panel1.add(l_p);
        panel1.add(path);
        
        JPanel panel2 = new JPanel();
        panel2.setLayout(row);
        panel2.add(l_v);
        panel2.add(vol);
        
        JPanel panel3 = new JPanel();
        panel3.setLayout(row);
        panel3.add(l_k);
        panel3.add(key);
        
        panel.add(panel1);
        panel.add(panel2);
        panel.add(panel3);
        
        this.add(panel, BorderLayout.CENTER );
        
        //Buttons panel
        JPanel btn = new JPanel();
        btn.setLayout(new FlowLayout(FlowLayout.CENTER));
        b_ccl = new JButton("Cancel");
        b_ccl.addActionListener(this);
        btn.add(b_ccl);
        b_sav = new JButton("Save");
        b_sav.addActionListener(this);
        btn.add(b_sav);
        this.add(btn, BorderLayout.SOUTH);
        
        this.validate();
    }
    
    public void add() throws IOException{
        node q = a;
        q.ID = 0;//proto není 0 obszena
        //Get Max ID
        for(node z : db){
            if(z.ID >= q.ID){
                q.ID = z.ID;
            } 
        }
        q.ID++;
        String t = path.getText();
        q.PATH = t;
        t = vol.getText();
        q.VOL = Float.parseFloat(t);
        t = key.getText();
        q.KEY = t;
        db.add(q);
        Save.Append(q);
        model.addElement(q);
        System.out.println("add done");
    }

    public void mod(){
        //Save modifications
        String t = path.getText();
        a.PATH = t;
        t = vol.getText();
        a.VOL = Float.parseFloat(t);
        t = key.getText();
        a.KEY = t;
        System.out.println("mod done");
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == b_ccl){
            //Cancel modifications - close window
            sreq = false;
            this.dispose();
        }else if(e.getSource() == b_sav){
            if(GonnaAdd){
                try { add(); } catch (IOException ex) {}
            }else{
                    mod();
            }
            sreq = false;
            this.dispose();
        }
    }
}
