package actions.query;

import actor.ActorsAwards;
import database.Database;
import fileio.ActionInputData;
import fileio.ActorInputData;
import fileio.Writer;
import org.json.simple.JSONArray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public final class Awards {
    /**
     * Verific fiecare actor in parte daca detine toate premiile mentionate
     * in parametrii actiunii curente. In cazul in care se afla, va fi inclus in
     * lista. La final, acestia vor fi sortati crescator sau descrescator in functie
     * de numarul total de premii primite
     *
     * @param action reprezinta actiunea curenta ce trebuie executata
     * @param mydatabase reprezinta baza de date ce contine toate informatiile despre
     * utilizatori, filme, etc
     * @param array mesajul final ce va fi afisat sub forma de obiect JSON
     * @throws IOException
     */
    void solve(final ActionInputData action, final Database mydatabase,
               final JSONArray array) throws IOException {

        List<ActorInputData> actors = new ArrayList<ActorInputData>();

        for (ActorInputData actor : mydatabase.getActors()) {
            short ok = 1;
            final int value = 3;
            for (String award : action.getFilters().get(value)) {
                short ok2 = 0;
                for (Map.Entry<ActorsAwards, Integer> awardActor : actor.getAwards().entrySet()) {
                    if (awardActor.getKey().toString().equals(award)) {
                        ok2 = 1;
                    }
                }
                if (ok2 == 0) {
                    ok = 0;
                }
            }
            if (ok == 1) {
                actors.add(actor);
            }
        }

        for (ActorInputData actor : actors) {
            actor.calculateNumberAwards();
        }
        actors.sort(new Comparator<ActorInputData>() {
            @Override
            public int compare(final ActorInputData o1, final ActorInputData o2) {
                if (action.getSortType().equals("asc")) {
                    if (o1.getNumberAwards().compareTo(o2.getNumberAwards()) == 0) {
                        return o1.getName().compareTo(o2.getName());
                    }
                    return o1.getNumberAwards().compareTo(o2.getNumberAwards());
                } else {
                    if (o1.getNumberAwards().compareTo(o2.getNumberAwards()) == 0) {
                        return -o1.getName().compareTo(o2.getName());
                    }
                    return -o1.getNumberAwards().compareTo(o2.getNumberAwards());
                }
            }
        });
        List<String> finalActors = new ArrayList<String>();
        for (ActorInputData actor : actors) {
            finalActors.add(actor.getName());
        }
        Writer w = new Writer("test");
        String message = "Query result: " + finalActors;
        array.add(w.writeFile(action.getActionId(), " ", message));
    }
}
