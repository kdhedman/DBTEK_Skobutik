import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class UI_AddToCart extends JPanel {
    Repository R1 = new Repository();
    ImageIcon imageIcon = new ImageIcon("res/Pictures/placeholder.jpg");
    JLabel labelWelcome = new JLabel("Välkommen, " + ActiveKund.getKund().getNamn() + "!");
    JLabel labelProductImage = new JLabel(imageIcon);
    JTextField jtfPris = new JTextField("Text");
    JTextField jtfLagerStatus = new JTextField("I lager: ");
    JComboBox jComboBoxStorlekar = new JComboBox();
    JComboBox jComboBoxColor = new JComboBox();
    JButton buttonAddToCart = new JButton("Lägg i kundvagn");
    JButton buttonShowCart = new JButton("Visa kundvagn");

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
        ArrayList<String> skor = R1.getskomodellFromDatabase();

        for (int i = 1; i <= skor.size(); i++) {
            JButton jb = new JButton(R1.getSkomodellFromDatabase(i));
            int finalIndex = i;
            jb.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    super.mouseEntered(e);

                    ImageIcon update = null;
                    String sko1 = R1.getSkomodellFromDatabase(finalIndex);
                    update = new ImageIcon("res/Pictures/" + sko1 + ".png");
                    labelProductImage.setIcon(update);
                    jtfPris.setText("Pris: " + R1.getPrisFromDatabase(finalIndex));
                    jComboBoxStorlekar.removeAllItems();
                    ArrayList temp = R1.getStorlekarFromDatabase(finalIndex);
                    for (int i = 0; i < temp.size(); i++) {
                        jComboBoxStorlekar.addItem(temp.get(i));
                    }
                    jComboBoxColor.removeAllItems();
                    ArrayList temp2 = R1.getfärgFromDatabase(finalIndex, Integer.parseInt((String) jComboBoxStorlekar.getSelectedItem()));
                    for (int i = 0; i < temp2.size(); i++) {
                        jComboBoxColor.addItem(temp2.get(i));
                    }
                    String tempstr = null;

                    tempstr = (String) jComboBoxColor.getSelectedItem();
                    System.out.println(tempstr);
                    int lagerstatus = Integer.parseInt(R1.getLagerstatusFromDatabase(finalIndex, Integer.parseInt((String) jComboBoxStorlekar.getSelectedItem()), tempstr));
                    jtfLagerStatus.setText("I lager: " + lagerstatus);
                    System.out.println(Integer.parseInt((String) jComboBoxStorlekar.getSelectedItem()));
                    buttonAddToCart.setText("Lägg i kundvagn!");
                    buttonAddToCart.setEnabled((lagerstatus > 0));

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
                temp = R1.getfärgFromDatabase(id1[0], Integer.parseInt((String) jComboBoxStorlekar.getSelectedItem()));
                for (int i = 0; i < temp.size(); i++) {
                    jComboBoxColor.addItem(temp.get(i));
                }
            } catch (NumberFormatException numberFormatException) {
                //Let's ignore this
            }

        });

        buttonAddToCart.addActionListener(e -> {
            R1.addToCart(id1[0], ActiveKund.getKund().getId(), Integer.parseInt(jComboBoxStorlekar.getSelectedItem().toString()), (String) jComboBoxColor.getSelectedItem());
            jtfLagerStatus.setText("I lager: " + R1.getLagerstatusFromDatabase(id1[0], Integer.parseInt((String) jComboBoxStorlekar.getSelectedItem()), (String) R1.getfärgFromDatabase(id1[0], Integer.parseInt((String) jComboBoxStorlekar.getSelectedItem())).get(0)));
            buttonAddToCart.setEnabled(false);
            buttonAddToCart.setText("Vara tilllagd!");
        });

        buttonShowCart.addActionListener(e -> {
            MainFrame mf = MainFrame.getInstance();
            Cart cart = new Cart();
            mf.changeView(cart);
        });
    }
}