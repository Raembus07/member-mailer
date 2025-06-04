package ch.josiaschweizer.view.step1;

import ch.josiaschweizer.controller.ReadWriteFile;
import ch.josiaschweizer.publ.StageHelper;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.annotation.Nonnull;
import java.io.File;
import java.util.List;

public class StepOneUI {
    @Nonnull
    private final Stage stage;
    @Nonnull
    private final Runnable onForward;

    @Nonnull
    private final ReadWriteFile readWriteFile = new ReadWriteFile();
    @Nonnull
    private final TableView<List<String>> tableView = new TableView<>();
    @Nonnull
    private final TextField filePathField;
    @Nonnull
    private final Button forwardButton;
    @Nonnull
    private final ComboBox<String> delimiterCombo;
    private final VBox root;
    private File file;

    public StepOneUI(@Nonnull final Stage stage, @Nonnull final Runnable onForward) {
        this.stage = stage;
        this.onForward = onForward;

        final var loadButton = new Button("CSV-Datei auswählen");
        loadButton.setOnAction(e -> loadCSV(stage));

        delimiterCombo = new ComboBox<>();
        delimiterCombo.getItems().addAll(",", ";", "\t", "|");
        delimiterCombo.setValue(",");
        delimiterCombo.setPromptText("Delimiter");
        delimiterCombo.setOnAction(e -> {
            if (file != null) {
                processCSV(file);
            }
        });

        final var loadSection = new HBox(10, loadButton, new Label("Delimiter:"), delimiterCombo);

        filePathField = new TextField();
        filePathField.setEditable(false);

        forwardButton = new Button("Weiter");
        forwardButton.setDisable(true);
        forwardButton.setOnAction(e -> onForward.run());
        final var forwardButtonBox = new HBox(10, forwardButton);
        forwardButtonBox.setAlignment(Pos.BOTTOM_RIGHT);

        root = new VBox(10, loadSection, filePathField, tableView, forwardButtonBox);
        root.setPadding(new javafx.geometry.Insets(10));
    }

    public void show() {
        StageHelper.configureStage(stage, root, "CSV Datei auswählen", true);
        stage.show();
    }

    private void loadCSV(@Nonnull final Stage stage) {
        final var fileChooser = new FileChooser();
        fileChooser.setTitle("CSV-Datei auswählen");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Dateien", "*.csv"));
        file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            filePathField.setText(file.getAbsolutePath());
            processCSV(file);
            forwardButton.setDisable(false);
        }
    }

    private void processCSV(@Nonnull final File file) {
        final var delimiter = delimiterCombo.getValue().charAt(0);
        List<List<String>> data = readWriteFile.readFile(file, delimiter);
        tableView.getItems().clear();
        tableView.getColumns().clear();

        if (data.isEmpty()) return;

        final var headers = data.get(0);
        for (int i = 0; i < headers.size(); i++) {
            final int colIndex = i;
            TableColumn<List<String>, String> column = new TableColumn<>(headers.get(i));
            column.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().get(colIndex)));
            tableView.getColumns().add(column);
        }

        for (int i = 1; i < data.size(); i++) {
            tableView.getItems().add(data.get(i));
        }
    }

    @Nonnull
    public File getFile() {
        return file;
    }

    @Nonnull
    public char getDelimiter() {
        return delimiterCombo.getValue().charAt(0);
    }
}