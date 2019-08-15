package dev.nuer.sd.trial.cmd;

import dev.nuer.sd.trial.SDGui;
import dev.nuer.sd.trial.enable.FileManager;
import dev.nuer.sd.trial.utils.MessageUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SDGuiCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = null;
        if (sender instanceof Player) {
            player = (Player) sender;
        }
        if (args.length == 0) {
            if (player!= null) {
                if (player.hasPermission("sd-gui.help")) {
                    MessageUtil.message("messages", "help", player);
                } else {
                    MessageUtil.message("messages", "permission-debug", player,
                            "{node}", "sd-gui.help");
                }
            } else {
                SDGui.log.info("Only players can use the commands: /warp and /kit, try /sd-gui reload.");
            }
        } else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("r") || args[0].equalsIgnoreCase("reload")) {
                if (player!= null) {
                    if (player.hasPermission("sd-gui.reload")) {
                        FileManager.reload();
                        MessageUtil.message("messages", "reload", player);
                    } else {
                        MessageUtil.message("messages", "permission-debug", player,
                                "{node}", "sd-gui.reload");
                    }
                } else {
                    FileManager.reload();
                    SDGui.log.info("All configuration files have been successfully reloaded.");
                }
            } else if (args[0].equalsIgnoreCase("h") || args[0].equalsIgnoreCase("help")) {
                if (player!= null) {
                    if (player.hasPermission("sd-gui.help")) {
                        MessageUtil.message("messages", "help", player);
                    } else {
                        MessageUtil.message("messages", "permission-debug", player,
                                "{node}", "sd-gui.help");
                    }
                } else {
                    SDGui.log.info("Only players can use the commands: /warp and /kit, try /sdgui reload.");
                }
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
