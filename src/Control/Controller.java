package Control;

import Model.EditAndSandWindow;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable{
    @FXML
    private AnchorPane primaryStage, operaStage;
    @FXML
    private ImageView butClose, butContact, butSettings;
    @FXML
    private ImageView butEmoji, butImage, butGif, butSand;

    @FXML
    private TextFlow showFlow;
    @FXML
    private VBox showBox;

    private ChangeStage chStage;
    private EditAndSandWindow editAndSand;

    @FXML
    private void addToolBarAction(MouseEvent e) {
       if(e.getTarget() == butClose) {
           System.exit(0);
       }
       else if(e.getTarget() == butContact) {
            chStage.toContact();
       }
       else if(e.getTarget() == butSettings) {
            chStage.toSetStage();
       }
    }

    @FXML
    private void addOperaStageMouseAction(MouseEvent e) {
        if(e.getTarget() == butSand) {
            editAndSand.sandMyText();
        }
    }


    //javafx的初始化
    public void initialize(URL url, ResourceBundle rb) {
        chStage = new ChangeStage(primaryStage);
        editAndSand = new EditAndSandWindow(operaStage, showFlow, showBox);
    }
}

class ChangeStage {
    private AnchorPane primaryStage;
    private AnchorPane setStage;
    private AnchorPane contactStage;
    private AnchorPane operaStage;

    public ChangeStage(AnchorPane primaryStage) {
        this.primaryStage = primaryStage;
        init();
    }

    private void init() {
        primaryStage = (AnchorPane)primaryStage.lookup("#primaryStage");
        setStage = (AnchorPane)primaryStage.lookup("#setStage");
        contactStage = (AnchorPane)primaryStage.lookup("#contactStage");
        operaStage = (AnchorPane)primaryStage.lookup("#operaStage");
    }

    public void toSetStage() {
        contactStage.setVisible(false);
        operaStage.setVisible(false);
        if (setStage.isVisible())
            setStage.setVisible(false);
        else
            setStage.setVisible(true);
    }

    public void toContact() {
        setStage.setVisible(false);
        operaStage.setVisible(false);
        if (contactStage.isVisible())
            contactStage.setVisible(false);
        else
            contactStage.setVisible(true);
    }

    public void toOpera() {
        if (operaStage.isVisible())
            operaStage.setVisible(true);
        else
            operaStage.setVisible(false);
    }

}

//拖动窗口
class DragWindowHandler implements EventHandler<MouseEvent> {

    private Stage stage;
    //get scene's location
    private double oldStageX;
    private double oldStageY;
    private double oldScreenX;
    private double oldScreenY;

    public DragWindowHandler(Stage stage) {
        this.stage = stage;
    }

    public void handle(MouseEvent e) {
        //鼠标按下的事件
        if (e.getEventType() == MouseEvent.MOUSE_PRESSED) {
            this.oldStageX = this.stage.getX();
            this.oldStageY = this.stage.getY();
            this.oldScreenX = e.getScreenX();
            this.oldScreenY = e.getScreenY();
        }

        //鼠标拖动的事件
        else if (e.getEventType() == MouseEvent.MOUSE_DRAGGED) {
            this.stage.setX(e.getScreenX() - this.oldScreenX + this.oldStageX);
            this.stage.setY(e.getScreenY() - this.oldScreenY + this.oldStageY);
        }
    }
    public void mix() {
        stage.setIconified(true);
    }
}
