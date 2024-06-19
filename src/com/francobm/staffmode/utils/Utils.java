package com.francobm.staffmode.utils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static String ChatColor(String string){
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static String getTime(int time){
        int hours = time / 3600;
        int i = time - hours * 3600;
        int minutes = i / 60;
        int seconds = i - minutes * 60;
        String secondsMsg = "";
        String minutesMsg = "";
        String hoursMsg = "";
        if(seconds >= 0 && seconds <= 9){
            secondsMsg = "0"+seconds+"s";
        }else{
            secondsMsg = seconds+"s";
        }
        if(minutes >= 0 && minutes <= 9){
            minutesMsg = "0"+minutes+"m";
        }else{
            minutesMsg = minutes+"m";
        }
        if(hours >= 0 && hours <= 9){
            hoursMsg = "0"+hours+"h";
        }else{
            hoursMsg = hours+"h";
        }

        if(hours != 0)
        {
            return hoursMsg + " " + minutesMsg + " " + secondsMsg;
        }else if(minutes != 0) {
            return minutesMsg + " " + secondsMsg;
        }
        return secondsMsg;
    }

    public static void ListColor(Player player, List<String> stringList){
        for(String s : stringList){
            player.sendMessage(ChatColor(s));
        }
    }
}
