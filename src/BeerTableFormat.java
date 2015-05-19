import ca.odell.glazedlists.gui.TableFormat;

/**
 * Created by mtiko_000 on 4/22/2015.
 */
public class BeerTableFormat implements TableFormat<Beer> {

    public int getColumnCount() {
        return 6;
    }

    public String getColumnName(int column) {
        if(column == 0)      return "Name";
        else if(column == 1) return "Style";
        else if(column == 2) return "abv";
        else if(column == 3) return "Brewery";
        else if(column == 4) return "Location";
        else if(column == 5) return "Price";

        throw new IllegalStateException();
    }

    public Object getColumnValue(Beer beer, int column) {


        if(column == 0)      return beer.getName();
        else if(column == 1) return beer.getStyle();
        else if(column == 2) return beer.getAbvString();
        else if(column == 3) return beer.getBrewery();
        else if(column == 4) return beer.getLocation();
        else if(column == 5) return beer.getPrice();
        throw new IllegalStateException();
    }
}
