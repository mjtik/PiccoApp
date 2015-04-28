/**
 * Created by mtiko_000 on 4/27/2015.
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.List;


import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import ca.odell.glazedlists.*;
import ca.odell.glazedlists.swing.*;

import javax.swing.*;

public class BeerChanger {

    SortedList<Beer> sortedBeer = new BeerXMLParser().parseXML();
    Beer selectedBeer = new Beer();

    public void display(){

        JTextField filterEdit = new JTextField(10);
        FilterList<Beer> textFilteredIssues = new FilterList<Beer>(sortedBeer, new TextComponentMatcherEditor<Beer>(filterEdit, new BeerTextFilter()));
        AdvancedTableModel<Beer> beerTableModel = GlazedListsSwing.eventTableModelWithThreadProxyList(textFilteredIssues, new SimpleBeerTableFormat());
        final JTable beerJTable = new JTable(beerTableModel);
        JButton print = new JButton("Print");



        print.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedBeer = sortedBeer.get(beerJTable.getSelectedRow());
                System.out.println(selectedBeer.toString());
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());


        TableComparatorChooser<Beer> tableSorter = TableComparatorChooser.install(beerJTable, sortedBeer, TableComparatorChooser.MULTIPLE_COLUMN_MOUSE);
        JScrollPane beerListScrollPane = new JScrollPane(beerJTable);
        panel.add(new JLabel("Filter: "), new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        panel.add(filterEdit,             new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        panel.add(print,                  new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        panel.add(beerListScrollPane,     new GridBagConstraints(0, 1, 3, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));


        JFrame frame = new JFrame("Beers");
        frame.setSize(540, 380);
        frame.getContentPane().add(panel);
        frame.setVisible(true);


    }

}
