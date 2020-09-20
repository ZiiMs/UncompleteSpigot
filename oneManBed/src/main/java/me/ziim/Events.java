package me.ziim;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.plugin.Plugin;

public class Events implements Listener {

    private final Plugin plugin = OneManBed.getPlugin(OneManBed.class);

    @EventHandler
    public void onSleep(PlayerBedEnterEvent e) {
        Player player = e.getPlayer();
        Block bed = e.getBed();
        PlayerBedEnterEvent.BedEnterResult bedResult = e.getBedEnterResult();

        e.setCancelled(true);
        e.setUseBed(Event.Result.DENY);
        if (bedResult.equals(PlayerBedEnterEvent.BedEnterResult.OK) /*  || (bedResult.equals(PlayerBedEnterEvent.BedEnterResult.NOT_POSSIBLE_NOW) && (player.getWorld().hasStorm() || player.getWorld().isThundering())) */) {
            player.sendMessage("bedOKayer!!!");
            e.setUseBed(Event.Result.ALLOW);
        }

        player.sendMessage("Is Sleeping?: " + player.isSleeping());
        player.sendMessage("Sleep ticks?: " + player.getSleepTicks());
        player.sendMessage("Bed result: " + bedResult.toString());
        this.plugin.getServer().getScheduler().scheduleSyncRepeatingTask(this.plugin, new Runnable() {
            @Override
            public void run() {
                if (player.isSleeping()) {
                    if (player.getSleepTicks() == 100) {
                        for (Player p : player.getWorld().getPlayers()) {
                            p.sendMessage(player.getDisplayName() + ChatColor.YELLOW + " has slept the time away.");
                        }
                        player.getWorld().setTime(0);
                        player.getWorld().setThundering(false); /* Sets the weather to stop it from thundering, not sure if needed but redundancies are very welcome! */
                        player.getWorld().setStorm(false);
                        player.wakeup(true);
                    }
                }
            }
        }, 0, 30);
    }
}
