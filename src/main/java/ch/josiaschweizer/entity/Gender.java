package ch.josiaschweizer.entity;

import javax.annotation.Nonnull;

public enum Gender {
    MALE("Männlich"),
    FEMALE("Weiblich");

    @Nonnull
    private final String description;

    Gender(@Nonnull final String description) {
        this.description = description;
    }

    @Nonnull
    public String getDescription() {
        return description;
    }

    @Nonnull
    public static Gender fromDescription(@Nonnull final String description) {
        for (final var gender : Gender.values()) {
            if (gender.getDescription().equals(description)) {
                return gender;
            }
        }
        return MALE;
    }
}
