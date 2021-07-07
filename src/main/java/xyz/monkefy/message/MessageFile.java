package xyz.monkefy.message;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import xyz.monkefy.Levels;
import xyz.monkefy.message.impl.ListMessages;
import xyz.monkefy.message.impl.Message;
import xyz.monkefy.utilities.ChatUtil;
import xyz.monkefy.utilities.ConfigFile;
import xyz.monkefy.utilities.StackUtil;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MessageFile {
    private final String prefix = "[Levels] ";
    private ConfigFile configFile;
    private ChatUtil chatUtil = new ChatUtil();
    private final FileConfiguration fileConfiguration;
    private Logger logger;

    public MessageFile(Levels levels) {
        YamlConfiguration yamlConfiguration = null;
        this.logger = Bukkit.getLogger();
        this.configFile = new ConfigFile("messages.yml", (Plugin) levels);
        FileConfiguration defaultValues = null;

        try {
            InputStreamReader inputStreamReader = new InputStreamReader(levels.getResource("messages.yml"), StandardCharsets.UTF_8);
            yamlConfiguration = YamlConfiguration.loadConfiguration(inputStreamReader);
            this.logger.info(prefix + "Successfully created messages.yml.");
        } catch (Exception e) {
            this.logger.severe(prefix + "Failed to create messages.yml");
            StackUtil.dumpStack(e);
        }

        this.fileConfiguration = yamlConfiguration;
    }

    public void reload() {
        this.configFile.reload();
        load();
    }

    public void load() {
        this.logger.info(prefix + "Loading messages.yml...");
        for(Message message : Message.values()) {
                String theMessage = this.configFile.getFileConfiguration().getString(message.getPath());
                if(theMessage == null) {
                    logger.info(prefix + "Couldn't get default value for " + message.getPath() + ", creating with default values.");
                    theMessage = this.fileConfiguration.getString(message.getPath());
                    if (theMessage == null) {
                        logger.severe(prefix + "Default value for " + message.getPath() + " could not be created.");
                        theMessage = "&cThere was no default created on the startup.";
                    } else {
                        logger.info(prefix + "Created: Default value for " + message.getPath() + ".");
                        }
                    }
                message.set(chatUtil.color(theMessage));

            }
            for(ListMessages listMessages : ListMessages.values()) {
                List<String> theListMessage = this.configFile.getFileConfiguration().getStringList(listMessages.getPath());
                if(theListMessage == null) {
                    logger.info(prefix + "Couldn't get default values for " + listMessages.getPath() + ", creating with default values.");
                    theListMessage = this.fileConfiguration.getStringList(listMessages.getPath());
                    if (theListMessage == null) {
                        logger.severe(prefix + "Default value for " + listMessages.getPath() + " could not be created.");
                        theListMessage = Collections.singletonList(ChatColor.RED + "There was no default created on the startup.");
                    } else {
                        logger.info(prefix + "Created: Default value for " + listMessages.getPath());
                    }
                }
                listMessages.set(chatUtil.colour(theListMessage));
            }
            this.logger.info(prefix + "Successfully loaded: messages.yml.");
    }


}
