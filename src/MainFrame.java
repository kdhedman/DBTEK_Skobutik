import javax.swing.*;

/**
 * Created by David Hedman <br>
 * Date: 2021-02-22 <br>
 * Time: 12:25 <br>
 * Project: DBTEK_Skobutik <br>
 * Copyright: Nackademin <br>
 */
public class MainFrame extends JFrame {
    public MainFrame(){
        setTitle("Skobutik");
        setSize(800, 600);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        LoginPanel login = new LoginPanel();
        add(login);
        setVisible(true);
    }
}
