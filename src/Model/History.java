package Model;

import Control.Settings;
import javafx.scene.text.TextFlow;
import java.io.*;


public class History {
    Settings set = Settings.getSettings();
    ObjectInputStream ois;
    ObjectOutputStream oos;
    TextFlow historyFlow;
    File historyFile;

    public TextFlow readHistory(File historyFile) {
        this.historyFile = historyFile;
        try {
            setDefaultHistoryFile();
            tryReadHistoryFile(historyFile);
        }
        catch (Exception e) {
            historyFlow = new TextFlow();
        }
        finally {
            tryCloseInputStream();
        }
        return historyFlow;
    }

    private void tryReadHistoryFile(File historyFile) throws IOException, ClassNotFoundException{
        ois = new ObjectInputStream(new FileInputStream(historyFile));
        historyFlow = (TextFlow)ois.readObject();
    }

    private void tryCloseInputStream() {
        try {
            if (ois != null)
                ois.close();
        }
        catch (IOException e) {

        }
    }

    public void writeHistory() {
        writeHistory(Operator.getOperator());
    }

    public void writeHistory(Operator o) {
        for(Friend f : o.getFriendList()) {
            writeHistory(f);
        }
    }

    public void writeHistory(Friend f) {
        writeHistory(f.getHistoryFlow(), f.historyFile);
    }

    public void writeHistory(TextFlow historyFlow, File historyFile) {
        this.historyFile = historyFile;
        this.historyFlow = historyFlow;
       try {
           setDefaultHistoryFile();
           trywriteHistoryFile();
       }
       catch (IOException e) {

       }
       finally {
          tryCloseOutputStream();
       }
    }
    private void trywriteHistoryFile() throws IOException{
        oos = new ObjectOutputStream(new FileOutputStream(historyFile));
        oos.writeObject(historyFlow);
    }
    private void tryCloseOutputStream() {
        try {
            if (oos != null)
                oos.close();
        }
        catch (IOException e) {

        }
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

}
