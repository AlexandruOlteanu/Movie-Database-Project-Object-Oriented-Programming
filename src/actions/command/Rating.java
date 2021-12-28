package actions.command;

import database.Database;
import entertainment.Season;
import fileio.ActionInputData;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.Writer;
import fileio.UserInputData;
import org.json.simple.JSONArray;

import java.io.IOException;
import java.util.List;

public final class Rating {
    /**
     * Verific daca utilizatorul curent a vizionat filmul / serialul primit
     * in actiune. Daca nu, afisez mesajul de eroare. Daca da, verific daca filmul, respectiv
     * sezonul serialului caruia se doreste sa i se dea rating a mai primit deja rating de la
     * utilizatorul curent in trecut. Daca da, afisez mesajul de eroare. Daca nu,
     * afisez mesajul de succes si adaug modificarile corespunzatoare bazei de date
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
        List<MovieInputData> movies = mydatabase.getMovies();
        List<SerialInputData> serials = mydatabase.getSerials();

        for (UserInputData user : users) {
            if (user.getUsername().equals(action.getUsername())) {
                if (!user.getHistory().containsKey(action.getTitle())) {
                    Writer w = new Writer("test");
                    String message = "error -> " + action.getTitle() + " is not seen";
                    array.add(w.writeFile(action.getActionId(), " ", message));
                    return;
                }
                short ok = 1;
                for (String name : user.getRatedMovies()) {
                    if (name.equals(action.getTitle())) {
                        ok = 0;
                    }
                }
                if (ok == 0) {
                    Writer w = new Writer("test");
                    String message = "error -> " + action.getTitle() + " has been already rated";
                    array.add(w.writeFile(action.getActionId(), " ", message));
                } else {
                    for (MovieInputData movie : movies) {
                        if (movie.getTitle().equals(action.getTitle())) {
                            user.getRatedMovies().add(action.getTitle());
                            movie.getRatings().add(action.getGrade());
                            Writer w = new Writer("test");
                            String message = "success -> " + action.getTitle()
                                    + " was rated with " + action.getGrade()
                                    + " by " + action.getUsername();
                            array.add(w.writeFile(action.getActionId(), " ", message));
                        }
                    }
                    for (SerialInputData serial : serials) {
                        if (serial.getTitle().equals(action.getTitle())) {
                            ok = 1;
                            List<Season> seasons = serial.getSeasons();
                            Season season = seasons.get(action.getSeasonNumber() - 1);
                            for (String name : season.getRatingUsers()) {
                                    if (name.equals(user.getUsername())) {
                                        ok = 0;
                                    }
                            }
                            if (ok == 0) {
                                Writer w = new Writer("test");
                                String message = "error -> " + action.getTitle()
                                        + " has been already rated";
                                array.add(w.writeFile(action.getActionId(), " ", message));
                            }
                            if (ok == 1) {
                                int ok2 = 1;
                                for (String name : user.getRatedSerials()) {
                                    if (name.equals(action.getTitle())) {
                                        ok2 = 0;
                                    }
                                }
                                if (ok2 == 1) {
                                    user.getRatedSerials().add(action.getTitle());
                                }
                                season.getRatingUsers().add(user.getUsername());
                                season.getRatings().add(action.getGrade());
                                Writer w = new Writer("test");
                                String message = "success -> " + action.getTitle()
                                        + " was rated with " + action.getGrade()
                                        + " by " + action.getUsername();
                                array.add(w.writeFile(action.getActionId(), " ", message));
                            }
                        }
                    }
                }
            }
        }
    }
}
