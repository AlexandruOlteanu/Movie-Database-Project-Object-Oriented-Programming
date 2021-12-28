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
import java.util.List;

public final class RecommendStandard {
    /**
     * Parcurgem baza de date si intoarcem primul film / serial intalnit care nu
     * a fost vazut. In cazul in care toate videoclipurile au fost vazute de utilizatorul
     * curent, se afiseaza mesajul de eroare corespunzator.
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
        List<MovieInputData> movies = new ArrayList<MovieInputData>();
        List<SerialInputData> serials = new ArrayList<SerialInputData>();

        movies = mydatabase.getMovies();
        serials = mydatabase.getSerials();

        int ok = 0;

        String recommendation = "";

        for (UserInputData user : users) {
            if (user.getUsername().equals(action.getUsername())) {
                for (int i = 0; i < movies.size() && ok != 1; ++i) {
                    if (!user.getHistory().containsKey(movies.get(i).getTitle())) {
                        ok = 1;
                        recommendation = movies.get(i).getTitle();
                    }
                }

                for (int i = 0; i < serials.size() && ok != 1; ++i) {
                    if (!user.getHistory().containsKey(serials.get(i).getTitle())) {
                        ok = 1;
                        recommendation = serials.get(i).getTitle();
                    }
                }
            }
        }

        Writer w = new Writer("test");
        String message = new String();

        if (ok == 1) {
            message = "StandardRecommendation result: " + recommendation;
        } else {
            message = "StandardRecommendation cannot be applied!";
        }

        array.add(w.writeFile(action.getActionId(), " ", message));
    }
}
