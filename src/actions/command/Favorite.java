package actions.command;

import database.Database;
import fileio.ActionInputData;
import fileio.Writer;
import fileio.UserInputData;
import org.json.simple.JSONArray;

import java.io.IOException;
import java.util.List;

public final class Favorite {
    /**
     * Verific daca videoclipul dat ca parametru in actiune este prezent
     * in istoricul de filme vizionate al utilizatorului curent sau in
     * lista de filme favorite. Daca nu este vizionat deja afisez mesajul de
     * eroare corespunzator, analog si pentru cazul in care videoclipul se
     * afla deja in lista sa de favorite. Daca nu este valabil niciunul din
     * cazurile de mai sus, afisez mesajul de succes si il adaug in baza de baze
     * ca nou film favorit pentru utilizator
     *
     * @param action reprezinta actiunea curenta ce trebuie executata
     * @param mydatabase reprezinta baza de date ce contine toate informatiile despre
     * utilizatori, filme, etc
     * @param array mesajul final ce va fi afisat sub forma de obiect JSON
     * @throws IOException
     */
    void solve(final ActionInputData action, final Database mydatabase,
               final JSONArray array) throws IOException {

        List<UserInputData> users = mydatabase.getUsers();
        for (UserInputData user : users) {
            if (user.getUsername().equals(action.getUsername())) {
                short ok = 1;
                for (String movie : user.getFavoriteMovies()) {
                    if (movie.equals(action.getTitle())) {
                        ok = 0;
                    }
                }
                if (!user.getHistory().containsKey(action.getTitle())) {
                    ok = 2;
                }
                if (ok == 0) {
                    Writer w = new Writer("test");
                    String message = "error -> " + action.getTitle()
                            + " is already in favourite list";
                    array.add(w.writeFile(action.getActionId(), " ", message));
                }
                if (ok == 1) {

                    Writer w = new Writer("test");
                    String message = "success -> " + action.getTitle() + " was added as favourite";
                    array.add(w.writeFile(action.getActionId(), " ", message));
                    user.getFavoriteMovies().add(action.getTitle());
                }
                if (ok == 2) {
                    Writer w = new Writer("test");
                    String message = "error -> " + action.getTitle() + " is not seen";
                    array.add(w.writeFile(action.getActionId(), " ", message));
                }
            }
        }
    }
}
