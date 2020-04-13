package Model;

import Control.Settings;

import java.io.File;

public class Friend extends User {

    File historyFile;
    ShowFlow historyFlow;

    public Friend(String name, int account) {
        this.name = name;
        this.account = account;
        init();
    }

    private void init() {
        historyFile = Settings.getHistoryFile(this);
    }

    public ShowFlow getHistoryFlow() {
        if (historyFlow == null) {
            historyFlow = new ReadHistory(this).readHistory();
            historyFlow.prefHeightProperty().bind(EditAndSandWindow.getScrollPane().prefHeightProperty());
            historyFlow.prefWidthProperty().bind(EditAndSandWindow.getScrollPane().prefWidthProperty());
        }
        return historyFlow;
    }
}
