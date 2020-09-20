package me.ziim.mineenhance.Enchants;

import me.ziim.mineenhance.CustomEnchants;
import me.ziim.mineenhance.MineEnhance;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.awt.*;
import java.util.Collection;
import java.util.Random;

public class MakeRain implements Listener {
    private final Plugin plugin = MineEnhance.getPlugin(MineEnhance.class);
    private int counter;
    private Block blck;
    private ItemStack drpItem;

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.getPlayer().getInventory().getItemInMainHand() == null)
            return;
        if (!event.getPlayer().getInventory().getItemInMainHand().hasItemMeta())
            return;
        if (event.getPlayer().getGameMode() == GameMode.CREATIVE || event.getPlayer().getGameMode() == GameMode.SPECTATOR)
            return;
        if (event.getBlock().getState() instanceof Container)
            return;
        if (event.getPlayer().getInventory().getItemInMainHand().getItemMeta().hasEnchant(CustomEnchants.MAKERAIN)) {
            Player player = event.getPlayer();
            Block block = event.getBlock();
            Random rnd = new Random();
            boolean chance = rnd.nextInt(99) + 1 <= 5;
            if (chance) {
                Collection<ItemStack> drops = block.getDrops(player.getInventory().getItemInMainHand());
                if (drops.isEmpty()) return;
                ItemStack dropItem = drops.iterator().next();

                new BukkitRunnable() {
                    private int amount = rnd.nextInt(10);

                    public void run() {
                        if (amount <= 0) {
                            cancel();
                        }
                        Location loc = player.getLocation();
                        loc.setY(loc.getY() + 1);
                        int count = rnd.nextInt(3);
                        System.out.print("Second " + amount);
                        for (int i = 0; i < count; i++) {
                            player.getWorld().dropItem(loc, dropItem);
                        }
                        amount--;
                    }
                }.runTaskTimer(this.plugin, 0L, 20L);
            }
        }
    }
}
