package piccoapp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by mtiko_000 on 4/22/2015.
 */
public class Beer {
    //these are the categories beers are sorted into, the beer style may be different and more descriptive (American or English Pale Ale)
    final String INDIA_PALE_ALES = "India Pale Ales";
    final String PALE_ALES = "Pale Ales";
    final String OTHER_ALES = "Other Ales";
    final String LAGERS = "Lagers";
    final String BELGIAN_STYLE = "Belgian Style";
    final String DARK = "Dark";
    final String CIDER = "Cider";
    final String [] categories_Array = {INDIA_PALE_ALES, PALE_ALES, OTHER_ALES, LAGERS, BELGIAN_STYLE, DARK, CIDER};
    // draft beer sizes, used to sort when printing
    final String SIXTEEN_OZ = "16oz";
    final String TWELVE_OZ = "12oz";
    final String TEN_OZ = "10oz";
    final String EIGHT_OZ = "8oz";
    final String[] pourSize_Array = {SIXTEEN_OZ, TWELVE_OZ, TEN_OZ, EIGHT_OZ};
    //type of bottled beer, used for sorting
    final String TABLE_BEER = "Table Beer";
    final String BOTTLES_AND_CANS = "Bottles & Cans";
    final String[] bottleType_Array = {TABLE_BEER, BOTTLES_AND_CANS};
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


    public void createNewDraftBeer(final draftBeerList draftBeerList_Master, final draftBeerList draftBeerList_Current){

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

        JButton createNewBeer_Button = new JButton("Create Beer");

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
                draftBeerList_Master.addBeer(newBeer);
                if (addToCurrentListCheckBox.isSelected()) {
                    draftBeerList_Current.addBeer(newBeer);
                }
                frame.setVisible(false);

            }
        });

    }

    public void editBeer(Beer b, final draftBeerList draftBeerList_Master, final draftBeerList draftBeerList_Current){
        final Beer beer = b;

        System.out.println(beer);

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

        JButton save_Button = new JButton("Save");
        JButton delete_Button = new JButton("Delete");

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
                for (int i = 0; i < draftBeerList_Master.getSortedList().size(); i++){

                    if (beer.getName().equals(draftBeerList_Master.getSortedList().get(i).getName())){

                        draftBeerList_Master.removeBeer(draftBeerList_Master.getSortedList().get(i));
                        draftBeerList_Master.addBeer(newBeer);

                    }
                }

                for (int i = 0; i < draftBeerList_Current.getSortedList().size(); i++){

                    if (beer.getName().equals(draftBeerList_Current.getSortedList().get(i).getName())){

                        draftBeerList_Current.removeBeer(draftBeerList_Current.getSortedList().get(i));
                        draftBeerList_Current.addBeer(newBeer);

                    }
                }

                frame.setVisible(false);

            }
        });

        delete_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
            }
        });
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

