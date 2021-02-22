import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;


public class UI_AddToCart {
    Repository R1 = new Repository();
    BufferedImage pic1 = ImageIO.read(new File("C:\\Users\\apa3\\Pictures\\MEmu Photo\\1NUzZXw.jpeg"));

    public UI_AddToCart() throws IOException {
    }

    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {
        UI_AddToCart ui = new UI_AddToCart();
        ui.drawBackground();
    }

    public void drawBackground() throws IOException, SQLException, ClassNotFoundException {
        JFrame f1 = new JFrame("Skobutik");
        JPanel p1 = new JPanel();
        JPanel p2 = new JPanel();
        ImageIcon test = new ImageIcon("C:\\Users\\apa3\\Pictures\\MEmu Photo\\1NUzZXw.jpeg");
        JLabel l1 = new JLabel(test);
        JTextField jtp1 = new JTextField("Text");
        JComboBox jcb1 = new JComboBox();
        JComboBox jcb2 = new JComboBox();
        String[] trollo = new String[]{"hej"};


        JButton jb1 = new JButton(R1.getSkomodellFromDatabase(1));
        jb1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                ImageIcon update = new ImageIcon("C:\\Users\\apa3\\Desktop\\Skola\\Databashantering\\Pictures\\skaterboi.png");
                l1.setIcon(update);
                try {
                    jtp1.setText(R1.getPrisFromDatabase(1));
                    jcb1.removeAllItems();
                    ArrayList temp = R1.getStorlekarFromDatabase(1);
                    for (int i = 0; i < temp.size(); i++) {
                        jcb1.addItem(temp.get(i));
                    }
                    jcb2.removeAllItems();
                    ArrayList temp2 = R1.getfärgFromDatabase(1);
                    for (int i = 0; i < temp2.size(); i++) {
                        jcb2.addItem(temp2.get(i));
                    }

                } catch (ClassNotFoundException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

        JButton jb2 = new JButton(R1.getSkomodellFromDatabase(2));
        jb2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                ImageIcon update = new ImageIcon("C:\\Users\\apa3\\Desktop\\Skola\\Databashantering\\Pictures\\skatergrl.png");
                l1.setIcon(update);
                try {
                    jtp1.setText(R1.getPrisFromDatabase(2));
                    jcb1.removeAllItems();
                    ArrayList temp = R1.getStorlekarFromDatabase(2);
                    for (int i = 0; i < temp.size(); i++) {
                        jcb1.addItem(temp.get(i));
                    }
                    jcb2.removeAllItems();
                    ArrayList temp2 = R1.getfärgFromDatabase(2);
                    for (int i = 0; i < temp2.size(); i++) {
                        jcb2.addItem(temp2.get(i));
                    }

                } catch (ClassNotFoundException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

        JButton jb3 = new JButton(R1.getSkomodellFromDatabase(3));
        jb3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                ImageIcon update = new ImageIcon("C:\\Users\\apa3\\Desktop\\Skola\\Databashantering\\Pictures\\tank.png");
                l1.setIcon(update);
                l1.setIcon(update);
                try {
                    jtp1.setText(R1.getPrisFromDatabase(3));
                } catch (ClassNotFoundException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
        JButton jb4 = new JButton(R1.getSkomodellFromDatabase(4));
        jb4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                ImageIcon update = new ImageIcon("C:\\Users\\apa3\\Desktop\\Skola\\Databashantering\\Pictures\\vanlijsko.png");
                l1.setIcon(update);
                l1.setIcon(update);
                try {
                    jtp1.setText(R1.getPrisFromDatabase(4));
                } catch (ClassNotFoundException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

        f1.add(p1);
        p1.add(jb1);
        p1.add(jb2);
        p1.add(jb3);
        p1.add(jb4);
        p1.add(p2);
        p2.add(l1);
        p1.add(jtp1);
        p1.add(jcb1);
        p1.add(jcb2);
        f1.setSize(500, 500);
        f1.setVisible(true);

    }

    public void bgchange(String s1) {

    }

}
