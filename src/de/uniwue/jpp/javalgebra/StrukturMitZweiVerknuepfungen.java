package de.uniwue.jpp.javalgebra;

import de.uniwue.jpp.javalgebra.mengen.EndlicheMenge;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

import java.util.stream.Collectors;

public class StrukturMitZweiVerknuepfungen<T> {

    private Menge<T> menge;
    private Abbildung<Tupel<T>,T> plus;
    private Abbildung<Tupel<T>,T> mal;
    public StrukturMitZweiVerknuepfungen(Menge<T> menge, Abbildung<Tupel<T>,T> plus, Abbildung<Tupel<T>,T> mal) {
        if (menge.getSize().isEmpty()){
            throw new IllegalArgumentException("unendliche Menge");
        }
        if (menge.getElements().anyMatch(n -> !menge.contains(plus.apply(new Tupel <>(n,n))))){
            throw new IllegalArgumentException("verknuepfung nicht zwei Elemente aus menge");
        }
        if (menge.getElements().anyMatch(n -> !menge.contains(mal.apply(new Tupel <>(n,n))))){
            throw new IllegalArgumentException("verknuepfung nicht zwei Elemente aus menge");
        }
        this.menge = menge;
        this.plus = plus;
        this.mal = mal;
    }

    public T applyPlus(T t1, T t2) {
        if (!menge.contains(t1) || !menge.contains(t2)){
            throw new IllegalArgumentException("t1 und t2 nicht in menge");
        }
        return plus.apply(new Tupel <>(t1,t2));
    }

    public T applyMal(T t1, T t2) {
        if (!menge.contains(t1) || !menge.contains(t2)){
            throw new IllegalArgumentException("t1 und t2 nicht in menge");
        }
        return mal.apply(new Tupel <>(t1,t2));
    }

    public boolean isDistributiv() {
        for (T t:menge.getElements().collect(Collectors.toList())) {
            if (!applyMal(t,applyPlus(t,t)).equals(applyPlus(applyMal(t,t),applyMal(t,t)))){
                return false;
            }

            if (!applyMal(applyPlus(t,t),t).equals(applyPlus(applyMal(t,t),applyMal(t,t)))){
                return false;
            }
        }
        return true;
    }

    public boolean isRing() {

        StrukturMitEinerVerknuepfung<T> strukturMitEinerVerknuepfungPlus = new StrukturMitEinerVerknuepfung<>(menge,plus);
        StrukturMitEinerVerknuepfung<T> strukturMitEinerVerknuepfungMal = new StrukturMitEinerVerknuepfung<>(menge,mal);


        return strukturMitEinerVerknuepfungPlus.isAbelscheGruppe() &&
                strukturMitEinerVerknuepfungMal.isHalbgruppe() && isDistributiv();
    }

    public T getNull() {
        if (!isRing()) {
            throw new UnsupportedOperationException("die Struktur kein Ring ist");
        }
        StrukturMitEinerVerknuepfung<T> strukturMitEinerVerknuepfungPlus = new StrukturMitEinerVerknuepfung<>(menge,plus);



        return strukturMitEinerVerknuepfungPlus.getNeutralesElement();
    }

    public boolean isKoerper() {

        try {
            List <T> listOhneNull = menge.getElements().collect(Collectors.toList());
            listOhneNull.remove(getNull());
            Set <T> tSet = new HashSet <>(listOhneNull);
            EndlicheMenge<T> endlicheMenge = new EndlicheMenge <>(tSet);
            StrukturMitEinerVerknuepfung <T> verknuepfungMal = new StrukturMitEinerVerknuepfung <>(endlicheMenge, mal);
            System.out.println(endlicheMenge.getElements().collect(Collectors.toList()));
            if (isRing() && verknuepfungMal.isAbelscheGruppe()){
                return true;
            }
        }catch(Exception e) {
            return false;
        }
        return false;
    }

    public T getEins() {
        if (!isKoerper()) {
            throw new UnsupportedOperationException("ist kein koerper");
        }

        StrukturMitEinerVerknuepfung<T> strukturMitEinerVerknuepfungMal = new StrukturMitEinerVerknuepfung<>(menge,mal);

        return strukturMitEinerVerknuepfungMal.getNeutralesElement() ;
    }

}
