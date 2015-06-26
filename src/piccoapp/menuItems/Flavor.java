package piccoapp.menuItems;

/**
 * Created by mtiko_000 on 6/26/2015.
 */
public class Flavor {

    String flavor;
    Boolean isSorbet = false;

    public void sorbet(String flavor) {

        this.flavor = flavor;
        isSorbet = true;

    }

    public void iceCream(String flavor) {
        this.flavor = flavor;
        isSorbet = false;

    }


    public String getFlavor() {
        return flavor;
    }
}
