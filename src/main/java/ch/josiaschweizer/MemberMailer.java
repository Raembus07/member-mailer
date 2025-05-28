package ch.josiaschweizer;

import ch.josiaschweizer.entity.User;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.mail.Authenticator;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class MemberMailer {

    public static final String FILENAME = "tvgossau_aktualisierungsexport_privatpersonen_2025-04-24_17-58-29_single.csv";
    private static MemberMailer instance = null;
    private final List<User> invalidUsers = new ArrayList<>();

    private MemberMailer() {
        //empty constructor to prevent instantiation
    }

    public static synchronized MemberMailer getInstance() {
        if (instance == null) {
            instance = new MemberMailer();
        }
        return instance;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(args));
        MemberMailer.getInstance().run();
    }

    public void run() {
        final var dotenv = Dotenv.configure().load();

        final var sender = dotenv.get("PROVIDER_EMAIL");
        final var password = dotenv.get("PROVIDER_APP_PASSWORD");
        final var smtpHost = "smtp.gmail.com";
        final var smtpPort = "587";

        final var props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.port", smtpPort);

        final var session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(sender, password);
            }
        });

        final var process = new Process(session);

        final String header;
        try (final var reader = new CSVReader(new FileReader(FILENAME))) {
            header = Arrays.toString(reader.readNext());
            System.out.println("Processing file with columns: " + String.join(", ", header));

            String[] line;
            while ((line = reader.readNext()) != null) {
                final var user = User.createUser(line);
                if (user.isValid()) {
                    process.process(sender, user);
                } else {
                    addInvalidUser(user);
                }
            }
        } catch (IOException | CsvValidationException | MessagingException e) {
            throw new RuntimeException(e);
        }

        if (!invalidUsers.isEmpty()) {
            final var invalidUser = new InvalidUser();
            invalidUser.writeInvalidUsersToFile(FILENAME, header, this.invalidUsers);
        }
    }

    private void addInvalidUser(final User line) {
        invalidUsers.add(line);
    }
}