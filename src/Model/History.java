package Model;


import org.dom4j.Document;
import org.dom4j.Element;

import java.io.*;


public class History {
    File historyFile;
    ShowFlow historyFlow;
    static Document document;
    static Element rootElement;

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
