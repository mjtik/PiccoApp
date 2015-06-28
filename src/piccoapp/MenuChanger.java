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
import piccoapp.customUI.customButton;
import piccoapp.customUI.customScrollPane;
import piccoapp.customUI.customTable;
import piccoapp.lists.beerList;
import piccoapp.lists.flavorList;
import piccoapp.menuItems.Beer;
import piccoapp.menuItems.Flavor;
import piccoapp.tables.beer.BeerTextFilter;
import piccoapp.tables.beer.SimpleBeerTableFormat;
import piccoapp.tables.flavors.FlavorTableFormat;
import piccoapp.tables.flavors.FlavorTextFilter;

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
    //filepaths for xml files
    public static final String HOME_DIR = System.getProperty("user.home") + System.getProperty("file.separator") +
            "Picco App";
    //colors for UI
    public static final Color one = Color.WHITE;
    public static final Color two = Color.DARK_GRAY;
    public static final Color three = Color.LIGHT_GRAY;
    //FTP info
    static final String SERVER = "ftp.piccorestaurant.com";
    static final int PORT = 21;
    static final String USER_NAME = "piccores";
    static final Color rightPanelColor = one;
    // insets for right panel components when two scroll lists are in view.
    // Keeps a little space in GBL in between sides.
    static final Insets rightComponentInsets = new Insets(5, 20, 5, 5);
    static final Insets leftComponentInsets = new Insets(5, 5, 5, 20);

    //fielpaths for draft beer XML files
    static final String MASTER_DRAFT_BEER_LIST_XML_FILEPATH = HOME_DIR + System.getProperty("file.separator") + "XML"
            + System.getProperty("file.separator") + "draftBeerMasterList.xml";
    static final String CURRENT_DRAFT_BEER_LIST_XML_FILEPATH = HOME_DIR + System.getProperty("file.separator") +
            "XML" + System.getProperty("file.separator") + "currentDraftBeerList.xml";
    static final beerList master_draftBeerList = new beerList(MASTER_DRAFT_BEER_LIST_XML_FILEPATH);
    static final beerList current_draftBeerList = new beerList(CURRENT_DRAFT_BEER_LIST_XML_FILEPATH);

    //filepaths for bottled beer XML files
    static final String MASTER_BOTTLED_BEER_LIST_XML_FILEPATH = HOME_DIR + System.getProperty("file.separator") +
            "XML" + System.getProperty("file.separator") + "bottledBeerMasterList.xml";
    static final String CURRENT_BOTTLED_BEER_LIST_XML_FILEPATH = HOME_DIR + System.getProperty("file.separator") +
            "XML" + System.getProperty("file.separator") + "currentBottledBeerList.xml";
    static final beerList master_bottledBeerList = new beerList(MASTER_BOTTLED_BEER_LIST_XML_FILEPATH);
    static final beerList current_bottledBeerList = new beerList(CURRENT_BOTTLED_BEER_LIST_XML_FILEPATH);

    //filepaths for icecream XML files
    static final String MASTER_ICE_CREAM_LIST_XML_FILESPATH = HOME_DIR + System.getProperty("file.separator") + "XML"
            + System.getProperty("file.separator") + "iceCreamMasterList.xml";
    static final String CURRENT_ICE_CREAM_LIST_XML_FILEPATH = HOME_DIR + System.getProperty("file.separator") +
            "XML" + System.getProperty("file.separator") + "currentIceCreamList.xml";
    static final flavorList master_iceCreamList = new flavorList(MASTER_ICE_CREAM_LIST_XML_FILESPATH);
    static final flavorList current_iceCreamList = new flavorList(CURRENT_ICE_CREAM_LIST_XML_FILEPATH);

    //filepaths for sorbet XML files
    static final String MASTER_SORBET_LIST_XML_FILESPATH = HOME_DIR + System.getProperty("file.separator") + "XML"
            + System.getProperty("file.separator") + "sorbetMasterList.xml";
    static final String CURRENT_SORBET_LIST_XML_FILEPATH = HOME_DIR + System.getProperty("file.separator") +
            "XML" + System.getProperty("file.separator") + "currentSorbetList.xml";
    static final flavorList master_sorbetList = new flavorList(MASTER_SORBET_LIST_XML_FILESPATH);
    static final flavorList current_sorbetList = new flavorList(CURRENT_SORBET_LIST_XML_FILEPATH);

    static SortedList<Beer> bottledBeerMasterSortedList;
    static SortedList<Beer> currentBottledBeerSortedList;
    static Dimension rightPanelDimension = new Dimension(500, 400);
    static String password = null;

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
        setUIFont(new javax.swing.plaf.FontUIResource("Segoe UI", Font.PLAIN, 16));

        /*try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }*/

        JFrame frame = new JFrame("Menu Changer");
        frame.setLayout(new GridBagLayout());
        frame.getContentPane().setBackground(two);

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
        panel.setBackground(three);

        gbc.anchor = GridBagConstraints.FIRST_LINE_START;

        gbc.weightx = 1;
        gbc.weighty = 0;
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

        beer_JLabel.setBackground(three);
        beer_JLabel.setForeground(Color.BLACK);

        beer_JLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                beer_JLabel.setBackground(three);
                beer_JLabel.setForeground(Color.BLACK);
            }
        });


        return panel;

    }


    //Beer cards and panels
    public static JPanel beerCard() {

        final String DRAFTBEER = "Draft";
        final String BOTTLEDBEER = "Bottle/Can";
        String[] choices = {DRAFTBEER, BOTTLEDBEER};
        String heading = "Please select beer type:";
        final CardLayout cardLayout = new CardLayout();
        final JPanel cards = new JPanel(cardLayout);

        final dropboxJPanel dropboxJPanel = new dropboxJPanel(choices, heading);
        cards.add(draftBeer_JPanel(master_draftBeerList, current_draftBeerList), DRAFTBEER);
        cards.add(bottledBeer_JPanel(master_bottledBeerList, current_bottledBeerList), BOTTLEDBEER);

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

    public static rightContentJPanel draftBeer_JPanel(final beerList beerList_Master, final beerList beerList_Current) {

        rightContentJPanel panel = new rightContentJPanel();

        Border paddingBorder = BorderFactory.createEmptyBorder(5, 30, 30, 30);
        panel.setBorder(paddingBorder);

        //Left table for BeerMasterList
        JTextField beerMasterListFilterEdit = new JTextField(10);
        final FilterList<Beer> beerMasterListTextFilteredIssues = new FilterList<>(beerList_Master.getSortedList(),
                new TextComponentMatcherEditor<>(beerMasterListFilterEdit, new BeerTextFilter()));
        final AdvancedTableModel<Beer> beerMasterListTableModel = GlazedListsSwing.eventTableModelWithThreadProxyList
                (beerMasterListTextFilteredIssues, new SimpleBeerTableFormat());
        final customTable beerMasterListJTable = new customTable(beerMasterListTableModel);
        beerMasterListJTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        TableComparatorChooser.install(beerMasterListJTable, beerList_Master.getSortedList(), TableComparatorChooser
                .MULTIPLE_COLUMN_MOUSE);


        //Right table for CurrentBeerList
        JTextField currentBeerListFilterEdit = new JTextField(10);
        final FilterList<Beer> currentBeerListTextFilteredIssues = new FilterList<>(beerList_Current.getSortedList(),
                new TextComponentMatcherEditor<>(currentBeerListFilterEdit, new BeerTextFilter()));
        final AdvancedTableModel<Beer> currentBeerListTableModel = GlazedListsSwing
                .eventTableModelWithThreadProxyList(currentBeerListTextFilteredIssues, new SimpleBeerTableFormat());
        final customTable currentDraftBeerListJTable = new customTable(currentBeerListTableModel);
        currentDraftBeerListJTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        TableComparatorChooser.install(currentDraftBeerListJTable, beerList_Current.getSortedList(),
                TableComparatorChooser.MULTIPLE_COLUMN_MOUSE);


        currentDraftBeerListJTable.getTableHeader().setOpaque(false);


        panel.setLayout(new GridBagLayout());

        customScrollPane beerMasterListScrollPane = new customScrollPane(beerMasterListJTable);
        customScrollPane currentBeerListScrollPane = new customScrollPane(currentDraftBeerListJTable);


        customButton createNewDraftBeerButton = new customButton("New");
        final customButton addDraftBeerToCurrentList = new customButton("Add To List");
        final customButton removeDraftBeerFromCurrentList = new customButton("86 From List");
        final customButton printList = new customButton("Print");
        final customButton editDraftBeer = new customButton("Edit");


        panel.add(new JLabel("All Beer:"), new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER,
                GridBagConstraints.BOTH, leftComponentInsets, 0, 0));
        panel.add(new JLabel("Current List: "), new GridBagConstraints(2, 0, 2, 1, 0.0, 0.0, GridBagConstraints
                .CENTER, GridBagConstraints.BOTH, rightComponentInsets, 0, 0));
        panel.add(new JLabel("Filter: "), new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
                GridBagConstraints.BOTH, leftComponentInsets, 0, 0));
        panel.add(beerMasterListFilterEdit, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
                GridBagConstraints.BOTH, leftComponentInsets, 0, 0));
        panel.add(new JLabel("Filter: "), new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
                GridBagConstraints.BOTH, rightComponentInsets, 0, 0));
        panel.add(currentBeerListFilterEdit, new GridBagConstraints(3, 1, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
                GridBagConstraints.BOTH, rightComponentInsets, 0, 0));

        panel.add(addDraftBeerToCurrentList, new GridBagConstraints(0, 2, 2, 1, 1.0, 0.0, GridBagConstraints.CENTER,
                GridBagConstraints.BOTH, leftComponentInsets, 0, 0));
        panel.add(removeDraftBeerFromCurrentList, new GridBagConstraints(2, 2, 2, 1, 1.0, 0.0, GridBagConstraints
                .CENTER, GridBagConstraints.BOTH, rightComponentInsets, 0, 0));

        panel.add(beerMasterListScrollPane, new GridBagConstraints(0, 3, 2, 1, 1.0, 1.0, GridBagConstraints.CENTER,
                GridBagConstraints.BOTH, leftComponentInsets, 0, 0));
        panel.add(currentBeerListScrollPane, new GridBagConstraints(2, 3, 2, 1, 1.0, 1.0, GridBagConstraints.CENTER,
                GridBagConstraints.BOTH, rightComponentInsets, 0, 0));

        panel.add(createNewDraftBeerButton, new GridBagConstraints(0, 4, 2, 1, 1, 0.0, GridBagConstraints.CENTER,
                GridBagConstraints.BOTH, leftComponentInsets, 0, 0));
        panel.add(editDraftBeer, new GridBagConstraints(0, 5, 2, 1, 1.0, 0.0, GridBagConstraints.CENTER,
                GridBagConstraints.BOTH, leftComponentInsets, 0, 0));
        panel.add(printList, new GridBagConstraints(2, 4, 2, 1, 1, 0.0, GridBagConstraints.CENTER, GridBagConstraints
                .BOTH, rightComponentInsets, 0, 0));


        createNewDraftBeerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                new Beer().createNewDraftBeer(beerList_Master, beerList_Current);

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

                beerList_Current.addBeer(beerMasterListTableModel.getElementAt(beerMasterListJTable.getSelectedRow()));

            }
        });

        removeDraftBeerFromCurrentList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                beerList_Current.removeBeer(currentBeerListTableModel.getElementAt(currentDraftBeerListJTable
                        .getSelectedRow()));

            }
        });

        printList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Beer().printBeerList(current_draftBeerList, current_bottledBeerList);
            }
        });

        editDraftBeer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new Beer().editDraftBeer(beerMasterListTableModel.getElementAt(beerMasterListJTable
                            .getSelectedRow()), beerList_Master, beerList_Current);

                } catch (IndexOutOfBoundsException e1) {

                    try {
                        new Beer().editDraftBeer(currentBeerListTableModel.getElementAt(currentDraftBeerListJTable
                                .getSelectedRow()), beerList_Master, beerList_Current);
                    } catch (IndexOutOfBoundsException e2) {

                    }

                }

            }


        });


        return panel;
    }

    public static rightContentJPanel bottledBeer_JPanel(final beerList beerList_Master, final beerList
            beerList_Current) {


        rightContentJPanel panel = new rightContentJPanel();

        Border paddingBorder = BorderFactory.createEmptyBorder(5, 30, 30, 30);
        panel.setBorder(paddingBorder);

        //Left table for BeerMasterList
        JTextField beerMasterListFilterEdit = new JTextField(10);
        final FilterList<Beer> beerMasterListTextFilteredIssues = new FilterList<>(beerList_Master.getSortedList(),
                new TextComponentMatcherEditor<>(beerMasterListFilterEdit, new BeerTextFilter()));
        final AdvancedTableModel<Beer> beerMasterListTableModel = GlazedListsSwing.eventTableModelWithThreadProxyList
                (beerMasterListTextFilteredIssues, new SimpleBeerTableFormat());
        final customTable beerMasterListJTable = new customTable(beerMasterListTableModel);
        beerMasterListJTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        TableComparatorChooser.install(beerMasterListJTable, beerList_Master.getSortedList(), TableComparatorChooser
                .MULTIPLE_COLUMN_MOUSE);


        //Right table for CurrentBeerList
        JTextField currentBeerListFilterEdit = new JTextField(10);
        final FilterList<Beer> currentBeerListTextFilteredIssues = new FilterList<>(beerList_Current.getSortedList(),
                new TextComponentMatcherEditor<>(currentBeerListFilterEdit, new BeerTextFilter()));
        final AdvancedTableModel<Beer> currentBeerListTableModel = GlazedListsSwing
                .eventTableModelWithThreadProxyList(currentBeerListTextFilteredIssues, new SimpleBeerTableFormat());
        final customTable currentDraftBeerListJTable = new customTable(currentBeerListTableModel);
        currentDraftBeerListJTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        TableComparatorChooser.install(currentDraftBeerListJTable, beerList_Current.getSortedList(),
                TableComparatorChooser.MULTIPLE_COLUMN_MOUSE);


        panel.setLayout(new GridBagLayout());

        customScrollPane beerMasterListScrollPane = new customScrollPane(beerMasterListJTable);
        customScrollPane currentBeerListScrollPane = new customScrollPane(currentDraftBeerListJTable);

        customButton createNewDraftBeerButton = new customButton("New");
        final customButton addDraftBeerToCurrentList = new customButton("Add To List");
        final customButton removeDraftBeerFromCurrentList = new customButton("86 From List");
        final customButton printList = new customButton("Print");
        final customButton editBottledBeer = new customButton("Edit");

        panel.add(new JLabel("All Beer:"), new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER,
                GridBagConstraints.BOTH, leftComponentInsets, 0, 0));
        panel.add(new JLabel("Current List: "), new GridBagConstraints(2, 0, 2, 1, 0.0, 0.0, GridBagConstraints
                .CENTER, GridBagConstraints.BOTH, rightComponentInsets, 0, 0));
        panel.add(new JLabel("Filter: "), new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
                GridBagConstraints.BOTH, leftComponentInsets, 0, 0));
        panel.add(beerMasterListFilterEdit, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
                GridBagConstraints.BOTH, leftComponentInsets, 0, 0));
        panel.add(new JLabel("Filter: "), new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
                GridBagConstraints.BOTH, rightComponentInsets, 0, 0));
        panel.add(currentBeerListFilterEdit, new GridBagConstraints(3, 1, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
                GridBagConstraints.BOTH, rightComponentInsets, 0, 0));

        panel.add(addDraftBeerToCurrentList, new GridBagConstraints(0, 2, 2, 1, 1.0, 0.0, GridBagConstraints.CENTER,
                GridBagConstraints.BOTH, leftComponentInsets, 0, 0));
        panel.add(removeDraftBeerFromCurrentList, new GridBagConstraints(2, 2, 2, 1, 1.0, 0.0, GridBagConstraints
                .CENTER, GridBagConstraints.BOTH, rightComponentInsets, 0, 0));

        panel.add(beerMasterListScrollPane, new GridBagConstraints(0, 3, 2, 1, 1.0, 1.0, GridBagConstraints.CENTER,
                GridBagConstraints.BOTH, leftComponentInsets, 0, 0));
        panel.add(currentBeerListScrollPane, new GridBagConstraints(2, 3, 2, 1, 1.0, 1.0, GridBagConstraints.CENTER,
                GridBagConstraints.BOTH, rightComponentInsets, 0, 0));

        panel.add(createNewDraftBeerButton, new GridBagConstraints(0, 4, 2, 1, 1, 0.0, GridBagConstraints.CENTER,
                GridBagConstraints.BOTH, leftComponentInsets, 0, 0));
        panel.add(editBottledBeer, new GridBagConstraints(0, 5, 2, 1, 1.0, 0.0, GridBagConstraints.CENTER,
                GridBagConstraints.BOTH, leftComponentInsets, 0, 0));
        panel.add(printList, new GridBagConstraints(2, 4, 2, 1, 1, 0.0, GridBagConstraints.CENTER, GridBagConstraints
                .BOTH, rightComponentInsets, 0, 0));


        createNewDraftBeerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                new Beer().createNewBottledBeer(beerList_Master, beerList_Current);

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

                beerList_Current.addBeer(beerMasterListTableModel.getElementAt(beerMasterListJTable.getSelectedRow()));

            }
        });

        removeDraftBeerFromCurrentList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                beerList_Current.removeBeer(currentBeerListTableModel.getElementAt(currentDraftBeerListJTable
                        .getSelectedRow()));

            }
        });

        printList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Beer().printBeerList(current_draftBeerList, current_bottledBeerList);
            }
        });

        editBottledBeer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new Beer().editBottledBeer(beerMasterListTableModel.getElementAt(beerMasterListJTable
                            .getSelectedRow()), beerList_Master, beerList_Current);

                } catch (IndexOutOfBoundsException e1) {

                    try {
                        new Beer().editBottledBeer(currentBeerListTableModel.getElementAt(currentDraftBeerListJTable
                                .getSelectedRow()), beerList_Master, beerList_Current);
                    } catch (IndexOutOfBoundsException e2) {

                    }

                }

            }


        });


        return panel;
    }

    //Ice Cream & Sorbet card and panels
    public static JPanel iceCreamCard() {

        final String ICECREAM = "Ice Cream";
        final String SORBET = "Sorbet";
        String[] choices = {ICECREAM, SORBET};
        String heading = "Please select flavor type:";
        final CardLayout cardLayout = new CardLayout();
        final JPanel cards = new JPanel(cardLayout);

        final dropboxJPanel dropboxJPanel = new dropboxJPanel(choices, heading);
        cards.add(iceCream_JPanel(master_iceCreamList, current_iceCreamList), ICECREAM);
        cards.add(sorbet_JPanel(master_sorbetList, current_sorbetList), SORBET);

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
                    case ICECREAM:
                        cardLayout.show(cards, ICECREAM);
                        break;
                    case SORBET:
                        cardLayout.show(cards, SORBET);
                        break;
                }
            }
        });

        return panel;

    }

    public static rightContentJPanel iceCream_JPanel(final flavorList master_flavorList, final flavorList
            current_flavorList) {

        rightContentJPanel panel = new rightContentJPanel();

        Border paddingBorder = BorderFactory.createEmptyBorder(5, 30, 30, 30);
        panel.setBorder(paddingBorder);

        //Left table for masterflavorlist
        JTextField master_flavorListTextEdit = new JTextField(10);
        final FilterList<Flavor> master_flavorListTextSortedIssues = new FilterList<>(master_flavorList.getSortedList(),
                new TextComponentMatcherEditor<>(master_flavorListTextEdit, new FlavorTextFilter()));
        final AdvancedTableModel<Flavor> master_flavorListTableModel = GlazedListsSwing
                .eventTableModelWithThreadProxyList
                        (master_flavorListTextSortedIssues, new FlavorTableFormat());
        final customTable master_flavorListJTable = new customTable(master_flavorListTableModel);
        master_flavorListJTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        TableComparatorChooser.install(master_flavorListJTable, master_flavorList.getSortedList(),
                TableComparatorChooser
                .MULTIPLE_COLUMN_MOUSE);


        //Right table for current flavors
        JTextField current_flavorListFilterEdit = new JTextField(10);
        final FilterList<Flavor> current_flavorListTextSortedIssues = new FilterList<>(current_flavorList
                .getSortedList(),
                new TextComponentMatcherEditor<>(current_flavorListFilterEdit, new FlavorTextFilter()));
        final AdvancedTableModel<Flavor> current_flavorListTableModel = GlazedListsSwing
                .eventTableModelWithThreadProxyList(current_flavorListTextSortedIssues, new FlavorTableFormat());
        final customTable current_flavorListJTable = new customTable(current_flavorListTableModel);
        current_flavorListJTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        TableComparatorChooser.install(current_flavorListJTable, current_flavorList.getSortedList(),
                TableComparatorChooser.MULTIPLE_COLUMN_MOUSE);


        current_flavorListJTable.getTableHeader().setOpaque(false);


        panel.setLayout(new GridBagLayout());

        customScrollPane master_flavorList_scrollPane = new customScrollPane(master_flavorListJTable);
        customScrollPane current_flavorList_scrollPane = new customScrollPane(current_flavorListJTable);


        customButton createNewFlavor_Button = new customButton("New");
        final customButton addFlavorToCurrentList_Button = new customButton("Add To List");
        final customButton removeFlavorFromCurrentList_Button = new customButton("86 From List");
        final customButton printList = new customButton("Print");
        final customButton editFlavor = new customButton("Edit");


        panel.add(new JLabel("All Flavors:"), new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER,
                GridBagConstraints.BOTH, leftComponentInsets, 0, 0));
        panel.add(new JLabel("Current List: "), new GridBagConstraints(2, 0, 2, 1, 0.0, 0.0, GridBagConstraints
                .CENTER, GridBagConstraints.BOTH, rightComponentInsets, 0, 0));
        panel.add(new JLabel("Filter: "), new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
                GridBagConstraints.BOTH, leftComponentInsets, 0, 0));
        panel.add(master_flavorListTextEdit, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
                GridBagConstraints.BOTH, leftComponentInsets, 0, 0));
        panel.add(new JLabel("Filter: "), new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
                GridBagConstraints.BOTH, rightComponentInsets, 0, 0));
        panel.add(current_flavorListFilterEdit, new GridBagConstraints(3, 1, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
                GridBagConstraints.BOTH, rightComponentInsets, 0, 0));

        panel.add(addFlavorToCurrentList_Button, new GridBagConstraints(0, 2, 2, 1, 1.0, 0.0, GridBagConstraints.CENTER,
                GridBagConstraints.BOTH, leftComponentInsets, 0, 0));
        panel.add(removeFlavorFromCurrentList_Button, new GridBagConstraints(2, 2, 2, 1, 1.0, 0.0, GridBagConstraints
                .CENTER, GridBagConstraints.BOTH, rightComponentInsets, 0, 0));

        panel.add(master_flavorList_scrollPane, new GridBagConstraints(0, 3, 2, 1, 1.0, 1.0, GridBagConstraints.CENTER,
                GridBagConstraints.BOTH, leftComponentInsets, 0, 0));
        panel.add(current_flavorList_scrollPane, new GridBagConstraints(2, 3, 2, 1, 1.0, 1.0, GridBagConstraints.CENTER,
                GridBagConstraints.BOTH, rightComponentInsets, 0, 0));

        panel.add(createNewFlavor_Button, new GridBagConstraints(0, 4, 2, 1, 1, 0.0, GridBagConstraints.CENTER,
                GridBagConstraints.BOTH, leftComponentInsets, 0, 0));
        panel.add(editFlavor, new GridBagConstraints(0, 5, 2, 1, 1.0, 0.0, GridBagConstraints.CENTER,
                GridBagConstraints.BOTH, leftComponentInsets, 0, 0));
        panel.add(printList, new GridBagConstraints(2, 4, 2, 1, 1, 0.0, GridBagConstraints.CENTER, GridBagConstraints
                .BOTH, rightComponentInsets, 0, 0));


        createNewFlavor_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                new Flavor().createNewFlavor(master_flavorList, current_flavorList);

            }
        });

        //if something is selected in current list, deselect it
        master_flavorListJTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (current_flavorListJTable.getSelectedRow() > -1) {
                    current_flavorListJTable.clearSelection();
                }
            }
        });

        //if something is selected in the master list, deselect it
        current_flavorListJTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (master_flavorListJTable.getSelectedRow() > -1) {
                    master_flavorListJTable.clearSelection();
                }
            }
        });

        addFlavorToCurrentList_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                current_flavorList.addFlavor(master_flavorListTableModel.getElementAt(master_flavorListJTable
                        .getSelectedRow()));

            }
        });

        removeFlavorFromCurrentList_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                current_flavorList.removeFlavor(current_flavorListTableModel.getElementAt(current_flavorListJTable
                        .getSelectedRow()));

            }
        });

        printList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        editFlavor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new Flavor().editFlavor(master_flavorListTableModel.getElementAt(master_flavorListJTable
                            .getSelectedRow()), master_flavorList, current_flavorList);

                } catch (IndexOutOfBoundsException e1) {

                    try {
                        new Flavor().editFlavor(current_flavorListTableModel.getElementAt(current_flavorListJTable
                                .getSelectedRow()), master_flavorList, current_flavorList);
                    } catch (IndexOutOfBoundsException e2) {

                    }

                }

            }


        });


        return panel;
    }




    public static JLabel leftMenuJLabel(String name) {

        JLabel button = new JLabel(name);
        Border paddingBorder = BorderFactory.createEmptyBorder(10, 30, 10, 10);
        button.setBorder(paddingBorder);
        button.setBackground(two);
        button.setForeground(one);
        button.setOpaque(true);

        return button;
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        //fileSetup();
        fileSetup();
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    public static void fileSetup() {

        //check if directory is setup, if not, make one. (mkdir() does both)
        new File(HOME_DIR).mkdir();
        new File(HOME_DIR + System.getProperty("file.separator") + "XML").mkdir();
        new File(HOME_DIR + System.getProperty("file.separator") + "HTML").mkdir();
        new File(HOME_DIR + System.getProperty("file.separator") + "Data").mkdir();

        try {
            password = FileUtils.readFileToString(new File(HOME_DIR + "\\Data\\hamsandwich.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static class dropboxJPanel extends JPanel {

        public JComboBox<String> dropbox;

        public dropboxJPanel(String[] choices, String heading) {

            Border paddingBorder = BorderFactory.createEmptyBorder(30, 30, 5, 30);
            this.setBorder(paddingBorder);
            this.setBackground(one);

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

    public static class rightContentJPanel extends JPanel {

        public rightContentJPanel() {
            setBackground(one);
        }
    }


}
