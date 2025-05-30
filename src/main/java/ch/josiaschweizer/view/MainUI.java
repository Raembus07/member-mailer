// MainUI.java
package ch.josiaschweizer.view;

import ch.josiaschweizer.controller.ProcessFile;
import ch.josiaschweizer.entity.factory.UserFactory;
import ch.josiaschweizer.publ.Publ;
import ch.josiaschweizer.view.step1.StepOneUI;
import ch.josiaschweizer.view.step2.StepTwoUI;
import ch.josiaschweizer.view.step3.StepThreeUI;
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
import java.util.logging.Logger;

public class MainUI {

    public static final String UNGÜLTIGE_BENUTZER_GEFUNDEN = "Ungültige Benutzer gefunden";
    private final Logger logger = Logger.getLogger(MainUI.class.getName());
    private StepOneUI stepOneUI;
    private StepTwoUI stepTwoUI;

    public void start(Stage primaryStage) {
        final Runnable runnable = () -> showStepTwo(primaryStage);
        stepOneUI = new StepOneUI(primaryStage, runnable);
        stepOneUI.show();
    }

    private void showStepTwo(Stage stage) {
        final var file = stepOneUI.getFile();
        stepTwoUI = new StepTwoUI(() -> {
            sendEmail(file,
                    stepTwoUI.getGetuAkroSubject(),
                    stepTwoUI.getErwachsenSubject(),
                    stepTwoUI.getGetuAkroText(),
                    stepTwoUI.getErwachsenText());
        });
        stepTwoUI.show(stage);
    }

    private void sendEmail(@Nonnull final File file,
                           @Nonnull final String getuAkroSubject,
                           @Nonnull final String erwachsenSubject,
                           @Nonnull final String getuAkroText,
                           @Nonnull final String erwachsenText) {
        final var stepThreeUI = new StepThreeUI(
                file,
                getuAkroSubject,
                erwachsenSubject,
                getuAkroText,
                erwachsenText,
                logger
        );
        stepThreeUI.show();
    }

}