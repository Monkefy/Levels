package xyz.monkefy.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.monkefy.Levels;
import xyz.monkefy.ThePlayer;
import xyz.monkefy.message.impl.ListMessages;
import xyz.monkefy.message.impl.Message;
import xyz.monkefy.utilities.ChatUtil;

import java.util.ArrayList;
import java.util.List;

public class PlayerCommands implements CommandExecutor {

    ChatUtil chatUtil = new ChatUtil();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            chatUtil.sendMessageToConsole(Message.CONSOLE_NO_PERMISSION.get());
            return true;
        } else {
            Player p = (Player) sender;
            ThePlayer thePlayer = Levels.getInstance().getPlayer(p);
            int max_level = Levels.getInstance().getConfig().getInt("max-level");

            if (args.length == 0) {
                switch (command.getName()) {
                    case "lvl":
                    case "level":
                        chatUtil.sendMessage(p , Message.LEVEL_SEE_LINE_1.get()
                                .replace("{level}", String.valueOf(thePlayer.getLevel()))
                                .replace("{prestige}", String.valueOf(thePlayer.getPrestige())));
                        chatUtil.sendMessage(p, Message.LEVEL_SEE_LINE_2.get()
                                .replace("{experience}", String.valueOf(thePlayer.getExperience()))
                                .replace("{experiencemax}", String.valueOf(getMaxExperience(p))));
                        return true;
                    case "prestige":
                        if (thePlayer.isCreated()) {
                            if (thePlayer.getLevel() >= max_level) {
                                if (thePlayer.getPrestige() == 1) {
                                    chatUtil.performCommands(p, Levels.getInstance().getConfig().getStringList("prestige-1-rewards"));
                                    thePlayer.setPrestige(thePlayer.getPrestige() + 1);
                                    thePlayer.setLevel(1);
                                    thePlayer.setExperience(0);
                                    return true;
                                } else if (thePlayer.getPrestige() == 2) {
                                    chatUtil.performCommands(p, Levels.getInstance().getConfig().getStringList("prestige-2-rewards"));
                                    thePlayer.setPrestige(thePlayer.getPrestige() + 1);
                                    thePlayer.setLevel(1);
                                    thePlayer.setExperience(0);
                                    return true;
                                } else if (thePlayer.getPrestige() == 3) {
                                    chatUtil.performCommands(p, Levels.getInstance().getConfig().getStringList("prestige-3-rewards"));
                                    thePlayer.setPrestige(thePlayer.getPrestige() + 1);
                                    thePlayer.setLevel(1);
                                    thePlayer.setExperience(0);
                                    return true;
                                } else if (thePlayer.getPrestige() >= 4) {
                                    thePlayer.setPrestige(thePlayer.getPrestige() + 1);
                                    thePlayer.setLevel(1);
                                    thePlayer.setExperience(0);
                                    return true;
                                }

                                if (Levels.getInstance().getConfig().getBoolean("announce-prestige") == true) {
                                    chatUtil.sendAnnouncement(Message.PRESTIGE_MESSAGE.get()
                                            .replace("{player}", p.getName())
                                            .replace("{prestige}", String.valueOf(thePlayer.getPrestige())));
                                } else return true;
                            } else chatUtil.sendMessage(p, Message.UNABLE_TO_PRESTIGE.get()
                                    .replace("{max-level}", String.valueOf(max_level)));
                        } else chatUtil.sendMessage(p, "There was an unexpected error, please contact the admin.");
                }
            } else if (args.length == 1) {
                switch (command.getName()) {
                    case "lvl":
                    case "level":
                        ThePlayer tp;
                        String user = args[0];
                        Player target = Bukkit.getPlayer(user);
                        if (target != null && target.isOnline()) {
                            tp = Levels.getInstance().getPlayer(target);
                        } else {
                            tp = Levels.getInstance().getPlayer(user);
                        }
                        if (tp.isCreated()) {
                            int level = tp.getLevel();
                            int prestige = tp.getPrestige();
                            chatUtil.sendMessage(p, Message.LEVEL_SEE_OTHER.get()
                                    .replace("{level}", String.valueOf(level))
                                    .replace("{prestige}", String.valueOf(prestige))
                                    .replace("{player}", target.getName()));
                            return true;
                        } else chatUtil.sendMessage(p, Message.PLAYER_DOES_NOT_EXIST.get()
                                .replace("{player}", user));
                }

            } else chatUtil.sendMessage(p, "Unknown command. Type \"/help\" for help.");
        }
        return false;
    }

    public int getMaxExperience(Player player) {
        int maxexp = 0;
        ThePlayer thePlayer = Levels.getInstance().getPlayer(player);
        if(thePlayer.getPrestige() == 1) {
            maxexp = Levels.getInstance().getConfig().getInt("experience-per-level-prestige-1");
        } else if(thePlayer.getPrestige() == 2) {
            maxexp = Levels.getInstance().getConfig().getInt("experience-per-level-prestige-2");
        } else if(thePlayer.getPrestige() == 3) {
            maxexp = Levels.getInstance().getConfig().getInt("experience-per-level-prestige-3");
        } else if(thePlayer.getPrestige() > 3) {
            maxexp = Levels.getInstance().getConfig().getInt("experience-per-level-from-prestige-4");
        }

        return maxexp;
    }
}
