package Model;

import Control.Settings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ShowFlow extends TextFlow implements Serializable {
    static TextArea editArea = EditAndSandWindow.editArea;
    static ScrollPane scrollPane = EditAndSandWindow.scrollPane;
    public static boolean isFromMe;

    public ShowFlow() {
        prefHeightProperty().bind(scrollPane.prefHeightProperty());
        prefWidthProperty().bind(scrollPane.prefWidthProperty());
    }

    public void addText(boolean isFromMe) {
        ShowFlow.isFromMe = isFromMe;
        appendMessage(addMessageTtemPane(new Message(editArea)));
    }

    public void addText(String msg, boolean isFromMe) {
        ShowFlow.isFromMe = isFromMe;
        appendMessage(addMessageTtemPane(new Message(msg)));
    }

    public void addPic(ImageView img, boolean isFromMe) {
        ShowFlow.isFromMe = isFromMe;
        appendMessage(addMessageTtemPane(img));
    }

    private  MessageItemPane addMessageTtemPane(Node node) {
        MessageItemPane mip = new MessageItemPane(this,node);
        if (isFromMe)
            mip.setTextAlignment(MessageItemPane.SETRIGHT);
        else
            mip.setTextAlignment(MessageItemPane.SETLEFT);
        return mip;
    }

    private void appendMessage(Node msg) {
        getChildren().addAll(addTime(), msg);
        scrollPane.setVvalue(1.0);
    }

    public MessageItemPane addTime() {
        TimeLabel time = new TimeLabel();
        MessageItemPane mip = new MessageItemPane(this,time);
        mip.setTextAlignment(MessageItemPane.SETCENTER);
        return mip;
    }
}

class TimeLabel extends Label implements Serializable{
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
class Message extends ShowFlow implements Serializable{
    public Text msgText = new Text();
    private String myBgColor = "#cad3c3";
    private String hisBgColor = "e4dfd7";
    private String showColor = "#cad3c3";

    public Message(TextArea editArea) {
        this(editArea.getText().trim());
        editArea.setText("");
    }

    public Message(String messageText) {
        msgText.setText(messageText);
        init();
        getChildren().addAll(msgText);
        WriteHistory.saveMsgToXml(this);
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

class MessageItemPane extends FlowPane implements Serializable {

    public static final int SETLEFT = 1;
    public static final int SETCENTER = 2;
    public static final int SETRIGHT = 3;

    public MessageItemPane(ShowFlow showFlow, Node node, int align) {
        this(showFlow, node);
        setTextAlignment(align);
    }

    public MessageItemPane(ShowFlow showFlow, Node node) {
        getChildren().addAll(node);
        prefWidthProperty().bind(showFlow.widthProperty());
        setPadding(new Insets(5));
    }

    public void setTextAlignment(int align) {
        switch (align) {
            case SETLEFT:
                setLeft();
                break;
            case SETCENTER:
                setCenter();
                break;
            case SETRIGHT:
                setRight();
                break;
        }
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
