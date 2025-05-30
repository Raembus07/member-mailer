package ch.josiaschweizer.entity.user;

import ch.josiaschweizer.entity.user.email.Email;
import ch.josiaschweizer.entity.user.email.EmailComposition;
import ch.josiaschweizer.entity.user.riege.Riege;
import ch.josiaschweizer.publ.Publ;

import javax.annotation.Nonnull;
import java.util.List;

public class ErwachsenerUser extends AbstractUser {

    private String mailMessage = "Dies ist ein Test-E-Mail für " + firstName + " " + lastName + ".";
    private String mailSubject = Publ.UEBERPRUEFUNG_DES_GETU_AKRO_TEILNEHMERS_FIRSTNAME_LASTNAME + "$firstname" + Publ.SPACE + "$lastName";

    public ErwachsenerUser() {
        // default constructor for serialization & getDynamicVariables
    }

    public ErwachsenerUser(final String firstName,
                           final String lastName,
                           final String primaryEmail,
                           final String secondaryEmail,
                           final String street,
                           final String zip,
                           final String city,
                           final String phoneNumberPrimary,
                           final String phoneNumberSecondary,
                           final String birthDate,
                           final Riege riege) {
        super.firstName = firstName;
        super.lastName = lastName;
        createEmail(primaryEmail, secondaryEmail);
        super.street = street;
        super.zip = zip;
        super.city = city;
        super.phoneNumberPrimary = phoneNumberPrimary;
        super.phoneNumberSecondary = phoneNumberSecondary;
        super.birthDate = birthDate;
        super.riege = riege;
    }

    private void createEmail(final String primaryEmail,
                             final String secondaryEmail) {
        super.email = new Email()
                .addEmail(EmailComposition.PRIMARY, primaryEmail)
                .addEmail(EmailComposition.SECONDARY, secondaryEmail);
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public List<String> getProperties() {
        return List.of();
    }

    @Override
    public String getPropertiesAsString() {
        return Publ.EMPTY_STRING;
    }

    @Override
    public String getMailMessage() {
        var result = mailMessage;
        for (final var entry : getPlaceholderMap().entrySet()) {
            result = result.replace(entry.getKey(), entry.getValue());
        }
        return result;
    }

    @Override
    public void setMailMessage(@Nonnull final String mailMessage) {
        this.mailMessage = mailMessage;
    }

    @Override
    public String getMailSubject() {
        var result = mailSubject;
        for (final var entry : getPlaceholderMap().entrySet()) {
            result = result.replace(entry.getKey(), entry.getValue());
        }
        return result;
    }

    @Override
    public void setMailSubject(@Nonnull final String mailSubject) {
        this.mailSubject = mailSubject;
    }
}
