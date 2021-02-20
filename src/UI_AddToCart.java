import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class UI_AddToCart {
    Repository R1 = new Repository();
    BufferedImage pic1 = ImageIO.read(new File("C:\\Users\\apa3\\Pictures\\MEmu Photo\\1NUzZXw.jpeg"));

    public UI_AddToCart() throws IOException {
    }

    public static void main(String[] args) throws IOException {
        UI_AddToCart ui = new UI_AddToCart();
        ui.drawBackground();
    }

    public void drawBackground() throws IOException {
        String skoFrånDb;
        JFrame f1 = new JFrame("Skobutik");
        JPanel p1 = new JPanel();
        JPanel p2 = new JPanel();
        ImageIcon test = new ImageIcon("C:\\Users\\apa3\\Pictures\\MEmu Photo\\1NUzZXw.jpeg");
        JLabel l1 = new JLabel(test);
        JTextField jtp1 = new JTextField("Text");

        JButton jb1 = new JButton("Sko1");
        jb1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                ImageIcon update = new ImageIcon("C:\\Users\\apa3\\Pictures\\MEmu Photo\\1NUzZXw.jpeg");
                l1.setIcon(update);
                f1.setVisible(true);
                jtp1.setText();
            }
        });

        JButton jb2 = new JButton("Sko2");
        jb2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                ImageIcon update = new ImageIcon("C:\\Users\\apa3\\Pictures\\MEmu Photo\\4m2bojA.jpg");
                l1.setIcon(update);
                f1.setVisible(true);
            }
        });
        JButton jb3 = new JButton("Sko3");
        jb3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                ImageIcon update = new ImageIcon("C:\\Users\\apa3\\Pictures\\MEmu Photo\\1fZubUv.jpeg");
                l1.setIcon(update);
                f1.setVisible(true);
            }
        });
        JButton jb4 = new JButton("Sko4");
        jb4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                ImageIcon update = new ImageIcon("C:\\Users\\apa3\\Pictures\\MEmu Photo\\lc3nsjF.jpg");
                l1.setIcon(update);
                f1.setVisible(true);
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
        f1.setSize(500, 500);
        f1.setVisible(true);

    }

    public void bgchange(String s1) {

    }

}
