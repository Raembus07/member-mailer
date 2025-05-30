package ch.josiaschweizer.service;

import ch.josiaschweizer.entity.user.User;
import ch.josiaschweizer.publ.Publ;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import javax.annotation.Nonnull;
import java.util.Optional;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MailerService {

    @Nonnull
    final Logger logger;

    private final String sender;
    private final String password;
    private final Session session;

    public MailerService(@Nonnull final Logger logger) {
        this.logger = logger;
        logger.log(Level.INFO, "Initializing MailerService");

        final var dotenv = Dotenv.load();
        sender = dotenv.get("PROVIDER_EMAIL");
        password = dotenv.get("PROVIDER_APP_PASSWORD");
        final var smtpHost = "smtp.gmail.com";
        final var smtpPort = "587";

        final var props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.port", smtpPort);

        this.session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(sender, password);
            }
        });
    }

    public Optional<Exception> sendMail(User user) {
        final var email = user.getEmail().getSenderEmail();
        final var subject = user.getMailSubject();
        final var messageText = user.getMailMessage();

        try {
            final var message = new MimeMessage(session);
            message.setFrom(new InternetAddress(this.sender));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject(subject);
            message.setText(messageText);

            Transport.send(message);
            final var msg = "Email sent successfully to " + user.getFirstName() + Publ.SPACE + user.getLastName() + " to email: " + email;
            logger.log(Level.INFO, msg);
            System.out.println(msg);
            return Optional.empty();
        } catch (MessagingException e) {
            return Optional.of(e);
        }
    }
}