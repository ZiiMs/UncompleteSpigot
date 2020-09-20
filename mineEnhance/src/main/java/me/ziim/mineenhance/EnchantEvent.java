package me.ziim.mineenhance;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.awt.*;
import java.util.*;
import java.util.List;

public class EnchantEvent implements Listener {

    @EventHandler
    public void onEnchant(EnchantItemEvent event){
        ItemStack item = event.getItem();
        Player player = event.getEnchanter();
        System.out.print(event.getItem().getType());
        if (event.getEnchantsToAdd().containsKey(Enchantment.DURABILITY)){
            Random random = new Random();
            int rnd = random.nextInt(99) + 1;
            boolean chance = rnd >= 70;
            System.out.print(chance + " | " + rnd);
            if (chance) {
                event.getEnchantsToAdd().put(CustomEnchants.TELEPATHY, 1);
                ItemMeta meta = item.getItemMeta();
                List<String> lore = new ArrayList<String>();
                assert meta != null;
                if (meta.hasLore())
                    lore.addAll(Objects.requireNonNull(meta.getLore()));
                lore.add(ChatColor.GRAY + "Telepathy I");
                meta.setLore(lore);
                item.setItemMeta(meta);
            }
        }
        if (event.getEnchantsToAdd().containsKey(Enchantment.DIG_SPEED)){
            Random random = new Random();
            int rnd = random.nextInt(99) + 1;
            boolean chance = rnd <= 10;
            boolean chance2 = rnd >= 44;
            System.out.print(chance + " | " + rnd);
            if (chance) {
                int lvl = random.nextInt(4);
                if (lvl == 0) lvl = 1;
                event.getEnchantsToAdd().put(CustomEnchants.AREAMINE, lvl);
                ItemMeta meta = item.getItemMeta();
                List<String> lore = new ArrayList<String>();

                assert meta != null;
                if (meta.hasLore())
                    lore.addAll(Objects.requireNonNull(meta.getLore()));
                switch (lvl) {
                    case 1: {
                        lore.add(ChatColor.GRAY + "Areaminer I");
                        break;
                    }
                    case 2: {
                        lore.add(ChatColor.GRAY + "Areaminer II");
                        break;
                    }
                    case 3: {
                        lore.add(ChatColor.GRAY + "Areaminer III");
                        break;
                    }
                }
                meta.setLore(lore);
                item.setItemMeta(meta);
            }
            if (chance2) {
                event.getEnchantsToAdd().put(CustomEnchants.VEINMINER, 1);
                ItemMeta meta = item.getItemMeta();
                List<String> lore = new ArrayList<String>();
                lore.forEach(s -> {

                });
                assert meta != null;
                if (meta.hasLore())
                    lore.addAll(Objects.requireNonNull(meta.getLore()));
                lore.add(ChatColor.GRAY + "Veinminer I");
                meta.setLore(lore);
                item.setItemMeta(meta);
            }
        }
        if (event.getEnchantsToAdd().containsKey(Enchantment.LOOT_BONUS_BLOCKS)){
            Random random = new Random();
            int rnd = random.nextInt(99) + 1;
            boolean chance = rnd <= 10;
            System.out.print(chance + " | " + rnd);
            if (chance) {
                event.getEnchantsToAdd().put(CustomEnchants.MAKERAIN, 1);
                ItemMeta meta = item.getItemMeta();
                List<String> lore = new ArrayList<String>();

                assert meta != null;
                if (meta.hasLore())
                    lore.addAll(Objects.requireNonNull(meta.getLore()));
                lore.add(ChatColor.GRAY + "Makerain I");
                meta.setLore(lore);
                item.setItemMeta(meta);
            }
        }
        if (event.getEnchantsToAdd().containsKey(Enchantment.SILK_TOUCH)){
            Random random = new Random();
            int rnd = random.nextInt(99) + 1;
            boolean chance = rnd <= 10;
            if (chance) {
                event.getEnchantsToAdd().clear();
                event.getEnchantsToAdd().put(CustomEnchants.SPAWNERTOUCH, 1);
                ItemMeta meta = item.getItemMeta();
                List<String> lore = new ArrayList<String>();
                assert meta != null;
                if (meta.hasLore())
                    lore.addAll(Objects.requireNonNull(meta.getLore()));
                lore.add(ChatColor.GRAY + "Spawner Touch I");
                meta.setLore(lore);
                item.setItemMeta(meta);
            }
        }
    }
}
