package com.francobm.staffmode.listeners;

import com.francobm.staffmode.StaffMode;
import com.francobm.staffmode.cache.PlayerCache;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;

public class InventoryListener implements Listener {
    private final StaffMode plugin;

    public InventoryListener(StaffMode plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void OnInteractInventory(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        if(event.getView().getTopInventory().getTitle().contains("Invsee ")){
            event.setCancelled(true);
            return;
        }
        PlayerCache playerCache = plugin.getPlayerCache(player);
        if(playerCache == null) return;
        if(playerCache.isFreeze()){
            event.setCancelled(true);
            return;
        }
        if(playerCache.isMode()){
            if(event.getCurrentItem() == null) {
                event.setCancelled(true);
                return;
            };
            event.setCancelled(true);
        }
    }
}
