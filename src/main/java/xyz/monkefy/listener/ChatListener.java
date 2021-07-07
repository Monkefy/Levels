package xyz.monkefy.listener;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import xyz.monkefy.Levels;
import xyz.monkefy.ThePlayer;
import xyz.monkefy.prefixes.impl.Prefixes;

public class ChatListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void formatChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        String formatted = e.getFormat();
        ThePlayer thePlayer = Levels.getInstance().getPlayer(p);

        if(thePlayer.getLevel() >= 1 && thePlayer.getLevel() <= 9) {
            formatted = Prefixes.level_1_9.get()
                    .replace("{prestige_symbol}", getPrestigeSymbol(p))
                    .replace("{level}", String.valueOf(thePlayer.getLevel()))
                    + formatted;

        } else if(thePlayer.getLevel() >= 10 && thePlayer.getLevel() <= 19) {
            formatted = Prefixes.level_10_19.get()
                    .replace("{prestige_symbol}", getPrestigeSymbol(p))
                    .replace("{level}", String.valueOf(thePlayer.getLevel()))
                    + formatted;

        } else if(thePlayer.getLevel() >= 20 && thePlayer.getLevel() <= 29) {
            formatted = Prefixes.level_20_29.get()
                    .replace("{prestige_symbol}", getPrestigeSymbol(p))
                    .replace("{level}", String.valueOf(thePlayer.getLevel()))
                    + formatted;

        } else if(thePlayer.getLevel() >= 30 && thePlayer.getLevel() <= 39) {
            formatted = Prefixes.level_30_39.get()
                    .replace("{prestige_symbol}", getPrestigeSymbol(p))
                    .replace("{level}", String.valueOf(thePlayer.getLevel()))
                    + formatted;

        } else if(thePlayer.getLevel() >= 40 && thePlayer.getLevel() <=49) {
            formatted = Prefixes.level_40_49.get()
                    .replace("{prestige_symbol}", getPrestigeSymbol(p))
                    .replace("{level}", String.valueOf(thePlayer.getLevel()))
                    + formatted;

        } else if(thePlayer.getLevel() >= 50 && thePlayer.getLevel() <= 59) {
            formatted = Prefixes.level_50_59.get()
                    .replace("{prestige_symbol}", getPrestigeSymbol(p))
                    .replace("{level}", String.valueOf(thePlayer.getLevel()))
                    + formatted;

        } else if(thePlayer.getLevel() >= 60 && thePlayer.getLevel() <= 69) {
            formatted = Prefixes.level_60_69.get()
                    .replace("{prestige_symbol}", getPrestigeSymbol(p))
                    .replace("{level}", String.valueOf(thePlayer.getLevel()))
                    + formatted;

        } else if(thePlayer.getLevel() >= 70 && thePlayer.getLevel() <= 79) {
            formatted = Prefixes.level_70_79.get()
                    .replace("{prestige_symbol}", getPrestigeSymbol(p))
                    .replace("{level}", String.valueOf(thePlayer.getLevel()))
                    + formatted;

        } else if(thePlayer.getLevel() >= 80 && thePlayer.getLevel() <= 89) {
            formatted = Prefixes.level_80_89.get()
                    .replace("{prestige_symbol}", getPrestigeSymbol(p))
                    .replace("{level}", String.valueOf(thePlayer.getLevel()))
                    + formatted;

        } else if(thePlayer.getLevel() >= 90 && thePlayer.getLevel() <= 100) {
            formatted = Prefixes.level_90_100.get()
                    .replace("{prestige_symbol}", getPrestigeSymbol(p))
                    .replace("{level}", String.valueOf(thePlayer.getLevel()))
                    + formatted;

        } else if(thePlayer.getLevel() > 100) {
            formatted = Prefixes.level_above_100.get()
                    .replace("{prestige_symbol}", getPrestigeSymbol(p))
                    .replace("{level}", String.valueOf(thePlayer.getLevel()))
                    + formatted;
        }

        e.setFormat(formatted);

    }

    private String getPrestigeSymbol(Player player) {
        String symbol = null;
        ThePlayer thePlayer = Levels.getInstance().getPlayer(player);
        if(thePlayer.isCreated()) {
            String symbol_1 = Prefixes.symbol_prestige_1.get();
            String symbol_2 = Prefixes.symbol_prestige_2.get();
            String symbol_3 = Prefixes.symbol_prestige_3.get();
            String symbol_above_3 = Prefixes.symbol_prestige_above_3.get();
            if(thePlayer.getPrestige() == 1) {
                symbol = symbol_1;
            } else if(thePlayer.getPrestige() == 2) {
                symbol = symbol_2;
            } else if(thePlayer.getPrestige() == 3) {
                symbol = symbol_3;
            } else if(thePlayer.getPrestige() >= 4) {
                symbol = symbol_above_3;
            }
        }
        return symbol;
    }

}
