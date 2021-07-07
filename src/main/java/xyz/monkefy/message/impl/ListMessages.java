package xyz.monkefy.message.impl;

import org.bukkit.ChatColor;
import xyz.monkefy.message.MessageManager;

import java.util.Collections;
import java.util.List;

public enum ListMessages implements MessageManager<List<String>> {
    LEVEL_SEE_SELF("general.LEVEL_SEE_SELF"),
    STAFF_COMMAND_LIST("staff.COMMAND_LIST")
    ;

    private final String path;
    private List<String> message;

    ListMessages(String path) {
        this.path = path;
        message = Collections.singletonList(ChatColor.RED + "Failed to load messages.");
    }

    @Override
    public List<String> get() {
        return message;
    }

    @Override
    public void set(List<String> message) {
        this.message = message;
    }

    @Override
    public String getPath() {
        return path;
    }
}
