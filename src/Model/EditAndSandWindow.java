package Model;

import Control.Settings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import java.text.SimpleDateFormat;
import java.util.Date;


public class EditAndSandWindow {
    private AnchorPane operaStage;
    private TextArea editArea;
    private static ScrollPane scrollPane;
    private static TextFlow showFlow;
    private static AnchorPane showPaneParent;

    public EditAndSandWindow(AnchorPane operaStage, AnchorPane showPaneParent) {
       this.operaStage = operaStage;
       EditAndSandWindow.showPaneParent = showPaneParent;
       init();
    }

    private void init() {
       editArea = (TextArea)operaStage.lookup("#editArea");
       scrollPane = (ScrollPane)operaStage.lookup("#scrollPane");
    }

    public void addText(boolean isFromMe) {
        MessageItemPane mip = new MessageItemPane(new Message(editArea));
        mip.setTextAlignment(isFromMe);
        appendMessage(mip);
    }

    private void appendMessage(Node Msg) {
        showFlow.getChildren().addAll(addTime(),Msg);
        scrollPane.setVvalue(1.0);
    }

    public MessageItemPane addTime() {
        TimeLabel time = new TimeLabel();
        MessageItemPane mip = new MessageItemPane(time);
        mip.setCenter();
        return mip;
    }

    class MessageItemPane extends FlowPane {

        public MessageItemPane(Node node) {
            getChildren().addAll(node);
            prefWidthProperty().bind(showFlow.widthProperty());
            setPadding(new Insets(5));
        }
        public void setTextAlignment(boolean isFromMe) {
            if (isFromMe)
                setRight();
            else
                setLeft();
        }

        private void setRight() {
            setAlignment(Pos.BOTTOM_RIGHT);
        }

        private void setLeft() {
            setAlignment(Pos.BOTTOM_LEFT);
        }

        public void setCenter() {
            setAlignment(Pos.BOTTOM_CENTER);
        }
    }

    public static ScrollPane getScrollPane() {
        return scrollPane;
    }

    public static void setShowFlow(Friend f) {
        EditAndSandWindow.showFlow = f.getHistoryFlow();
        EditAndSandWindow.showPaneParent.getChildren().clear();
        EditAndSandWindow.showPaneParent.getChildren().add(showFlow);
    }
    public static TextFlow getShowFlow() {
        return showFlow;
    }
}

class TimeLabel extends Label {
    private Date nowTime = new Date();
    private Date lastMessageTime = Settings.getLastMessageTime();
    private SimpleDateFormat sdf;

    public TimeLabel() {
        init();
    }

    private void init() {
        //时间差的分钟数
        int timeDifferenceMin = (int)(nowTime.getTime() - lastMessageTime.getTime()) / (1000*60);
        //时间差的天数
        int timeDifferenceDay = timeDifferenceMin / (60*24);
        //时间差的年数
        int timeDifferenceYear = timeDifferenceDay / (30*12);

        if (timeDifferenceMin < 3) {
            setText("");
        }
        else if (timeDifferenceDay < 1) {
            sdf = new SimpleDateFormat("HH:mm");
            setText(sdf.format(nowTime));
        }
        else if (timeDifferenceDay < 3){
            setText(timeDifferenceDay + "天前");
        }
        else if (timeDifferenceYear < 1) {
           sdf = new SimpleDateFormat("MM/dd HH:mm");
           setText(sdf.format(nowTime));
        }
        else {
            sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            setText(sdf.format(nowTime));
        }
        Settings.setLastMessageTime(nowTime);
    }

}

//实现消息的包装
class Message extends TextFlow{
    private Text messageText = new Text();
    private String myBgColor = "#cad3c3";
    private String hisBgColor = "e4dfd7";
    private String showColor = "#cad3c3";

    public Message(TextArea editArea) {
        init();
        messageText.setText(editArea.getText().trim());
        editArea.setText("");
        getChildren().addAll(messageText);
    }

    private void init() {
        //一行最大的宽度
        setMaxWidth(500);

        getStyleClass().add("bubble");

        //内间距
        setPadding(new Insets(10));

        String Color = "-fx-background-color: " + showColor;

        setStyle(Color);
    }

    public void setMessageBackgroundColor(String showColor) {
        this.showColor = showColor;
    }
}


