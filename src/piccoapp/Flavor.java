package piccoapp;

/**
 * Created by mtiko_000 on 6/26/2015.
 */
public class Flavor {

    String flavor;
    Boolean isSorbet = false;

    //constructor for ice cream
    public Flavor(String flavor) {

        this.flavor = flavor;

    }

    //constructor for sorbet
    public Flavor(String flavor, Boolean isSorbet) {

        this.flavor = flavor;
        this.isSorbet = isSorbet;

    }

    public String getFlavor() {
        return flavor;
    }

    public void setFlavor(String flavor) {
        this.flavor = flavor;
    }

    public Boolean getIsSorbet() {
        return isSorbet;
    }

    public void setIsSorbet(Boolean isSorbet) {
        this.isSorbet = isSorbet;
    }
}
