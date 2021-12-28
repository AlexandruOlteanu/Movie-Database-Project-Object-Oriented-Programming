package actions;

import database.Database;

import fileio.ActorInputData;
import fileio.MovieInputData;
import fileio.UserInputData;
import fileio.SerialInputData;
import org.json.simple.JSONArray;

import java.util.List;

public  class Actions {

    protected List<ActorInputData> actors;
    protected List<UserInputData> users;
    protected List<MovieInputData> movies;
    protected List<SerialInputData> serials;
    protected JSONArray arrayResult = new JSONArray();
    protected Database database;

    /**
     *
     * @return Imi returneaza baza de date
     */
    public Database getDatabase() {
        return database;
    }
}
