package ch.josiaschweizer.model.fileio;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class Writer {

    public static void write(@Nonnull final File file,
                             @Nonnull final List<String> lines) {
        try (PrintWriter writer = new PrintWriter(file, StandardCharsets.UTF_8)) {
            for (String line : lines) {
                writer.println(line);
                System.err.println("writing line to invalid file: " + line);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to write to file: " + file.getAbsolutePath(), e);
        }
    }
}
