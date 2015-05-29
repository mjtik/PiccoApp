/**
 * Created by mtiko_000 on 4/27/2015.
 */

import ca.odell.glazedlists.FilterList;
import ca.odell.glazedlists.SortedList;
import ca.odell.glazedlists.swing.AdvancedTableModel;
import ca.odell.glazedlists.swing.GlazedListsSwing;
import ca.odell.glazedlists.swing.TableComparatorChooser;
import ca.odell.glazedlists.swing.TextComponentMatcherEditor;
import org.apache.commons.io.FileUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MenuChanger {

    //FTP info
    final String SERVER = "ftp.piccorestaurant.com";
    final int PORT = 21;
    final String USER_NAME = "piccores";
    String password = null;

    //these are the categories beers are sorted into, the beer style may be different and more descriptive (American or English Pale Ale)
    private final String INDIA_PALE_ALES = "India Pale Ales";
    private final String PALE_ALES = "Pale Ales";
    private final String OTHER_ALES = "Other Ales";
    private final String LAGERS = "Lagers";
    private final String BELGIAN_STYLE = "Belgian Style";
    private final String DARK = "Dark";
    private final String CIDER = "Cider";

    // draft beer sizes, used to sort when printing
    private final String SIXTEEN_OZ = "16oz";
    private final String TWELVE_OZ = "12oz";
    private final String TEN_OZ = "10oz";
    private final String EIGHT_OZ = "8oz";

    //type of bottled beer, used for sorting
    private final String TABLE_BEER = "Table Beer";
    private final String BOTTLES_AND_CANS = "Bottles & Cans";

    //filepaths for xml files
    private String DRAFT_BEER_MASTER_LIST_XML_FILEPATH;
    private String CURRENT_DRAFT_BEER_LIST_XML_FILEPATH;
    private String BOTTLED_BEER_MASTER_LIST_XML_FILEPATH;
    private String CURRENT_BOTTLED_BEER_LIST_XML_FILEPATH;

    //home dir, set in filesetup, run at launch
    private String HOME_DIR;


    private String PRINT_LIST_HTML_FOOTER_FILEPATH;
    private String PRINT_LIST_HTML_HEADER_FILEPATH;

    private String WEBSITE_HTML_FOOTER_FILEPATH;
    private String WEBSITE_HTML_HEADER_FILEPATH;

    private String bottlesAndCansHTML;
    private String tableBeerHTML;

    private File beerList_printFile;
    private File beerList_htmlFile;

    //File testFile = new File(System.getProperty("user.home") + System.getProperty("file.separator") + "test.txt");


    final String BOTTLED_LIST_HEADER = "<div class=\"bottleBox\">";
    final String BOTTLED_LIST_FOOTER = "</div>";

    String[] bottleType = {TABLE_BEER, BOTTLES_AND_CANS};
    String [] categories = {INDIA_PALE_ALES, PALE_ALES, OTHER_ALES, LAGERS, BELGIAN_STYLE, DARK, CIDER};
    String[] pourSize = {SIXTEEN_OZ, TWELVE_OZ, TEN_OZ, EIGHT_OZ};

    SortedList<Beer> draftBeerMasterSortedList;
    SortedList<Beer> currentDraftBeerSortedList;

    SortedList<Beer> bottledBeerMasterSortedList;
    SortedList<Beer> currentBottledBeerSortedList;

    Beer selectedBeer = new Beer();
    static Dimension rightPanelDimension = new Dimension(500,400);
    static Color rightPanelColor = Color.WHITE;

    public void fileSetup() {

        //check if directory is setup, if not, make one. (mkdir() does both)
        HOME_DIR = System.getProperty("user.home")+ System.getProperty("file.separator") + "Picco App";
        new File(HOME_DIR).mkdir();
        new File(HOME_DIR + System.getProperty("file.separator") + "XML").mkdir();
        new File(HOME_DIR + System.getProperty("file.separator") + "HTML").mkdir();

        //set file paths
        DRAFT_BEER_MASTER_LIST_XML_FILEPATH = HOME_DIR + System.getProperty("file.separator") + "XML" + System.getProperty("file.separator") + "draftBeerMasterList.xml";
        CURRENT_DRAFT_BEER_LIST_XML_FILEPATH = HOME_DIR + System.getProperty("file.separator") + "XML" + System.getProperty("file.separator") + "currentDraftBeerList.xml";
        BOTTLED_BEER_MASTER_LIST_XML_FILEPATH = HOME_DIR + System.getProperty("file.separator") + "XML" + System.getProperty("file.separator") + "bottledBeerMasterList.xml";
        CURRENT_BOTTLED_BEER_LIST_XML_FILEPATH = HOME_DIR + System.getProperty("file.separator") + "XML" + System.getProperty("file.separator") + "currentBottledBeerList.xml";

        PRINT_LIST_HTML_FOOTER_FILEPATH = HOME_DIR + System.getProperty("file.separator") + "HTML" + System.getProperty("file.separator") + "printList_htmlFooter.txt";
        PRINT_LIST_HTML_HEADER_FILEPATH = HOME_DIR + System.getProperty("file.separator") + "HTML" + System.getProperty("file.separator") + "printList_htmlHeader.txt";

        WEBSITE_HTML_FOOTER_FILEPATH = HOME_DIR + System.getProperty("file.separator") + "HTML" + System.getProperty("file.separator") + "website_htmlFooter.txt";
        WEBSITE_HTML_HEADER_FILEPATH = HOME_DIR + System.getProperty("file.separator") + "HTML" + System.getProperty("file.separator") + "website_htmlHeader.txt";


        draftBeerMasterSortedList = new BeerXMLParser().parseXML(DRAFT_BEER_MASTER_LIST_XML_FILEPATH);
        currentDraftBeerSortedList = new BeerXMLParser().parseXML(CURRENT_DRAFT_BEER_LIST_XML_FILEPATH);
        bottledBeerMasterSortedList = new BeerXMLParser().parseXML(BOTTLED_BEER_MASTER_LIST_XML_FILEPATH);
        currentBottledBeerSortedList = new BeerXMLParser().parseXML(CURRENT_BOTTLED_BEER_LIST_XML_FILEPATH);

        try {
            password = FileUtils.readFileToString(new File(HOME_DIR + "\\No_Commit\\password.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        beerList_printFile = new File(HOME_DIR + "\\html\\Beer_List.html");
        beerList_htmlFile = new File(HOME_DIR + "\\html\\beer.html");

        displayDraftBeerChanger();

    }

    public static void createAndShowGUI(){

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        System.out.println("yes");
        JFrame frame = new JFrame("Beer Menu Changer");
        frame.getContentPane().setLayout(new BorderLayout());

        frame.setSize(600, 500);
        frame.getContentPane().add(leftMenu_JPanel(), BorderLayout.WEST);
        frame.getContentPane().add(beerDropbox_JPanel(), BorderLayout.EAST);
        frame.getContentPane().add(draftBeer_JPanel(), BorderLayout.EAST);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);


    }

    public static JPanel leftMenu_JPanel(){
        JPanel panel = new JPanel();

        //buttons for leftMenu_Panel
        JLabel filler = new JLabel();
        filler.setPreferredSize(new Dimension(100,30));
        final JLabel beer_JLabel = leftMenuJLabel("Beer");
        JLabel wine_JLabel = leftMenuJLabel("Wine");
        JLabel food_JLabel = leftMenuJLabel("Food");
        JLabel icecream_JLabel = leftMenuJLabel("Ice Cream");

        panel.setPreferredSize(new Dimension(100, 500));
        panel.setBackground(Color.LIGHT_GRAY);
        panel.add(filler);
        panel.add(beer_JLabel);
        panel.add(wine_JLabel);
        panel.add(food_JLabel);
        panel.add(icecream_JLabel);

        beer_JLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                beer_JLabel.setBackground(rightPanelColor);
            }
        });



        return panel;

    }

    public static JLabel leftMenuJLabel(String name){

        JLabel button = new JLabel(name);
        button.setBackground(Color.LIGHT_GRAY);
        button.setPreferredSize(new Dimension(100, 30));
        button.setForeground(Color.BLACK);
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.setOpaque(true);

        return button;
    }

    public static class rightDropboxJPanel extends JPanel{

        public rightDropboxJPanel() {
            setPreferredSize(new Dimension(500,100));
            setBackground(Color.BLUE);
        }
    }

    public static class rightJPanel extends JPanel {

        public rightJPanel() {
            setPreferredSize(new Dimension(400,400));
            setBackground(Color.BLUE);
        }
    }

    public static rightDropboxJPanel beerDropbox_JPanel (){

        rightDropboxJPanel panel = new rightDropboxJPanel();
        return panel;
    }

    public static rightJPanel draftBeer_JPanel (){

        rightJPanel panel = new rightJPanel();
        return panel;
    }

    public static rightJPanel bottledBeer_JPanel (){

        rightJPanel panel = new rightJPanel();
        return panel;
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

        final JFrame frame = new JFrame("Beer Menu Changer");
        frame.setSize(540, 380);
        frame.getContentPane().add(panel);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);

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
                addBeerToAList(selectedBeer, CURRENT_BOTTLED_BEER_LIST_XML_FILEPATH, currentBottledBeerSortedList);

            }
        });

        removeBottledBeerFromCurrentList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedBeer = currentBottledBeerSortedList.get(currentBottledBeerListJTable.getSelectedRow());
                removeBeerFromAList(selectedBeer, CURRENT_BOTTLED_BEER_LIST_XML_FILEPATH, currentBottledBeerSortedList);

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
                editBottledBeer(bottledBeerMasterSortedList.get(bottledBeerMasterListJTable.getSelectedRow()), BOTTLED_BEER_MASTER_LIST_XML_FILEPATH, bottledBeerMasterSortedList);

            }
        });

        editDraftBeerList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                displayDraftBeerChanger();
            }
        });





    }

    public void displayDraftBeerChanger() {

        //Left table for BeerMasterList
        JTextField beerMasterListFilterEdit = new JTextField(10);
        final FilterList<Beer> beerMasterListTextFilteredIssues = new FilterList<>(draftBeerMasterSortedList, new TextComponentMatcherEditor<>(beerMasterListFilterEdit, new BeerTextFilter()));
        final AdvancedTableModel<Beer> beerMasterListTableModel = GlazedListsSwing.eventTableModelWithThreadProxyList(beerMasterListTextFilteredIssues, new SimpleBeerTableFormat());
        final JTable beerMasterListJTable = new JTable(beerMasterListTableModel);
        beerMasterListJTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        TableComparatorChooser.install(beerMasterListJTable, draftBeerMasterSortedList, TableComparatorChooser.MULTIPLE_COLUMN_MOUSE);


        //Right table for CurrentBeerList
        JTextField currentBeerListFilterEdit = new JTextField(10);
        final FilterList<Beer> currentBeerListTextFilteredIssues = new FilterList<>(currentDraftBeerSortedList, new TextComponentMatcherEditor<>(currentBeerListFilterEdit, new BeerTextFilter()));
        final AdvancedTableModel<Beer> currentBeerListTableModel = GlazedListsSwing.eventTableModelWithThreadProxyList(currentBeerListTextFilteredIssues, new SimpleBeerTableFormat());
        final JTable currentDraftBeerListJTable = new JTable(currentBeerListTableModel);
        currentDraftBeerListJTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        TableComparatorChooser.install(currentDraftBeerListJTable, currentDraftBeerSortedList, TableComparatorChooser.MULTIPLE_COLUMN_MOUSE);


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


        final JFrame frame = new JFrame("Beer Menu Changer");
        frame.setSize(500, 400);
        frame.getContentPane().add(panel);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        createNewDraftBeerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createNewDraftBeer();
            }
        });

        //if something is selected in current list, deselect it
        beerMasterListJTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (currentDraftBeerListJTable.getSelectedRow()>-1){
                    currentDraftBeerListJTable.clearSelection();
                }
            }
        });

        //if something is selected in the master list, deselect it
        currentDraftBeerListJTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (beerMasterListJTable.getSelectedRow()>-1){
                    beerMasterListJTable.clearSelection();
                }
            }
        });

        addDraftBeerToCurrentList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedBeer = beerMasterListTableModel.getElementAt(beerMasterListJTable.getSelectedRow());
                addBeerToAList(selectedBeer, CURRENT_DRAFT_BEER_LIST_XML_FILEPATH, currentDraftBeerSortedList);
            }
        });

        removeDraftBeerFromCurrentList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedBeer = currentBeerListTableModel.getElementAt(currentDraftBeerListJTable.getSelectedRow());
                removeBeerFromAList(selectedBeer, CURRENT_DRAFT_BEER_LIST_XML_FILEPATH, currentDraftBeerSortedList);

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
                selectedBeer = beerMasterListTableModel.getElementAt(beerMasterListJTable.getSelectedRow());
                editDraftBeer(selectedBeer, DRAFT_BEER_MASTER_LIST_XML_FILEPATH, draftBeerMasterSortedList);
            }
        });

        editBottledBeerList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                displayBottledBeerChanger();
            }
        });


    }

    void printDraftBeer(Beer beer, StringBuilder stringBuilder, String beerCategory) {

        if (stringBuilder.length() == 0) {
            stringBuilder.append("<p class=\"beerStyleHeader\">" + beerCategory + "</p>");
        }

        stringBuilder.append("<p class=\"beerName\">" + beer.getName());
        // deal with adding pour size next to beer name is NOT 16oz
        if (beer.getSize().equals(SIXTEEN_OZ)) {
            stringBuilder.append("</p>");
        } else {
            stringBuilder.append(" " + "(" + beer.getSize() + ")" + "</p>");
        }

        stringBuilder.append("<p1 class=\"beerStyle\">" + beer.getStyle() + "</p1>");
        stringBuilder.append("<p class=\"abv\">" + beer.getAbvString() + "%</p>");
        stringBuilder.append("<p1 class=\"brewery\">" + beer.getBrewery() + "</p1>");
        stringBuilder.append("<p class=\"location\">" + beer.getLocation() + "</p>");
        stringBuilder.append("<p class=\"price\">" + beer.getPrice() + "</p>");

    }

    void printBottledBeer(Beer beer, StringBuilder stringBuilder, String bottleCategory){

        if (stringBuilder.length() == 0) {
            stringBuilder.append("<div class=\"bottleCategoryHeader\">" + bottleCategory + "</div>");

        }
        stringBuilder.append("<div class=\"bottleWrapper\">");
        stringBuilder.append("<div class=\"beerName\">" + beer.getName() + "</div>");
        stringBuilder.append("<div class=\"beerStyle\">" + beer.getStyle() + "</div>");
        stringBuilder.append("<div class=\"abv\">" + beer.getAbvString() + "%</div>");
        stringBuilder.append("<div class=\"brewery\">" + beer.getBrewery() + "</div>");
        stringBuilder.append("<div class=\"location\">" + beer.getLocation() + "</div>");
        stringBuilder.append("<div class=\"price\">" + beer.getPrice() + "</div>");
        stringBuilder.append("</div>");

    }

    void printList(){

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


    }

    void writeList (BufferedWriter bufferedWriter, String header, String footer, List<String> draftBeerArray, List<String> rightColumnArray, List<String> leftColumnArray, int leftColumnSize, int rightColumnSize){
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
        frame.setSize(300, 375);
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
                addBeerToAList(newBeer, DRAFT_BEER_MASTER_LIST_XML_FILEPATH, draftBeerMasterSortedList);
                if (addToCurrentListCheckBox.isSelected()) {
                    addBeerToAList(newBeer, CURRENT_DRAFT_BEER_LIST_XML_FILEPATH, currentDraftBeerSortedList);
                }
                frame.setVisible(false);

            }
        });


    }

    void createNewBottledBeer() {

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


    }

    void addBeerToAList(Beer newBeer, String fileName, SortedList<Beer> sortedList) {

        sortedList.add(newBeer);
        XMLOutputter out = new XMLOutputter();

        try {
            Document doc = new SAXBuilder().build(fileName);

            Element beer = new Element("beer");
            beer.addContent(new Element("name").setText(newBeer.getName()));
            beer.addContent(new Element("style").setText(newBeer.getStyle()));
            beer.addContent(new Element("abv").setText(newBeer.getAbvString()));
            beer.addContent(new Element("size").setText(newBeer.getSize()));
            beer.addContent(new Element("bottleType").setText(newBeer.getBottleType()));
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

    void editDraftBeer(final Beer beer, final String fileName, final SortedList<Beer> sortedList) {

        final Beer editedBeer = new Beer();

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
        final JComboBox<String> pourSizeComboBox = new JComboBox<>(pourSize);
        final JComboBox<String> category = new JComboBox<>(categories);

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
                editedBeer.setSize(pourSizeComboBox.getSelectedItem().toString());
                editedBeer.setBrewery(brewery.getText());
                editedBeer.setLocation(location.getText());
                editedBeer.setPrice(price.getText());
                editedBeer.setCategory(category.getSelectedItem().toString());
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

    }

    void editBottledBeer(final Beer beer, final String fileName, final SortedList<Beer> sortedList) {

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

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

}
