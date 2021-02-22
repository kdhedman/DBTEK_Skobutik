
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
        JPanel p2 = new JPanel();
        ImageIcon test = new ImageIcon("C:\\Users\\apa3\\Pictures\\MEmu Photo\\1NUzZXw.jpeg");
        JLabel l1 = new JLabel(test);
        JTextField jtf1 = new JTextField("Text");
        JTextField jtf2 = new JTextField("I lager: ");
        ArrayList<String> skor = R1.getskomodellFromDatabase();
        JComboBox jcb1 = new JComboBox();
        JComboBox jcb2 = new JComboBox();
        String[] trollo = new String[]{"hej"};
        int index = 1;
        for (int i = 1; i <= skor.size(); i++) {
            JButton jb = new JButton(R1.getSkomodellFromDatabase(index));
            int finalIndex = index;
            jb.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);

                ImageIcon update = null;
                update = new ImageIcon("C:\\Users\\apa3\\Desktop\\Skola\\Databashantering\\Pictures\\" + R1.getSkomodellFromDatabase(finalIndex) + ".png");
                l1.setIcon(update);
                jtf1.setText("Pris: " + R1.getPrisFromDatabase(finalIndex));
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
                jtf2.setText("I lager: " + R1.getLagerstatusFromDatabase(finalIndex));
                }
            });
//            jb.addActionListener(e->{
//                ImageIcon update = null;
//                update = new ImageIcon("C:\\Users\\apa3\\Desktop\\Skola\\Databashantering\\Pictures\\" + R1.getSkomodellFromDatabase(finalIndex) + ".png");
//                l1.setIcon(update);
//                jtf1.setText("Pris: " + R1.getPrisFromDatabase(finalIndex));
//                jcb1.removeAllItems();
//                ArrayList temp = R1.getStorlekarFromDatabase(finalIndex);
//                for (int j = 0; j < temp.size(); j++) {
//                    jcb1.addItem(temp.get(j));
//                }
//                jcb2.removeAllItems();
//                ArrayList temp2 = R1.getfärgFromDatabase(finalIndex);
//                for (int j = 0; j < temp2.size(); j++) {
//                    jcb2.addItem(temp2.get(j));
//                }
//                jtf2.setText("I lager: " + R1.getLagerstatusFromDatabase(finalIndex));
//            });

            p1.add(jb);
            index++;
        }
//        f1.add(p1);
        p1.add(p2);
        p2.add(l1);
        p1.add(jtf1);
        p1.add(jcb1);
        p1.add(jcb2);
        p1.add(jtf2);
//        f1.setSize(500, 500);
//        f1.setVisible(true);
    }
}
