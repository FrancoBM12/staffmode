package com.francobm.staffmode.commands;

import com.francobm.staffmode.StaffMode;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class FreezeCommand implements CommandExecutor {
    private StaffMode plugin;

    public FreezeCommand(StaffMode plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof ConsoleCommandSender){
            if(args.length >= 1){
                Player target = Bukkit.getPlayer(args[0]);
                if(target == null){
                    sender.sendMessage(plugin.getMessage("target_not_online", true).replace("%target%", args[0]));
                    return true;
                }
                plugin.getStaffManager().freeze((ConsoleCommandSender) sender, target);
            }
        }
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(args.length >= 1){
                Player target = Bukkit.getPlayer(args[0]);
                if(target == null){
                    player.sendMessage(plugin.getMessage("target_not_online", true).replace("%target%", args[0]));
                    return true;
                }
                plugin.getStaffManager().freeze(player, target);
            }
        }
        return true;
    }
}
