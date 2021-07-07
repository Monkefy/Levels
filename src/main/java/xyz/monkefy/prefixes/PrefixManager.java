package xyz.monkefy.prefixes;

public interface PrefixManager<T> {

    T get();

    void set(T message);

    String getPath();
}
