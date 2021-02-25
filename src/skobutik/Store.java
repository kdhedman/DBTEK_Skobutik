package skobutik;

import dbObjects.*;
import iodb.Repository;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Store extends JPanel {
    Repository r1 = new Repository();
    List<Skomodell> shoes = r1.getSkomodellerAsOBjects();
    JButton activeButton;
    Skomodell activeShoe;

    BlockingQueue<Object> hate = new LinkedBlockingQueue<>();


    JLabel labelWelcome = new JLabel("Välkommen, " + ActiveKund.getKund().getNamn() + "!");
    JPanel shoeContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 4, 4));
    JScrollPane scrollPaneShoes = new JScrollPane(shoeContainer, ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

    ImageIcon imageIconShoe = new ImageIcon("res/Pictures/placeholder.jpg");
    JLabel labelProductImage = new JLabel(imageIconShoe);
    JLabel labelProduct = new JLabel("SkomodellSkomodell");
    JLabel labelPris = new JLabel("Pris: Woowee!");
    JLabel labelStorlek = new JLabel("Storlek:");
    JLabel labelColor = new JLabel("Färg:");
    JLabel labelLagerstatus = new JLabel("I lager: ");
    JFormattedTextField jtfMedelbetyg = new JFormattedTextField();
    JComboBox comboBoxStorlekar = new JComboBox();
    JComboBox comboBoxColor = new JComboBox();
    JButton buttonAddToCart = new JButton("Lägg i kundvagn");
    JButton buttonShowCart = new JButton("Visa kundvagn");
    JScrollPane scrollPane = new JScrollPane();
    String[] tableHeader = {"Användare", "Betyg", "Kommentar"};
    JLabel labelBetyg = new JLabel("Betyg: ");
    JTextField rating = new JTextField("1-4");
    JLabel labelComment = new JLabel(" Kommentar: ");
    JLabel labelCommentCounter = new JLabel("00");
    JTextField comment = new JTextField("Kommentar (Max 50 tecken)");
    JButton buttonRate = new JButton("Åsikta dig!");

    public Store() {
        setLayout(null);  // Testar annan layoutform
        setLocationAndSize(); //Det här ingår där också, kommentera bort för gamla design.
        addComponents();
        editComponents();
        updateGUI();
        addActionListeners();
    }

    private void setLocationAndSize() {
        labelWelcome.setBounds(0, 0, 800, 30);
        //Maybe add categories here.
        scrollPaneShoes.setBounds(5, 30, 775, 76);
        labelProductImage.setBounds(20, 110, 200, 200);
        labelProduct.setBounds(220, 130, 200, 30);
        labelPris.setBounds(220, 150, 100, 40);
        labelStorlek.setBounds(220, 170, 50, 40);
        comboBoxStorlekar.setBounds(220, 200, 60, 40);
        labelColor.setBounds(280, 170, 50, 40);
        comboBoxColor.setBounds(290, 200, 60, 40);
        labelLagerstatus.setBounds(230, 240, 70, 30);
        scrollPane.setBounds(5, 355, 775, 200);
        labelBetyg.setBounds(5,325, 50, 30);
        rating.setBounds(80,325, 50, 30 );
        labelComment.setBounds(130, 325, 80, 30);
        comment.setBounds(210,325,450,30);
        buttonRate.setBounds(679,324,100,29);
        labelCommentCounter.setBounds(659, 325, 20, 30);
        buttonAddToCart.setBounds(405, 150, 370, 60);
        buttonShowCart.setBounds(405, 220, 370, 60);
    }

    private void editComponents() {
        Font font = labelProduct.getFont();
        labelWelcome.setHorizontalAlignment(SwingConstants.CENTER);
        labelProduct.setHorizontalAlignment(SwingConstants.LEFT);
        labelColor.setHorizontalAlignment(SwingConstants.RIGHT);
        labelStorlek.setHorizontalAlignment(SwingConstants.RIGHT);
        labelLagerstatus.setHorizontalAlignment(SwingConstants.LEFT);
        labelProduct.setFont(new Font(font.getName(), Font.PLAIN, 20));
        scrollPane.setVerticalScrollBar(new JScrollBar());
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        labelCommentCounter.setHorizontalAlignment(SwingConstants.CENTER);
    }

    private void addComponents() {
        add(labelWelcome);
        addShoeButtons(shoeContainer);
        add(scrollPaneShoes);
        add(labelProductImage);
        add(labelProduct);
        add(labelPris);
        add(labelColor);
        add(comboBoxColor);
        add(labelStorlek);
        add(comboBoxStorlekar);
        add(labelLagerstatus);
        updateMedelbetyg();
        updateReviewTable();
        add(scrollPane);
        add(labelBetyg);
        add(rating);
        add(labelComment);
        add(comment);
        add(buttonRate);
        add(labelCommentCounter);
        add(buttonAddToCart);
        add(buttonShowCart);
    }

    private void addShoeButtons(JPanel container) {
        shoes.stream().forEach(shoe -> {
            JButton button = new JButton(shoe.toString());
            activeButton = button;
            button.setPreferredSize(new Dimension(110, 50));
            addShoeButtonActionListener(button);
            container.add(button);
        });
    }

    private void addShoeButtonActionListener(JButton button) {
        button.addActionListener(l -> {
            activeButton = button;
            updateGUI();
        });
    }


    private void addActionListeners() {
        addComboBoxStorlekarActionListener();
        addComboBoxColorActionListener();

        buttonAddToCart.addActionListener(e -> {
            r1.addToCart(activeShoe.id, ActiveKund.getKund().getId(), Integer.parseInt(comboBoxStorlekar.getSelectedItem().toString()), (String) comboBoxColor.getSelectedItem());
            labelLagerstatus.setText("I lager: " + r1.getLagerstatusFromDatabase(
                    activeShoe.id,
                    Integer.parseInt((String) comboBoxStorlekar.getSelectedItem()),
                    (String) r1.getfärgFromDatabase(activeShoe.id, Integer.parseInt((String) comboBoxStorlekar.getSelectedItem())).get(0)));
            buttonAddToCart.setEnabled(false);
            buttonAddToCart.setText("Vara tilllagd!");
        });

        buttonShowCart.addActionListener(e -> {
            MainFrame mf = MainFrame.getInstance();
            Cart cart = new Cart();
            mf.changeView(cart);
        });

        buttonRate.addActionListener(e -> {
            String name = ActiveKund.getKund().getNamn();
            int kundId = r1.getKundIDFromNamn(name);
            int skomodellID = r1.getSkomodellIDbyModell(activeButton.getText());
            int ratingInt = 0;
            try {
                ratingInt = Integer.parseInt(rating.getText());
            } catch (NumberFormatException nfe) {
                rating.setText("1-4");
                return;
            }
            if (ratingInt <= 0 || ratingInt > 4) {
                rating.setText("1-4");
                return;
            }

            if (comment.getText().length() > 50) {
                comment.setText("Kommentar: (max 50 tecken)");
                return;
            }
            r1.setRating(ratingInt, comment.getText(), kundId, skomodellID);
            updateReviewTable();
            rating.setText("1-4");
            comment.setText("Kommentar");
        });

        comment.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                labelCommentCounter.setText(String.valueOf(comment.getText().length()));
            }
        });
    }

    private void addComboBoxStorlekarActionListener(){
        comboBoxStorlekar.addActionListener(e -> {
            comboBoxColor.removeAllItems();
            ArrayList temp = null;
            try {
                temp = r1.getfärgFromDatabase(activeShoe.id, Integer.parseInt((String) comboBoxStorlekar.getSelectedItem()));
                for (int i = 0; i < temp.size(); i++) {
                    comboBoxColor.addItem(temp.get(i));
                }
                updateLabelLagerStatus();
            } catch (NumberFormatException numberFormatException) {
                //Let's ignore this
            }
        });

    }

    private void addComboBoxColorActionListener(){

        comboBoxColor.addActionListener(e -> {
            try {
                updateLabelLagerStatus();
            } catch (NumberFormatException numberFormatException) {
                //NOUP
            }
        });
    }

    private void updateGUI() {
        activeShoe = shoes.stream().filter(shoe -> shoe.toString().equals(activeButton.getText())).findFirst().get();
        labelProductImage.setIcon(new ImageIcon(String.format("res/Pictures/%s.png", activeShoe.skomodell)));
        labelProduct.setText(activeShoe.skomodell);
        labelPris.setText(String.format("%d valuta", activeShoe.pris));

        comboBoxStorlekar.removeAllItems();
        ArrayList temp = r1.getStorlekarFromDatabase(activeShoe.id);
        for (int i = 0; i < temp.size(); i++) {
            comboBoxStorlekar.addItem(temp.get(i));
        }
        comboBoxColor.removeAllItems();
        ArrayList temp2 = r1.getfärgFromDatabase(activeShoe.id, Integer.parseInt((String) comboBoxStorlekar.getSelectedItem()));
        for (int i = 0; i < temp2.size(); i++) {
            comboBoxColor.addItem(temp2.get(i));
        }

        updateReviewTable();
    }

    private void updateLabelLagerStatus() throws NumberFormatException{
        int skoId = r1.getSkomodellIDbyModell(activeButton.getText());
        String color = (String) comboBoxColor.getSelectedItem();
        int lagerstatus = Integer.parseInt(r1.getLagerstatusFromDatabase(skoId, Integer.parseInt((String) comboBoxStorlekar.getSelectedItem()), color));
        labelLagerstatus.setText("I lager: " + lagerstatus);
        buttonAddToCart.setEnabled(lagerstatus > 0);
        buttonAddToCart.setText("Lägg i kundvagn!");
        this.revalidate();
    }

    private void updateMedelbetyg() {
        int skoId = r1.getSkomodellIDbyModell(activeButton.getText());
        float medelbetyg = r1.getSkomodellAverageBetyg(skoId);
        jtfMedelbetyg.setText(String.format("Medel betyg: %1.1f", medelbetyg));
        if (medelbetyg == 0) jtfMedelbetyg.setText("Ej betygsatt.");
    }


    private void updateReviewTable() {
        JTable newTable;
        newTable = new JTable(r1.skomodellBetyg(activeButton.getText()), tableHeader);

        newTable.setDragEnabled(false);
        newTable.getColumnModel().getColumn(0).setMaxWidth(100);
        newTable.getColumnModel().getColumn(1).setMaxWidth(100);
        newTable.getColumnModel().getColumn(2).setMinWidth(607);
        newTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        scrollPane.setViewportView(newTable);
    }
}