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
import piccoapp.menuItems.Sorbet;

import java.io.File;
import java.io.IOException;
import java.util.List;


public class SorbetXMLParser {

    EventList<Sorbet> sorbetEventList = new BasicEventList<>();

    public SortedList<Sorbet> parseXML(String fileName) {

        SortedList<Sorbet> sortedSorbet = new SortedList<Sorbet>(sorbetEventList, null);

        SAXBuilder builder = new SAXBuilder();
        File file = new File(fileName);

        try {
            Document document = builder.build(file);
            Element rootNode = document.getRootElement();
            List<Element> sorbetList = rootNode.getChildren("flavor");

            for (int i = 0; i <= sorbetList.size() - 1; i++) {

                Element element = sorbetList.get(i);
                Sorbet sorbet = new Sorbet(element.getChildText("name"));
                sorbetEventList.add(sorbet);

            }


        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sortedSorbet;

    }


}
