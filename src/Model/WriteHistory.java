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
            writer = new XMLWriter(new FileWriter(historyFile),format);
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

    public static void saveMsgToXml(ShowFlow showFlow,String str,boolean isFromMe) {
        document = showFlow.getFriend().historyDocument;
        rootElement = document.getRootElement();
        Element nodeElement = rootElement.addElement("node");
        nodeElement.addAttribute("type","msg");
        nodeElement.addAttribute("content",str);
        nodeElement.addAttribute("fromMe",String.valueOf(isFromMe));
    }


    /*
    public void savePicToXml(ImageView img) {
        Element nodeElement = rootElement.addElement("node");
        nodeElement.addAttribute("type","picture");
        nodeElement.addAttribute("path", Settings.getPicPath()+img.getImageURL().toString());
    }

     */
}

