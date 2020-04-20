package Control;

import javafx.scene.control.Alert;

public class Alerts {

    public static void historyNotFoundAlert(String path) {
        String tip = "The History Path:  " + path + "  not existts";
        addErrorAlert(tip);
    }

    public static void colorNotFoundAlert(String color) {
        String tip = "The Color:  " + color + "  is not correct";
        addErrorAlert(tip);
    }

    private static void addErrorAlert(String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText(content);

        alert.showAndWait();
    }

    public static void addLogCreatFalseAlert() {
        String tip = "The LogFile can't be Create";
        addWarningAlert(tip);
    }

    private static void addWarningAlert(String tip) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(tip);

        alert.showAndWait();
    }
}
