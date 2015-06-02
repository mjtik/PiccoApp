package piccoapp;

import ca.odell.glazedlists.SortedList;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created by mtiko_000 on 5/31/2015.
 */
public class beerList {

    private SortedList<Beer> sortedList;
    private String filePath;

    beerList(String s) {
        filePath = s;
        sortedList = new BeerXMLParser().parseXML(filePath);

    }

    public SortedList<Beer> getSortedList() {
        return sortedList;
    }

    public void setSortedList(SortedList<Beer> sortedList) {
        this.sortedList = sortedList;
    }

    public void addBeer(Beer newBeer){

        sortedList.add(newBeer);
        XMLOutputter out = new XMLOutputter();

        try {
            Document doc = new SAXBuilder().build(filePath);

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

            FileWriter writer = new FileWriter(filePath);
            out.setFormat(Format.getPrettyFormat());
            out.output(doc, writer);
            writer.close();

        } catch (JDOMException | IOException e) {
            e.printStackTrace();
        }


    }

    public void removeBeer(Beer beer){
        SAXBuilder builder = new SAXBuilder();
        String removeName = beer.getName();

        try {
            Document document = builder.build(filePath);
            Element rootNode = document.getRootElement();
            List<Element> beers = rootNode.getChildren("beer");

            for(int i=0;i<=beers.size()-1;i++) {

                Element element = beers.get(i);
                if (removeName.equals(element.getChildText("name"))){

                    beers.remove(i);
                }

            }

            XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
            outputter.output(document, new FileWriter(filePath));



        } catch (JDOMException | IOException e) {
            e.printStackTrace();
        }

        sortedList.remove(beer);

    }


}