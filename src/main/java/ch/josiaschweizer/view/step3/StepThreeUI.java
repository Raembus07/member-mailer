package ch.josiaschweizer.view.step3;

import ch.josiaschweizer.publ.Publ;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.annotation.Nonnull;
import java.util.prefs.Preferences;

public class StepThreeUI {

    private final TextField emailField;
    private final PasswordField appPasswordField;
    private final TextField smtpHostField;
    private final VBox root;
    @Nonnull
    private final Preferences prefs;

    public StepThreeUI(@Nonnull final Preferences prefs,
                       @Nonnull final Runnable runnable) {
        this.prefs = prefs;
        this.root = new VBox(12);
        this.root.setPadding(new Insets(24));
        final var sendButton = new Button("Send Emails");
        sendButton.setOnMouseClicked(event -> {
            savePrefs();
            runnable.run();
        });

        this.emailField = new TextField();
        this.appPasswordField = new PasswordField();
        this.smtpHostField = new TextField();

        final var buttonBox = new VBox(10);
        buttonBox.setPadding(new Insets(10));
        buttonBox.setAlignment(Pos.BOTTOM_RIGHT);
        buttonBox.getChildren().add(sendButton);
        root.getChildren().addAll(
                new Label("Enter your email address:"),
                emailField,
                new Label("Enter your app password:"),
                appPasswordField,
                new Label("Enter your smtp host: (default host is smtp.google.com)"),
                smtpHostField,
                buttonBox
        );

        loadPrefs();
    }

    public void show(@Nonnull final Stage stage) {
        stage.setTitle("Enter Email Credentials");
        stage.setOnCloseRequest(event -> savePrefs());
        stage.setScene(new Scene(root, 400, 300));
        stage.setResizable(false);

        if (!emailField.getText().isEmpty()) {
            appPasswordField.requestFocus();
        }

        stage.show();
    }

    private void savePrefs() {
        prefs.put(Publ.SENDER_EMAIL, getEmail());
        prefs.put(Publ.SENDER_SMTP_HOST, getSmtpHost());
    }

    public void loadPrefs() {
        emailField.setText(prefs.get(Publ.SENDER_EMAIL, ""));
        smtpHostField.setText(prefs.get(Publ.SENDER_SMTP_HOST, "smtp.google.com"));
    }

    public String getEmail() {
        return emailField.getText();
    }

    public String getAppPassword() {
        return appPasswordField.getText();
    }

    public String getSmtpHost() {
        return smtpHostField.getText();
    }
}