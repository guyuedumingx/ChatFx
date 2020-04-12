package Model;

import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;


public class ContactWindows {
    Operator operator = Operator.getOperator();
    VBox friendListBox;

    public ContactWindows(VBox friendListBox) {
        this.friendListBox = friendListBox;
        readFriendsList();
    }

    private void readFriendsList() {
        for (Friend f : operator.getFriendList()) {
            FButton friendButton = new FButton(f);
            friendListBox.getChildren().addAll(friendButton);
        }
    }
}

class FButton extends Button {

    private ChangeStage chStage;

   FButton(Friend f) {
       super(f.getName());
       init();
   }

   private void init() {
       chStage = ChangeStage.getChangeStage();
       setChangeToOpera();
       getStyleClass().add("user");
       setBorder();
   }

   private void setBorder() {
       Border bo = new Border(new BorderStroke(Paint.valueOf("#80766e"),
               BorderStrokeStyle.SOLID,
               new CornerRadii(0),
               new BorderWidths(0.0,0.0,1.0,0.0)
       ));
       setBorder(bo);
   }

   private void setChangeToOpera() {
       addEventFilter(MouseEvent.ANY, e-> {
           if (e.getEventType() == MouseEvent.MOUSE_PRESSED) {
               chStage.toOpera();
           }
       });
   }
}