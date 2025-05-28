package ch.josiaschweizer;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class Process {

    private final Session session;
    private String name = "";

    public Process(Session session) {
        this.session = session;
    }

    public void process(final String sender,
                        final String[] line) throws MessagingException {

        final var email = line[1];
        final var content = getString(line);

        final var message = new MimeMessage(session);
        message.setFrom(new InternetAddress(sender));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
        message.setSubject("Überprüfung Mitgliedsdaten für " + name);
        message.setText(content);

        Transport.send(message);
        System.out.println("Gesendet an: " + email + "\n" + " - " + name + "\n" + content);
        System.out.println(message);
    }


    private String getString(String[] line) {
        final var firstname = line[6];
        this.name = line[7];
        final var address = line[2] + ", " + line[3] + " " + line[4];
        final var phoneNumber = line[5];

        return String.format(
                "Hallo %s %s,\n\n" +
                        "bitte überprüfe deine aktuellen Mitgliedsdaten:\n\n" +
                        "Adresse: %s\n" +
                        "Telefon: %s\n\n" +
                        "Falls etwas nicht stimmt, antworte einfach auf diese E-Mail.\n\n" +
                        "Viele Grüße\n" +
                        "Dein Team",
                firstname, name, address, phoneNumber);
    }
}
