package Model;

import Control.Settings;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import java.util.Iterator;


public class ReadHistory extends History {
    Friend friend;
    static Document document;
    static Element rootElement;
    ShowFlow historyFlow;

    public ReadHistory(Friend friend) {
        this.historyFile = Settings.getHistoryFile(friend);
        this.friend = friend;
    }

    public ShowFlow readHistory() {
        historyFlow = new ShowFlow(friend);

        try {
            setDefaultHistoryFile();
            readDocument(friend);
            readMsg();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return historyFlow;
    }

    private void readMsg() {
        for (Iterator<Element> root = rootElement.elementIterator("node"); root.hasNext();) {
            Element node = root.next();
            System.out.println(node.element("type").getName());
            if ("msg".equals(node.element("type").getText())) {
                historyFlow.addText(node.element("content").getText(),
                        Boolean.getBoolean(node.element("fromMe").getText()));
            }
        }
    }

    public void readDocument(Friend f) {
        try {
            SAXReader saxReader = new SAXReader();
            document = saxReader.read(historyFile);
            rootElement = document.getRootElement();
        } catch (Exception e) {
            document = DocumentHelper.createDocument();
            rootElement = document.addElement("root");
            rootElement.addComment("This is a history file");
        }
    }

    public Document getDocument() {
        return document;
    }
}

