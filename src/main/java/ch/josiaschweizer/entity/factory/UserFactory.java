package ch.josiaschweizer.entity.factory;

import ch.josiaschweizer.entity.user.AbstractUser;
import ch.josiaschweizer.entity.user.ErwachsenerUser;
import ch.josiaschweizer.entity.user.GetuAkroUser;
import ch.josiaschweizer.entity.user.riege.Riege;
import ch.josiaschweizer.entity.user.riege.RiegeComposition;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class UserFactory {

    private String getuAkroSubject;
    private String getuAkroMessage;
    private String erwachsenSubject;
    private String erwachsenMessage;

    public void setMailMessages(@Nonnull final String getuAkroText,
                                @Nonnull final String erwachsenText) {
        this.getuAkroMessage = getuAkroText;
        this.erwachsenMessage = erwachsenText;
    }

    public void setMailSubjects(@Nonnull final String getuAkroSubject,
                                @Nonnull final String erwachsenSubject) {
        this.getuAkroSubject = getuAkroSubject;
        this.erwachsenSubject = erwachsenSubject;
    }

    public AbstractUser createUser(
            @Nonnull final String firstName,
            @Nonnull final String lastName,
            @Nullable final String primaryEmail,
            @Nullable final String secondaryEmail,
            @Nullable final String childEmail,
            @Nonnull final String street,
            @Nonnull final String zip,
            @Nonnull final String city,
            @Nonnull final String phoneNumberPrimary,
            @Nonnull final String phoneNumberSecondary,
            @Nonnull final String birthDate,
            @Nonnull final String riegeString,
            @Nonnull final String ahvNumber) {
        final var riege = Riege.createRiege(riegeString);
        if (riege.getRiegeComposition() == RiegeComposition.GETUAKRO) {
            final var user = new GetuAkroUser(
                    firstName,
                    lastName,
                    primaryEmail,
                    secondaryEmail,
                    childEmail,
                    street,
                    zip,
                    city,
                    phoneNumberPrimary,
                    phoneNumberSecondary,
                    birthDate,
                    riege,
                    ahvNumber);
            user.setMailSubject(getuAkroSubject);
            user.setMailMessage(getuAkroMessage);
            return user;
        } else {
            final var user = new ErwachsenerUser(
                    firstName,
                    lastName,
                    primaryEmail,
                    secondaryEmail,
                    street,
                    zip,
                    city,
                    phoneNumberPrimary,
                    phoneNumberSecondary,
                    birthDate,
                    riege);
            user.setMailSubject(erwachsenSubject);
            user.setMailMessage(erwachsenMessage);
            return user;
        }
    }
}

