package Model;

import java.util.ArrayList;


public class Operator extends User{
    private static Operator operator = new Operator();
    private ArrayList<Friend> friendList = new ArrayList<>();

    private Operator() {
        setName("wangwu");
        setAccount(1111);
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
        f.setIcon(icon);
        return f;
    }

    public static Operator getOperator() {
        return operator;
    }
}


