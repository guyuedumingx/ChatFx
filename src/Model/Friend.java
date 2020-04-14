package Model;


import org.dom4j.Document;


public class Friend extends User {

    Document historyDocument;
    ShowFlow historyFlow;

    public Friend(String name, int account) {
        this.name = name;
        this.account = account;
    }

    public ShowFlow getHistoryFlow() {
        if (historyFlow == null) {
            ReadHistory rHistory = new ReadHistory(this);
            historyFlow =rHistory.readHistory();
            historyDocument = rHistory.getDocument();
        }
        return historyFlow;
    }
}
