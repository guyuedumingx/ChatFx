package Model;

import java.io.*;

import Control.Settings;
import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

public class WriteHistory extends History{

    static File historyFile;

    public static void writeHistory() {
        writeHistory(Operator.getOperator());
    }

    public static void writeHistory(Operator o) {
        for(Friend f : o.getFriendList()) {
            writeHistory(f);
        }
    }

    private static void writeHistory(Friend f) {
        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding("GBK");
        XMLWriter writer = null;
        historyFile = Settings.getHistoryFile(f);
        try {
            writer = new XMLWriter(new FileWriter(historyFile));
            writer.write(document);
        }
        catch (IOException e) {

        }
        finally {
            if (writer != null) {
                try {
                    writer.close();
                }
                catch (IOException e) {

                }
            }
        }
    }

    public static void saveMsgToXml(Friend f,Message msg) {
        document = f.historyDocument;
        rootElement = document.getRootElement();
        Element nodeElement = rootElement.addElement("node");
        nodeElement.addAttribute("type","msg");
        nodeElement.addAttribute("content",msg.msgText.getText());
        nodeElement.addAttribute("fromMe",);
    }


    /*
    public void savePicToXml(ImageView img) {
        Element nodeElement = rootElement.addElement("node");
        nodeElement.addAttribute("type","picture");
        nodeElement.addAttribute("path", Settings.getPicPath()+img.getImageURL().toString());
    }

     */
}

