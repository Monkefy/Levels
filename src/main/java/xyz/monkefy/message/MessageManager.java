package xyz.monkefy.message;

public interface MessageManager<A> {

    A get();

    void set(A message);

    String getPath();


}
