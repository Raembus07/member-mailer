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
import java.util.prefs.Preferences;

public class MainUI {

    private static final Preferences prefs = Preferences.userNodeForPackage(MainUI.class);
    private final Logger logger;
    private StepOneUI stepOneUI;
    private StepTwoUI stepTwoUI;
    private StepThreeUI stepThreeUI;
    private File file;
    private Character delimiter = ',';

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
        this.delimiter = stepOneUI.getDelimiter();
        stepTwoUI = new StepTwoUI(prefs, () -> {
            showStepThree(stage);
        });
        stepTwoUI.show(stage);
    }

    private void showStepThree(@Nonnull final Stage stage) {
        stepThreeUI = new StepThreeUI(prefs, () -> {
            sendEmail(file,
                    stepThreeUI.getEmail(),
                    stepThreeUI.getAppPassword(),
                    stepThreeUI.getSmtpHost(),
                    stepTwoUI.getSubjectText(),
                    stepTwoUI.getMailText()
            );
        });
        stepThreeUI.show(stage);
    }

    private void sendEmail(@Nonnull final File file,
                           @Nonnull final String senderEmail,
                           @Nonnull final String appPassword,
                           @Nonnull final String smptHost,
                           @Nonnull final String subject,
                           @Nonnull final String mailText) {
        stepTwoUI.getStage().close();
        final var stepThreeUI = new StepFourUI(
                file,
                senderEmail,
                appPassword,
                smptHost,
                subject,
                mailText,
                logger,
                delimiter
        );
        stepThreeUI.show();
    }

}