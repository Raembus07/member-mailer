package ch.josiaschweizer.view.step2;

import ch.josiaschweizer.entity.user.AbstractUser;
import ch.josiaschweizer.entity.user.ErwachsenerUser;
import ch.josiaschweizer.entity.user.GetuAkroUser;
import ch.josiaschweizer.publ.StageHelper;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.prefs.Preferences;

public class StepTwoUI {

    public static final String PREFS_GETU_AKRO_SUBJECT = "getuAkroSubject";
    public static final String PREFS_GETU_AKRO_MAIL = "getuAkroMail";
    public static final String PREFS_ERWACHSEN_SUBJECT = "erwachsenSubject";
    public static final String PREFS_ERWACHSEN_MAIL = "erwachsenMail";
    private Stage stage;
    private static final Preferences prefs = Preferences.userNodeForPackage(StepTwoUI.class);

    private final VBox root;
    private final TextArea getuAkroSubjectTextArea;
    private final TextArea getuAkroMailTextArea;
    private final TextArea erwachsenSubjectTextArea;
    private final TextArea erwachsenMailTextArea;
    private Focus currentFocus = Focus.NOTHING;

    public StepTwoUI(@Nonnull final Runnable runnable) {
        this.getuAkroSubjectTextArea = new TextArea();
        final var getuAkroSubjectArea = createTextAreaWithLabel("Betreff für Getu-Akro:", getuAkroSubjectTextArea, Focus.GETU_AKRO_SUBJECT, 4);
        this.getuAkroMailTextArea = new TextArea();
        final var getuAkroMailArea = createTextAreaWithLabel("Text für Getu-Akro:", getuAkroMailTextArea, Focus.GETU_AKRO_MAIL, 9);
        this.erwachsenSubjectTextArea = new TextArea();
        final var erwachsenSubjectArea = createTextAreaWithLabel("Betreff für Erwachsene:", erwachsenSubjectTextArea, Focus.ERWACHSEN_SUBJECT, 4);
        this.erwachsenMailTextArea = new TextArea();
        final var erwachsenMailArea = createTextAreaWithLabel("Text für Erwachsene:", erwachsenMailTextArea, Focus.ERWACHSEN_MAIL, 9);

        HBox formulaBox = new HBox(5);
        formulaBox.getChildren().add(new Label("Platzhalter:"));
        final var variables = getDynamicVariables();
        for (String var : variables) {
            Button btn = new Button(var);
            btn.setOnAction(e -> insertVariable(var));
            formulaBox.getChildren().add(btn);
        }

        final var processButton = new Button("Weiter");
        processButton.setOnAction(e -> {
            runnable.run();
        });
        final var processBox = new HBox(10, processButton);
        processBox.setAlignment(Pos.BOTTOM_RIGHT);
        processBox.setPadding(new Insets(0, 0, 0, 0));

        this.root = new VBox(10, formulaBox, getuAkroSubjectArea, getuAkroMailArea, erwachsenSubjectArea, erwachsenMailArea, processBox);
        root.setPadding(new Insets(10));
    }

    public void show(@Nonnull final Stage stage) {
        StageHelper.configureStage(this.stage = stage, root, "Mail-Text festlegen", true, 1200, 800);
        stage.onCloseRequestProperty().set(event -> {
            savePrefs();
            stage.close();
        });
        loadPrefs();

        this.stage.setResizable(true);
        this.stage.show();
    }


    private VBox createTextAreaWithLabel(@Nonnull final String labelText,
                                         @Nonnull final TextArea textArea,
                                         @Nonnull final Focus focus,
                                         final int prefRowCount) {
        final var label = new Label(labelText);
        textArea.setWrapText(true);
        textArea.setPrefRowCount(prefRowCount);
        textArea.setOnMouseClicked(event -> {
            currentFocus = focus;
        });
        VBox.setVgrow(textArea, Priority.ALWAYS);
        final var vbox = new VBox(5, label, textArea);
        vbox.setPadding(new Insets(10));
        return vbox;
    }

    private void insertVariable(String variable) {
        if (currentFocus == Focus.GETU_AKRO_SUBJECT) {
            final int pos = getuAkroSubjectTextArea.getCaretPosition();
            getuAkroSubjectTextArea.insertText(pos, variable);
            getuAkroSubjectTextArea.requestFocus();
        } else if (currentFocus == Focus.GETU_AKRO_MAIL) {
            final int pos = getuAkroMailTextArea.getCaretPosition();
            getuAkroMailTextArea.insertText(pos, variable);
            getuAkroMailTextArea.requestFocus();
        } else if (currentFocus == Focus.ERWACHSEN_SUBJECT) {
            final int pos = erwachsenSubjectTextArea.getCaretPosition();
            erwachsenSubjectTextArea.insertText(pos, variable);
            erwachsenSubjectTextArea.requestFocus();
        } else if (currentFocus == Focus.ERWACHSEN_MAIL) {
            final int pos = erwachsenMailTextArea.getCaretPosition();
            erwachsenMailTextArea.insertText(pos, variable);
            erwachsenMailTextArea.requestFocus();
        }
    }

    private List<String> getDynamicVariables() {
        Set<String> keys = new LinkedHashSet<>();
        List<AbstractUser> userTypes = List.of(
                new GetuAkroUser(),
                new ErwachsenerUser()
        );
        for (AbstractUser user : userTypes) {
            keys.addAll(user.getPlaceholderMap().keySet());
        }
        return new ArrayList<>(keys);
    }

    private void savePrefs() {
        prefs.put(PREFS_GETU_AKRO_SUBJECT, getuAkroSubjectTextArea.getText());
        prefs.put(PREFS_GETU_AKRO_MAIL, getuAkroMailTextArea.getText());
        prefs.put(PREFS_ERWACHSEN_SUBJECT, erwachsenSubjectTextArea.getText());
        prefs.put(PREFS_ERWACHSEN_MAIL, erwachsenMailTextArea.getText());
    }

    private void loadPrefs() {
        getuAkroSubjectTextArea.setText(prefs.get(PREFS_GETU_AKRO_SUBJECT, ""));
        getuAkroMailTextArea.setText(prefs.get(PREFS_GETU_AKRO_MAIL, ""));
        erwachsenSubjectTextArea.setText(prefs.get(PREFS_ERWACHSEN_SUBJECT, ""));
        erwachsenMailTextArea.setText(prefs.get(PREFS_ERWACHSEN_MAIL, ""));
    }

    public String getErwachsenSubject() {
        return erwachsenSubjectTextArea.getText();
    }

    public String getGetuAkroText() {
        return getuAkroMailTextArea.getText();
    }

    public String getGetuAkroSubject() {
        return getuAkroSubjectTextArea.getText();
    }

    public String getErwachsenText() {
        return erwachsenMailTextArea.getText();
    }

    public Stage getStage() {
        return stage;
    }
}