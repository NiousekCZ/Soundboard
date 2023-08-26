/**
 * Window GUI class.
 *
 * @author NiousekCZ
 */

package sb;

import sb.node;
import static sb.DB.db;
import static sb.SB.acty;

import java.io.IOException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.plaf.metal.MetalToggleButtonUI;

public class Window extends JFrame implements ActionListener {
    JMenuItem m_about;
    JMenuItem m_phd;
    JButton b_add;
    JButton b_mod;
    JButton b_rem;
    JButton b_sav;
    JToggleButton b_tgb;
    node p;
    static boolean sreq = false;//Ask to save config, when Modify button was activated.
    
    JList<node> vis = new JList<>();
    public static DefaultListModel<node> model = new DefaultListModel<>();
    
    Window(){  
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500,500);
        //this.setLayout(null);
        this.setLayout(new BorderLayout(10,10));
        this.setResizable(false);
        
        //Close action override - ask to save config upon closing
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if(sreq){
                    if (JOptionPane.showConfirmDialog(null, "Save Config?", "Close Window?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
                        try { Save.Save(); } catch (IOException ex) {}
                        System.exit(0);
                    }
                }else{
                    System.exit(0);
                }
            }
        });
        
        //Window title
        this.setTitle("Soundboard");
        
        //Setup menu bar
        JMenuBar mb = new JMenuBar();
        JMenu x = new JMenu("Menu");
        m_phd = new JMenuItem("Placeholder");
        m_phd.addActionListener(this);
        m_about = new JMenuItem("About");
        m_about.addActionListener(this);
        
        //Compose menu bar
        x.add(m_phd);
        x.add(m_about);
        mb.add(x);
        this.setJMenuBar(mb);

        //List
        Load.Load();

        for(node q : db){
            model.addElement(q);
        }
        
        vis.getSelectionModel().addListSelectionListener(e -> {
            p = vis.getSelectedValue();
        });
        
        vis.setModel(model);
        vis.setFont( new Font("monospaced", Font.PLAIN, 12) );//Format list
        
        JScrollPane scr = new JScrollPane(vis);
        scr.setBorder(BorderFactory.createLineBorder(Color.black));
        scr.setViewportView(vis);
        this.add(scr, BorderLayout.CENTER);
        
        
        //Buttons panel
        JPanel btn = new JPanel();
        btn.setLayout(new FlowLayout(FlowLayout.CENTER));
        b_tgb = new JToggleButton("ACTY");
        b_tgb.addActionListener(this);
        b_tgb.setUI( new MetalToggleButtonUI(){
            @Override
            protected Color getSelectColor() {
                return Color.RED;
            }
        });
        btn.add(b_tgb);
        b_add = new JButton("Add");
        b_add.addActionListener(this);
        btn.add(b_add);
        b_mod = new JButton("Modify");
        b_mod.addActionListener(this);
        btn.add(b_mod);
        b_rem = new JButton("Remove");
        b_rem.addActionListener(this);
        btn.add(b_rem);
        b_sav = new JButton("Save");
        b_sav.addActionListener(this);
        btn.add(b_sav);
        this.add(btn, BorderLayout.SOUTH);
        
        
        
        
        //Auxiliary panels - design
        JPanel lft = new JPanel();
        JPanel rgt = new JPanel();
        JPanel tpn = new JPanel();
        this.add(lft, BorderLayout.EAST);
        this.add(rgt, BorderLayout.WEST);
        this.add(tpn, BorderLayout.NORTH);
        
        //this.pack();
        this.validate();
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //PLAIN_MESSAGE,INFORMATION_MESSAGE,WARNING_MESSAGE,ERROR_MESSAGE
        if(e.getSource() == m_about){
            JOptionPane.showMessageDialog(this, "Soundboard\r\nversion 1.0.0.0\r\nby NiousekCZ\r\n©2023", "Info", JOptionPane.INFORMATION_MESSAGE);
        }else if(e.getSource() == m_phd){
            JOptionPane.showMessageDialog(this, "Not implemented.", "Placeholder", JOptionPane.ERROR_MESSAGE);
        }else if(e.getSource() == b_add){
            if(!acty){
                System.out.println("ADD");
                Modify tmp0 = new Modify(new node(),true);
                tmp0.setVisible(true);
                sreq = true;
            }
        }else if(e.getSource() == b_mod){
            if(!acty){
                if(p != null){
                    System.out.println("MOD");
                    //p = db.get(0);
                    Modify tmp1 = new Modify(p, false);           
                    tmp1.setVisible(true);
                    sreq = true;
                }
            }
        }else if(e.getSource() == b_rem){
            if(!acty){
                if(p != null){
                    //THIS WORKS DO NOT MODIFY NOR ASK WHY
                    //Wasted time - too much
                    //REMOVED - Do not use isListening anywhere, going to broke this.
                    //SOLVED - Removes efectively only 1 node at start ???
                    //When only 2 items remaining, throws error and deletes both from view, selected from file.
                    System.out.println("REM");
                    try { Save.remove(p); } catch (IOException ex) {}
                    db.clear();
                    //model.removeElement(p);
                    model.removeAllElements();
                    Load.Load();
                    for(node q : db){
                        model.addElement(q);
                    }
                    if(db.size() <= 2){
                        JOptionPane.showMessageDialog(this, "Less than 3 files is going to occur error.\r\nAdd another file before removing further.", "Warning!", JOptionPane.WARNING_MESSAGE);
                    }
                    try { Save.Save(); } catch (IOException ex) {}
                    System.out.println("rem done");
                }
            }
        }else if(e.getSource() == b_sav){
            try { Save.Save(); System.out.println("sav");} catch (IOException ex) {}
        }else if(e.getSource() == b_tgb){
            acty = !acty;
        }
    }
    
}
