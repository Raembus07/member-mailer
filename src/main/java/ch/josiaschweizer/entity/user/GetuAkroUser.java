package ch.josiaschweizer.entity.user;

import ch.josiaschweizer.entity.user.email.Email;
import ch.josiaschweizer.entity.user.email.EmailComposition;
import ch.josiaschweizer.entity.user.riege.Riege;
import ch.josiaschweizer.publ.Publ;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

public class GetuAkroUser extends AbstractUser {
    private String mailMessage = "Dies ist ein Test-E-Mail für " + firstName + " " + lastName + ".";
    private String mailSubject = Publ.UEBERPRUEFUNG_DES_GETU_AKRO_TEILNEHMERS_FIRSTNAME_LASTNAME + firstName + Publ.SPACE + lastName;

    private final String ahvNumber;

    public GetuAkroUser() {
        // default constructor for serialization & getDynamicVariables
        this.ahvNumber = null;
    }

    public GetuAkroUser(@Nonnull final String firstName,
                        @Nonnull final String lastName,
                        @Nullable final String primaryEmail,
                        @Nullable final String secondaryEmail,
                        @Nullable final String childEmail,
                        @Nonnull final String street,
                        @Nonnull final String zip,
                        @Nonnull final String city,
                        @Nullable final String phoneNumberPrimary,
                        @Nullable final String phoneNumberSecondary,
                        @Nonnull final String birthDate,
                        @Nonnull final Riege riege,
                        @Nullable final String ahvNumber) {
        super.firstName = firstName;
        super.lastName = lastName;
        createEmail(primaryEmail, secondaryEmail, childEmail);
        super.street = street;
        super.zip = zip;
        super.city = city;
        super.phoneNumberPrimary = phoneNumberPrimary;
        super.phoneNumberSecondary = phoneNumberSecondary;
        super.birthDate = birthDate;
        super.riege = riege;
        this.ahvNumber = ahvNumber;
    }


    private void createEmail(final String primaryEmail,
                             final String secondaryEmail,
                             final String childEmail) {
        super.email = new Email();
        if (primaryEmail != null && !primaryEmail.isEmpty()) {
            super.email.addEmail(EmailComposition.PRIMARY, primaryEmail);
        }
        if (secondaryEmail != null && !secondaryEmail.isEmpty()) {
            super.email.addEmail(EmailComposition.SECONDARY, secondaryEmail);
        }
        if (childEmail != null && !childEmail.isEmpty()) {
            super.email.addEmail(EmailComposition.CHILD, childEmail);
        }
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Nonnull
    @Override
    public List<String> getProperties() {
        return List.of();
    }

    @Nonnull
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

    @Override
    public Map<String, String> getPlaceholderMap() {
        final var placeholderMap = super.getPlaceholderMap();
        placeholderMap.put("$ahvnummer", ahvNumber != null ? ahvNumber : Publ.EMPTY_STRING);
        return placeholderMap;
    }

    @Nullable
    public String getAhvNumber() {
        return ahvNumber;
    }
}
