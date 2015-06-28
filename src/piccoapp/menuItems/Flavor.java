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

    public void editFlavor(final Flavor f, final flavorList master_flavorList, final flavorList current_flavorList) {

        final Flavor flavor = f;

        final JTextField name = new JTextField(25);
        name.setText(flavor.getFlavor());

        customButton save_Button = new customButton("Save");
        customButton delete_Button = new customButton("Delete");


        JPanel panel = new JPanel();
        GridBagLayout gbl = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        panel.setLayout(gbl);

        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(5, 20, 5, 5);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.BOTH;

        panel.add(new JLabel("Name: "), gbc);

        gbc.gridx = 1;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets = new Insets(5, 5, 5, 20);

        panel.add(name, gbc);


        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 1;
        gbc.gridy = 10;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        panel.add(save_Button, gbc);

        gbc.gridx = 2;
        gbc.gridy = 10;
        gbc.insets = new Insets(5, 5, 5, 20);
        panel.add(delete_Button, gbc);


        final JFrame frame = new JFrame("Edit Flavor");
        frame.setSize(400, 500);
        frame.getContentPane().add(panel);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);

        save_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Flavor newFlavor = new Flavor(name.getText());

                //search for previous versions of the beer in master and current list and remove them and add the new
                // beer
                for (int i = 0; i < master_flavorList.getSortedList().size(); i++) {

                    if (newFlavor.getFlavor().equals(master_flavorList.getSortedList().get(i).getFlavor())) {

                        master_flavorList.removeFlavor(master_flavorList.getSortedList().get(i));
                        master_flavorList.addFlavor(newFlavor);

                    }
                }

                for (int i = 0; i < current_flavorList.getSortedList().size(); i++) {

                    if (flavor.getFlavor().equals(current_flavorList.getSortedList().get(i).getFlavor())) {

                        current_flavorList.removeFlavor(current_flavorList.getSortedList().get(i));
                        current_flavorList.addFlavor(newFlavor);

                    }
                }

                frame.setVisible(false);

            }
        });

        delete_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //confirm delete
                int response = JOptionPane.showConfirmDialog(null, "Do you want to delete " + flavor.getFlavor() + " " +
                        "from the master list? \r\n (you cannot undo)", "Confirm", JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                switch (response) {
                    case JOptionPane.NO_OPTION:
                        break;

                    case JOptionPane.YES_OPTION:

                        //search for versions of the beer in master and current list and remove them
                        for (int i = 0; i < master_flavorList.getSortedList().size(); i++) {

                            if (flavor.getFlavor().equals(master_flavorList.getSortedList().get(i).getFlavor())) {

                                master_flavorList.removeFlavor(master_flavorList.getSortedList().get(i));

                            }
                        }

                        for (int i = 0; i < current_flavorList.getSortedList().size(); i++) {

                            if (flavor.getFlavor().equals(current_flavorList.getSortedList().get(i).getFlavor())) {

                                current_flavorList.removeFlavor(current_flavorList.getSortedList().get(i));

                            }
                        }

                        break;

                }


                frame.setVisible(false);
            }
        });
    }

    public String getFlavor() {
        return flavor;
    }
}
