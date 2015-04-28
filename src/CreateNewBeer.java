import javax.swing.*;
import java.awt.*;

/**
 * Created by mtiko_000 on 4/27/2015.
 */
public class CreateNewBeer {

    public void display(){

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        frame.getContentPane().add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

}
