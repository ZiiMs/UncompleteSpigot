package me.ziim.mineenhance.Enchants;

import me.ziim.mineenhance.CustomEnchants;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.awt.*;

public class spawnerTouch implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if (e.getPlayer().getInventory().getItemInMainHand() == null)
            return;
        if (!e.getPlayer().getInventory().getItemInMainHand().hasItemMeta())
            return;
        if (e.getPlayer().getGameMode() == GameMode.CREATIVE || e.getPlayer().getGameMode() == GameMode.SPECTATOR)
            return;
        if (e.getBlock().getState() instanceof Container)
            return;
        if (e.getPlayer().getInventory().getItemInMainHand().getItemMeta().hasEnchant(CustomEnchants.SPAWNERTOUCH)) {
            Player player = e.getPlayer();
            Block block = e.getBlock();

            if (IsSpawner(block.getType())) {
                if (getSpawner(block.getState()) != null) {
                    CreatureSpawner spawner = getSpawner(block.getState());
                    block.setType(Material.AIR);
                    ItemStack spawnerBlock = new ItemStack(Material.SPAWNER);
                    ItemMeta meta = spawnerBlock.getItemMeta();
                    BlockStateMeta blockStateMeta = (BlockStateMeta) meta;
                    blockStateMeta.setDisplayName(spawner.getSpawnedType().toString().toLowerCase() + " spawner");
                    CreatureSpawner newSpawner = (CreatureSpawner) blockStateMeta.getBlockState();
                    newSpawner.setSpawnedType(spawner.getSpawnedType());
                    blockStateMeta.setBlockState(newSpawner);
                    spawnerBlock.setItemMeta(blockStateMeta);
                    player.getInventory().addItem(spawnerBlock);
                }
            }
        }
    }

    public boolean IsSpawner(Material block) {
        if (block.equals(Material.SPAWNER)) return true;
        return false;
    }

    public CreatureSpawner getSpawner(BlockState block) {

        if (block instanceof CreatureSpawner) {
            CreatureSpawner spawner = (CreatureSpawner) block;
            return spawner;
        }
        return null;
    }
}
