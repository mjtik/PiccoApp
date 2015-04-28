/**
 * Created by mtiko_000 on 4/22/2015.
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ca.odell.glazedlists.impl.beans.BeanTextFilterator;
import javafx.collections.transformation.FilteredList;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import ca.odell.glazedlists.*;
import ca.odell.glazedlists.swing.*;

import javax.swing.*;

import static com.sun.java.accessibility.util.AWTEventMonitor.addMouseListener;
import static javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION;
import static javax.swing.ListSelectionModel.SINGLE_SELECTION;


public class XMLParser {

    Beer selectedBeer = new Beer();


    EventList <Beer> beerEventList = new BasicEventList<Beer>();
    SortedList<Beer> sortedBeer = new SortedList<Beer>(beerEventList,null);

    public void parseXML(){

        SAXBuilder builder = new SAXBuilder();
        File file = new File("beers.xml");

        try {
            Document document = builder.build(file);
            Element rootNode = document.getRootElement();
            List <Element> beerList = rootNode.getChildren("beer");

            for(int i=0;i<=beerList.size()-1;i++) {

                Beer beer = new Beer();
                Element element = beerList.get(i);

                beer.setName(element.getChildText("name"));
                beer.setStyle(element.getChildText("style"));
                beer.setAbv(element.getChildText("abv"));
                beer.setBrewery(element.getChildText("brewery"));
                beer.setLocation(element.getChildText("location"));
                beer.setPrice(element.getChildText("price"));

                beerEventList.add(beer);

            }



        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }

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
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(540, 380);
        frame.getContentPane().add(panel);
        frame.setVisible(true);


    }
}
