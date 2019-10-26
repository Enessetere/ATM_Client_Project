package windows;

import accounting.Accounting;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Border;
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
                break;
            case MENU:
                label = new Label("Choose Your option:");
                label.setLayoutX(300);
                label.setLayoutY(100);
                label.setFont(Font.font("Palatino Linotype", 20));
                pane.getChildren().add(label);
                Button balanceButton = new Button("Balance");
                balanceButton.setLayoutY(200);
                balanceButton.setLayoutX(300);
                balanceButton.setPrefSize(200, 100);
                balanceButton.setFont(Font.font("Palatino Linotype", 20));
                balanceButton.setOnAction(actionEvent -> setStage(stage, SceneEnum.BALANCE));
                Button depositButton = new Button("Deposit");
                depositButton.setLayoutY(325);
                depositButton.setLayoutX(300);
                depositButton.setPrefSize(200, 100);
                depositButton.setFont(Font.font("Palatino Linotype", 20));
                depositButton.setOnAction(actionEvent -> setStage(stage, SceneEnum.DEPOSIT));
                Button withdrawButton = new Button("Withdraw");
                withdrawButton.setLayoutY(450);
                withdrawButton.setLayoutX(300);
                withdrawButton.setPrefSize(200, 100);
                withdrawButton.setFont(Font.font("Palatino Linotype", 20));
                withdrawButton.setOnAction(actionEvent -> setStage(stage, SceneEnum.WITHDRAW));
                pane.getChildren().add(balanceButton);
                pane.getChildren().add(depositButton);
                pane.getChildren().add(withdrawButton);
                Button serverShutdown = new Button();
                serverShutdown.setLayoutX(795);
                serverShutdown.setLayoutY(0);
                serverShutdown.setPrefSize(5,5);
                serverShutdown.borderProperty().setValue(Border.EMPTY);
                serverShutdown.setOnAction(actionEvent -> shutdown(stage));
                pane.getChildren().add(serverShutdown);
                Button exitButton = new Button("Exit");
                exitButton.setLayoutX(680);
                exitButton.setLayoutY(550);
                exitButton.setPrefSize(100,30);
                exitButton.setOnAction(actionEvent -> close(stage));
                pane.getChildren().add(exitButton);
                break;
            case BALANCE:
                label = new Label("Your balance:");
                label.setLayoutX(350);
                label.setLayoutY(100);
                label.setFont(Font.font("Palatino Linotype", 20));
                pane.getChildren().add(label);
                Label balanceLabel = new Label(accounting.getBalance());
                balanceLabel.setLayoutX(375);
                balanceLabel.setLayoutY(125);
                balanceLabel.setFont(Font.font("Palatino Linotype", 20));
                pane.getChildren().add(balanceLabel);
                Button backButton = new Button("Return");
                backButton.setLayoutY(325);
                backButton.setLayoutX(300);
                backButton.setPrefSize(200, 100);
                backButton.setFont(Font.font("Palatino Linotype", 20));
                backButton.setOnAction(actionEvent -> setStage(stage, SceneEnum.MENU));
                pane.getChildren().add(backButton);
                break;
            case DEPOSIT:
                label = new Label("You wish to deposit:");
                label.setLayoutX(325);
                label.setLayoutY(100);
                label.setFont(Font.font("Palatino Linotype", 20));
                pane.getChildren().add(label);
                textField = new TextField();
                label.setLabelFor(textField);
                textField.setLayoutX(275);
                textField.setLayoutY(125);
                textField.setFont(Font.font("Palatino Linotype", 20));
                textField.setAlignment(Pos.CENTER);
                pane.getChildren().add(textField);
                Button confirmButton = new Button("Confirm");
                confirmButton.setLayoutY(225);
                confirmButton.setLayoutX(300);
                confirmButton.setPrefSize(200, 100);
                confirmButton.setFont(Font.font("Palatino Linotype", 20));
                confirmButton.setOnAction(actionEvent -> setStage(stage, accounting.deposit(textField.getText())));
                pane.getChildren().add(confirmButton);
                backButton = new Button("Return");
                backButton.setLayoutY(400);
                backButton.setLayoutX(300);
                backButton.setPrefSize(200, 100);
                backButton.setFont(Font.font("Palatino Linotype", 20));
                backButton.setOnAction(actionEvent -> setStage(stage, SceneEnum.MENU));
                pane.getChildren().add(backButton);
                break;
            case WITHDRAW:
                label = new Label("You wish to withdraw:");
                label.setLayoutX(325);
                label.setLayoutY(100);
                label.setFont(Font.font("Palatino Linotype", 20));
                pane.getChildren().add(label);
                textField = new TextField();
                label.setLabelFor(textField);
                textField.setLayoutX(275);
                textField.setLayoutY(125);
                textField.setFont(Font.font("Palatino Linotype", 20));
                textField.setAlignment(Pos.CENTER);
                pane.getChildren().add(textField);
                confirmButton = new Button("Confirm");
                confirmButton.setLayoutY(225);
                confirmButton.setLayoutX(300);
                confirmButton.setPrefSize(200, 100);
                confirmButton.setFont(Font.font("Palatino Linotype", 20));
                confirmButton.setOnAction(actionEvent -> setStage(stage, accounting.withdraw(textField.getText())));
                pane.getChildren().add(confirmButton);
                backButton = new Button("Return");
                backButton.setLayoutY(400);
                backButton.setLayoutX(300);
                backButton.setPrefSize(200, 100);
                backButton.setFont(Font.font("Palatino Linotype", 20));
                backButton.setOnAction(actionEvent -> setStage(stage, SceneEnum.MENU));
                pane.getChildren().add(backButton);
                break;
        }
        return pane;
    }

    private void setStage(Stage stage, SceneEnum sceneEnum) {
        this.sceneEnum = sceneEnum;
        stage.setScene(createNewScene(stage));
    }

    private void close(Stage stage) {
        accounting.close();
        stage.close();
    }

    private void shutdown(Stage stage) {
        accounting.shutdown();
        stage.close();
    }
}
