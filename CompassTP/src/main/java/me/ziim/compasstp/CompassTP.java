package me.ziim.compasstp;

import me.ziim.compasstp.Events.EventClass;
import me.ziim.compasstp.Events.TPBeacon;
import org.bukkit.plugin.java.JavaPlugin;

public final class CompassTP extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("Compass TP by ZiiM started!");

        getConfig().options().copyDefaults();
        saveDefaultConfig();

        CustomConfig.setup();
        CustomConfig.save();

        this.getServer().getPluginManager().registerEvents(new ItemEvent(), this);
        this.getServer().getPluginManager().registerEvents(new CustomInventory(), this);
        this.getServer().getPluginManager().registerEvents(new EventClass(), this);
        this.getServer().getPluginManager().registerEvents(new TPBeacon(), this);

        this.getCommand("beacon").setExecutor(new TPBeacon());
        Recipe recipe = new Recipe();
        recipe.createRecipe();
    }

    @Override
    public void onDisable() {
        getLogger().info("Compass TP by ZiiM stopped!");
        // Plugin shutdown logic
    }

}



