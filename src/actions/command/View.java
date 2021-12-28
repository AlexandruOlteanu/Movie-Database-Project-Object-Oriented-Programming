package actions.command;

import database.Database;
import fileio.ActionInputData;
import fileio.UserInputData;
import fileio.Writer;
import org.json.simple.JSONArray;

import java.io.IOException;
import java.util.List;

public final class View {
    /**
     * Cresc nuamrul de vizualizari din istoricul utilizatorului curent in
     * dreptul unui film / serial. Istoricul fiind reprezentat de o mapa,
     * ma folosesc de suprascriere pentru a repune cheia cu noua valoare in
     * aceasta mapa
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
                int value = 1;
                if (user.getHistory().containsKey(action.getTitle())) {
                    value = user.getHistory().get(action.getTitle()) + 1;
                    user.getHistory().put(action.getTitle(), value);
                } else {
                    user.getHistory().put(action.getTitle(), 1);
                }
                Writer w = new Writer("test");
                String message = "success -> " + action.getTitle()
                       + " was viewed with total views of " + value;
                array.add(w.writeFile(action.getActionId(), " ", message));
            }
        }
    }
}
