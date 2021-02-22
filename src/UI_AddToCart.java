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
//        ui.drawBackground();
    }

    public void drawBackground() {
//        JFrame f1 = new JFrame("Skobutik");
        final int[] id1 = new int[1];
        JPanel p2 = new JPanel();
        ImageIcon test = new ImageIcon("res/Pictures/skaterboi.png");
        JLabel l1 = new JLabel(test);
        JTextField jtf1 = new JTextField("Text");
        JTextField jtf2 = new JTextField("I lager: ");
        ArrayList<String> skor = R1.getskomodellFromDatabase();
        JComboBox jcb1 = new JComboBox();
        JComboBox jcb2 = new JComboBox();
        JButton jb1 = new JButton("Add to cart!!!!!!!!!!");
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
                    l1.setIcon(update);
                    jtf1.setText("Pris: "+R1.getPrisFromDatabase(finalIndex));
                    jcb1.removeAllItems();
                    ArrayList temp = R1.getStorlekarFromDatabase(finalIndex);
                    for (int i = 0; i < temp.size(); i++) {
                        jcb1.addItem(temp.get(i));
                    }
                    jcb2.removeAllItems();
                    ArrayList temp2 = R1.getfärgFromDatabase(finalIndex);
                    for (int i = 0; i < temp2.size(); i++) {
                        jcb2.addItem(temp2.get(i));
                    }
                    jtf2.setText("I lager: "+R1.getLagerstatusFromDatabase(finalIndex));
                    id1[0] = finalIndex;

                }
            });
            p1.add(jb);
        }
//        f1.add(p1);
        p1.add(p2);
        p2.add(l1);
        p1.add(jtf1);
        p1.add(jcb1);
        p1.add(jcb2);
        p1.add(jtf2);
        p1.add(jb1);

        jb1.addActionListener(e -> {
                try {
                    R1.addToCart(id1[0],8,Integer.parseInt(jcb1.getSelectedItem().toString()),(String)jcb2.getSelectedItem());
                    jtf2.setText("I lager: "+R1.getLagerstatusFromDatabase(id1[0]));
                } catch (ClassNotFoundException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
        });
//        f1.setSize(500, 500);
//        f1.setVisible(true);
    }

}
