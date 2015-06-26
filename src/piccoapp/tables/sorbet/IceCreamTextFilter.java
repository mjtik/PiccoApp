package piccoapp.tables.sorbet;

import ca.odell.glazedlists.TextFilterator;
import piccoapp.menuItems.IceCream;

import java.util.List;

/**
 * Created by mtiko_000 on 4/22/2015.
 */
public class IceCreamTextFilter implements TextFilterator<IceCream> {

    public void getFilterStrings(List<String> baseList, IceCream iceCream) {
        baseList.add(iceCream.getFlavor());
    }


}
