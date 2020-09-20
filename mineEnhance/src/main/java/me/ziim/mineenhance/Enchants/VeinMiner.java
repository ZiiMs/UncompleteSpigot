package me.ziim.mineenhance.Enchants;

import me.ziim.mineenhance.CustomEnchants;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.awt.*;
import java.util.Collection;
import java.util.HashSet;

public class VeinMiner implements Listener {
    private static void getOres(Block anchor, HashSet<Block> ores) {
        if (ores.size() > 200) return;

        Block nextAnchor = null;
        nextAnchor = anchor.getRelative(BlockFace.NORTH);
        if (nextAnchor.getType().equals(anchor.getType()) && !ores.contains(nextAnchor)) {
            ores.add(nextAnchor);
            getOres(nextAnchor, ores);
        }
        nextAnchor = anchor.getRelative(BlockFace.NORTH_EAST);
        if (nextAnchor.getType().equals(anchor.getType()) && !ores.contains(nextAnchor)) {
            ores.add(nextAnchor);
            getOres(nextAnchor, ores);
        }
        nextAnchor = anchor.getRelative(BlockFace.EAST);
        if (nextAnchor.getType().equals(anchor.getType()) && !ores.contains(nextAnchor)) {
            ores.add(nextAnchor);
            getOres(nextAnchor, ores);
        }
        nextAnchor = anchor.getRelative(BlockFace.SOUTH_EAST);
        if (nextAnchor.getType().equals(anchor.getType()) && !ores.contains(nextAnchor)) {
            ores.add(nextAnchor);
            getOres(nextAnchor, ores);
        }
        nextAnchor = anchor.getRelative(BlockFace.SOUTH);
        if (nextAnchor.getType().equals(anchor.getType()) && !ores.contains(nextAnchor)) {
            ores.add(nextAnchor);
            getOres(nextAnchor, ores);
        }
        nextAnchor = anchor.getRelative(BlockFace.SOUTH_WEST);
        if (nextAnchor.getType().equals(anchor.getType()) && !ores.contains(nextAnchor)) {
            ores.add(nextAnchor);
            getOres(nextAnchor, ores);
        }
        nextAnchor = anchor.getRelative(BlockFace.WEST);
        if (nextAnchor.getType().equals(anchor.getType()) && !ores.contains(nextAnchor)) {
            ores.add(nextAnchor);
            getOres(nextAnchor, ores);
        }
        nextAnchor = anchor.getRelative(BlockFace.NORTH_WEST);
        if (nextAnchor.getType().equals(anchor.getType()) && !ores.contains(nextAnchor)) {
            ores.add(nextAnchor);
            getOres(nextAnchor, ores);
        }
        nextAnchor = anchor.getRelative(BlockFace.UP);
        if (nextAnchor.getType().equals(anchor.getType()) && !ores.contains(nextAnchor)) {
            ores.add(nextAnchor);
            getOres(nextAnchor, ores);
        }
        nextAnchor = anchor.getRelative(BlockFace.DOWN);
        if (nextAnchor.getType().equals(anchor.getType()) && !ores.contains(nextAnchor)) {
            ores.add(nextAnchor);
            getOres(nextAnchor, ores);
        }
        nextAnchor = anchor.getRelative(BlockFace.SELF);
        if (nextAnchor.getType().equals(anchor.getType()) && !ores.contains(nextAnchor)) {
            ores.add(nextAnchor);
            getOres(nextAnchor, ores);
        }

    }

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
        if (event.getPlayer().getInventory().getItemInMainHand().getItemMeta().hasEnchant(CustomEnchants.VEINMINER)) {
            event.setDropItems(false);
            Player player = event.getPlayer();
            Block block = event.getBlock();
            Material blockType = event.getBlock().getType();
            HashSet<Block> blocks = new HashSet<Block>();
            if (blockType.equals(Material.IRON_ORE) || blockType.equals(Material.COAL_ORE) || blockType.equals(Material.GOLD_ORE) || blockType.equals(Material.DIAMOND_ORE) || blockType.equals(Material.LAPIS_ORE)
                    || blockType.equals(Material.REDSTONE_ORE) || blockType.equals(Material.EMERALD_ORE) || blockType.equals(Material.NETHER_QUARTZ_ORE)) {
                getOres(block, blocks);
                if (blocks.size() != 0) {
                    blocks.iterator().forEachRemaining(s -> {
                        if (event.getPlayer().getInventory().getItemInMainHand().getItemMeta().hasEnchant(CustomEnchants.TELEPATHY)) {
                            if (event.getPlayer().getInventory().firstEmpty() != -1) {
                                Collection<ItemStack> drops = s.getDrops(player.getInventory().getItemInMainHand());
                                if (!drops.isEmpty()) {
                                    player.getInventory().addItem(drops.iterator().next());
                                }
                                s.setType(Material.AIR);
                            } else {
                                s.breakNaturally(event.getPlayer().getInventory().getItemInMainHand());
                            }
                        } else {
                            s.breakNaturally(event.getPlayer().getInventory().getItemInMainHand());
                        }
                    });
                    ItemStack modifiedItem = player.getInventory().getItemInMainHand();
                    ItemMeta itemMeta = modifiedItem.getItemMeta();
                    int dmg = ((Damageable) itemMeta).getDamage();
                    if (itemMeta instanceof Damageable) {
                        ((Damageable) itemMeta).setDamage(dmg + blocks.size());
                    }
                    modifiedItem.setItemMeta(itemMeta);
                    if (dmg + blocks.size() >= modifiedItem.getType().getMaxDurability()) {
                        player.getInventory().removeItem(modifiedItem);
                        player.updateInventory();
                    }
                }
            }
        }
    }
}
