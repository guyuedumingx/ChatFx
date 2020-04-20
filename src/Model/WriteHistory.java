package Model;

import java.io.*;
import java.util.Iterator;
import Control.Log4Chat;
import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;


public class WriteHistory {

    private static final String TEXT = "text";
    private static final String TIME = "time";
    private static final String PICTURE = "picture";
    private static final String FROMME = "fromMe";

    private static File hisFile;
    private static Document document;
    private static XMLWriter writer;
    private static ShowFlow flow;
    private static Element rootElement;
    private static Element nodeElement;

    private WriteHistory() {}

    public static void write() {
        write(Operator.getOperator());
    }

    public static void write(Operator o) {
        for(Friend f : o.getFriendList()) {
            write(f);
        }
    }

    private static void write(Friend f) {
        hisFile = f.getHistoryFile();
        document = f.getDocument();
        trywriteDocument();
    }

    private static void trywriteDocument() {
        try {
            writeDocument();
        } catch (IOException e) {
            Log4Chat.printError(e);
        } finally {
            tryCloseWrite();
        }
    }

    private static void writeDocument() throws IOException {
        if (document != null) {
            writer = new XMLWriter(new FileWriter(hisFile), setFormat());
            writer.write(document);
        }
    }

    private static OutputFormat setFormat() {
        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding("UTF-8");
        return format;
    }

    private static void tryCloseWrite() {
            try {
                if (writer != null)
                    writer.close();
            } catch (IOException e) {
                Log4Chat.printError(e);
            }
    }

    public static void saveMsgToXml(ShowFlow showFlow, String text, boolean isFromMe) {
        flow = showFlow;
        add(TEXT,text,isFromMe);
    }

    public static void saveTimeToXml(ShowFlow showFlow,TimeLabel timeLabel) {
        flow = showFlow;
        String date = timeLabel.getTrueTime();
        add(TIME,date);
    }

    public static void saveImageToXml(ShowFlow showFlow,String url) {
        flow = showFlow;
        add("img",url, true);
    }

    public static void clearHistory(Friend f) {
       rootElement = f.getDocument().getRootElement();
       Iterator iterator = rootElement.elementIterator("node");
       while (iterator.hasNext()) {
           rootElement.remove((Element)iterator.next());
       }
    }

    private static void add(String type, String content, boolean isFromeMe) {
        add(type,content);
        nodeElement.addAttribute(FROMME,String.valueOf(isFromeMe));
    }

    private static void add(String type, String content) {
        nodeElement = addNodeElement();
        nodeElement.addAttribute("type",type);
        nodeElement.addAttribute("content",content);
    }

    private static Element addNodeElement() {
        document = flow.getFriend().getDocument();
        rootElement = document.getRootElement();
        return rootElement.addElement("node");
    }
}

