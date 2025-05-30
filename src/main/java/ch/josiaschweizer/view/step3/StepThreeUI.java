package ch.josiaschweizer.view.step3;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.annotation.Nonnull;

public class StepThreeUI {

    private final TextField emailField = new TextField();
    private final PasswordField appPasswordField = new PasswordField();
    private final Button sendButton;
    private final VBox root;

    public StepThreeUI(@Nonnull final Runnable runnable) {
        root = new VBox(12);
        root.setPadding(new Insets(24));
        sendButton = new Button("Send Emails");
        sendButton.setOnMouseClicked(event -> {
            runnable.run();
        });
        root.getChildren().addAll(
                new Label("Enter your email address:"),
                emailField,
                new Label("Enter your app password:"),
                appPasswordField,
                sendButton
        );

    }

    public void show(@Nonnull final Stage stage) {
        stage.setTitle("Enter Email Credentials");
        stage.setScene(new Scene(root, 400, 250));
        stage.setResizable(false);
        stage.show();
    }

    public String getEmail() {
        return emailField.getText();
    }

    public String getAppPassword() {
        return appPasswordField.getText();
    }
}