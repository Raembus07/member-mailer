package ch.josiaschweizer.controller;

import ch.josiaschweizer.model.fileio.Reader;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class ReadWriteFile {

    public List<List<String>> readFile(@Nonnull final File file,
                                       @Nonnull final Character delimiter) {
        try {
            return Reader.read(file, delimiter);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
