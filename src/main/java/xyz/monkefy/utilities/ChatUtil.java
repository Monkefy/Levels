package xyz.monkefy.utilities;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ChatUtil {

    public String color(String messageToColor) {
        return ChatColor.translateAlternateColorCodes('&', messageToColor);
    }

    public List<String> colour(List<String> list) {
        List<String> stringList = new ArrayList<>();
        for (String str : list)
            stringList.add(color(str));
        return stringList;
    }

    public void sendMessage(Player p, String message) {
        p.sendMessage(color(message));
    }

    public void sendListMessage(Player p, Iterable<String> listMessage) {
        for (String message : listMessage)
            p.sendMessage(color(message));
    }

    public void sendMessageToConsole(String message) {
        Bukkit.getConsoleSender().sendMessage(color(message));
    }

    public void performCommands(Player player, List<String> commands) {
        for(String str : commands) {
            Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), str.replace("{player}", player.getName()));
        }
    }

    public void sendAnnouncement(String message) {
        Bukkit.getServer().broadcastMessage(color(message));
    }

}
