package actions.query;

import database.Database;
import database.FiltersCheck;
import fileio.ActionInputData;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.Writer;
import fileio.UserInputData;
import org.json.simple.JSONArray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class VideoFavorite {
    /**
     * Am verificat toate listele de videoclipuri favorite ale
     * utilizatorilor si am adaugat astfel fiecarui videoclip in parte
     * numarul total de ori in care a fost adaugat la favorite. Aceste videoclipuri
     * au fost apoi sortate crescator / descrescator, fiind excluse cele cu 0 favorite.
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
        List<String> finalVideos = new ArrayList<String>();
        int takingVideos = action.getNumber();

        if (action.getObjectType().equals("movies")) {
            List<MovieInputData> movies = new ArrayList<MovieInputData>();
            movies.addAll(mydatabase.getMovies());
            for (MovieInputData movie : movies) {
                movie.calculateFavorite(users);
            }

            movies.sort(new Comparator<MovieInputData>() {
                @Override
                public int compare(final MovieInputData o1, final MovieInputData o2) {
                    if (action.getSortType().equals("asc")) {
                        if (o1.getFavorite().compareTo(o2.getFavorite()) == 0) {
                            return o1.getTitle().compareTo(o2.getTitle());
                        }
                        return o1.getFavorite().compareTo(o2.getFavorite());
                    } else {
                        if (o1.getFavorite().compareTo(o2.getFavorite()) == 0) {
                            return -o1.getTitle().compareTo(o2.getTitle());
                        }
                        return -o1.getFavorite().compareTo(o2.getFavorite());
                    }
                }
            });

            FiltersCheck filtersCheck = new FiltersCheck();
            for (int i = 0; i < movies.size() && takingVideos > 0; ++i) {
                if (!movies.get(i).getFavorite().equals(0)) {
                    int ok = filtersCheck.filterCheckMovie(movies.get(i), action);
                    if (ok == 1) {
                        finalVideos.add(movies.get(i).getTitle());
                        --takingVideos;
                    }
                }
            }

            Writer w = new Writer("test");
            String message = "Query result: " + finalVideos;
            array.add(w.writeFile(action.getActionId(), " ", message));
        } else {
            List<SerialInputData> serials = new ArrayList<SerialInputData>();
            serials.addAll(mydatabase.getSerials());

            for (SerialInputData serial : serials) {
                serial.calculateFavorite(users);
            }

            serials.sort(new Comparator<SerialInputData>() {
                @Override
                public int compare(final SerialInputData o1, final SerialInputData o2) {
                    if (action.getSortType().equals("asc")) {
                        if (o1.getFavorite().compareTo(o2.getFavorite()) == 0) {
                            return o1.getTitle().compareTo(o2.getTitle());
                        }
                        return o1.getFavorite().compareTo(o2.getFavorite());
                    } else {
                        if (o1.getFavorite().compareTo(o2.getFavorite()) == 0) {
                            return -o1.getTitle().compareTo(o2.getTitle());
                        }
                        return -o1.getFavorite().compareTo(o2.getFavorite());
                    }
                }
            });
            FiltersCheck filtersCheck = new FiltersCheck();
            for (int i = 0; i < serials.size() && takingVideos > 0; ++i) {
                    if (!serials.get(i).getFavorite().equals(0)) {
                        int ok = filtersCheck.filterCheckSerial(serials.get(i), action);
                        if (ok == 1) {
                            finalVideos.add(serials.get(i).getTitle());
                            --takingVideos;
                        }
                    }
            }

            Writer w = new Writer("test");
            String message = "Query result: " + finalVideos;
            array.add(w.writeFile(action.getActionId(), " ", message));
        }
    }
}
