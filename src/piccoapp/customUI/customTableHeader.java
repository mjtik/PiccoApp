package piccoapp.customUI;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 * Created by mtiko_000 on 6/2/2015.
 */
public class customTableHeader extends JLabel implements TableCellRenderer {

    public customTableHeader() {
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {
        setText(value.toString());

        return this;
    }
}
