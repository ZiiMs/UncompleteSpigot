package easyscoreboard;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class ScoreBoard extends JavaPlugin implements Listener {
    Plugin plugin = this;
    public sbManager SBManager;
    @Override
    public void onEnable() {
        getLogger().info(plugin.getName() + " created by ZiiM");
        SBManager = new sbManager();
        this.getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @EventHandler
    public void playerJoin(PlayerJoinEvent e) {
        SBManager.onJoin(e.getPlayer());
        System.out.print("sbManager send?");
    }
}
