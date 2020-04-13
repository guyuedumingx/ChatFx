package Model;

import Control.Settings;
import javafx.scene.text.TextFlow;
import java.io.*;


public class History {
    Settings set = Settings.getSettings();
    ObjectInputStream ois;
    ObjectOutputStream oos;
    TextFlow historyFlow;

    public TextFlow readHistory(File historyFile) {
        try {
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


    public void writeHistory(TextFlow historyFlow, File historyfile) {
       this.historyFlow = historyFlow;
       try {
           trywriteHistoryFile(historyfile);
       }
       catch (IOException e) {

       }
       finally {
          tryCloseOutputStream();
       }
    }
    private void trywriteHistoryFile(File historyfile) throws IOException{
        oos = new ObjectOutputStream(new FileOutputStream(historyfile));
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
}
