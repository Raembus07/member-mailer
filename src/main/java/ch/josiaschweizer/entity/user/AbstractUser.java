package ch.josiaschweizer.entity.user;

import ch.josiaschweizer.entity.Gender;
import ch.josiaschweizer.entity.user.email.Email;
import ch.josiaschweizer.entity.user.email.EmailComposition;
import ch.josiaschweizer.entity.user.parent.Parent;
import ch.josiaschweizer.publ.Publ;

import java.util.LinkedHashMap;
import java.util.Map;

public abstract class AbstractUser implements User {

    String firstName;
    String lastName;
    String birthDate;
    String street;
    String zip;
    String city;
    String phoneNumberPrimary; //Handy
    Email email; // primary, secondary, child
    String phoneNumberSecondary; //Telefon Privat
    String eduction;
    Parent parentDetails;
    Gender gender;
    String membership;
    String riege;

    public Map<String, String> getPlaceholderMap() {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("$vorname", firstName != null ? firstName : Publ.EMPTY_STRING);
        map.put("$nachname", lastName != null ? lastName : Publ.EMPTY_STRING);
        map.put("$geburtsdatum", birthDate != null ? birthDate : Publ.EMPTY_STRING);
        map.put("$strasse", street != null ? street : Publ.EMPTY_STRING);
        map.put("$plz", zip != null ? zip : Publ.EMPTY_STRING);
        map.put("$ort", city != null ? city : Publ.EMPTY_STRING);
        map.put("$handy", phoneNumberPrimary != null ? phoneNumberPrimary : Publ.EMPTY_STRING);
        map.put("$email-primär", email != null ? email.getEmail(EmailComposition.PRIMARY) != null ? email.getEmail(EmailComposition.PRIMARY) : Publ.EMPTY_STRING : Publ.EMPTY_STRING);
        map.put("$email-2", email != null ? email.getEmail(EmailComposition.SECONDARY) != null ? email.getEmail(EmailComposition.SECONDARY) : Publ.EMPTY_STRING : Publ.EMPTY_STRING);
        map.put("$email-kind", email != null ? email.getEmail(EmailComposition.CHILD) != null ? email.getEmail(EmailComposition.CHILD) : Publ.EMPTY_STRING : Publ.EMPTY_STRING);
        map.put("$telefon-festnetz", phoneNumberSecondary != null ? phoneNumberSecondary : Publ.EMPTY_STRING);
        map.put("$ausbildung", eduction != null ? eduction : Publ.EMPTY_STRING);
        map.put("$elternteil 1 - name", parentDetails != null ? parentDetails.getNameFather() != null ? parentDetails.getNameFather() : Publ.EMPTY_STRING : Publ.EMPTY_STRING);
        map.put("$elternteil 1 - email", parentDetails != null ? parentDetails.getEmailFather() != null ? parentDetails.getEmailFather() : Publ.EMPTY_STRING : Publ.EMPTY_STRING);
        map.put("$elternteil 1 - handy", parentDetails != null ? parentDetails.getPhoneNumberFather() != null ? parentDetails.getPhoneNumberFather() : Publ.EMPTY_STRING : Publ.EMPTY_STRING);
        map.put("$elternteil 2 - name", parentDetails != null ? parentDetails.getNameMother() != null ? parentDetails.getNameMother() : Publ.EMPTY_STRING : Publ.EMPTY_STRING);
        map.put("$elternteil 2 - email", parentDetails != null ? parentDetails.getEmailMother() != null ? parentDetails.getEmailMother() : Publ.EMPTY_STRING : Publ.EMPTY_STRING);
        map.put("$elternteil 2 - handy", parentDetails != null ? parentDetails.getPhoneNumberMother() != null ? parentDetails.getPhoneNumberMother() : Publ.EMPTY_STRING : Publ.EMPTY_STRING);
        map.put("$geschlecht", gender != null ? gender.getDescription() : Publ.EMPTY_STRING);
        map.put("$mitgliedschaft", membership != null ? membership : Publ.EMPTY_STRING);
        map.put("$riege", riege != null ? riege : Publ.EMPTY_STRING);
        map.put("$liebe-r", gender != null ? gender.equals(Gender.MALE) ? "Lieber" : "Liebe" : Publ.EMPTY_STRING);

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
    public String getRiege() {
        return riege;
    }
}
