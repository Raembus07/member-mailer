package ch.josiaschweizer.entity.user.email;

public enum EmailComposition {
    PRIMARY(1),
    SECONDARY(2),
    CHILD(3);

    private final int value;

    EmailComposition(final int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static EmailComposition fromValue(final int value) {
        for (EmailComposition composition : EmailComposition.values()) {
            if (composition.getValue() == value) {
                return composition;
            }
        }
        throw new IllegalArgumentException("Invalid value: " + value);
    }
}
