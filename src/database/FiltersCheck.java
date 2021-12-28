package database;

import fileio.ActionInputData;
import fileio.MovieInputData;
import fileio.SerialInputData;

public class FiltersCheck {
    /**
     * Verifica daca filmul transmis ca parametru corespunde filtrelor aplicate
     * de actiune
     *
     * @param movie Filmul ce trebuie verificat
     * @param action Actiunea curenta ce trebuie rezolvata
     * @return Returneza 1 daca filmul respecta filtrul, 0 daca nu
     */
    public int filterCheckMovie(final MovieInputData movie, final ActionInputData action) {
        short ok = 1;
        Integer a = movie.getYear();
        short ok2 = 0;
        if (action.getFilters().get(0).get(0) == null) {
            ok2 = 1;
        } else {
            for (String year : action.getFilters().get(0)) {
                if (year.equals(a.toString())) {
                    ok2 = 1;
                }
            }
        }
        if (ok2 == 0) {
            ok = 0;
        }
        ok2 = 0;
        if (action.getFilters().get(1).get(0) == null) {
            ok2 = 1;
        } else {
            for (String genreNeeded : action.getFilters().get(1)) {
                ok2 = 0;
                for (String genreMovie : movie.getGenres()) {
                    if (genreMovie.equals(genreNeeded)) {
                        ok2 = 1;
                    }
                }
                if (ok2 == 0) {
                    ok = 0;
                }
            }
        }
        return ok;
    }

    /**
     * Verifica daca serialul transmis ca parametru corespunde filtrelor aplicate
     * de actiune
     *
     * @param serial Serialul ce trebuie verificat
     * @param action Actiunea curenta ce trebuie rezolvata
     * @return Returneza 1 daca serialul respecta filtrul, 0 daca nu
     */
    public int filterCheckSerial(final SerialInputData serial, final ActionInputData action) {
        short ok = 1;
        Integer a = serial.getYear();
        short ok2 = 0;
        if (action.getFilters().get(0).get(0) == null) {
            ok2 = 1;
        } else {
            for (String year : action.getFilters().get(0)) {
                if (year.equals(a.toString())) {
                    ok2 = 1;
                }
            }
        }
        if (ok2 == 0) {
            ok = 0;
        }
        ok2 = 0;
        for (String genreNeeded : action.getFilters().get(1)) {
            ok2 = 0;
            for (String genreMovie : serial.getGenres()) {
                if (genreMovie.equals(genreNeeded)) {
                    ok2 = 1;
                }
            }
            if (ok2 == 0) {
                ok = 0;
            }
        }
        return ok;
    }
}
