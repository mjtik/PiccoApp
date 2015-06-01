package piccoapp; /**
 * Created by mtiko_000 on 4/27/2015.
 */

import ca.odell.glazedlists.FilterList;
import ca.odell.glazedlists.SortedList;
import ca.odell.glazedlists.swing.AdvancedTableModel;
import ca.odell.glazedlists.swing.GlazedListsSwing;
import ca.odell.glazedlists.swing.TableComparatorChooser;
import ca.odell.glazedlists.swing.TextComponentMatcherEditor;
import org.apache.commons.io.FileUtils;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

public class MenuChanger {

    static final Color rightPanelColor = Color.WHITE;

    // insets for right panel components when two scroll lists are in view.
    // Keeps a little space in GBL in between sides.
    static final Insets rightComponentInsets = new Insets(5, 20, 5, 5);
    static final Insets leftComponentInsets = new Insets(5, 5, 5, 20);


    //filepaths for xml files
    static final String HOME_DIR = System.getProperty("user.home") + System.getProperty("file.separator") + "Picco App";
    static String bottlesAndCansHTML;
    static String tableBeerHTML;
    static SortedList<Beer> bottledBeerMasterSortedList;
    static SortedList<Beer> currentBottledBeerSortedList;
    static Dimension rightPanelDimension = new Dimension(500, 400);
    //FTP info
    final String SERVER = "ftp.piccorestaurant.com";
    final int PORT = 21;
    final String USER_NAME = "piccores";
    final String BOTTLED_BEER_MASTER_LIST_XML_FILEPATH = HOME_DIR + System.getProperty("file.separator") + "XML" + System.getProperty("file.separator") + "bottledBeerMasterList.xml";
    final String CURRENT_BOTTLED_BEER_LIST_XML_FILEPATH = HOME_DIR + System.getProperty("file.separator") + "XML" + System.getProperty("file.separator") + "currentBottledBeerList.xml";
    final String PRINT_LIST_HTML_FOOTER_FILEPATH = HOME_DIR + System.getProperty("file.separator") + "HTML" + System.getProperty("file.separator") + "printList_htmlFooter.txt";
    final String PRINT_LIST_HTML_HEADER_FILEPATH = HOME_DIR + System.getProperty("file.separator") + "HTML" + System.getProperty("file.separator") + "printList_htmlHeader.txt";
    final String WEBSITE_HTML_FOOTER_FILEPATH = HOME_DIR + System.getProperty("file.separator") + "HTML" + System.getProperty("file.separator") + "website_htmlFooter.txt";
    final String WEBSITE_HTML_HEADER_FILEPATH = HOME_DIR + System.getProperty("file.separator") + "HTML" + System.getProperty("file.separator") + "website_htmlHeader.txt";


    final File beerList_printFile = new File(HOME_DIR + "\\html\\Beer_List.html");
    final File beerList_htmlFile = new File(HOME_DIR + "\\html\\beer.html");
    final String BOTTLED_LIST_HEADER = "<div class=\"bottleBox\">";
    final String BOTTLED_LIST_FOOTER = "</div>";
    String password = null;

    public static void setUIFont(javax.swing.plaf.FontUIResource f) {
        java.util.Enumeration keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value != null && value instanceof javax.swing.plaf.FontUIResource)
                UIManager.put(key, f);
        }
    }

    public static void createAndShowGUI() {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            setUIFont(new javax.swing.plaf.FontUIResource("Segoe UI", Font.PLAIN, 16));

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        JFrame frame = new JFrame("Menu Changer");
        frame.setLayout(new GridBagLayout());
        frame.getContentPane().setBackground(Color.LIGHT_GRAY);

        JPanel categoryCards = new JPanel(new CardLayout());

        final String BEER_CARD = "Beer Card";

        categoryCards.add(beerCard(), BEER_CARD);

        frame.setSize(840, 800);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;


        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.weightx = .1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        frame.add(leftMenu_JPanel(), gbc);


        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        frame.add(categoryCards, gbc);

        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }


    public static JPanel leftMenu_JPanel() {
        JPanel panel = new JPanel();

        GridBagConstraints gbc = new GridBagConstraints();
        panel.setLayout(new GridBagLayout());
        //buttons for leftMenu_Panel
        JLabel filler_JLabel = leftMenuJLabel(" ");
        final JLabel beer_JLabel = leftMenuJLabel("Beer  ");
        JLabel wine_JLabel = leftMenuJLabel("Wine  ");
        JLabel food_JLabel = leftMenuJLabel("Food  ");
        JLabel icecream_JLabel = leftMenuJLabel("Ice Cream  ");
        panel.setBackground(Color.LIGHT_GRAY);

        gbc.anchor = GridBagConstraints.FIRST_LINE_START;

        gbc.weightx = 1;
        gbc.weighty=0;
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridy = 0;
        panel.add(filler_JLabel, gbc);
        gbc.gridy = 1;
        panel.add(beer_JLabel, gbc);
        gbc.gridy = 2;
        panel.add(wine_JLabel, gbc);
        gbc.gridy = 3;
        panel.add(food_JLabel, gbc);
        gbc.gridy = 4;
        panel.add(icecream_JLabel, gbc);


        beer_JLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                beer_JLabel.setBackground(rightPanelColor);
            }
        });


        return panel;

    }

    public static JPanel beerCard() {

        final String DRAFTBEER = "Draft";
        final String BOTTLEDBEER = "Bottle/Can";
        String[] choices = {DRAFTBEER, BOTTLEDBEER};
        String heading = "Please select beer type:";
        final CardLayout cardLayout = new CardLayout();
        final JPanel cards = new JPanel(cardLayout);

        final dropboxJPanel dropboxJPanel = new dropboxJPanel(choices, heading);
        cards.add(draftBeer_JPanel(), DRAFTBEER);
        cards.add(bottledBeer_JPanel(), BOTTLEDBEER);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.weightx = .1;
        gbc.weighty = .1;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(dropboxJPanel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1;
        gbc.weighty = 3;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(cards, gbc);

        dropboxJPanel.dropbox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selection = dropboxJPanel.dropbox.getSelectedItem().toString();
                System.out.println(selection);
                switch (selection) {
                    case DRAFTBEER:
                        cardLayout.show(cards, DRAFTBEER);
                        break;
                    case BOTTLEDBEER:
                        cardLayout.show(cards, BOTTLEDBEER);
                        break;
                }
            }
        });

        return panel;

    }

    public static rightContentJPanel draftBeer_JPanel() {

        final String DRAFT_BEER_MASTER_LIST_XML_FILEPATH = HOME_DIR + System.getProperty("file.separator") + "XML" + System.getProperty("file.separator") + "draftBeerMasterList.xml";
        final String CURRENT_DRAFT_BEER_LIST_XML_FILEPATH = HOME_DIR + System.getProperty("file.separator") + "XML" + System.getProperty("file.separator") + "currentDraftBeerList.xml";
        final draftBeerList draftBeerList_Master = new draftBeerList(DRAFT_BEER_MASTER_LIST_XML_FILEPATH);
        final draftBeerList draftBeerList_Current = new draftBeerList(CURRENT_DRAFT_BEER_LIST_XML_FILEPATH);

        rightContentJPanel panel = new rightContentJPanel();

        Border paddingBorder = BorderFactory.createEmptyBorder(5, 30, 30, 30);
        panel.setBorder(paddingBorder);

        //Left table for BeerMasterList
        JTextField beerMasterListFilterEdit = new JTextField(10);
        final FilterList<Beer> beerMasterListTextFilteredIssues = new FilterList<>(draftBeerList_Master.getSortedList(), new TextComponentMatcherEditor<>(beerMasterListFilterEdit, new BeerTextFilter()));
        final AdvancedTableModel<Beer> beerMasterListTableModel = GlazedListsSwing.eventTableModelWithThreadProxyList(beerMasterListTextFilteredIssues, new SimpleBeerTableFormat());
        final JTable beerMasterListJTable = new JTable(beerMasterListTableModel);
        beerMasterListJTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        TableComparatorChooser.install(beerMasterListJTable, draftBeerList_Master.getSortedList(), TableComparatorChooser.MULTIPLE_COLUMN_MOUSE);
        beerMasterListJTable.setRowHeight(25);


        //Right table for CurrentBeerList
        JTextField currentBeerListFilterEdit = new JTextField(10);
        final FilterList<Beer> currentBeerListTextFilteredIssues = new FilterList<>(draftBeerList_Current.getSortedList(), new TextComponentMatcherEditor<>(currentBeerListFilterEdit, new BeerTextFilter()));
        final AdvancedTableModel<Beer> currentBeerListTableModel = GlazedListsSwing.eventTableModelWithThreadProxyList(currentBeerListTextFilteredIssues, new SimpleBeerTableFormat());
        final JTable currentDraftBeerListJTable = new JTable(currentBeerListTableModel);
        currentDraftBeerListJTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        TableComparatorChooser.install(currentDraftBeerListJTable, draftBeerList_Current.getSortedList(), TableComparatorChooser.MULTIPLE_COLUMN_MOUSE);
        currentDraftBeerListJTable.setRowHeight(25);


        panel.setLayout(new GridBagLayout());

        JScrollPane beerMasterListScrollPane = new JScrollPane(beerMasterListJTable);
        JScrollPane currentBeerListScrollPane = new JScrollPane(currentDraftBeerListJTable);

        JButton createNewDraftBeerButton = new JButton("New");
        final JButton addDraftBeerToCurrentList = new JButton("Add To List");
        final JButton removeDraftBeerFromCurrentList = new JButton("86 From List");
        final JButton printList = new JButton("Print");
        final JButton editDraftBeer = new JButton("Edit");

        panel.add(new JLabel("All Beer:"), new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, leftComponentInsets, 0, 0));
        panel.add(new JLabel("Current List: "), new GridBagConstraints(2, 0, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, rightComponentInsets, 0, 0));
        panel.add(new JLabel("Filter: "), new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, leftComponentInsets, 0, 0));
        panel.add(beerMasterListFilterEdit, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, leftComponentInsets, 0, 0));
        panel.add(new JLabel("Filter: "), new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, rightComponentInsets, 0, 0));
        panel.add(currentBeerListFilterEdit, new GridBagConstraints(3, 1, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, rightComponentInsets, 0, 0));

        panel.add(addDraftBeerToCurrentList, new GridBagConstraints(0, 2, 2, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, leftComponentInsets, 0, 0));
        panel.add(removeDraftBeerFromCurrentList, new GridBagConstraints(2, 2, 2, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, rightComponentInsets, 0, 0));

        panel.add(beerMasterListScrollPane, new GridBagConstraints(0, 3, 2, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, leftComponentInsets, 0, 0));
        panel.add(currentBeerListScrollPane, new GridBagConstraints(2, 3, 2, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, rightComponentInsets, 0, 0));

        panel.add(createNewDraftBeerButton, new GridBagConstraints(0, 4, 2, 1, 1, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, leftComponentInsets, 0, 0));
        panel.add(editDraftBeer, new GridBagConstraints(0, 5, 2, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, leftComponentInsets, 0, 0));
        panel.add(printList, new GridBagConstraints(2, 4, 2, 1, 1, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, rightComponentInsets, 0, 0));


        createNewDraftBeerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                new Beer().createNewDraftBeer(draftBeerList_Master, draftBeerList_Current);

            }
        });

        //if something is selected in current list, deselect it
        beerMasterListJTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (currentDraftBeerListJTable.getSelectedRow() > -1) {
                    currentDraftBeerListJTable.clearSelection();
                }
            }
        });

        //if something is selected in the master list, deselect it
        currentDraftBeerListJTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (beerMasterListJTable.getSelectedRow() > -1) {
                    beerMasterListJTable.clearSelection();
                }
            }
        });

        addDraftBeerToCurrentList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                draftBeerList_Current.addBeer(beerMasterListTableModel.getElementAt(beerMasterListJTable.getSelectedRow()));

            }
        });

        removeDraftBeerFromCurrentList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                draftBeerList_Current.removeBeer(currentBeerListTableModel.getElementAt(currentDraftBeerListJTable.getSelectedRow()));

            }
        });

        printList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //printList();
            }
        });

        editDraftBeer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new Beer().editBeer(beerMasterListTableModel.getElementAt(beerMasterListJTable.getSelectedRow()), draftBeerList_Master, draftBeerList_Current);

                } catch (IndexOutOfBoundsException e1) {

                    try {
                        new Beer().editBeer(currentBeerListTableModel.getElementAt(currentDraftBeerListJTable.getSelectedRow()), draftBeerList_Master, draftBeerList_Current);
                    } catch (IndexOutOfBoundsException e2) {

                    }

                }

            }


        });


        return panel;
    }

    public static rightContentJPanel bottledBeer_JPanel() {

        rightContentJPanel panel = new rightContentJPanel();
        return panel;
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        //fileSetup();
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    public static JLabel leftMenuJLabel(String name) {

        JLabel button = new JLabel(name);
        //Font bigFont = new Font(Font.SANS_SERIF, Font.PLAIN, 20);
        //button.setFont(bigFont);
        Border paddingBorder = BorderFactory.createEmptyBorder(10, 30, 10, 10);
        button.setBorder(paddingBorder);
        button.setBackground(Color.LIGHT_GRAY);
        button.setForeground(Color.BLACK);
        button.setOpaque(true);

        return button;
    }

    public static class dropboxJPanel extends JPanel {

        public JComboBox<String> dropbox;

        public dropboxJPanel(String[] choices, String heading) {

            Border paddingBorder = BorderFactory.createEmptyBorder(30, 30, 5, 30);
            this.setBorder(paddingBorder);

            GridBagConstraints gbc = new GridBagConstraints();
            this.setLayout(new GridBagLayout());


            gbc.weighty = 1;
            gbc.anchor = GridBagConstraints.FIRST_LINE_START;
            gbc.insets = new Insets(5, 5, 5, 5);

            dropbox = new JComboBox<>(choices);
            JLabel beerDropBox_Label = new JLabel(heading);

            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.weightx = 0;
            this.add(beerDropBox_Label, gbc);
            gbc.gridx = 1;
            gbc.gridy = 0;
            gbc.weightx = 5;
            this.add(dropbox, gbc);

        }




    }

    public static class dropboxJPanel extends JPanel {

        public JComboBox<String> dropbox;

        public dropboxJPanel(String[] choices, String heading) {

            Border paddingBorder = BorderFactory.createEmptyBorder(30, 30, 5, 30);
            this.setBorder(paddingBorder);

            GridBagConstraints gbc = new GridBagConstraints();
            this.setLayout(new GridBagLayout());


            gbc.weighty = 1;
            gbc.anchor = GridBagConstraints.FIRST_LINE_START;
            gbc.insets = new Insets(5, 5, 5, 5);

            dropbox = new JComboBox<>(choices);
            JLabel beerDropBox_Label = new JLabel(heading);

            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.weightx = 0;
            this.add(beerDropBox_Label, gbc);
            gbc.gridx = 1;
            gbc.gridy = 0;
            gbc.weightx = 5;
            this.add(dropbox, gbc);

        }


    }

    public void fileSetup() {

        //check if directory is setup, if not, make one. (mkdir() does both)
        new File(HOME_DIR).mkdir();
        new File(HOME_DIR + System.getProperty("file.separator") + "XML").mkdir();
        new File(HOME_DIR + System.getProperty("file.separator") + "HTML").mkdir();
        new File(HOME_DIR + System.getProperty("file.separator") + "Data").mkdir();

        bottledBeerMasterSortedList = new BeerXMLParser().parseXML(BOTTLED_BEER_MASTER_LIST_XML_FILEPATH);
        currentBottledBeerSortedList = new BeerXMLParser().parseXML(CURRENT_BOTTLED_BEER_LIST_XML_FILEPATH);

        try {
            password = FileUtils.readFileToString(new File(HOME_DIR + "\\Data\\hamsandwich.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static class rightContentJPanel extends JPanel {

        public rightContentJPanel() {
            setBackground(Color.WHITE);
        }
    }



//    void printDraftBeer(Beer beer, StringBuilder stringBuilder, String beerCategory) {
//
//        if (stringBuilder.length() == 0) {
//            stringBuilder.append("<p class=\"beerStyleHeader\">" + beerCategory + "</p>");
//        }
//
//        stringBuilder.append("<p class=\"beerName\">" + beer.getName());
//        // deal with adding pour size next to beer name is NOT 16oz
//        if (beer.getSize().equals(SIXTEEN_OZ)) {
//            stringBuilder.append("</p>");
//        } else {
//            stringBuilder.append(" " + "(" + beer.getSize() + ")" + "</p>");
//        }
//
//        stringBuilder.append("<p1 class=\"beerStyle\">" + beer.getStyle() + "</p1>");
//        stringBuilder.append("<p class=\"abv\">" + beer.getAbvString() + "%</p>");
//        stringBuilder.append("<p1 class=\"brewery\">" + beer.getBrewery() + "</p1>");
//        stringBuilder.append("<p class=\"location\">" + beer.getLocation() + "</p>");
//        stringBuilder.append("<p class=\"price\">" + beer.getPrice() + "</p>");
//
//    }
//
//    static void printBottledBeer(Beer beer, StringBuilder stringBuilder, String bottleCategory){
//
//        if (stringBuilder.length() == 0) {
//            stringBuilder.append("<div class=\"bottleCategoryHeader\">" + bottleCategory + "</div>");
//
//        }
//        stringBuilder.append("<div class=\"bottleWrapper\">");
//        stringBuilder.append("<div class=\"beerName\">" + beer.getName() + "</div>");
//        stringBuilder.append("<div class=\"beerStyle\">" + beer.getStyle() + "</div>");
//        stringBuilder.append("<div class=\"abv\">" + beer.getAbvString() + "%</div>");
//        stringBuilder.append("<div class=\"brewery\">" + beer.getBrewery() + "</div>");
//        stringBuilder.append("<div class=\"location\">" + beer.getLocation() + "</div>");
//        stringBuilder.append("<div class=\"price\">" + beer.getPrice() + "</div>");
//        stringBuilder.append("</div>");
//
//    }

    /*static void printList(){

        String indiaPaleAlesHTML;
        String paleAlesHTML;
        String otherAlesHTML;
        String lagersHTML;
        String belgianStyleHTML;
        String darkHTML;
        String ciderHTML;

        String printList_headerHTML;
        String printList_footerHTML;

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
        List<List> allCategories_ArrayList = new ArrayList();
        allCategories_ArrayList.add(indiaPaleAles_ArrayList);
        allCategories_ArrayList.add(paleAles_ArrayList);
        allCategories_ArrayList.add(otherAles_ArrayList);
        allCategories_ArrayList.add(lagers_ArrayList);
        allCategories_ArrayList.add(belgianStyle_ArrayList);
        allCategories_ArrayList.add(dark_ArrayList);
        allCategories_ArrayList.add(cider_ArrayList);

        int leftColumnSize = 0;
        int rightColumnSize = 0;

        //Bottled list
        for (int i = 0; i <= currentBottledBeerSortedList.size() - 1;i++){

            Beer beer = currentBottledBeerSortedList.get(i);

            switch (beer.getBottleType()){
                case BOTTLES_AND_CANS:
                    printBottledBeer(beer, bottlesAndCansStringBuilder, BOTTLES_AND_CANS);
                    break;
                case TABLE_BEER:
                    printBottledBeer(beer, tableBeerStringBuilder, TABLE_BEER);
                    break;
            }



        }

        //Draft List
        //sort into categories and arrays
        for (int i = 0; i <= currentDraftBeerSortedList.size() - 1; i++) {

            Beer beer = currentDraftBeerSortedList.get(i);

            switch (beer.getCategory()){
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
        for (List l : allCategories_ArrayList){

            Collections.sort(l, new draftAbvSorter());

        }

        //print all beers in each category
        for (Beer b : indiaPaleAles_ArrayList){
            printDraftBeer(b, indiaPaleAlesBuilder, INDIA_PALE_ALES);
        }

        for (Beer b : paleAles_ArrayList){
            printDraftBeer(b, paleAlesBuilder, PALE_ALES);
        }

        for (Beer b : otherAles_ArrayList){
            printDraftBeer(b, otherAlesBuilder, OTHER_ALES);
        }

        for (Beer b : lagers_ArrayList){
            printDraftBeer(b, lagersBuilder, LAGERS);
        }

        for (Beer b : belgianStyle_ArrayList){
            printDraftBeer(b, belgianStyleBuilder, BELGIAN_STYLE);
        }

        for (Beer b : dark_ArrayList){
            printDraftBeer(b, darkBuilder, DARK);
        }

        for (Beer b : cider_ArrayList){
            printDraftBeer(b, ciderBuilder, CIDER);
        }

        indiaPaleAlesHTML = indiaPaleAlesBuilder.toString();
        paleAlesHTML = paleAlesBuilder.toString();
        otherAlesHTML = otherAlesBuilder.toString();
        lagersHTML = lagersBuilder.toString();
        belgianStyleHTML = belgianStyleBuilder.toString();
        darkHTML = darkBuilder.toString();
        ciderHTML = ciderBuilder.toString();

        bottlesAndCansHTML = bottlesAndCansStringBuilder.toString();
        tableBeerHTML = tableBeerStringBuilder.toString();

        File printList_headerFile = new File(PRINT_LIST_HTML_HEADER_FILEPATH);
        File printList_footerFile = new File(PRINT_LIST_HTML_FOOTER_FILEPATH);

        File website_headerFile = new File(WEBSITE_HTML_HEADER_FILEPATH);
        File website_footerFile = new File(WEBSITE_HTML_FOOTER_FILEPATH);


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

        try {
            printList_headerHTML = FileUtils.readFileToString(printList_headerFile);
            printList_footerHTML = FileUtils.readFileToString(printList_footerFile);

            website_headerHTML= FileUtils.readFileToString(website_headerFile);
            website_footerHTML = FileUtils.readFileToString(website_footerFile);

            BufferedWriter printList_bufferedWriter = new BufferedWriter(new FileWriter(beerList_printFile));
            BufferedWriter website_bufferedWriter = new BufferedWriter(new FileWriter(beerList_htmlFile));

            writeList (printList_bufferedWriter, printList_headerHTML, printList_footerHTML, draftBeerArray, rightColumnArray, leftColumnArray, leftColumnSize, rightColumnSize);
            writeList (website_bufferedWriter, website_headerHTML, website_footerHTML, draftBeerArray, rightColumnArray, leftColumnArray, leftColumnSize, rightColumnSize);

            FTPClient ftpClient = new FTPClient();

            ftpClient.connect(SERVER, PORT);
            ftpClient.login(USER_NAME, password);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            ftpClient.changeWorkingDirectory("/httpdocs/test");

            String printList_remoteFile = "printList.html";
            InputStream printList_inputStream = new FileInputStream(beerList_printFile);
            boolean printList_done = ftpClient.storeFile(printList_remoteFile, printList_inputStream);
            if (printList_done){
                System.out.println("beerList_printFile uploaded");
            }

            String website_remoteFile = "beer.html";
            InputStream website_inputStream = new FileInputStream(beerList_htmlFile);
            boolean website_done = ftpClient.storeFile(website_remoteFile, website_inputStream);
            if (website_done){
                System.out.println("beerList_website uploaded");
            }


            
        } catch (IOException e) {
            e.printStackTrace();
        }


    }*/

    /*static void writeList (BufferedWriter bufferedWriter, String header, String footer, List<String> draftBeerArray, List<String> rightColumnArray, List<String> leftColumnArray, int leftColumnSize, int rightColumnSize){
        try {
        bufferedWriter.write(header);

        bufferedWriter.write(BOTTLED_LIST_HEADER);
        bufferedWriter.write(bottlesAndCansHTML);
        bufferedWriter.write(tableBeerHTML);

            bufferedWriter.write(BOTTLED_LIST_FOOTER);


        // sort into right and left columns depending on the size of each column
        // to avoid being to big to print

        for (int i=0;i<draftBeerArray.size();i++){


            if (leftColumnSize > rightColumnSize){
                rightColumnArray.add((draftBeerArray.get(i)));
                rightColumnSize += draftBeerArray.get(i).length();

            } else {
                leftColumnArray.add(draftBeerArray.get(i));
                leftColumnSize += draftBeerArray.get(i).length();

            }

        }

            // header text for draft list "Draft Beer: All draft beers are served in 16oz glasses unless noted otherwise
        bufferedWriter.write("<div id=\"draftHeader\">Draft Beer</div>\n" +
                "    <div id=\"draftDescription\">All draft beers are served in 16oz glasses unless noted otherwise</div>");
        bufferedWriter.write("<div class=\"leftSide\">");
        for (String s : leftColumnArray){
            bufferedWriter.write(s);
        }
        bufferedWriter.write("</div>");

        bufferedWriter.write("<div class=\"rightSide\">");
        for (String s : rightColumnArray){
            bufferedWriter.write(s);
        }

        bufferedWriter.write("</div>");

        bufferedWriter.write(footer);
        bufferedWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }*/



    /*static void createNewBottledBeer() {

        final Beer newBeer = new Beer();

        final JTextField name = new JTextField(15);
        final JTextField style = new JTextField(15);
        final JTextField abv = new JTextField(15);
        final JTextField size = new JTextField(15);
        final JTextField brewery = new JTextField(15);
        final JTextField location = new JTextField(15);
        final JTextField price = new JTextField(15);
        final JComboBox<String> bottleTypeComboBox = new JComboBox<>(bottleType);
        final JCheckBox addToCurrentListCheckBox = new JCheckBox("Add beer to current list?");

        JButton createNewBeerButton = new JButton("Create Beer");

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
        panel.add(new JLabel("Style: "),gbc);
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
        panel.add(createNewBeerButton, gbc);

        final JFrame frame = new JFrame("Create New Beer");
        frame.setSize(300, 380);
        frame.getContentPane().add(panel);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
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
                newBeer.setSize(size.getText());
                newBeer.setBottleType(bottleTypeComboBox.getSelectedItem().toString());
                addBeerToAList(newBeer, BOTTLED_BEER_MASTER_LIST_XML_FILEPATH, bottledBeerMasterSortedList);
                if (addToCurrentListCheckBox.isSelected()) {
                    addBeerToAList(newBeer, CURRENT_BOTTLED_BEER_LIST_XML_FILEPATH, currentBottledBeerSortedList);
                }
                frame.setVisible(false);

            }
        });


    }*/


    /*static void editBottledBeer(final Beer beer, final String fileName, final SortedList<Beer> sortedList) {

        final Beer editedBeer = new Beer();

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
        final JComboBox<String> bottleTypeComboBox = new JComboBox<>(bottleType);

        JButton save_Button = new JButton("Save");
        JButton delete_Button = new JButton("Delete");

        switch (beer.getBottleType()){
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
        gbc.insets = new Insets(5,20,5,5);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.BOTH;

        panel.add(new JLabel("Name: "), gbc);
        panel.add(new JLabel("Style: "),gbc);
        panel.add(new JLabel("ABV: "), gbc);
        panel.add(new JLabel("Size: "), gbc);
        panel.add(new JLabel("Bottle Type: "), gbc);
        panel.add(new JLabel("Brewery: "), gbc);
        panel.add(new JLabel("Location: "), gbc);
        panel.add(new JLabel("Price: "), gbc);

        gbc.gridx = 1;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets = new Insets(5,5,5,20);

        panel.add(name,gbc);
        panel.add(style, gbc);
        panel.add(abv, gbc);
        panel.add(size, gbc);
        panel.add(brewery, gbc);
        panel.add(location, gbc);
        panel.add(price, gbc);
        panel.add(bottleTypeComboBox,gbc);

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
        frame.setSize(300, 375);
        frame.getContentPane().add(panel);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);

        save_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                removeBeerFromAList(beer, fileName, sortedList);

                editedBeer.setName(name.getText());
                editedBeer.setStyle(style.getText());
                editedBeer.setAbv(abv.getText());
                editedBeer.setSize(size.getText());
                editedBeer.setBrewery(brewery.getText());
                editedBeer.setLocation(location.getText());
                editedBeer.setPrice(price.getText());
                editedBeer.setBottleType(bottleTypeComboBox.getSelectedItem().toString());
                addBeerToAList(editedBeer, fileName, sortedList);
                frame.setVisible(false);

            }
        });

        delete_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeBeerFromAList(beer, fileName, sortedList);
                frame.setVisible(false);
            }
        });

    }*/



}
