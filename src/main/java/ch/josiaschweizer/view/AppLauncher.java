package ch.josiaschweizer.view;

import javafx.application.Application;
import javafx.stage.Stage;

public class AppLauncher extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        final var logger = java.util.logging.Logger.getLogger(AppLauncher.class.getName());
        new MainUI(logger).start(stage);
    }
}
