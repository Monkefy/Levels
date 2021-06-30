package xyz.monkefy.database;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import xyz.monkefy.Levels;
import xyz.monkefy.utilities.StackUtil;

import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL extends SQLite implements DataManager {


    private String prefix  = Levels.getPrefix();
    private FileConfiguration fileConfiguration;

    private MySQL(String fileDirectory, String fileName) {
        super(fileDirectory, fileName);
    }

    public MySQL(FileConfiguration fileConfiguration) {
        super(fileConfiguration);
        this.fileConfiguration = fileConfiguration;
        this.open();
    }

    @Override
    public void open() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            Bukkit.getLogger().severe(prefix + "Failed to open MySQL Driver");
            StackUtil.dumpStack(e);
            return;
        }
        String host = this.fileConfiguration.getString("MySQL.host", "MySQLHost");
        String port = String.valueOf(this.fileConfiguration.getInt("MySQL.port", 0));
        String db = this.fileConfiguration.getString("MySQL.database", "MySQLDatabase");
        String user = this.fileConfiguration.getString("MySQL.username", "MySQlName");
        String password = this.fileConfiguration.getString("MySQL.password", "MySQLPassword");
        try {
            this.con = DriverManager.getConnection("jdbc:mysql://" + host + ':' + port + '/' + db + '?' +  "user=" + user + "&password=" + password);
            this.statement = this.con.createStatement();
            this.statement.setQueryTimeout(30);
        } catch (SQLException e) {
            Bukkit.getLogger().severe(prefix + "Failed to open MySQL connection");
            StackUtil.dumpStack(e);
        }
    }
}
