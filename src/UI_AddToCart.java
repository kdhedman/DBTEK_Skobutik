import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;

public class UI_AddToCart {
    Repository R1 = new Repository();
    JPanel p1 = new JPanel();

    public UI_AddToCart() {
        drawBackground();
    }

    public static void main(String[] args){
        UI_AddToCart ui = new UI_AddToCart();
    }

    public void drawBackground() {
        final int[] id1 = new int[1];
        JPanel p2 = new JPanel();
        ImageIcon imageIcon = new ImageIcon("res/Pictures/placeholder.jpg");
        JLabel labelProductImage = new JLabel(imageIcon);
        JTextField jtfPris = new JTextField("Text");
        JTextField jtfLagerStatus = new JTextField("I lager: ");
        ArrayList<String> skor = R1.getskomodellFromDatabase();
        JComboBox jComboBoxStorlekar = new JComboBox();
        JComboBox jComboBoxColor = new JComboBox();
        JButton buttonAddToCart = new JButton("Lägg i kundvagn");
        JButton buttonShowCart = new JButton("Visa kundvagn");

        addActionListeners();

        jComboBoxStorlekar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jComboBoxColor.removeAllItems();
                ArrayList temp = R1.getfärgFromDatabase(id1[0],Integer.parseInt((String )jComboBoxStorlekar.getSelectedItem()));
                for (int i = 0; i < temp.size(); i++) {
                    jComboBoxColor.addItem(temp.get(i));
                }
            }
        });

        for (int i = 1; i <= skor.size(); i++) {
            JButton jb = new JButton(R1.getSkomodellFromDatabase(i));
            int finalIndex = i;
            jb.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    super.mouseEntered(e);

                    ImageIcon update = null;
                    String sko1 =R1.getSkomodellFromDatabase(finalIndex);
                    update = new ImageIcon("res/Pictures/"+ sko1 +".png");
                    labelProductImage.setIcon(update);
                    jtfPris.setText("Pris: "+R1.getPrisFromDatabase(finalIndex));
                    jComboBoxStorlekar.removeAllItems();
                    ArrayList temp = R1.getStorlekarFromDatabase(finalIndex);
                    for (int i = 0; i < temp.size(); i++) {
                        jComboBoxStorlekar.addItem(temp.get(i));
                    }
                    jComboBoxColor.removeAllItems();
                    ArrayList temp2 = R1.getfärgFromDatabase(finalIndex, Integer.parseInt((String )jComboBoxStorlekar.getSelectedItem()));
                    for (int i = 0; i < temp2.size(); i++) {
                        jComboBoxColor.addItem(temp2.get(i));
                    }
                    String tempstr = null;
                    try {
                        tempstr = (String)jComboBoxColor.getSelectedItem();
                        System.out.println(tempstr);
//                                (String)R1.getfärgFromDatabase(id1[0],Integer.parseInt((String )jComboBoxStorlekar.getSelectedItem()));
                        jtfLagerStatus.setText("I lager: "+R1.getLagerstatusFromDatabase(finalIndex,Integer.parseInt((String)jComboBoxStorlekar.getSelectedItem()),tempstr));
                        System.out.println(Integer.parseInt((String)jComboBoxStorlekar.getSelectedItem()));
                    } catch (NumberFormatException numberFormatException) {
                        numberFormatException.printStackTrace();
                    } catch (IndexOutOfBoundsException hhh){
                        jtfLagerStatus.setText("sdfsdf");
                    }

                    id1[0] = finalIndex;

                }
            });
            p1.add(jb);
        }
        p1.add(p2);
        p2.add(labelProductImage);
        p1.add(jtfPris);
        p1.add(jComboBoxStorlekar);
        p1.add(jComboBoxColor);
        p1.add(jtfLagerStatus);
        p1.add(buttonAddToCart);
        p1.add(buttonShowCart);

        buttonAddToCart.addActionListener(e -> {
                try {
                    R1.addToCart(id1[0],8,Integer.parseInt(jComboBoxStorlekar.getSelectedItem().toString()),(String)jComboBoxColor.getSelectedItem());
                    jtfLagerStatus.setText("I lager: "+R1.getLagerstatusFromDatabase(id1[0],Integer.parseInt((String)jComboBoxStorlekar.getSelectedItem()),(String)R1.getfärgFromDatabase(id1[0],Integer.parseInt((String )jComboBoxStorlekar.getSelectedItem())).get(0)));
                } catch (ClassNotFoundException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
        });

        buttonShowCart.addActionListener(e -> {
            MainFrame mf = MainFrame.getInstance();
            Cart cart = new Cart();
            mf.changeView(cart);
        });
    }
}