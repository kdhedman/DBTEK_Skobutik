import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by David Hedman <br>
 * Date: 2021-02-22 <br>
 * Time: 16:14 <br>
 * Project: DBTEK_Skobutik <br>
 * Copyright: Nackademin <br>
 */
public interface KeyPressedListener extends KeyListener {
    @Override
    default void keyReleased(KeyEvent e){}
    @Override
    default void keyTyped(KeyEvent e){
    }
}
