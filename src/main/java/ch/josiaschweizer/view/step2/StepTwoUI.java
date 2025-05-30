package ch.josiaschweizer.view.step2;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.annotation.Nonnull;
import java.util.List;

public class StepTwoUI {

    private final Runnable runnable;
    private Stage stage;

    private final VBox root;
    private TextArea getuAkroSubjectTextArea;
    private TextArea getuAkroMailTextArea;
    private TextArea erwachsenSubjectTextArea;
    private TextArea erwachsenMailTextArea;
    private final List<String> variables = List.of("$vorname", "$nachname", "$email", "$geburtsdatum");
    private Focus currentFocus = Focus.NOTHING;

    public StepTwoUI(@Nonnull final Runnable runnable) {
        this.runnable = runnable;

        this.getuAkroSubjectTextArea = new TextArea();
        final var getuAkroSubjectArea = createTextAreaWithLabel("Betreff für Getu-Akro:", getuAkroSubjectTextArea, Focus.GETU_AKRO_SUBJECT);
        this.getuAkroMailTextArea = new TextArea();
        final var getuAkroMailArea = createTextAreaWithLabel("Text für Getu-Akro:", getuAkroMailTextArea, Focus.GETU_AKRO_MAIL);
        this.erwachsenSubjectTextArea = new TextArea();
        final var erwachsenSubjectArea = createTextAreaWithLabel("Betreff für Erwachsene:", erwachsenSubjectTextArea, Focus.ERWACHSEN_SUBJECT);
        this.erwachsenMailTextArea = new TextArea();
        final var erwachsenMailArea = createTextAreaWithLabel("Text für Erwachsene:", erwachsenMailTextArea, Focus.ERWACHSEN_MAIL);

        HBox formulaBox = new HBox(5);
        formulaBox.getChildren().add(new Label("Platzhalter:"));
        for (String var : variables) {
            Button btn = new Button(var);
            btn.setOnAction(e -> insertVariable(var));
            formulaBox.getChildren().add(btn);
        }

        final var processButton = new Button("Email versenden");
        processButton.setOnAction(e -> runnable.run());
        final var processBox = new HBox(10, processButton);
        processBox.setAlignment(Pos.BOTTOM_RIGHT);
        processBox.setPadding(new Insets(0, 0, 0, 0));

        this.root = new VBox(10, formulaBox, getuAkroSubjectArea, getuAkroMailArea, erwachsenSubjectArea, erwachsenMailArea, processBox);
        root.setPadding(new Insets(10));
        VBox.setVgrow(getuAkroSubjectArea, Priority.ALWAYS);
        VBox.setVgrow(getuAkroMailArea, Priority.ALWAYS);
        VBox.setVgrow(erwachsenSubjectArea, Priority.ALWAYS);
        VBox.setVgrow(erwachsenMailArea, Priority.ALWAYS);
    }

    public void show(@Nonnull final Stage stage) {
        this.stage = stage;
        this.stage.setScene(new Scene(root, 800, 500));
        this.stage.setMinWidth(500);
        this.stage.setMinWidth(600);
        this.stage.setTitle("Mail-Text festlegen");
        this.stage.show();
    }

    private VBox createTextAreaWithLabel(@Nonnull final String labelText,
                                         @Nonnull final TextArea textArea,
                                         @Nonnull final Focus focus) {
        final var label = new Label(labelText);
        textArea.setOnMouseClicked(event -> {
            currentFocus = focus;
        });
        final var vbox = new VBox(5, label, textArea);
        vbox.setPadding(new Insets(10));
        return vbox;
    }

    private void insertVariable(String variable) {
        if (currentFocus == Focus.GETU_AKRO_SUBJECT) {
            final int pos = getuAkroSubjectTextArea.getCaretPosition();
            getuAkroSubjectTextArea.insertText(pos, variable);
        } else if (currentFocus == Focus.GETU_AKRO_MAIL) {
            final int pos = getuAkroMailTextArea.getCaretPosition();
            getuAkroMailTextArea.insertText(pos, variable);
        } else if (currentFocus == Focus.ERWACHSEN_SUBJECT) {
            final int pos = erwachsenSubjectTextArea.getCaretPosition();
            erwachsenSubjectTextArea.insertText(pos, variable);
        } else if (currentFocus == Focus.ERWACHSEN_MAIL) {
            final int pos = erwachsenMailTextArea.getCaretPosition();
            erwachsenMailTextArea.insertText(pos, variable);
        }
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