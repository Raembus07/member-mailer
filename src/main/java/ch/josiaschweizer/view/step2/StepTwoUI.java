package ch.josiaschweizer.view.step2;

import ch.josiaschweizer.entity.user.UserImpl;
import ch.josiaschweizer.publ.Publ;
import ch.josiaschweizer.publ.StageHelper;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.prefs.Preferences;

public class StepTwoUI {

    @Nonnull
    private final Preferences prefs;
    private Stage stage;

    private final VBox root;
    private final TextArea subjectTextArea;
    private final TextArea mailTextArea;
    private Focus currentFocus = Focus.NOTHING;

    public StepTwoUI(@Nonnull final Preferences prefs,
                     @Nonnull final Runnable runnable) {
        this.prefs = prefs;
        this.subjectTextArea = new TextArea();
        final var getuAkroSubjectArea = createTextAreaWithLabel("Betreff", subjectTextArea, Focus.SUBJECT, 4);
        this.mailTextArea = new TextArea();
        final var getuAkroMailArea = createTextAreaWithLabel("Mail Text", mailTextArea, Focus.MAIL, 9);

        FlowPane formulaBox = new FlowPane(5, 5);
        formulaBox.getChildren().add(new Label("Platzhalter:"));
        final var variables = getDynamicVariables();
        for (String var : variables) {
            Button btn = new Button(var);
            btn.setOnAction(e -> insertVariable(var));
            formulaBox.getChildren().add(btn);
        }

        final var processButton = new Button("Weiter");
        processButton.setOnAction(e -> {
            savePrefs();
            runnable.run();
        });
        final var processBox = new HBox(10, processButton);
        processBox.setAlignment(Pos.BOTTOM_RIGHT);
        processBox.setPadding(new Insets(0, 0, 0, 0));

        this.root = new VBox(10, formulaBox, getuAkroSubjectArea, getuAkroMailArea, processBox);
        root.setPadding(new Insets(10));
    }

    public void show(@Nonnull final Stage stage) {
        StageHelper.configureStage(this.stage = stage, root, "Mail-Text festlegen", true);
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
        if (currentFocus == Focus.SUBJECT) {
            final int pos = subjectTextArea.getCaretPosition();
            subjectTextArea.insertText(pos, variable);
            subjectTextArea.requestFocus();
        } else if (currentFocus == Focus.MAIL) {
            final int pos = mailTextArea.getCaretPosition();
            mailTextArea.insertText(pos, variable);
            mailTextArea.requestFocus();
        }
    }

    private List<String> getDynamicVariables() {
        final var keys = new LinkedHashSet<>(new UserImpl().getPlaceholderMap().keySet());
        return new ArrayList<>(keys);
    }

    private void savePrefs() {
        prefs.put(Publ.PREFS_SUBJECT, subjectTextArea.getText());
        prefs.put(Publ.PREFS_MAIL, mailTextArea.getText());
    }

    private void loadPrefs() {
        subjectTextArea.setText(prefs.get(Publ.PREFS_SUBJECT, ""));
        mailTextArea.setText(prefs.get(Publ.PREFS_MAIL, ""));
    }

    public String getMailText() {
        return mailTextArea.getText();
    }

    public String getSubjectText() {
        return subjectTextArea.getText();
    }

    public Stage getStage() {
        return stage;
    }
}