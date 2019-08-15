package dev.nuer.sd.trial.cmd;

import dev.nuer.sd.trial.SDGui;
import dev.nuer.sd.trial.gui.warp.WarpMenuGui;
import dev.nuer.sd.trial.utils.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WarpCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = null;
        if (sender instanceof Player) {
            player = (Player) sender;
        }
        if (args.length == 0) {
            if (player != null) {
                new WarpMenuGui(player).open(player);
            } else {
                SDGui.log.info("Only players can view the warps Gui.");
            }
        } else if (args.length == 1) {
            try {
                SDGui.ess.getWarps().getWarp(args[0]);
            } catch (Exception e) {
                if (player != null) {
                    MessageUtil.message("messages", "command-debug", player,
                            "{reason}", "The warp you entered does not exist");
                } else {
                    SDGui.log.info("The warp you entered does not exist.");
                }
                return true;
            }
            if (player != null) {
                if (player.hasPermission("essentials.warps." + args[0])) {
                    Bukkit.dispatchCommand(player, "essentials:warp " + args[0]);
                } else {
                    MessageUtil.message("messages", "permission-debug", player,
                            "{node}", "essentials.warps." + args[0]);
                }
            } else {
                SDGui.log.info("Only players can use warps.");
            }
        } else if (args.length == 2) {
            try {
                SDGui.ess.getWarps().getWarp(args[0]);
            } catch (Exception e) {
                if (player != null) {
                    MessageUtil.message("messages", "command-debug", player,
                            "{reason}", "The warp you entered does not exist");
                } else {
                    SDGui.log.info("The warp you entered does not exist.");
                }
                return true;
            }
            if (player != null) {
                if (player.hasPermission("essentials.warp.otherplayers")) {
                    Bukkit.dispatchCommand(player, "essentials:warp " + args[0] + " " + args[1]);
                } else {
                    MessageUtil.message("messages", "permission-debug", player,
                            "{node}", "essentials.warp.otherplayers");
                }
            } else {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "essentials:warp " + args[0] + " " + args[1]);
                SDGui.log.info("You have warped: " + args[1] + " to warp: " + args[0]);
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