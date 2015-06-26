package piccoapp.tables.sorbet;

import ca.odell.glazedlists.TextFilterator;
import piccoapp.menuItems.Sorbet;

import java.util.List;

/**
 * Created by mtiko_000 on 4/22/2015.
 */
public class SorbetTextFilter implements TextFilterator<Sorbet> {

    public void getFilterStrings(List<String> baseList, Sorbet sorbet) {
        baseList.add(Sorbet.getFlavor());
    }


}
