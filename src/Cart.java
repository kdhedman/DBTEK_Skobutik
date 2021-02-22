import javax.swing.*;

public class Cart extends JPanel {
    JLabel labelCartandName = new JLabel(ActiveKund.getKund().getNamn() + "s kundvagn");
    JButton buttonBackToStore = new JButton("Tillbaka");

    public Cart(){
        setLayout(null);
        setLocationAndSize();
        addContent();
        addActionListeners();
    }

    private void setLocationAndSize(){
        labelCartandName.setBounds(10,3,100,30);
        buttonBackToStore.setBounds(370,420,100,30);
    }

    private void addContent(){
        add(labelCartandName);
        add(buttonBackToStore);

    }

    private void addActionListeners(){
        buttonBackToStore.addActionListener(e -> {
            MainFrame mf = MainFrame.getInstance();
            UI_AddToCart store = new UI_AddToCart();
            mf.changeView(store.p1);
        });
    }
}
