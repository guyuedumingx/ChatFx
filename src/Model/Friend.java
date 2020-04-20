package Model;


import Control.Settings;
import javafx.scene.image.Image;
import org.dom4j.Document;
import java.io.File;


//联系人
public class Friend extends User {

    private ShowFlow historyFlow;
    private File historyFile;
    private Document historyDocument;
    private FButton fButton;

    public Friend(String name, int account) {
        this.name = name;
        this.account = account;
        profilePic = new Image("file:..\\..\\1.jpg");
    }

    public ShowFlow getHistoryFlow() {
        if (historyFlow == null) {
            loadHistoryDocument();
            historyFlow = ReadHistory.readShowFlow(this);
        }
        return historyFlow;
    }

    public void loadHistoryDocument() {
        loadHistoryFile();
        historyDocument = ReadHistory.tryReadDocument(this);
    }

    public void loadHistoryFile() {
        historyFile = Settings.getHistoryFile(this);
    }

    public Document getDocument() {
        return historyDocument;
    }

    public File getHistoryFile() {
        return historyFile;
    }

    public Image getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(Image profilePic) {
       this.profilePic = profilePic;
    }

    public void setfButton(FButton fButton) {
        this.fButton = fButton;
    }



}
