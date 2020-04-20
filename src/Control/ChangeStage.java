package Control;

import Model.EditAndSandWindow;
import Model.Friend;
import Model.WriteHistory;
import javafx.application.Platform;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;


/*这个类用来切换界面
* 制作系统托盘的组件是awt中的
*/
public class ChangeStage {
    private static ChangeStage shStage;
    private AnchorPane primaryStage;
    private AnchorPane setStage;
    private AnchorPane contactStage;
    private AnchorPane operaStage;

    private ChangeStage(AnchorPane primaryStage) {
        this.primaryStage = primaryStage;
        init();
    }

    private void init() {
        primaryStage = (AnchorPane)primaryStage.lookup("#primaryStage");
        setStage = (AnchorPane)primaryStage.lookup("#setStage");
        contactStage = (AnchorPane)primaryStage.lookup("#contactStage");
        operaStage = (AnchorPane)primaryStage.lookup("#operaStage");
    }

    public void toSetStage() {
        contactStage.setVisible(false);
        operaStage.setVisible(false);
        if (setStage.isVisible())
            setStage.setVisible(false);
        else
            setStage.setVisible(true);
    }

    public void toContact() {
        setStage.setVisible(false);
        operaStage.setVisible(false);
        if (contactStage.isVisible())
            contactStage.setVisible(false);
        else
            contactStage.setVisible(true);
    }

    public void toOpera(Friend f) {
        if (operaStage.isVisible())
            isOnCorrectPane(f);
        else {
            operaStage.setVisible(true);
            EditAndSandWindow.setShowFlow(f);
        }
    }

    //检测是否在正确的聊天框
    private void isOnCorrectPane(Friend f) {
        if (EditAndSandWindow.getShowFlow().equals(f.getHistoryFlow())) {
            operaStage.setVisible(false);
        }
        else {
            EditAndSandWindow.setShowFlow(f);
        }
    }

    //最小化到系统托盘
    //用的是awt的组件
    public void toSystemTray() {
        SystemTray systemTray = SystemTray.getSystemTray();
        Image image = Toolkit.getDefaultToolkit().getImage("src/View/images/chat.png");
        String str = "Chat";
        PopupMenu menu = new PopupMenu();
        MenuItem itemShow = new MenuItem("Show");
        MenuItem itemExit = new MenuItem("Exit");
        menu.add(itemShow);
        menu.add(itemExit);
        TrayIcon icon = new TrayIcon(image, str, menu);

        try {
            systemTray.add(icon);
        } catch (Exception e) {
            Log4Chat.printError(e);
        }
        addItemAction(icon,itemShow,itemExit, systemTray);
        primaryStage.setVisible(false);
    }

    private void addItemAction(TrayIcon icon,MenuItem itemShow, MenuItem itemExit, SystemTray tray) {
        icon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPrimary(icon, tray);
            }
        });

        itemShow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPrimary(icon, tray);
            }
        });

        itemExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toExit();
            }
        });
    }

    private void showPrimary(TrayIcon icon, SystemTray tray) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                primaryStage.setVisible(true);
                tray.remove(icon);
            }
        });
    }

    public void toExit() {
        WriteHistory.write();
        Settings.storeSettingsProperties();
        System.exit(0);
    }

    //读取图片
    public void openPicture() {
       File f = openFileChoose("Open Picture");
        if (f.exists()) {
            String url = "file:" + f.getAbsolutePath();
            EditAndSandWindow.getShowFlow().addPic(url, true);
        }
    }

    //调用系统的路径选择器
    public String getPath() {
       File f = openDirChoose("Open Path");
       if(f.isDirectory())
           return f.getAbsolutePath();
       else
           return "e:/";
    }

    private File openFileChoose(String title) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);
        File f = fileChooser.showOpenDialog(new Stage());
        return f;
    }

    private File openDirChoose(String title) {
        DirectoryChooser dirChooser = new DirectoryChooser();
        dirChooser.setTitle("Open Path");
        File f = dirChooser.showDialog(new Stage());
        return f;
    }

    //单例
    public static ChangeStage getChangeStage(AnchorPane primaryStage) {
        if (shStage == null)
            shStage = new ChangeStage(primaryStage);
        else
            shStage = getChangeStage();
        return shStage;
    }

    public static ChangeStage getChangeStage() {
        return shStage;
    }

}

