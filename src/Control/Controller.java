package Control;

import Model.*;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
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
    private ImageView butClose, butContact, butSettings, butTray;
    @FXML
    private ImageView butEmoji, butImage, butGif, butSand;
    @FXML
    private Button butClearHistory;
    @FXML
    private ImageView butPath;
    @FXML
    private TextField filePath;
    @FXML
    private VBox friendsBox;
    @FXML
    private TextFlow showPaneParent;
    @FXML
    private ScrollPane emojiScrollPane, gifScrollPane;

    private ChangeStage chStage;
    private EditAndSandWindow editAndSand;
    private ContactWindows contactWindows;

    //定义工具栏按键操作
    @FXML
    private void addToolBarAction(MouseEvent e) {
       if(e.getTarget() == butClose) {
           chStage.toExit();
       }
       else if(e.getTarget() == butContact) {
            chStage.toContact();
       }
       else if(e.getTarget() == butSettings) {
            chStage.toSetStage();
       }
       else if(e.getTarget() == butTray) {
            chStage.toSystemTray();
       }
       else if (e.getTarget() == butPath) {
           //设置历史文件路径
           String url = chStage.getPath();
           filePath.setText(url);
       }
    }

    //定义编辑工具栏操作
    @FXML
    private void addEditBarAction(MouseEvent e) {
        if(e.getTarget() == butGif) {
            if(gifScrollPane.isVisible())
                gifScrollPane.setVisible(false);
            else {
                GifSelector gifSelector = new GifSelector(primaryStage);
                gifScrollPane.setVisible(true);
            }
        }

        else if(e.getTarget() == butImage) {
            chStage.openPicture();
        }
        else if(e.getTarget() == butEmoji) {
            if(emojiScrollPane.isVisible())
                emojiScrollPane.setVisible(false);
            else
                emojiScrollPane.setVisible(true);
        }
    }

    //清除历史记录
    @FXML
    private void addClearHistoryAction(MouseEvent e) {
        if(e.getButton() == MouseButton.PRIMARY) {
            EditAndSandWindow.getShowFlow().clearHistory();
        }
    }

    //发送操作
    @FXML
    private void addOperaStageMouseAction(MouseEvent e) {
        if(e.getTarget() == butSand) {
            EditAndSandWindow.showFlow.addTextFromEditArea();
        }
    }
    @FXML
    private void addOperaStageKeyAction(KeyEvent e) {
        if (e.getCode() == KeyCode.ENTER) {
            EditAndSandWindow.showFlow.addTextFromEditArea();
        }
    }

    //javafx的初始化
    public void initialize(URL url, ResourceBundle rb) {
        Operator operator = Operator.getOperator();

        //纯属举个例子
        operator.addFriend(new Friend("奥巴马",1112));
        operator.addFriend(new Friend("川建国",1113));
        operator.addFriend(new Friend("马云",1114));
        operator.addFriend(new Friend("马化腾",1115));
        operator.addFriend(new Friend("周立波",1116));
        operator.addFriend(new Friend("普京",1117));
        operator.addFriend(new Friend("霍金",1118));
        operator.addFriend(new Friend("亚伯拉罕 艾因斯坦",1119));
        operator.addFriend(new Friend("王五",11110));
        operator.addFriend(new Friend("李氏",11111));
        operator.addFriend(new Friend("赵六",11112));

        new Settings(primaryStage);
        chStage = ChangeStage.getChangeStage(primaryStage);
        new EditAndSandWindow(operaStage, showPaneParent);
        new ContactWindows(friendsBox);
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
