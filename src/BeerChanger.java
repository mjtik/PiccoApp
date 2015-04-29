/**
 * Created by mtiko_000 on 4/27/2015.
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.List;


import ca.odell.glazedlists.*;
import ca.odell.glazedlists.swing.*;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import javax.swing.*;

public class BeerChanger {

    SortedList<Beer> BeerMasterSortedList = new BeerXMLParser().parseXML("BeerMasterList.xml");
    SortedList<Beer> currentBeerSortedList = new BeerXMLParser().parseXML("CurrentBeerList.xml");

    Beer selectedBeer = new Beer();


    public BeerChanger() {

        display();
    }

    public void display(){

        //Left table for BeerMasterList
        JTextField beerMasterListFilterEdit = new JTextField(10);
        FilterList<Beer> beerMasterListTextFilteredIssues = new FilterList<Beer>(BeerMasterSortedList, new TextComponentMatcherEditor<Beer>(beerMasterListFilterEdit, new BeerTextFilter()));
        AdvancedTableModel<Beer> beerMasterListTableModel = GlazedListsSwing.eventTableModelWithThreadProxyList(beerMasterListTextFilteredIssues, new BeerTableFormat());
        final JTable beerMasterListJTable = new JTable(beerMasterListTableModel);

        //Right table for CurrentBeerList
        JTextField currentBeerListFilterEdit = new JTextField(10);
        FilterList<Beer> currentBeerListTextFilteredIssues = new FilterList<Beer>(currentBeerSortedList, new TextComponentMatcherEditor<Beer>(currentBeerListFilterEdit, new BeerTextFilter()));
        AdvancedTableModel<Beer> currentBeerListTableModel = GlazedListsSwing.eventTableModelWithThreadProxyList(currentBeerListTextFilteredIssues, new BeerTableFormat());
        final JTable currentBeerListJTable = new JTable(currentBeerListTableModel);


        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        JScrollPane beerMasterListScrollPane = new JScrollPane(beerMasterListJTable);
        JScrollPane currentBeerListScrollPane = new JScrollPane(currentBeerListJTable);

        JButton createNewBeerButton = new JButton("Create New Beer");
        final JButton addBeerToCurrentList = new JButton("Add To List");
        final JButton removeBeerFromCurrentList = new JButton("86 From List");
        final JButton printList = new JButton("Print List");
        final JButton editBeer = new JButton("Edit Beer");

        panel.add(new JLabel("All Beer:"),      new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        panel.add(new JLabel("Current List: "), new GridBagConstraints(2, 0, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));

        panel.add(new JLabel("Filter: "),       new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        panel.add(beerMasterListFilterEdit,     new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        panel.add(new JLabel("Filter: "),       new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        panel.add(currentBeerListFilterEdit,    new GridBagConstraints(3, 1, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));

        panel.add(addBeerToCurrentList,         new GridBagConstraints(0, 2, 2, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        panel.add(removeBeerFromCurrentList,    new GridBagConstraints(2, 2, 2, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));

        panel.add(beerMasterListScrollPane, new GridBagConstraints(0, 3, 2, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        panel.add(currentBeerListScrollPane, new GridBagConstraints(2, 3, 2, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));

        panel.add(createNewBeerButton, new GridBagConstraints(0, 4, 2, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        panel.add(editBeer, new GridBagConstraints(0, 5, 2, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        panel.add(printList, new GridBagConstraints(2, 5, 2, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));


        createNewBeerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createNewBeer();
            }
        });

        addBeerToCurrentList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedBeer = BeerMasterSortedList.get(beerMasterListJTable.getSelectedRow());
                addBeerToCurrentList(selectedBeer);
            }
        });

        removeBeerFromCurrentList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedBeer = currentBeerSortedList.get(currentBeerListJTable.getSelectedRow());
                currentBeerSortedList.remove(selectedBeer);
                removeBeerFromCurrentList(selectedBeer);

            }
        });

        printList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                printList();
            }
        });


        JFrame frame = new JFrame("Beer Menu Changer");
        frame.setSize(540, 380);
        frame.getContentPane().add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);





       /* print.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedBeer = BeerMasterSortedList.get(beerJTable.getSelectedRow());
                System.out.println(selectedBeer.toString());
            }
        });*/


    }

    void printList(){

        final String indiaPaleAles = "India Pale Ales";
        final String paleAles = "Pale Ales";
        final String otherAles = "Other Ales";
        final String lagers = "Lagers";
        final String belgianStyle = "Belgian Style";
        final String dark = "Dark";
        final String cider = "Cider";

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


        for(int i=0;i<=currentBeerSortedList.size()-1;i++){

                Beer beer = currentBeerSortedList.get(i);
                if (beer.getCategory().equals(indiaPaleAles)){

                    if (indiaPaleAlesBuilder.length()==0){
                        indiaPaleAlesBuilder.append("<p class=\"beerStyleHeader\">India Pale Ales</p>");
                    }

                    indiaPaleAlesBuilder.append(System.lineSeparator());
                    indiaPaleAlesBuilder.append("<p class=\"beerName\">" + beer.getName() + "</p>");
                    indiaPaleAlesBuilder.append("<p1 class=\"beerStyle\">" + beer.getStyle() + "</p1>");
                    indiaPaleAlesBuilder.append("<p class=\"abv\">" + beer.getAbv() + "%</p>");
                    indiaPaleAlesBuilder.append("<p1 class=\"brewery\">" + beer.getBrewery() + "</p1>");
                    indiaPaleAlesBuilder.append("<p class=\"location\">" + beer.getLocation() + "</p>");
                    indiaPaleAlesBuilder.append("<p class=\"price\">$" + beer.getPrice() + "</p>");

                }else if (beer.getCategory().equals(paleAles)){

                    if (paleAlesBuilder.length()==0){
                        paleAlesBuilder.append("<p class=\"beerStyleHeader\">Pale Ales</p>");
                    }

                    paleAlesBuilder.append("<p class=\"beerName\">" + beer.getName() + "</p>");
                    paleAlesBuilder.append("<p1 class=\"beerStyle\">" + beer.getStyle() + "</p1>");
                    paleAlesBuilder.append("<p class=\"abv\">" + beer.getAbv() + "%</p>");
                    paleAlesBuilder.append("<p1 class=\"brewery\">" + beer.getBrewery() + "</p1>");
                    paleAlesBuilder.append("<p class=\"location\">" + beer.getLocation() + "</p>");
                    paleAlesBuilder.append("<p class=\"price\">$" + beer.getPrice() + "</p>");

                }else if (beer.getCategory().equals(otherAles)){

                    if (otherAlesBuilder.length()==0){
                        otherAlesBuilder.append("<p class=\"beerStyleHeader\">Other Ales</p>");
                    }

                    otherAlesBuilder.append("<p class=\"beerName\">" + beer.getName() + "</p>");
                    otherAlesBuilder.append("<p1 class=\"beerStyle\">" + beer.getStyle() + "</p1>");
                    otherAlesBuilder.append("<p class=\"abv\">" + beer.getAbv() + "%</p>");
                    otherAlesBuilder.append("<p1 class=\"brewery\">" + beer.getBrewery() + "</p1>");
                    otherAlesBuilder.append("<p class=\"location\">" + beer.getLocation() + "</p>");
                    otherAlesBuilder.append("<p class=\"price\">$" + beer.getPrice() + "</p>");

                }else if (beer.getCategory().equals(lagers)){

                    if (lagersBuilder.length()==0){
                        lagersBuilder.append("<p class=\"beerStyleHeader\">Lagers</p>");
                    }

                    lagersBuilder.append("<p class=\"beerName\">" + beer.getName() + "</p>");
                    lagersBuilder.append("<p1 class=\"beerStyle\">" + beer.getStyle() + "</p1>");
                    lagersBuilder.append("<p class=\"abv\">" + beer.getAbv() + "%</p>");
                    lagersBuilder.append("<p1 class=\"brewery\">" + beer.getBrewery() + "</p1>");
                    lagersBuilder.append("<p class=\"location\">" + beer.getLocation() + "</p>");
                    lagersBuilder.append("<p class=\"price\">$" + beer.getPrice() + "</p>");

                }else if (beer.getCategory().equals(belgianStyle)){

                    if (belgianStyleBuilder.length()==0){
                        belgianStyleBuilder.append("<p class=\"beerStyleHeader\">Belgian Style</p>");
                    }

                    belgianStyleBuilder.append("<p class=\"beerName\">" + beer.getName() + "</p>");
                    belgianStyleBuilder.append("<p1 class=\"beerStyle\">" + beer.getStyle() + "</p1>");
                    belgianStyleBuilder.append("<p class=\"abv\">" + beer.getAbv() + "%</p>");
                    belgianStyleBuilder.append("<p1 class=\"brewery\">" + beer.getBrewery() + "</p1>");
                    belgianStyleBuilder.append("<p class=\"location\">" + beer.getLocation() + "</p>");
                    belgianStyleBuilder.append("<p class=\"price\">$" + beer.getPrice() + "</p>");

                }else if (beer.getCategory().equals(dark)){

                    if (darkBuilder.length()==0){
                        darkBuilder.append("<p class=\"beerStyleHeader\">Dark</p>");
                    }

                    darkBuilder.append("<p class=\"beerName\">" + beer.getName() + "</p>");
                    darkBuilder.append("<p1 class=\"beerStyle\">" + beer.getStyle() + "</p1>");
                    darkBuilder.append("<p class=\"abv\">" + beer.getAbv() + "%</p>");
                    darkBuilder.append("<p1 class=\"brewery\">" + beer.getBrewery() + "</p1>");
                    darkBuilder.append("<p class=\"location\">" + beer.getLocation() + "</p>");
                    darkBuilder.append("<p class=\"price\">$" + beer.getPrice() + "</p>");

                }else if (beer.getCategory().equals(cider)){

                    if (ciderBuilder.length()==0){
                        ciderBuilder.append("<p class=\"beerStyleHeader\">Cider</p>");
                    }

                    ciderBuilder.append("<p class=\"beerName\">" + beer.getName() + "</p>");
                    ciderBuilder.append("<p1 class=\"beerStyle\">" + beer.getStyle() + "</p1>");
                    ciderBuilder.append("<p class=\"abv\">" + beer.getAbv() + "%</p>");
                    ciderBuilder.append("<p1 class=\"brewery\">" + beer.getBrewery() + "</p1>");
                    ciderBuilder.append("<p class=\"location\">" + beer.getLocation() + "</p>");
                    ciderBuilder.append("<p class=\"price\">$" + beer.getPrice() + "</p>");

                }

        }

        indiaPaleAlesHTML = indiaPaleAlesBuilder.toString();
        paleAlesHTML = paleAlesBuilder.toString();
        otherAlesHTML = otherAlesBuilder.toString();
        lagersHTML = lagersBuilder.toString();
        belgianStyleHTML = belgianStyleBuilder.toString();
        darkHTML = darkBuilder.toString();
        ciderHTML = ciderBuilder.toString();

        headerHTML = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "    <head>\n" +
                "        <meta charset=\"utf-8\">\n" +
                "        <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge,chrome=1\">\n" +
                "        <link rel=\"stylesheet\" href=\"main.css\">\n" +
                "    </head>\n" +
                "    <body>\n" +
                "        \n" +
                "        <div class=\"leftSide\">";

        footerHTML = "</div></body></html>";


        File file = new File("html\\beerList.html");

        try {
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

    void editBeer(){


    }

    void createNewBeer(){

        final Beer newBeer = new Beer();

        final JTextField name = new JTextField(15);
        final JTextField style = new JTextField(15);
        final JTextField abv = new JTextField(15);
        final JTextField brewery = new JTextField(15);
        final JTextField location = new JTextField(15);
        final JTextField price = new JTextField(15);
        String [] categories = {"India Pale Ales", "Pale Ales", "Other Ales", "Lagers", "Belgian Style", "Dark", "Cider"};
        final JComboBox category = new JComboBox(categories);

        JButton createNewBeerButton = new JButton("Create Beer");

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

        panel.add(createNewBeerButton, new GridBagConstraints(0, 8, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));

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
                newBeer.setCategory(category.getSelectedItem().toString());
                addBeerToMasterList(newBeer);
                frame.setVisible(false);

            }
        });


    }

    void addBeerToMasterList(Beer newBeer){

        BeerMasterSortedList.add(newBeer);

        File file = new File("BeerMasterList.xml");
        XMLOutputter out = new XMLOutputter();

        try {
            Document doc = new SAXBuilder().build(file);

            Element beer = new Element("beer");
            beer.addContent(new Element("name").setText(newBeer.getName()));
            beer.addContent(new Element("style").setText(newBeer.getStyle()));
            beer.addContent(new Element("abv").setText(newBeer.getAbv()));
            beer.addContent(new Element("brewery").setText(newBeer.getBrewery()));
            beer.addContent(new Element("location").setText(newBeer.getLocation()));
            beer.addContent(new Element("price").setText(newBeer.getPrice()));
            beer.addContent(new Element("category").setText(newBeer.getCategory()));

            doc.getRootElement().addContent(beer);

            FileWriter writer = new FileWriter(file);
            out.setFormat(Format.getPrettyFormat());
            out.output(doc, writer);
            writer.close();

        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    void addBeerToCurrentList(Beer newBeer){

        currentBeerSortedList.add(newBeer);

        File file = new File("CurrentBeerList.xml");
        XMLOutputter out = new XMLOutputter();

        try {
            Document doc = new SAXBuilder().build(file);

            Element beer = new Element("beer");
            beer.addContent(new Element("name").setText(newBeer.getName()));
            beer.addContent(new Element("style").setText(newBeer.getStyle()));
            beer.addContent(new Element("abv").setText(newBeer.getAbv()));
            beer.addContent(new Element("brewery").setText(newBeer.getBrewery()));
            beer.addContent(new Element("location").setText(newBeer.getLocation()));
            beer.addContent(new Element("price").setText(newBeer.getPrice()));
            beer.addContent(new Element("category").setText(newBeer.getCategory()));

            doc.getRootElement().addContent(beer);

            FileWriter writer = new FileWriter(file);
            out.setFormat(Format.getPrettyFormat());
            out.output(doc, writer);
            writer.close();

        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    void removeBeerFromCurrentList(Beer beer){

        SAXBuilder builder = new SAXBuilder();
        String removeName = beer.getName();

        try {
            Document document = builder.build("CurrentBeerList.xml");
            Element rootNode = document.getRootElement();
            List <Element> beers = rootNode.getChildren("beer");

            for(int i=0;i<=beers.size()-1;i++) {

                Element element = beers.get(i);
                if (removeName.equals(element.getChildText("name"))){

                    beers.remove(i);
                }

            }

            XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
            outputter.output(document, new FileWriter("CurrentBeerList.xml"));



        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
