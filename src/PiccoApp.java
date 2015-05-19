import javax.swing.*;

/**
 * Created by mjtik on 4/22/2015.
 */
public class PiccoApp {

    public static void main(String [] args){

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        new BeerChanger();

    }

}
