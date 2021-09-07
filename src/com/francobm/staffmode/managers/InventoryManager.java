package com.francobm.staffmode.managers;

import com.francobm.staffmode.StaffMode;
import com.francobm.staffmode.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class InventoryManager implements InventoryHolder {
    private StaffMode plugin;
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
    }

    private ItemStack createItem(String name, Material material, int amount, short s){
        ItemStack item = new ItemStack(material, amount, s);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Utils.ChatColor(name));
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public Inventory getInventory() {
        return inv;
    }
}
