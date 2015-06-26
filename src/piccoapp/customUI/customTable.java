package piccoapp.customUI;

import piccoapp.MenuChanger;

import javax.swing.*;
import javax.swing.table.TableModel;

/**
 * Created by mtiko_000 on 6/2/2015.
 */
public class customTable extends JTable {
    public customTable(TableModel dm) {
        super(dm);
        this.setBackground(MenuChanger.one);
        this.setShowGrid(false);
        this.getTableHeader().setDefaultRenderer(new customTableHeader());
        this.setDefaultRenderer(Object.class, new customTableCell());
        this.setRowHeight(27);
    }
}
