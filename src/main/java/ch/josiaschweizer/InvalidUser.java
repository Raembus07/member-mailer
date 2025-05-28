package ch.josiaschweizer;

import ch.josiaschweizer.entity.User;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class InvalidUser {

    public void writeInvalidUsersToFile(final String originalFileName,
                                        final String fileHeader,
                                        final List<User> invalidUsers) {
        String invalidFileName = getInvalidFileName(originalFileName);
        try (FileWriter writer = new FileWriter(invalidFileName)) {
            write(writer, fileHeader);
            for (final var invalidUser : invalidUsers) {
                final var user = invalidUser.getPropertiesAsString();
                write(writer, user);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void write(final Writer writer, final String row) throws IOException {
        writer.write(row);
        writer.write(System.lineSeparator());
    }

    private String getInvalidFileName(String originalFileName) {
        int dotIndex = originalFileName.lastIndexOf('.');
        if (dotIndex != -1) {
            return originalFileName.substring(0, dotIndex) + "_invalid" + originalFileName.substring(dotIndex);
        } else {
            return originalFileName + "_invalid";
        }
    }
}