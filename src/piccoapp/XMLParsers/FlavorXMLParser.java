package piccoapp.XMLParsers; /**
 * Created by mtiko_000 on 4/22/2015.
 */

import ca.odell.glazedlists.BasicEventList;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.SortedList;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import piccoapp.menuItems.Flavor;

import java.io.File;
import java.io.IOException;
import java.util.List;


public class FlavorXMLParser {

    EventList<Flavor> flavorEvenList = new BasicEventList<>();

    public SortedList<Flavor> parseXML(String fileName) {

        SortedList<Flavor> sortedFlavor = new SortedList<Flavor>(flavorEvenList, null);

        SAXBuilder builder = new SAXBuilder();
        File file = new File(fileName);

        try {
            Document document = builder.build(file);
            Element rootNode = document.getRootElement();
            List<Element> flavorList = rootNode.getChildren("flavor");

            for (int i = 0; i <= flavorList.size() - 1; i++) {

                Element element = flavorList.get(i);
                Flavor flavor = new Flavor(element.getChildText("name"));
                flavorEvenList.add(flavor);

            }


        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sortedFlavor;

    }


}
