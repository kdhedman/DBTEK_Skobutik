import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class UI_AddToCart extends JPanel {
    Repository r1 = new Repository();
    ImageIcon imageIcon = new ImageIcon("res/Pictures/placeholder.jpg");
    JLabel labelWelcome = new JLabel("Välkommen, " + ActiveKund.getKund().getNamn() + "!");
    JLabel labelProductImage = new JLabel(imageIcon);
    JTextField jtfPris = new JTextField("Text");
    JTextField jtfLagerStatus = new JTextField("I lager: ");
    JComboBox jComboBoxStorlekar = new JComboBox();
    JComboBox jComboBoxColor = new JComboBox();
    JButton buttonAddToCart = new JButton("Lägg i kundvagn");
    JButton buttonShowCart = new JButton("Visa kundvagn");
    JButton activeButton;


    public UI_AddToCart() {
//        setLayout(null);  // Testar annan layoutform
//        setLocationAndSize(); //Det här ingår där också, kommentera bort för gamla design.
        drawBackground();
    }

    private void setLocationAndSize() {
        labelWelcome.setBounds(10, 0, 100, 30);
    }

    public void drawBackground() {
        final int[] id1 = new int[1];
        ArrayList<String> skor = r1.getskomodellFromDatabase();

        for (int i = 1; i <= skor.size(); i++) {
            JButton jb = new JButton(r1.getSkomodellFromDatabase(i));
            int finalIndex = i;
            jb.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    super.mouseEntered(e);
                    activeButton = jb;
                    ImageIcon update = null;
                    String sko1 = r1.getSkomodellFromDatabase(finalIndex);
                    update = new ImageIcon("res/Pictures/" + sko1 + ".png");
                    labelProductImage.setIcon(update);
                    jtfPris.setText("Pris: " + r1.getPrisFromDatabase(finalIndex));
                    jComboBoxStorlekar.removeAllItems();
                    ArrayList temp = r1.getStorlekarFromDatabase(finalIndex);
                    for (int i = 0; i < temp.size(); i++) {
                        jComboBoxStorlekar.addItem(temp.get(i));
                    }
                    jComboBoxColor.removeAllItems();
                    ArrayList temp2 = r1.getfärgFromDatabase(finalIndex, Integer.parseInt((String) jComboBoxStorlekar.getSelectedItem()));
                    for (int i = 0; i < temp2.size(); i++) {
                        jComboBoxColor.addItem(temp2.get(i));
                    }
                    updateLagerstatus();

//                    String color = null;
//                    color = (String) jComboBoxColor.getSelectedItem();
//                    int lagerstatus = Integer.parseInt(r1.getLagerstatusFromDatabase(finalIndex, Integer.parseInt((String) jComboBoxStorlekar.getSelectedItem()), color));
//                    jtfLagerStatus.setText("I lager: " + lagerstatus);
//                    System.out.println(color);
//                    System.out.println(Integer.parseInt((String) jComboBoxStorlekar.getSelectedItem()));
//                    buttonAddToCart.setEnabled((lagerstatus > 0));
//                    buttonAddToCart.setText("Lägg i kundvagn!");

                    id1[0] = finalIndex;

                }
            });
            add(jb);
        }
        add(labelProductImage);
        add(jtfPris);
        add(jComboBoxStorlekar);
        add(jComboBoxColor);
        add(jtfLagerStatus);
        add(buttonAddToCart);
        add(buttonShowCart);

        jComboBoxStorlekar.addActionListener(e -> {
            jComboBoxColor.removeAllItems();
            ArrayList temp = null;
            try {
                temp = r1.getfärgFromDatabase(id1[0], Integer.parseInt((String) jComboBoxStorlekar.getSelectedItem()));
                for (int i = 0; i < temp.size(); i++) {
                    jComboBoxColor.addItem(temp.get(i));
                }
                updateLagerstatus();
            } catch (NumberFormatException numberFormatException) {
                //Let's ignore this
            }

        });

        jComboBoxColor.addActionListener(e-> {
            try {
                updateLagerstatus();
            } catch (NumberFormatException numberFormatException) {
                //NOUP
            }
        });

        buttonAddToCart.addActionListener(e -> {
            r1.addToCart(id1[0], ActiveKund.getKund().getId(), Integer.parseInt(jComboBoxStorlekar.getSelectedItem().toString()), (String) jComboBoxColor.getSelectedItem());
            jtfLagerStatus.setText("I lager: " + r1.getLagerstatusFromDatabase(id1[0], Integer.parseInt((String) jComboBoxStorlekar.getSelectedItem()), (String) r1.getfärgFromDatabase(id1[0], Integer.parseInt((String) jComboBoxStorlekar.getSelectedItem())).get(0)));
            buttonAddToCart.setEnabled(false);
            buttonAddToCart.setText("Vara tilllagd!");
        });

        buttonShowCart.addActionListener(e -> {
            MainFrame mf = MainFrame.getInstance();
            Cart cart = new Cart();
            mf.changeView(cart);
        });
    }

    private void updateLagerstatus() throws NumberFormatException{
        int skoId = r1.getSkomodellIDbyModell(activeButton.getText());
        String color = (String) jComboBoxColor.getSelectedItem();
        int lagerstatus = Integer.parseInt(r1.getLagerstatusFromDatabase(skoId, Integer.parseInt((String) jComboBoxStorlekar.getSelectedItem()), color));
        jtfLagerStatus.setText("I lager: " + lagerstatus);
        buttonAddToCart.setEnabled(lagerstatus > 0);
        buttonAddToCart.setText("Lägg i kundvagn!");
    }
}