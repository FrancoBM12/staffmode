package com.francobm.staffmode.managers;

import com.francobm.staffmode.StaffMode;
import com.francobm.staffmode.cache.PlayerCache;
import com.francobm.staffmode.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.List;

public class InventoryManager implements InventoryHolder {
    private final StaffMode plugin;
    private final Inventory inv;

    public InventoryManager(StaffMode plugin, Player player){
        this.plugin = plugin;
        inv = Bukkit.createInventory(this, 6*9, "Invsee " + player.getName());
        init(player);
    }

    private void init(Player player){
        ItemStack itemStack = createItem("&b", Material.STAINED_GLASS_PANE, 1, (short) 15);
        int a = 0;
        for(int i = 9; i < player.getInventory().getContents().length; i++){
            //plugin.getServer().getLogger().info(a+ " : " + i);
            inv.setItem(a, player.getInventory().getContents()[i]);
            a++;
        }
        a = 27;
        for(int i = 0; i < 9; i++){
            inv.setItem(a, player.getInventory().getContents()[i]);
            a++;
        }
        for(int i = 36; i < 45; i++){
            inv.setItem(i, itemStack);
        }
        inv.setItem(45, player.getInventory().getHelmet());
        inv.setItem(46, player.getInventory().getChestplate());
        inv.setItem(47, player.getInventory().getLeggings());
        inv.setItem(48, player.getInventory().getBoots());
        inv.setItem(49, player.getInventory().getItemInHand());
        inv.setItem(50, itemStack);
        itemStack = createItem(plugin.getMessage("inventory.health_item.name", true), plugin.getMessage("inventory.health_item.item", true), healthLore(player), 1);
        inv.setItem(51, itemStack);
        itemStack = createItem(plugin.getMessage("inventory.player_data_item.name", true), plugin.getMessage("inventory.player_data_item.item", true), dataLore(player), 1);
        inv.setItem(52, itemStack);
        itemStack = createItem(plugin.getMessage("inventory.potions_item.name", true), plugin.getMessage("inventory.potions_item.item", true), potionsLore(player), 1);
        inv.setItem(53, itemStack);
    }

    private List<String> healthLore(Player player){
        List<String> list = new ArrayList<>();
        for(String string : plugin.getConfig().getStringList("messages.inventory.health_item.lore")){
            list.add(Utils.ChatColor(string.replace("%max_health%", String.valueOf(player.getMaxHealth())).replace("%current_health%", String.valueOf(player.getHealth())).replace("%current_food%", String.valueOf(player.getFoodLevel()))));
        }
        return list;
    }

    private List<String> potionsLore(Player player){
        List<String> list = new ArrayList<>();
        String format = plugin.getMessage("inventory.potions_item.format", true);
        for(PotionEffect potionEffect : player.getActivePotionEffects()){
            list.add(format.replace("%potion_name%", potionEffect.getType().getName()).replace("%potion_level%", String.valueOf(potionEffect.getAmplifier())).replace("%potion_duration%", Utils.getTime(potionEffect.getDuration()*20)));
        }
        return list;
    }

    private List<String> dataLore(Player player){
        PlayerCache playerCache = plugin.getPlayerCache(player);
        List<String> list = new ArrayList<>();
        if(playerCache == null){
            list.add(Utils.ChatColor("&cERROR"));
            return list;
        }
        for(String string : plugin.getConfig().getStringList("messages.inventory.player_data_item.lore")){
            list.add(Utils.ChatColor(string.replace("%mode%", String.valueOf(playerCache.isMode())).replace("%freeze%", String.valueOf(playerCache.isFreeze())).replace("%build%", String.valueOf(playerCache.isBuild())).replace("%vanish%", String.valueOf(playerCache.isVanish()))));
        }
        return list;
    }

    private ItemStack createItem(String name, Material material, int amount, short s){
        ItemStack item = new ItemStack(material, amount, s);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Utils.ChatColor(name));
        item.setItemMeta(meta);
        return item;
    }
    private ItemStack createItem(String name, String material, List<String> lore,int amount){
        ItemStack item;
        if(material.contains(":")){
            item = new ItemStack(Material.getMaterial(material.split(":")[0]), amount, Short.parseShort(material.split(":")[1]));
        }else {
            item = new ItemStack(Material.getMaterial(material), amount);
        }
        ItemMeta meta = item.getItemMeta();
        meta.setLore(lore);
        meta.setDisplayName(Utils.ChatColor(name));
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public Inventory getInventory() {
        return inv;
    }
}
