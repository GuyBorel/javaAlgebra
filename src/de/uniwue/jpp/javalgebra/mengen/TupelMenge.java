package de.uniwue.jpp.javalgebra.mengen;

import de.uniwue.jpp.javalgebra.Menge;
import de.uniwue.jpp.javalgebra.Tupel;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TupelMenge<T> implements Menge <Tupel <T>> {

    private Menge <T> menge;
    private Stream <Tupel <T>> tupelStream;
    private List <Tupel <T>> tupelList;

    public TupelMenge(Menge <T> menge) {

        if (menge.getSize().isEmpty()) {
            throw new IllegalArgumentException("undenliche Menge");
        }
        this.menge = menge;

        tupelList = new LinkedList <>();
        List <T> listValue = menge.getElements().collect(Collectors.toList());

        for (int i = 0; i < menge.getSize().get(); i++) {
            for (int j = i; j < menge.getSize().get(); j++) {
                if (listValue.get(i) != listValue.get(j)) {
                    tupelList.add(new Tupel <>(listValue.get(i), listValue.get(j)));
                    tupelList.add(new Tupel <>(listValue.get(j), listValue.get(i)));
                }else {
                    tupelList.add(new Tupel <>(listValue.get(i), listValue.get(j)));
                }
            }
        }

        tupelStream = tupelList.stream();
    }

    @Override
    public Stream <Tupel <T>> getElements() {

        return tupelStream;
    }

    @Override
    public boolean contains(Tupel <T> element) {

        return tupelList.contains(element);

    }

    @Override
    public Optional <Integer> getSize() {

        return Optional.of(tupelList.size());
    }

    public static void main(String[] args) {

        System.out.println(new TupelMenge <>(new RestklassenMenge(4)).contains(new Tupel <>(2, 3)));

        System.out.println(new TupelMenge <>(new RestklassenMenge(4)).getSize());

        System.out.println(new TupelMenge <>(new RestklassenMenge(4)).tupelStream.collect(Collectors.toList()));
    }
}
