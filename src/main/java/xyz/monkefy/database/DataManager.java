package xyz.monkefy.database;

public interface DataManager {
    void setTable(Table table);
    void set(Object... objectParam);
    Object get(String parameter1, String parameter2, Object objParam);
    boolean contains(String parameterString, Object parameterObject);
    void update(String parameter1, String parameter2, Object paramObj1, Object paramObj2);
    void remove(String paramString1, Object objParam);
    boolean isOpen();
    void open();
    void close();


}
