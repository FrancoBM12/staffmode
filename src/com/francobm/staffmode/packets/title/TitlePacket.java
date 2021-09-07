package com.francobm.staffmode.packets.title;

import com.francobm.staffmode.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class TitlePacket {

    public void sendFullTitle(Player player, String title, String subtitle){
        sendTitlePacket(player, title, subtitle, 10, 20, 10);
        /*PacketPlayOutTitle t = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + Utils.ChatColor(title) + "\"}"));
        PacketPlayOutTitle st = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + Utils.ChatColor(subtitle) + "\"}"));
        PacketPlayOutTitle time = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, null, 10, 20, 10);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(t);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(st);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(time);*/
    }
    public void sendTitle(Player player, String message) {
        sendTitlePacket(player, message, "", 10, 20, 10);
        /*
        PacketPlayOutTitle title = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + Utils.ChatColor(message) + "\"}"));
        PacketPlayOutTitle subtitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, null);
        PacketPlayOutTitle time = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, null, 10, 20, 10);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(title);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(subtitle);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(time);
         */
    }

    public void sendSubtitle(Player player, String message) {
        sendTitlePacket(player, "", message, 10, 20, 10);
        /*
        PacketPlayOutTitle title = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, null);
        PacketPlayOutTitle subtitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + Utils.ChatColor(message) + "\"}"));
        PacketPlayOutTitle time = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, null, 10, 20, 10);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(title);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(subtitle);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(time);
         */
    }

    public void sendActionBar(Player player, String message) {
        sendActionBarPacket(player, message);
        /*PacketPlayOutChat actionBar = new PacketPlayOutChat(IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + Utils.ChatColor(message) + "\"}"), (byte)2);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(actionBar);*/
    }

    public void sendActionBarPacket(Player player, String message){
        try {
            Object actionBarChat = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, "{\"text\":\"" + Utils.ChatColor(message) + "\"}");
            Constructor<?> actionBarConstructor = getNMSClass("PacketPlayOutChat").getConstructor(getNMSClass("IChatBaseComponent"), byte.class);
            Object packetActionBar = actionBarConstructor.newInstance(actionBarChat, (byte)2);

            sendPacket(player, packetActionBar);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendTitlePacket(Player player, String title, String subTitle, int fadeIn, int stay, int fadeOut) {
        try {
            Object playOutTitle = getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TITLE").get(null);
            Object chatTitle = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, "{\"text\":\"" + Utils.ChatColor(title) + "\"}");
            Constructor<?> titleConstructor = getNMSClass("PacketPlayOutTitle").getConstructor(getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], getNMSClass("IChatBaseComponent"));
            Object packetTitle = titleConstructor.newInstance(playOutTitle, chatTitle);

            Object playOutSubTitle = getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("SUBTITLE").get(null);
            Object chatSubTitle = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, "{\"text\":\"" + Utils.ChatColor(subTitle) + "\"}");
            Constructor<?> subTitleConstructor = getNMSClass("PacketPlayOutTitle").getConstructor(getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], getNMSClass("IChatBaseComponent"));
            Object packetSubTitle = subTitleConstructor.newInstance(playOutSubTitle, chatSubTitle);

            Object PlayOutTimes = getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TIMES").get(null);
            Constructor<?> timesConstructor = getNMSClass("PacketPlayOutTitle").getConstructor(getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], getNMSClass("IChatBaseComponent"), int.class, int.class, int.class);
            Object packetTimes = timesConstructor.newInstance(PlayOutTimes, null, fadeIn, stay, fadeOut);

            sendPacket(player, packetTitle);
            sendPacket(player, packetSubTitle);
            sendPacket(player, packetTimes);
        }

        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void sendPacket(Player player, Object packet) {
        try {
            Object handle = player.getClass().getMethod("getHandle").invoke(player);
            Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
            playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet") /* Packet class */).invoke(playerConnection, packet);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public Class<?> getOBCClass(String Name){
        //import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
        String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        try {
            return Class.forName("org.bukkit.craftbukkit." + version + "." + Name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Class<?> getNMSClass(String name){
        //import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
        String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        try {
            return Class.forName("net.minecraft.server." + version + "." + name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
