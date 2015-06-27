package piccoapp.lists;

import ca.odell.glazedlists.SortedList;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import piccoapp.XMLParsers.FlavorXMLParser;
import piccoapp.menuItems.Beer;
import piccoapp.menuItems.Flavor;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created by mtiko_000 on 5/31/2015.
 */
public class flavorList {

    private SortedList<Flavor> sortedList;
    private String filePath;

    flavorList(String s) {
        filePath = s;
        sortedList = new FlavorXMLParser().parseXML(filePath);

    }

    public SortedList<Flavor> getSortedList() {
        return sortedList;
    }

    public void setSortedList(SortedList<Flavor> sortedList) {
        this.sortedList = sortedList;
    }

    public void addFlavor(Flavor newFlavor) {

        sortedList.add(newFlavor);
        XMLOutputter out = new XMLOutputter();

        try {
            Document doc = new SAXBuilder().build(filePath);

            Element flavor = new Element("flavor");
            flavor.addContent(new Element("name").setText(newFlavor.getFlavor()));

            doc.getRootElement().addContent(flavor);

            FileWriter writer = new FileWriter(filePath);
            out.setFormat(Format.getPrettyFormat());
            out.output(doc, writer);
            writer.close();

        } catch (JDOMException | IOException e) {
            e.printStackTrace();
        }


    }

    public void removeBeer(Beer beer) {
        SAXBuilder builder = new SAXBuilder();
        String removeName = beer.getName();

        try {
            Document document = builder.build(filePath);
            Element rootNode = document.getRootElement();
            List<Element> beers = rootNode.getChildren("beer");

            for (int i = 0; i <= beers.size() - 1; i++) {

                Element element = beers.get(i);
                if (removeName.equals(element.getChildText("name"))) {

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