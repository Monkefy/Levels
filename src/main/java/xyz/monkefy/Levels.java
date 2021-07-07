package xyz.monkefy;

import com.sun.glass.ui.Window;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.monkefy.commands.AdminCommands;
import xyz.monkefy.commands.PlayerCommands;
import xyz.monkefy.database.*;
import xyz.monkefy.listener.ChatListener;
import xyz.monkefy.listener.DataListener;
import xyz.monkefy.listener.LevelListener;
import xyz.monkefy.message.MessageFile;
import xyz.monkefy.prefixes.PrefixFile;
import xyz.monkefy.prefixes.impl.Prefixes;
import xyz.monkefy.utilities.SaveData;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public final class Levels extends JavaPlugin {

    private static String prefix;
    public Map<String, ThePlayer> playerList = new HashMap<>();
    private MessageFile messageFile;
    private PrefixFile prefixFile;
    private DataManager sqlControler;
    private SQLThread sqlThread;
    private static Levels a;

    @Override
    public void onEnable() {
        prefix = this.getConfig().getString("plugin-prefix");
        a = this;
        Logger logger = this.getLogger();
        logger.info("Enabling plugin...");
        logger.info("Loading database...");
        FileConfiguration config = getConfig();
        Table table = new Table("players", "username VARCHAR(50) NOT NULL UNIQUE,level INT,experience INT,prestige INT");
        this.sqlControler = createSQLControler(config, "leveldata");
        this.sqlControler.setTable(table);
        this.sqlThread = new SQLThread(this.sqlControler, 300L);
        this.sqlThread.start();
        PluginManager pluginManager = this.getServer().getPluginManager();
        pluginManager.registerEvents(new DataListener(), this);
        pluginManager.registerEvents(new LevelListener(), this);
        pluginManager.registerEvents(new ChatListener(), this);
        getCommand("lvl").setExecutor(new PlayerCommands());
        getCommand("level").setExecutor(new PlayerCommands());
        getCommand("prestige").setExecutor(new PlayerCommands());
        getCommand("levelconfig").setExecutor(new AdminCommands());
        getCommand("lconfig").setExecutor(new AdminCommands());
        new SaveData();
        logger.info("Loaded database.");
        logger.info("Loading necessary files...");
        this.messageFile = new MessageFile(this);
        this.messageFile.load();
        this.prefixFile = new PrefixFile(this);
        this.prefixFile.load();
        this.getConfig().options().copyDefaults();
        this.saveDefaultConfig();
        logger.info("Loaded necessary files.");
        logger.info("Finished enabling.");
    }

    @Override
    public void onDisable() {
        this.sqlThread.interrupt();
        this.sqlControler.close();
        this.saveConfig();
    }

    public ThePlayer getPlayer(Player player) {
        return this.playerList.get(player.getName());
    }

    public ThePlayer getPlayer(String name) {
        return new ThePlayer(name);
    }

    public void createPlayer(Player player) {
        ThePlayer tp = new ThePlayer(player.getName());
        this.playerList.put(player.getName(), tp);
        if(!tp.isCreated()) {
            tp.create();
        }

    }

    public void savePlayer(Player player) {
        ThePlayer tp = this.playerList.remove(player.getName());
        if(tp != null)
            tp.save();
    }

    public void loadPlayer(Player player) {
        ThePlayer pp = new ThePlayer(player.getName());
        this.playerList.put(player.getName(), pp);
        if (!pp.isCreated())
            pp.create();
    }

    public void unloadPlayer(Player player) {
        ThePlayer pp = this.playerList.remove(player.getName());
        if (pp != null)
            pp.save();
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

    public DataManager getSqlControler() {
        return this.sqlControler;
    }
}
