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

public final class RecommendationBestUnseen {
    /**
     * Ordonam descrescator videoclipurile in functie de rating-ul acestora,
     * al doilea criteriu fiind ordinea in baza de date (pentru aceasta in lista
     * de AllVideos am adaugat prima data filmele si apoi serialele). Odata
     * ordonate, parcurgem lista element cu element pana intalnim primul videoclip
     * ce nu a fost vazut si il recomandam. Daca toate videoclipurile din lista
     * au fost vazute de catre utilizatorul curent se intoarce mesajul de eroare.
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
        List<AllVideos> allVideos = new ArrayList<AllVideos>();
        List<MovieInputData> movies = mydatabase.getMovies();
        List<SerialInputData> serials = mydatabase.getSerials();

        for (MovieInputData movie : movies) {
            movie.calculateRating();
            AllVideos newVideo = new AllVideos();
            newVideo.setRating(movie.getRating());
            newVideo.setTitle(movie.getTitle());
            allVideos.add(newVideo);
        }

        for (SerialInputData serial : serials) {
            serial.calculateRating();
            AllVideos newVideo = new AllVideos();
            newVideo.setRating(serial.getRating());
            newVideo.setTitle(serial.getTitle());
            allVideos.add(newVideo);
        }

        int ok = 0;

        allVideos.sort(new Comparator<AllVideos>() {
            @Override
            public int compare(final AllVideos o1, final AllVideos o2) {
                return -Double.compare(o1.getRating(), o2.getRating());
            }
        });

        String recommendation = "";

        for (UserInputData user : users) {
            if (user.getUsername().equals(action.getUsername())) {
                for (int i = 0; i < allVideos.size() && ok != 1; ++i) {
                    if (!user.getHistory().containsKey(allVideos.get(i).getTitle())) {
                        ok = 1;
                        recommendation = allVideos.get(i).getTitle();
                    }
                }
            }
        }

        Writer w = new Writer("test");
        String message = "";

        if (ok == 1) {
            message = "BestRatedUnseenRecommendation result: " + recommendation;
        } else {
            message = "BestRatedUnseenRecommendation cannot be applied!";
        }

        array.add(w.writeFile(action.getActionId(), " ", message));
    }
}
