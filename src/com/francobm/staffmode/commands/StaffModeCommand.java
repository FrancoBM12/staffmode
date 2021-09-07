package com.francobm.staffmode.commands;

import com.francobm.staffmode.StaffMode;
import com.francobm.staffmode.cache.PlayerCache;
import com.francobm.staffmode.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StaffModeCommand implements CommandExecutor {
    private final StaffMode plugin;

    public StaffModeCommand(StaffMode plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(args.length >= 1){
                if(args[0].equalsIgnoreCase("help")){
                    sendHelp(player);
                }else if(args[0].equalsIgnoreCase("reload")){
                    plugin.reload();
                    player.sendMessage(plugin.getMessage("reload", true));
                }
                return true;
            }
            plugin.getStaffManager().staffMode(player);
        }
        return true;
    }

    public void sendHelp(Player player){
        if(player.hasPermission("staffmode.use")){
            for(String string : plugin.getHelp_page()){
                player.sendMessage(Utils.ChatColor(string).replace("%prefix%", plugin.getPluginName()));
            }
        }
    }
}
