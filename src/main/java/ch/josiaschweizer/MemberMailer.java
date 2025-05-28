package ch.josiaschweizer;

import com.opencsv.CSVReader;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.mail.*;
import jakarta.mail.internet.*;

import java.io.FileReader;
import java.util.Properties;

public class MemberMailer {

    public static void main(String[] args) throws Exception {
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

        try (final var reader = new CSVReader(new FileReader("tvgossau_aktualisierungsexport_privatpersonen_2025-04-24_17-58-29.csv"))) {
            final var header = reader.readNext();
            System.out.println("Processing file with columns: " + String.join(", ", header));

            String[] line;
            while ((line = reader.readNext()) != null) {
                process.process(sender, line);
            }
        }
    }
}