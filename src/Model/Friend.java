package Model;

import Control.Settings;
import javafx.scene.text.TextFlow;

import java.io.File;
import java.io.IOException;

public class Friend extends User {

    String histroyFileName = name + ".txt";
    Settings set = Settings.getSettings();

    File historyFile = new File(histroyFileName);

    TextFlow historyFlow;

    public Friend(String name, int account) {
        this.name = name;
        this.account = account;
        init();
    }

    private void init() {
        setDefaultHistoryFile();
    }

    private void setDefaultHistoryFile(){
        if (!historyFile.exists())
            tryCreateHistoryFile();
    }

    private void tryCreateHistoryFile() {
        try {
            historyFile.createNewFile();
        }
        catch (IOException e) {

        }
    }

    private TextFlow getHistoryFlow() {
        if (historyFlow == null)
            return new History().readHistory(historyFile);
        else
            return historyFlow;
    }
}
