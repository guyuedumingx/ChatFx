package Model;

import javafx.scene.layout.*;


//负责联系人按键界面
public class ContactWindows {
    Operator operator = Operator.getOperator();
    VBox friendListBox;

    public ContactWindows(VBox friendListBox) {
        this.friendListBox = friendListBox;
        readFriendsList();
    }

    //导入联系人
    private void readFriendsList() {
        for (Friend f : operator.getFriendList()) {
            FButton friendButton = new FButton(f);
            friendListBox.getChildren().addAll(friendButton);
        }
    }
}

