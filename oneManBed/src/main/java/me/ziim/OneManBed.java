package me.ziim;

import org.bukkit.plugin.java.JavaPlugin;

public final class OneManBed extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info(this.getName() + " by ZiiM started!");
        this.getServer().getPluginManager().registerEvents(new Events(), this);
    }

    @Override
    public void onDisable() {
        getLogger().info(this.getName() + " by ZiiM stopped!");
        // Plugin shutdown logic
    }
}
