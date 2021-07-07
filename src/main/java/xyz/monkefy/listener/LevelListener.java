package xyz.monkefy.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import xyz.monkefy.Levels;
import xyz.monkefy.ThePlayer;
import xyz.monkefy.message.impl.Message;
import xyz.monkefy.utilities.ChatUtil;

import java.util.HashMap;

public class LevelListener implements Listener {

    private HashMap<String, String> attackers = new HashMap<>();

    @EventHandler
    public void putKiller(EntityDamageByEntityEvent e) {
        if(e.isCancelled()) return;
        Entity a = e.getDamager();
        Entity d = e.getEntity();
        if(e.getDamager() instanceof Player && e.getEntity() instanceof Player) {
            Player att = (Player) e.getDamager();
            Player deff = (Player) e.getEntity();
            attackers.put(deff.getName(), att.getName());
        }
    }

    @EventHandler
    public void deathEvent(EntityDeathEvent e) {
        Entity a = e.getEntity();
        if(a instanceof Player) {
            Player def = (Player) a;
            String defName = def.getName();
            String attName = attackers.get(defName);
            Player att = (attName == null) ? null : Bukkit.getPlayer(attName);
            if (att != null && att.isOnline()) {
                ThePlayer tpA = Levels.getInstance().getPlayer(att);
                ThePlayer tpD = Levels.getInstance().getPlayer(def);

                int level = tpA.getLevel();
                int experience = tpA.getExperience();
                int experience_Per_Kill = Levels.getInstance().getConfig().getInt("experience-received-per-kill");
                int experience_per_level_prestige_1 = Levels.getInstance().getConfig().getInt("experience-per-level-prestige-1");
                int experience_per_level_prestige_2 = Levels.getInstance().getConfig().getInt("experience-per-level-prestige-2");
                int experience_per_level_prestige_3 = Levels.getInstance().getConfig().getInt("experience-per-level-prestige-3");
                int max_level = Levels.getInstance().getConfig().getInt("max-level");


                if (def.getInventory().getHelmet() == null && def.getInventory().getChestplate() == null && def.getInventory().getLeggings() == null && def.getInventory().getBoots() == null) {
                    return;
                } else {

                    ChatUtil chatUtil = new ChatUtil();



                    if (tpA.getLevel() < max_level) {
                        chatUtil.sendMessage(att, Message.KILLED_PLAYER.get().replace("{player}", def.getName()).replace("{experience}", String.valueOf(experience_Per_Kill)));
                        tpA.setExperience(tpA.getExperience() + experience_Per_Kill);

                        if (tpA.getPrestige() == 1 && tpA.getExperience() >= experience_per_level_prestige_1 && tpA.getLevel() < max_level) {
                            tpA.setExperience(0);
                            tpA.setLevel(tpA.getLevel() + 1);

                            chatUtil.sendMessage(att, Message.LEVEL_UP.get().replace("{level}", String.valueOf(tpA.getLevel())));

                        } else if (tpA.getPrestige() == 2 && tpA.getExperience() >= experience_per_level_prestige_2 && tpA.getLevel() < max_level) {
                            tpA.setExperience(0);
                            tpA.setLevel(tpA.getLevel() + 1);

                            chatUtil.sendMessage(att, Message.LEVEL_UP.get().replace("{level}", String.valueOf(tpA.getLevel())));

                        } else if (tpA.getPrestige() == 3 && tpA.getExperience() >= experience_per_level_prestige_3 && tpA.getLevel() < max_level) {
                            tpA.setExperience(0);
                            tpA.setLevel(tpA.getLevel() + 1);

                            chatUtil.sendMessage(att, Message.LEVEL_UP.get().replace("{level}", String.valueOf(tpA.getLevel())));

                        } else  return;



                    }


                }


            }
        }
    }
}
