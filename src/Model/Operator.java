package Model;

import javafx.scene.image.Image;

import java.util.ArrayList;


//登录软件的操作者
public class Operator extends User{
    private static Operator operator = new Operator();
    private ArrayList<Friend> friendList = new ArrayList<>();

    private Operator() {
        setName("wangwu");
        setAccount(1111);
        profilePic = new Image("file:..\\..\\2.jpg");
    }

    public void addFriend(Friend f) {
        friendList.add(f);
    }

    public void deleteFriend(Friend f) {
        friendList.remove(f);
    }

    public ArrayList<Friend> getFriendList() {
        return friendList;
    }

    public Friend getUserInfo() {
        Friend f = new Friend(name, account);
        f.setProfilePic(profilePic);
        return f;
    }

    public static Operator getOperator() {
        return operator;
    }
}


