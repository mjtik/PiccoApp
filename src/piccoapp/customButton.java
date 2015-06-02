package piccoapp;

import javax.swing.*;
import java.awt.*;

/**
 * Created by mtiko_000 on 6/1/2015.
 */
public class customButton extends JButton {
    public customButton(String s) {
        this.setBorderPainted(false);
        this.setBackground(MenuChanger.three);
        this.setForeground(MenuChanger.one);
        this.setFocusPainted(false);
        this.setForeground(Color.BLACK);
        this.setText(s);
    }
}
