package Model;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
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

    private void appendMessage(Node Msg, boolean isfromMe) {
        Text lineSeparate = new Text(System.lineSeparator());
        showFlow.getChildren().addAll(Msg,lineSeparate);
        scrollPane.setVvalue(1.0);
        setFromeMe(isfromMe);
    }

    public void sandMyText() {
        Message msg = new Message(editArea);
        appendMessage(msg,true);
        sandTime();
    }

    public void sandTime() {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd mm:ss");
        String s = sdf.format(d);
        Label time = new Label(s);
        appendMessage(time,false);
    }

    private void setFromeMe(boolean bl) {
        if (bl) {
            showFlow.setTextAlignment(TextAlignment.RIGHT);
        }
        else {
            showFlow.setTextAlignment(TextAlignment.LEFT);
        }
    }

}

class Message extends TextFlow{
    private Text messageText = new Text();
    private String myBgColor = "#cad3c3";
    private String hisBgColor = "e4dfd7";
    private String showColor = "#cad3c3";

    public Message(TextArea editArea) {
        this();
        messageText.setText(editArea.getText());
        editArea.clear();
        getChildren().addAll(messageText);

    }

    public Message() {
       init();
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

    public void setFromMe(boolean bl) {
        if (bl) {
            setTextAlignment(TextAlignment.RIGHT);
            setMessageBackgroundColor(myBgColor);
        }
        else {
            setTextAlignment(TextAlignment.LEFT);
            setMessageBackgroundColor(hisBgColor);
        }
    }

    public void setMessageBackgroundColor(String showColor) {
        this.showColor = showColor;
    }

}

