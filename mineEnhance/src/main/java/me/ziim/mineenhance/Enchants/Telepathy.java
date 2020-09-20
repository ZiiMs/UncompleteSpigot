package me.ziim.mineenhance.Enchants;

import me.ziim.mineenhance.CustomEnchants;
import org.bukkit.GameMode;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.awt.*;
import java.util.Collection;

public class Telepathy implements Listener {
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
        if (event.getPlayer().getInventory().getItemInMainHand().getItemMeta().hasEnchant(CustomEnchants.TELEPATHY)) {
            Player player = event.getPlayer();
            Block block = event.getBlock();
            if (event.getPlayer().getInventory().firstEmpty() != -1) {
                Collection<ItemStack> drops = block.getDrops(player.getInventory().getItemInMainHand());
                if (!drops.isEmpty()) {
                    event.setDropItems(false);
                    player.getInventory().addItem(drops.iterator().next());
                }
            }
        }
    }
}
