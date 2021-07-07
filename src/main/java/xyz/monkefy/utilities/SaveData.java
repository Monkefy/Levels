package xyz.monkefy.utilities;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.monkefy.Levels;
import xyz.monkefy.ThePlayer;

public class SaveData {

    public SaveData() {
        new BukkitRunnable() {

            @Override
            public void run() {
                Bukkit.getLogger().info("Saving data.");
                for(Player player : Bukkit.getOnlinePlayers()) {
                    try {
                        ThePlayer thePlayer = Levels.getInstance().getPlayer(player);
                        if(thePlayer.isCreated()) {
                            thePlayer.save();
                        } else Bukkit.getLogger().severe("Failed to save data!");
                    } catch (Exception e) {

                        StackUtil.dumpStack(e);
                    }

                }
                Bukkit.getLogger().info("Saved data.");
            }
        }.runTaskTimerAsynchronously(Levels.getInstance(),24000L, 24000L);
    }
}
