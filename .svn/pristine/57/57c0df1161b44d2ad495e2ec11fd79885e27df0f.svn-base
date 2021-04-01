package de.uniwue.jpp.javalgebra.mengen;

import de.uniwue.jpp.javalgebra.Menge;

import java.math.BigInteger;
import java.util.Optional;
import java.util.stream.Stream;

public class GanzeZahlen implements Menge<BigInteger> {

    @Override
    public Stream<BigInteger> getElements() {

        return Stream.iterate(BigInteger.ZERO, n -> n.add(BigInteger.ONE)).map(x -> recurse(x.intValue()));
    }

    public static  BigInteger recurse(int n){

        if (n == 0){
            return BigInteger.ZERO;
        }else if (n == 1){
            return BigInteger.ONE;
        }else if (n%2 == 0){
            return recurse(n - 1).subtract(BigInteger.valueOf(n));
        }else {
            return recurse(n-1).add(BigInteger.valueOf(n));
        }
    }


    @Override
    public boolean contains(BigInteger element) {
        return true;
    }

    @Override
    public Optional<Integer> getSize() {
        return Optional.empty();
    }

    public static void main(String[] args) {
        GanzeZahlen ganzeZahlen = new GanzeZahlen();

        System.out.println(ganzeZahlen.asString(25));
    }
}
