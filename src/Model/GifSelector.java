package Model;

import Control.Settings;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.TextFlow;

import java.io.File;


public class GifSelector {
    AnchorPane primaryStage;
    ScrollPane gifScrollPane;
    TextFlow gifPane;
    TextArea editArea;

    public GifSelector(AnchorPane primaryStage) {
        this.primaryStage = primaryStage;
        init();
    }

    private void init() {
       gifScrollPane = (ScrollPane)primaryStage.lookup("#gifScrollPane");
       gifPane = (TextFlow)primaryStage.lookup("#gifPane");
       editArea = (TextArea)primaryStage.lookup("#editArea");

        //addEmoji();
        getGif();
    }

    private void addEmoji() {
        String str = new String(Character.toChars(0x1F60D));

        Button butEmoji = new Button(str);
        butEmoji.getStyleClass().add(".butEmoji");
        gifPane.getChildren().addAll(butEmoji);

        butEmoji.addEventFilter(javafx.scene.input.MouseEvent.ANY, e-> {
            if (e.getEventType() == MouseEvent.MOUSE_PRESSED) {
                editArea.appendText(butEmoji.getText());
            }
        });
    }

    private void getGif() {
        File dir = Settings.getGifDir();

        for (String f : dir.list()) {
            if (f.endsWith(".gif")) {
                Image img = new Image("file:"+ Settings.getGifDir()+f);
                ImageView imgView = new ImageView(img);
                //imgView.setFitWidth(80);
                //imgView.setFitHeight(80);
                //Button but = new Button();
                //but.setGraphic(imgView);
                gifPane.getChildren().add(imgView);
            }
        }
    }
}
