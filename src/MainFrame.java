import com.sun.tools.javac.Main;

import javax.swing.*;

public class MainFrame extends JFrame {
    private static MainFrame instance;

    private MainFrame(){
        setTitle("Skobutik");
        setSize(500, 500);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        LoginPanel login = new LoginPanel();
        add(login);
        setVisible(true);
    }

    public static MainFrame getInstance(){
        if(instance == null) {
            instance = new MainFrame();
        }
        return instance;
    }

    public void changeView(JPanel panel){
        getContentPane().removeAll();
        getContentPane().validate();
        getContentPane().add(panel);
        validate();
    }
}
