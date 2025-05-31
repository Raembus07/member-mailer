package ch.josiaschweizer.entity.user;

import ch.josiaschweizer.entity.Gender;
import ch.josiaschweizer.entity.user.email.Email;
import ch.josiaschweizer.entity.user.email.EmailComposition;
import ch.josiaschweizer.entity.user.parent.Parent;
import ch.josiaschweizer.publ.Publ;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

public class UserImpl extends AbstractUser {
    private String mailMessage = "Dies ist ein Test-E-Mail für " + firstName + " " + lastName + ".";
    private String mailSubject = Publ.UEBERPRUEFUNG_DES_GETU_AKRO_TEILNEHMERS_FIRSTNAME_LASTNAME + firstName + Publ.SPACE + lastName;

    private final String ahvNumber;

    public UserImpl() {
        // default constructor for serialization & getDynamicVariables
        this.ahvNumber = null;
    }

    public UserImpl(@Nonnull final String firstName,
                    @Nonnull final String lastName,
                    @Nonnull final String birthDate,
                    @Nonnull final String street,
                    @Nonnull final String zip,
                    @Nonnull final String city,
                    @Nonnull final String phoneNumberPrimary,
                    @Nonnull final String primaryEmail,
                    @Nonnull final String secondaryEmail,
                    @Nonnull final String childEmail,
                    @Nonnull final String phoneNumberSecondary,
                    @Nonnull final String eduction,
                    @Nonnull final String parentName1,
                    @Nonnull final String parentEmail1,
                    @Nonnull final String parentPhoneNumber1,
                    @Nonnull final String parentName2,
                    @Nonnull final String parentEmail2,
                    @Nonnull final String parentPhoneNumber2,
                    @Nonnull final String ahvNumber,
                    @Nonnull final String gender,
                    @Nonnull final String membership,
                    @Nonnull final String riege) {
        super.firstName = firstName;
        super.lastName = lastName;
        super.birthDate = birthDate;
        super.street = street;
        super.zip = zip;
        super.city = city;
        super.phoneNumberPrimary = phoneNumberPrimary;
        createEmail(primaryEmail,
                secondaryEmail,
                childEmail);
        super.phoneNumberSecondary = phoneNumberSecondary;
        super.eduction = eduction;
        createParentDetails(
                parentName1,
                parentEmail1,
                parentPhoneNumber1,
                parentName2,
                parentEmail2,
                parentPhoneNumber2);
        createGender(gender);
        super.membership = membership;
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

    private void createParentDetails(@Nonnull final String parentName1,
                                     @Nonnull final String parentEmail1,
                                     @Nonnull final String parentPhoneNumber1,
                                     @Nonnull final String parentName2,
                                     @Nonnull final String parentEmail2,
                                     @Nonnull final String parentPhoneNumber2) {
        super.parentDetails = new Parent(
                parentName1,
                parentEmail1,
                parentPhoneNumber1,
                parentName2,
                parentEmail2,
                parentPhoneNumber2
        );
    }

    private void createGender(@Nonnull final String gender) {
        super.gender = Gender.fromDescription(gender);
    }

    @Override
    public boolean isValid() {
        if (super.email == null || super.email.getSenderEmail() == null || super.email.getSenderEmail().isEmpty()) {
            System.err.println("Email is not set or empty, user is not valid.");
            return false;
        } else if (super.firstName == null || super.firstName.isEmpty()) {
            System.err.println("First name is not set or empty, user is not valid.");
            return false;
        } else if (super.lastName == null || super.lastName.isEmpty()) {
            System.err.println("Last name is not set or empty, user is not valid.");
            return false;
        }
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
        final var sb = new StringBuilder();
        addProperty(sb, firstName);
        addProperty(sb, lastName);
        addProperty(sb, birthDate);
        addProperty(sb, street);
        addProperty(sb, zip);
        addProperty(sb, city);
        addProperty(sb, phoneNumberPrimary);
        addProperty(sb, email != null ? email.getEmail(EmailComposition.PRIMARY) : Publ.EMPTY_STRING);
        addProperty(sb, email != null ? email.getEmail(EmailComposition.SECONDARY) : Publ.EMPTY_STRING);
        addProperty(sb, email != null ? email.getEmail(EmailComposition.CHILD) : Publ.EMPTY_STRING);
        addProperty(sb, phoneNumberSecondary);
        addProperty(sb, eduction);
        addProperty(sb, parentDetails != null ? parentDetails.getNameFather() : Publ.EMPTY_STRING);
        addProperty(sb, parentDetails != null ? parentDetails.getEmailFather() : Publ.EMPTY_STRING);
        addProperty(sb, parentDetails != null ? parentDetails.getPhoneNumberFather() : Publ.EMPTY_STRING);
        addProperty(sb, parentDetails != null ? parentDetails.getNameMother() : Publ.EMPTY_STRING);
        addProperty(sb, parentDetails != null ? parentDetails.getEmailMother() : Publ.EMPTY_STRING);
        addProperty(sb, parentDetails != null ? parentDetails.getPhoneNumberMother() : Publ.EMPTY_STRING);
        addProperty(sb, ahvNumber);
        addProperty(sb, gender.getDescription());
        addProperty(sb, membership);
        addProperty(sb, riege);
        return sb.toString();
    }

    private void addProperty(@Nonnull final StringBuilder sb,
                             @Nonnull final String property) {
        sb.append(property);
        sb.append(Publ.COMMA);
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
