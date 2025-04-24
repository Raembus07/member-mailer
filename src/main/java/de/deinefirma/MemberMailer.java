package de.deinefirma;

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

        try (final var reader = new CSVReader(new FileReader("tvgossau_aktualisierungsexport_privatpersonen_2025-04-24_17-58-29.csv"))) {
            final var header = reader.readNext(); // Read and store header for reference
            System.out.println("Processing file with columns: " + String.join(", ", header));

            String[] line;
            while ((line = reader.readNext()) != null) {
                final var email = line[1];
                final var firstname = line[6];
                final var name = line[7];
                final var address = line[2] + ", " + line[3] + " " + line[4];
                final var phoneNumber = line[5];

                final var content = String.format(
                        "Hallo %s %s,\n\n" +
                                "bitte überprüfe deine aktuellen Mitgliedsdaten:\n\n" +
                                "Adresse: %s\n" +
                                "Telefon: %s\n\n" +
                                "Falls etwas nicht stimmt, antworte einfach auf diese E-Mail.\n\n" +
                                "Viele Grüße\n" +
                                "Dein Team",
                        firstname, name, address, phoneNumber);

                final var message = new MimeMessage(session);
                message.setFrom(new InternetAddress(sender));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
                message.setSubject("Bitte prüfe deine Mitgliedsdaten");
                message.setText(content);

                Transport.send(message);
                System.out.println("Gesendet an: " + email);
                System.out.println(message);
            }
        }
    }
}