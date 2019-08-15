package dev.nuer.sd.trial.utils;

import dev.nuer.sd.trial.enable.FileManager;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachmentInfo;

public class CalculatePermissions {
    private Player player;
    private int donor;
    private int skill;
    private int special;

    public CalculatePermissions(Player player) {
        this.player = player;
        for (PermissionAttachmentInfo pa : player.getEffectivePermissions()) {
            String node = pa.getPermission();
            for (String specialKit : FileManager.get("kit_gui").getStringList("kits.special")) {
                if (node.equalsIgnoreCase("essentials.kits." + specialKit)) special++;
                break;
            }
            for (String donorKit : FileManager.get("kit_gui").getStringList("kits.donor")) {
                if (node.equalsIgnoreCase("essentials.kits." + donorKit)) donor++;
                break;
            }
            for (String classKit : FileManager.get("kit_gui").getStringList("kits.class")) {
                if (node.equalsIgnoreCase("essentials.kits." + classKit)) skill++;
                break;
            }
        }
    }

    public Player getPlayer() {
        return player;
    }

    public String getDonor() {
        return String.valueOf(donor);
    }

    public String getSkill() {
        return String.valueOf(skill);
    }

    public String getSpecial() {
        return String.valueOf(special);
    }
}