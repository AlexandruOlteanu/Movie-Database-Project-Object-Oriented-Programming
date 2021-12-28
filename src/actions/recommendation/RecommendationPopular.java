package actions.recommendation;

import database.Database;
import entertainment.Genre;
import fileio.ActionInputData;
import fileio.MovieInputData;
import fileio.UserInputData;
import fileio.SerialInputData;
import fileio.Writer;
import org.json.simple.JSONArray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public final class RecommendationPopular {
    /**
     * Se calculeaza pentru fiecare gen in parte numarul de vizualizari al
     * filmelor care fac parte din acest gen. Aceasta lista de genuri se
     * sorteaza descrescator in functie de acest criteriu. Apoi, se parcurg
     * genurile si se gaseste primul videoclip ce nu a fost vazut de utilizatorul
     * curent. Daca toate videoclipurile din lista au fost vazute de catre utilizatorul
     * curent se intoarce mesajul de eroare. De asemenea, se verifica daca utilizatorul ce a accesat
     * recomandarea are subscriptie Premium sau Basic. In cazul celei Basic, acesta nu poate
     * accesa recomandarea asa ca se afiseaza mesajul de eroare corespunzator.
     *
     * @param action reprezinta actiunea curenta ce trebuie executata
     * @param mydatabase reprezinta baza de date ce contine toate informatiile despre
     * utilizatori, filme, etc
     * @param array mesajul final ce va fi afisat sub forma de obiect JSON
     * @throws IOException
     */
    public void solve(final ActionInputData action, final Database mydatabase,
                      final JSONArray array) throws IOException {

        List<UserInputData> users = mydatabase.getUsers();
        int ok = 1;
        UserInputData myUser = null;
        for (UserInputData user : users) {
            if (user.getUsername().equals(action.getUsername())) {
                myUser = user;
                if (user.getSubscriptionType().equals("BASIC")) {
                    ok = 0;
                }
            }
        }
        if (ok == 0) {
            Writer w = new Writer("test");
            String message = "PopularRecommendation cannot be applied!";
            array.add(w.writeFile(action.getActionId(), " ", message));
            return;
        }

        List<GenresViews> genresViews = new ArrayList<GenresViews>();
        List<MovieInputData> movies = mydatabase.getMovies();
        List<SerialInputData> serials = mydatabase.getSerials();

        List<AllVideos> allVideos = new ArrayList<AllVideos>();

        for (MovieInputData movie : movies) {
            AllVideos newItem = new AllVideos();
            newItem.setTitle(movie.getTitle());
            newItem.setGenres(movie.getGenres());
            allVideos.add(newItem);
        }

        for (SerialInputData serial : serials) {
            AllVideos newItem = new AllVideos();
            newItem.setTitle(serial.getTitle());
            newItem.setGenres(serial.getGenres());
            allVideos.add(newItem);
        }


        for (Genre genre : Genre.values()) {
            GenresViews newItem = new GenresViews();
            newItem.setName(genre);
            newItem.setViews(0);
            genresViews.add(newItem);
        }

        for (UserInputData user : users) {
            for (Map.Entry<String, Integer> entry : user.getHistory().entrySet()) {
                for (GenresViews genre : genresViews) {
                    for (AllVideos video : allVideos) {
                        if (video.getTitle().equals(entry.getKey())) {
                            for (String videoGenre : video.getGenres()) {
                                if (videoGenre.toUpperCase().equals(genre.getName().toString())) {
                                    genre.setViews(genre.getViews() + entry.getValue());
                                }
                            }
                        }
                    }
                }
            }
        }

        genresViews.sort(new Comparator<GenresViews>() {
            @Override
            public int compare(final GenresViews o1, final GenresViews o2) {
                if (o1.getViews().compareTo(o2.getViews()) == 0) {
                    return -o1.getName().toString().compareTo(o2.getName().toString());
                }
                return -o1.getViews().compareTo(o2.getViews());
            }
        });

        ok = 0;

        String recommendation = "";

        for (GenresViews genre : genresViews) {
            for (AllVideos video : allVideos) {
                if (ok == 1) {
                    continue;
                }
                int ok2 = 0;
                for (String videoGenre : video.getGenres()) {
                    if (videoGenre.toUpperCase().equals(genre.getName().toString())) {
                            ok2 = 1;
                    }
                }
                if (ok2 == 1) {
                    for (Map.Entry<String, Integer> entry : myUser.getHistory().entrySet()) {
                        if (entry.getKey().equals(video.getTitle())) {
                            ok2 = 0;
                        }
                    }
                    if (ok2 == 1) {
                        recommendation = video.getTitle();
                        ok = 1;
                    }
                }
            }
        }

        Writer w = new Writer("test");
        String message = "PopularRecommendation cannot be applied!";
        if (ok == 1) {
            message = "PopularRecommendation result: " + recommendation;
        }
        array.add(w.writeFile(action.getActionId(), " ", message));
    }
}
