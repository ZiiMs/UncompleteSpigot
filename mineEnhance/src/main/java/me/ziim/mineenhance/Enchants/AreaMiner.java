package me.ziim.mineenhance.Enchants;

import me.ziim.mineenhance.CustomEnchants;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.awt.*;

public class AreaMiner implements Listener {
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
        if (event.getPlayer().getInventory().getItemInMainHand().getItemMeta().hasEnchant(CustomEnchants.AREAMINE)) {
            Player player = event.getPlayer();
            Block block = event.getBlock();
            Block tempBlock = null;
            int Lvl = event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getEnchantLevel(CustomEnchants.AREAMINE);
            Location loc = event.getBlock().getLocation();
            final ItemStack modifiedItem = player.getInventory().getItemInMainHand();
            ItemMeta itemMeta = modifiedItem.getItemMeta();
            int dmg = ((Damageable) itemMeta).getDamage();
            if (block.getType().isSolid()) {
                switch (Lvl) {
                    case 1: {
                        for (int x = -1; x <= 1; x++) {
                            for (int y = -1; y <= 1; y++) {
                                for (int z = -1; z <= 1; z++) {

                                    tempBlock = loc.getWorld().getBlockAt(
                                            (int) loc.getX() + x,
                                            (int) loc.getY() + y,
                                            (int) loc.getZ() + z);
                                    if (!tempBlock.getType().equals(Material.BEDROCK) && !tempBlock.getLocation().equals(block.getLocation())) {
                                        tempBlock.breakNaturally(player.getInventory().getItemInMainHand());
                                    }
                                    dmg = dmg + 1;
                                }
                            }
                        }
                        break;
                    }
                    case 2: {
                        for (int x = -2; x <= 2; x++) {
                            for (int y = -2; y <= 2; y++) {
                                for (int z = -2; z <= 2; z++) {
                                    tempBlock = loc.getWorld().getBlockAt(
                                            (int) loc.getX() + x,
                                            (int) loc.getY() + y,
                                            (int) loc.getZ() + z);
                                    if (!tempBlock.getType().equals(Material.BEDROCK) && !tempBlock.getLocation().equals(block.getLocation())) {
                                        tempBlock.breakNaturally(player.getInventory().getItemInMainHand());
                                    }
                                    dmg = dmg + 1;
                                }
                            }
                        }
                        break;
                    }
                    case 3: {
                        for (int x = -3; x <= 3; x++) {
                            for (int y = -3; y <= 3; y++) {
                                for (int z = -3; z <= 3; z++) {
                                    tempBlock =
                                            loc.getWorld().getBlockAt(
                                            (int) loc.getX() + x,
                                            (int) loc.getY() + y,
                                            (int) loc.getZ() + z);
                                    if (!tempBlock.getType().equals(Material.BEDROCK) && !tempBlock.getLocation().equals(block.getLocation())) {
                                        tempBlock.breakNaturally(player.getInventory().getItemInMainHand());
                                    }
                                    dmg = dmg + 1;
                                }
                            }
                        }
                        break;
                    }
                }
            }
            if (itemMeta instanceof Damageable) {
                ((Damageable) itemMeta).setDamage(((Damageable) itemMeta).getDamage() + 5);
            }
            modifiedItem.setItemMeta(itemMeta);
        }
    }

}
