package dev.nuer.sd.trial.gui.kit;

import dev.nuer.sd.trial.enable.FileManager;
import dev.nuer.sd.trial.gui.AbstractGui;
import dev.nuer.sd.trial.utils.ColorUtil;
import dev.nuer.sd.trial.utils.ItemBuilderUtil;
import dev.nuer.sd.trial.utils.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class KitSubPageGui extends AbstractGui {
    Player player;
    YamlConfiguration config;

    /**
     * Constructor the create a new Gui
     */
    public KitSubPageGui(Player player, String type) {
        super(FileManager.get("kit_gui_" + type).getInt("size"), ColorUtil.colorize(FileManager.get("kit_gui_" + type).getString("name")));
        this.player = player;
        this.config = FileManager.get("kit_gui_" + type);
        for (int i = 0; i < config.getInt("size"); i++) {
            try {
                int fileID = i;
                if (config.getBoolean(fileID + ".kit-item.enabled")
                        && !player.hasPermission("essentials.kits." + config.getString(fileID + ".kit-item.kit-name"))) {

                } else {
                    setItemInSlot(config.getInt(fileID + ".slot"), buildItem(fileID), player1 -> {
                        if (config.getBoolean(fileID + ".kit-item.enabled")) {
                            if (player.hasPermission("essentials.kits." + config.getString(fileID + ".kit-item.kit-name"))) {
                                player.closeInventory();
                                Bukkit.dispatchCommand(player, "essentials:kit " + config.getString(fileID + ".kit-item.kit-name"));
                            } else {
                                player.closeInventory();
                                MessageUtil.message("messages", "permission-debug", player,
                                        "{node}", "essentials.kits." + config.getString(fileID + ".kit-item.kit-name"));
                            }
                        } else if (config.getBoolean(fileID + ".back-button.enabled")) {
                            new KitMenuGui(player).open(player);
                        }
                    });
                }
            } catch (Exception e) {
                if (i == 2) {
                    e.printStackTrace();
                }
                //Do nothing, item doesn't exist
            }
        }
    }

    private ItemStack buildItem(int fileID) {
        ItemBuilderUtil ibu = new ItemBuilderUtil(config.getString(fileID + ".material"),
                config.getString(fileID + ".data-value"));
        ibu.addLore(config.getStringList(fileID + ".lore"));
        if (config.getBoolean(fileID + ".kit-item.enabled")) {
            ibu.replaceLorePlaceholder("{status}", getStatus(config.getString(fileID + ".kit-item.kit-name")));
            ibu.addName(ColorUtil.colorize(config.getString(fileID + ".name")), "{kit-name}", config.getString(fileID + ".kit-item.kit-name"));
        } else {
            ibu.addName(ColorUtil.colorize(config.getString(fileID + ".name")), "debug", "debug");
        }
        ibu.addEnchantments(config.getStringList(fileID + ".enchantments"));
        ibu.addItemFlags(config.getStringList(fileID + ".item-flags"));
        return ibu.getItem();
    }

    public String getStatus(String kitName) {
        if (player.hasPermission("essentials.kits." + kitName)) return config.getString("status.unlocked");
        return config.getString("status.locked");
    }
}
