package me.ziim.quarry;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;

public class RecipeEvents implements Listener {

    private final Plugin plugin = Quarry.getPlugin(Quarry.class);
    private NamespacedKey key = new NamespacedKey(this.plugin, "quarry");

    public void createRecipe() {
        ItemStack quarry = new ItemStack(Material.HOPPER, 1);
        ItemMeta meta = quarry.getItemMeta();
        meta.setDisplayName(ChatColor.AQUA + "Quarry");
        ArrayList<String> lore = new ArrayList<String>();
        lore.add(ChatColor.WHITE + "A quarry");
        lore.add(ChatColor.WHITE + "It will mine out the current chunk!");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        quarry.setItemMeta(meta);



        ShapedRecipe r = new ShapedRecipe(key, quarry);

        r.shape("III", "IHI", "SCS");

        r.setIngredient('I', Material.IRON_BLOCK);
        r.setIngredient('H', Material.HOPPER);
        r.setIngredient('S', Material.STONE);
        r.setIngredient('C', Material.CHEST);

        plugin.getServer().addRecipe(r);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();

        player.discoverRecipe(key);
    }
}
