package de.uniwue.jpp.javalgebra;

import de.uniwue.jpp.javalgebra.mengen.EndlicheMenge;
import de.uniwue.jpp.javalgebra.mengen.TupelMenge;

import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class StrukturMitEinerVerknuepfung<T> {

    private Menge <T> menge;
    private Abbildung <Tupel <T>, T> verknuepfung;
    private T neutralElement = null;

    public StrukturMitEinerVerknuepfung(Menge <T> menge, Abbildung <Tupel <T>, T> verknuepfung) {
        if (menge.getSize().isEmpty()) {
            throw new IllegalArgumentException("unendliche Menge");
        }
        if (menge.getElements().anyMatch(n -> !menge.contains(verknuepfung.apply(new Tupel <>(n, n))))) {
            throw new IllegalArgumentException("verknuepfung nicht zwei Elemente aus menge");
        }
        this.menge = menge;
        this.verknuepfung = verknuepfung;
    }

    public T apply(T t1, T t2) {
        if (!menge.contains(t1) || !menge.contains(t2)) {
            throw new IllegalArgumentException("t1 oder t2 nicht in menge");
        }
        return verknuepfung.apply(new Tupel <>(t1, t2));
    }

    public boolean isHalbgruppe() {
        List <T> tList = menge.getElements().collect(Collectors.toList());

        for (int i = 0; i < menge.getSize().get(); i++) {
            for (int j = i; j < menge.getSize().get(); j++) {
                for (int k = j; k < menge.getSize().get(); k++) {
                    T a1 = tList.get(i);
                    T a2 = tList.get(j);
                    T a3 = tList.get(k);
                    if (!apply(a1, apply(a2, a3)).equals(apply(apply(a1, a2), a3))) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public boolean isMonoid() {

        if (!isHalbgruppe()) {
            return false;
        }

        AtomicBoolean notNeutral = new AtomicBoolean(false);
        AtomicBoolean exists = new AtomicBoolean(false);
        menge.getElements().forEach(t -> {
            notNeutral.set(menge.getElements().allMatch(m -> apply(t, m).equals(m) && apply(m, t).equals(m)));
            if (notNeutral.get()) {
                exists.set(true);
                neutralElement = t;
            }
        });

        return exists.get();
    }

    public T getNeutralesElement() {

        if (isMonoid()) {
            return neutralElement;
        } else {
            throw new UnsupportedOperationException("Struktur kein Monoid");
        }

    }

    public boolean isGruppe() {
        if (!isMonoid()) {
            return false;
        }

        boolean exists = true;

        List <T> tList = menge.getElements().collect(Collectors.toList());
        if (menge.getSize().isPresent()) {

            for (int i = 0; i < menge.getSize().get(); i++) {
                T a = tList.get(i);
                for (int j = 0; j < menge.getSize().get(); j++) {
                    T b = tList.get(j);
                    T e = getNeutralesElement();
                    if (apply(a, b).equals(apply(b, a)) && apply(a, b).equals(e)) {
                        exists &= true;
                        break;
                    } else if (j == menge.getSize().get()-1){
                        exists &= false;
                    }

                }
                continue;
            }

        }
        return exists;
    }

    public boolean isKommutativ() {
        List <T> tList = menge.getElements().collect(Collectors.toList());

        for (int i = 0; i < menge.getSize().get(); i++) {
            for (int j = i; j < menge.getSize().get(); j++) {
                T a1 = tList.get(i);
                T a2 = tList.get(j);
                if (!apply(a1, a2).equals(apply(a2, a1))) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isAbelscheGruppe() {
        return isGruppe() && isKommutativ();
    }

}
