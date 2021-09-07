package com.francobm.staffmode.commands;

import com.francobm.staffmode.StaffMode;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BuildModeCommand implements CommandExecutor {
    private final StaffMode plugin;

    public BuildModeCommand(StaffMode plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(args.length == 0){
                plugin.getStaffManager().buildMode(player);
            }
        }
        return true;
    }
}
