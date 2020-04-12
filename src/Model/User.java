package Model;

import javafx.scene.image.ImageView;

public class User {
    String name;
    int account;
    ImageView icon;

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setAccount(int account) {
        this.account = account;
    }
    public int getAccount() {
        return account;
    }

    public void setIcon(ImageView icon) {
        this.icon = icon;
    }
    public ImageView getIcon() {
        return icon;
    }

}

