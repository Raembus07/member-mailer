package ch.josiaschweizer.entity.user.riege;

public enum RiegeComposition {
    GETUAKRO("Getu Akro"),
    ERWACHSEN("Erwachsen");

    private final String description;

    private RiegeComposition(final String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
