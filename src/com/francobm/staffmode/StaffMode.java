package com.francobm.staffmode;

import com.francobm.staffmode.cache.PlayerCache;
import com.francobm.staffmode.commands.*;
import com.francobm.staffmode.listeners.*;
import com.francobm.staffmode.managers.StaffManager;
import com.francobm.staffmode.utils.Utils;
import com.francobm.staffmode.utils.hookPlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class StaffMode extends JavaPlugin {
    private StaffManager staffManager;
    private ArrayList<PlayerCache> players;
    private static StaffMode Instance;
    private List<String> help_page;
    private String pluginName;
    private boolean isWebhook;

    @Override
    public void onEnable() {
        super.onEnable();
        Instance = this;
        staffManager = new StaffManager(this);
        players = new ArrayList<>();
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        help_page = getConfig().getStringList("messages.help");
        pluginName = getConfig().getString("messages.prefix");
        isWebhook = getConfig().getBoolean("discord.active");
        if(isWebhook){
            getServer().getLogger().info("StaffMode >> Discord Webhook Activado!");
        }
        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null){
            new hookPlaceholderAPI(this).register();
        }
        registerCommands();
        registerListeners();
        if(Bukkit.getOnlinePlayers().size() > 0){
            for(Player player : Bukkit.getOnlinePlayers()){
                addPlayerCache(new PlayerCache(player));
            }
        }
    }

    public PlayerCache addPlayerCache(PlayerCache playerCache){
        this.players.add(playerCache);
        return playerCache;
    }
    public PlayerCache getPlayerCache(Player player){
        for(PlayerCache playerCache : this.players){
            if(playerCache.getPlayer().getName().equalsIgnoreCase(player.getName())){
                return playerCache;
            }
        }
        return null;
    }

    public void removePlayerCache(PlayerCache playerCache){
        this.players.remove(playerCache);
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    public void registerCommands(){
        getCommand("vanish").setExecutor(new VanishCommand(this));
        getCommand("freeze").setExecutor(new FreezeCommand(this));
        getCommand("staffmode").setExecutor(new StaffModeCommand(this));
        getCommand("revive").setExecutor(new ReviveCommand(this));
        getCommand("invsee").setExecutor(new InvseeCommand(this));
        getCommand("gamemode").setExecutor(new GamemodeCommand(this));
        getCommand("webhook").setExecutor(new WebhookCommand(this));
        getCommand("buildmode").setExecutor(new BuildModeCommand(this));

    }

    public void registerListeners(){
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new JoinListener(this), this);
        pm.registerEvents(new LeaveListener(this), this);
        pm.registerEvents(new InventoryListener(this), this);
        pm.registerEvents(new PlayerListener(this), this);
        pm.registerEvents(new EntityListener(this), this);
        pm.registerEvents(new BlockListener(this), this);
    }

    public void reload(){
        getLogger().info(Utils.ChatColor("StaffMode >> Plugin reloading.."));
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        reloadConfig();
        pluginName = getConfig().getString("messages.prefix");
        help_page = getConfig().getStringList("messages.help");
        isWebhook = getConfig().getBoolean("discord.active");
        getLogger().info("StafMode " + getDescription().getVersion() + " has successfully reloaded!");
    }

    public String getMessage(String path, boolean live){
        if(live){
            if(getConfig().getString("messages."+path) == null){
                return Utils.ChatColor("StaffMode &cError path Message Not Found");
            }else{
                return Utils.ChatColor(getConfig().getString("messages."+path));
            }
        }else{
            if(getConfig().getString(path) == null) {
                return Utils.ChatColor("StaffMode &cError path Not Found");
            }else{
                return Utils.ChatColor(getConfig().getString(path));
            }
        }
    }

    public void disableWebhook(){
        isWebhook = false;
        getConfig().set("discord.active", false);
    }

    public void enableWebhook(){
        isWebhook = true;
        getConfig().set("discord.active", true);
    }

    public int getFakePlayers(){
        int count = 0;
        if(Bukkit.getOnlinePlayers().size() == 0) return 0;
        for(Player p : Bukkit.getOnlinePlayers()){
            count++;
            PlayerCache pCache = getPlayerCache(p);
            if(pCache == null) continue;
            if(pCache.isVanish()){
                count--;
            }
        }
        return count;
    }

    public static StaffMode getInstance() {
        return Instance;
    }

    public StaffManager getStaffManager() {
        return staffManager;
    }

    public List<String> getHelp_page() {
        return help_page;
    }

    public String getPluginName() {
        return pluginName;
    }

    public boolean isWebhook() {
        return isWebhook;
    }
}
