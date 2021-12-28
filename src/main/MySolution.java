package main;

import actions.command.Command;
import actions.query.Query;
import actions.recommendation.Recommendation;
import database.Database;
import fileio.ActionInputData;
import fileio.Input;
import org.json.simple.JSONArray;

import java.io.IOException;
import java.util.List;

class MySolution {

    private Input myinput = new Input();
    private JSONArray arrayResult = new JSONArray();
    private Database mydatabase = new Database();

    public void setMyinput(final Input myinput) {
        this.myinput = myinput;
    }

    /**
     *
     * @throws IOException
     */
    public void work() throws IOException {

        mydatabase.setData(myinput);
        List<ActionInputData> myactions = mydatabase.getActions();

        for (ActionInputData currentAction : myactions) {
            if (currentAction.getActionType().equals("command")) {
                 Command command = new Command();
                 command.resolveCommand(currentAction, mydatabase, arrayResult);
            }
            if (currentAction.getActionType().equals("query")) {
                Query query = new Query();
                query.resolveCommand(currentAction, mydatabase, arrayResult);
            }
            if (currentAction.getActionType().equals("recommendation")) {
                Recommendation recommendation = new Recommendation();
                recommendation.resolveCommand(currentAction, mydatabase, arrayResult);
            }
        }
    }

    /**
     *
     * @return
     */
    public JSONArray getFinal() {
        return arrayResult;
    }
}
