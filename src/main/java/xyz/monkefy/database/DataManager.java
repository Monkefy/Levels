package xyz.monkefy.database;

public interface DataManager {
    void setTable(Table table);
    void set(Object... objParam);
    Object get(String parameter1, String parameter2, Object objParam);
    boolean contains(String stringParameter, Object paramObject);
    void update(String stringParameter1, String stringParameter2, Object paramObj1, Object paramObj2);
    void remove(String stringParameter1, Object objParam);
    boolean isOpen();
    void open();
    void close();


}
