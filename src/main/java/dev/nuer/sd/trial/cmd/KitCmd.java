package dev.nuer.sd.trial.cmd;

import dev.nuer.sd.trial.SDGui;
import dev.nuer.sd.trial.gui.kit.KitMenuGui;
import dev.nuer.sd.trial.utils.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KitCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = null;
        if (sender instanceof Player) {
            player = (Player) sender;
        }
        if (args.length == 0) {
            if (player != null) {
                new KitMenuGui(player).open(player);
            } else {
                SDGui.log.info("Only players can view the kits Gui.");
            }
        } else if (args.length == 1) {
            try {
                SDGui.ess.getKits().getKit(args[0]);
            } catch (Exception e) {
                if (player != null) {
                    MessageUtil.message("messages", "command-debug", player,
                            "{reason}", "The kit you entered does not exist");
                } else {
                    SDGui.log.info("The kit you entered does not exist.");
                }
                return true;
            }
            if (player != null) {
                if (player.hasPermission("essentials.kits." + args[0])) {
                    Bukkit.dispatchCommand(player, "essentials:kit " + args[0]);
                } else {
                    MessageUtil.message("messages", "permission-debug", player,
                            "{node}", "essentials.kits." + args[0]);
                }
            } else {
                SDGui.log.info("Only players can receive kits.");
            }
        } else if (args.length == 2) {
            try {
                SDGui.ess.getKits().getKit(args[0]);
            } catch (Exception e) {
                if (player != null) {
                    MessageUtil.message("messages", "command-debug", player,
                            "{reason}", "The kit you entered does not exist");
                } else {
                    SDGui.log.info("The kit you entered does not exist.");
                }
                return true;
            }
            if (player != null) {
                if (player.hasPermission("essentials.kit.others")) {
                    Bukkit.dispatchCommand(player, "essentials:kit " + args[0] + " " + args[1]);
                } else {
                    MessageUtil.message("messages", "permission-debug", player,
                            "{node}", "essentials.kit.others");
                }
            } else {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "essentials:kit " + args[0] + " " + args[1]);
                SDGui.log.info("You have given kit: " + args[0] + " to the player: " + args[1]);
            }
        } else {
            if (player != null) {
                MessageUtil.message("messages", "command-debug", player,
                        "{reason}", "Invalid command arguments");
            } else {
                SDGui.log.info("Invalid command arguments.");
            }
        }
        return true;
    }
}
