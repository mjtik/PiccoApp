import ca.odell.glazedlists.TextFilterator;

import java.util.List;

/**
 * Created by mtiko_000 on 4/22/2015.
 */
public class BeerTextFilter implements TextFilterator<Beer>{

    public void getFilterStrings(List<String> baseList, Beer beer) {
        baseList.add(beer.getName());
        baseList.add(beer.getStyle());
        baseList.add(beer.getAbv());
        baseList.add(beer.getBrewery());
        baseList.add(beer.getLocation());
        baseList.add(beer.getPrice());
    }


}
