package xyz.monkefy.prefixes;

import jdk.nashorn.internal.runtime.regexp.joni.Config;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import xyz.monkefy.Levels;
import xyz.monkefy.message.MessageFile;
import xyz.monkefy.prefixes.impl.Prefixes;
import xyz.monkefy.utilities.ChatUtil;
import xyz.monkefy.utilities.ConfigFile;
import xyz.monkefy.utilities.StackUtil;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

public class PrefixFile {
    private final String prefix = "[Levels] ";
    private ConfigFile prefixFile;
    private ChatUtil chatUtil = new ChatUtil();
    private final FileConfiguration fileConfiguration;
    private Logger logger;

    public PrefixFile(Levels levels) {
        YamlConfiguration yamlConfiguration = null;
        this.logger = Bukkit.getServer().getLogger();
        this.prefixFile = new ConfigFile("prefixes.yml", (Plugin) levels);
        FileConfiguration defaultValues = null;

        try {
            InputStreamReader inputStreamReader = new InputStreamReader(levels.getResource("prefixes.yml"), StandardCharsets.UTF_8);
            yamlConfiguration = YamlConfiguration.loadConfiguration(inputStreamReader);
            this.logger.info(prefix + "Successfully created prefixes.yml.");
        } catch (Exception e) {
            this.logger.severe(prefix + "Could not create prefixes.yml.");
            StackUtil.dumpStack(e);
        }

        this.fileConfiguration = yamlConfiguration;
    }

    public void reload() {
        this.prefixFile.reload();
        load();
    }

    public void load() {
        this.logger.info(prefix + "Loading prefixes.yml...");
            for(Prefixes prefix : Prefixes.values()) {
                String pprefix = this.prefixFile.getFileConfiguration().getString(prefix.getPath());
                if (pprefix == null) {
                    logger.severe("Couldn't load " + prefix.getPath() + ", creating with default values.");
                    pprefix = this.fileConfiguration.getString(prefix.getPath());
                    if (pprefix == null) {
                        logger.severe("Default value for: " + prefix.getPath() + " could not be created.");
                        pprefix = "&cFailed to load.";
                    } else {
                        logger.info(prefix + "Created: default value for " + prefix.getPath() + ".");
                    }
                }
                prefix.set(chatUtil.color(pprefix));
            }
            this.logger.info(prefix + "Successfully loaded prefixes.yml.");
    }
}
