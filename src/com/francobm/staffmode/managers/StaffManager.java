package com.francobm.staffmode.managers;

import com.francobm.staffmode.StaffMode;
import com.francobm.staffmode.cache.PlayerCache;
import com.francobm.staffmode.cache.SavedPlayer;
import com.francobm.staffmode.extras.DiscordWebhook;
import com.francobm.staffmode.packets.title.TitlePacket;
import com.francobm.staffmode.utils.PlayerUtil;
import com.francobm.staffmode.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StaffManager {
    private final StaffMode plugin;
    private final TitlePacket titlePacket;

    public StaffManager(StaffMode plugin){
        this.plugin = plugin;
        titlePacket = new TitlePacket();
    }

    public void gameMode(Player player, Player target, GameMode gameMode){
        if(!player.hasPermission("staffmode.gamemode")){
            player.sendMessage(plugin.getMessage("no_perms", true));
            return;
        }
        player.sendMessage(plugin.getMessage("gamemode.others", true).replace("%target%", target.getName()).replace("%gamemode%", gameMode.name()));
        target.setGameMode(gameMode);
        target.sendMessage(plugin.getMessage("gamemode.own", true).replace("%gamemode%", gameMode.name()));
    }

    public void gameMode(Player player, GameMode gameMode){
        if(!player.hasPermission("staffmode.gamemode")){
            player.sendMessage(plugin.getMessage("no_perms", true));
            return;
        }
        player.setGameMode(gameMode);
        player.sendMessage(plugin.getMessage("gamemode.own", true).replace("%gamemode%", gameMode.name()));
    }

    public void inventorySee(Player player, Player target){
        if(!player.hasPermission("staffmode.invsee")){
            player.sendMessage(plugin.getMessage("no_perms", true));
            return;
        }
        InventoryManager inventoryManager = new InventoryManager(plugin, target);
        player.openInventory(inventoryManager.getInventory());
    }

    public void tpRandom(Player player){
        if(!player.hasPermission("staffmode.tpr")) {
            player.sendMessage(plugin.getMessage("no_perms", true));
            return;
        }
        PlayerCache playerCache = plugin.getPlayerCache(player);
        if(playerCache == null) return;
        List<Player> playerList = new ArrayList<>();
        for(Player p : Bukkit.getOnlinePlayers()){
            if(p.getName().equalsIgnoreCase(player.getName())){
                continue;
            }
            playerList.add(p);
        }
        if(playerList.size() == 0){
            player.sendMessage(plugin.getMessage("tpr.error", true));
            return;
        }

        int random = new Random().nextInt(playerList.size());
        Player p = playerList.get(random);
        player.teleport(p.getLocation());
        player.sendMessage(plugin.getMessage("tpr.success", true).replace("%player%", p.getName()));
    }
    public void revive(Player player, Player target){
        if(!player.hasPermission("staffmode.revive")) {
            player.sendMessage(plugin.getMessage("no_perms", true));
            return;
        }
        if(player.getName().equalsIgnoreCase(target.getName())){
            player.sendMessage(plugin.getMessage("revive.same_player", true));
            return;
        }
        PlayerCache playerCache = plugin.getPlayerCache(target);
        if(playerCache == null) return;
        if(playerCache.isMode()){
            player.sendMessage(plugin.getMessage("revive.others.staff", true).replace("%target%", target.getName()));
            return;
        }
        if(playerCache.getSavedItems() == null){
            player.sendMessage(plugin.getMessage("revive.others.error", true));
            return;
        }
        ItemStack[] savedInventory = playerCache.getSavedItems().getSavedInventory();
        ItemStack[] savedEquipment = playerCache.getSavedItems().getSavedEquipment();
        float savedXP = playerCache.getSavedItems().getSavedXP();
        int savedLevel = playerCache.getSavedItems().getSavedLevel();
        target.getInventory().setContents(savedInventory);
        target.getInventory().setArmorContents(savedEquipment);
        target.setExp(savedXP);
        target.setLevel(savedLevel);
        target.updateInventory();
        target.sendMessage(plugin.getMessage("revive.own.success", true));
        player.sendMessage(plugin.getMessage("revive.others.success", true).replace("%target%", target.getName()));
    }

    public void revive(Player player){
        if(!player.hasPermission("staffmode.revive")) {
            player.sendMessage(plugin.getMessage("no_perms", true));
            return;
        }
        PlayerCache playerCache = plugin.getPlayerCache(player);
        if(playerCache == null) return;
        if(playerCache.isMode()){
            player.sendMessage(plugin.getMessage("revive.own.staff", true));
            return;
        }
        if(playerCache.getSavedItems() == null){
            player.sendMessage(plugin.getMessage("revive.own.error", true));
            return;
        }
        ItemStack[] savedInventory = playerCache.getSavedItems().getSavedInventory();
        ItemStack[] savedEquipment = playerCache.getSavedItems().getSavedEquipment();
        float savedXP = playerCache.getSavedItems().getSavedXP();
        int savedLevel = playerCache.getSavedItems().getSavedLevel();
        player.getInventory().setContents(savedInventory);
        player.getInventory().setArmorContents(savedEquipment);
        player.setExp(savedXP);
        player.setLevel(savedLevel);
        player.updateInventory();
        player.sendMessage(plugin.getMessage("revive.own.success", true));
    }

    public void buildMode(Player player){
        if(!player.hasPermission("staffmode.buildmode")) {
            player.sendMessage(plugin.getMessage("no_perms", true));
            return;
        }
        PlayerCache playerCache = plugin.getPlayerCache(player);
        if(playerCache == null) return;
        if(!playerCache.isBuild()){
            titlePacket.sendFullTitle(player, plugin.getMessage("build-mode.join.title", true), plugin.getMessage("build-mode.join.subtitle", true));
            playerCache.setBuild(true);
            return;
        }
        titlePacket.sendFullTitle(player, plugin.getMessage("build-mode.exit.title", true), plugin.getMessage("build-mode.exit.subtitle", true));
        playerCache.setBuild(false);
    }

    public void staffMode(Player player){
        if(!player.hasPermission("staffmode.use")) {
            player.sendMessage(plugin.getMessage("no_perms", true));
            return;
        }
        PlayerCache playerCache = plugin.getPlayerCache(player);
        if(playerCache == null) return;
        if(playerCache.isMode()) {
            if(playerCache.isVanish()){
                vanish(player);
            }
            ItemStack[] savedInventory = playerCache.getSavedItems().getSavedInventory();
            ItemStack[] savedEquipment = playerCache.getSavedItems().getSavedEquipment();
            GameMode savedGameMode = playerCache.getSavedItems().getSavedGameMode();
            float savedXP = playerCache.getSavedItems().getSavedXP();
            int savedLevel = playerCache.getSavedItems().getSavedLevel();
            int hungerSaved = playerCache.getSavedItems().getHungerSaved();
            double healthSaved = playerCache.getSavedItems().getHealthSaved();
            double maxHealthSaved = playerCache.getSavedItems().getMaxHealthSaved();
            boolean flyingSaved = playerCache.getSavedItems().isFlyingSaved();
            boolean allowFlightSaved = playerCache.getSavedItems().isAllowFlightSaved();
            player.getInventory().setContents(savedInventory);
            player.getInventory().setArmorContents(savedEquipment);
            player.setGameMode(savedGameMode);
            player.setExp(savedXP);
            player.setLevel(savedLevel);
            player.setFoodLevel(hungerSaved);
            player.setMaxHealth(maxHealthSaved);
            player.setHealth(healthSaved);
            player.setAllowFlight(allowFlightSaved);
            player.setFlying(flyingSaved);
            player.updateInventory();
            playerCache.setMode(false);
            titlePacket.sendFullTitle(player, plugin.getMessage("staff-mode.exit.title", true), plugin.getMessage("staff-mode.exit.subtitle", true));
            return;
        }
        playerCache.setSavedItems(new SavedPlayer(player.getInventory().getContents(), player.getInventory().getArmorContents(), player.getGameMode(), player.getExp(), player.getLevel(), player.getFoodLevel(), player.getHealth(), player.getMaxHealth(), player.isFlying(), player.getAllowFlight()));
        player.getInventory().clear();
        player.getEquipment().clear();
        player.getEquipment().setArmorContents(null);
        player.updateInventory();
        player.setGameMode(GameMode.ADVENTURE);
        player.setExp(0);
        player.setLevel(0);
        player.setFoodLevel(20);
        player.setMaxHealth(20);
        player.setHealth(20);
        player.setAllowFlight(true);
        player.setFlying(true);
        PlayerUtil.setItemsStaff(player);
        playerCache.setMode(true);
        titlePacket.sendFullTitle(player, plugin.getMessage("staff-mode.join.title", true), plugin.getMessage("staff-mode.join.subtitle", true));
    }

    public void freeze(Player player, Player target){
        if(!player.hasPermission("staffmode.freeze")){
            player.sendMessage(plugin.getMessage("no_perms", true));
            return;
        }
        if(target.hasPermission("staffmode.use")){
            player.sendMessage(plugin.getMessage("target_op", true));
            return;
        }
        PlayerCache playerCache = plugin.getPlayerCache(player);
        PlayerCache targetCache = plugin.getPlayerCache(target);
        if(playerCache == null || targetCache == null) return;
        if(targetCache.isFreeze()){
            target.removePotionEffect(PotionEffectType.BLINDNESS);
            PlayerUtil.allowMovement(target);
            targetCache.setFreeze(false);
            titlePacket.sendFullTitle(target, plugin.getMessage("unfreeze.title", true), plugin.getMessage("unfreeze.subtitle", true));
            player.sendMessage(plugin.getMessage("unfreeze.staff", true).replace("%player%", target.getName()));
            return;
        }
        if(player.getName().equalsIgnoreCase(target.getName())){
            player.sendMessage(plugin.getMessage("freeze.same_player", true));
            return;
        }
        target.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, Integer.MAX_VALUE, 4, false, false));
        PlayerUtil.denyMovement(target);
        targetCache.setFreeze(true);
        titlePacket.sendFullTitle(target, plugin.getMessage("freeze.title", true), plugin.getMessage("freeze.subtitle", true));
        Utils.ListColor(target, plugin.getConfig().getStringList("messages.freeze.message"));
        player.sendMessage(plugin.getMessage("freeze.staff", true).replace("%player%", target.getName()));
        sendDiscordWebhook(player, target);
    }

    public void freeze(ConsoleCommandSender sender, Player target){
        if(target.hasPermission("staffmode.use")){
            sender.sendMessage(plugin.getMessage("target_op", true));
            return;
        }
        PlayerCache targetCache = plugin.getPlayerCache(target);
        if(targetCache == null) return;
        if(targetCache.isFreeze()){
            target.removePotionEffect(PotionEffectType.BLINDNESS);
            PlayerUtil.allowMovement(target);
            targetCache.setFreeze(false);
            titlePacket.sendFullTitle(target, plugin.getMessage("unfreeze.title", true), plugin.getMessage("unfreeze.subtitle", true));
            sender.sendMessage(plugin.getMessage("unfreeze.staff", true).replace("%player%", target.getName()));
            return;
        }

        target.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, Integer.MAX_VALUE, 4, false, false));
        PlayerUtil.denyMovement(target);
        targetCache.setFreeze(true);
        titlePacket.sendFullTitle(target, plugin.getMessage("freeze.title", true), plugin.getMessage("freeze.subtitle", true));
        Utils.ListColor(target, plugin.getConfig().getStringList("messages.freeze.message"));
        sender.sendMessage(plugin.getMessage("freeze.staff", true).replace("%player%", target.getName()));
        sendDiscordWebhook(sender, target);
    }
    public void vanish(Player player, Player target){
        if(!player.hasPermission("staffmode.vanish")){
            player.sendMessage(plugin.getMessage("no_perms", true));
            return;
        }
        PlayerCache playerCache = plugin.getPlayerCache(player);
        PlayerCache targetCache = plugin.getPlayerCache(target);
        if(playerCache == null) return;
        if(targetCache.isVanish()) {
            for(Player players : Bukkit.getOnlinePlayers()){
                if(!players.canSee(target)){
                    players.showPlayer(target);
                }
            }
            target.removePotionEffect(PotionEffectType.INVISIBILITY);
            titlePacket.sendFullTitle(target, plugin.getMessage("vanish.own.visible.title", true), plugin.getMessage("vanish.own.visible.subtitle", true));
            titlePacket.sendFullTitle(player, plugin.getMessage("vanish.others.visible.title", true).replace("%target%", target.getName()), plugin.getMessage("vanish.others.visible.subtitle", true).replace("%target%", target.getName()));
            targetCache.setVanish(false);
            return;
        }
        for (Player players : Bukkit.getOnlinePlayers()) {
            if (players.canSee(target)) {
                if (!players.hasPermission("staffmode.use")) {
                    players.hidePlayer(target);
                }
            }
        }
        target.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 4, false, false));
        titlePacket.sendFullTitle(target, plugin.getMessage("vanish.own.invisible.title", true), plugin.getMessage("vanish.own.invisible.subtitle", true));
        titlePacket.sendFullTitle(player, plugin.getMessage("vanish.others.invisible.title", true).replace("%target%", target.getName()), plugin.getMessage("vanish.others.invisible.subtitle", true).replace("%target%", target.getName()));
        targetCache.setVanish(true);
    }

    public void vanish(Player player){
        if(!player.hasPermission("staffmode.vanish")){
            player.sendMessage(plugin.getMessage("no_perms", true));
            return;
        }
        PlayerCache playerCache = plugin.getPlayerCache(player);
        if(playerCache == null) return;
        if(playerCache.isVanish()) {
            for(Player players : Bukkit.getOnlinePlayers()){
                if(!players.canSee(player)){
                    players.showPlayer(player);
                }
            }
            player.removePotionEffect(PotionEffectType.INVISIBILITY);
            titlePacket.sendFullTitle(player, plugin.getMessage("vanish.own.visible.title", true), plugin.getMessage("vanish.own.visible.subtitle", true));
            playerCache.setVanish(false);
            return;
        }
        for (Player players : Bukkit.getOnlinePlayers()) {
            if (players.canSee(player)) {
                if (!players.hasPermission("staffmode.use")) {
                    players.hidePlayer(player);
                }
            }
        }
        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 4, false, false));
        titlePacket.sendFullTitle(player, plugin.getMessage("vanish.own.invisible.title", true), plugin.getMessage("vanish.own.invisible.subtitle", true));
        playerCache.setVanish(true);
    }

    public void mount(Player player, Entity entity){
        if(!player.hasPermission("staffmode.mount")){
            player.sendMessage(plugin.getMessage("no_perms", true));
            return;
        }
        PlayerCache playerCache = plugin.getPlayerCache(player);
        if(playerCache == null) return;
        if(entity.setPassenger(player)) {
            if (entity instanceof Player) {
                titlePacket.sendFullTitle((Player) entity, plugin.getMessage("mount.target.title", true), plugin.getMessage("mount.target.subtitle", true).replace("%player%", player.getName()));
            }
            titlePacket.sendFullTitle(player, plugin.getMessage("mount.player.title", true), plugin.getMessage("mount.player.subtitle", true).replace("%target%", entity.getName()));
        }
    }

    public void sendDiscordWebhook(ConsoleCommandSender staff, Player target){
        if(!plugin.isWebhook()) return;
        String title = plugin.getMessage("webhook.title", true);
        List<String> descriptionList = plugin.getConfig().getStringList("messages.webhook.description");
        String description = convertDescription(descriptionList, staff, target);
        if(title.isEmpty()){
            plugin.getLogger().info("Title is empty!");
            return;
        }
        DiscordWebhook discordWebhook = new DiscordWebhook(plugin.getConfig().getString("discord.webhookUrl"));
        discordWebhook.setAvatarUrl("https://www.mc-heads.net/avatar/FrancoBM");
        discordWebhook.setUsername("StaffMode");
        discordWebhook.setTts(false);
        discordWebhook.addEmbed(new DiscordWebhook.EmbedObject()
                .setTitle(title)
                .setDescription(description)
                .setColor(Color.PINK)
                .setThumbnail("https://www.mc-heads.net/avatar/" + target.getName())
                .setFooter("Logs","https://www.mc-heads.net/avatar/" + target.getName()));
        try{
            discordWebhook.execute();
        }catch (IOException exception){
            exception.printStackTrace();
        }
    }
    public String convertDescription(List<String> description, ConsoleCommandSender staff, Player target){
        if(description == null) {
            plugin.getLogger().info("Description is null!");
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        int i = 0;
        for(String desc : description){
            i++;
            if(desc.isEmpty()){
                desc = "\\n";
            }else {
                if (description.size() != i) {
                    desc = desc + "\\n";
                }
            }
            desc = desc.replace("%staff%", staff.getName()).replace("%target%", target.getName()).replace("%target_world%", target.getWorld().getName()).replace("%target_coords_x%",String.valueOf((int) target.getLocation().getX())).replace("%target_coords_y%",String.valueOf((int) target.getLocation().getY())).replace("%target_coords_z%",String.valueOf((int) target.getLocation().getZ()));
            stringBuilder.append(desc);
        }
        return stringBuilder.toString();
    }
    public String convertDescription(List<String> description, Player staff, Player target){
        if(description == null) {
            plugin.getLogger().info("Description is null!");
            return "";
        }
        int i = 0;
        StringBuilder stringBuilder = new StringBuilder();
        for(String desc : description){
            i++;
            if(desc.isEmpty()){
                desc = "\\n";
            }else {
                if (description.size() != i) {
                    desc = desc + "\\n";
                }
            }
            desc = desc.replace("%staff%", staff.getName()).replace("%target%", target.getName()).replace("%target_world%", target.getWorld().getName()).replace("%target_coords_x%",String.valueOf((int) target.getLocation().getX())).replace("%target_coords_y%",String.valueOf((int) target.getLocation().getY())).replace("%target_coords_z%",String.valueOf((int) target.getLocation().getZ()));
            stringBuilder.append(desc);
        }
        return stringBuilder.toString();
    }
    public void sendDiscordWebhook(Player staff, Player target){
        if(!plugin.isWebhook()) return;
        String title = plugin.getMessage("webhook.title", true);
        List<String> descriptionList = plugin.getConfig().getStringList("messages.webhook.description");
        String description = convertDescription(descriptionList, staff, target);
        if(title.isEmpty()){
            plugin.getLogger().info("Title is empty!");
            return;
        }
        DiscordWebhook discordWebhook = new DiscordWebhook(plugin.getConfig().getString("discord.webhookUrl"));
        discordWebhook.setAvatarUrl("https://www.mc-heads.net/avatar/" + staff.getName());
        discordWebhook.setUsername("StaffMode");
        discordWebhook.setTts(false);
        discordWebhook.addEmbed(new DiscordWebhook.EmbedObject()
                .setTitle(title)
                .setDescription("¡Oh!, parece que un `miembro` de nuestro equipo acaba de detectar a un `usuario sospechoso`! Consulte los registros para obtener mas `informacion`\\n" +
                        "\\n" +
                        "▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬\\n" +
                        ":crossed_swords: **|** **Staff:**\\n" +
                        "➥ "+staff.getName()+"\\n" +
                        "\\n" +
                        ":key: **|** **Usuario:**\\n" +
                        "➥ "+target.getName()+"\\n" +
                        "\\n" +
                        ":earth_americas: **|** **Mundo:**\\n" +
                        "➥ "+target.getWorld().getName()+"\\n" +
                        "\\n" +
                        ":compass: **|** **Cordenadas:**\\n" +
                        "➥ "+((int) target.getLocation().getX())+ ", " +((int) target.getLocation().getY())+ ", " +((int) target.getLocation().getZ())+"\\n" +
                        "▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬")
                .setColor(Color.PINK)
                .setThumbnail("https://www.mc-heads.net/avatar/" + target.getName())
                .setFooter("Logs","https://www.mc-heads.net/avatar/" + target.getName()));
        try{
            discordWebhook.execute();
        }catch (IOException exception){
            exception.printStackTrace();
        }
    }

    public void sendDiscordWebhookTest(){
        if(!plugin.isWebhook()) return;
        DiscordWebhook discordWebhook = new DiscordWebhook(plugin.getConfig().getString("discord.webhookUrl"));
        discordWebhook.setUsername("StaffMode");
        discordWebhook.setTts(false);
        discordWebhook.addEmbed(new DiscordWebhook.EmbedObject()
                .setTitle(":shield: StaffMode >> Prueba de Webhook..")
                .setDescription("▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬\\n\\n:crossed_swords: **|** **Bienvenido:**\\n➥ Este es un `mensaje` que cumple la unica funcion de probar la webhook mediante discord y tu servidor de `mc`!\\n\\n:key: **|** **Informacion:**\\n➥ No se que poner aqui `:,P`\\n\\n▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬")
                .setColor(Color.PINK)
                .setThumbnail("https://www.mc-heads.net/avatar/FrancoBM")
                .setFooter("Logs","https://www.mc-heads.net/avatar/FrancoBM"));
        try {
            discordWebhook.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
