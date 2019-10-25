package windows;

import accounting.Accounting;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class StartUpWindow extends Application {
    private Accounting accounting;
    private SceneEnum sceneEnum;

    @Override
    public void start(Stage stage) {
        accounting = new Accounting();
        stage.initStyle(StageStyle.DECORATED);
        stage.setTitle("ATM Customer App");
        stage.getIcons().add(new Image("file:" + System.getProperty("user.dir") + "\\src\\main\\resources\\icon.png"));
        sceneEnum = SceneEnum.CARD;
        stage.setScene(createNewScene(stage));
        stage.sizeToScene();
        stage.show();
    }

    private Scene createNewScene(Stage stage) {
        Pane pane = createNewPane(sceneEnum, stage);
        return new Scene(pane, 800, 600);
    }

    private Pane createNewPane(SceneEnum sceneEnum, Stage stage) {
        Pane pane = new Pane();
        switch (sceneEnum) {
            case CARD:
                Label label = new Label("Enter a valid card number:");
                label.setLayoutX(300);
                label.setLayoutY(200);
                label.setTextAlignment(TextAlignment.CENTER);
                label.setFont(Font.font("Palatino Linotype", 20));
                TextField textField = new TextField();
                label.setLabelFor(textField);
                textField.setLayoutX(290);
                textField.setLayoutY(250);
                textField.setAlignment(Pos.CENTER);
                textField.setFont(Font.font("Palatino Linotype", 20));
                textField.setOnAction(actionEvent -> setStage(stage, accounting.cardValidator(textField.getText())));
                pane.getChildren().add(label);
                pane.getChildren().add(textField);
                break;
            case PIN:
                label = new Label("Enter a valid PIN number:");
                label.setLayoutX(300);
                label.setLayoutY(200);
                label.setFont(Font.font("Palatino Linotype", 20));
                PasswordField passwordField = new PasswordField();
                label.setLabelFor(passwordField);
                passwordField.setLayoutX(290);
                passwordField.setLayoutY(250);
                passwordField.setAlignment(Pos.CENTER);
                passwordField.setFont(Font.font("Palatino Linotype", 20));
                passwordField.setOnAction(actionEvent -> setStage(stage, accounting.pinValidator(passwordField.getText())));
                pane.getChildren().add(label);
                pane.getChildren().add(passwordField);
        }
        return pane;
    }

    private void setStage(Stage stage, SceneEnum sceneEnum) {
        this.sceneEnum = sceneEnum;
        stage.setScene(createNewScene(stage));
    }
}
