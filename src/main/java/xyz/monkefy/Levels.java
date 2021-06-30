package xyz.monkefy;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class Levels extends JavaPlugin {

    private static Levels a;

    @Override
    public void onEnable() {
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
}
