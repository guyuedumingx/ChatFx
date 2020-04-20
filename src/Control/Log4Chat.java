package Control;

import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Log4Chat {

    //定义软件日志文件
    private static final String LOG = "exception.log";

    public static void printError(Exception e) {
        //把错误输出到日志
        try {
            PrintStream ps = new PrintStream(LOG);
            //把输出改到日志文件
            System.setOut(ps);
            ps.println(getTime());
            e.printStackTrace(System.out);
            ps.close();
        }
        //弹出无法创建日志文件的对话框
        catch (IOException ex) {
            Alerts.addLogCreatFalseAlert();
        }
    }

    private static String getTime() {
        //获取时间
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(d);
    }
}
