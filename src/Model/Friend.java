package Model;

import Control.Settings;
import javafx.scene.text.TextFlow;

import java.io.File;
import java.io.IOException;

public class Friend extends User {

    String histroyFileName;
    Settings set = Settings.getSettings();

    File historyFile;

    TextFlow historyFlow;

    public Friend(String name, int account) {
        this.name = name;
        this.account = account;
        init();
    }

    private void init() {
        histroyFileName = name + ".txt";
        historyFile = new File(histroyFileName);
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

    public TextFlow getHistoryFlow() {
        if (historyFlow == null) {
            historyFlow = new History().readHistory(historyFile);
            historyFlow.prefHeightProperty().bind(EditAndSandWindow.getScrollPane().prefHeightProperty());
            historyFlow.prefWidthProperty().bind(EditAndSandWindow.getScrollPane().prefWidthProperty());
        }
        return historyFlow;
    }
}
