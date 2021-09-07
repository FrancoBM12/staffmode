package com.francobm.staffmode.listeners;

import com.francobm.staffmode.StaffMode;
import com.francobm.staffmode.cache.PlayerCache;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {
    private final StaffMode plugin;

    public JoinListener(StaffMode plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void OnJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        PlayerCache playerCache = plugin.getPlayerCache(player);
        if(playerCache == null){
            plugin.addPlayerCache(new PlayerCache(player));
        }
    }
}
