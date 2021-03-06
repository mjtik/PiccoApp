package piccoapp.menuItems;

import piccoapp.MenuChanger;
import piccoapp.customUI.customButton;
import piccoapp.draftAbvSorter;
import piccoapp.draftColumnSorter;
import piccoapp.lists.beerList;
import piccoapp.menuPrinter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by mtiko_000 on 4/22/2015.
 */
public class Beer {
    //these are the categories beers are sorted into, the beer style may be different and more descriptive (American or English Pale Ale)
    static final String INDIA_PALE_ALES = "India Pale Ales";
    static final String PALE_ALES = "Pale Ales";
    static final String OTHER_ALES = "Other Ales";
    static final String LAGERS = "Lagers";
    static final String BELGIAN_STYLE = "Belgian Style";
    static final String DARK = "Dark";
    static final String CIDER = "Cider";
    static final String[] categories_Array = {INDIA_PALE_ALES, PALE_ALES, OTHER_ALES, LAGERS, BELGIAN_STYLE, DARK, CIDER};
    // draft beer sizes, used to sort when printing
    static final String SIXTEEN_OZ = "16oz";
    static final String TWELVE_OZ = "12oz";
    static final String TEN_OZ = "10oz";
    static final String EIGHT_OZ = "8oz";
    static final String[] pourSize_Array = {SIXTEEN_OZ, TWELVE_OZ, TEN_OZ, EIGHT_OZ};
    //type of bottled beer, used for sorting
    static final String TABLE_BEER = "Table Beer";
    static final String BOTTLES_AND_CANS = "Bottles & Cans";
    static final String[] bottleType_Array = {TABLE_BEER, BOTTLES_AND_CANS};

    static String bottlesAndCansHTML;
    static String tableBeerHTML;
    final String WEBSITE_HTML_FOOTER_FILEPATH = MenuChanger.HOME_DIR + System.getProperty("file.separator") + "HTML" + System.getProperty("file.separator") + "website_htmlFooter.txt";
    final String WEBSITE_HTML_HEADER_FILEPATH = MenuChanger.HOME_DIR + System.getProperty("file.separator") + "HTML" + System.getProperty("file.separator") + "website_htmlHeader.txt";


    String price;
    String name;
    String style;
    String category;
    String abv;
    String brewery;
    String location;
    String size;
    String bottleType;

    public Beer() {

    }

    public void createNewDraftBeer(final beerList beerList_Master, final beerList beerList_Current) {

        final Beer newBeer = new Beer();

        final JTextField name = new JTextField(15);
        final JTextField style = new JTextField(15);
        final JTextField abv = new JTextField(15);
        final JTextField brewery = new JTextField(15);
        final JTextField location = new JTextField(15);
        final JTextField price = new JTextField(15);
        final JComboBox<String> categoryComboBox = new JComboBox<>(categories_Array);
        final JComboBox<String> pourSizeComboBox = new JComboBox<>(pourSize_Array);
        final JCheckBox addToCurrentListCheckBox = new JCheckBox("Add beer to current list");

        customButton createNewBeer_Button = new customButton("Create Beer");

        JPanel panel = new JPanel();
        GridBagLayout gbl = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        panel.setLayout(gbl);

        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(5,5,5,5);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.BOTH;

        panel.add(new JLabel("Name: "), gbc);
        panel.add(new JLabel("Style: "), gbc);
        panel.add(new JLabel("ABV: "), gbc);
        panel.add(new JLabel("Pour Size: "), gbc);
        panel.add(new JLabel("Brewery: "), gbc);
        panel.add(new JLabel("Location: "), gbc);
        panel.add(new JLabel("Price: "), gbc);
        panel.add(new JLabel("Category"), gbc);

        gbc.gridx = 1;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        panel.add(name,gbc);
        panel.add(style, gbc);
        panel.add(abv, gbc);
        panel.add(pourSizeComboBox, gbc);
        panel.add(brewery, gbc);
        panel.add(location, gbc);
        panel.add(price, gbc);
        panel.add(categoryComboBox,gbc);
        panel.add(addToCurrentListCheckBox, gbc);


        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(createNewBeer_Button, gbc);


        final JFrame frame = new JFrame("Create New Beer");
        frame.setSize(400, 500);
        frame.getContentPane().add(panel);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);

        createNewBeer_Button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                newBeer.setName(name.getText());
                newBeer.setStyle(style.getText());
                newBeer.setAbv(abv.getText());
                newBeer.setBrewery(brewery.getText());
                newBeer.setLocation(location.getText());
                newBeer.setPrice(price.getText());
                newBeer.setCategory(categoryComboBox.getSelectedItem().toString());
                newBeer.setSize(pourSizeComboBox.getSelectedItem().toString());
                beerList_Master.addBeer(newBeer);
                if (addToCurrentListCheckBox.isSelected()) {
                    beerList_Current.addBeer(newBeer);
                }
                frame.setVisible(false);

            }
        });

    }

    public void createNewBottledBeer(final beerList beerList_Master, final beerList beerList_Current) {

        final Beer newBeer = new Beer();

        final JTextField name = new JTextField(15);
        final JTextField style = new JTextField(15);
        final JTextField abv = new JTextField(15);
        final JTextField size = new JTextField(15);
        final JTextField brewery = new JTextField(15);
        final JTextField location = new JTextField(15);
        final JTextField price = new JTextField(15);
        final JComboBox<String> bottleTypeComboBox = new JComboBox<>(bottleType_Array);
        final JCheckBox addToCurrentListCheckBox = new JCheckBox("Add beer to current list?");

        customButton createNewBeer_Button = new customButton("Create Beer");

        JPanel panel = new JPanel();
        GridBagLayout gbl = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        panel.setLayout(gbl);

        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.BOTH;

        panel.add(new JLabel("Name: "), gbc);
        panel.add(new JLabel("Style: "), gbc);
        panel.add(new JLabel("ABV: "), gbc);
        panel.add(new JLabel("Size: "), gbc);
        panel.add(new JLabel("Bottle Type: "), gbc);
        panel.add(new JLabel("Brewery: "), gbc);
        panel.add(new JLabel("Location: "), gbc);
        panel.add(new JLabel("Price: "), gbc);

        gbc.gridx = 1;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        panel.add(name, gbc);
        panel.add(style, gbc);
        panel.add(abv, gbc);
        panel.add(size, gbc);
        panel.add(bottleTypeComboBox, gbc);
        panel.add(brewery, gbc);
        panel.add(location, gbc);
        panel.add(price, gbc);
        panel.add(addToCurrentListCheckBox, gbc);
        panel.add(createNewBeer_Button, gbc);

        final JFrame frame = new JFrame("Create New Beer");
        frame.setSize(400, 500);
        frame.getContentPane().add(panel);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);

        createNewBeer_Button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                newBeer.setName(name.getText());
                newBeer.setName(name.getText());
                newBeer.setStyle(style.getText());
                newBeer.setAbv(abv.getText());
                newBeer.setBrewery(brewery.getText());
                newBeer.setLocation(location.getText());
                newBeer.setPrice(price.getText());
                newBeer.setSize(size.getText());
                newBeer.setBottleType(bottleTypeComboBox.getSelectedItem().toString());
                if (addToCurrentListCheckBox.isSelected()) {
                    beerList_Current.addBeer(newBeer);
                }
                beerList_Master.addBeer(newBeer);
                frame.setVisible(false);

            }
        });

    }

    public void editDraftBeer(Beer b, final beerList beerList_Master, final beerList beerList_Current) {

        final Beer beer = b;

        final JTextField name = new JTextField(15);
        name.setText(beer.getName());
        final JTextField style = new JTextField(15);
        style.setText(beer.getStyle());
        final JTextField abv = new JTextField(15);
        abv.setText(beer.getAbvString());
        final JTextField brewery = new JTextField(15);
        brewery.setText(beer.getBrewery());
        final JTextField location = new JTextField(15);
        location.setText(beer.getLocation());
        final JTextField price = new JTextField(15);
        price.setText(beer.getPrice());
        final JComboBox<String> pourSizeComboBox = new JComboBox<>(pourSize_Array);
        final JComboBox<String> category = new JComboBox<>(categories_Array);

        customButton save_Button = new customButton("Save");
        customButton delete_Button = new customButton("Delete");

        switch (beer.getCategory()){
            case INDIA_PALE_ALES:
                category.setSelectedIndex(0);
                break;
            case PALE_ALES:
                category.setSelectedIndex(1);
                break;
            case OTHER_ALES:
                category.setSelectedIndex(2);
                break;
            case LAGERS:
                category.setSelectedIndex(3);
                break;
            case BELGIAN_STYLE:
                category.setSelectedIndex(4);
                break;
            case DARK:
                category.setSelectedIndex(5);
                break;
            case CIDER:
                category.setSelectedIndex(6);
                break;
        }


        JPanel panel = new JPanel();
        GridBagLayout gbl = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        panel.setLayout(gbl);

        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(5,20,5,5);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.BOTH;

        panel.add(new JLabel("Name: "), gbc);
        panel.add(new JLabel("Style: "), gbc);
        panel.add(new JLabel("ABV: "), gbc);
        panel.add(new JLabel("Pour Size: "), gbc);
        panel.add(new JLabel("Brewery: "), gbc);
        panel.add(new JLabel("Location: "), gbc);
        panel.add(new JLabel("Price: "), gbc);
        panel.add(new JLabel("Category"), gbc);

        gbc.gridx = 1;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets = new Insets(5,5,5,20);

        panel.add(name,gbc);
        panel.add(style, gbc);
        panel.add(abv, gbc);
        panel.add(pourSizeComboBox, gbc);
        panel.add(brewery, gbc);
        panel.add(location, gbc);
        panel.add(price, gbc);
        panel.add(category,gbc);

        gbc.insets = new Insets(5,5,5,5);
        gbc.gridx = 1;
        gbc.gridy = 10;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        panel.add(save_Button, gbc);

        gbc.gridx = 2;
        gbc.gridy = 10;
        gbc.insets = new Insets(5,5,5,20);
        panel.add(delete_Button, gbc);


        final JFrame frame = new JFrame("Edit Beer");
        frame.setSize(400, 500);
        frame.getContentPane().add(panel);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);

        save_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Beer newBeer = new Beer();

                newBeer.setName(name.getText());
                newBeer.setStyle(style.getText());
                newBeer.setAbv(abv.getText());
                newBeer.setSize(pourSizeComboBox.getSelectedItem().toString());
                newBeer.setBrewery(brewery.getText());
                newBeer.setLocation(location.getText());
                newBeer.setPrice(price.getText());
                newBeer.setCategory(category.getSelectedItem().toString());

                //search for previous versions of the beer in master and current list and remove them and add the new beer
                for (int i = 0; i < beerList_Master.getSortedList().size(); i++) {

                    if (beer.getName().equals(beerList_Master.getSortedList().get(i).getName())) {

                        beerList_Master.removeBeer(beerList_Master.getSortedList().get(i));
                        beerList_Master.addBeer(newBeer);

                    }
                }

                for (int i = 0; i < beerList_Current.getSortedList().size(); i++) {

                    if (beer.getName().equals(beerList_Current.getSortedList().get(i).getName())) {

                        beerList_Current.removeBeer(beerList_Current.getSortedList().get(i));
                        beerList_Current.addBeer(newBeer);

                    }
                }

                frame.setVisible(false);

            }
        });

        delete_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //confirm delete
                int response = JOptionPane.showConfirmDialog(null, "Do you want to delete " + beer.getName() + " from the master list? \r\n (you cannot undo)", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                switch (response) {
                    case JOptionPane.NO_OPTION:
                        break;

                    case JOptionPane.YES_OPTION:

                        //search for versions of the beer in master and current list and remove them
                        for (int i = 0; i < beerList_Master.getSortedList().size(); i++) {

                            if (beer.getName().equals(beerList_Master.getSortedList().get(i).getName())) {

                                beerList_Master.removeBeer(beerList_Master.getSortedList().get(i));

                            }
                        }

                        for (int i = 0; i < beerList_Current.getSortedList().size(); i++) {

                            if (beer.getName().equals(beerList_Current.getSortedList().get(i).getName())) {

                                beerList_Current.removeBeer(beerList_Current.getSortedList().get(i));

                            }
                        }

                        break;

                }




                frame.setVisible(false);
            }
        });
    }

    public void editBottledBeer(Beer b, final beerList beerList_Master, final beerList beerList_Current) {

        final Beer beer = b;

        final JTextField name = new JTextField(15);
        name.setText(beer.getName());
        final JTextField style = new JTextField(15);
        style.setText(beer.getStyle());
        final JTextField abv = new JTextField(15);
        abv.setText(beer.getAbvString());
        final JTextField size = new JTextField(15);
        size.setText(beer.getSize());
        final JTextField brewery = new JTextField(15);
        brewery.setText(beer.getBrewery());
        final JTextField location = new JTextField(15);
        location.setText(beer.getLocation());
        final JTextField price = new JTextField(15);
        price.setText(beer.getPrice());
        final JComboBox<String> bottleTypeComboBox = new JComboBox<String>(bottleType_Array);

        customButton save_Button = new customButton("Save");
        customButton delete_Button = new customButton("Delete");

        switch (beer.getBottleType()) {
            case TABLE_BEER:
                bottleTypeComboBox.setSelectedIndex(0);
                break;
            case BOTTLES_AND_CANS:
                bottleTypeComboBox.setSelectedIndex(1);
                break;
        }


        JPanel panel = new JPanel();
        GridBagLayout gbl = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        panel.setLayout(gbl);

        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(5, 20, 5, 5);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.BOTH;

        panel.add(new JLabel("Name: "), gbc);
        panel.add(new JLabel("Style: "), gbc);
        panel.add(new JLabel("ABV: "), gbc);
        panel.add(new JLabel("Size: "), gbc);
        panel.add(new JLabel("Bottle Type: "), gbc);
        panel.add(new JLabel("Brewery: "), gbc);
        panel.add(new JLabel("Location: "), gbc);
        panel.add(new JLabel("Price: "), gbc);

        gbc.gridx = 1;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets = new Insets(5, 5, 5, 20);

        panel.add(name, gbc);
        panel.add(style, gbc);
        panel.add(abv, gbc);
        panel.add(size, gbc);
        panel.add(brewery, gbc);
        panel.add(location, gbc);
        panel.add(price, gbc);
        panel.add(bottleTypeComboBox, gbc);

        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 1;
        gbc.gridy = 10;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        panel.add(save_Button, gbc);

        gbc.gridx = 2;
        gbc.gridy = 10;
        gbc.insets = new Insets(5, 5, 5, 20);
        panel.add(delete_Button, gbc);

        final JFrame frame = new JFrame("Edit Beer");
        frame.setSize(400, 500);
        frame.getContentPane().add(panel);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);

        save_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Beer newBeer = new Beer();

                newBeer.setName(name.getText());
                newBeer.setStyle(style.getText());
                newBeer.setAbv(abv.getText());
                newBeer.setSize(size.getText());
                newBeer.setBrewery(brewery.getText());
                newBeer.setLocation(location.getText());
                newBeer.setPrice(price.getText());
                newBeer.setBottleType(bottleTypeComboBox.getSelectedItem().toString());

                //search for previous versions of the beer in master and current list and remove them and add the new beer
                for (int i = 0; i < beerList_Master.getSortedList().size(); i++) {

                    if (beer.getName().equals(beerList_Master.getSortedList().get(i).getName())) {

                        beerList_Master.removeBeer(beerList_Master.getSortedList().get(i));
                        beerList_Master.addBeer(newBeer);

                    }
                }

                for (int i = 0; i < beerList_Current.getSortedList().size(); i++) {

                    if (beer.getName().equals(beerList_Current.getSortedList().get(i).getName())) {

                        beerList_Current.removeBeer(beerList_Current.getSortedList().get(i));
                        beerList_Current.addBeer(newBeer);

                    }
                }

                frame.setVisible(false);

            }
        });

        delete_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //confirm delete
                int response = JOptionPane.showConfirmDialog(null, "Do you want to delete " + beer.getName() + " from the master list? \r\n (you cannot undo)", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                switch (response) {
                    case JOptionPane.NO_OPTION:
                        break;

                    case JOptionPane.YES_OPTION:

                        //search for versions of the beer in master and current list and remove them
                        for (int i = 0; i < beerList_Master.getSortedList().size(); i++) {

                            if (beer.getName().equals(beerList_Master.getSortedList().get(i).getName())) {

                                beerList_Master.removeBeer(beerList_Master.getSortedList().get(i));

                            }
                        }

                        for (int i = 0; i < beerList_Current.getSortedList().size(); i++) {

                            if (beer.getName().equals(beerList_Current.getSortedList().get(i).getName())) {

                                beerList_Current.removeBeer(beerList_Current.getSortedList().get(i));

                            }
                        }

                        break;

                }


                frame.setVisible(false);
            }
        });
    }

    public void printDraftBeer(Beer beer, StringBuilder stringBuilder, String beerCategory) {

        if (stringBuilder.length() == 0) {
            stringBuilder.append("<p class=\"beerStyleHeader\">" + beerCategory + "</p>");
        }

        stringBuilder.append("<p class=\"beerName\">" + beer.getName());
// deal with adding pour size next to beer name is NOT 16oz
        if (beer.getSize().equals(SIXTEEN_OZ)) {
            stringBuilder.append("</p>");
        } else {
            stringBuilder.append("<span style=\"font-weight:normal;\"> (" + beer.getSize() + ")</span>" + "</p>");
        }

        stringBuilder.append("<p1 class=\"beerStyle\">" + beer.getStyle() + "</p1>");
        stringBuilder.append("<p class=\"abv\">" + beer.getAbvString() + "%</p>");
        stringBuilder.append("<p1 class=\"brewery\">" + beer.getBrewery() + "</p1>");
        stringBuilder.append("<p class=\"location\">" + beer.getLocation() + "</p>");
        stringBuilder.append("<p class=\"price\">" + beer.getPrice() + "</p>");

    }

    public void printBottledBeer(Beer beer, StringBuilder stringBuilder, String bottleCategory) {

        if (stringBuilder.length() == 0) {
            stringBuilder.append("<div class=\"bottleCategoryHeader\">" + bottleCategory + "</div>");

        }
        stringBuilder.append("<div class=\"bottleWrapper\">");
        stringBuilder.append("<div class=\"beerName\">" + beer.getName() + "<span style=\"font-weight:normal;\"> (" + beer.getSize() + "oz)</span>" + "</div>");
        stringBuilder.append("<div class=\"beerStyle\">" + beer.getStyle() + "</div>");
        stringBuilder.append("<div class=\"abv\">" + beer.getAbvString() + "%</div>");
        stringBuilder.append("<div class=\"brewery\">" + beer.getBrewery() + "</div>");
        stringBuilder.append("<div class=\"location\">" + beer.getLocation() + "</div>");
        stringBuilder.append("<div class=\"price\">" + beer.getPrice() + "</div>");
        stringBuilder.append("</div>");

    }


    public void printBeerList(beerList draftBeerList_Current, beerList bottledBeerList_Current) {

        final String BOTTLED_LIST_HEADER = "<div class=\"bottleBox\">";
        final String BOTTLED_LIST_FOOTER = "</div>";

        String indiaPaleAlesHTML;
        String paleAlesHTML;
        String otherAlesHTML;
        String lagersHTML;
        String belgianStyleHTML;
        String darkHTML;
        String ciderHTML;

        String website_headerHTML;
        String website_footerHTML;

        StringBuilder indiaPaleAlesBuilder = new StringBuilder();
        StringBuilder paleAlesBuilder = new StringBuilder();
        StringBuilder otherAlesBuilder = new StringBuilder();
        StringBuilder lagersBuilder = new StringBuilder();
        StringBuilder belgianStyleBuilder = new StringBuilder();
        StringBuilder darkBuilder = new StringBuilder();
        StringBuilder ciderBuilder = new StringBuilder();

        StringBuilder bottlesAndCansStringBuilder = new StringBuilder();
        StringBuilder tableBeerStringBuilder = new StringBuilder();

        List<Beer> indiaPaleAles_ArrayList = new ArrayList();
        List<Beer> paleAles_ArrayList = new ArrayList();
        List<Beer> otherAles_ArrayList = new ArrayList();
        List<Beer> lagers_ArrayList = new ArrayList();
        List<Beer> belgianStyle_ArrayList = new ArrayList();
        List<Beer> dark_ArrayList = new ArrayList();
        List<Beer> cider_ArrayList = new ArrayList();
        List<java.util.List> allCategories_ArrayList = new ArrayList();

        allCategories_ArrayList.add(indiaPaleAles_ArrayList);
        allCategories_ArrayList.add(paleAles_ArrayList);
        allCategories_ArrayList.add(otherAles_ArrayList);
        allCategories_ArrayList.add(lagers_ArrayList);
        allCategories_ArrayList.add(belgianStyle_ArrayList);
        allCategories_ArrayList.add(dark_ArrayList);
        allCategories_ArrayList.add(cider_ArrayList);

        //Bottled list
        for (int i = 0; i <= bottledBeerList_Current.getSortedList().size() - 1; i++) {

            Beer beer = bottledBeerList_Current.getSortedList().get(i);
            System.out.println(beer);

            switch (beer.getBottleType()) {
                case BOTTLES_AND_CANS:
                    printBottledBeer(beer, bottlesAndCansStringBuilder, BOTTLES_AND_CANS);
                    break;
                case TABLE_BEER:
                    printBottledBeer(beer, tableBeerStringBuilder, TABLE_BEER);
                    break;
            }


        }

        bottlesAndCansHTML = bottlesAndCansStringBuilder.toString();
        tableBeerHTML = tableBeerStringBuilder.toString();

        StringBuilder bottledBeerList_StringBuilder = new StringBuilder();
        bottledBeerList_StringBuilder.append(BOTTLED_LIST_HEADER);
        bottledBeerList_StringBuilder.append(bottlesAndCansHTML);
        bottledBeerList_StringBuilder.append(tableBeerHTML);
        bottledBeerList_StringBuilder.append(BOTTLED_LIST_FOOTER);

        String bottledBeerListHTML = bottledBeerList_StringBuilder.toString();

        //Draft List
        //sort into categories and arrays
        for (int i = 0; i <= draftBeerList_Current.getSortedList().size() - 1; i++) {

            Beer beer = draftBeerList_Current.getSortedList().get(i);

            switch (beer.getCategory()) {
                case INDIA_PALE_ALES:
                    indiaPaleAles_ArrayList.add(beer);
                    break;

                case PALE_ALES:
                    paleAles_ArrayList.add(beer);
                    break;

                case OTHER_ALES:
                    otherAles_ArrayList.add(beer);
                    break;

                case LAGERS:
                    lagers_ArrayList.add(beer);
                    break;

                case BELGIAN_STYLE:
                    belgianStyle_ArrayList.add(beer);
                    break;

                case DARK:
                    dark_ArrayList.add(beer);
                    break;

                case CIDER:
                    cider_ArrayList.add(beer);
                    break;

            }

        }

        // sort each category by abv
        for (List l : allCategories_ArrayList) {

            Collections.sort(l, new draftAbvSorter());

        }

        //print all beers in each category
        for (Beer b : indiaPaleAles_ArrayList) {
            printDraftBeer(b, indiaPaleAlesBuilder, INDIA_PALE_ALES);
        }

        for (Beer b : paleAles_ArrayList) {
            printDraftBeer(b, paleAlesBuilder, PALE_ALES);
        }

        for (Beer b : otherAles_ArrayList) {
            printDraftBeer(b, otherAlesBuilder, OTHER_ALES);
        }

        for (Beer b : lagers_ArrayList) {
            printDraftBeer(b, lagersBuilder, LAGERS);
        }

        for (Beer b : belgianStyle_ArrayList) {
            printDraftBeer(b, belgianStyleBuilder, BELGIAN_STYLE);
        }

        for (Beer b : dark_ArrayList) {
            printDraftBeer(b, darkBuilder, DARK);
        }

        for (Beer b : cider_ArrayList) {
            printDraftBeer(b, ciderBuilder, CIDER);
        }


        indiaPaleAlesHTML = indiaPaleAlesBuilder.toString();
        paleAlesHTML = paleAlesBuilder.toString();
        otherAlesHTML = otherAlesBuilder.toString();
        lagersHTML = lagersBuilder.toString();
        belgianStyleHTML = belgianStyleBuilder.toString();
        darkHTML = darkBuilder.toString();
        ciderHTML = ciderBuilder.toString();

        /// sort beer categories by size
        List<String> draftBeerArray = new ArrayList();
        List<String> leftColumnArray = new ArrayList();
        List<String> rightColumnArray = new ArrayList();


        draftBeerArray.add(paleAlesHTML);
        draftBeerArray.add(otherAlesHTML);
        draftBeerArray.add(lagersHTML);
        draftBeerArray.add(belgianStyleHTML);
        draftBeerArray.add(darkHTML);
        draftBeerArray.add(ciderHTML);
        draftBeerArray.add(indiaPaleAlesHTML);

        //sorts each category by how many beers it has
        Collections.sort(draftBeerArray, new draftColumnSorter());

        int leftColumnSize = 0;
        int rightColumnSize = 0;

        //sorts categories into left and right columns by size, so page doesn't overflow
        for (int i = 0; i < draftBeerArray.size(); i++) {

            if (leftColumnSize > rightColumnSize) {
                rightColumnArray.add((draftBeerArray.get(i)));
                rightColumnSize += draftBeerArray.get(i).length();

            } else {
                leftColumnArray.add(draftBeerArray.get(i));
                leftColumnSize += draftBeerArray.get(i).length();

            }

        }

        //build string containing draft beer list. Can be used in any menu or the website
        StringBuilder draftBeer_StringBuilder = new StringBuilder();

        // header text for draft list "Draft Beer: All draft beers are served in 16oz glasses unless noted otherwise
        draftBeer_StringBuilder.append("<div id=\"draftHeader\">Draft Beer</div>\n" +
                "    <div id=\"draftDescription\">All draft beers are served in 16oz glasses unless noted otherwise</div>");
        draftBeer_StringBuilder.append("<div class=\"leftSide\">");
        for (String s : leftColumnArray) {
            draftBeer_StringBuilder.append(s);
        }
        draftBeer_StringBuilder.append("</div>");

        draftBeer_StringBuilder.append("<div class=\"rightSide\">");
        for (String s : rightColumnArray) {
            draftBeer_StringBuilder.append(s);
        }

        draftBeer_StringBuilder.append("</div>");

        String draftBeerListHTML = draftBeer_StringBuilder.toString();

        //write in house beer list
        new menuPrinter().writeBeerList(bottledBeerListHTML, draftBeerListHTML);
        //write window draft list
        new menuPrinter().writeWindowBeerList(draftBeerListHTML);
        //write website draft list
        new menuPrinter().writeWebBeerList(bottledBeerListHTML, draftBeerListHTML);


    }


    ///////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////
    // mutators ///////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////
    public String getBottleType() {
        return bottleType;
    }

    public void setBottleType(String bottleType) {
        this.bottleType = bottleType;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getPrice() {
        return this.price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStyle() {
        return this.style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAbvString() {
        return this.abv;
    }

    public void setAbv(String abv) {
        this.abv = abv;
    }

    public String getBrewery() {
        return this.brewery;
    }

    public void setBrewery(String brewery) {
        this.brewery = brewery;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String toString() {
        return this.getName();
    }

    public Double getAbvDouble() {
        return Double.parseDouble(this.abv);
    }

}
/**
 * Created by mtiko_000 on 4/22/2015.
 */

