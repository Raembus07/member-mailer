package ch.josiaschweizer.entity.user.parent;

import javax.annotation.Nonnull;

public class Parent {

    private String nameFather;
    private String emailFather;
    private String phoneNumberFather;
    private String nameMother;
    private String emailMother;
    private String phoneNumberMother;

    public Parent() {
        // Default constructor for serialization
    }

    public Parent(@Nonnull final String nameFather,
                  @Nonnull final String emailFather,
                  @Nonnull final String phoneNumberFather,
                  @Nonnull final String nameMother,
                  @Nonnull final String emailMother,
                  @Nonnull final String phoneNumberMother) {
        this.nameFather = nameFather;
        this.emailFather = emailFather;
        this.phoneNumberFather = phoneNumberFather;
        this.nameMother = nameMother;
        this.emailMother = emailMother;
        this.phoneNumberMother = phoneNumberMother;
    }

    public String getNameFather() {
        return nameFather;
    }

    public void setNameFather(@Nonnull final String nameFather) {
        this.nameFather = nameFather;
    }

    public String getEmailFather() {
        return emailFather;
    }

    public void setEmailFather(@Nonnull final String emailFather) {
        this.emailFather = emailFather;
    }

    public String getPhoneNumberFather() {
        return phoneNumberFather;
    }

    public void setPhoneNumberFather(@Nonnull final String phoneNumberFather) {
        this.phoneNumberFather = phoneNumberFather;
    }

    public String getNameMother() {
        return nameMother;
    }

    public void setNameMother(@Nonnull final String nameMother) {
        this.nameMother = nameMother;
    }

    public String getEmailMother() {
        return emailMother;
    }

    public void setEmailMother(@Nonnull final String emailMother) {
        this.emailMother = emailMother;
    }

    public String getPhoneNumberMother() {
        return phoneNumberMother;
    }

    public void setPhoneNumberMother(@Nonnull final String phoneNumberMother) {
        this.phoneNumberMother = phoneNumberMother;
    }
}
