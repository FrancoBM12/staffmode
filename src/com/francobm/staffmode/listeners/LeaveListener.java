package com.francobm.staffmode.listeners;

import com.francobm.staffmode.StaffMode;
import com.francobm.staffmode.cache.PlayerCache;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class LeaveListener implements Listener {
    private final StaffMode plugin;

    public LeaveListener(StaffMode plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        PlayerCache playerCache = plugin.getPlayerCache(player);
        if(playerCache == null) return;
        if(playerCache.isMode()){
            plugin.getStaffManager().staffMode(player);
        }
    }
}
