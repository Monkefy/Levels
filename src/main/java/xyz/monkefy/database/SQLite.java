package xyz.monkefy.database;

import xyz.monkefy.utilities.StackUtil;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;

public class SQLite implements DataManager{

    Connection con;
    Statement statement;
    private String fileDirectory;
    private String fileName;
    Table table;

    public SQLite(String fileDirectory, String fileName) {
        this.fileDirectory = fileDirectory;
        this.fileName = fileName;
        File directory = new File(fileDirectory);
        directory.mkdirs();
        File file = new File(String.valueOf(fileDirectory) + File.separator + fileName);
        if(!file.exists())
            try {
                file.createNewFile();
            } catch (IOException e) {
                StackUtil.dumpStack(e);
            }
    }

    @Override
    public void setTable(Table table) {

    }

    @Override
    public void set(Object... objectParam) {

    }

    @Override
    public Object get(String parameter1, String parameter2, Object objParam) {
        return null;
    }

    @Override
    public boolean contains(String parameterString, Object parameterObject) {
        return false;
    }

    @Override
    public void update(String parameter1, String parameter2, Object paramObj1, Object paramObj2) {

    }

    @Override
    public void remove(String paramString1, Object objParam) {

    }

    @Override
    public boolean isOpen() {
        return false;
    }

    @Override
    public void open() {

    }

    @Override
    public void close() {

    }
}
