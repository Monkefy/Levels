package xyz.monkefy;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.monkefy.database.*;

import java.util.logging.Logger;

public final class Levels extends JavaPlugin {

    private static String prefix;

    private DataManager sqlControler;
    private SQLThread sqlThread;

    private static Levels a;

    @Override
    public void onEnable() {
        prefix = this.getConfig().getString("plugin-prefix");
        a = this;
        Logger logger = this.getLogger();
        logger.info(prefix + "Enabling plugin.");
        FileConfiguration config = getConfig();
        Table table = new Table("players", "username VARCHAR(50) NOT NULL UNIQUE, level INT, experience INT, prestige INT");
        this.sqlControler = createSQLControler(config, "data");
        this.sqlControler.setTable(table);
        this.sqlThread = new SQLThread(this.sqlControler, 300L);
        this.sqlThread.start();

    }

    @Override
    public void onDisable() {

    }

    public static Levels getInstance() {
        return a;
    }

    public static String getPrefix() {
        return prefix + " ";
    }

    private DataManager createSQLControler(FileConfiguration fileConfiguration, String fileName) {
        if(fileConfiguration.getBoolean("MySQL.use", false)) return ((DataManager)new MySQL(fileConfiguration));
        return (DataManager) new SQLite(getDataFolder().getPath(), String.valueOf(fileName) + ".db");
    }
}
