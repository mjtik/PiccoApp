/**
 * Created by mtiko_000 on 4/27/2015.
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import ca.odell.glazedlists.*;
import ca.odell.glazedlists.swing.*;

import javax.swing.*;

public class BeerChanger {

    SortedList<Beer> AllBeer = new BeerXMLParser().parseXML("BeerMasterList.xml");
    SortedList<Beer> CurrentBeer = new BeerXMLParser().parseXML("CurrentBeerList.xml");

    Beer selectedBeer = new Beer();

    public void display(){

        //Left table for BeerMasterList
        JTextField beerMasterListFilterEdit = new JTextField(10);
        FilterList<Beer> beerMasterListTextFilteredIssues = new FilterList<Beer>(AllBeer, new TextComponentMatcherEditor<Beer>(beerMasterListFilterEdit, new BeerTextFilter()));
        AdvancedTableModel<Beer> beerMasterListTableModel = GlazedListsSwing.eventTableModelWithThreadProxyList(beerMasterListTextFilteredIssues, new SimpleBeerTableFormat());
        final JTable beerMasterListJTable = new JTable(beerMasterListTableModel);

        //Right table for CurrentBeerList
        JTextField currentBeerListFilterEdit = new JTextField(10);
        FilterList<Beer> currentBeerListTextFilteredIssues = new FilterList<Beer>(AllBeer, new TextComponentMatcherEditor<Beer>(currentBeerListFilterEdit, new BeerTextFilter()));
        AdvancedTableModel<Beer> currentBeerListTableModel = GlazedListsSwing.eventTableModelWithThreadProxyList(currentBeerListTextFilteredIssues, new SimpleBeerTableFormat());
        final JTable currentBeerListJTable = new JTable(currentBeerListTableModel);


        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        JScrollPane beerMasterListScrollPane = new JScrollPane(beerMasterListJTable);
        JScrollPane currentBeerListScrollPane = new JScrollPane(currentBeerListJTable);

        JButton createNewBeer = new JButton("Create New Beer");
        JButton addBeerToCurrentList = new JButton("Add To List");
        JButton removeBeerFromCurrentList = new JButton("86 From List");

        panel.add(new JLabel("All Beer:"),      new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        panel.add(new JLabel("Current List: "), new GridBagConstraints(2, 0, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));

        panel.add(new JLabel("Filter: "),       new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        panel.add(beerMasterListFilterEdit,     new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        panel.add(new JLabel("Filter: "),       new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        panel.add(currentBeerListFilterEdit,    new GridBagConstraints(3, 1, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));

        panel.add(addBeerToCurrentList,         new GridBagConstraints(0, 2, 2, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        panel.add(removeBeerFromCurrentList,    new GridBagConstraints(2, 2, 2, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));

        panel.add(beerMasterListScrollPane,     new GridBagConstraints(0, 3, 2, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        panel.add(currentBeerListScrollPane,    new GridBagConstraints(2, 3, 2, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));

        panel.add(createNewBeer,                new GridBagConstraints(0, 4, 2, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));



        JFrame frame = new JFrame("Beers");
        frame.setSize(540, 380);
        frame.getContentPane().add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);






       /* print.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedBeer = AllBeer.get(beerJTable.getSelectedRow());
                System.out.println(selectedBeer.toString());
            }
        });*/


    }



}
