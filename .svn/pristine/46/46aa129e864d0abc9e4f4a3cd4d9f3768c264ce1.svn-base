package de.uniwue.jpp.javalgebra;

public class Tupel<T> {  //Richtig

    private T first,  second;
    public Tupel(T first, T second) {
        this.first = first;
        this.second = second;
    }

    public T getFirst() {
        return this.first;
    }

    public T getSecond() {
        return this.second;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tupel)) return false;

        Tupel <?> tupel = (Tupel <?>) o;

        if (!first.equals(tupel.first)) return false;
        return second.equals(tupel.second);
    }

    @Override
    public int hashCode() {
        int result = first.hashCode();
        result = 31 * result + second.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "(" + first + ", " + second + ")";
    }


}
