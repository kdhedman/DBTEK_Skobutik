import dbObjects.Kund;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class LoginPanel extends JPanel  {
    private Repository db_rep = new Repository();

    //Content
    private JLabel userLabel = new JLabel("Användarnamn");
    private JLabel passwordLabel = new JLabel("Passerord");
    JTextField usernameField = new JTextField();
    JPasswordField passwordField = new JPasswordField();
    JCheckBox showPassword = new JCheckBox("Visa lösenord");
    JButton login = new JButton("In fö'fan");
    JLabel errorMessage = new JLabel("");

    public LoginPanel(){
        setLayout(null);
        setLocationAndSize();
        addContent();
        setActionListeners();
        errorMessage.setForeground(Color.RED);

        userLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        passwordLabel.setHorizontalAlignment(SwingConstants.RIGHT);

    }

    private void setLocationAndSize(){
        userLabel.setBounds(50,120,100,30);
        usernameField.setBounds(180,120,220,30);
        passwordLabel.setBounds(50,150,100,30);
        passwordField.setBounds(180,150,220,30);
        showPassword.setBounds(180,180,120,30);
        login.setBounds(300,180,100,30);
        errorMessage.setBounds(180,90,300,30);
    }

    private void addContent(){
        add(userLabel);
        add(usernameField);
        add(passwordLabel);
        add(passwordField);
        add(showPassword);
        add(login);
        add(errorMessage);
    }

    public void setActionListeners(){
        showPassword.addActionListener(e->{
            passwordField.setEchoChar((showPassword.isSelected() ? ((char) 0) : '*'));
        });
        login.addActionListener(e -> {
            tryLogin();
        });
        usernameField.addKeyListener((KeyPressedListener) e -> {
            errorMessage.setText("");
        });
        passwordField.addKeyListener((KeyPressedListener) e -> {
            errorMessage.setText("");
            if(e.getKeyCode() == KeyEvent.VK_ENTER){
                tryLogin();
            }
        });
    }

    private void tryLogin(){
        System.out.println("Loginförsök");
        String username = usernameField.getText();
        String password = String.valueOf(passwordField.getPassword());
        System.out.printf("User: %s, Password: %s\n", username, password);
        boolean success = db_rep.tryLogin(username, password);
        System.out.println(success ? "Login: Success!" : "Login: Fail");
        if(success){
            Kund kund = new Kund(username, db_rep.getKundIDFromNamn(username));
            ActiveKund.setKund(kund);
            MainFrame mf = MainFrame.getInstance();
            UI_AddToCart store = new UI_AddToCart();
            mf.changeView(store.p1);
        } else {
            errorMessage.setText("Nähä, du! Där gick det fel!");
            errorMessage.validate();
        }
    }
}
