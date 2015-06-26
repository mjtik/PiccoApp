package piccoapp.tables.sorbet; /**
 * Created by mtiko_000 on 4/27/2015.
 */

import ca.odell.glazedlists.gui.TableFormat;
import piccoapp.menuItems.IceCream;

/**
 * Created by mtiko_000 on 4/22/2015.
 */
public class IceCreamTableFormat implements TableFormat<IceCream> {

    public int getColumnCount() {
        return 1;
    }

    public String getColumnName(int column) {
        if (column == 0) return "Flavor";

        throw new IllegalStateException();
    }

    public Object getColumnValue(IceCream iceCream, int column) {


        if (column == 0) return iceCream.getFlavor();

        throw new IllegalStateException();
    }
}