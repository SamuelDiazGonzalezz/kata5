package software.ulpgc.kata5.application.claudia;

import software.ulpgc.kata5.application.Deserializer;
import software.ulpgc.kata5.application.MainFrame;
import software.ulpgc.kata5.application.RemoteStore;
import software.ulpgc.kata5.model.Movie;
import software.ulpgc.kata5.tasks.HistogramBuilder;
import software.ulpgc.kata5.viewmodel.Histogram;

import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        Stream<Movie> movies = new RemoteStore(Deserializer::fromTsv).movies()
                .filter(m->m.year() > 1900)
                .filter(m->m.year() < 2025)
                .limit(10_000);
        Histogram histogram = HistogramBuilder.with(movies)
                .title("Movies per year")
                .x("Year")
                .y("Frequency")
                .legend("Movies")
                .build(Movie::year);
        MainFrame.create()
                .display(histogram)
                .setVisible(true);
        System.out.println("uifio");;
    }


}
