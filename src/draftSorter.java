import java.util.Comparator;

/**
 * Created by mtiko_000 on 5/16/2015.
 */
public class draftSorter implements Comparator <String> {

    @Override
    public int compare(String o1, String o2) {
        if (o1.length()<o2.length()) {
            return 1;
        } else{
            return -1;
        }
    }
}
