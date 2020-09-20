package me.ziim.compasstp;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.Plugin;
import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static me.ziim.compasstp.Events.TPBeacon.beacons;


public class CustomInventory implements Listener {
    private final Plugin plugin = CompassTP.getPlugin(CompassTP.class);

    public void TPInventory(Player player) {
        Inventory i = plugin.getServer().createInventory(null, 27, ChatColor.DARK_GREEN + "Teleporter");
        ItemStack tp = new ItemStack(Material.WHITE_BED, 1);
        ItemMeta tpMeta = tp.getItemMeta();
        tpMeta.setDisplayName(ChatColor.GREEN + "TP Home!");
        tp.setItemMeta(tpMeta);
        i.setItem(0, tp);
        System.out.print("Beacon size: " + beacons.size());
        int slot = 1;
        for (Map.Entry m : beacons.entrySet()) {
            Pair<UUID, String> tuple = (Pair<UUID, String>) m.getValue();
            UUID playerID = tuple.getValue0();
            String key = tuple.getValue1();
            if (playerID.equals(player.getUniqueId())) {
                tp.setType(Material.LECTERN);
                ItemMeta bcMeta = tp.getItemMeta();
                String name = CustomConfig.get().getString("data." + playerID + "." + key + ".name");
                List<String> lore = new ArrayList<String>();
                lore.add(0, key);
                if (isNumber(name)) {
                    bcMeta.setDisplayName("Unnamed beacon!");
                } else {
                    bcMeta.setDisplayName(name);
                }
//                bcMeta.setDisplayName(String.valueOf(key));
                bcMeta.setLore(lore);
                bcMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

                tp.setItemMeta(bcMeta);

                i.setItem(slot, tp);
                slot++;
            }
        }
        player.openInventory(i);
    }

    public void ConfigInventory(Player player, String key) {
        Inventory i = plugin.getServer().createInventory(null, 27, ChatColor.DARK_GREEN + "Beacon config");

        List<String> lore = new ArrayList<String>();
        lore.add(0, key);

        ItemStack add = new ItemStack(Material.GREEN_STAINED_GLASS_PANE, 1);
        ItemMeta addMeta = add.getItemMeta();
        addMeta.setDisplayName("Add player");
        addMeta.setLore(lore);
        add.setItemMeta(addMeta);

        ItemStack remove = new ItemStack(Material.RED_STAINED_GLASS_PANE, 1);
        ItemMeta removeMeta = remove.getItemMeta();
        removeMeta.setDisplayName("Remove player");
        removeMeta.setLore(lore);
        remove.setItemMeta(removeMeta);

//        ItemStack list = new ItemStack(Material.BLUE_STAINED_GLASS_PANE, 1);
//        ItemMeta listMeta = list.getItemMeta();
//        listMeta.setDisplayName("List players");
//        listMeta.setLore(lore);
//        list.setItemMeta(listMeta);


        i.setItem(11, add);
//        i.setItem(13, list);
        i.setItem(15, remove);

        player.openInventory(i);
    }

    public void ListPlayer(Player player, String name, String key) {
        if (name == null) {
            return;
        }
        System.out.print("ListPlayerinv open!");
        Inventory i = plugin.getServer().createInventory(null, 27, name);
        Location location = new Location(Bukkit.getWorld(CustomConfig.get().getString("data." + player.getUniqueId() + "." + key + ".world")),
                CustomConfig.get().getDouble("data." + player.getUniqueId() + "." + key + ".x"),
                CustomConfig.get().getDouble("data." + player.getUniqueId() + "." + key + ".y"),
                CustomConfig.get().getDouble("data." + player.getUniqueId() + "." + key + ".z"));
        Block block = location.getBlock();
        List<String> lore = new ArrayList<String>();
        lore.add(0, key);
        int id = 0;
        System.out.print(name.equalsIgnoreCase(ChatColor.BLUE + "List players"));
        if (name.equalsIgnoreCase(ChatColor.BLUE + "Add players")) {
            for (Player p : player.getWorld().getPlayers()) {
                if (p.getUniqueId() != player.getUniqueId()) {
                    for (String var : CustomConfig.get().getConfigurationSection("data." + p.getUniqueId()).getKeys(false)) {
                        System.out.print(var);
                        Location loc = new Location(Bukkit.getWorld(CustomConfig.get().getString("data." + p.getUniqueId() + "." + var + ".world")),
                                CustomConfig.get().getDouble("data." + p.getUniqueId() + "." + var + ".x"),
                                CustomConfig.get().getDouble("data." + p.getUniqueId() + "." + var + ".y"),
                                CustomConfig.get().getDouble("data." + p.getUniqueId() + "." + var + ".z"));
                        Block tempBlock = loc.getBlock();
                        if (!tempBlock.equals(block)) {
                            ItemStack head = new ItemStack(Material.PLAYER_HEAD, 1);
                            SkullMeta skull = (SkullMeta) head.getItemMeta();
                            skull.setOwningPlayer(Bukkit.getOfflinePlayer(p.getUniqueId()));
                            skull.setDisplayName(p.getDisplayName());
                            skull.setLore(lore);
                            head.setItemMeta(skull);
                            i.setItem(id, head);
                            id++;
                        }
                    }
                }
            }
            player.openInventory(i);
        }
        if (name.equalsIgnoreCase(ChatColor.BLUE + "Remove players")) {
            for (Player p : player.getWorld().getPlayers()) {
                if (p.getUniqueId() != player.getUniqueId()) {
                    if (CustomConfig.get().getString("data." + p.getUniqueId()) != null) {
                        for (String var : CustomConfig.get().getConfigurationSection("data." + p.getUniqueId()).getKeys(false)) {
                            System.out.print(var);
                            Location loc = new Location(Bukkit.getWorld(CustomConfig.get().getString("data." + p.getUniqueId() + "." + var + ".world")),
                                    CustomConfig.get().getDouble("data." + p.getUniqueId() + "." + var + ".x"),
                                    CustomConfig.get().getDouble("data." + p.getUniqueId() + "." + var + ".y"),
                                    CustomConfig.get().getDouble("data." + p.getUniqueId() + "." + var + ".z"));
                            Block tempBlock = loc.getBlock();
                            if (tempBlock.equals(block)) {
                                ItemStack head = new ItemStack(Material.PLAYER_HEAD, 1);
                                SkullMeta skull = (SkullMeta) head.getItemMeta();
                                skull.setOwningPlayer(Bukkit.getOfflinePlayer(p.getUniqueId()));
                                skull.setDisplayName(p.getDisplayName());
                                skull.setLore(lore);
                                head.setItemMeta(skull);
                                i.setItem(id, head);
                                id++;
                                break;
                            }
                        }
                    }
                }
            }
            player.openInventory(i);
        }
    }

    public boolean isNumber(String number) {
        if (number == null) {
            return false;
        }

        try {
            double d = Double.parseDouble(number);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
