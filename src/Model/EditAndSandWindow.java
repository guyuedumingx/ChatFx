package Model;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.TextFlow;


//负责聊天操作区域，可以向里面添加朋友的聊天框
public class EditAndSandWindow {
    private static AnchorPane operaStage;
    public static TextArea editArea;
    public static ScrollPane scrollPane;
    public static ShowFlow showFlow;
    private static TextFlow showPaneParent;

    public EditAndSandWindow(AnchorPane operaStage, TextFlow showPaneParent) {
       this.operaStage = operaStage;
       EditAndSandWindow.showPaneParent = showPaneParent;
       init();
    }

    private void init() {
       editArea = (TextArea)operaStage.lookup("#editArea");
       scrollPane = (ScrollPane)operaStage.lookup("#scrollPane");
    }

    public static void setShowFlow(Friend f) {
        showFlow = f.getHistoryFlow();
        showPaneParent.getChildren().clear();
        showPaneParent.getChildren().add(showFlow);
    }

    public static ShowFlow getShowFlow() {
        return showFlow;
    }
}



