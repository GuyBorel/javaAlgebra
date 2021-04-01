package de.uniwue.jpp.javalgebra.mengen;

import de.uniwue.jpp.javalgebra.Menge;

import java.math.BigInteger;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NatuerlicheZahlen implements Menge<BigInteger> {

    @Override
    public Stream<BigInteger> getElements() {
        return Stream.iterate(BigInteger.ONE, i -> i.add(BigInteger.ONE));

    }

    @Override
    public boolean contains(BigInteger element) {

        return element.compareTo(BigInteger.ZERO) > 0;

    }

    @Override
    public Optional<Integer> getSize() {
        return Optional.empty();
    }


    public static void main(String[] args) {
        NatuerlicheZahlen natuerlicheZahlen = new NatuerlicheZahlen();

        System.out.println(natuerlicheZahlen.contains(BigInteger.ZERO));

        System.out.println(natuerlicheZahlen.asString(10));
    }
}
