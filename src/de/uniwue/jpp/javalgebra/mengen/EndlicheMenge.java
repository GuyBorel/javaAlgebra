package de.uniwue.jpp.javalgebra.mengen;

import de.uniwue.jpp.javalgebra.Menge;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EndlicheMenge<T> implements Menge<T> {


    private Set<T> objects;
    public EndlicheMenge(Set<T> objects) {
        this.objects = objects;
    }

    @Override
    public Stream<T> getElements() {

        return objects.stream().limit(objects.size());
    }

    public EndlicheMenge<T> createUntermenge(Predicate<T> filter) {

        return new EndlicheMenge <T>(objects.stream().filter(filter).collect(Collectors.toSet()));

    }

    public static void main(String[] args) {
        Set<Integer> set = new HashSet <>();
        set.add(2);
        set.add(6);
        set.add(5);
        set.add(8);
        set.add(10);
        EndlicheMenge endlicheMenge = new EndlicheMenge(set);

        Menge<Integer> unendlicheMenge = new Menge <Integer>() {
            @Override
            public Stream <Integer> getElements() {
                return Stream.iterate(1, i -> i);
            }

            @Override
            public Optional <Integer> getSize() {
                return Optional.empty();
            }
        };

        System.out.println(endlicheMenge.asString(3));
    }
}
