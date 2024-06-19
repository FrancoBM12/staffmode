package com.francobm.staffmode.listeners;

import com.francobm.staffmode.StaffMode;
import com.francobm.staffmode.cache.PlayerCache;
import com.francobm.staffmode.cache.SavedPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class PlayerListener implements Listener {
    private final StaffMode plugin;

    public PlayerListener(StaffMode plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onRespawn(PlayerDeathEvent event){
        if(event.getEntity() == null) return;
        Player player = event.getEntity();
        PlayerCache playerCache = plugin.getPlayerCache(player);
        if(playerCache == null) return;
        if(playerCache.isDeath()){
            playerCache.setDeath(false);
        }
    }

    @EventHandler
    public void onPickUpItem(PlayerPickupItemEvent event){
        Player player = event.getPlayer();
        PlayerCache playerCache = plugin.getPlayerCache(player);
        if(playerCache == null) return;
        if(playerCache.isFreeze()){
            event.setCancelled(true);
            return;
        }
        if(playerCache.isMode()){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDropItem(PlayerDropItemEvent event){
        Player player = event.getPlayer();
        PlayerCache playerCache = plugin.getPlayerCache(player);
        if(playerCache == null) return;
        if(playerCache.isFreeze()){
            event.setCancelled(true);
            return;
        }
        if(playerCache.isMode()){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if(player.getInventory().getContents().length == 0) return;
        PlayerCache playerCache = plugin.getPlayerCache(player);
        if(playerCache == null) return;
        if(playerCache.isFreeze()){
            event.getDrops().clear();
            return;
        }
        if(playerCache.isMode()){
            event.getDrops().clear();
            return;
        }
        playerCache.setDeath(true);
        if(playerCache.isDeath()) {
            playerCache.setSavedItems(new SavedPlayer(player.getInventory().getContents(), player.getInventory().getArmorContents(), player.getExp(), player.getLevel()));
        }
    }
}
