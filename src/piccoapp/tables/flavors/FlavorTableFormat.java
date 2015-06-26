package piccoapp.tables.flavors; /**
 * Created by mtiko_000 on 4/27/2015.
 */

import ca.odell.glazedlists.gui.TableFormat;
import piccoapp.menuItems.Flavor;

/**
 * Created by mtiko_000 on 4/22/2015.
 */
public class FlavorTableFormat implements TableFormat<Flavor> {

    public int getColumnCount() {
        return 1;
    }

    public String getColumnName(int column) {
        if (column == 0) return "Flavor";

        throw new IllegalStateException();
    }

    public Object getColumnValue(Flavor flavor, int column) {


        if (column == 0) return flavor.getFlavor();

        throw new IllegalStateException();
    }
}