package actions.query;

import actions.Actions;
import database.Database;
import fileio.ActionInputData;
import org.json.simple.JSONArray;

import java.io.IOException;

public final class Query extends Actions {
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

        if (action.getCriteria().equals("average")) {
            Average average = new Average();
            average.solve(action, mydatabase, array);
        }

        if (action.getCriteria().equals("awards")) {
            Awards awards = new Awards();
            awards.solve(action, mydatabase, array);
        }

        if (action.getCriteria().equals("filter_description")) {
            FilterDescription filterDescription = new FilterDescription();
            filterDescription.solve(action, mydatabase, array);
        }

        if (action.getCriteria().equals("ratings")) {
            VideoRating videoRating = new VideoRating();
            videoRating.solve(action, mydatabase, array);
        }

        if (action.getCriteria().equals("longest")) {
            VideoLongest videoLongest = new VideoLongest();
            videoLongest.solve(action, mydatabase, array);
        }

        if (action.getCriteria().equals("most_viewed")) {
            VideoMostViewed videoMostViewed = new VideoMostViewed();
            videoMostViewed.solve(action, mydatabase, array);
        }

        if (action.getCriteria().equals("favorite")) {
            VideoFavorite videoFavorite = new VideoFavorite();
            videoFavorite.solve(action, mydatabase, array);
        }

        if (action.getCriteria().equals("num_ratings")) {
            UserNumberRatings userNumberRatings = new UserNumberRatings();
            userNumberRatings.solve(action, mydatabase, array);
        }
    }
}
