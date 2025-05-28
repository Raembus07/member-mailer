package ch.josiaschweizer.entity;

public class User {
    public static final String UNBEKANNT = "UNBEKANNT";
    private final Long kontaktId;
    private final String email;
    private final String street;
    private final String zip;
    private final String city;
    private final String phoneNumber;
    private final String firstName;
    private final String lastName;
    private final String birthDate;
    private final Gender gender;
    private final Anredeart salutation;
    private final String memberType;

    public User(final String kontaktId,
                final String email,
                final String street,
                final String zip,
                final String city,
                final String phoneNumber,
                final String firstName,
                final String lastName,
                final String birthDate,
                final String gender,
                final String salutation,
                final String memberType) {
        this.kontaktId = Long.parseLong(kontaktId);
        this.email = email;
        this.street = street;
        this.zip = zip;
        this.city = city;
        this.phoneNumber = phoneNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.gender = Gender.fromCode(Integer.parseInt(gender));
        this.salutation = Anredeart.fromCode(Integer.parseInt(salutation));
        this.memberType = memberType;
    }


    public static User createUser(final String[] line) {
        if (line.length < 12) {
            throw new IllegalArgumentException("Insufficient data to create User object");
        }

        final var contactId = line[0];
        final var email = line[1];
        final var street = line[2];
        final var zip = line[3];
        final var city = line[4];
        final var phoneNumber = line[5];
        final var firstName = line[6];
        final var lastName = line[7];
        final var birthDate = line[8];
        final var gender = line[9];
        final var salutation = line[10];
        final var memberType = line[11];

        return new User(contactId,
                email,
                street,
                zip,
                city,
                phoneNumber,
                firstName,
                lastName,
                birthDate,
                gender,
                salutation,
                memberType);
    }

    public boolean isValid() {
        return true;
    }

    public String[] getProperties() {
        return new String[]{
                kontaktId != null ? kontaktId.toString() : UNBEKANNT,
                email != null ? email : UNBEKANNT,
                street != null ? street : UNBEKANNT,
                zip != null ? zip : UNBEKANNT,
                city != null ? city : UNBEKANNT,
                phoneNumber != null ? phoneNumber : UNBEKANNT,
                firstName != null ? firstName : UNBEKANNT,
                lastName != null ? lastName : UNBEKANNT,
                birthDate != null ? birthDate : UNBEKANNT,
                gender != null ? getGender().toString() : UNBEKANNT,
                salutation != null ? salutation.toString() : UNBEKANNT,
                memberType != null ? memberType : UNBEKANNT
        };
    }

    public String getPropertiesAsString() {
        return String.join(",", getProperties());
    }

    public String getPropertiesAsContent() {
        return String.format("""
                        Vorname: %s
                        Nachname: %s
                        E-Mail: %s
                        Strasse: %s
                        PLZ: %s
                        Ort: %s
                        Telefonnummer: %s
                        Geburtsdatum: %s
                        Geschlecht: %s
                        Anredeart: %s
                        Mitgliedstyp: %s\
                        
                        
                        """,
                firstName, lastName, email,
                street, zip, city,
                phoneNumber, birthDate, gender.toString(),
                salutation.toString(), memberType);
    }

    public long getKontaktId() {
        return kontaktId;
    }

    public String getEmail() {
        return email;
    }

    public String getStreet() {
        return street;
    }

    public String getZip() {
        return zip;
    }

    public String getCity() {
        return city;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public Gender getGender() {
        return gender;
    }

    public Anredeart getSalutation() {
        return salutation;
    }

    public String getMemberType() {
        return memberType;
    }
}
