package Model;

import Control.Log4Chat;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;


public class ReadHistory {
    private static Document document;
    private static Element rootElement;
    private static ShowFlow flow;
    private static File hisFile;
    private static Element node;

    private ReadHistory() {}

    public static ShowFlow readShowFlow(Friend f) {
        document = f.getDocument();
        flow = new ShowFlow(f);
        tryReadMsg();
        return flow;
    }

    private static void tryReadMsg() {
        try {
            readMsg();
        } catch (Exception e) {
            Log4Chat.printError(e);
        }
    }

    private static void readMsg() {
        rootElement = document.getRootElement();
        for (Iterator<Element> root = rootElement.elementIterator(); root.hasNext();) {
            node = root.next();
            readElement();
        }
    }

    private static void readElement() {
        String type = node.attribute("type").getText();
        String content = node.attribute("content").getText();
        addElementToFlow(type,content);
    }

    private static void addElementToFlow(String type, String content) {
        if ("text".equals(type)) {
            boolean fromMe = Boolean.parseBoolean(node.attribute("fromMe").getText());
            flow.addText(content, fromMe);
        }
        else if("time".equals(type)) {
            flow.addTimeFromXml(parseTime(content));
        }
        else if("img".equals(type)) {
            boolean fromMe = Boolean.parseBoolean(node.attribute("fromMe").getText());
            flow.addPic(loadImage(content),fromMe);
        }
    }

    private static ImageView loadImage(String url) {
        return new ImageView(new Image(url));
    }

    private static Date parseTime(String str) {
        try {
            return tryParseTime(str);
        } catch (Exception e) {
            Log4Chat.printError(e);
        }
        return new Date();
    }

    private static Date tryParseTime(String str) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        return sdf.parse(str);
    }

    public static Document tryReadDocument(Friend f) {
        try {
            readDocument(f);
        } catch (Exception e) {
            creatDocument();
        }
        return document;
    }

    private static void readDocument(Friend f) throws Exception {
        hisFile = f.getHistoryFile();
        setDefaultHistoryFile();
        SAXReader saxReader = new SAXReader();
        document = saxReader.read(hisFile);
        rootElement = document.getRootElement();
    }

    private static void creatDocument() {
        document = DocumentHelper.createDocument();
        rootElement = document.addElement("root");
        rootElement.addComment("This is a history file");
    }

    private static void setDefaultHistoryFile(){
        if (!hisFile.exists())
            tryCreateHistoryFile();
    }

    private static void tryCreateHistoryFile() {
        try {
            hisFile.createNewFile();
        } catch (IOException e) {
            Log4Chat.printError(e);
        }
    }
}

