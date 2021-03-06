package de.uniwue.jpp.javalgebra;


import de.uniwue.jpp.javalgebra.mengen.EndlicheMenge;
import de.uniwue.jpp.javalgebra.mengen.RestklassenMenge;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


public class Abbildung<T, S> {

    private Menge <T> definitionsmenge;
    private Menge <S> zielmenge;
    private Function <T, S> abbVorschrift;

    public Abbildung(Menge <T> definitionsmenge, Menge <S> zielmenge, Function <T, S> abbVorschrift) {


        if (definitionsmenge.getSize().isEmpty()) {
            throw new IllegalArgumentException("definitionsmenge Unendliche Menge");
        }
        if (zielmenge.getSize().isEmpty()) {
            throw new IllegalArgumentException("zielMenge undendliche Menge");
        }

        if (definitionsmenge.getElements().anyMatch(n -> !zielmenge.contains(abbVorschrift.apply(n)))) {
            throw new IllegalArgumentException("S element ist nicht im zielmenge");
        }
        this.definitionsmenge = definitionsmenge;
        this.zielmenge = zielmenge;
        this.abbVorschrift = abbVorschrift;
    }

    public S apply(T t) {
        if (!definitionsmenge.contains(t)) {
            throw new IllegalArgumentException("t nicht in definitionsmenge");
        }
        return abbVorschrift.apply(t);
    }

    public Menge <S> getBildVon(Menge <T> m) {
        List <T> tList = m.getElements().collect(Collectors.toList());
        Set <S> sList = new HashSet <>();

        for (T t : tList) {
            if (!definitionsmenge.contains(t)) {
                throw new IllegalArgumentException("m ist keine Untermenge von definitionsmenge");
            }

            sList.add(abbVorschrift.apply(t));
        }
        return new EndlicheMenge <>(sList);

    }

    public Menge <T> getUrbildVon(Menge <S> m) {
        List <S> tList = m.getElements().collect(Collectors.toList());
        List <T> listT = definitionsmenge.getElements().collect(Collectors.toList());
        Set<T> tSet = new HashSet <>();


        for (S s : tList) {
            if (!zielmenge.contains(s)) {
                throw new IllegalArgumentException("m ist keine untermenge von zielMenge");
            }
            for (T t : listT) {
                if (m.contains(abbVorschrift.apply(t))) {
                    tSet.add(t);
                }
            }

        }

        return new EndlicheMenge <>(tSet);
    }

    public boolean isInjektiv() {
        List tList = new ArrayList();
        Set tSet = new HashSet <>();

        definitionsmenge.getElements().forEach(s -> tList.add(abbVorschrift.apply(s)));

        definitionsmenge.getElements().forEach(s -> tSet.add(abbVorschrift.apply(s)));

        return tList.size() == tSet.size();
    }

    public boolean isSurjektiv() {
        List tList = new ArrayList();
        Set tSet = new HashSet <>();

        definitionsmenge.getElements().forEach(s -> tSet.add(abbVorschrift.apply(s)));

        zielmenge.getElements().forEach(tList::add);

        return tList.size() <= tSet.size();
    }

    public boolean isBijektiv() {
        return isInjektiv() && isSurjektiv();
    }

    public Abbildung <S, T> getUmkehrabbildung() {
        if (!isBijektiv()) {
            throw new UnsupportedOperationException("Abbildung nicht bijektiv");
        }

        Function <S, T> stFunction = new Function <S, T>() {
            @Override
            public T apply(S s) {
                Set<S> sSet = new HashSet <>();

                sSet.add(s);


                return getUrbildVon(new EndlicheMenge <>(sSet)).getElements().findAny().get();
            }
        };


        return new Abbildung <>(zielmenge,definitionsmenge,stFunction);
    }

    public static void main(String[] args) {
        Abbildung<Integer,Integer> f8 = new Abbildung <Integer,Integer>(new RestklassenMenge(5), new RestklassenMenge (5),(t -> (t + 1)%5));

        Abbildung<Integer,Integer> f9 = new Abbildung <Integer,Integer>(new RestklassenMenge(5), new RestklassenMenge (5),(t -> (t %2)));

        System.out.println(f8.getUmkehrabbildung().apply(3));

        System.out.println(f8.apply(3));

        System.out.println(f8.isBijektiv());

        System.out.println(f9.apply(3));

        System.out.println(f9.isBijektiv());

        //System.out.println(f9.getUmkehrabbildung().apply(3));
    }

}
