/**
 * Created by mtiko_000 on 4/22/2015.
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



public class BeerXMLParser {

    Beer selectedBeer = new Beer();

    EventList <Beer> beerEventList = new BasicEventList<Beer>();


    public SortedList<Beer> parseXML(){

        SortedList<Beer> sortedBeer = new SortedList<Beer>(beerEventList,null);

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

        return sortedBeer;

    }


}
