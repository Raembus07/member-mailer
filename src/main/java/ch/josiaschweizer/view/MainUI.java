// MainUI.java
package ch.josiaschweizer.view;

import ch.josiaschweizer.view.step1.StepOneUI;
import ch.josiaschweizer.view.step2.StepTwoUI;
import ch.josiaschweizer.view.step3.StepThreeUI;
import ch.josiaschweizer.view.step4.StepFourUI;
import javafx.stage.Stage;

import javax.annotation.Nonnull;
import java.io.File;
import java.util.logging.Logger;

public class MainUI {

    private final Logger logger;
    private StepOneUI stepOneUI;
    private StepTwoUI stepTwoUI;
    private StepThreeUI stepThreeUI;
    private File file;

    public MainUI(@Nonnull final Logger logger) {
        this.logger = logger;
    }

    public void start(Stage primaryStage) {
        final Runnable runnable = () -> showStepTwo(primaryStage);
        stepOneUI = new StepOneUI(primaryStage, runnable);
        stepOneUI.show();
    }

    private void showStepTwo(Stage stage) {
        file = stepOneUI.getFile();
        stepTwoUI = new StepTwoUI(() -> {
            showStepThree(stage);
        });
        stepTwoUI.show(stage);
    }

    private void showStepThree(@Nonnull final Stage stage) {
        stepThreeUI = new StepThreeUI(() -> {
            sendEmail(file,
                    stepThreeUI.getEmail(),
                    stepThreeUI.getAppPassword(),
                    stepTwoUI.getGetuAkroSubject(),
                    stepTwoUI.getErwachsenSubject(),
                    stepTwoUI.getGetuAkroText(),
                    stepTwoUI.getErwachsenText());
        });
        stepThreeUI.show(stage);
    }

    private void sendEmail(@Nonnull final File file,
                           @Nonnull final String senderEmail,
                           @Nonnull final String appPassword,
                           @Nonnull final String getuAkroSubject,
                           @Nonnull final String erwachsenSubject,
                           @Nonnull final String getuAkroText,
                           @Nonnull final String erwachsenText) {
        stepTwoUI.getStage().close();
        final var stepThreeUI = new StepFourUI(
                file,
                senderEmail,
                appPassword,
                getuAkroSubject,
                erwachsenSubject,
                getuAkroText,
                erwachsenText,
                logger
        );
        stepThreeUI.show();
    }

}