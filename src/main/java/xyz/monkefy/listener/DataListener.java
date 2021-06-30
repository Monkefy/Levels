package xyz.monkefy.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import xyz.monkefy.Levels;
import xyz.monkefy.ThePlayer;

public class DataListener implements Listener  {

    @EventHandler
    public void createData(PlayerJoinEvent e) {
        ThePlayer tp = Levels.getInstance().getPlayer(e.getPlayer());
        tp.create();
    }

    @EventHandler
    public void saveData(PlayerQuitEvent e) {
        ThePlayer tp = Levels.getInstance().getPlayer(e.getPlayer());
        tp.save();
    }
}
