package actions.query;

import database.Database;
import fileio.ActionInputData;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.Writer;
import fileio.ActorInputData;
import org.json.simple.JSONArray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public final class Average {
    /**
     * Sortez crescator sau descrescator actorii in functie de rating-ul
     * filmelor in care au jucat. Modul in care se calculeaza acest rating pentru
     * filme si seriale va fi detaliat in dreptul metodelor corespunzatoare.
     * Odata sortati corespunzator, actorii vor fi alesi in ordine, primii N,
     * cei cu rating 0 (Echivalent cu actori care nu au primit inca rating) fiind
     * exclusi. Cei N (sau mai putini) sunt pusi intr-un array si afisati la output
     *
     * @param action reprezinta actiunea curenta ce trebuie executata
     * @param mydatabase reprezinta baza de date ce contine toate informatiile despre
     * utilizatori, filme, etc
     * @param array mesajul final ce va fi afisat sub forma de obiect JSON
     * @throws IOException
     */
    void solve(final ActionInputData action, final Database mydatabase,
               final JSONArray array) throws IOException {

        List<ActorInputData> actors = new ArrayList<ActorInputData>();
        actors.addAll(mydatabase.getActors());

        List<MovieInputData> movies = mydatabase.getMovies();
        List<SerialInputData> serials = mydatabase.getSerials();

        for (ActorInputData actor : actors) {
            actor.setRating(0.0);
            actor.setVideosPlayed(0.0);
        }

        for (MovieInputData movie : movies) {
            movie.calculateRating();
            if (Double.compare(movie.getRating(), 0.0) != 0) {
                for (String nameActor : movie.getCast()) {
                    for (ActorInputData actor : actors) {
                        if (actor.getName().equals(nameActor)) {
                            actor.setRating(actor.getRating() + movie.getRating());
                            actor.setVideosPlayed(actor.getVideosPlayed() + 1);
                        }
                    }
                }
            }
        }

        for (SerialInputData serial : serials) {
            serial.calculateRating();
            if (Double.compare(serial.getRating(), 0.0) != 0) {
                for (String nameActor : serial.getCast()) {
                    for (ActorInputData actor : actors) {
                        if (actor.getName().equals(nameActor)) {
                            actor.setRating(actor.getRating() + serial.getRating());
                            actor.setVideosPlayed(actor.getVideosPlayed() + 1);
                        }
                    }
                }
            }
        }

        for (ActorInputData actor : actors) {
            if (Double.compare(actor.getVideosPlayed(), 0.0) != 0) {
                actor.setRating(actor.getRating() / actor.getVideosPlayed());
            }
        }
        actors.sort(new Comparator<ActorInputData>() {
            @Override
            public int compare(final ActorInputData o1, final ActorInputData o2) {
                 if (action.getSortType().equals("asc")) {
                     if (Double.compare(o1.getRating(), o2.getRating()) == 0) {
                         return o1.getName().compareTo(o2.getName());
                     }
                     return Double.compare(o1.getRating(), o2.getRating());
                 } else {
                     if (Double.compare(o1.getRating(), o2.getRating()) == 0) {
                         return -o1.getName().compareTo(o2.getName());
                     }
                     return -Double.compare(o1.getRating(), o2.getRating());
                 }
            }
        });

        List<String> finalActors = new ArrayList<String>();
        int takingActors = action.getNumber();
        for (int i = 0; i < actors.size() && takingActors > 0; ++i) {
            if (Double.compare(actors.get(i).getRating(), 0.0) != 0) {
                finalActors.add(actors.get(i).getName());
                --takingActors;
            }
        }

        Writer w = new Writer("test");
        String message = "Query result: " + finalActors;
        array.add(w.writeFile(action.getActionId(), " ", message));
    }
}
