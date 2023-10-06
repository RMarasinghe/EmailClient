public interface Iterator {

    boolean hasNext(); // check if there's a next object
    Object next(); // return next object
    void reset(); // to make index 0
}
