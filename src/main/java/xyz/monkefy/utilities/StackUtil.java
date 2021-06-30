package xyz.monkefy.utilities;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import xyz.monkefy.Levels;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StackUtil {

    public static String NAME;

    public static String VERSION;

    public static final Levels plugin = Levels.getInstance();

    public static final BukkitScheduler scheduler = plugin.getServer().getScheduler();

    public static final Logger logger = plugin.getLogger();

    static {
        NAME = plugin.getDescription().getName();
        VERSION = plugin.getDescription().getVersion();
    }

    public static void dumpStack(Throwable t) {
        ChatColor a = ChatColor.GREEN;
        ChatColor b = ChatColor.GRAY;
        String prefix = "[" + NAME + "] ";
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);
        String[] sts = sw.toString().replace("\r", "").split("\n");
        StackTraceElement[] rawElements = t.getStackTrace();
        List<StackTraceElement> elements = new ArrayList<>();
        String[] out = new String[sts.length + elements.size() * 4 + 9];
        out[0] = String.valueOf(prefix) + ChatColor.RED + "Internal error!";
        out[1] = String.valueOf(prefix) + "If this bug has not been reported please open a ticket at BukkitDev";
        out[2] = String.valueOf(prefix) + "Include the following into your bug report:";
        out[3] = String.valueOf(prefix) + "          ====== " + a + "STACK TRACE" + b + " ======";
        int j = 0;
        for (int i = 4; i - 4 < sts.length; i++)
            out[i] = String.valueOf(prefix) + sts[j = i - 4];
        j += 4;
        out[j++] = String.valueOf(prefix) + "          ====== " + a + "DUMP" + b + " ======";
        out[j++] = String.valueOf(prefix) + "plugin name: " + NAME;
        out[j++] = String.valueOf(prefix) + "plugin version: " + VERSION;
        out[j++] = String.valueOf(prefix) + "bukkit version: " + Bukkit.getBukkitVersion();
        out[j++] = String.valueOf(prefix) + "description: " + t.getMessage();
        int k = 1;
        for (StackTraceElement e : elements) {
            out[j++] = String.valueOf(prefix) + "          ====== " + a + "Element #" + k + b + " ======";
            out[j++] = String.valueOf(prefix) + "class: " + e.getClassName();
            out[j++] = String.valueOf(prefix) + "at line: " + e.getLineNumber();
            out[j++] = String.valueOf(prefix) + "method: " + e.getMethodName();
            k++;
        }
        Runnable task = new SyncMessagePair(null, out, null);
        scheduler.scheduleSyncDelayedTask((Plugin)plugin, task);
    }

    public static class SyncMessagePair implements Runnable {
        private CommandSender receiver;

        private String[] messages;

        private Level level;

        public SyncMessagePair(Player player, String[] messages, Level level) {
            this.receiver = (player != null) ? (CommandSender)player : (CommandSender) Bukkit.getServer().getConsoleSender();
            this.messages = messages;
            this.level = level;
        }

        public void run() {
            if (this.receiver instanceof Player && !((Player)this.receiver).isOnline())
                return;
            boolean toPlayer = this.receiver instanceof Player;
            byte b;
            int i;
            String[] arrayOfString;
            for (i = (arrayOfString = this.messages).length, b = 0; b < i; ) {
                String msg = arrayOfString[b];
                if (msg != null) {
                    if (!toPlayer && this.level != null)
                        msg = "[" + this.level.toString() + "] " + msg;
                    this.receiver.sendMessage(msg);
                }
                b++;
            }
        }
    }

}
