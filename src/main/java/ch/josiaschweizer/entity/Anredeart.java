package ch.josiaschweizer.entity;

public enum Anredeart {
    MALE("Herr", 1),
    FEMALE("Frau", 2);

    private final String label;
    private final int code;

    Anredeart(String label, int code) {
        this.label = label;
        this.code = code;
    }

    public String getLabel() {
        return label;
    }

    public int getCode() {
        return code;
    }

    public static Anredeart fromCode(int code) {
        for (Anredeart anredeart : Anredeart.values()) {
            if (anredeart.code == code) {
                return anredeart;
            }
        }
        throw new IllegalArgumentException("Invalid code for anredeart: " + code);
    }
}
