package me.ziim.quarry;

import me.ziim.quarry.events.QuarryPlaceEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class Quarry extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("Quarry by ZiiM started!");
        this.getServer().getPluginManager().registerEvents(new RecipeEvents(), this);
        this.getServer().getPluginManager().registerEvents(new QuarryPlaceEvent(), this);
        RecipeEvents recipe = new RecipeEvents();
        recipe.createRecipe();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("Quarry by ZiiM stopped!");
    }
}
