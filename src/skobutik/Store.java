package skobutik;

import dbObjects.*;
import iodb.Repository;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Store extends JPanel {
    Repository r1 = new Repository();
    List<Skomodell> shoes = r1.getSkomodellerAsOBjects();
    JButton activeButton;
    Skomodell activeShoe;


    JLabel labelWelcome = new JLabel("Välkommen, " + ActiveKund.getKund().getNamn() + "!");
    JPanel shoeContainer = new JPanel(new FlowLayout(FlowLayout.CENTER,4,4));
    JScrollPane scrollPaneShoes = new JScrollPane(shoeContainer, ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

    ImageIcon imageIconShoe = new ImageIcon("res/Pictures/placeholder.jpg");
    JLabel labelProductImage = new JLabel(imageIconShoe);
    JLabel labelProduct = new JLabel("SkomodellSkomodell");
    JLabel labelPris = new JLabel("Pris: Woowee!");
    JLabel labelColor = new JLabel("Färg       ");
    JLabel labelStorlek = new JLabel("Storlek     ");
    JLabel labelLagerstatus = new JLabel("I lager: ");

    JFormattedTextField jtfMedelbetyg = new JFormattedTextField();

    JComboBox comboBoxStorlekar = new JComboBox();
    JComboBox comboBoxColor = new JComboBox();
    JButton buttonAddToCart = new JButton("Lägg i kundvagn");
    JButton buttonShowCart = new JButton("Visa kundvagn");
    JScrollPane scrollPane = new JScrollPane();
    String[] tableHeader = {"Användare", "Betyg", "Kommentar"};
    JLabel labelBetyg = new JLabel("Betyg: ");
    JTextField rating = new JTextField("");
    JLabel labelComment = new JLabel(" Kommentar: ");
    JLabel labelCommentCounter = new JLabel();
    JTextField comment = new JTextField("Kommentar (Max 50 tecken)");
    JButton buttonRate = new JButton("Åsikta dig!");

    public Store() {
        setLayout(null);  // Testar annan layoutform
        setLocationAndSize(); //Det här ingår där också, kommentera bort för gamla design.
        addComponents();
        editComponents();
        updateGUI();
        addActionListeners();
//        drawBackground();
    }

    public static void main(String[] args) {
        Kund kund = new Kund(2, "Elin", null, null);
        ActiveKund.setKund(kund);
        JFrame frame = new JFrame();
        frame.setTitle("Skobutik");
        frame.setSize(800, 600);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Store store = new Store();
        frame.add(store);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void setLocationAndSize() {
        labelWelcome.setBounds(0, 0, 800, 30);
        //Maybe add categories here.
        scrollPaneShoes.setBounds(5, 30, 775,76);
        labelProductImage.setBounds(20,110,200,200);
        labelProduct.setBounds(220, 130, 200, 30);
        labelPris.setBounds(220,150,100,40);
        labelStorlek.setBounds(220, 170, 60, 40);
        comboBoxStorlekar.setBounds(220, 205, 60, 40);
        labelColor.setBounds(290, 170, 60,40);
        comboBoxColor.setBounds(290, 205, 60, 40);
        labelLagerstatus.setBounds(220, 240, 100, 30);
    }

    private void editComponents(){
        Font font = labelProduct.getFont();
        labelWelcome.setHorizontalAlignment(SwingConstants.CENTER);
        labelProduct.setHorizontalAlignment(SwingConstants.LEFT);
        labelColor.setHorizontalAlignment(SwingConstants.CENTER);
        labelStorlek.setHorizontalAlignment(SwingConstants.CENTER);

        labelProduct.setFont(new Font(font.getName(), Font.PLAIN, 20));
    }

    private void addComponents(){
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
    }

    private void addShoeButtons(JPanel container){
        shoes.stream().forEach(shoe -> {

            JButton button = new JButton(shoe.skomodell);
            activeButton = button;
            int width = button.getWidth();
            button.setPreferredSize(new Dimension(110, 50));
            addShoeButtonActionListener(button);
            container.add(button);
        });
    }

    private void addShoeButtonActionListener(JButton button){
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                activeButton = button;
                updateGUI();
            }
        });
    }

    private void addActionListeners(){
        comboBoxStorlekar.addActionListener(l -> {
            try {
                updateComboBoxColors();
            } catch (NullPointerException e) {
                //I Don't care!
            }
        });

        comboBoxColor.addActionListener(l -> {
            updateLabelLagerStatus();
        });
    }

    private void updateGUI() throws InterruptedException {
        activeShoe = shoes.stream().filter(shoe -> shoe.getSkomodell().equals(activeButton.getText())).findFirst().get();

        labelProductImage.setIcon(new ImageIcon("res/Pictures/" + activeShoe.getSkomodell() + ".png"));
        labelProduct.setText(activeShoe.getSkomodell());
        labelPris.setText(String.format("%s valuta", String.valueOf(activeShoe.pris)));


        comboBoxStorlekar.removeAllItems();
        List<Integer> sizes = activeShoe.sizeColorQuantityMap.keySet().stream().map(Storlek::getSkostorlek).collect(Collectors.toList());
        Collections.sort(sizes);
        sizes.stream().forEach(size -> comboBoxStorlekar.addItem(size));
        try {
            updateComboBoxColors();
        } catch (NullPointerException e) {
            //I DONT CARE!
        }
    }

    private void updateComboBoxColors() throws NullPointerException{
        comboBoxColor.removeAllItems();
        List<String> colors = activeShoe.sizeColorQuantityMap.get(
                activeShoe.sizeColorQuantityMap.keySet().
                        stream().filter(size -> size.getSkostorlek() == (int)comboBoxStorlekar.getSelectedItem()).findFirst().get()).
                keySet().stream().map(Färg::getFärg).collect(Collectors.toList());
        Collections.sort(colors);
        colors.stream().forEach(color -> comboBoxColor.addItem(color));
    }

    private void updateLabelLagerStatus(){

        Storlek storlek = activeShoe.sizeColorQuantityMap.keySet().stream().filter(
                size -> size.getSkostorlek() == (int)comboBoxStorlekar.getSelectedItem()).findFirst().get();
        System.out.println(storlek.toString());
        Färg color = activeShoe.sizeColorQuantityMap.get(storlek).keySet().stream().filter(
                c -> c.färg.equals(comboBoxColor.getSelectedItem())).findFirst().get();
        int lagerstatus = activeShoe.sizeColorQuantityMap.get(storlek).get(color).lagerstatus;
    }

    private void updateLagerStatus(){
        //        int lagerstatus = Integer.parseInt(r1.getLagerstatusFromDatabase(skoId, Integer.parseInt((String) comboBoxStorlekar.getSelectedItem()), color));
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
                    labelPris.setText("Pris: " + r1.getPrisFromDatabase(finalIndex));
                    comboBoxStorlekar.removeAllItems();
                    ArrayList temp = r1.getStorlekarFromDatabase(finalIndex);
                    for (int i = 0; i < temp.size(); i++) {
                        comboBoxStorlekar.addItem(temp.get(i));
                    }
                    comboBoxColor.removeAllItems();
                    ArrayList temp2 = r1.getfärgFromDatabase(finalIndex, Integer.parseInt((String) comboBoxStorlekar.getSelectedItem()));
                    for (int i = 0; i < temp2.size(); i++) {
                        comboBoxColor.addItem(temp2.get(i));
                    }
//                    updateLagerstatus();
                    updateMedelbetyg();
                    updateReviewTable();

                    id1[0] = finalIndex;

                }
            });
            add(jb);
            activeButton = jb;
        }
        add(labelProductImage);
        add(labelPris);
        add(comboBoxStorlekar);
        add(comboBoxColor);
        add(labelLagerstatus);
        add(jtfMedelbetyg);
        buttonAddToCart.setPreferredSize(new Dimension(235,30));
        add(buttonAddToCart);
        buttonShowCart.setPreferredSize(new Dimension(235,30));
        add(buttonShowCart);
        add(labelBetyg);
        rating.setPreferredSize(new Dimension(25,30));
        rating.setText("1-4");
        add(rating);
        add(labelComment);
        comment.setPreferredSize(new Dimension(220,30));
        add(comment);
        add(buttonRate);

        updateReviewTable();
        scrollPane.setVerticalScrollBar(new JScrollBar());
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane);
        revalidate();

        comboBoxStorlekar.addActionListener(e -> {
            comboBoxColor.removeAllItems();
            ArrayList temp = null;
            try {
                temp = r1.getfärgFromDatabase(id1[0], Integer.parseInt((String) comboBoxStorlekar.getSelectedItem()));
                for (int i = 0; i < temp.size(); i++) {
                    comboBoxColor.addItem(temp.get(i));
                }
//                updateLabelLagerStatus();
            } catch (NumberFormatException numberFormatException) {
                //Let's ignore this
            }
        });

        comboBoxColor.addActionListener(e-> {
            try {
//                updateLabelLagerStatus();
            } catch (NumberFormatException numberFormatException) {
                //NOUP
            }
        });

        buttonAddToCart.addActionListener(e -> {
            r1.addToCart(id1[0], ActiveKund.getKund().getId(), Integer.parseInt(comboBoxStorlekar.getSelectedItem().toString()), (String) comboBoxColor.getSelectedItem());
            labelLagerstatus.setText("I lager: " + r1.getLagerstatusFromDatabase(id1[0], Integer.parseInt((String) comboBoxStorlekar.getSelectedItem()), (String) r1.getfärgFromDatabase(id1[0], Integer.parseInt((String) comboBoxStorlekar.getSelectedItem())).get(0)));
            buttonAddToCart.setEnabled(false);
            buttonAddToCart.setText("Vara tilllagd!");
        });

        buttonShowCart.addActionListener(e -> {
            MainFrame mf = MainFrame.getInstance();
            Cart cart = new Cart();
            mf.changeView(cart);
        });

        buttonRate.addActionListener(e->{
            String name = ActiveKund.getKund().getNamn();
            int kundId = r1.getKundIDFromNamn(name);
            int skomodellID = r1.getSkomodellIDbyModell(activeButton.getText());
            int ratingInt = 0;
            try{
                ratingInt = Integer.parseInt(rating.getText());
            } catch (NumberFormatException nfe){
                rating.setText("1-4");
                return;
            }
            if(ratingInt <= 0 || ratingInt > 4){
                rating.setText("1-4");
                return;
            }

            if(comment.getText().length() > 50){
                comment.setText("Kommentar: (max 50 tecken)");
                return;
            }
            r1.setRating(ratingInt, comment.getText(), kundId, skomodellID);
            updateReviewTable();
            rating.setText("1-4");
            comment.setText("Kommentar");
        });

        updateMedelbetyg();
    }

    private void updateMedelbetyg(){
        int skoId = r1.getSkomodellIDbyModell(activeButton.getText());
        float medelbetyg  = r1.getSkomodellAverageBetyg(skoId);
        jtfMedelbetyg.setText(String.format("Medel betyg: %1.1f",medelbetyg));
        if (medelbetyg == 0) jtfMedelbetyg.setText("Ej betygsatt.");
    }


//    private void updateLagerstatus() throws NumberFormatException{
//        int skoId = r1.getSkomodellIDbyModell(activeButton.getText());
//        String color = (String) comboBoxColor.getSelectedItem();
//        int lagerstatus = Integer.parseInt(r1.getLagerstatusFromDatabase(skoId, Integer.parseInt((String) comboBoxStorlekar.getSelectedItem()), color));
//        labelLagerstatus.setText("I lager: " + lagerstatus);
//        buttonAddToCart.setEnabled(lagerstatus > 0);
//        buttonAddToCart.setText("Lägg i kundvagn!");
//        this.revalidate();
//    }

    private void updateReviewTable() {
        JTable newTable;
        newTable = new JTable(r1.skomodellBetyg(activeButton.getText()), tableHeader);

        newTable.setDragEnabled(false);
        newTable.getColumnModel().getColumn(0).setMaxWidth(100);
        newTable.getColumnModel().getColumn(1).setMaxWidth(100);
        newTable.getColumnModel().getColumn(2).setMinWidth(300);
        newTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        scrollPane.setViewportView(newTable);
    }

}