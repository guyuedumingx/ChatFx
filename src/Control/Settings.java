package Control;

import Model.Operator;
import java.util.Date;


public class Settings {
    private static Settings set = new Settings();
    private static String resPath = "~/res/";
    private static final String historyFolder = "history/";
    private static Date lastMessageTime;
    private Operator operator = Operator.getOperator();

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

    public String getHistoryPath() {
        return resPath + historyFolder + operator.getName() + ".txt";
    }
}
