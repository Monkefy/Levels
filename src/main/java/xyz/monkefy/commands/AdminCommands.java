package xyz.monkefy.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.monkefy.Levels;
import xyz.monkefy.ThePlayer;
import xyz.monkefy.message.MessageFile;
import xyz.monkefy.message.impl.ListMessages;
import xyz.monkefy.message.impl.Message;
import xyz.monkefy.prefixes.PrefixFile;
import xyz.monkefy.utilities.ChatUtil;

public class AdminCommands implements CommandExecutor {

    private ChatUtil chatUtil = new ChatUtil();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(command.getName().equalsIgnoreCase("levelconfig") || command.getName().equalsIgnoreCase("lconfig")) {
            if(sender instanceof Player) {
                Player p = (Player) sender;
                if(p.hasPermission(Levels.getInstance().getConfig().getString("ADMIN-PERMISSION")) || p.isOp()) {
                    switch(args.length) {
                        case 0: 
                            chatUtil.sendListMessage(p, ListMessages.STAFF_COMMAND_LIST.get());
                        case 1:
                            if(args[0].equalsIgnoreCase("reload")) {
                            MessageFile messageFile = new MessageFile(Levels.getInstance());
                            PrefixFile prefixFile = new PrefixFile(Levels.getInstance());
                            messageFile.reload();
                            prefixFile.reload();
                            Levels.getInstance().reloadConfig();
                            chatUtil.sendMessage(p, "&6Successfully reloaded config.");
                        } else chatUtil.sendMessage(p, Message.UNKNOWN_ARGS.get());
                    
                    } else if(args.length > 2) {
                        ThePlayer thePlayer;
                        String user = args[1];
                        Player player = Bukkit.getPlayer(user);
                        if (player != null && player.isOnline()) {
                            thePlayer = Levels.getInstance().getPlayer(player);
                        } else {
                            thePlayer = Levels.getInstance().getPlayer(user);
                        }
                        
                        if(thePlayer.isCreated()) {
                            try {
                                int level = thePlayer.getLevel();
                                int prestige = thePlayer.getPrestige();
                                int experience = thePlayer.getExperience();
                                int value = Integer.parseInt(args[2]);
                                int max_prestige = Levels.getInstance().getConfig().getInt("max-prestige");
                                int max_level = Levels.getInstance().getConfig().getInt("max-level");
                                if(args[0].equalsIgnoreCase("addlevel")) {
                                    level = Math.min(level + value, max_level);
                                } else if(args[0].equalsIgnoreCase("removelevel")) {
                                    level = Math.min(level - value, 0);
                                } else if(args[0].equalsIgnoreCase("addprestige")) {
                                    prestige = Math.min(prestige + value, max_prestige);
                                } else if(args[0].equalsIgnoreCase("removeprestige")) {
                                    prestige = Math.min(prestige - value, 0);
                                }

                                thePlayer.setLevel(level);
                                thePlayer.setPrestige(prestige);
                                if(args[0].contains("prestige")) {
                                    chatUtil.sendMessage(p, "&6Changed prestige of &e" + user + "&6 to &e" + prestige);
                                } else if(args[0].contains("level")) {
                                    chatUtil.sendMessage(p, "&6Changed level of &e" + user + "&6 to &e" + level);
                                }
                            } catch (Exception e) {
                                chatUtil.sendMessage(p, "&6Invalid number '&e" + args[2] + "'&6!");
                            }
                        } else chatUtil.sendMessage(p, "&6Player &e" + user + " &6can not be located in the database.");
                    } else chatUtil.sendMessage(p, Message.UNKNOWN_ARGS.get());
                } else chatUtil.sendMessage(p, Message.NO_PERMISSION.get());
            } else chatUtil.sendMessageToConsole("&cYou are not allowed to do this in console.");

        }
        return true;
    }
}
