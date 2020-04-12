package Model;


import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class ContactWindows {
    Operator operator = Operator.getOperator();
    VBox friendListBox;

    public ContactWindows(VBox friendListBox) {
        this.friendListBox = friendListBox;
        readFriendsList();
    }

    private void readFriendsList() {
        for (Friend f : operator.getFriendList()) {
            Button friendButton = new Button(f.getName());
            friendButton.setStyle(".user");
            friendListBox.getChildren().addAll(friendButton);
        }
    }
}
