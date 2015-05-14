/**
 * Created by mtiko_000 on 4/22/2015.
 */
public class Beer {
    String price;
    String name;
    String style;
    String category;
    String abv;
    String brewery;
    String location;
    String size;
    String bottleType;

    public Beer() {
    }

    public String getBottleType() {
        return bottleType;
    }

    public void setBottleType(String bottleType) {
        this.bottleType = bottleType;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getPrice() {
        return this.price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStyle() {
        return this.style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAbv() {
        return this.abv;
    }

    public void setAbv(String abv) {
        this.abv = abv;
    }

    public String getBrewery() {
        return this.brewery;
    }

    public void setBrewery(String brewery) {
        this.brewery = brewery;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String toString() {
        return this.getName();
    }
}
/**
 * Created by mtiko_000 on 4/22/2015.
 */

