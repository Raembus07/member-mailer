package ch.josiaschweizer.controller;

import ch.josiaschweizer.entity.factory.UserFactory;
import ch.josiaschweizer.entity.user.User;
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

    public ProcessFile(@Nonnull final UserFactory userFactory,
                       @Nonnull final Logger logger) {
        this.userFactory = userFactory;
        this.logger = logger;
    }

    @Nonnull
    public List<User> process(@Nonnull final File file) {
        final var users = getUsersFromFile(file);
        final List<User> invalidUsers = new ArrayList<>(List.of());

        final var mailerService = new MailerService(logger);
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

    private List<User> getUsersFromFile(@Nonnull final File file) {
        final var data = new ReadWriteFile().readFile(file);
        return extractUsers(data);
    }

    private List<User> extractUsers(List<List<String>> data) {
        final Map<String, Integer> headerMap = new HashMap<>();

        final var header = data.get(0);
        for (int i = 0; i < header.size(); i++) {
            headerMap.put(header.get(i).trim(), i);
        }

        final List<User> users = new ArrayList<>(List.of());
        for (int i = 1; i < data.size(); i++) {
            final var row = data.get(i);

            final var firstName = getByHeaderProperty(row, "Vorname", headerMap);
            final var lastName = getByHeaderProperty(row, "Nachname", headerMap);
            final var primaryEmail = getByHeaderProperty(row, "Primäre E-Mail", headerMap);
            final var secondaryEmail = getByHeaderProperty(row, "E-Mail 2", headerMap);
            final var childEmail = getByHeaderProperty(row, "E-Mail Kinder bis 18 J.", headerMap);
            final var street = getByHeaderProperty(row, "Strasse (Korr.)", headerMap);
            final var zip = getByHeaderProperty(row, "PLZ (Korr.)", headerMap);
            final var city = getByHeaderProperty(row, "Ort (Korr.)", headerMap);
            final var phoneNumberPrimary = getByHeaderProperty(row, "Handy", headerMap);
            final var phoneNumberSecondary = getByHeaderProperty(row, "Telefon Privat", headerMap);
            final var birthDate = getByHeaderProperty(row, "Geburtsdatum", headerMap);
            final var riegeString = getByHeaderProperty(row, "Riegen [21/21]", headerMap);
            final var ahvNumber = getByHeaderProperty(row, "Sozialversicherungsnr", headerMap);

            users.add(userFactory.createUser(
                    firstName,
                    lastName,
                    primaryEmail,
                    secondaryEmail,
                    childEmail,
                    street,
                    zip,
                    city,
                    phoneNumberPrimary,
                    phoneNumberSecondary,
                    birthDate,
                    riegeString,
                    ahvNumber));
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

    }
}