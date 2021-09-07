package com.francobm.staffmode.listeners;

import com.francobm.staffmode.StaffMode;
import com.francobm.staffmode.cache.PlayerCache;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockListener implements Listener {
    private final StaffMode plugin;

    public BlockListener(StaffMode plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event){
        Player player = event.getPlayer();
        PlayerCache playerCache = plugin.getPlayerCache(player);
        if(playerCache == null) return;
        if(playerCache.isFreeze()){
            event.setCancelled(true);
            return;
        }
        if(!playerCache.isBuild()){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event){
        Player player = event.getPlayer();
        PlayerCache playerCache = plugin.getPlayerCache(player);
        if(playerCache == null) return;
        if(playerCache.isFreeze()){
            event.setCancelled(true);
            return;
        }
        if(!playerCache.isBuild()){
            event.setCancelled(true);
        }
    }
}
