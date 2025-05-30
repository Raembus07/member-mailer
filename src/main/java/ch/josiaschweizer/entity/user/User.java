package ch.josiaschweizer.entity.user;

import ch.josiaschweizer.entity.user.email.Email;
import ch.josiaschweizer.entity.user.riege.Riege;

import javax.annotation.Nonnull;
import java.util.List;

public interface User {

    boolean isValid();

    List<String> getProperties();

    String getPropertiesAsString();

    String getMailMessage();

    void setMailMessage(@Nonnull final String mailMessage);

    String getMailSubject();

    void setMailSubject(@Nonnull final String mailSubject);

    String getFirstName();

    String getLastName();

    Email getEmail();

    String getStreet();

    String getZip();

    String getCity();

    String getPhoneNumberPrimary();

    String getPhoneNumberSecondary();

    String getBirthDate();

    Riege getRiege();

}
