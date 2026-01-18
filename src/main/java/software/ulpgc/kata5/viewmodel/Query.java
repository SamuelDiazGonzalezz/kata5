package software.ulpgc.kata5.viewmodel;

import software.ulpgc.kata5.io.Store;
import software.ulpgc.kata5.model.Movie;

import java.util.Map;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class Query {
    private final Store store;

    public Query(Store store) {
        this.store = store;
    }

    public Map<Integer, Long> movieCountPerYear() {
        return store.movies().collect(groupingBy(Movie::year, counting()));
    }
}
