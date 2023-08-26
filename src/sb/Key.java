/**
 * background keyboard listener
 * 
 * @author KLM
 */

package sb;

import static sb.SB.frame;
import static sb.SB.kps;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import javax.swing.JOptionPane;

public class Key implements NativeKeyListener{
    
    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        System.out.println("Key: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
        kps = NativeKeyEvent.getKeyText(e.getKeyCode());
        if (e.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
            try {
                GlobalScreen.unregisterNativeHook();
                JOptionPane.showMessageDialog(frame, "You pressed ESC.\r\nKeyboard listening has stopped..", "Alert!", JOptionPane.WARNING_MESSAGE);
            } catch (NativeHookException nhe) {
                
            }
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {

    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent e) {

    }
}
