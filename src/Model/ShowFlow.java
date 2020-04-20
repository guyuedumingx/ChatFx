package Model;

import Control.Settings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;


//聊天显示框，每个朋友人手一个
public class ShowFlow extends TextFlow implements Serializable {
    public static boolean isFromMe;
    static TextArea editArea = EditAndSandWindow.editArea;
    static ScrollPane scrollPane = EditAndSandWindow.scrollPane;
    private Friend friend;
    private static Date lastMsgTime;
    private static Date newMsgTime;
    TimeLabel time;

    public ShowFlow(Friend friend) {
        this.friend = friend;
        prefHeightProperty().bind(scrollPane.prefHeightProperty());
        prefWidthProperty().bind(scrollPane.prefWidthProperty());
        addScrollPaneListener();
    }

    public void addScrollPaneListener() {
        this.heightProperty().addListener((obs,oldval, newval)-> {
            scrollPane.setVvalue(this.getHeight());
        });
    }

    public void addTextFromEditArea() {
        String s = editArea.getText().trim();
        editArea.clear();
        addTime();
        addText(s, true);
        WriteHistory.saveMsgToXml(this,s,true);
    }

    public void addText(String msg, boolean isFromMe) {
        this.isFromMe = isFromMe;
        appendMessage(addMessagePane(new Message(msg)));
    }

    public void addPic(String url, boolean isFromMe) {
        Image img = new Image(url);
        EditAndSandWindow.getShowFlow().addPic(new ImageView(img), isFromMe);
        WriteHistory.saveImageToXml(this, url);
    }

    public void addPic(ImageView img, boolean isFromMe) {
        this.isFromMe = isFromMe;
        img.setFitHeight(100);
        img.setFitWidth(60);
        img.setStyle("-fx-background-radius: 20px");
        appendMessage(addMessagePane(img));
    }

    private MessagePane addMessagePane(Node node) {
        MessagePane mp = new MessagePane(this,node);
        if (isFromMe)
            mp.setTextAlignment(MessagePane.SETRIGHT);
        else
            mp.setTextAlignment(MessagePane.SETLEFT);
        return mp;
    }

    private void appendMessage(Node msg) {
        getChildren().addAll(msg);
    }

    public void addTime() {
        Date date = new Date();
        if (lastMsgTime == null) {
            newMsgTime = date;
            time = new TimeLabel(date);
        }
        else {
            ShowFlow.newMsgTime = date;
            time = new TimeLabel(lastMsgTime,newMsgTime);
        }
        if (time.isDifferentTime()) {
            showTime(time);
            WriteHistory.saveTimeToXml(this,time);
        }
        lastMsgTime = newMsgTime;
    }

    public void addTimeFromXml(Date date) {
        lastMsgTime = date;
        newMsgTime = new Date();
        time = new TimeLabel(newMsgTime,lastMsgTime,true);
        showTime(time);
    }

    private void showTime(TimeLabel time) {
        MessagePane mp = new MessagePane(this, time);
        mp.setTextAlignment(MessagePane.SETCENTER);
        appendMessage(mp);
    }

    public Friend getFriend() {
        return friend;
    }

    public void clearHistory() {
        getChildren().clear();
        WriteHistory.clearHistory(friend);
    }
}


//时间标签
class TimeLabel extends Label implements Serializable{

    private Date newMsgTime;
    private Date lastMsgTime;
    private SimpleDateFormat sdf;
    private boolean isDifferentTime = true;

    public TimeLabel(Date lastMsgTime, Date newMsgTime, boolean isFromXml) {
        this(lastMsgTime, newMsgTime);
        if (isFromXml && !isDifferentTime) {
            sdf = new SimpleDateFormat("HH:mm");
            setText(sdf.format(newMsgTime));
        }
    }

    public TimeLabel(Date lastMsgTime, Date newMsgTime) {
        this.lastMsgTime = lastMsgTime;
        this.newMsgTime = newMsgTime;
        init();
    }

    public TimeLabel(Date newMsgTime) {
        this.lastMsgTime = newMsgTime;
        this.newMsgTime = newMsgTime;
        sdf = new SimpleDateFormat("HH:mm");
        setText(sdf.format(newMsgTime));
    }

    private void init() {
        int timeDifference = Math.abs((int)(newMsgTime.getTime() - lastMsgTime.getTime()));

        //时间差的分钟数
        int timeDifferenceMin = timeDifference / (1000*60);
        //时间差的天数
        int timeDifferenceDay = timeDifference / (1000*60*60*24);
        //时间差的年数
        int timeDifferenceYear = timeDifference / (1000*60*60*24*365);

        if(timeDifferenceYear >= 1) {
            sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            setText(sdf.format(newMsgTime));
        }
        else if(timeDifferenceDay > 1) {
            sdf = new SimpleDateFormat("MM/dd HH:mm");
            setText(sdf.format(newMsgTime));
        }
        else if(timeDifferenceMin > 3) {
            sdf = new SimpleDateFormat("HH:mm");
            setText(sdf.format(newMsgTime));
        }
        else
           isDifferentTime = false;
    }

    public boolean isDifferentTime() {
       return isDifferentTime;
    }

    public String getTrueTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        return simpleDateFormat.format(newMsgTime);
    }
}


//实现消息的包装, 气泡
class Message extends TextFlow implements Serializable{
    public Text msgText = new Text();
    private String hisBgColor = "#e4dfd7";
    private String showColor = "#cad3c3";

    public Message(String messageText) {
        msgText.setText(messageText);
        init();
        getChildren().addAll(msgText);
    }

    private void init() {
        //一行最大的宽度
        setMaxWidth(500);
        getStyleClass().add("bubble");
        //内间距
        setPadding(new Insets(10));
        setTextColor();
        setTextSize();
        setBubbleColor();
    }

    private void setTextColor() {
        String color = Settings.getMsgColor();
        msgText.setFill(Paint.valueOf(color));
    }

    private void setBubbleColor() {
        if(ShowFlow.isFromMe)
            showColor = Settings.getBubbleColor();
        else
            showColor = hisBgColor;
        setStyle("-fx-background-color: " + showColor);
    }

    private void setTextSize() {
       int textSize = Settings.getTextSize();
       msgText.setStyle("-fx-font-size: " + textSize);
    }
}

//把每个消息都放到一个面板中，头像也放到面板中，在把该面板添加到聊天框中
class MessagePane extends FlowPane implements Serializable {

    public static final int SETLEFT = 1;
    public static final int SETCENTER = 2;
    public static final int SETRIGHT = 3;

    private ShowFlow showFlow;
    private boolean isFromMe = ShowFlow.isFromMe;

    public MessagePane(Node node) {
        prefHeight(80);
        prefWidth(80);
        getChildren().addAll(node);
    }

    public MessagePane(ShowFlow showFlow, Node node) {
        this.showFlow = showFlow;
        //如果是时间，则不需要加头像
        if (node instanceof TimeLabel)
            getChildren().addAll(node);
        else {
            if (isFromMe)
                getChildren().addAll(containMsgPane(node), addprofilePic());
            else
                getChildren().addAll(addprofilePic(), containMsgPane(node));

        }
        prefWidthProperty().bind(showFlow.widthProperty());
        setPadding(new Insets(5));
    }

    //为显示消息的部分单独添加一个布局
    private TextFlow containMsgPane(Node node) {
        TextFlow tf = new TextFlow();
        tf.setPadding(new Insets(5));
        tf.getChildren().addAll(node);
        tf.setTextAlignment(TextAlignment.RIGHT);
        return tf;
    }

    //加载头像
    private ImageView addprofilePic() {
        Image img;
        if (isFromMe)
            img  = Operator.getOperator().getProfilePic();
        else
            img = showFlow.getFriend().getProfilePic();
        ImageView imgView = new ImageView(img);
        imgView.setFitWidth(40);
        imgView.setFitHeight(40);
        imgView.setStyle("-fx-background-radius: 10");
        return imgView;
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
        setAlignment(Pos.TOP_RIGHT);
    }

    private void setLeft() {
        setAlignment(Pos.TOP_LEFT);
    }

    public void setCenter() {
        setAlignment(Pos.TOP_CENTER);
    }
}
