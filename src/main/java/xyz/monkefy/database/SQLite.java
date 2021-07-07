package xyz.monkefy.database;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import xyz.monkefy.Levels;
import xyz.monkefy.utilities.StackUtil;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.Stack;

public class SQLite implements DataManager{

    Connection con;
    Statement statement;
    private String fileDirectory;
    private String fileName;
    Table table;
    String prefix = Levels.getPrefix();

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
        open();
    }

    protected SQLite(FileConfiguration fileConfiguration) {}

    @Override
    public void setTable(Table table) {
        this.table = table;
        try {
            this.statement.executeUpdate("CREATE TABLE IF NOT EXISTS " + table.getName() + table.getUsage());
        } catch (SQLException e) {
            Bukkit.getLogger().severe(prefix + "Couldn't create SQL task, plugin won't work without it.");
            StackUtil.dumpStack(e);
            return;
        }
    }

    @Override
    public void set(Object... values) {
        try {
            String valueCount = "";
            for (int i = 0; i < values.length; i++) {
                valueCount = String.valueOf(valueCount) + "?";
                if (i < values.length - 1)
                    valueCount = String.valueOf(valueCount) + ",";
            }
            PreparedStatement ps = this.con.prepareStatement("INSERT INTO " + this.table.getName() + this.table.getValues() + " VALUES(" + valueCount + ");");
            for (int j = 0; j < values.length; j++)
                ps.setObject(j + 1, values[j]);
            ps.executeUpdate();
        } catch (SQLException e) {
            Bukkit.getLogger().severe("[PvpLevels] Failed SQL task:");
            StackUtil.dumpStack(e);
            return;
        }
    }


    @Override
    public Object get(String parameter1, String parameter2, Object objParam) {
        try {
            PreparedStatement preparedStatement = this.con.prepareStatement("SELECT * FROM " + this.table.getName() + " WHERE " + parameter1 + "=?;");
            preparedStatement.setObject(1, objParam);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) return resultSet.getObject(parameter2);
        } catch (SQLException e) {
            Bukkit.getLogger().severe(prefix + "Failed SQL task.");
            StackUtil.dumpStack(e);
        }
        return null;
    }

    @Override
    public boolean contains(String parameterString, Object parameterObject) {
        try {
            PreparedStatement preparedStatement = this.con.prepareStatement("SELECT * FROM " + this.table.getName() + " WHERE " + parameterString + "=?;");
            preparedStatement.setObject(1, parameterObject);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            Bukkit.getLogger().severe(prefix + "Failed SQL task.");
            StackUtil.dumpStack(e);
            return false;
        }
    }

    @Override
    public void update(String parameter1, String parameter2, Object paramObj1, Object paramObj2) {
        try {
            PreparedStatement preparedStatement = this.con.prepareStatement("UPDATE " + this.table.getName() + " SET " + parameter2 + "=? WHERE " + parameter1 + "=?;");
            preparedStatement.setObject(1, paramObj2);
            preparedStatement.setObject(2, paramObj1);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            Bukkit.getLogger().severe(prefix + "Failed SQL task.");
            StackUtil.dumpStack(e);
            return;
        }
    }

    @Override
    public void remove(String paramString1, Object objParam) {
        try {
            PreparedStatement preparedStatement = this.con.prepareStatement("DELETE FROM " + this.table.getName() + " WHERE " + paramString1 + "=?;");
            preparedStatement.setObject(1, objParam);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            Bukkit.getLogger().severe(prefix + "Failed SQL task.");
            StackUtil.dumpStack(e);
            return;
        }
    }

    @Override
    public boolean isOpen() {
        return !(this.con == null && this.statement == null);
    }

    @Override
    public void open() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch(ClassNotFoundException e) {
            Bukkit.getLogger().severe(prefix + "Failed to open SQLite driver.");
            StackUtil.dumpStack(e);
            return;
        }
        try {
            this.con = DriverManager.getConnection("jdbc:sqlite:" + this.fileDirectory + File.separator + this.fileName);
            this.statement = this.con.createStatement();
            this.statement.setQueryTimeout(30);
        } catch (SQLException e) {
            Bukkit.getLogger().severe(prefix + "Failed to open SQLite connection");
            StackUtil.dumpStack(e);
            return;
        }
    }

    @Override
    public void close() {
        try {
            if(this.statement != null)
                this.statement.close();
            if(this.con != null)
                this.con.close();
            } catch (SQLException e) {
            Bukkit.getLogger().severe(prefix + "Failed to close SQL");
            StackUtil.dumpStack(e);
        }
    }
}
