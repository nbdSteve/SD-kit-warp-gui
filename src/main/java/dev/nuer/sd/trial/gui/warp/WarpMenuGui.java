package dev.nuer.sd.trial.gui.warp;

import dev.nuer.sd.trial.enable.FileManager;
import dev.nuer.sd.trial.gui.AbstractGui;
import dev.nuer.sd.trial.utils.ColorUtil;
import dev.nuer.sd.trial.utils.ItemBuilderUtil;
import dev.nuer.sd.trial.utils.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class WarpMenuGui extends AbstractGui {
    Player player;
    YamlConfiguration config;

    /**
     * Constructor the create a new Gui
     */
    public WarpMenuGui(Player player) {
        super(FileManager.get("warp_gui").getInt("size"), ColorUtil.colorize(FileManager.get("warp_gui").getString("name")));
        this.player = player;
        this.config = FileManager.get("warp_gui");
        for (int i = 0; i < config.getInt("size"); i++) {
            try {
                int fileID = i;
                if (config.getBoolean(fileID + ".warp-item.enabled") &&
                        !player.hasPermission("essentials.warps." + config.getString(fileID + ".warp-item.warp-name"))) {
                } else {
                    setItemInSlot(config.getInt(fileID + ".slot"), buildItem(fileID), player1 -> {
                        if (config.getBoolean(fileID + ".warp-item.enabled")) {
                            if (player.hasPermission("essentials.warps." + config.getString(fileID + ".warp-item.warp-name"))) {
                                player.closeInventory();
                                Bukkit.dispatchCommand(player, "essentials:warp " + config.getString(fileID + ".warp-item.warp-name"));
                            } else {
                                player.closeInventory();
                                MessageUtil.message("messages", "permission-debug", player,
                                        "{node}", "essentials.warps." + config.getString(fileID + ".warp-item.warp-name"));
                            }
                        }
                    });
                }
            } catch (Exception e) {
                //Do nothing, item doesn't exist
            }
        }
    }

    public ItemStack buildItem(int fileID) {
        ItemBuilderUtil ibu = new ItemBuilderUtil(config.getString(fileID + ".material"),
                config.getString(fileID + ".data-value"));
        ibu.addLore(config.getStringList(fileID + ".lore"));
        if (config.getBoolean(fileID + ".warp-item.enabled")) {
            ibu.replaceLorePlaceholder("{status}", getStatus(config.getString(fileID + ".warp-item.warp-name")));
            ibu.addName(ColorUtil.colorize(config.getString(fileID + ".name")), "{warp-name}", config.getString(fileID + ".warp-item.warp-name"));
        } else {
            ibu.addName(ColorUtil.colorize(config.getString(fileID + ".name")), "debug", "debug");
        }
        ibu.addEnchantments(config.getStringList(fileID + ".enchantments"));
        ibu.addItemFlags(config.getStringList(fileID + ".item-flags"));
        return ibu.getItem();
    }

    public String getStatus(String warpName) {
        if (player.hasPermission("essentials.warps." + warpName)) return config.getString("status.unlocked");
        return config.getString("status.locked");
    }
}
