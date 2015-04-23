import org.jdom2.input.SAXBuilder;

/**
 * Created by mjtik on 4/22/2015.
 */
public class PiccoApp {

    public static void main(String [] args){
        XMLParser parser = new XMLParser();

        parser.parseXML();
        parser.display();

    }

}
