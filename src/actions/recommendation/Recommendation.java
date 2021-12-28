package actions.recommendation;
import actions.Actions;
import database.Database;
import fileio.ActionInputData;
import org.json.simple.JSONArray;

import java.io.IOException;

public final class Recommendation extends Actions {
    /**
     * Creeaza clasele potrivite, apeland metodele din acestea in functie de
     * tipul de comanda din actiunea curenta
     *
     * @param action reprezinta actiunea curenta ce trebuie executata
     * @param mydatabase reprezinta baza de date ce contine toate informatiile despre
     * utilizatori, filme, etc
     * @param array mesajul final ce va fi afisat sub forma de obiect JSON
     * @throws IOException
     */
    public void resolveCommand(final ActionInputData action, final Database mydatabase,
                                final JSONArray array) throws IOException {

        database = mydatabase;
        arrayResult = array;
        users = mydatabase.getUsers();

        if (action.getType().equals("standard")) {
            RecommendStandard recommendStandard = new RecommendStandard();
            recommendStandard.solve(action, mydatabase, array);
        }

        if (action.getType().equals("best_unseen")) {
            RecommendationBestUnseen recommendationBestUnseen = new RecommendationBestUnseen();
            recommendationBestUnseen.solve(action, mydatabase, array);
        }

        if (action.getType().equals("popular")) {
            RecommendationPopular recommendationPopular = new RecommendationPopular();
            recommendationPopular.solve(action, mydatabase, array);
        }

        if (action.getType().equals("favorite")) {
            RecommendationFavorite recommendationFavorite = new RecommendationFavorite();
            recommendationFavorite.solve(action, mydatabase, array);
        }

        if (action.getType().equals("search")) {
            RecommendationSearch recommendationSearch = new RecommendationSearch();
            recommendationSearch.solve(action, mydatabase, array);
        }
    }
}
