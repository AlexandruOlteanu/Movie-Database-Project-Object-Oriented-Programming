package actions.recommendation;

import database.Database;
import fileio.ActionInputData;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.UserInputData;
import org.json.simple.JSONArray;
import fileio.Writer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public final class RecommendationFavorite {
    /**
     * Sorteaza descrescator videoclipurile dupa numarul de utilizatori ce
     * le-au adaugat la favorite. In urma acestei ordonari se parcurge lista
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
    public void solve(final ActionInputData action, final  Database mydatabase,
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
            String message = "FavoriteRecommendation cannot be applied!";
            array.add(w.writeFile(action.getActionId(), " ", message));
            return;
        }

        List<MovieInputData> movies = mydatabase.getMovies();
        List<SerialInputData> serials = mydatabase.getSerials();

        List<AllVideos> allVideos = new ArrayList<AllVideos>();

        for (MovieInputData movie : movies) {
            AllVideos newItem = new AllVideos();
            Integer favorite = 0;
            for (UserInputData user : users) {
                for (String favoriteVideo : user.getFavoriteMovies()) {
                    if (favoriteVideo.equals(movie.getTitle())) {
                        ++favorite;
                    }
                }
            }
            newItem.setTitle(movie.getTitle());
            newItem.setFavorite(favorite);
            allVideos.add(newItem);
        }

        for (SerialInputData serial : serials) {
            AllVideos newItem = new AllVideos();
            Integer favorite = 0;
            for (UserInputData user : users) {
                for (String favoriteVideo : user.getFavoriteMovies()) {
                    if (favoriteVideo.equals(serial.getTitle())) {
                        ++favorite;
                    }
                }
            }
            newItem.setTitle(serial.getTitle());
            newItem.setFavorite(favorite);
            allVideos.add(newItem);
        }

        allVideos.sort(new Comparator<AllVideos>() {
            @Override
            public int compare(final AllVideos o1, final AllVideos o2) {
                return -o1.getFavorite().compareTo(o2.getFavorite());
            }
        });

        ok = 0;

        String recommendation = "";

        for (AllVideos video : allVideos) {
            if (video.getFavorite().equals(0) || ok == 1) {
                continue;
            }
            int ok2 = 1;
            for (Map.Entry<String, Integer> entry : myUser.getHistory().entrySet()) {
                if (entry.getKey().equals(video.getTitle())) {
                    ok2 = 0;
                }
            }
            if (ok2 == 1) {
                ok = 1;
                recommendation = video.getTitle();
            }
        }

        Writer w = new Writer("test");
        String message = "FavoriteRecommendation cannot be applied!";
        if (ok == 1) {
            message = "FavoriteRecommendation result: " + recommendation;
        }
        array.add(w.writeFile(action.getActionId(), " ", message));
    }
}
