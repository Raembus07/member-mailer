// MainUI.java
package ch.josiaschweizer.view;

import ch.josiaschweizer.view.step1.StepOneUI;
import ch.josiaschweizer.view.step2.StepTwoUI;
import ch.josiaschweizer.view.step3.StepThreeUI;
import javafx.stage.Stage;

import javax.annotation.Nonnull;
import java.io.File;
import java.util.logging.Logger;

public class MainUI {

    private final Logger logger;
    private StepOneUI stepOneUI;
    private StepTwoUI stepTwoUI;

    public MainUI(@Nonnull final Logger logger) {
        this.logger = logger;
    }

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
        stepTwoUI.getStage().close();
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