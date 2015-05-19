import java.util.Comparator;

/**
 * Created by mtiko_000 on 5/19/2015.
 */
public class draftAbvSorter implements Comparator<Beer> {
    @Override
    public int compare(Beer b1, Beer b2) {
        if (b1.getAbvDouble()<b2.getAbvDouble()) {
            return -1;
        } else{
            return 1;
        }
    }
}



