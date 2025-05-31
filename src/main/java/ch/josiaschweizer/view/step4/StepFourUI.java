package ch.josiaschweizer.view.step4;

import ch.josiaschweizer.controller.ProcessFile;
import ch.josiaschweizer.entity.factory.UserFactory;
import ch.josiaschweizer.publ.Publ;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.annotation.Nonnull;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StepFourUI {
    private final Stage loadingStage = new Stage();
    private final Logger logger;
    private final File file;
    private final String senderEmail;
    private final String appPassword;
    private final String smptHost;
    private final String mailSubject;
    private final String mailText;

    public StepFourUI(@Nonnull final File file,
                      @Nonnull final String senderEmail,
                      @Nonnull final String appPassword,
                      @Nonnull final String smptHost,
                      @Nonnull final String mailSubject,
                      @Nonnull final String mailText,
                      @Nonnull final Logger logger) {
        this.file = file;
        this.senderEmail = senderEmail;
        this.appPassword = appPassword;
        this.smptHost = smptHost;
        this.mailSubject = mailSubject;
        this.mailText = mailText;
        this.logger = logger;
    }

    public void show() {
        loadingStage.initModality(Modality.APPLICATION_MODAL);
        loadingStage.setTitle("Sending Emails...");

        final var progressBar = new ProgressBar();
        progressBar.setPrefWidth(300);
        progressBar.setProgress(ProgressBar.INDETERMINATE_PROGRESS);

        final var logArea = new javafx.scene.control.TextArea();
        logArea.setEditable(false);
        logArea.setPrefHeight(200);

        final var vbox = new VBox(10, new Label("Sending emails, please wait..."), progressBar, logArea);
        vbox.setStyle("-fx-padding: 20;");
        loadingStage.setScene(new Scene(vbox));
        loadingStage.setResizable(false);

        class LevelTrackingHandler extends java.util.logging.Handler {
            private java.util.logging.Level maxLevel = java.util.logging.Level.INFO;

            @Override
            public void publish(java.util.logging.LogRecord record) {
                if (record.getLevel().intValue() > maxLevel.intValue()) {
                    maxLevel = record.getLevel();
                }
                String msg = getFormatter().format(record);
                javafx.application.Platform.runLater(() -> logArea.appendText(msg));
            }

            @Override
            public void flush() {
            }

            @Override
            public void close() {
            }

            public java.util.logging.Level getMaxLevel() {
                return maxLevel;
            }
        }

        final var textAreaHandler = new LevelTrackingHandler();
        textAreaHandler.setFormatter(new java.util.logging.SimpleFormatter());
        logger.addHandler(textAreaHandler);

        Platform.runLater(loadingStage::show);

        new Thread(() -> {
            final var userFactory = new UserFactory();
            userFactory.setMailSubject(mailSubject);
            userFactory.setMailMessage(mailText);

            logger.info("Starting to process users from file: " + file.getName());

            final var processFile = new ProcessFile(userFactory, logger);
            final var invalidUsers = processFile.process(file, senderEmail, appPassword, smptHost);

            Platform.runLater(() -> {
                progressBar.setProgress(1.0);
                logger.removeHandler(textAreaHandler);
                final var alert = new Alert(Alert.AlertType.INFORMATION);
                if (!invalidUsers.isEmpty()) {
                    loadingStage.close();
                    alert.setTitle(Publ.HINWEIS);
                    alert.setHeaderText("Ungültige Benutzer gefunden");
                    alert.setContentText("Die ungültigen Benutzer wurden in eine Datei mit dem Zusatz _invalid geschrieben.");
                    alert.showAndWait();
                    Platform.exit();
                } else if (textAreaHandler.getMaxLevel() == Level.SEVERE) {
                    alert.setTitle("Fehler");
                    alert.setHeaderText("Es sind Fehler beim Senden aufgetreten");
                    alert.setContentText("Bitte prüfen Sie die Log-Ausgabe für Details.");
                    alert.showAndWait();
                } else {
                    loadingStage.close();
                    alert.setTitle("Erfolg");
                    alert.setHeaderText("E-Mails wurden erfolgreich gesendet");
                    alert.setContentText("Alle gültigen Benutzer haben ihre E-Mails erhalten.");
                    alert.showAndWait();
                    Platform.exit();
                }
            });
        }).start();
    }
}