package Model;

import javafx.scene.layout.AnchorPane;

public class ChangeStage {
    private static ChangeStage shStage;
    private AnchorPane primaryStage;
    private AnchorPane setStage;
    private AnchorPane contactStage;
    private AnchorPane operaStage;

    private ChangeStage(AnchorPane primaryStage) {
        this.primaryStage = primaryStage;
        init();
    }

    private void init() {
        primaryStage = (AnchorPane)primaryStage.lookup("#primaryStage");
        setStage = (AnchorPane)primaryStage.lookup("#setStage");
        contactStage = (AnchorPane)primaryStage.lookup("#contactStage");
        operaStage = (AnchorPane)primaryStage.lookup("#operaStage");
    }

    public void toSetStage() {
        contactStage.setVisible(false);
        operaStage.setVisible(false);
        if (setStage.isVisible())
            setStage.setVisible(false);
        else
            setStage.setVisible(true);
    }

    public void toContact() {
        setStage.setVisible(false);
        operaStage.setVisible(false);
        if (contactStage.isVisible())
            contactStage.setVisible(false);
        else
            contactStage.setVisible(true);
    }

    public void toOpera() {
        if (operaStage.isVisible())
            operaStage.setVisible(false);
        else
            operaStage.setVisible(true);
    }

    public static ChangeStage getChangeStage(AnchorPane primaryStage) {
        if (shStage == null)
            shStage = new ChangeStage(primaryStage);
        else
            getChangeStage();
        return shStage;
    }

    public static ChangeStage getChangeStage() {
        return shStage;
    }

}

