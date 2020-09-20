package me.ziim.compasstp.Events;

import me.ziim.compasstp.CustomConfig;
import me.ziim.compasstp.CustomInventory;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.javatuples.Pair;

import java.util.*;

public class TPBeacon implements Listener, CommandExecutor {


    public static Map<Block, Pair<UUID, String>> beacons = new HashMap<>();


    @EventHandler
    public void placeBeacon(BlockPlaceEvent e) {
        Player player = e.getPlayer();
        Block block = e.getBlock();
        ItemStack item = e.getItemInHand();
        ItemMeta meta = item.getItemMeta();
        Location loc = block.getLocation();

        if (!block.getType().equals(Material.LECTERN) || !meta.getDisplayName().equals(ChatColor.AQUA + "Teleport beacon")) {
            return;
        }


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
        player.sendMessage(ChatColor.YELLOW + "You have placed a beacon!\n You can rename it by looking at it and typing /beacon [name]");

        CustomConfig.get().set("data." + player.getUniqueId() + "." + var + ".x", (int) loc.getX());
        CustomConfig.get().set("data." + player.getUniqueId() + "." + var + ".y", (int) loc.getY());
        CustomConfig.get().set("data." + player.getUniqueId() + "." + var + ".z", (int) loc.getZ());
        CustomConfig.get().set("data." + player.getUniqueId() + "." + var + ".world", loc.getWorld().getName());
        CustomConfig.get().set("data." + player.getUniqueId() + "." + var + ".name", var);
        CustomConfig.save();
        if (meta.getDisplayName().equals(ChatColor.AQUA + "Teleport beacon")) {
            beacons.put(block, new Pair(player.getUniqueId(), var));
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Player player = e.getPlayer();
        Block block = e.getBlock();

        Boolean correctBlock = false;

        if (block.getType().equals(Material.LECTERN)) {
            String id = null;
            for (Player p : player.getWorld().getPlayers()) {
                for (String b : CustomConfig.get().getConfigurationSection("data." + p.getUniqueId()).getKeys(false)) {
                    Location location = new Location(Bukkit.getWorld(CustomConfig.get().getString("data." + p.getUniqueId() + "." + b + ".world")),
                            CustomConfig.get().getDouble("data." + p.getUniqueId() + "." + b + ".x"),
                            CustomConfig.get().getDouble("data." + p.getUniqueId() + "." + b + ".y"),
                            CustomConfig.get().getDouble("data." + p.getUniqueId() + "." + b + ".z"));
                    Block blck = location.getBlock();

                    if (block.equals(blck)) {
                        System.out.print("Found blocxk!!");
                        correctBlock = true;
                        id = b;
                        break;
                    }
                }
            }
            if (correctBlock) {
                for (Entity ent : block.getWorld().getNearbyEntities(block.getLocation(), 1, 1, 1)) {
                    if (ent.getType().equals(EntityType.ARMOR_STAND)) {
                        player.sendMessage("Removing armor stand");
                        ArmorStand armorStand = (ArmorStand) ent;
                        armorStand.remove();
                        ent.remove();
                    }
                }
                CustomConfig.get().set("data." + player.getUniqueId() + "." + id, null);
                CustomConfig.save();
                beacons.remove(block);
            }
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        NamespacedKey key = NamespacedKey.minecraft("tpbeacon");
        player.discoverRecipe(key);
        if (CustomConfig.get().getString("data." + player.getUniqueId()) != null) {
            CustomConfig.get().getConfigurationSection("data." + player.getUniqueId()).getKeys(false).forEach(b -> {
                Location location = new Location(Bukkit.getWorld(CustomConfig.get().getString("data." + player.getUniqueId() + "." + b + ".world")),
                        CustomConfig.get().getDouble("data." + player.getUniqueId() + "." + b + ".x"),
                        CustomConfig.get().getDouble("data." + player.getUniqueId() + "." + b + ".y"),
                        CustomConfig.get().getDouble("data." + player.getUniqueId() + "." + b + ".z"));
                Block blck = location.getBlock();
                String name = CustomConfig.get().getString("data." + player.getUniqueId() + "." + b + ".name");
                beacons.put(blck, new Pair(player.getUniqueId(), b));
                if (!name.equals(b)) {
                    ArmorStand astand = (ArmorStand) blck.getWorld().spawnEntity(blck.getLocation().add(0.5D, -0.8D, 0.5D), EntityType.ARMOR_STAND);

                    astand.setArms(false);
                    astand.setGravity(false);
                    astand.setSmall(false);
                    astand.setVisible(false);
                    astand.setCustomName(name);
                    astand.setCustomNameVisible(true);
                    astand.setCanPickupItems(false);
                    astand.setCollidable(false);
                }
            });
            System.out.print("Beacon is now: " + beacons.entrySet().size() + " | " + beacons.size());
        }
    }

    @EventHandler
    public void BlockInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        Action action = e.getAction();
        Block block = e.getClickedBlock();

        Boolean foundBlock = false;
        String var = null;

        if (action.equals(Action.RIGHT_CLICK_BLOCK) && block.getType().equals(Material.LECTERN)) {
            for (String keys : CustomConfig.get().getConfigurationSection("data." + player.getUniqueId()).getKeys(false)) {
                Location location = new Location(Bukkit.getWorld(CustomConfig.get().getString("data." + player.getUniqueId() + "." + keys + ".world")),
                        CustomConfig.get().getDouble("data." + player.getUniqueId() + "." + keys + ".x"),
                        CustomConfig.get().getDouble("data." + player.getUniqueId() + "." + keys + ".y"),
                        CustomConfig.get().getDouble("data." + player.getUniqueId() + "." + keys + ".z"));
                Block compBlock = location.getBlock();

                if (block.equals(compBlock)) {
                    var = keys;
                    foundBlock = true;
                    break;
                }
            }
            if (foundBlock) {
                CustomInventory i = new CustomInventory();
                i.ConfigInventory(player, var);
            }

        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        List<Block> keys = new ArrayList<>();
        for (Map.Entry m : beacons.entrySet()) {
            if (m.getValue().equals(player.getUniqueId())) {
                keys.add((Block) m.getKey());
                System.out.print(keys.size());
            }
        }
        keys.forEach(block -> {
            beacons.remove(block);
            System.out.print("Removed key!");
        });
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }
        if (args[0].isEmpty() || args[0].equals(null)) {
            return false;
        }
        Player player = (Player) sender;
        Block block = player.getTargetBlock(null, 4);
        Boolean correctBlock = false;

        if (block.getType().equals(Material.LECTERN)) {
            String id = null;
            for (String b : CustomConfig.get().getConfigurationSection("data." + player.getUniqueId()).getKeys(false)) {
                Location location = new Location(Bukkit.getWorld(CustomConfig.get().getString("data." + player.getUniqueId() + "." + b + ".world")),
                        CustomConfig.get().getDouble("data." + player.getUniqueId() + "." + b + ".x"),
                        CustomConfig.get().getDouble("data." + player.getUniqueId() + "." + b + ".y"),
                        CustomConfig.get().getDouble("data." + player.getUniqueId() + "." + b + ".z"));
                Block blck = location.getBlock();

                if (block.equals(blck)) {
                    System.out.print("Found blocxk!!");
                    correctBlock = true;
                    id = b;
                    break;
                }
            }
            if (correctBlock) {
                String name = String.join(" ", Arrays.copyOfRange(args, 0, args.length));
                System.out.print("Correct blocks");
                player.sendMessage(name);

                CustomConfig.get().set("data." + player.getUniqueId() + "." + id + ".name", name); //Save player beacon name
                CustomConfig.save();
                for (Entity e : block.getWorld().getNearbyEntities(block.getLocation(), 1, 1, 1)) {
                    if (e.getType().equals(EntityType.ARMOR_STAND)) {
                        player.sendMessage("Removing armor stand");
                        ArmorStand armorStand = (ArmorStand) e;
                        armorStand.remove();
                        e.remove();
                    }
                }
                ArmorStand astand = (ArmorStand) block.getWorld().spawnEntity(block.getLocation().add(0.5D, -0.8D, 0.5D), EntityType.ARMOR_STAND);

                astand.setArms(false);
                astand.setGravity(false);
                astand.setSmall(false);
                astand.setVisible(false);
                astand.setCustomName(name);
                astand.setCustomNameVisible(true);
                astand.setCanPickupItems(false);
                astand.setCollidable(false);
            }
        }
        return true;
    }
}
