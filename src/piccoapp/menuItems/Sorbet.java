package piccoapp.menuItems;

/**
 * Created by mtiko_000 on 6/26/2015.
 */
public class Sorbet {

    static String flavor;

    public Sorbet(String flavor) {

        Sorbet.flavor = flavor;
    }

    public static String getFlavor() {
        return flavor;
    }
}
