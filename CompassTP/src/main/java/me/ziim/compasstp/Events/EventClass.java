package me.ziim.compasstp.Events;

import me.ziim.compasstp.CompassTP;
import me.ziim.compasstp.CustomConfig;
import me.ziim.compasstp.CustomInventory;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.Plugin;
import org.javatuples.Pair;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static me.ziim.compasstp.Events.TPBeacon.beacons;


public class EventClass implements Listener {
    private final Plugin plugin = CompassTP.getPlugin(CompassTP.class);

    @EventHandler
    public void InvenClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        ClickType click = event.getClick();
        InventoryView open = event.getView();
        ItemStack item = event.getCurrentItem();

        if (open.getTitle().equals(ChatColor.DARK_GREEN + "Teleporter")) {
            event.setCancelled(true);
            if (item == null || !item.hasItemMeta()) {
                player.sendMessage("Returning null");
                return;
            }
            if (item.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "TP Home!")) {
                player.closeInventory();
                Location bedPos = player.getBedSpawnLocation();
                if (bedPos == null) {
                    player.sendMessage(ChatColor.RED + "You dont have a bed set you fuckin retard!!");
                } else {
                    player.sendMessage(ChatColor.RED + "Teleporting you in 5 seconds!");
                    this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable() {

                        @Override
                        public void run() {
                            player.teleport(bedPos);
                            player.sendMessage(ChatColor.RED + "Teleporting you home!!");
                        }
                    }, 100);
                }
            }
            if (item.getType().equals(Material.LECTERN)) {
                List<String> lore = item.getItemMeta().getLore();
                String var = lore.get(0);
                Location pos = new Location(Bukkit.getWorld(CustomConfig.get().getString("data." + player.getUniqueId() + "." + var + ".world")),
                        CustomConfig.get().getDouble("data." + player.getUniqueId() + "." + var + ".x"),
                        CustomConfig.get().getDouble("data." + player.getUniqueId() + "." + var + ".y"),
                        CustomConfig.get().getDouble("data." + player.getUniqueId() + "." + var + ".z"));
                player.teleport(pos);
                player.sendMessage(ChatColor.RED + "Teleporting you home!!");
            }
        }
        if (open.getTitle().equals(ChatColor.DARK_GREEN + "Beacon config")) {
            if (item != null) {
                event.setCancelled(true);
                if (item.getType().equals(Material.GREEN_STAINED_GLASS_PANE)) {
                    System.out.print("Opening add players");
                    CustomInventory i = new CustomInventory();
                    List<String> lore = item.getItemMeta().getLore();
                    String key = lore.get(0);
                    i.ListPlayer(player, ChatColor.BLUE + "Add players", key);
                }
                if (item.getType().equals(Material.RED_STAINED_GLASS_PANE)) {
                    System.out.print("Opening remove players");
                    List<String> lore = item.getItemMeta().getLore();
                    String key = lore.get(0);
                    CustomInventory i = new CustomInventory();
                    i.ListPlayer(player, ChatColor.BLUE + "Remove players", key);
                }
            }
        }
        if (open.getTitle().equals(ChatColor.BLUE + "Add players")) {
            if (item != null) {
                event.setCancelled(true);
                if (item.getType().equals(Material.PLAYER_HEAD)) {
                    SkullMeta skull = (SkullMeta) item.getItemMeta();
                    String var = String.valueOf(1);
                    if (CustomConfig.get().contains("data." + player.getUniqueId())) {
                        Set<String> tempList = CustomConfig.get().getConfigurationSection("data." + player.getUniqueId()).getKeys(false);
                        if (tempList.size() != 0) {
                            String max = Collections.max(tempList);
                            int tempInt = Integer.parseInt(max) + 1;
                            var = String.valueOf(tempInt);
                        }
                    }
                    System.out.print(var);
                    List<String> lore = item.getItemMeta().getLore();
                    String key = lore.get(0);
                    player.sendMessage(ChatColor.YELLOW + "You have selected " + skull.getOwningPlayer());
                    OfflinePlayer selped = skull.getOwningPlayer();
                    selped.getUniqueId();
                    Location loc = new Location(Bukkit.getWorld(CustomConfig.get().getString("data." + player.getUniqueId() + "." + key + ".world")),
                            CustomConfig.get().getDouble("data." + player.getUniqueId() + "." + key + ".x"),
                            CustomConfig.get().getDouble("data." + player.getUniqueId() + "." + key + ".y"),
                            CustomConfig.get().getDouble("data." + player.getUniqueId() + "." + key + ".z"));
                    String name = CustomConfig.get().getString("data." + player.getUniqueId() + "." + key + ".name");

                    CustomConfig.get().set("data." + selped.getUniqueId() + "." + var + ".x", (int) loc.getX());
                    CustomConfig.get().set("data." + selped.getUniqueId() + "." + var + ".y", (int) loc.getY());
                    CustomConfig.get().set("data." + selped.getUniqueId() + "." + var + ".z", (int) loc.getZ());
                    CustomConfig.get().set("data." + selped.getUniqueId() + "." + var + ".world", loc.getWorld().getName());
                    CustomConfig.get().set("data." + selped.getUniqueId() + "." + var + ".name", name);
                    CustomConfig.save();

                    beacons.put(loc.getBlock(), new Pair(selped.getUniqueId(), var));
                }
            }
        }
    }
}
