package ch.josiaschweizer.entity;

public enum Gender {
    MALE("männlich", 1),
    FEMALE("weiblich", 2);

    private final String label;
    private final int code;

    Gender(String label, int code) {
        this.label = label;
        this.code = code;
    }

    public String getLabel() {
        return label;
    }

    public int getCode() {
        return code;
    }

    public static Gender fromCode(int code) {
        for (Gender gender : Gender.values()) {
            if (gender.code == code) {
                return gender;
            }
        }
        throw new IllegalArgumentException("Invalid code for Gender: " + code);
    }
}