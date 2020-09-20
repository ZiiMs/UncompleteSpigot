package me.ziim.mineenhance;

import me.ziim.mineenhance.Enchants.*;
import org.bukkit.plugin.java.JavaPlugin;

public final class MineEnhance extends JavaPlugin {
    private int count;

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("MineEnhance by ZiiM started!");
        CustomEnchants.register();
        this.getServer().getPluginManager().registerEvents(new EnchantEvent(), this);
        this.getServer().getPluginManager().registerEvents(new AreaMiner(), this);
        this.getServer().getPluginManager().registerEvents(new VeinMiner(), this);
        this.getServer().getPluginManager().registerEvents(new Telepathy(), this);
        this.getServer().getPluginManager().registerEvents(new MakeRain(), this);
        this.getServer().getPluginManager().registerEvents(new spawnerTouch(), this);
    }

    @Override
    public void onDisable() {
        getLogger().info("MineEnhance by ZiiM stopped!");
        // Plugin shutdown logic
    }
}
