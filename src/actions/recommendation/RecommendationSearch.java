package actions.recommendation;

import database.Database;

import fileio.ActionInputData;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.UserInputData;
import fileio.Writer;
import org.json.simple.JSONArray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public final class RecommendationSearch {
    /**
     * Se construieste o lista comuna ce contine toate filmele si serialele ce fac
     * parte din genul dat ca filtru de cautare in actiune. Acestea sunt apoi sortate
     * in functie de rating si apoi in functie de nume. In urma acestei ordonari se parcurge lista
     * element cu element pana intalnim primul videoclipce nu a fost vazut si il recomandam.
     * Daca toate videoclipurile din lista au fost vazute de catre utilizatorul curent
     * se intoarce mesajul de eroare. De asemenea, se verifica daca utilizatorul ce a accesat
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
            String message = "SearchRecommendation cannot be applied!";
            array.add(w.writeFile(action.getActionId(), " ", message));
            return;
        }

        List<MovieInputData> movies = mydatabase.getMovies();
        List<SerialInputData> serials = mydatabase.getSerials();

        List<AllVideos> allVideos = new ArrayList<AllVideos>();

        for (MovieInputData movie : movies) {
            int ok2 = 0;
            for (String genre : movie.getGenres()) {
                if (genre.equals(action.getGenre())) {
                    ok2 = 1;
                }
            }
            if (ok2 == 0) {
                continue;
            }
            AllVideos newItem = new AllVideos();
            movie.calculateRating();
            newItem.setTitle(movie.getTitle());
            newItem.setRating(movie.getRating());
            allVideos.add(newItem);
        }

        for (SerialInputData serial : serials) {
            int ok2 = 0;
            for (String genre : serial.getGenres()) {
                if (genre.equals(action.getGenre())) {
                    ok2 = 1;
                }
            }
            if (ok2 == 0) {
                continue;
            }
            AllVideos newItem = new AllVideos();
            serial.calculateRating();
            newItem.setTitle(serial.getTitle());
            newItem.setRating(serial.getRating());
            allVideos.add(newItem);
        }

        allVideos.sort(new Comparator<AllVideos>() {
            @Override
            public int compare(final AllVideos o1, final AllVideos o2) {
                if (Double.compare(o1.getRating(), o2.getRating()) == 0) {
                    return o1.getTitle().compareTo(o2.getTitle());
                }
                return Double.compare(o1.getRating(), o2.getRating());
            }
        });

        List<String> recommendation = new ArrayList<String>();

        ok = 0;

        for (AllVideos video : allVideos) {
            int ok2 = 1;
            for (Map.Entry<String, Integer> entry : myUser.getHistory().entrySet()) {
                if (entry.getKey().equals(video.getTitle())) {
                    ok2 = 0;
                }
            }
            if (ok2 == 1) {
                ok = 1;
                recommendation.add(video.getTitle());
            }
        }

        Writer w = new Writer("test");
        String message = "SearchRecommendation cannot be applied!";
        if (ok == 1) {
            message = "SearchRecommendation result: " + recommendation;
        }
        array.add(w.writeFile(action.getActionId(), " ", message));
    }
}
