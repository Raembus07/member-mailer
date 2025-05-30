package ch.josiaschweizer.entity.user;

import ch.josiaschweizer.entity.user.email.Email;
import ch.josiaschweizer.entity.user.email.EmailComposition;
import ch.josiaschweizer.entity.user.riege.Riege;
import ch.josiaschweizer.publ.Publ;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractUser implements User {

    String firstName;
    String lastName;
    Email email;
    String street;
    String zip;
    String city;
    String phoneNumberPrimary; //Handy
    String phoneNumberSecondary; //Telefon Privat
    String birthDate;
    Riege riege; //just for me

    public Map<String, String> getPlaceholderMap() {
        Map<String, String> map = new HashMap<>();
        map.put("$vorname", firstName != null ? firstName : Publ.EMPTY_STRING);
        map.put("$nachname", lastName != null ? lastName : Publ.EMPTY_STRING);
        map.put("$primary-email", email.getEmail(EmailComposition.PRIMARY) != null ? email.getEmail(EmailComposition.PRIMARY) : Publ.EMPTY_STRING);
        map.put("$secondary-email", email.getEmail(EmailComposition.SECONDARY) != null ? email.getEmail(EmailComposition.SECONDARY) : Publ.EMPTY_STRING);
        map.put("$child-email", email.getEmail(EmailComposition.CHILD) != null ? email.getEmail(EmailComposition.CHILD) : Publ.EMPTY_STRING);
        map.put("$strasse", street != null ? street : Publ.EMPTY_STRING);
        map.put("$postleitzahl", zip != null ? zip : Publ.EMPTY_STRING);
        map.put("$ort", city != null ? city : Publ.EMPTY_STRING);
        map.put("$telefon", phoneNumberPrimary != null ? phoneNumberPrimary : Publ.EMPTY_STRING);
        map.put("$telefon2", phoneNumberSecondary != null ? phoneNumberSecondary : Publ.EMPTY_STRING);
        map.put("$geburtsdatum", birthDate != null ? birthDate : Publ.EMPTY_STRING);

        return map;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public Email getEmail() {
        return email;
    }

    @Override
    public String getStreet() {
        return street;
    }

    @Override
    public String getZip() {
        return zip;
    }

    @Override
    public String getCity() {
        return city;
    }

    @Override
    public String getPhoneNumberPrimary() {
        return phoneNumberPrimary;
    }

    @Override
    public String getPhoneNumberSecondary() {
        return phoneNumberSecondary;
    }

    @Override
    public String getBirthDate() {
        return birthDate;
    }

    @Override
    public Riege getRiege() {
        return riege;
    }
}
