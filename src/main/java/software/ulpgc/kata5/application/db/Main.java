package software.ulpgc.kata5.application.jacinto;

import software.ulpgc.kata5.application.*;
import software.ulpgc.kata5.model.Movie;
import software.ulpgc.kata5.tasks.HistogramBuilder;
import software.ulpgc.kata5.viewmodel.Histogram;

import java.io.File;
import java.sql.*;
import java.util.stream.Stream;

public class Main {

    private static File database = new File("movies.db");

    public static void main(String[] args) throws SQLException {
        try (Connection connection = openConnection()) {
            importMoviesFromRemoteIfRequiredWith(connection);
            Stream<Movie> movies = new DatabaseStore(connection).movies()
                    .filter(m->m.year() > 1900)
                    .filter(m->m.year() < 2025);
            Histogram histogram = HistogramBuilder.with(movies)
                    .title("Movies per year")
                    .x("Year")
                    .y("Frequency")
                    .legend("Movies")
                    .build(Movie::year);
            MainFrame.create()
                    .display(histogram)
                    .setVisible(true);

        }
    }

    private static void importMoviesFromRemoteIfRequiredWith(Connection connection) throws SQLException {
        if (database.length() > 0) return;
        new DatabaseRecorder(connection).put(new RemoteStore(Deserializer::fromTsv).movies());
    }

    private static Connection openConnection() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:" + database.getAbsolutePath());
        connection.setAutoCommit(false);
        return connection;
    }
}


