package cat.udl.eps.ed.bst;

public interface Map<Key, Value> {
    void put(Key key, Value value);
    Value get(Key key);
    void remove(Key key);
    boolean containsKey(Key key);
    boolean isEmpty();
}
