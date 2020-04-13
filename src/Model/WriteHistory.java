package Model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class WriteHistory extends History{
    ObjectOutputStream oos;

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

    public void writeHistory(ShowFlow historyFlow, File historyFile) {
        this.historyFile = historyFile;
        this.historyFlow = historyFlow;
        try {
            trywriteHistoryFile();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            tryCloseOutputStream();
        }
    }
    private void trywriteHistoryFile() throws IOException{
        oos = new ObjectOutputStream(new FileOutputStream(historyFile));
        System.out.println(historyFlow.getChildren());
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

