package windows;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class PopUpWindow {
    private Stage stage = new Stage();

    public PopUpWindow(String text) {
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.DECORATED);
        stage.setResizable(false);
        stage.getIcons().add(new Image("file:" + System.getProperty("user.dir") + "\\src\\main\\resources\\icon.png"));
        Pane pane = new Pane();
        Label label = new Label(text);
        label.setLayoutY(20);
        label.setLayoutX(5);
        Button resumeWindowButton = new Button("OK");
        resumeWindowButton.setLayoutX(150);
        resumeWindowButton.setLayoutY(100);
        resumeWindowButton.setPrefSize(100, 30);
        resumeWindowButton.setOnAction(actionEvent -> this.stage.close());
        pane.getChildren().add(label);
        pane.getChildren().add(resumeWindowButton);
        Scene scene = new Scene(pane, 400, 150);
        stage.setScene(scene);
        stage.setTitle("ATM Customer App - ERROR");
        show();
    }

    private void show() {
        stage.showAndWait();
    }
}
