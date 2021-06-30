package xyz.monkefy;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class Levels extends JavaPlugin {

    private static String prefix;

    private static Levels a;

    @Override
    public void onEnable() {
        prefix = this.getConfig().getString("plugin-prefix");
        a = this;
        Logger logger = this.getLogger();
        logger.info("Enabling plugin.");

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
}
