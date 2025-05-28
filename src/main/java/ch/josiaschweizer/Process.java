package ch.josiaschweizer;

import ch.josiaschweizer.entity.User;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class Process {

    private final Session session;

    public Process(Session session) {
        this.session = session;
    }

    public void process(final String sender, final User user) throws MessagingException {
        final var name = user.getFirstName() + " " + user.getLastName();

        final var message = new MimeMessage(session);
        message.setFrom(new InternetAddress(sender));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(user.getEmail()));
        message.setSubject("Überprüfung Mitgliedsdaten für " + name);
        message.setText(getContent(user));

        Transport.send(message);
        System.out.println("Gesendet an: " + user.getEmail() + "\n" + " - " + name + "\n");
        System.out.println(message);
    }


    private String getContent(final User user) {
        return String.format(
                "Hallo " + user.getFullName() + "\n\n" +
                        "Bitte überprüfe deine aktuellen Mitgliedsdaten:\n\n" +
                        user.getPropertiesAsContent() +
                        "Falls etwas nicht stimmt, antworte einfach auf diese E-Mail.\n\n" +
                        "Viele Grüsse\n" +
                        "Dein GeTu-Team");
    }
}
