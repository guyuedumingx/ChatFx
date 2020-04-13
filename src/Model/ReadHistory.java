package Model;

import Control.Settings;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class ReadHistory extends History {
    ObjectInputStream ois;

    public ReadHistory(Friend friend) {
        this.historyFile = Settings.getHistoryFile(friend);
    }

    public ShowFlow readHistory() {
        try {
            setDefaultHistoryFile();
            tryReadHistoryFile();
        }
        catch (Exception e) {
            e.printStackTrace();
            historyFlow = new ShowFlow();
        }
        finally {
            tryCloseInputStream();
        }
        return historyFlow;
    }

    private void tryReadHistoryFile() throws IOException, ClassNotFoundException{
        ois = new ObjectInputStream(new FileInputStream(historyFile));
        historyFlow = (ShowFlow)ois.readObject();
        System.out.println(historyFlow.getChildren());
    }

    private void tryCloseInputStream() {
        try {
            if (ois != null)
                ois.close();
        }
        catch (IOException e) {

        }
    }
}

