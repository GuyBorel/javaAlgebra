package de.uniwue.jpp.javalgebra.mengen;

import de.uniwue.jpp.javalgebra.Menge;

import java.math.BigInteger;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RestklassenMenge implements Menge <Integer> {

    private int mod;

    public RestklassenMenge(int mod) {
        if (mod <= 0) {
            throw new IllegalArgumentException("value should not be negative or null");
        }
        this.mod = mod;
    }

    @Override
    public Stream <Integer> getElements() {
        return Stream.iterate(0, i -> i + 1).limit(mod);
    }

    @Override
    public boolean contains(Integer element) {

        return element >= 0 && element < mod;
    }

    @Override
    public Optional <Integer> getSize() {
        return Optional.of(mod);
    }

    public static void main(String[] args) {
        RestklassenMenge restklassenMenge = new RestklassenMenge(34);
        System.out.println(restklassenMenge.getElements().collect(Collectors.toList()));
        System.out.println(restklassenMenge.asString(30));
        System.out.println(new RestklassenMenge(3).asString());
    }


}
