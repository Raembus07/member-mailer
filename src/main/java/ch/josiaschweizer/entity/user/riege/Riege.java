package ch.josiaschweizer.entity.user.riege;

import ch.josiaschweizer.publ.Publ;

public class Riege {

    private final RiegeComposition riegeComposition;

    private Riege(final RiegeComposition riegeComposition) {
        this.riegeComposition = riegeComposition;
    }

    public static Riege createRiege(final String riege) {
        if (riege.contains(Publ.EGT) ||
                riege.contains(Publ.VGT) ||
                riege.contains(Publ.AKRO)) {
            return new Riege(RiegeComposition.GETUAKRO);
        } else {
            return new Riege(RiegeComposition.ERWACHSEN);
        }
    }

    public RiegeComposition getRiegeComposition() {
        return riegeComposition;
    }
}
