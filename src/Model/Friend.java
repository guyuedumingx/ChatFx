package Model;

import Control.Settings;

import java.io.File;

public class Friend extends User {

    String histroyFileName;
    Settings set = Settings.getSettings();

    File historyFile;

    ShowFlow historyFlow;

    public Friend(String name, int account) {
        this.name = name;
        this.account = account;
        init();
    }

    private void init() {
        histroyFileName = name + ".ser";
        historyFile = new File(histroyFileName);
    }

    public ShowFlow getHistoryFlow() {
        if (historyFlow == null) {
            historyFlow = new History().readHistory(historyFile);
            historyFlow.prefHeightProperty().bind(EditAndSandWindow.getScrollPane().prefHeightProperty());
            historyFlow.prefWidthProperty().bind(EditAndSandWindow.getScrollPane().prefWidthProperty());
        }
        return historyFlow;
    }
}
