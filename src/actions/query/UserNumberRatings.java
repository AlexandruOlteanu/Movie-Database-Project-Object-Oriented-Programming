package actions.query;

import database.Database;
import fileio.ActionInputData;
import fileio.UserInputData;
import fileio.Writer;
import org.json.simple.JSONArray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class UserNumberRatings {
    /**
     * Calculez numarul de rating-uri pe care le-a dat fiecare utilizator si
     * ii ordonez crescator sau descrescator, excluzand pe cei cu 0 videoclipuri
     * carora le-a fost acordat rating
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
        List<String> finalUsers = new ArrayList<String>();
        int takingUsers = action.getNumber();

        List<UserInputData> userArray = new ArrayList<UserInputData>();

        userArray = users;

        userArray.sort(new Comparator<UserInputData>() {
            @Override
            public int compare(final UserInputData o1, final UserInputData o2) {
                if (action.getSortType().equals("asc")) {
                    Integer a = o1.getRatedMovies().size() + o1.getRatedSerials().size();
                    Integer b = o2.getRatedMovies().size() + o2.getRatedSerials().size();
                    if (a.compareTo(b) == 0) {
                        return o1.getUsername().compareTo(o2.getUsername());
                    }
                    return a.compareTo(b);
                } else {
                    Integer a = o1.getRatedMovies().size() + o1.getRatedSerials().size();
                    Integer b = o2.getRatedMovies().size() + o2.getRatedSerials().size();
                    if (a.compareTo(b) == 0) {
                        return -o1.getUsername().compareTo(o2.getUsername());
                    }
                    return -a.compareTo(b);
                }
            }
        });

        for (int i = 0; i < userArray.size() && takingUsers > 0; ++i) {
            int ok = 1;
            int videosRated = userArray.get(i).getRatedMovies().size();
            videosRated += userArray.get(i).getRatedSerials().size();
            if (videosRated == 0) {
                ok = 0;
            }
            if (ok == 1) {
                finalUsers.add(userArray.get(i).getUsername());
                --takingUsers;
            }
        }
        Writer w = new Writer("test");
        String message = "Query result: " + finalUsers;
        array.add(w.writeFile(action.getActionId(), " ", message));
    }
}
