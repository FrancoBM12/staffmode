package com.francobm.staffmode.listeners;

import com.francobm.staffmode.StaffMode;
import com.francobm.staffmode.cache.PlayerCache;
import com.francobm.staffmode.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class EntityListener implements Listener {
    private final StaffMode plugin;

    public EntityListener(StaffMode plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void PlayerInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        PlayerCache playerCache = plugin.getPlayerCache(player);
        if(playerCache.isFreeze()){
            event.setCancelled(true);
            return;
        }
        if(item == null || item.getType() == Material.AIR || !item.getItemMeta().hasDisplayName()) return;
        ItemStack itemStack;
        ItemMeta itemMeta;
        if(ChatColor.stripColor(item.getItemMeta().getDisplayName()).contains("Vanish")){
            event.setCancelled(true);
            plugin.getStaffManager().vanish(player);
            if(playerCache.isVanish()) {
                itemStack = new ItemStack(Material.INK_SACK, 1, (short) 8);
                itemMeta = itemStack.getItemMeta();
                itemMeta.setDisplayName(Utils.ChatColor("&b&lVanish &7(&aInvisible&7)"));
            }else{
                itemStack = new ItemStack(Material.INK_SACK, 1, (short) 10);
                itemMeta = itemStack.getItemMeta();
                itemMeta.setDisplayName(Utils.ChatColor("&b&lVanish &7(&cVisible&7)"));
            }
            itemStack.setItemMeta(itemMeta);
            player.getInventory().setItem(1, itemStack);
        }
        if(ChatColor.stripColor(item.getItemMeta().getDisplayName()).equalsIgnoreCase("TP Random")){
            plugin.getStaffManager().tpRandom(player);
        }
        if(ChatColor.stripColor(item.getItemMeta().getDisplayName()).equalsIgnoreCase("Freeze") || ChatColor.stripColor(item.getItemMeta().getDisplayName()).equalsIgnoreCase("Invsee")){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void EntityDamage(EntityDamageEvent event){
        if(event.getEntity() instanceof Player){
            Player player = (Player) event.getEntity();
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
    }

    @EventHandler
    public void EntityDamageByEntity(EntityDamageByEntityEvent event){
        if(event.getEntity() instanceof Player){
            Player player = (Player) event.getEntity();
            PlayerCache playerCache = plugin.getPlayerCache(player);
            if(playerCache == null) return;
            if(playerCache.isFreeze()){
                event.setCancelled(true);
                return;
            }
            if(playerCache.isMode()){
                event.setCancelled(true);
                return;
            }
        }
        if(event.getDamager() instanceof Player){
            Player player = (Player) event.getDamager();
            PlayerCache playerCache = plugin.getPlayerCache(player);
            if(playerCache == null) return;
            if(playerCache.isFreeze()){
                event.setCancelled(true);
            }
        }
    }
    @EventHandler
    public void InteractEntity(PlayerInteractEntityEvent event){
        Player player = event.getPlayer();
        if(player.getInventory().getItemInHand() == null) return;
        if(player.getInventory().getItemInHand().getType() == Material.AIR) return;
        if(!player.getInventory().getItemInHand().getItemMeta().hasDisplayName()) return;
        if(!player.hasPermission("staffmode.use")) return;
        PlayerCache playerCache = plugin.getPlayerCache(player);
        if(playerCache == null) return;
        switch (ChatColor.stripColor(player.getInventory().getItemInHand().getItemMeta().getDisplayName()).toLowerCase()){
            case "invsee":
                if(event.getRightClicked() instanceof Player){
                    Player target = (Player) event.getRightClicked();
                    plugin.getStaffManager().inventorySee(player, target);
                }
                break;
            case "freeze":
                if(event.getRightClicked() instanceof Player){
                    Player target = (Player) event.getRightClicked();
                    plugin.getStaffManager().freeze(player, target);
                }
                break;
            case "mount":
                event.setCancelled(true);
                plugin.getStaffManager().mount(player, event.getRightClicked());
                break;
        }
    }
}