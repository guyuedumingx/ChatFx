package Model;

import javax.swing.text.html.ImageView;

public class Operator extends User{
    private static Operator operator = new Operator();

    private Operator() {
        setName("wangwu");
        setAccount(1111);
    }

    public static Operator getOperator() {
        return operator;
    }
}

class Friends extends User {

}


class User {
    private String name;
    private int account;
    private ImageView icon;

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

    }
    public ImageView getIcon() {
        return icon;
    }
}

