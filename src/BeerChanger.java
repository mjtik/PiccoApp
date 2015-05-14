/**
 * Created by mtiko_000 on 4/27/2015.
 */

import ca.odell.glazedlists.FilterList;
import ca.odell.glazedlists.SortedList;
import ca.odell.glazedlists.swing.AdvancedTableModel;
import ca.odell.glazedlists.swing.GlazedListsSwing;
import ca.odell.glazedlists.swing.TextComponentMatcherEditor;
import org.apache.commons.io.FileUtils;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class BeerChanger {


    //these are the categorys beers are sorted into, the beer style may be different and more descriptive (American or English Pale Ale)
    final String INDIA_PALE_ALES = "India Pale Ales";
    final String PALE_ALES = "Pale Ales";
    final String OTHER_ALES = "Other Ales";
    final String LAGERS = "Lagers";
    final String BELGIAN_STYLE = "Belgian Style";
    final String DARK = "Dark";
    final String CIDER = "Cider";


    final String SIXTEEN_OZ = "16oz";
    final String TWELVE_OZ = "12oz";
    final String TEN_OZ = "10oz";
    final String EIGHT_OZ = "8oz";
    final String DRAFT_BEER_MASTER_LIST_XML = "draftBeerMasterList.xml";
    final String CURRENT_DRAFT_BEER_LIST_XML = "currentDraftBeerList.xml";
    final String BOTTLED_BEER_MASTER_LIST_XML = "bottledBeerMasterList.xml";
    final String CURRENT_BOTTLED_BEER_LIST_XML = "currentBottledBeerList.xml";

    final String BEER_LIST_HTML_HEADER = "HTMLheader.txt";
    final String BEER_LIST_HTML_FOOTER = "HTMLfooter.txt";
    String [] categories = {INDIA_PALE_ALES, PALE_ALES, OTHER_ALES, LAGERS, BELGIAN_STYLE, DARK, CIDER};
    String[] pourSize = {SIXTEEN_OZ, TWELVE_OZ, TEN_OZ, EIGHT_OZ};
    SortedList<Beer> draftBeerMasterSortedList = new BeerXMLParser().parseXML(DRAFT_BEER_MASTER_LIST_XML);
    SortedList<Beer> currentDraftBeerSortedList = new BeerXMLParser().parseXML(CURRENT_DRAFT_BEER_LIST_XML);

    SortedList<Beer> bottledBeerMasterSortedList = new BeerXMLParser().parseXML(BOTTLED_BEER_MASTER_LIST_XML);
    SortedList<Beer> currentBottledBeerSortedList = new BeerXMLParser().parseXML(CURRENT_BOTTLED_BEER_LIST_XML);

    Beer selectedBeer = new Beer();


    public BeerChanger() {

        displayDraftBeerChanger();
    }

    public void displayBottledBeerChanger() {

        //Left table for BeerMasterList
        JTextField bottledBeerMasterListFilterEdit = new JTextField(10);
        FilterList<Beer> bottledBeerMasterListTextFilteredIssues = new FilterList<>(bottledBeerMasterSortedList, new TextComponentMatcherEditor<>(bottledBeerMasterListFilterEdit, new BeerTextFilter()));
        AdvancedTableModel<Beer> bottledBeerMasterListTableModel = GlazedListsSwing.eventTableModelWithThreadProxyList(bottledBeerMasterListTextFilteredIssues, new SimpleBeerTableFormat());
        final JTable bottledBeerMasterListJTable = new JTable(bottledBeerMasterListTableModel);

        //Right table for CurrentBeerList
        JTextField currentBottledBeerListFilterEdit = new JTextField(10);
        FilterList<Beer> currentBottledBeerListTextFilteredIssues = new FilterList<>(currentBottledBeerSortedList, new TextComponentMatcherEditor<>(currentBottledBeerListFilterEdit, new BeerTextFilter()));
        AdvancedTableModel<Beer> currentBottledBeerListTableModel = GlazedListsSwing.eventTableModelWithThreadProxyList(currentBottledBeerListTextFilteredIssues, new SimpleBeerTableFormat());
        final JTable currentBottledBeerListJTable = new JTable(currentBottledBeerListTableModel);


        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        JScrollPane beerMasterListScrollPane = new JScrollPane(bottledBeerMasterListJTable);
        JScrollPane currentBeerListScrollPane = new JScrollPane(currentBottledBeerListJTable);

        JButton createNewBeerButton = new JButton("Create New Bottled Beer");
        final JButton addBottledBeerToCurrentList = new JButton("Add To Bottled List");
        final JButton removeBottledBeerFromCurrentList = new JButton("86 From Bottled List");
        final JButton printList = new JButton("Print Beer List");
        final JButton editBottledBeer = new JButton("Edit Bottled Beer");
        final JButton editDraftBeerList = new JButton("Edit Draft Beer List");

        panel.add(new JLabel("All Beer:"), new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        panel.add(new JLabel("Current List: "), new GridBagConstraints(2, 0, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));

        panel.add(new JLabel("Filter: "), new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        panel.add(bottledBeerMasterListFilterEdit, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        panel.add(new JLabel("Filter: "), new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        panel.add(currentBottledBeerListFilterEdit, new GridBagConstraints(3, 1, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));

        panel.add(addBottledBeerToCurrentList, new GridBagConstraints(0, 2, 2, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        panel.add(removeBottledBeerFromCurrentList, new GridBagConstraints(2, 2, 2, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));

        panel.add(beerMasterListScrollPane, new GridBagConstraints(0, 3, 2, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        panel.add(currentBeerListScrollPane, new GridBagConstraints(2, 3, 2, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));

        panel.add(createNewBeerButton, new GridBagConstraints(0, 4, 2, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        panel.add(editDraftBeerList, new GridBagConstraints(2, 4, 2, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        panel.add(editBottledBeer, new GridBagConstraints(0, 5, 2, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        panel.add(printList, new GridBagConstraints(2, 5, 2, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));


        createNewBeerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createNewBottledBeer();
            }
        });

        addBottledBeerToCurrentList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedBeer = bottledBeerMasterSortedList.get(bottledBeerMasterListJTable.getSelectedRow());
                addBeerToAList(selectedBeer, CURRENT_BOTTLED_BEER_LIST_XML, currentBottledBeerSortedList);

            }
        });

        removeBottledBeerFromCurrentList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedBeer = currentBottledBeerSortedList.get(currentBottledBeerListJTable.getSelectedRow());
                removeBeerFromAList(selectedBeer, CURRENT_BOTTLED_BEER_LIST_XML, currentBottledBeerSortedList);

            }
        });

        printList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                printList();
            }
        });

        editBottledBeer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                editDraftBeer(draftBeerMasterSortedList.get(bottledBeerMasterListJTable.getSelectedRow()));
                draftBeerMasterSortedList.remove(bottledBeerMasterListJTable.getSelectedRow());
            }
        });

        editDraftBeerList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayDraftBeerChanger();
            }
        });


        JFrame frame = new JFrame("Beer Menu Changer");
        frame.setSize(540, 380);
        frame.getContentPane().add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


    }

    public void displayDraftBeerChanger() {

        //Left table for BeerMasterList
        JTextField beerMasterListFilterEdit = new JTextField(10);
        FilterList<Beer> beerMasterListTextFilteredIssues = new FilterList<>(draftBeerMasterSortedList, new TextComponentMatcherEditor<>(beerMasterListFilterEdit, new BeerTextFilter()));
        AdvancedTableModel<Beer> beerMasterListTableModel = GlazedListsSwing.eventTableModelWithThreadProxyList(beerMasterListTextFilteredIssues, new SimpleBeerTableFormat());
        final JTable beerMasterListJTable = new JTable(beerMasterListTableModel);

        //Right table for CurrentBeerList
        JTextField currentBeerListFilterEdit = new JTextField(10);
        FilterList<Beer> currentBeerListTextFilteredIssues = new FilterList<>(currentDraftBeerSortedList, new TextComponentMatcherEditor<>(currentBeerListFilterEdit, new BeerTextFilter()));
        AdvancedTableModel<Beer> currentBeerListTableModel = GlazedListsSwing.eventTableModelWithThreadProxyList(currentBeerListTextFilteredIssues, new SimpleBeerTableFormat());
        final JTable currentDraftBeerListJTable = new JTable(currentBeerListTableModel);


        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        JScrollPane beerMasterListScrollPane = new JScrollPane(beerMasterListJTable);
        JScrollPane currentBeerListScrollPane = new JScrollPane(currentDraftBeerListJTable);

        JButton createNewDraftBeerButton = new JButton("Create New Draft Beer");
        final JButton addDraftBeerToCurrentList = new JButton("Add To Draft List");
        final JButton removeDraftBeerFromCurrentList = new JButton("86 From Draft List");
        final JButton printList = new JButton("Print Beer List");
        final JButton editDraftBeer = new JButton("Edit Draft Beer");
        final JButton editBottledBeerList = new JButton("Edit Bottled Beer List");

        panel.add(new JLabel("All Beer:"),      new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        panel.add(new JLabel("Current List: "), new GridBagConstraints(2, 0, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));

        panel.add(new JLabel("Filter: "),       new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        panel.add(beerMasterListFilterEdit,     new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        panel.add(new JLabel("Filter: "),       new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        panel.add(currentBeerListFilterEdit,    new GridBagConstraints(3, 1, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));

        panel.add(addDraftBeerToCurrentList, new GridBagConstraints(0, 2, 2, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        panel.add(removeDraftBeerFromCurrentList, new GridBagConstraints(2, 2, 2, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));

        panel.add(beerMasterListScrollPane, new GridBagConstraints(0, 3, 2, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        panel.add(currentBeerListScrollPane, new GridBagConstraints(2, 3, 2, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));

        panel.add(createNewDraftBeerButton, new GridBagConstraints(0, 4, 2, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        panel.add(editBottledBeerList, new GridBagConstraints(2, 4, 2, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        panel.add(editDraftBeer, new GridBagConstraints(0, 5, 2, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        panel.add(printList, new GridBagConstraints(2, 5, 2, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));


        createNewDraftBeerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createNewDraftBeer();
            }
        });

        addDraftBeerToCurrentList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedBeer = draftBeerMasterSortedList.get(beerMasterListJTable.getSelectedRow());
                addBeerToAList(selectedBeer, CURRENT_DRAFT_BEER_LIST_XML, currentDraftBeerSortedList);
            }
        });

        removeDraftBeerFromCurrentList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedBeer = currentDraftBeerSortedList.get(currentDraftBeerListJTable.getSelectedRow());
                removeBeerFromAList(selectedBeer, CURRENT_DRAFT_BEER_LIST_XML, currentDraftBeerSortedList);

            }
        });

        printList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                printList();
            }
        });

        editDraftBeer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedBeer = draftBeerMasterSortedList.get(beerMasterListJTable.getSelectedRow());
                editDraftBeer(selectedBeer);
                draftBeerMasterSortedList.remove(beerMasterListJTable.getSelectedRow());
            }
        });

        editBottledBeerList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayBottledBeerChanger();
            }
        });


        JFrame frame = new JFrame("Beer Menu Changer");
        frame.setSize(540, 380);
        frame.getContentPane().add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);




    }

    void printBeer(Beer beer, StringBuilder stringBuilder, String beerCategory) {

        if (stringBuilder.length() == 0) {
            stringBuilder.append("<p class=\"beerStyleHeader\">" + beerCategory + "</p>");
        }

        stringBuilder.append("<p class=\"beerName\">" + beer.getName() + "</p>");
        // deal with adding pour size next to beer name is NOT 16oz
        if (beer.getPourSize().equals(SIXTEEN_OZ)) {
            stringBuilder.append("</p>");
        } else {
            stringBuilder.append(" " + "(" + beer.getPourSize() + ")" + "</p>");
        }

        stringBuilder.append("<p1 class=\"beerStyle\">" + beer.getStyle() + "</p1>");
        stringBuilder.append("<p class=\"abv\">" + beer.getAbv() + "%</p>");
        stringBuilder.append("<p1 class=\"brewery\">" + beer.getBrewery() + "</p1>");
        stringBuilder.append("<p class=\"location\">" + beer.getLocation() + "</p>");
        stringBuilder.append("<p class=\"price\">$" + beer.getPrice() + "</p>");

    }

    void printList(){

        String indiaPaleAlesHTML;
        String paleAlesHTML;
        String otherAlesHTML;
        String lagersHTML;
        String belgianStyleHTML;
        String darkHTML;
        String ciderHTML;

        String headerHTML;
        String footerHTML;


        StringBuilder indiaPaleAlesBuilder = new StringBuilder();
        StringBuilder paleAlesBuilder = new StringBuilder();
        StringBuilder otherAlesBuilder = new StringBuilder();
        StringBuilder lagersBuilder = new StringBuilder();
        StringBuilder belgianStyleBuilder = new StringBuilder();
        StringBuilder darkBuilder = new StringBuilder();
        StringBuilder ciderBuilder = new StringBuilder();


        for (int i = 0; i <= currentDraftBeerSortedList.size() - 1; i++) {

            Beer beer = currentDraftBeerSortedList.get(i);


            switch (beer.getCategory()){
                case INDIA_PALE_ALES:
                    printBeer(beer, indiaPaleAlesBuilder, INDIA_PALE_ALES);
                    break;

                case PALE_ALES:
                    printBeer(beer, paleAlesBuilder, PALE_ALES);
                    break;

                case OTHER_ALES:
                    printBeer(beer, otherAlesBuilder, OTHER_ALES);
                    break;

                case LAGERS:
                    printBeer(beer, lagersBuilder, LAGERS);
                    break;

                case BELGIAN_STYLE:
                    printBeer(beer, belgianStyleBuilder, BELGIAN_STYLE);
                    break;

                case DARK:
                    printBeer(beer, darkBuilder, DARK);
                    break;

                case CIDER:
                    printBeer(beer, ciderBuilder, CIDER);
                    break;

            }

        }

        indiaPaleAlesHTML = indiaPaleAlesBuilder.toString();
        paleAlesHTML = paleAlesBuilder.toString();
        otherAlesHTML = otherAlesBuilder.toString();
        lagersHTML = lagersBuilder.toString();
        belgianStyleHTML = belgianStyleBuilder.toString();
        darkHTML = darkBuilder.toString();
        ciderHTML = ciderBuilder.toString();


        File footerFile = new File(BEER_LIST_HTML_FOOTER);
        File headerFile = new File(BEER_LIST_HTML_HEADER);
        File file = new File("html\\beerList.html");


        try {
            headerHTML = FileUtils.readFileToString(headerFile);
            footerHTML = FileUtils.readFileToString(footerFile);
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            bufferedWriter.write(headerHTML);
            bufferedWriter.write(indiaPaleAlesHTML);
            bufferedWriter.write(paleAlesHTML);
            bufferedWriter.write(otherAlesHTML);
            bufferedWriter.write(lagersHTML);
            bufferedWriter.write(belgianStyleHTML);
            bufferedWriter.write(darkHTML);
            bufferedWriter.write(ciderHTML);
            bufferedWriter.write(footerHTML);
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    void createNewDraftBeer() {

        final Beer newBeer = new Beer();

        final JTextField name = new JTextField(15);
        final JTextField style = new JTextField(15);
        final JTextField abv = new JTextField(15);
        final JTextField brewery = new JTextField(15);
        final JTextField location = new JTextField(15);
        final JTextField price = new JTextField(15);
        final JComboBox<String> categoryComboBox = new JComboBox<>(categories);
        final JComboBox<String> pourSizeComboBox = new JComboBox<>(pourSize);
        final JCheckBox addToCurrentListCheckBox = new JCheckBox("Add beer to current list?");

        JButton createNewBeerButton = new JButton("Create Beer");

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        panel.add(new JLabel("Name: "), new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        panel.add(name, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        panel.add(new JLabel("Style: "), new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        panel.add(style, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        panel.add(new JLabel("ABV: "), new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        panel.add(abv, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        panel.add(new JLabel("Pour Size: "), new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        panel.add(pourSizeComboBox, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        panel.add(new JLabel("Brewery: "), new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        panel.add(brewery, new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        panel.add(new JLabel("Location: "), new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        panel.add(location, new GridBagConstraints(1, 6, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        panel.add(new JLabel("Price: "), new GridBagConstraints(0, 7, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        panel.add(price, new GridBagConstraints(1, 7, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        panel.add(new JLabel("Category: "), new GridBagConstraints(0, 8, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        panel.add(categoryComboBox, new GridBagConstraints(1, 8, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        panel.add(addToCurrentListCheckBox, new GridBagConstraints(1, 9, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        panel.add(createNewBeerButton, new GridBagConstraints(0, 10, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));

        final JFrame frame = new JFrame("Create New Beer");
        frame.setSize(300, 375);
        frame.getContentPane().add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        createNewBeerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newBeer.setName(name.getText());
                newBeer.setStyle(style.getText());
                newBeer.setAbv(abv.getText());
                newBeer.setBrewery(brewery.getText());
                newBeer.setLocation(location.getText());
                newBeer.setPrice(price.getText());
                newBeer.setCategory(categoryComboBox.getSelectedItem().toString());
                newBeer.setPourSize(pourSizeComboBox.getSelectedItem().toString());
                addBeerToAList(newBeer, DRAFT_BEER_MASTER_LIST_XML, draftBeerMasterSortedList);
                if (addToCurrentListCheckBox.isSelected()) {
                    addBeerToAList(newBeer, CURRENT_DRAFT_BEER_LIST_XML, currentDraftBeerSortedList);
                }
                frame.setVisible(false);

            }
        });


    }

    //NOT READY!!
    void createNewBottledBeer() {

        final Beer newBeer = new Beer();

        final JTextField name = new JTextField(15);
        final JTextField style = new JTextField(15);
        final JTextField abv = new JTextField(15);
        final JTextField brewery = new JTextField(15);
        final JTextField location = new JTextField(15);
        final JTextField price = new JTextField(15);

        JButton createNewBeerButton = new JButton("Create Beer");

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        panel.add(new JLabel("Name: "), new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        panel.add(name, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        panel.add(new JLabel("Style: "), new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        panel.add(style, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        panel.add(new JLabel("ABV: "), new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        panel.add(abv, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        panel.add(new JLabel("Brewery: "), new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        panel.add(brewery, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        panel.add(new JLabel("Location: "), new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        panel.add(location, new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        panel.add(new JLabel("Price: "), new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        panel.add(price, new GridBagConstraints(1, 6, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));

        panel.add(createNewBeerButton, new GridBagConstraints(0, 7, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));

        final JFrame frame = new JFrame("Create New Beer");
        frame.setSize(300, 300);
        frame.getContentPane().add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        createNewBeerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newBeer.setName(name.getText());
                newBeer.setStyle(style.getText());
                newBeer.setAbv(abv.getText());
                newBeer.setBrewery(brewery.getText());
                newBeer.setLocation(location.getText());
                newBeer.setPrice(price.getText());
                addBeerToAList(newBeer, BOTTLED_BEER_MASTER_LIST_XML, bottledBeerMasterSortedList);
                frame.setVisible(false);

            }
        });


    }

    void addBeerToAList(Beer newBeer, String fileName, SortedList<Beer> sortedList) {

        sortedList.add(newBeer);
        XMLOutputter out = new XMLOutputter();

        try {
            Document doc = new SAXBuilder().build(fileName);

            Element beer = new Element("beer");
            beer.addContent(new Element("name").setText(newBeer.getName()));
            beer.addContent(new Element("style").setText(newBeer.getStyle()));
            beer.addContent(new Element("abv").setText(newBeer.getAbv()));
            beer.addContent(new Element("brewery").setText(newBeer.getBrewery()));
            beer.addContent(new Element("location").setText(newBeer.getLocation()));
            beer.addContent(new Element("price").setText(newBeer.getPrice()));
            beer.addContent(new Element("category").setText(newBeer.getCategory()));

            doc.getRootElement().addContent(beer);

            FileWriter writer = new FileWriter(fileName);
            out.setFormat(Format.getPrettyFormat());
            out.output(doc, writer);
            writer.close();

        } catch (JDOMException | IOException e) {
            e.printStackTrace();
        }

    }

    void editDraftBeer(Beer beer) {

        final Beer editedBeer = new Beer();

        final JTextField name = new JTextField(15);
        name.setText(beer.getName());
        final JTextField style = new JTextField(15);
        style.setText(beer.getStyle());
        final JTextField abv = new JTextField(15);
        abv.setText(beer.getAbv());
        final JTextField brewery = new JTextField(15);
        brewery.setText(beer.getBrewery());
        final JTextField location = new JTextField(15);
        location.setText(beer.getLocation());
        final JTextField price = new JTextField(15);
        price.setText(beer.getPrice());
        final JComboBox<String> category = new JComboBox<>(categories);

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


        JButton save = new JButton("Save");

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        panel.add(new JLabel("Name: "), new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        panel.add(name, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        panel.add(new JLabel("Style: "), new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        panel.add(style,                 new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        panel.add(new JLabel("ABV: "),   new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        panel.add(abv,                   new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        panel.add(new JLabel("Brewery: "),   new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        panel.add(brewery,                   new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        panel.add(new JLabel("Location: "),   new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        panel.add(location,                   new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        panel.add(new JLabel("Price: "), new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        panel.add(price, new GridBagConstraints(1, 6, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        panel.add(new JLabel("Category"), new GridBagConstraints(0, 7, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        panel.add(category, new GridBagConstraints(1, 7, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));

        panel.add(save, new GridBagConstraints(0, 8, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));

        final JFrame frame = new JFrame("Edit Beer");
        frame.setSize(300, 300);
        frame.getContentPane().add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                editedBeer.setName(name.getText());
                editedBeer.setStyle(style.getText());
                editedBeer.setAbv(abv.getText());
                editedBeer.setBrewery(brewery.getText());
                editedBeer.setLocation(location.getText());
                editedBeer.setPrice(price.getText());
                editedBeer.setCategory(category.getSelectedItem().toString());
                addBeerToAList(editedBeer, DRAFT_BEER_MASTER_LIST_XML, draftBeerMasterSortedList);
                frame.setVisible(false);

            }
        });

    }

    void removeBeerFromAList(Beer beer, String fileName, SortedList<Beer> sortedList) {

        SAXBuilder builder = new SAXBuilder();
        String removeName = beer.getName();

        try {
            Document document = builder.build(fileName);
            Element rootNode = document.getRootElement();
            List <Element> beers = rootNode.getChildren("beer");

            for(int i=0;i<=beers.size()-1;i++) {

                Element element = beers.get(i);
                if (removeName.equals(element.getChildText("name"))){

                    beers.remove(i);
                }

            }

            XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
            outputter.output(document, new FileWriter(fileName));



        } catch (JDOMException | IOException e) {
            e.printStackTrace();
        }

        sortedList.remove(beer);


    }

}
