package me.ziim.compasstp;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;


public class ItemEvent implements Listener {

    @EventHandler
    public void OnItemUse(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = new ItemStack(Material.COMPASS);
        if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (item.isSimilar(event.getItem())) {
                CustomInventory i = new CustomInventory();
                i.TPInventory(player);
            }
        }
    }
}
