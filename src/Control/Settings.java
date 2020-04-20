package Control;

import Model.Friend;
import Model.Operator;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import sun.rmi.runtime.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;


//保存软件的一些设置
public class Settings {
    private static final String historyFolder = "history\\";
    private static final String HISTORYFILENAME = "_history.xml";
    private static final String PICTUREFOLDERPATH = "picture\\";
    private static final String GIFFOLDERPATH = "gif\\";
    private static TextField msgColor;
    private static TextField bubbleColor;
    private static TextField textSize;
    public static TextField resPath;

    public Settings(AnchorPane primaryStage) {
        //从fxml文件中通过id来获取相应的对象
        msgColor = (TextField)primaryStage.lookup("#msgColor");
        bubbleColor = (TextField)primaryStage.lookup("#bubbleColor");
        textSize = (TextField)primaryStage.lookup("#textSize");
        resPath = (TextField)primaryStage.lookup("#filePath");

        //加载用户配置
        tryLoadProperties();
    }

    private void tryLoadProperties() {
        try {
            LoadAndWrite.loadSettings();
        } catch (Exception e) {
            Log4Chat.printError(e);
        }
    }

    public static void storeSettingsProperties() {
        try {
            LoadAndWrite.storeProps();
        } catch (Exception e) {
            Log4Chat.printError(e);
        }
    }

    public static String getHistoryDir() {
        return getResPath() + historyFolder + Operator.getOperator().getName() + "\\";
    }

    private static void setDefaultDir(String dirPath) {
        File dir = new File(dirPath);
        if(!dir.exists())
            dir.mkdirs();
    }
    private static String getPictureFolderPath() {
        return getResPath() + PICTUREFOLDERPATH;
    }

    public static File getHistoryFile(Friend f) {
        setDefaultDir(getHistoryDir());
        File file = new File(getHistoryDir()+ f.getName() + HISTORYFILENAME);
        if (!file.exists())
            tryCreateHistoryFile(file);
        return file;
    }

    private static void tryCreateHistoryFile(File f) {
        try {
            f.createNewFile();
        }
        catch (IOException e) {
            Log4Chat.printError(e);
        }
    }

    public static File getPictureFile() {
        setDefaultDir(getPictureFolderPath());
        return new File(getPictureFolderPath());
    }

    public static File getGifDir() {
        String path = getResPath() + GIFFOLDERPATH;
        setDefaultDir(path);
        return new File(path);
    }

    public static String getMsgColor() {
        String color = msgColor.getText();
        isColor(color);
        return color;
    }

    public static void setMsgColor(String color) {
        msgColor.setText(color);
    }

    public static String getBubbleColor() {
        String color = bubbleColor.getText();
        isColor(color);
        return color;
    }

    public static void setBubbleColor(String color) {
        bubbleColor.setText(color);
    }

    private static void isColor(String color) {
        //匹配颜色编码
        String pattern = "^#\\w{6}$";
        if(!color.matches(pattern))
            Alerts.colorNotFoundAlert(color);
    }

    public static int getTextSize() {
        return Integer.valueOf(textSize.getText());
    }

    public static void setTextSize(String size) {
        textSize.setText(size);
    }

    public static String getResPath() {
        String path = resPath.getText();
        testHistoryDir(path);
        return path + "res\\";
    }

    public static void setResPath(String path) {
        resPath.setText(path);
    }

    private static void testHistoryDir(String path) {
        File dir = new File(path);
        if(!dir.exists() || !dir.isDirectory())
            Alerts.historyNotFoundAlert(path);
    }
}

class LoadAndWrite {
    private static File f = new File("set.ini");
    private static Properties prop = new Properties();

    private LoadAndWrite() {}


    private static void tryLoadFile() {
        if(!f.exists())
            tryCreateNewSettingFile(f);
    }

    private static void tryCreateNewSettingFile(File f) {
        try {
          f.createNewFile();
          storeProps();
        } catch (Exception e) {
            Log4Chat.printError(e);
        }
    }

    //保存配置文件
    public static void storeProps() throws IOException {
        storePropsList();
        FileOutputStream fos = new FileOutputStream(f);
        prop.store(fos, "Some setting for Chat");
        fos.close();
    }

    //保存的属性
    private static void storePropsList() {
        prop.setProperty("resPath", Settings.resPath.getText());
        prop.setProperty("bubbleColor", Settings.getBubbleColor());
        prop.setProperty("msgColor", Settings.getMsgColor());
        prop.setProperty("textSize", String.valueOf(Settings.getTextSize()));
    }

    //加载配置文件
    public static void loadSettings() throws IOException {

        tryLoadFile();

        FileInputStream fis = new FileInputStream(f);
        prop.load(fis);
        loadProsList();
        fis.close();
    }

    //加载的属性
    private static void loadProsList() {
        Settings.setResPath(prop.getProperty("resPath"));
        Settings.setTextSize(prop.getProperty("textSize"));
        Settings.setBubbleColor(prop.getProperty("bubbleColor"));
        Settings.setMsgColor(prop.getProperty("msgColor"));
    }
}
