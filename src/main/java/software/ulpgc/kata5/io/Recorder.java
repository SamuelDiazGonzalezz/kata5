package software.ulpgc.kata5.io;

import software.ulpgc.kata5.model.Movie;

import java.util.stream.Stream;

public interface Recorder {
    void put(Stream<Movie> movies);
}
