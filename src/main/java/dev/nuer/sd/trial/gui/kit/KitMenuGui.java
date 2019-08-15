package dev.nuer.sd.trial.gui.kit;

import dev.nuer.sd.trial.SDGui;
import dev.nuer.sd.trial.enable.FileManager;
import dev.nuer.sd.trial.gui.AbstractGui;
import dev.nuer.sd.trial.utils.CalculatePermissions;
import dev.nuer.sd.trial.utils.ColorUtil;
import dev.nuer.sd.trial.utils.ItemBuilderUtil;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class KitMenuGui extends AbstractGui {
    Player player;
    YamlConfiguration config;

    /**
     * Constructor the create a new Gui
     */
    public KitMenuGui(Player player) {
        super(FileManager.get("kit_gui").getInt("size"), ColorUtil.colorize(FileManager.get("kit_gui").getString("name")));
        this.player = player;
        this.config = FileManager.get("kit_gui");
        for (int i = 0; i < config.getInt("size"); i++) {
            try {
                int fileID = i;
                setItemInSlot(config.getInt(fileID + ".slot"), buildItem(fileID), player1 -> {
                    if (config.getBoolean(fileID + ".open-gui.enabled")) {
                        switch (config.getString(fileID + ".open-gui.gui")) {
                            case "donor":
                                new KitSubPageGui(player, "donor").open(player);
                                break;
                            case "class":
                                new KitSubPageGui(player, "class").open(player);
                                break;
                            case "special":
                                new KitSubPageGui(player, "special").open(player);
                                break;
                        }
                    }
                });
            } catch (Exception e) {
                if (i == 1) {
                    e.printStackTrace();
                }
                //Do nothing, item doesn't exist
            }
        }
    }

    private ItemStack buildItem(int fileID) {
        ItemBuilderUtil ibu;
        if (config.getBoolean(fileID + ".head-item.enabled")) {
            ibu = new ItemBuilderUtil("skull_item", "3");
            SkullMeta meta = (SkullMeta) ibu.getItemMeta();
            meta.setOwner(player.getName());
            ibu.setItemMeta(meta);
            CalculatePermissions perms = new CalculatePermissions(player);
            ibu.addLore(config.getStringList(fileID + ".lore"));
            ibu.replaceLorePlaceholder("{donor}", perms.getDonor());
            ibu.replaceLorePlaceholder("{class}", perms.getSkill());
            ibu.replaceLorePlaceholder("{special}", perms.getSpecial());
        } else {
            ibu = new ItemBuilderUtil(config.getString(fileID + ".material"),
                    config.getString(fileID + ".data-value"));
            ibu.addLore(config.getStringList(fileID + ".lore"));
        }
        ibu.addName(ColorUtil.colorize(config.getString(fileID + ".name")), "{player}", player.getName());
        ibu.addEnchantments(config.getStringList(fileID + ".enchantments"));
        ibu.addItemFlags(config.getStringList(fileID + ".item-flags"));
        return ibu.getItem();
    }
}
