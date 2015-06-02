package piccoapp;

import javax.swing.*;
import java.awt.*;

/**
 * Created by mtiko_000 on 6/1/2015.
 */
public class customScrollPane extends JScrollPane {

    public customScrollPane(Component view) {
        super(view);
        this.setBackground(MenuChanger.one);
        this.setForeground(Color.blue);

    }
}
