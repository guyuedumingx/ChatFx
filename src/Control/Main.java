package Control;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;



public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../View/windows.fxml"));
        //实现窗口透明
        stage.initStyle(StageStyle.TRANSPARENT);
        //窗口标题
        stage.setTitle("Chat");
        Scene scene = new Scene(root);
        //实现窗口透明
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();

        DragWindowHandler dwh = new DragWindowHandler(stage);
        //实现窗口拖拽
        EventHandler handler = dwh;
        scene.setOnMousePressed(handler);
        scene.setOnMouseDragged(handler);

    }



    public static void main(String[] args) {
        launch(args);
    }
}

