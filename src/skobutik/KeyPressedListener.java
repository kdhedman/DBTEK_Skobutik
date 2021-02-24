package skobutik;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public interface KeyPressedListener extends KeyListener {
    @Override
    default void keyReleased(KeyEvent e){}
    @Override
    default void keyTyped(KeyEvent e){
    }
}
