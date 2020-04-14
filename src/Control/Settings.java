package Control;

import Model.Friend;
import Model.Operator;

import java.io.File;
import java.io.IOException;
import java.util.Date;


public class Settings {
    private static Settings set = new Settings();
    private static String resPath = "~/res/";
    private static final String historyFolder = "history/";
    private static final String HISTORYFILENAME = "history.xml";
    private static final String PICTUREFOLDERPATH = "picture";
    private static Date lastMessageTime;

    private Settings() {}

    public static Date getLastMessageTime() {
        if (lastMessageTime==null)
            return new Date();
        else
            return lastMessageTime;
    }

    public static void setLastMessageTime(Date d) {
        lastMessageTime = d;
    }

    public static Settings getSettings() {
        return set;
    }

    public static String getHistoryDic() {
        return resPath + historyFolder + Operator.getOperator().getName() + "/";
    }

    public static File getHistoryFile(Friend f) {
        File file = new File(f.getName()+ HISTORYFILENAME);
        if (!file.exists())
            tryCreateHistoryFile(file);
        return file;
    }

    private static void tryCreateHistoryFile(File f) {
        try {
            f.createNewFile();
        }
        catch (IOException e) {

        }
    }

    public static File getPicPath() {
        File f = new File(resPath + PICTUREFOLDERPATH);
        if (!f.exists()) {
           f.mkdirs();
        }
        return f;
    }


}
