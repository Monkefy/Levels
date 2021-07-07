package xyz.monkefy.prefixes.impl;

import org.bukkit.ChatColor;
import xyz.monkefy.prefixes.PrefixFile;
import xyz.monkefy.prefixes.PrefixManager;

public enum Prefixes implements PrefixManager<String> {
    level_1_9("level-1-9"),
    level_10_19("level-10-19"),
    level_20_29("level-20-29"),
    level_30_39("level-30-39"),
    level_40_49("level-40-49"),
    level_50_59("level-50-59"),
    level_60_69("level-60-69"),
    level_70_79("level-70-79"),
    level_80_89("level-80-89"),
    level_90_100("level-90-100"),
    level_above_100("level-above-100"),
    symbol_prestige_1("symbol-prestige-1"),
    symbol_prestige_2("symbol-prestige-2"),
    symbol_prestige_3("symbol-prestige-3"),
    symbol_prestige_above_3("symbol-prestige-above-3")
    ;

    private String path;
    String message;

    Prefixes(String path) {
        this.path = path;
        message = ChatColor.RED + "Failed to load.";
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
