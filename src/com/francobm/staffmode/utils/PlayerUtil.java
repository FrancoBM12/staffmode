package com.francobm.staffmode.utils;

import com.francobm.staffmode.StaffMode;
import com.francobm.staffmode.cache.PlayerCache;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerUtil {

    public static void denyMovement(Player player) {
        player.setWalkSpeed(0.0F);
        player.setFlySpeed(0.0F);
        player.setFoodLevel(0);
        player.setSprinting(false);
        player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 200, false, false));
    }

    public static void setItemsStaff(Player player){
        PlayerCache playerCache = StaffMode.getInstance().getPlayerCache(player);
        if(playerCache == null) return;
        ItemStack itemStack;
        ItemMeta itemMeta;
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
        itemStack = new ItemStack(Material.PACKED_ICE, 1);
        itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(Utils.ChatColor("&9&lFreeze"));
        itemStack.setItemMeta(itemMeta);
        player.getInventory().setItem(0, itemStack);
        itemStack = new ItemStack(Material.CHEST, 1);
        itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(Utils.ChatColor("&d&lInvsee"));
        itemStack.setItemMeta(itemMeta);
        player.getInventory().setItem(4, itemStack);
        itemStack = new ItemStack(Material.LEASH, 1);
        itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(Utils.ChatColor("&6&lMount"));
        itemStack.setItemMeta(itemMeta);
        player.getInventory().setItem(7, itemStack);
        itemStack = new ItemStack(Material.WATCH, 1);
        itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(Utils.ChatColor("&3&lTP Random"));
        itemStack.setItemMeta(itemMeta);
        player.getInventory().setItem(8, itemStack);
    }

    public static void allowMovement(Player player) {
        player.setWalkSpeed(0.2F);
        player.setFlySpeed(0.1F);
        player.setFoodLevel(20);
        player.setSprinting(true);
        player.removePotionEffect(PotionEffectType.JUMP);
    }
}
