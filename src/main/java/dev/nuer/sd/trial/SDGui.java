package dev.nuer.sd.trial;

import com.earth2me.essentials.Essentials;
import dev.nuer.sd.trial.enable.FileManager;
import dev.nuer.sd.trial.enable.SetupManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class SDGui extends JavaPlugin {
    public static SDGui instance;
    public static Logger log;
    public static Essentials ess;

    @Override
    public void onEnable() {
        instance = this;
        log = getLogger();
        ess = (Essentials) Bukkit.getPluginManager().getPlugin("Essentials");
        SetupManager.setupFiles(new FileManager(this));
        SetupManager.registerCommands(this);
        SetupManager.registerEvents(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}