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
import piccoapp.menuItems.IceCream;

import java.io.File;
import java.io.IOException;
import java.util.List;


public class IceCreamXMLParser {

    EventList<IceCream> iceCreamEventList = new BasicEventList<>();

    public SortedList<IceCream> parseXML(String fileName) {

        SortedList<IceCream> sortedIceCream = new SortedList<IceCream>(iceCreamEventList, null);

        SAXBuilder builder = new SAXBuilder();
        File file = new File(fileName);

        try {
            Document document = builder.build(file);
            Element rootNode = document.getRootElement();
            List<Element> iceCreamList = rootNode.getChildren("flavor");

            for (int i = 0; i <= iceCreamList.size() - 1; i++) {

                Element element = iceCreamList.get(i);
                IceCream iceCream = new IceCream(element.getChildText("name"));
                iceCreamEventList.add(iceCream);

            }


        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sortedIceCream;

    }


}
