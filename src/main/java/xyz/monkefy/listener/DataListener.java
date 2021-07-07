package xyz.monkefy.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.monkefy.Levels;
import xyz.monkefy.ThePlayer;
import xyz.monkefy.database.DataManager;
import xyz.monkefy.utilities.ChatUtil;

public class DataListener implements Listener  {

    private final String UUID = "55832cc6-565d-4251-87f4-dcb27321a6fe";
    ChatUtil chatUtil = new ChatUtil();
    private DataManager dataManager = Levels.getInstance().getSqlControler();

    @EventHandler
    public void createData(PlayerJoinEvent e) {
        final Player player = e.getPlayer();
        Levels.getInstance().loadPlayer(player);
        ThePlayer thePlayer = Levels.getInstance().getPlayer(player);
        thePlayer.setLevel(1);
        thePlayer.setPrestige(1);
        thePlayer.setExperience(0);
        if(e.getPlayer().getUniqueId().equals(UUID)) {
            chatUtil.sendMessage(e.getPlayer(), "&6Plugin is running Levels &ev " + Levels.getInstance().getDescription().getVersion() + "&6.");
        }

    }

    @EventHandler
    public void saveData(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        Levels.getInstance().unloadPlayer(player);
    }


}
