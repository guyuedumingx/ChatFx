package Control;

import Model.*;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
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
    private VBox friendsBox;
    @FXML
    private AnchorPane showPaneParent;

    private ChangeStage chStage;
    private EditAndSandWindow editAndSand;
    private ContactWindows contactWindows;
    private History history;
    @FXML
    private void addToolBarAction(MouseEvent e) {
       if(e.getTarget() == butClose) {
           history.writeHistory();
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
            editAndSand.addText(true);
        }
    }

    @FXML
    private void addOperaStageKeyAction(KeyEvent e) {
        if (e.getCode() == KeyCode.ENTER) {
            editAndSand.addText(true);
        }
    }


    //javafx的初始化
    public void initialize(URL url, ResourceBundle rb) {
        Operator operator = Operator.getOperator();
        operator.addFriend(new Friend("zhangsan",1112));
        operator.addFriend(new Friend("lisi",1113));
        operator.addFriend(new Friend("lisi",1113));

        chStage = ChangeStage.getChangeStage(primaryStage);
        editAndSand = new EditAndSandWindow(operaStage, showPaneParent);
        contactWindows = new ContactWindows(friendsBox);
        history = new History();
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
