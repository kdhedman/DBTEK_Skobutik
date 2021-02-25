package skobutik;

import iodb.Repository;
import dbObjects.ActiveKund;

import javax.swing.*;

public class Cart extends JPanel {
    Repository db_rep = new Repository();
    JLabel labelCartandName = new JLabel(ActiveKund.getKund().getNamn() + "s eviga kundvagn");
    JLabel labelTotalCost = new JLabel();
    JButton buttonBackToStore = new JButton("Tillbaka");
    String[] columns = {"Kvantitet", "Skomodell", "Färg", "Skostorlek", "pris"};
    String[][] content = db_rep.getKundsBeställningar(ActiveKund.getKund().getNamn());
    JScrollPane scrollPane = new JScrollPane();
    JTable jTable;

    public Cart(){
        setTableContent();
        countTotalPrice();
        setLayout(null);
        setLocationAndSize();
        addContent();
        addActionListeners();
    }

    private void setTableContent(){
        jTable = new JTable(content, columns);
    }

    private void countTotalPrice(){
        int total = 0;
        for (int i = 0; i < content.length; i++) {
            total += Integer.parseInt(content[i][0]) * Integer.parseInt(content[i][4]);
        }
        labelTotalCost.setText("Summa: " + total);
    }

    private void setLocationAndSize(){
        labelCartandName.setBounds(10,5,300,30);
        labelTotalCost.setBounds(600,5,170,30);
        labelTotalCost.setHorizontalAlignment(SwingConstants.RIGHT);
        buttonBackToStore.setBounds(5,453,775,100);
        scrollPane.setBounds(5,50, 775,400);
    }

    private void addContent(){
        add(labelCartandName);
        add(labelTotalCost);
        labelTotalCost.setHorizontalAlignment(SwingConstants.RIGHT);
        add(buttonBackToStore);
        add(scrollPane);
        scrollPane.setViewportView(jTable);
    }

    private void addActionListeners(){
        buttonBackToStore.addActionListener(e -> {
            MainFrame mf = MainFrame.getInstance();
            Store store = new Store();
            mf.changeView(store);
        });
    }
}
