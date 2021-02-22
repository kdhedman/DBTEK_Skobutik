import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by David Hedman <br>
 * Date: 2021-02-22 <br>
 * Time: 12:57 <br>
 * Project: DBTEK_Skobutik <br>
 * Copyright: Nackademin <br>
 */
public class LoginPanel extends JPanel implements ActionListener {
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

        userLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        passwordLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        showPassword.addActionListener(this);
        login.addActionListener(this);
    }

    private void setLocationAndSize(){
        userLabel.setBounds(50,120,100,30);
        usernameField.setBounds(180,120,220,30);
        passwordLabel.setBounds(50,150,100,30);
        passwordField.setBounds(180,150,220,30);
        showPassword.setBounds(180,180,120,30);
        login.setBounds(300,180,100,30);
        errorMessage.setBounds(180,90,100,30);
    }

    private void addContent(){
        add(userLabel);
        add(usernameField);
        add(passwordLabel);
        add(passwordField);
        add(showPassword);
        add(login);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == showPassword){
            passwordField.setEchoChar(showPassword.isSelected() ? ((char) 0) : '*');
        }
        if(e.getSource() == login){
            tryLogin();
        }
    }

    private void tryLogin(){
        System.out.println("Loginförsök");
        String username = usernameField.getText();
        String password = String.valueOf(passwordField.getPassword());
        System.out.println(username + " " + password);
        System.out.println(db_rep.tryLogin(username, password) ? "Success!" : "Fail");

    }
}
