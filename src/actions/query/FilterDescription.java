package actions.query;

import database.Database;
import fileio.ActionInputData;
import fileio.ActorInputData;
import fileio.Writer;
import org.json.simple.JSONArray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class FilterDescription {
    /**
     * M-am folosit de regulile de match pe expresii oferite de REGEX,
     * documentatia cu privire la Patten si Matcher a fost preluata de
     * pe <a href = "https://stackoverflow.com/"> stackoverflow </a> si
     * <a href = "https://www.w3schools.com/">W3Schools</a>
     * Astfel, am reusit sa parcurg descreierea fiecarui actor si sa verific daca
     * aceasta contine toate keyword-urile date ca parametri in actiune.
     *
     * @param action reprezinta actiunea curenta ce trebuie executata
     * @param mydatabase reprezinta baza de date ce contine toate informatiile despre
     * utilizatori, filme, etc
     * @param array mesajul final ce va fi afisat sub forma de obiect JSON
     * @throws IOException
     */
    void solve(final ActionInputData action, final Database mydatabase,
               final JSONArray array) throws IOException {

        List<String> actorsAll = new ArrayList<String>();
        List<ActorInputData> actors = mydatabase.getActors();

        for (ActorInputData actor : actors) {
            String s = actor.getCareerDescription();
            int ok = 1;
            for (String keyword : action.getFilters().get(2)) {
                Pattern p = Pattern.compile("\\W(?i)" + keyword + "\\W");
                Matcher match = p.matcher(s);
                if (!match.find()) {
                    ok = 0;
                }
            }
            if (ok == 1) {
                actorsAll.add(actor.getName());
            }
        }

        actorsAll.sort(new Comparator<String>() {
            @Override
            public int compare(final String o1, final String o2) {
                if (action.getSortType().equals("asc")) {
                    return o1.compareTo(o2);
                } else {
                    return -o1.compareTo(o2);
                }
            }
        });


        Writer w = new Writer("test");
        String message = "Query result: " + actorsAll;
        array.add(w.writeFile(action.getActionId(), " ", message));
    }
}
