package xyz.monkefy.message.impl;

import org.bukkit.ChatColor;
import xyz.monkefy.message.MessageManager;

public enum Message implements MessageManager<String> {
    LEVEL_UP("general.LEVEL_UP"),
    NO_PERMISSION("general.NO_PERMISSION"),
    CONSOLE_NO_PERMISSION("general.CONSOLE_NO_PERMISSION"),
    LEVEL_SEE_OTHER("general.LEVEL_SEE_OTHER"),
    UNKNOWN_ARGS("general.UNKNOWN_ARGS"),
    PRESTIGE_MESSAGE("general.PRESTIGE_MESSAGE_GLOBAL"),
    PLAYER_DOES_NOT_EXIST("general.PLAYER_DOES_NOT_EXIST"),
    LEVEL_SEE_LINE_1("general.LEVEL_SEE_ONE"),
    LEVEL_SEE_LINE_2("general.LEVEL_SEE_TWO"),
    UNABLE_TO_PRESTIGE("general.UNABLE_TO_PRESTIGE"),
    KILLED_PLAYER("general.KILLED_PLAYER");

    private final String path;
    private String message;

    Message(String path) {
        this.path = path;
        this.message = ChatColor.RED + "Failed to load messages.";
    }

    @Override
    public String get() {
        return message;
    }

    @Override
    public void set(String message) {
        this.message = message;
    }

    @Override
    public String getPath() {
        return path;
    }

    public String toString() {
        return path;
    }
}
