package ch.josiaschweizer.publ;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.annotation.Nonnull;

public class StageHelper {

    public static void configureStage(@Nonnull final Stage stage,
                                      @Nonnull final VBox root,
                                      @Nonnull final String title,
                                      final boolean resizable,
                                      final int... sizes) {
        stage.setScene(new Scene(root, sizes.length > 0 ? sizes[0]: 1200, sizes.length > 1 ? sizes[1] : 500));
        stage.setMinWidth(500);
        stage.setMinWidth(600);
        stage.setTitle(title);
        stage.setResizable(resizable);
    }

}
