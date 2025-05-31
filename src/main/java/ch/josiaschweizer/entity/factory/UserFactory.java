package ch.josiaschweizer.entity.factory;

import ch.josiaschweizer.entity.user.AbstractUser;
import ch.josiaschweizer.entity.user.UserImpl;
import ch.josiaschweizer.entity.user.email.Email;
import ch.josiaschweizer.entity.user.email.EmailComposition;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class UserFactory {

    private String mailSubject;
    private String mailMessage;

    public void setMailMessage(@Nonnull final String mailMessage) {
        this.mailMessage = mailMessage;
    }

    public void setMailSubject(@Nonnull final String mailSubject) {
        this.mailSubject = mailSubject;
    }

    public AbstractUser createUser(@Nonnull final String firstName,
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
                                   @Nonnull final String riegeString) {
        final var user = new UserImpl(
                firstName,
                lastName,
                birthDate,
                street,
                zip,
                city,
                phoneNumberPrimary,
                primaryEmail,
                secondaryEmail,
                childEmail,
                phoneNumberSecondary,
                eduction,
                parentName1,
                parentEmail1,
                parentPhoneNumber1,
                parentName2,
                parentEmail2,
                parentPhoneNumber2,
                ahvNumber,
                gender,
                membership,
                riegeString);
        user.setMailSubject(mailSubject);
        user.setMailMessage(mailMessage);
        return user;
    }
}

