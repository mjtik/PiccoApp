package piccoapp.menuItems;

import piccoapp.customUI.customButton;
import piccoapp.lists.flavorList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by mtiko_000 on 6/26/2015.
 */
public class Flavor {

    String flavor;

    public Flavor() {

    }

    public Flavor(String flavor) {
        this.flavor = flavor;
    }

    public void createNewFlavor(final flavorList master_flavorList, final flavorList current_flavorList) {

        final JTextField name = new JTextField(25);
        final JCheckBox addToCurrentListCheckBox = new JCheckBox("Add flavor to current list");

        customButton createNewFlavor_Button = new customButton("Create flavor");

        JPanel panel = new JPanel();
        GridBagLayout gbl = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        panel.setLayout(gbl);

        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.BOTH;

        panel.add(new JLabel("Name: "), gbc);

        gbc.gridx = 1;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        panel.add(name, gbc);
        panel.add(addToCurrentListCheckBox, gbc);


        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(createNewFlavor_Button, gbc);


        final JFrame frame = new JFrame("Create New Beer");
        frame.setSize(400, 500);
        frame.getContentPane().add(panel);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);

        createNewFlavor_Button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                Flavor newFlavor = new Flavor(name.getText());


                master_flavorList.addFlavor(newFlavor);
                if (addToCurrentListCheckBox.isSelected()) {
                    current_flavorList.addFlavor(newFlavor);
                }
                frame.setVisible(false);

            }
        });

    }

    public String getFlavor() {
        return flavor;
    }
}
