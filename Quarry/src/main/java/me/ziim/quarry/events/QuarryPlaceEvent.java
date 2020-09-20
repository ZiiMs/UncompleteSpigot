package me.ziim.quarry.events;

import me.ziim.quarry.Quarry;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.block.Hopper;
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
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class QuarryPlaceEvent implements Listener {

    private final Plugin plugin = Quarry.getPlugin(Quarry.class);
    public List<Location> blocks = new ArrayList<org.bukkit.Location>();

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        Block block1 = e.getBlock();

        ItemStack item = e.getItemInHand();
        Player player = e.getPlayer();
        ItemMeta meta = item.getItemMeta();

        if (meta.getDisplayName().equalsIgnoreCase(ChatColor.AQUA + "Quarry") && item.getType().equals(Material.HOPPER)) {
            Block chestBlock;
            if (player.getFacing().equals(BlockFace.NORTH)) {
                BlockFace topHopper = BlockFace.UP;
                chestBlock = block1.getRelative(topHopper);
            } else if (player.getFacing().equals(BlockFace.EAST)) {
                BlockFace topHopper = BlockFace.UP;
                chestBlock = block1.getRelative(topHopper);

            } else if (player.getFacing().equals(BlockFace.SOUTH)) {
                BlockFace topHopper = BlockFace.UP;
                chestBlock = block1.getRelative(topHopper);
            } else if (player.getFacing().equals(BlockFace.WEST)) {
                BlockFace topHopper = BlockFace.UP;
                chestBlock = block1.getRelative(topHopper);
            } else {
                return;
            }
            chestBlock.setType(Material.CHEST);
            Chest chest = (Chest) chestBlock.getState();
            if (chestBlock.getType().equals(Material.CHEST)) {
//                Chest c = (Chest) chestBlock.getState();

                chest.setCustomName(ChatColor.AQUA + "Quarry Chest");
                chest.update();
            }
            StartQuarry(block1, player);
        }
    }

    @EventHandler
    public void InteractEvent(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        Action action = e.getAction();
        Block block = e.getClickedBlock();
        if (action.equals(Action.RIGHT_CLICK_BLOCK) && block.getType().equals(Material.HOPPER)) {
            Hopper hopper = (Hopper) block.getState();
            if (hopper.getCustomName() != null) {
                if (hopper.getCustomName().equals(ChatColor.AQUA + "Quarry")) {
                    StartQuarry(block, player);
                }
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Player player = (Player) e.getPlayer();
        Block block = (Block) e.getBlock();

        player.sendMessage(block.getType().toString() + " | Test123");
        if (block.getType().equals(Material.CHEST)) {
            Chest chest = (Chest) block.getState();
            if (chest.getCustomName() != null) {
                if (chest.getCustomName().equals(ChatColor.AQUA + "Quarry Chest")) {
                    e.setCancelled(true);
                    player.sendMessage("You cant break that, please break the hopper block instead.");
                }
            }
        }
        if (block.getType().equals(Material.HOPPER)) {
            Hopper hopper = (Hopper) block.getState();
            if (hopper.getCustomName() != null) {
                if (hopper.getCustomName().equals(ChatColor.AQUA + "Quarry")) {
                    Block chest = block.getRelative(BlockFace.UP);
                    chest.setType(Material.AIR);
                    for (Entity ent : block.getWorld().getNearbyEntities(block.getLocation(), 2, 2, 2)) {
                        System.out.print(ent.getType());
                        if (ent.getType().equals(EntityType.ARMOR_STAND)) {
                            player.sendMessage("Removing armor stand");
                            ArmorStand armorStand = (ArmorStand) ent;
                            armorStand.remove();
                            ent.remove();
                        }
                    }
                }
            }
        }
    }

    public void StartQuarry(Block block, Player player) {
        Chunk chunk = block.getChunk();
        Block chestBlock = block.getRelative(BlockFace.UP);
        Chest chest = (Chest) chestBlock.getState();
        int count = (int) blocks.stream().filter(location -> location.equals(block.getLocation())).count();
        if (count >= 1)
            return;

        blocks.add(block.getLocation());

        Location loc = new Location(block.getWorld(), block.getLocation().getX(), block.getLocation().getY() + 1, block.getLocation().getZ());

        for (Entity ent : block.getWorld().getNearbyEntities(block.getLocation(), 2, 2, 2)) {
            if (ent.getType().equals(EntityType.ARMOR_STAND)) {
                player.sendMessage("Removing armor stand");
                ArmorStand armorStand = (ArmorStand) ent;
                armorStand.remove();
                ent.remove();
            }
        }

        Entity textStand = loc.getWorld().spawnEntity(loc.add(0.5,0.2,0.5), EntityType.ARMOR_STAND);
        textStand.setInvulnerable(true);
        textStand.setCustomNameVisible(true);
        textStand.setSilent(true);
        ArmorStand astand = (ArmorStand) textStand;
        astand.setVisible(false);
        astand.setGravity(false);
        astand.setCollidable(false);
        astand.setCanPickupItems(false);
        astand.setSmall(true);
        final int maxY = block.getY() - 1;
        this.plugin.getServer().getScheduler().scheduleSyncRepeatingTask(this.plugin, new Runnable() {
            private int x = 0;
            private int y = maxY;
            private int z = 0;


            @Override
            public void run() {
                if (block.getType().isAir()) {
                    textStand.remove();
                }
                if (textStand.getType().equals(EntityType.ARMOR_STAND)) {
                    int i = 0;
                    for (ItemStack item : chest.getInventory().getContents()) {
                        if (item == null) continue;
                        i++;
                    }
                    textStand.setCustomName("Quarry: " + i + "/" + chest.getInventory().getSize() + " slots used");
                }
                if (chestBlock.isBlockPowered() && chest.getInventory().firstEmpty() != -1 && !block.getType().isAir()) {
                    int x2 = x;
                    int y2 = y;
                    int z2 = z;
                    if (y < 0) {
                        return;
                    }
                    if (x != 15) {
                        x++;
                    } else if (z != 15) {
                        z++;
                        x = 0;
                    } else {
                        y--;
                        z = 0;
                        x = 0;
                    }

                    Block foundBlock = chunk.getBlock(x, y, z);
                    if (foundBlock.getType().isAir() && !foundBlock.getType().equals(Material.WATER) && !foundBlock.getType().isSolid()) {
                        for (x2 = x; x2 <= 16; x2++) {
                            foundBlock = chunk.getBlock(x2, y2, z2);
                            if (!foundBlock.getType().isAir() && !foundBlock.getType().equals(Material.WATER)) {
                                x = x2;
                                y = y2;
                                z = z2;
                                System.out.print("Breaking! " + foundBlock.getType());
                                break;
                            }

                            if (x2 == 15) {
                                x2 = 0;

                                if (z2 >= 15) {
                                    y2--;
                                    z2 = 0;
                                }
                                z2++;
                            }
                        }
                    }
                    //System.out.print(foundBlock.getType())
                    if (!foundBlock.getType().equals(Material.AIR) && !foundBlock.getType().equals(Material.BEDROCK) && !foundBlock.getType().equals(Material.CAVE_AIR) && !foundBlock.getType().equals(Material.VOID_AIR)) {
                        Collection<ItemStack> drops = foundBlock.getDrops(new ItemStack(Material.DIAMOND_PICKAXE));
                        drops.forEach(itemStack -> {
                            chest.getBlockInventory().addItem(itemStack);
                        });
                        foundBlock.setType(Material.AIR);
                    }
                }
            }
        }, 0, 5);
    }
}
