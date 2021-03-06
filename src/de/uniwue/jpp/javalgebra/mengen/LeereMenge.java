package de.uniwue.jpp.javalgebra.mengen;

import de.uniwue.jpp.javalgebra.Menge;

import java.util.stream.Stream;

public class LeereMenge<T> implements Menge<T> {


    @Override
    public Stream<T> getElements() {
        return Stream.empty();
    }

    public static void main(String[] args) {
        LeereMenge<Integer> leereMenge = new LeereMenge <>();
        System.out.println(leereMenge.getElements().count());
        System.out.println(leereMenge.getSize());
        System.out.println(leereMenge.contains(2));
        System.out.println(leereMenge.isEmpty());

        System.out.println();
        System.out.println(leereMenge.asString());
    }
}
