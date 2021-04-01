package de.uniwue.jpp.javalgebra;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface Menge<T> {

    Stream <T> getElements();

    default boolean contains(T element) {
        return getElements().collect(Collectors.toList()).contains(element);
    }

    default Optional <Integer> getSize() {

        return Optional.of((int) getElements().count());
    }

    default boolean isEmpty() {

        if (getSize().isEmpty()) {
            return false;
        }
        return getSize().get() == 0;
    }

    default String asString(int maxDisplay) {
        StringBuilder stringBuilder = new StringBuilder();

        if (maxDisplay < 0) {
            throw new IllegalArgumentException("value is not be negative !");
        }

        if (maxDisplay == 0){
            throw new IllegalArgumentException("value is not be null");
        }
        List <T> listElements ;
        Stream<T> stream = getElements();

        if (getSize().isEmpty()) {
            listElements = stream.limit(maxDisplay).collect(Collectors.toList());
            stringBuilder.append(listElements);
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            stringBuilder.append(", ...]");
            return stringBuilder.toString();
        }

        if (getSize().get() <= maxDisplay) {
            listElements = stream.collect(Collectors.toList());
            stringBuilder.append(listElements);
        }else {
            listElements = stream.limit(maxDisplay).collect(Collectors.toList());
            stringBuilder.append(listElements);
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            stringBuilder.append(", ...]");
        }

        return stringBuilder.toString();
    }

    default String asString() {
        StringBuilder stringBuilder = new StringBuilder();
        List <T> listElements ;
        Stream<T> stream = getElements();
        if (getSize().isEmpty()) {
            listElements = stream.limit(10).collect(Collectors.toList());
            stringBuilder.append(listElements);
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            stringBuilder.append(", ...]");
        }else {
            listElements = stream.collect(Collectors.toList());
            stringBuilder.append(listElements);
        }
        return stringBuilder.toString();
    }
}
