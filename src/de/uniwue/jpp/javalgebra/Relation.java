package de.uniwue.jpp.javalgebra;

import de.uniwue.jpp.javalgebra.mengen.RestklassenMenge;

import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Relation<T> implements Menge <Tupel <T>> {

    private Menge <T> menge;
    private BiFunction <T, T, Boolean> isInRelation;
    private Stream <Tupel <T>> tupelStream;
    private List <Tupel <T>> tupelList;
    private List <Tupel <T>> tupelTotal;

    public Relation(Menge <T> menge, BiFunction <T, T, Boolean> isInRelation) {
        if (menge.getSize().isEmpty()) {
            throw new IllegalArgumentException("unendliche Menge");
        }
        this.menge = menge;
        this.isInRelation = isInRelation;


        tupelList = new LinkedList <>();
        tupelTotal = new ArrayList <>();
        List <T> listValue = menge.getElements().collect(Collectors.toList());

        for (int i = 0; i < menge.getSize().get(); i++) {
            for (int j = 0; j < menge.getSize().get(); j++) {
                tupelTotal.add(new Tupel <>(listValue.get(i), listValue.get(j)));
                if (isInRelation.apply(listValue.get(i), listValue.get(j))) {
                    tupelList.add(new Tupel <>(listValue.get(i), listValue.get(j)));
                }
            }
        }

        tupelStream = tupelList.stream();
    }

    @Override
    public Stream <Tupel <T>> getElements() {
        return tupelList.stream();
    }


    @Override
    public boolean contains(Tupel <T> element) {

        return isInRelation.apply(element.getFirst(), element.getSecond());

    }

    public boolean isReflexiv() {
        List <T> listValue = menge.getElements().collect(Collectors.toList());
        for (T t : listValue) {
            if (!isInRelation.apply(t, t)) {
                return false;
            }
        }
        return true;
    }

    public boolean isIrreflexiv() {

        List <T> listValue = menge.getElements().collect(Collectors.toList());
        for (T t : listValue) {
            if (isInRelation.apply(t, t)) {
                return false;
            }
        }
        return true;
    }

    public boolean isSymmetrisch() {

        for (Tupel <T> list : tupelList) {
            if (!isInRelation.apply(list.getSecond(), list.getFirst())) {
                return false;
            }
        }
        return true;
    }

    public boolean isAsymmetrisch() {
        for (Tupel <T> list : tupelList) {
            if (isInRelation.apply(list.getSecond(), list.getFirst())) {
                return false;
            }

            if (list.getFirst().equals(list.getSecond())) {
                return false;
            }
        }
        return true;

    }

    public boolean isAntisymmetrisch() {
        for (Tupel <T> t : tupelList) {

            if (isInRelation.apply(t.getFirst(), t.getSecond())) {
                if (isInRelation.apply(t.getSecond(), t.getFirst())) {
                    if (!t.getFirst().equals(t.getSecond())) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public boolean isTransitiv() {

        for (int i = 0; i < tupelList.size(); i++) {
            T a1 = tupelList.get(i).getFirst();
            T a2 = tupelList.get(i).getSecond();
            for (int j = 0; isInRelation.apply(a1, a2) && j < tupelList.size(); j++) {
                T b1 = tupelList.get(j).getFirst();
                T b2 = tupelList.get(j).getSecond();
                if (a2.equals(b1) && isInRelation.apply(b1, b2) && !isInRelation.apply(a1, b2)) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isTotal() {

        for (Tupel <T> t : tupelTotal) {
            T a = t.getFirst();
            T b = t.getSecond();
            if (!isInRelation.apply(a, b) && !isInRelation.apply(b, a)) {
                return false;
            }
        }
        return true;
    }

    public boolean isAequivalenzrelation() {
        return isReflexiv() & isSymmetrisch() & isTransitiv();

    }

    public Set <Set <T>> getAequivalenzklassen() {
        List <T> listValue = menge.getElements().collect(Collectors.toList());


        Set <Set <T>> value = new HashSet <>();

        if (!isAequivalenzrelation()) {
            throw new UnsupportedOperationException("Menge ist keine ??quivalenzrelation");
        } else {
            for (int i = 0; i < menge.getSize().get(); i++) {
                Set <T> tSet = new HashSet <>();
                for (int j = 0; j < menge.getSize().get(); j++) {
                    if (isInRelation.apply(listValue.get(i), listValue.get(j)) && isInRelation.apply(listValue.get(j), listValue.get(i))) {
                        tSet.add(listValue.get(i));
                        tSet.add(listValue.get(j));
                    }
                }
                value.add(tSet);
            }

        }

        return value;
    }

    public boolean isTotalordnung() {

        return isReflexiv() & isAntisymmetrisch() & isTransitiv() & isTotal();

    }

    public List <T> getElementsInOrder() {
        List <T> elementsInOrder = new LinkedList <>();
        List <T> listValue = menge.getElements().collect(Collectors.toList());


        if (!isTotalordnung()) {
            throw new UnsupportedOperationException("Eine Relation ist genau dann eine Totalordnung");
        } else {
            elementsInOrder.add(0, listValue.get(0));
            for (int i = 1; i < listValue.size(); i++) {
                T a = listValue.get(i);
                for (int j = 0; j < elementsInOrder.size(); j++) {
                    T b = elementsInOrder.get(j);
                    if (isInRelation.apply(a, b)) {
                        elementsInOrder.add(j, a);
                        break;
                    }
                    if(j == elementsInOrder.size() - 1){
                        elementsInOrder.add(a);
                        break;
                    }
                }
            }
        }

        return elementsInOrder;
    }

    public static void main(String[] args) {


        Relation <Integer> test = new Relation <>(new RestklassenMenge(5), (a,b) -> true);



        System.out.println(test.isAntisymmetrisch());

        System.out.println(test.isTransitiv());

        System.out.println(test.getAequivalenzklassen());

        System.out.println(test.getElements().collect(Collectors.toList()));


    }

}
