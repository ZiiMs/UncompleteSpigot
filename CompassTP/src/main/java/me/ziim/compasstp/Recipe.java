package me.ziim.compasstp;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;

public class Recipe implements Listener {

    private final Plugin plugin = CompassTP.getPlugin(CompassTP.class);

    public void createRecipe() {
        ItemStack tpbeacon = new ItemStack(Material.LECTERN, 1);
        ItemMeta meta = tpbeacon.getItemMeta();
        meta.setDisplayName(ChatColor.AQUA + "Teleport beacon");
        ArrayList<String> lore = new ArrayList<String>();
        lore.add(ChatColor.WHITE + "A teleport beacon");
        lore.add(ChatColor.WHITE + "Using a compass will allow you to teleport to it.");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        tpbeacon.setItemMeta(meta);

        NamespacedKey key = new NamespacedKey(this.plugin, "tpbeacon");

        ShapedRecipe r = new ShapedRecipe(key, tpbeacon);

        r.shape(" D ", "ILI", "   ");


        r.setIngredient('L', Material.LECTERN);
        r.setIngredient('D', Material.DIAMOND);
        r.setIngredient('I', Material.IRON_BLOCK);

        plugin.getServer().addRecipe(r);
    }
}
