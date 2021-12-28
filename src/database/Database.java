package database;
import fileio.ActorInputData;
import fileio.Input;
import fileio.MovieInputData;
import fileio.UserInputData;
import fileio.SerialInputData;
import fileio.ActionInputData;

import java.util.List;

public final class Database {

    private Input myinput = new Input();

    private List<ActorInputData> actors;
    private List<UserInputData> users;
    private List<MovieInputData> movies;
    private List<SerialInputData> serials = myinput.getSerials();
    private List<ActionInputData> actions = myinput.getCommands();

    /**
     *
     * @param input Datele primite din main, punctul de plecare in rezolvarea actiunilor
     */
    public void setData(final Input input) {
        myinput = input;
        actors = myinput.getActors();
        users = myinput.getUsers();
        movies = myinput.getMovies();
        serials = myinput.getSerials();
        actions = myinput.getCommands();
    }

    void setActors(final List<ActorInputData> actors) {
        this.actors = actors;
    }

    public List<ActorInputData> getActors() {
        return actors;
    }

    public void setUsers(final List<UserInputData> users) {
        this.users = users;
    }

    public List<UserInputData> getUsers() {
        return users;
    }

    public void setMovies(final List<MovieInputData> movies) {
        this.movies = movies;
    }

    public List<MovieInputData> getMovies() {
        return movies;
    }

    public void setSerials(final List<SerialInputData> serials) {
        this.serials = serials;
    }

    public List<SerialInputData> getSerials() {
        return serials;
    }

    public void setActions(final List<ActionInputData> actions) {
        this.actions = actions;
    }

    public List<ActionInputData> getActions() {
        return actions;
    }
}
