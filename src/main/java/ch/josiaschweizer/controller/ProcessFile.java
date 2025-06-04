package ch.josiaschweizer.controller;

import ch.josiaschweizer.entity.factory.UserFactory;
import ch.josiaschweizer.entity.user.User;
import ch.josiaschweizer.model.fileio.Writer;
import ch.josiaschweizer.service.MailerService;

import javax.annotation.Nonnull;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProcessFile {
    @Nonnull
    final UserFactory userFactory;
    @Nonnull
    final Logger logger;
    @Nonnull
    private final Character delimiter;
    private Map<String, Integer> headerMap;
    private File file;

    public ProcessFile(@Nonnull final UserFactory userFactory,
                       @Nonnull final Logger logger,
                       @Nonnull final Character delimiter) {
        this.userFactory = userFactory;
        this.logger = logger;
        this.delimiter = delimiter;
    }

    @Nonnull
    public List<User> process(@Nonnull final File file,
                              @Nonnull final String senderEmail,
                              @Nonnull final String appPassword,
                              @Nonnull final String smptHost) {
        this.file = file;
        final var users = getUsersFromFile(file, delimiter);
        final List<User> invalidUsers = new ArrayList<>(List.of());

        final var mailerService = new MailerService(logger, senderEmail, appPassword, smptHost);
        for (var user : users) {
            if (user.isValid()) {
                final var response = mailerService.sendMail(user);
                response.ifPresent(e -> logger.log(Level.SEVERE, e.toString()));
            } else {
                final var msg = "Invalid user found: " + user.getFirstName() + " " + user.getLastName();
                System.err.println(msg);
                logger.log(Level.SEVERE, msg);
                invalidUsers.add(user);
            }
        }

        if (!invalidUsers.isEmpty()) {
            handleInvalidUsers(invalidUsers);
        }

        return invalidUsers;
    }

    private List<User> getUsersFromFile(@Nonnull final File file,
                                        @Nonnull final Character delimiter) {
        final var data = new ReadWriteFile().readFile(file, delimiter);
        return extractUsers(data);
    }

    private List<User> extractUsers(List<List<String>> data) {
        headerMap = new HashMap<>();

        final var header = data.get(0);
        for (int i = 0; i < header.size(); i++) {
            headerMap.put(header.get(i).trim(), i);
        }

        final List<User> users = new ArrayList<>(List.of());
        for (int i = 1; i < data.size(); i++) {
            final var row = data.get(i);

            users.add(userFactory.createUser(
                    getByHeaderProperty(row, "Vorname", headerMap),
                    getByHeaderProperty(row, "Nachname", headerMap),
                    getByHeaderProperty(row, "Geburtsdatum", headerMap),
                    getByHeaderProperty(row, "Strasse (Korr.)", headerMap),
                    getByHeaderProperty(row, "PLZ (Korr.)", headerMap),
                    getByHeaderProperty(row, "Ort (Korr.)", headerMap),
                    getByHeaderProperty(row, "Handy", headerMap),
                    getByHeaderProperty(row, "Primäre E-Mail", headerMap),
                    getByHeaderProperty(row, "E-Mail 2", headerMap),
                    getByHeaderProperty(row, "E-Mail Kinder bis 18 J.", headerMap),
                    getByHeaderProperty(row, "Telefon privat", headerMap),
                    getByHeaderProperty(row, "Ausbildung", headerMap),
                    getByHeaderProperty(row, "Elternteil 1", headerMap),
                    getByHeaderProperty(row, "E-Mail Eltern 1", headerMap),
                    getByHeaderProperty(row, "Handy Eltern 1", headerMap),
                    getByHeaderProperty(row, "Elternteil 2", headerMap),
                    getByHeaderProperty(row, "E-Mail Eltern 2", headerMap),
                    getByHeaderProperty(row, "Handy Eltern 2", headerMap),
                    getByHeaderProperty(row, "Sozialversicherungsnr", headerMap),
                    getByHeaderProperty(row, "Geschlecht", headerMap),
                    getByHeaderProperty(row, "Mitgliedschaft", headerMap),
                    getByHeaderProperty(row, "Riegen [21/21]", headerMap)));
        }

        return users;
    }

    private String getByHeaderProperty(@Nonnull final List<String> row, @Nonnull final String header, @Nonnull final Map<String, Integer> headerMap) {
        final var index = headerMap.get(header);
        if (index != null && index < row.size()) {
            return row.get(index).trim();
        }
        return "";
    }

    private void handleInvalidUsers(@Nonnull final List<User> invalidUsers) {
        final var invalidFileName = file.getAbsolutePath().replaceFirst("(\\.\\w+)$", "_invalid$1");
        final var invalidFile = new File(invalidFileName);
        final var headerLine = String.join(", ", headerMap.keySet());
        final var fileContent = new ArrayList<String>();
        fileContent.add(headerLine);
        for (User user : invalidUsers) {
            fileContent.add(user.getPropertiesAsString());
        }
        Writer.write(invalidFile, fileContent);
    }
}