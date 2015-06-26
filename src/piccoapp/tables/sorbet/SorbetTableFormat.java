package piccoapp.tables.sorbet; /**
 * Created by mtiko_000 on 4/27/2015.
 */

import ca.odell.glazedlists.gui.TableFormat;
import piccoapp.menuItems.Sorbet;

/**
 * Created by mtiko_000 on 4/22/2015.
 */
public class SorbetTableFormat implements TableFormat<Sorbet> {

    public int getColumnCount() {
        return 1;
    }

    public String getColumnName(int column) {
        if (column == 0) return "Flavor";

        throw new IllegalStateException();
    }

    public Object getColumnValue(Sorbet sorbet, int column) {


        if (column == 0) return Sorbet.getFlavor();

        throw new IllegalStateException();
    }
}