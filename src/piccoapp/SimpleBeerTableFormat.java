package piccoapp; /**
 * Created by mtiko_000 on 4/27/2015.
 */

import ca.odell.glazedlists.gui.TableFormat;

/**
 * Created by mtiko_000 on 4/22/2015.
 */
public class SimpleBeerTableFormat implements TableFormat<Beer> {

    public int getColumnCount() {
        return 1;
    }

    public String getColumnName(int column) {
        if(column == 0)      return "Name";

        throw new IllegalStateException();
    }

    public Object getColumnValue(Beer beer, int column) {


        if(column == 0)      return beer.getName();

        throw new IllegalStateException();
    }
}