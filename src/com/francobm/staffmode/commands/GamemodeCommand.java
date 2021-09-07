package com.francobm.staffmode.commands;

import com.francobm.staffmode.StaffMode;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GamemodeCommand implements CommandExecutor {
    private final StaffMode plugin;

    public GamemodeCommand(StaffMode plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            boolean survival = label.equalsIgnoreCase("gms") || label.equalsIgnoreCase("survival") || label.equalsIgnoreCase("gm0");
            boolean creative = label.equalsIgnoreCase("gmc") || label.equalsIgnoreCase("creative") || label.equalsIgnoreCase("gm1") || label.equalsIgnoreCase("creativo");
            boolean adventure = label.equalsIgnoreCase("gma") || label.equalsIgnoreCase("adventure") || label.equalsIgnoreCase("gm2") || label.equalsIgnoreCase("aventura");
            boolean spectator = label.equalsIgnoreCase("gmspect") || label.equalsIgnoreCase("spectator") || label.equalsIgnoreCase("gm3") || label.equalsIgnoreCase("espectador");
            if(label.equalsIgnoreCase("gamemode") || label.equalsIgnoreCase("gm")){
                if(args.length == 2){
                    try{
                        int gamemode = Integer.parseInt(args[0]);
                        Player target = Bukkit.getPlayer(args[1]);
                        switch (gamemode){
                            case 0:
                                plugin.getStaffManager().gameMode(player, target, GameMode.SURVIVAL);
                                break;
                            case 1:
                                plugin.getStaffManager().gameMode(player, target, GameMode.CREATIVE);
                                break;
                            case 2:
                                plugin.getStaffManager().gameMode(player, target, GameMode.ADVENTURE);
                                break;
                            case 3:
                                plugin.getStaffManager().gameMode(player, target, GameMode.SPECTATOR);
                                break;
                            default:
                                player.sendMessage(plugin.getMessage("gamemode.error", true).replace("%gamemode%", args[0]));
                                break;
                        }
                    }catch (NumberFormatException exception){
                        player.sendMessage(plugin.getMessage("gamemode.error", true).replace("%gamemode%", args[0]));
                    }
                }
                if(args.length == 1){
                    try{
                        int gamemode = Integer.parseInt(args[0]);
                        switch (gamemode){
                            case 0:
                                plugin.getStaffManager().gameMode(player, GameMode.SURVIVAL);
                                break;
                            case 1:
                                plugin.getStaffManager().gameMode(player, GameMode.CREATIVE);
                                break;
                            case 2:
                                plugin.getStaffManager().gameMode(player, GameMode.ADVENTURE);
                                break;
                            case 3:
                                plugin.getStaffManager().gameMode(player, GameMode.SPECTATOR);
                                break;
                            default:
                                player.sendMessage(plugin.getMessage("gamemode.error", true).replace("%gamemode%", args[0]));
                                break;
                        }
                    }catch (NumberFormatException exception){
                        player.sendMessage(plugin.getMessage("gamemode.error", true).replace("%gamemode%", args[0]));
                    }
                }
                return true;
            }
            if(args.length >= 1){
                Player target = Bukkit.getPlayer(args[0]);
                if(target == null){
                    player.sendMessage(plugin.getMessage("target_not_online", true).replace("%target%", args[0]));
                    return true;
                }
                if(survival){
                    plugin.getStaffManager().gameMode(player, target, GameMode.SURVIVAL);
                    return true;
                }
                if(creative){
                    plugin.getStaffManager().gameMode(player, target, GameMode.CREATIVE);
                    return true;
                }
                if(adventure){
                    plugin.getStaffManager().gameMode(player, target, GameMode.ADVENTURE);
                    return true;
                }
                if(spectator){
                    plugin.getStaffManager().gameMode(player, target, GameMode.SPECTATOR);
                    return true;
                }
            }
            if(survival){
                plugin.getStaffManager().gameMode(player, GameMode.SURVIVAL);
                return true;
            }
            if(creative){
                plugin.getStaffManager().gameMode(player, GameMode.CREATIVE);
                return true;
            }
            if(adventure){
                plugin.getStaffManager().gameMode(player, GameMode.ADVENTURE);
                return true;
            }
            if(spectator){
                plugin.getStaffManager().gameMode(player, GameMode.SPECTATOR);
                return true;
            }
        }
        return true;
    }
}
