package Model;

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
    private ScrollPane scrollPane;
    private TextFlow showFlow;

    public EditAndSandWindow(AnchorPane operaStage, TextFlow showFlow) {
       this.operaStage = operaStage;
       this.showFlow = showFlow;
       init();
    }

    private void init() {
       editArea = (TextArea)operaStage.lookup("#editArea");
       scrollPane = (ScrollPane)operaStage.lookup("#scrollPane");
    }

    public void addText(boolean isFromMe) {
        addTime();
        MessageItemPane mip = new MessageItemPane(new Message(editArea));
        mip.setTextAlignment(isFromMe);
        appendMessage(mip);
    }

    private void appendMessage(Node Msg) {
        showFlow.getChildren().addAll(Msg);
        scrollPane.setVvalue(1.0);
    }

    public void addTime() {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
        Label time = new Label(sdf.format(d));
        MessageItemPane mip = new MessageItemPane(time);
        mip.setCenter();
        appendMessage(mip);
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


