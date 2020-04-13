package Model;

import Control.Settings;

import java.io.*;

public class History {
    File historyFile;
    ShowFlow historyFlow;

    void setDefaultHistoryFile(){
        if (!historyFile.exists())
            tryCreateHistoryFile();
    }

    void tryCreateHistoryFile() {
        try {
            historyFile.createNewFile();
        }
        catch (IOException e) {

        }
    }

}
