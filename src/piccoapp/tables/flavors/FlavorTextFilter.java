package piccoapp.tables.flavors;

import ca.odell.glazedlists.TextFilterator;
import piccoapp.menuItems.Flavor;

import java.util.List;

/**
 * Created by mtiko_000 on 4/22/2015.
 */
public class FlavorTextFilter implements TextFilterator<Flavor> {

    public void getFilterStrings(List<String> baseList, Flavor flavor) {
        baseList.add(flavor.getFlavor());
    }


}
