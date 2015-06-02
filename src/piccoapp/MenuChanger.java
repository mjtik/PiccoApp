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
    //filepaths for xml files
    public static final String HOME_DIR = System.getProperty("user.home") + System.getProperty("file.separator") + "Picco App";
    //colors for UI
    public static final Color one = Color.WHITE;
    public static final Color two = Color.DARK_GRAY;
    public static final Color three = Color.LIGHT_GRAY;
    static final Color rightPanelColor = one;
    // insets for right panel components when two scroll lists are in view.
    // Keeps a little space in GBL in between sides.
    static final Insets rightComponentInsets = new Insets(5, 20, 5, 5);
    static final Insets leftComponentInsets = new Insets(5, 5, 5, 20);
    static final String DRAFT_BEER_MASTER_LIST_XML_FILEPATH = HOME_DIR + System.getProperty("file.separator") + "XML" + System.getProperty("file.separator") + "draftBeerMasterList.xml";
    static final String CURRENT_DRAFT_BEER_LIST_XML_FILEPATH = HOME_DIR + System.getProperty("file.separator") + "XML" + System.getProperty("file.separator") + "currentDraftBeerList.xml";
    static final beerList draftBeerList_Master = new beerList(DRAFT_BEER_MASTER_LIST_XML_FILEPATH);
    static final beerList draftBeerList_Current = new beerList(CURRENT_DRAFT_BEER_LIST_XML_FILEPATH);
    static final String BOTTLED_BEER_MASTER_LIST_XML_FILEPATH = HOME_DIR + System.getProperty("file.separator") + "XML" + System.getProperty("file.separator") + "bottledBeerMasterList.xml";
    static final String CURRENT_BOTTLED_BEER_LIST_XML_FILEPATH = HOME_DIR + System.getProperty("file.separator") + "XML" + System.getProperty("file.separator") + "currentBottledBeerList.xml";
    static final beerList bottledBeerList_Master = new beerList(BOTTLED_BEER_MASTER_LIST_XML_FILEPATH);
    static final beerList bottledBeerList_Current = new beerList(CURRENT_BOTTLED_BEER_LIST_XML_FILEPATH);
    static SortedList<Beer> bottledBeerMasterSortedList;
    static SortedList<Beer> currentBottledBeerSortedList;
    static Dimension rightPanelDimension = new Dimension(500, 400);
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

    public static JPanel beerCard() {

        final String DRAFTBEER = "Draft";
        final String BOTTLEDBEER = "Bottle/Can";
        String[] choices = {DRAFTBEER, BOTTLEDBEER};
        String heading = "Please select beer type:";
        final CardLayout cardLayout = new CardLayout();
        final JPanel cards = new JPanel(cardLayout);

        final dropboxJPanel dropboxJPanel = new dropboxJPanel(choices, heading);
        cards.add(draftBeer_JPanel(draftBeerList_Master, draftBeerList_Current), DRAFTBEER);
        cards.add(bottledBeer_JPanel(bottledBeerList_Master, bottledBeerList_Current), BOTTLEDBEER);

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
        final FilterList<Beer> beerMasterListTextFilteredIssues = new FilterList<>(beerList_Master.getSortedList(), new TextComponentMatcherEditor<>(beerMasterListFilterEdit, new BeerTextFilter()));
        final AdvancedTableModel<Beer> beerMasterListTableModel = GlazedListsSwing.eventTableModelWithThreadProxyList(beerMasterListTextFilteredIssues, new SimpleBeerTableFormat());
        final customTable beerMasterListJTable = new customTable(beerMasterListTableModel);
        beerMasterListJTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        TableComparatorChooser.install(beerMasterListJTable, beerList_Master.getSortedList(), TableComparatorChooser.MULTIPLE_COLUMN_MOUSE);



        //Right table for CurrentBeerList
        JTextField currentBeerListFilterEdit = new JTextField(10);
        final FilterList<Beer> currentBeerListTextFilteredIssues = new FilterList<>(beerList_Current.getSortedList(), new TextComponentMatcherEditor<>(currentBeerListFilterEdit, new BeerTextFilter()));
        final AdvancedTableModel<Beer> currentBeerListTableModel = GlazedListsSwing.eventTableModelWithThreadProxyList(currentBeerListTextFilteredIssues, new SimpleBeerTableFormat());
        final customTable currentDraftBeerListJTable = new customTable(currentBeerListTableModel);
        currentDraftBeerListJTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        TableComparatorChooser.install(currentDraftBeerListJTable, beerList_Current.getSortedList(), TableComparatorChooser.MULTIPLE_COLUMN_MOUSE);


        currentDraftBeerListJTable.getTableHeader().setOpaque(false);



        panel.setLayout(new GridBagLayout());

        customScrollPane beerMasterListScrollPane = new customScrollPane(beerMasterListJTable);
        customScrollPane currentBeerListScrollPane = new customScrollPane(currentDraftBeerListJTable);


        customButton createNewDraftBeerButton = new customButton("New");
        final customButton addDraftBeerToCurrentList = new customButton("Add To List");
        final customButton removeDraftBeerFromCurrentList = new customButton("86 From List");
        final customButton printList = new customButton("Print");
        final customButton editDraftBeer = new customButton("Edit");


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

                beerList_Current.removeBeer(currentBeerListTableModel.getElementAt(currentDraftBeerListJTable.getSelectedRow()));

            }
        });

        printList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Beer().printList(draftBeerList_Current, bottledBeerList_Current);
            }
        });

        editDraftBeer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new Beer().editDraftBeer(beerMasterListTableModel.getElementAt(beerMasterListJTable.getSelectedRow()), beerList_Master, beerList_Current);

                } catch (IndexOutOfBoundsException e1) {

                    try {
                        new Beer().editDraftBeer(currentBeerListTableModel.getElementAt(currentDraftBeerListJTable.getSelectedRow()), beerList_Master, beerList_Current);
                    } catch (IndexOutOfBoundsException e2) {

                    }

                }

            }


        });


        return panel;
    }

    public static rightContentJPanel bottledBeer_JPanel(final beerList beerList_Master, final beerList beerList_Current) {



        rightContentJPanel panel = new rightContentJPanel();

        Border paddingBorder = BorderFactory.createEmptyBorder(5, 30, 30, 30);
        panel.setBorder(paddingBorder);

        //Left table for BeerMasterList
        JTextField beerMasterListFilterEdit = new JTextField(10);
        final FilterList<Beer> beerMasterListTextFilteredIssues = new FilterList<>(beerList_Master.getSortedList(), new TextComponentMatcherEditor<>(beerMasterListFilterEdit, new BeerTextFilter()));
        final AdvancedTableModel<Beer> beerMasterListTableModel = GlazedListsSwing.eventTableModelWithThreadProxyList(beerMasterListTextFilteredIssues, new SimpleBeerTableFormat());
        final customTable beerMasterListJTable = new customTable(beerMasterListTableModel);
        beerMasterListJTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        TableComparatorChooser.install(beerMasterListJTable, beerList_Master.getSortedList(), TableComparatorChooser.MULTIPLE_COLUMN_MOUSE);


        //Right table for CurrentBeerList
        JTextField currentBeerListFilterEdit = new JTextField(10);
        final FilterList<Beer> currentBeerListTextFilteredIssues = new FilterList<>(beerList_Current.getSortedList(), new TextComponentMatcherEditor<>(currentBeerListFilterEdit, new BeerTextFilter()));
        final AdvancedTableModel<Beer> currentBeerListTableModel = GlazedListsSwing.eventTableModelWithThreadProxyList(currentBeerListTextFilteredIssues, new SimpleBeerTableFormat());
        final customTable currentDraftBeerListJTable = new customTable(currentBeerListTableModel);
        currentDraftBeerListJTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        TableComparatorChooser.install(currentDraftBeerListJTable, beerList_Current.getSortedList(), TableComparatorChooser.MULTIPLE_COLUMN_MOUSE);


        panel.setLayout(new GridBagLayout());

        customScrollPane beerMasterListScrollPane = new customScrollPane(beerMasterListJTable);
        customScrollPane currentBeerListScrollPane = new customScrollPane(currentDraftBeerListJTable);

        customButton createNewDraftBeerButton = new customButton("New");
        final customButton addDraftBeerToCurrentList = new customButton("Add To List");
        final customButton removeDraftBeerFromCurrentList = new customButton("86 From List");
        final customButton printList = new customButton("Print");
        final customButton editBottledBeer = new customButton("Edit");

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
        panel.add(editBottledBeer, new GridBagConstraints(0, 5, 2, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, leftComponentInsets, 0, 0));
        panel.add(printList, new GridBagConstraints(2, 4, 2, 1, 1, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, rightComponentInsets, 0, 0));


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

                beerList_Current.removeBeer(currentBeerListTableModel.getElementAt(currentDraftBeerListJTable.getSelectedRow()));

            }
        });

        printList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Beer().printList(draftBeerList_Current, beerList_Current);
            }
        });

        editBottledBeer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new Beer().editBottledBeer(beerMasterListTableModel.getElementAt(beerMasterListJTable.getSelectedRow()), beerList_Master, beerList_Current);

                } catch (IndexOutOfBoundsException e1) {

                    try {
                        new Beer().editBottledBeer(currentBeerListTableModel.getElementAt(currentDraftBeerListJTable.getSelectedRow()), beerList_Master, beerList_Current);
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
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    public void fileSetup() {

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
