package Model;

import Control.ChangeStage;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;

//朋友的面板
public class FButton extends Button {

    private ChangeStage chStage;
    private Friend f;

    FButton(Friend f) {
        super(f.getName());
        this.f = f;
        f.setfButton(this);
        init();
    }

    private void init() {
        setAlignment(Pos.TOP_LEFT);
        chStage = ChangeStage.getChangeStage();
        setChangeToOpera();
        getStyleClass().add("user");
        setBorder();
        setIcon();
    }

    private void setIcon () {
        ImageView iconView = new ImageView(f.profilePic);
        iconView.setFitHeight(60);
        iconView.setFitWidth(60);
        setGraphic(iconView);
    }

    private void setBorder() {
        Border bo = new Border(new BorderStroke(Paint.valueOf("#D4D4D4"),
                BorderStrokeStyle.SOLID,
                new CornerRadii(0),
                new BorderWidths(0.0,0.0,1.0,0.0)
        ));
        setBorder(bo);
    }

    //点击面板打开聊天框
    private void setChangeToOpera() {
        addEventFilter(MouseEvent.ANY, e-> {
            if (e.getEventType() == MouseEvent.MOUSE_PRESSED) {
                chStage.toOpera(f);
            }
        });
    }
}
