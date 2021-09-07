package com.francobm.staffmode.utils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static String ChatColor(String string){
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static void ListColor(Player player, List<String> stringList){
        for(String s : stringList){
            player.sendMessage(ChatColor(s));
        }
    }
}
