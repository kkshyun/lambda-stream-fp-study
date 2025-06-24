package lambda.lambda5.filter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class GenericFilter {
    public static <T> List<T> filter(List<T> numbers, Predicate<T> predicate) {
        ArrayList<T> filtered = new ArrayList<>();
        for(T num : numbers) {
            if(predicate.test(num)) {
                filtered.add(num);
            }
        }
        return filtered;
    }
}
