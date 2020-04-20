package Model;

import javafx.scene.image.Image;


public class User {
    String name;
    int account;
    Image profilePic;

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

    public void setProfilePic(Image profilePic) {
        this.profilePic = profilePic;
    }
    public Image getProfilePic() {
        return profilePic;
    }

}

