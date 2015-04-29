import org.jdom2.input.SAXBuilder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by mjtik on 4/22/2015.
 */
public class PiccoApp {

    public PiccoApp() {

        display();
    }

    public void display (){
        JLabel  header     = new JLabel("What menu are you changing?",SwingConstants.CENTER);
        JButton changeBeer = new JButton("Beer");
        JButton changeWine = new JButton("Wine");
        JButton changeFood = new JButton("Food");

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        panel.add(header,     new GridBagConstraints(0,0,3,1,0.0,0.0,GridBagConstraints.CENTER,GridBagConstraints.BOTH,new Insets(5,5,5,5),0,0));
        panel.add(changeBeer, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        panel.add(changeWine, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        panel.add(changeFood, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        frame.getContentPane().add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        changeFood.setEnabled(false);
        changeWine.setEnabled(false);

        changeBeer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                new BeerChanger();

            }
        });

    }

    public static void main(String [] args){

        new PiccoApp();


        //testing purposes
        new BeerChanger();
    }

}
