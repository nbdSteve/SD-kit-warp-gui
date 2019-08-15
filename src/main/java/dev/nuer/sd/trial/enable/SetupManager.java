package dev.nuer.sd.trial.enable;

import dev.nuer.sd.trial.SDGui;
import dev.nuer.sd.trial.cmd.KitCmd;
import dev.nuer.sd.trial.cmd.SDGuiCmd;
import dev.nuer.sd.trial.cmd.WarpCmd;
import dev.nuer.sd.trial.gui.listener.GuiClickListener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import java.io.File;

/**
 * Class that handles setting up the plugin on start
 */
public class SetupManager {

    /**
     * Loads the files into the file manager
     *
     * @param fileManager FileManager, the plugins file manager
     */
    public static void setupFiles(FileManager fileManager) {
        //General files
        fileManager.add("messages", "messages.yml");
        //Warp files
        fileManager.add("warp_gui", "warp-gui.yml");
        //Kit files
        fileManager.add("kit_gui", "kit-gui.yml");
        fileManager.add("kit_gui_donor", "kit-gui-config" + File.separator + "donor-gui.yml");
        fileManager.add("kit_gui_class", "kit-gui-config" + File.separator + "class-gui.yml");
        fileManager.add("kit_gui_special", "kit-gui-config" + File.separator + "special-gui.yml");
    }

    public static void registerCommands(SDGui instance) {
        instance.getCommand("kit").setExecutor(new KitCmd());
        instance.getCommand("warp").setExecutor(new WarpCmd());
        instance.getCommand("sd-gui").setExecutor(new SDGuiCmd());
    }

    /**
     * Register all of the events for the plugin
     *
     * @param instance Plugin, the main plugin instance
     */
    public static void registerEvents(Plugin instance) {
        PluginManager pm = instance.getServer().getPluginManager();
        pm.registerEvents(new GuiClickListener(), instance);
    }
}