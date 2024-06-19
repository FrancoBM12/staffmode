package com.francobm.staffmode.commands;

import com.francobm.staffmode.StaffMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class WebhookCommand implements CommandExecutor {
    private final StaffMode plugin;

    public WebhookCommand(StaffMode plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(args.length >= 1){
            if(args[0].equalsIgnoreCase("test")){
                plugin.getStaffManager().sendDiscordWebhookTest();
            }else if(args[0].equalsIgnoreCase("enable")){
                plugin.enableWebhook();
            }else if(args[0].equalsIgnoreCase("disable")){
                plugin.disableWebhook();
            }
        }
        return true;
    }
}
