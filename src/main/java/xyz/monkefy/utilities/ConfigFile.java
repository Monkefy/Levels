package xyz.monkefy.utilities;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.logging.Logger;

public class ConfigFile {

    private final String name;
    private FileConfiguration fileConfiguration;
    private File file;
    private final Logger logger;

    public ConfigFile(String name, Plugin plugin) {
        this.file = new File(plugin.getDataFolder(), name);
        if(!file.exists()) {
            this.file.getParentFile().mkdirs();
            plugin.saveResource(name, false);

        }
        this.name = name;
        this.logger = plugin.getLogger();
        try {
            this.fileConfiguration = (FileConfiguration) new YamlConfiguration();
            this.fileConfiguration.load(file);
        } catch (Exception e) {
            this.logger.warning("Unable to load messages.");
        }
    }

    public void save() {
        try {
            fileConfiguration.save(file);
            reload();
        } catch (Exception e) {
            logger.info("Couldn't save config file: " + name);
        }
    }

    public void reload() {
        try {
            this.fileConfiguration = YamlConfiguration.loadConfiguration(this.file);
        } catch (Exception e) {
            logger.info("Couldn't reload config file: " + name);
        }
    }

    public FileConfiguration getFileConfiguration() {
        return fileConfiguration;
    }


}
